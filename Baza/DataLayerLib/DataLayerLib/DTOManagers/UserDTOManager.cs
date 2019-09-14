using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ObjectModel.DTOs;
using NHibernate;
using ObjectModel.Entities;
using DataLayerLib.Codes;
using BuisnessLogicLayer.DAOInterfaces;

namespace DataLayerLib.DTOManagers
{
    public class UserDTOManager : UserData
    {
        public List<UserDTO> GetAllUsers()
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

        public List<UserDTO> GetUsersWhoModifiedThisNews(int newsId)
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

        public User GetFullUser(int userId)
        {
            ISession session = null;
            User result = null;
            try
            {
                session = DataLayer.GetSession();
                result = session.Load<User>(userId);
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

        public UserDTO GetUser(int userId)
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

        public UserWithPassword CreateUser(string username, string password)
        {
            UserWithPassword result = null;
            ISession session = null;
            try
            {
                User user = new User();
                user.Username = username;
                user.Password = password;

                session = DataLayer.GetSession();

                bool usernameInUse = ValidateUsername(session, user.Id, user.Username);
                if (usernameInUse)
                {
                    //return result;//new UsernameTaken();
                    result = null;
                }
                else
                {
                    session.Save(user);
                    session.Flush();
                    result = new UserWithPassword(user);
                    session.Close();
                    //result = new UserCreated();
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return result;
        }

        public UserWithPassword GetUser(string username, string password)
        {
            UserWithPassword result = null;
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                User user = session.Query<User>()
                    .Where(u => u.Username.Equals(username))
                    .Where(u => u.Password.Equals(password)).FirstOrDefault();
                result = new UserWithPassword(user);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return result;
        }

        public UserDTO UpdateUser(int userId, string oldPassword,
            string newUsername, string newPassword)
        {
            UserDTO result = null;
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                User user = session.Load<User>(userId);

                bool usernameInUse = ValidateUsername(session, user.Id, user.Username);
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

                //MessageQueueManager menager = MessageQueueManager.Instance;
                //menager.PublishMessage(1, user.Id, new UserDTO(user), MessageOperation.Update);

                result = new UserDTO(user);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }

            return result;
        }

        public bool UpdateUserName(int userId, string password, string newUsername)
        {
            bool result = false;
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                User user = session.Load<User>(userId);
                if(password != user.Password)
                {
                    throw new Exception("Wrong password");
                }
                user.Username = newUsername;
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

        public bool UpdateUserPassword(int userId, UserPasswordsDTO passwords)
        {
            bool result = false;
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                User user = session.Load<User>(userId);
                if (user.Password != passwords.OldPassword)
                    throw new Exception("WrongPassword");
                user.Password = passwords.NewPassword;
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

        public bool DeleteUser(int userId)
        {
            ISession session = null;
            bool result = false;
            try
            {
                session = DataLayer.GetSession();
                User user = session.Load<User>(userId);

                //UserDTO userDTO = new UserDTO(user);
                session.Delete(user);
                
                //MessageQueueManager manager = MessageQueueManager.Instance;
                //manager.PublishMessage(userDTO.BelongsToNewsId, commentDTO.Id, commentDTO, MessageOperation.Delete);

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

        private bool ValidateUsername(ISession session,int id, string username)
        {
            IEnumerable<User> qresult = from u in session.Query<User>()
                                        where u.Username == username
                                        select u;
            bool usernameInUse = false;
            foreach (User u in qresult)
                if (u.Username.Equals(username) && u.Id != id)
                    usernameInUse = true;
            return usernameInUse;
        }
    }
}
