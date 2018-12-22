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
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();

                IEnumerable<News> retData = from n in session.Query<News>()
                                            select n;
                session.Close();

                foreach (News n in retData)
                    news.Add(n);
            }
            catch(Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return news;
        }

        public static List<User> GetALlUsers()
        {
            List<User> users = new List<User>();
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
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
                if (session != null)
                    session.Close();
            }
            return users;
        }

        public static List<Comment> GetAllComments()
        {
            List<Comment> comments = new List<Comment>();
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
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
                if (session != null)
                    session.Close();
            }
            return comments;
        }

        public static List<Picture> GetAllPictures()
        {
            List<Picture> pictures = new List<Picture>();
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
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
                if (session != null)
                    session.Close();
            }
            return pictures;
        }

        public static List<Audio> GetAllAudio()
        {
            List<Audio> audios = new List<Audio>();
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
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
                if (session != null)
                    session.Close();
            }
            return audios;
        }

        public static List<User> GetUsersWhoModifiedThisNews(int newsId)
        {
            List<User> users = new List<User>();
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
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
                if (session != null)
                    session.Close();
            }
            return users;
        }

        public static List<News> GetNewsModifiedByUser(int userId)
        {
            List<News> news = new List<News>();
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
                        news.Add(m.News);
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

        public static List<Comment> GetCommentsForNews(int newsId)
        {
            List<Comment> comments = new List<Comment>();
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
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
                if (session != null)
                    session.Close();
            }
            return comments;
        }

        public static List<Picture> GetPicturesForNews(int newsId)
        {
            List<Picture> pictures = new List<Picture>();
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
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
                if (session != null)
                    session.Close();
            }
            return pictures;
        }

        public static List<Audio> GetAudiosForNews(int newsId)
        {
            List<Audio> audios = new List<Audio>();
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
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
                if (session != null)
                    session.Close();
            }
            return audios;
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
            catch(Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return result;
        }

        public static bool CreateUser(string username, string password)
        {
            bool result = false;
            ISession session = null;
            try
            {
                User user = new User();
                user.Username = username;
                user.Password = password;
                //user.ModifiedNews = new List<NewsModified>();
                //user.CommentsPosted = new List<Comment>();
                session = DataLayer.GetSession();
                session.Save(user);
                session.Flush();
                session.Close();

                result = true;
            }
            catch(Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return result;
        }

        public static bool CreateComment(int userId, int newsId, string content)
        {
            bool result = false;
            ISession session = null;
            try
            {
                Comment comment = new Comment();
                comment.Content = content;
                comment.PostDate = DateTime.Today;

                session = DataLayer.GetSession();
                User creator = session.Load<User>(userId);
                News belongsTo = session.Load<News>(newsId);
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
                if (session != null)
                    session.Close();
            }
            return result;
        }

        public static bool CreatePicture(int newsId, string name,
            string description, byte[] pictureData = null)
        {
            bool result = false;
            ISession session = null;
            try
            {
                Picture picture = new Picture();
                picture.Name = name;
                picture.Description = description;

                session = DataLayer.GetSession();
                News belongsTo = session.Load<News>(newsId);
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
                if (session != null)
                    session.Close();
            }
            return result;
        }

        public static bool CreateAudio(int newsId, string name,
            string description, byte[] audioData = null)
        {
            bool result = false;
            ISession session = null;
            try
            {
                Audio audio = new Audio();
                audio.Name = name;
                audio.Description = description;

                session = DataLayer.GetSession();
                News belongsTo = session.Load<News>(newsId);
                audio.BelongsTo = belongsTo;
                session.Save(audio);
                session.Flush();
                session.Close();

                //mora da se napravi poseban za audio
                if (audioData != null)
                {
                    MultimediaLoader.IPictureLoader loader = new MultimediaLoader.FileSystemPictureLoader();
                    loader.SavePicture(audio.Id, audio.Name, audioData);
                }

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

        //postoji li nacin da se naprave vrste modifikacija i da se u runtime odredjuje tip, pa da se i to nekako loguje
        //slicno kao command pattern
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
            catch(Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return result;
        }

        public static bool UpdateUser(int userId, string oldPassword,
            string newUsername,string newPassword)
        {
            bool result = false;
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                User user = session.Load<User>(userId);
                string sql = "select Id_user from user where username = " + newUsername + " ;";
                ISQLQuery query = session.CreateSQLQuery(sql).AddEntity(typeof(User));
                List<User> qresult = (List<User>)query.List();
                if(qresult.Count>0)
                {
                    Exception exception = new Exception("Username already used by another user");
                    throw exception;
                }
                else
                {
                    user.Username = newUsername;
                }
                if(user.Password.Equals(oldPassword))
                {
                    user.Password = newPassword;
                }
                else
                {
                    Exception exception = new Exception("Wrong old password");
                    throw exception;
                }

                session.SaveOrUpdate(user);
                session.Flush();
                session.Close();

            }
            catch(Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }

            return result;
        }

        public static bool UpdateComment(int commntId, string content)
        {
            ISession session = null;
            bool result = false;
            try
            {
                session = DataLayer.GetSession();
                Comment comment = session.Load<Comment>(commntId);
                comment.Content = content;
                session.SaveOrUpdate(comment);
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

        public static bool UpdatePicture(int pictureId, string name,
            string description)
        {
            ISession session = null;
            bool result = false;
            try
            {
                session = DataLayer.GetSession();
                Picture picture = session.Load<Picture>(pictureId);
                picture.Name = name;
                picture.Description = description;
                session.SaveOrUpdate(picture);
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

        public static bool UpdateAudio(int audioId, string name,
            string description)
        {
            ISession session = null;
            bool result = false;
            try
            {
                session = DataLayer.GetSession();
                Audio audio = session.Load<Audio>(audioId);
                audio.Name = name;
                audio.Description = description;
                session.SaveOrUpdate(audio);
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
            catch(Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }

            return result;
        }

        public static bool DeleteUser(int userId)
        {
            ISession session = null;
            bool result = false;
            try
            {
                session = DataLayer.GetSession();
                User user = session.Load<User>(userId);
                session.Delete(user);
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

        public static bool DeleteComment(int commentId)
        {
            ISession session = null;
            bool result = false;
            try
            {
                session = DataLayer.GetSession();
                Comment comment = session.Load<Comment>(commentId);
                session.Delete(comment);
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

        public static bool DeletePicture(int pictureId)
        {
            ISession session = null;
            bool result = false;
            try
            {
                session = DataLayer.GetSession();
                Picture picture = session.Load<Picture>(pictureId);
                session.Delete(picture);
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

        public static bool DeleteAudio(int audioId)
        {
            ISession session = null;
            bool result = false;
            try
            {
                session = DataLayer.GetSession();
                Audio audio = session.Load<Audio>(audioId);
                session.Delete(audio);
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

    }
}
