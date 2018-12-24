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
    public class UserDTOManager
    {
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
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return users;
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
                foreach (NewsModified modification in modifications)
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
            catch (Exception ex)
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

                session = DataLayer.GetSession();

                IEnumerable<User> qresult = from u in session.Query<User>()
                                            where u.Username == user.Username
                                            select u;
                bool usernameInUse = false;
                foreach (User u in qresult)
                    if (u.Username.Equals(user.Username) && u.Id != user.Id)
                        usernameInUse = true;
                if (usernameInUse)
                {
                    Exception exception = new Exception("Username already used by another user");
                    throw exception;
                }

                session.Save(user);
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

        public static bool UpdateUser(int userId, string oldPassword,
            string newUsername, string newPassword)
        {
            bool result = false;
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                User user = session.Load<User>(userId);
                IEnumerable<User> qresult = from u in session.Query<User>()
                                            where u.Username == newUsername
                                            select u;
                bool usernameInUse = false;
                foreach (User u in qresult)
                    if (u.Username.Equals(newUsername) && u.Id != userId)
                        usernameInUse = true;
                if (usernameInUse)
                {
                    Exception exception = new Exception("Username already used by another user");
                    throw exception;
                }
                else
                {
                    user.Username = newUsername;
                }
                if (user.Password.Equals(oldPassword))
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
            catch (Exception ex)
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


    }
}
