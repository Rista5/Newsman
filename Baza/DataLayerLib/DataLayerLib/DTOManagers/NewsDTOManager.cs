﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DataLayerLib.DTOs;
using NHibernate;
using DataLayerLib.Entities;
using DataLayerLib.MultimediaLoader;

namespace DataLayerLib.DTOManagers
{
    public class NewsDTOManager
    {
        private static IMultimediaLoader _loader;
        static IMultimediaLoader Loader
        {
            get
            {
                if (_loader == null)
                    _loader = new FileSystemLoader();
                return _loader;
            }
        }

        public static List<NewsDTO> GetAllNews()
        {
            List<NewsDTO> news = new List<NewsDTO>();
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();

                IEnumerable<News> retData = from n in session.Query<News>()
                                            select n;
                foreach (News n in retData)
                    news.Add(new NewsDTO(n));
                //TODO change this
                foreach(NewsDTO n in news)
                {
                    if(n.BackgroundPicture != null)
                        n.BackgroundPicture.SetPictureBytes(Loader.GetMedia(n.BackgroundPicture.Id,
                                                                        n.BackgroundPicture.BelongsToNewsId));
                    foreach (PictureDTO p in n.Pictures)
                        p.SetPictureBytes(Loader.GetMedia(p.Id, p.BelongsToNewsId));
                }
                session.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return news;
        }

        public static List<NewsDTO> GetNewsModifiedByUser(int userId)
        {
            List<NewsDTO> news = new List<NewsDTO>();
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                IEnumerable<NewsModified> retData = from m in session.Query<NewsModified>()
                                                    where m.User.Id == userId
                                                    select m;

                foreach (NewsModified m in retData)
                {
                    if (!news.Exists(x => x.Id == m.News.Id))
                        news.Add(new NewsDTO(m.News));
                }
                session.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return news;
        }

        public static NewsDTO GetNews(int newsId)
        {
            ISession session = null;
            NewsDTO result = null;
            try
            {
                session = DataLayer.GetSession();
                News user = session.Load<News>(newsId);
                result = new NewsDTO(user);
                session.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return result;
        }

        public static News GetFullNews(int newsId)
        {
            ISession session = null;
            News result = null;
            try
            {
                session = DataLayer.GetSession();
                result = session.Load<News>(newsId);
                session.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return result;
        }

        public static bool CreateNews(string title, string content,
            int userId)
        {
            bool result = false;
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                News news = new News();
                User user = session.Load<User>(userId);
                NewsModified modifiaction = new NewsModified();
                modifiaction.ModificationDate = DateTime.Today;
                modifiaction.User = user;
                modifiaction.News = news;

                news.LastModified = DateTime.Today;
                news.Title = title;
                news.Content = content;
                news.Modifications = new List<NewsModified>();
                news.Modifications.Add(modifiaction);

                ITransaction transaction = session.BeginTransaction();

                session.Save(news);
                session.Save(modifiaction);

                transaction.Commit();

                session.Flush();


                MessageQueueManager manager = MessageQueueManager.Instance;
                manager.PublishMessage(news.Id, news.Id, new NewsDTO(news), MessageOperation.Insert);

                session.Close();

                result = true;

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return result;
        }

        public static bool CreateNews(NewsDTO news, int userId)
        {
            bool result = false;
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                News newNews = ExpandDTO(news);
                newNews.LastModified = DateTime.Today;
                User user = session.Load<User>(userId);
                NewsModified modified = new NewsModified();
                modified.News = newNews;
                modified.User = user;
                modified.ModificationDate = DateTime.Today;
                modified.News = newNews;

                //if (news.BackgroundPicture != null)
                //{
                //    //TODO ovde ne moze da se snimi slika jer jos nisu perzistirani podaci u bazi!!!
                //    Picture backgroundPic = ExpandBackgroundPicture(news.BackgroundPicture);
                //    newNews.BackgroundPicture = backgroundPic;
                //}

                ITransaction transaction = session.BeginTransaction();
                
                //session.SaveOrUpdate(newNews.BackgroundPicture);
                session.SaveOrUpdate(newNews);
                session.Save(modified);
                session.Flush();

                transaction.Commit();

                MessageQueueManager manager = MessageQueueManager.Instance;
                NewsDTO message = new NewsDTO(newNews);
                if(news.BackgroundPicture != null)
                {
                    Loader.SaveMedia(newNews.BackgroundPicture.Id, newNews.Id, news.BackgroundPicture.GetPictureBytes());
                    message.BackgroundPicture = new PictureDTO(newNews.BackgroundPicture);
                    message.BackgroundPicture.PictureData = news.BackgroundPicture.PictureData;
                }

                //TODO ovo uopste ne izgleda dobro, ne znam kako bi bolje mogle da se ucitavaju slike

                for (int i = 0; i < newNews.Pictures.Count; i++)
                {
                    Picture p = newNews.Pictures[i];
                    Loader.SaveMedia(p.Id, newNews.Id, news.Pictures[i].GetPictureBytes());
                    //PictureDTO dto = new PictureDTO(p);
                    PictureDTO dto = message.Pictures[i];
                    dto.PictureData = news.Pictures[i].PictureData;
                    //message.Pictures.Add(dto);
                }
                manager.PublishMessage(newNews.Id, newNews.Id, message, MessageOperation.Insert);

                session.Close();

                result = true;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return result;
        }

        public static bool UpdateNews(int userId, int newsId,
            string title, string content)
        {
            bool result = false;
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                User user = session.Load<User>(userId);
                News news = session.Load<News>(newsId);
                news.Title = title;
                news.Content = content;
                news.LastModified = DateTime.Today;

                NewsModified modification = new NewsModified();
                modification.ModificationDate = DateTime.Today;
                modification.News = news;
                modification.User = user;

                session.SaveOrUpdate(news);
                session.Save(modification);
                session.Flush();

                MessageQueueManager manager = MessageQueueManager.Instance;
                manager.PublishMessage(news.Id, news.Id, new NewsDTO(news), MessageOperation.Update);

                session.Close();

                result = true;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return result;
        }

        public static bool UpdateNews(SimpleNewsDTO simpleDTO, int userId)
        {
            bool result = false;
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                User user = session.Load<User>(userId);
                News news = session.Load<News>(simpleDTO.Id);
                news.Title = simpleDTO.Title;
                news.Content = simpleDTO.Content;

                if (simpleDTO.BackgroundPicture != null && news.BackgroundPicture == null)
                {
                    news.BackgroundPicture = ExpandBackgroundPicture(simpleDTO.BackgroundPicture);
                }
                else if (simpleDTO.BackgroundPicture == null)
                    news.BackgroundPicture = null;
                news.LastModified = DateTime.Today;

                NewsModified modification = new NewsModified();
                modification.ModificationDate = DateTime.Today;
                modification.News = news;
                modification.User = user;

                session.SaveOrUpdate(news);
                session.Save(modification);
                session.Flush();

                MessageQueueManager manager = MessageQueueManager.Instance;
                SimpleNewsDTO message = new SimpleNewsDTO(news);
                if (simpleDTO.BackgroundPicture != null)
                {
                    Loader.SaveMedia(news.BackgroundPicture.Id, news.Id, simpleDTO.BackgroundPicture.GetPictureBytes());
                    //message.BackgroundPicture = new PictureDTO(news.BackgroundPicture);
                    message.BackgroundPicture.PictureData = simpleDTO.BackgroundPicture.PictureData;
                }
                manager.PublishMessage(news.Id, news.Id, message, MessageOperation.Update);

                session.Close();

                result = true;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return result;
        }

        public static bool UpdateNews(NewsDTO newsDTO, int userId)
        {
            bool result = false;
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                News news = ExpandDTO(newsDTO);
                news.LastModified = DateTime.Today;
                User user = session.Load<User>(userId);
                if (newsDTO.BackgroundPicture != null)
                {
                    Picture backgroundPic = ExpandBackgroundPicture(newsDTO.BackgroundPicture);
                    news.BackgroundPicture = backgroundPic;
                }
                else
                    news.BackgroundPicture = null;
                NewsModified modified = new NewsModified();
                modified.News = news;
                modified.User = user;
                modified.ModificationDate = DateTime.Today;
                modified.News = news;

                ITransaction transaction = session.BeginTransaction();
                session.Save(news);
                session.Save(modified);
                session.Flush();

                transaction.Commit();


                MessageQueueManager manager = MessageQueueManager.Instance;
                NewsDTO message = new NewsDTO(news);
                if (newsDTO.BackgroundPicture != null)
                {
                    Loader.SaveMedia(news.BackgroundPicture.Id, news.Id,
                        newsDTO.BackgroundPicture.GetPictureBytes());
                    message.BackgroundPicture = new PictureDTO(news.BackgroundPicture);
                    message.BackgroundPicture.PictureData = newsDTO.BackgroundPicture.PictureData;
                }
                //ovo uopste ne izgleda dobro, ne znam kako bi bolje mogle da se ucitavaju slike
                for (int i = 0; i < news.Pictures.Count; i++)
                {
                    Picture p = news.Pictures[i];
                    Loader.SaveMedia(p.Id, news.Id, newsDTO.Pictures[i].GetPictureBytes());
                    PictureDTO dto = new PictureDTO(p);
                    dto.PictureData = newsDTO.Pictures[i].PictureData;
                    message.Pictures.Add(dto);
                }
                manager.PublishMessage(news.Id, news.Id, new NewsDTO(news), MessageOperation.Update);

                session.Close();

                result = true;

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return result;
        }

        public static bool DeleteNews(int newsId)
        {
            ISession session = null;
            bool result = false;
            try
            {
                session = DataLayer.GetSession();
                News news = session.Load<News>(newsId);
                NewsDTO newsDTO = new NewsDTO(news);

                foreach (Picture p in news.Pictures)
                {
                    Loader.DeleteMedia(p.Id, news.Id);
                }
                if (news.BackgroundPicture != null)
                    Loader.DeleteMedia(news.BackgroundPicture.Id, news.Id);
                session.Delete(news);

                MessageQueueManager manager = MessageQueueManager.Instance;
                manager.PublishMessage(news.Id, news.Id, newsDTO, MessageOperation.Delete);

                session.Flush();
                session.Close();
                result = true;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }

            return result;
        }

        public static bool DeleteNewsModifiaction(int modificationId)
        {
            ISession session = null;
            bool result = false;
            try
            {
                session = DataLayer.GetSession();
                NewsModified modification = session.Load<NewsModified>(modificationId);
                session.Delete(modification);
                session.Flush();
                session.Close();
                result = true;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }

            return result;
        }

        public static News ExpandDTO(NewsDTO newsDTO)
        {
            News news = new News();
            news.LastModified = newsDTO.LasModified;
            news.Content = newsDTO.Content;
            news.Title = newsDTO.Title;
            if(newsDTO.BackgroundPicture.PictureData != null)
                news.BackgroundPicture = ExpandBackgroundPicture(newsDTO.BackgroundPicture);
            foreach(PictureDTO picture in newsDTO.Pictures)
            {
                news.Pictures.Add(ExpandPicture(picture, news));
            }
            foreach(AudioDTO audio in newsDTO.AudioRecordings)
            {
                Audio aud = new Audio();
                aud.BelongsTo = news;
                aud.Description = audio.Description;
                aud.Name = audio.Name;
                news.AudioRecordings.Add(aud);
            }
            foreach(CommentDTO comm in newsDTO.Comments)
            {
                Comment comment = new Comment();
                comment.BelongsTo = news;
                comment.Content = comm.Content;
                comment.PostDate = comm.PostDate;
                news.Comments.Add(comment);
            }
            return news;
        }

        public static Picture ExpandPicture(PictureDTO picture, News news)
        {
            Picture pic = new Picture(picture.Id, picture.Name, picture.Description, news);
            return pic;
        }

        public static Picture ExpandBackgroundPicture(PictureDTO picture)
        {
            Picture pic = new Picture(picture.Id, picture.Name, picture.Description, null);
            return pic;
        }
    }
}
