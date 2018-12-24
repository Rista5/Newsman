using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DataLayerLib.DTOs;
using NHibernate;
using DataLayerLib.Entities;


namespace DataLayerLib.DTOManagers
{
    public class NewsDTOManager
    {
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
                                                    where m.News.Id == userId
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
                //news.Pictures = new List<Picture>();
                //news.AudioRecordings = new List<Audio>();
                news.Modifications = new List<NewsModified>();
                news.Modifications.Add(modifiaction);

                session.Save(news);
                session.Save(modifiaction);

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

        public static bool CreateNews(NewsDTO news)
        {
            bool result = false;
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                News newNews = ExpandDTO(news);
                session.Save(newNews);

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

        public static bool UpdateNews(NewsDTO news)
        {
            bool result = false;
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                session.SaveOrUpdate(ExpandDTO(news));

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

        public static bool DeleteNews(int newsId)
        {
            ISession session = null;
            bool result = false;
            try
            {
                session = DataLayer.GetSession();
                News news = session.Load<News>(newsId);
                session.Delete(news);
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
            foreach(PictureDTO picture in newsDTO.Pictures)
            {
                Picture pic = new Picture();
                pic.Description = picture.Description;
                pic.BelongsTo = news;
                pic.Name = picture.Name;
            }
            foreach(AudioDTO audio in newsDTO.AudioRecordings)
            {
                Audio aud = new Audio();
                aud.BelongsTo = news;
                aud.Description = audio.Description;
                aud.Name = audio.Name;
            }
            foreach(CommentDTO comm in newsDTO.Comments)
            {
                Comment comment = new Comment();
                comment.BelongsTo = news;
                comment.Content = comm.Content;
                comment.PostDate = comm.PostDate;
                //we are missing a User
            }
            return news;
        }
    }
}
