using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DataLayerLib.Entities;
using NHibernate;
using DataLayerLib.DTOs;

namespace DataLayerLib
{
    public class DTOManager
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
            catch(Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return news;
        }

        public static List<UserDTO> GetALlUsers()
        {
            List<UserDTO> users = new List<UserDTO>();
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                IEnumerable<User> retData = from u in session.Query<User>()
                                            select u;
                foreach (User u in retData)
                {
                    users.Add(new UserDTO(u));
                }
                session.Close();
            }
            catch(Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return users;
        }

        public static List<CommentDTO> GetAllComments()
        {
            List<CommentDTO> comments = new List<CommentDTO>();
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                IEnumerable<Comment> retData = from c in session.Query<Comment>()
                                            select c;
                foreach (Comment c in retData)
                {
                    comments.Add(new CommentDTO(c));
                }
                session.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return comments;
        }

        public static List<PictureDTO> GetAllPictures()
        {
            List<PictureDTO> pictures = new List<PictureDTO>();
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                IEnumerable<Picture> retData = from p in session.Query<Picture>()
                                               select p;
                foreach (Picture p in retData)
                {
                    PictureDTO picDto = new PictureDTO(p);

                    pictures.Add(picDto);
                }
                session.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return pictures;
        }

        public static List<AudioDTO> GetAllAudio()
        {
            List<AudioDTO> audios = new List<AudioDTO>();
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                IEnumerable<Audio> retData = from a in session.Query<Audio>()
                                               select a;
                foreach (Audio a in retData)
                {
                    AudioDTO audioDto = new AudioDTO(a);
                    audios.Add(audioDto);
                }
                session.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return audios;
        }

        public static List<UserDTO> GetUsersWhoModifiedThisNews(int newsId)
        {
            List<UserDTO> users = new List<UserDTO>();
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
                        users.Add(new UserDTO(modification.User));
                }
                session.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return users;
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

        public static List<CommentDTO> GetCommentsForNews(int newsId)
        {
            List<CommentDTO> comments = new List<CommentDTO>();
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                IEnumerable<Comment> retData = from c in session.Query<Comment>()
                                               where c.BelongsTo.Id == newsId
                                               select c;
                foreach (Comment c in retData)
                {
                    comments.Add(new CommentDTO(c));
                }
                session.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return comments;
        }

        public static List<PictureDTO> GetPicturesForNews(int newsId)
        {
            List<PictureDTO> pictures = new List<PictureDTO>();
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                IEnumerable<Picture> retData = from p in session.Query<Picture>()
                                               where p.BelongsTo.Id == newsId
                                               select p;
                foreach (Picture p in retData)
                {
                    PictureDTO pictureDto = new PictureDTO(p);

                    pictures.Add(pictureDto);
                }
                session.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return pictures;
        }

        public static List<AudioDTO> GetAudiosForNews(int newsId)
        {
            List<AudioDTO> audios = new List<AudioDTO>();
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                IEnumerable<Audio> retData = from a in session.Query<Audio>()
                                             where a.BelongsTo.Id == newsId
                                             select a;
                //IList<Audio> retData = session.QueryOver<Audio>()
                //    .Where(x => x.BelongsTo.Id == newsId).List();
                foreach (Audio a in retData)
                {
                    AudioDTO audioDto = new AudioDTO(a);

                    audios.Add(audioDto);
                }
                session.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return audios;
        }

        public static UserDTO GetUser(int userId)
        {
            ISession session = null;
            UserDTO result = null;
            try
            {
                session = DataLayer.GetSession();
                User user = session.Load<User>(userId);
                result = new UserDTO(user);
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

        public static CommentDTO GetComment(int commentId)
        {
            ISession session = null;
            CommentDTO result = null;
            try
            {
                session = DataLayer.GetSession();
                Comment user = session.Load<Comment>(commentId);
                result = new CommentDTO(user);
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

        public static PictureDTO GetPicture(int pictureId)
        {
            ISession session = null;
            PictureDTO result = null;
            try
            {
                session = DataLayer.GetSession();
                Picture picture = session.Load<Picture>(pictureId);
                result = new PictureDTO(picture);
                MultimediaLoader.IMultimediaLoader loader = new MultimediaLoader.FileSystemLoader();
                result.SetPictureBytes(loader.GetMedia(picture.Id, picture.BelongsTo.Id, picture.Name));
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

        public static AudioDTO GetAudio(int audioId)
        {
            ISession session = null;
            AudioDTO result = null;
            try
            {
                session = DataLayer.GetSession();
                Audio user = session.Load<Audio>(audioId);
                result = new AudioDTO(user);
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

                ITransaction transaction = session.BeginTransaction();
                session.Save(news);
                session.Save(modifiaction);

                session.Flush();
                transaction.Commit();
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
                    MultimediaLoader.IMultimediaLoader loader = new MultimediaLoader.FileSystemLoader();
                    loader.SaveMedia(picture.Id,picture.BelongsTo.Id, picture.Name, pictureData);
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
                    MultimediaLoader.IMultimediaLoader loader = new MultimediaLoader.FileSystemLoader();
                    loader.SaveMedia(audio.Id, audio.BelongsTo.Id, audio.Name, audioData);
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
                //string sql = "select Id_user from user where username = " + newUsername + " ;";
                //ISQLQuery query = session.CreateSQLQuery(sql).AddEntity(typeof(User));

                IEnumerable<User> qresult = from u in session.Query<User>()
                                     where u.Username == newUsername
                                     select u;
                bool usernameInUse = false;
                foreach (User u in qresult)
                    if (u.Username.Equals(newUsername) && u.Id != userId)
                        usernameInUse = true;
                if(usernameInUse)
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
            string description, byte[] pictureData)
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
                if(pictureData!=null)
                {
                    MultimediaLoader.IMultimediaLoader loader = new MultimediaLoader.FileSystemLoader();
                    loader.SaveMedia(picture.Id,picture.BelongsTo.Id, picture.Name, pictureData);
                }
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

                MultimediaLoader.IMultimediaLoader loader = new MultimediaLoader.FileSystemLoader();
                loader.DeleteMedia(picture.Id,picture.BelongsTo.Id,picture.Name);

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
