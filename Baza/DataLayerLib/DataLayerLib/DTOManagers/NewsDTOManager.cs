using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ObjectModel.DTOs;
using NHibernate;
using ObjectModel.Entities;
using BuisnessLogicLayer.DAOInterfaces;

namespace DataLayerLib.DTOManagers
{
    public class NewsDTOManager : NewsData
    {
        public List<NewsDTO> GetAllNews()
        {
            List<NewsDTO> news = new List<NewsDTO>();
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();

                IEnumerable<News> retData = from n in session.QueryOver<News>().List()
                                            select n;
                foreach (News n in retData)
                {
                    NewsDTO dto = new NewsDTO(n);

                    var newestDate = n.Modifications.Max(x => x.ModificationDate);
                    int LastModifiedUser = n.Modifications.First(x => x.ModificationDate == newestDate).User.Id;
                    dto.LastModifiedUser = new UserDTO(session.Load<User>(LastModifiedUser));

                    news.Add(dto);
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

        public List<SimpleNewsDTO> GetAllNewsSimple()
        {
            List<SimpleNewsDTO> news = new List<SimpleNewsDTO>();
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();

                IEnumerable<News> retData = from n in session.QueryOver<News>().List()
                                            select n;
                foreach (News n in retData)
                {
                    SimpleNewsDTO dto = new SimpleNewsDTO(n);

                    var newestDate = n.Modifications.Max(x => x.ModificationDate);
                    int LastModifiedUser = n.Modifications.First(x => x.ModificationDate == newestDate).Id;
                    dto.LastModifiedUser = new UserDTO(session.Load<User>(LastModifiedUser));

                    news.Add(dto);
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

        public List<NewsDTO> GetNewsModifiedByUser(int userId)
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
                    {
                        NewsDTO dto = new NewsDTO(m.News);
                        var newestDate = m.News.Modifications.Max(x => x.ModificationDate);
                        int LastModifiedUser = m.News.Modifications.First(x => x.ModificationDate == newestDate).Id;
                        dto.LastModifiedUser = new UserDTO(session.Load<User>(LastModifiedUser));
                        news.Add(dto);
                    }
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

        public NewsDTO GetNews(int newsId)
        {
            ISession session = null;
            NewsDTO result = null;
            try
            {
                session = DataLayer.GetSession();
                News news = session.Load<News>(newsId);
                result = new NewsDTO(news);
                var newestDate = news.Modifications.Max(x => x.ModificationDate);
                int LastModifiedUser = news.Modifications.First(x => x.ModificationDate == newestDate).Id;
                result.LastModifiedUser = new UserDTO(session.Load<User>(LastModifiedUser));

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

        public News GetFullNews(int newsId)
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

        public NewsDTO CreateNews(string title, string content,
            int userId)
        {
            NewsDTO result = null;
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

                result = new NewsDTO(news);

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

        public SimpleNewsDTO CreateNews(SimpleNewsDTO dto, int userId)
        {
            NewsDTO news= new NewsDTO(dto);
            SimpleNewsDTO result = null;
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

                ITransaction transaction = session.BeginTransaction();

                session.SaveOrUpdate(newNews);
                session.Save(modified);
                session.Flush();
                transaction.Commit();

                result = new SimpleNewsDTO(newNews);

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

        public NewsDTO CreateNews(NewsDTO news, int userId)
        {
            NewsDTO result = null;
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

                ITransaction transaction = session.BeginTransaction();

                session.SaveOrUpdate(newNews);
                session.Save(modified);
                session.Flush();
                transaction.Commit();

                result = new NewsDTO(newNews);

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

        public SimpleNewsDTO UpdateNews(SimpleNewsDTO simpleDTO, int userId)
        {
            SimpleNewsDTO result = null;
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

                result = new SimpleNewsDTO(news);

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

        public NewsDTO DeleteNews(int newsId)
        {
            ISession session = null;
            NewsDTO result = null;
            try
            {
                session = DataLayer.GetSession();
                News news = session.Load<News>(newsId);
                result = new NewsDTO(news);

                session.Delete(news);

                session.Flush();
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

        public bool DeleteNewsModifiaction(int modificationId)
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

        public News ExpandDTO(NewsDTO newsDTO)
        {
            News news = new News();
            news.LastModified = newsDTO.LastModified;
            news.Content = newsDTO.Content;
            news.Title = newsDTO.Title;
            if(newsDTO.BackgroundPicture != null)
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
