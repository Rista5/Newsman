using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DataLayerLib.Entities;
using NHibernate;

namespace DataLayerLib
{
    public class DTOManager
    {
        public static List<News> GetAllNews()
        {
            List<News> news = new List<News>();
            
            try
            {
                ISession session = DataLayer.GetSession();

                IEnumerable<News> retData = from n in session.Query<News>()
                                            select n;
                session.Close();

                foreach (News n in retData)
                    news.Add(n);
            }
            catch(Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            return news;
        }

        public static List<User> GetALlUsers()
        {
            List<User> users = new List<User>();

            try
            {
                ISession session = DataLayer.GetSession();
                IEnumerable<User> retData = from u in session.Query<User>()
                                            select u;
                session.Close();
                foreach (User u in retData)
                {
                    users.Add(u);
                }
            }
            catch(Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            return users;
        }

        public static List<Comment> GetAllComments()
        {
            List<Comment> comments = new List<Comment>();

            try
            {
                ISession session = DataLayer.GetSession();
                IEnumerable<Comment> retData = from c in session.Query<Comment>()
                                            select c;
                session.Close();
                foreach (Comment c in retData)
                {
                    comments.Add(c);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            return comments;
        }

        public static List<Picture> GetAllPictures()
        {
            List<Picture> pictures = new List<Picture>();

            try
            {
                ISession session = DataLayer.GetSession();
                IEnumerable<Picture> retData = from p in session.Query<Picture>()
                                               select p;
                session.Close();
                foreach (Picture p in retData)
                {
                    pictures.Add(p);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            return pictures;
        }

        public static List<Audio> GetAllAudio()
        {
            List<Audio> audios = new List<Audio>();

            try
            {
                ISession session = DataLayer.GetSession();
                IEnumerable<Audio> retData = from a in session.Query<Audio>()
                                               select a;
                session.Close();
                foreach (Audio a in retData)
                {
                    audios.Add(a);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            return audios;
        }

        public static List<User> GetUsersWhoModifiedThisNews(int newsId)
        {
            List<User> users = new List<User>();
            try
            {
                ISession session = DataLayer.GetSession();
                IEnumerable<NewsModified> modifications = from m in session.Query<NewsModified>()
                                                          where m.News.Id == newsId
                                                          select m;
                foreach(NewsModified modification in modifications)
                {
                    if (!users.Exists(x => x.Id == modification.User.Id))
                        users.Add(modification.User);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            return users;
        }

        public static List<News> GetNewsModifiedByUser(int userId)
        {
            List<News> news = new List<News>();
            try
            {
                ISession session = DataLayer.GetSession();
                IEnumerable<NewsModified> retData = from m in session.Query<NewsModified>()
                                                    where m.News.Id == userId
                                                    select m;
                
                foreach (NewsModified m in retData)
                {
                    if (!news.Exists(x => x.Id == m.News.Id))
                        news.Add(m.News);
                }
                session.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            return news;
        }

        public static List<Comment> GetCommentsForNews(int newsId)
        {
            List<Comment> comments = new List<Comment>();

            try
            {
                ISession session = DataLayer.GetSession();
                IEnumerable<Comment> retData = from c in session.Query<Comment>()
                                               where c.BelongsTo.Id == newsId
                                               select c;
                session.Close();
                foreach (Comment c in retData)
                {
                    comments.Add(c);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            return comments;
        }

        public static List<Picture> GetPicturesForNews(int newsId)
        {
            List<Picture> pictures = new List<Picture>();
            try
            {
                ISession session = DataLayer.GetSession();
                IEnumerable<Picture> retData = from p in session.Query<Picture>()
                                               where p.BelongsTo.Id == newsId
                                               select p;
                session.Close();
                foreach (Picture p in retData)
                {
                    pictures.Add(p);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            return pictures;
        }

        public static List<Audio> GetAudiosForNews(int newsId)
        {
            List<Audio> audios = new List<Audio>();
            try
            {
                ISession session = DataLayer.GetSession();
                IEnumerable<Audio> retData = from a in session.Query<Audio>()
                                             where a.BelongsTo.Id ==newsId
                                             select a;
                session.Close();
                foreach (Audio a in retData)
                {
                    audios.Add(a);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            return audios;
        }

        public static bool CreateNews(string title, string content, 
            int userId)
        {
            bool result = false;
            try
            {
                ISession session = DataLayer.GetSession();
                News news = new News();
                User user = session.Get<User>(userId);
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
            catch(Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            return result;
        }

        public static bool CreateUser(string username, string password)
        {
            bool result = false;
            try
            {
                User user = new User();
                user.Username = username;
                user.Password = password;
                //user.ModifiedNews = new List<NewsModified>();
                //user.CommentsPosted = new List<Comment>();
                ISession session = DataLayer.GetSession();
                session.Save(user);
                session.Flush();
                session.Close();

                result = true;
            }
            catch(Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            return result;
        }

        public static bool CreateComment(int userId, int newsId, string content)
        {
            bool result = false;
            try
            {
                Comment comment = new Comment();
                comment.Content = content;
                comment.PostDate = DateTime.Today;

                ISession session = DataLayer.GetSession();
                User creator = session.Get<User>(userId);
                News belongsTo = session.Get<News>(newsId);
                comment.CreatedBy = creator;
                comment.BelongsTo = belongsTo;
                session.Save(comment);
                session.Flush();
                session.Close();

                result = true;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            return result;
        }

        public static bool CreatePicture(int newsId, string name,
            string description, byte[] pictureData = null)
        {
            bool result = false;
            try
            {
                Picture picture = new Picture();
                picture.Name = name;
                picture.Description = description;

                ISession session = DataLayer.GetSession();
                News belongsTo = session.Get<News>(newsId);
                picture.BelongsTo = belongsTo;
                session.Save(picture);
                session.Flush();
                session.Close();

                if(pictureData != null)
                {
                    MultimediaLoader.IPictureLoader loader = new MultimediaLoader.FileSystemPictureLoader();
                    loader.SavePicture(picture.Id, picture.Name, pictureData);
                }

                result = true;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            return result;
        }
    }
}
