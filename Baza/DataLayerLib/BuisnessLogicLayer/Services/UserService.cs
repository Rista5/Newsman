using BuisnessLogicLayer.DAOInterfaces;
using ObjectModel.DTOs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BuisnessLogicLayer.Services
{
    public class UserService
    {
        private UserData userDataAccess;
        private UserService() { }
        public UserService(UserData userData)
        {
            this.userDataAccess = userData;
        }

        public IEnumerable<UserDTO> GetAllUsers()
        {
            return userDataAccess.GetAllUsers();
        }

        public UserDTO GetUserById(int userId)
        {
            return userDataAccess.GetUser(userId);
        }

        public bool UpdateUserUsername(int id, string password, string newName)
        {
            return userDataAccess.UpdateUserName(id, password, newName);
        }

        public bool UpdateUserPassword(int id, UserPasswordsDTO passwords)
        {
            return userDataAccess.UpdateUserPassword(id, passwords);
        }
        public bool DeleteUser(int id)
        {
            return userDataAccess.DeleteUser(id);
        }

        public bool CreateUser(string username, string password)
        {
            return userDataAccess.CreateUser(username, password);
        }
    }
}
