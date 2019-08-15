using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DataLayerLib.DTOs;
using DataLayerLib.Entities;
using NHibernate;

namespace DataLayerLib.DTOManagerInterfaces
{
    public interface IUserDTOManager
    {
        List<UserDTO> GetAll();
        List<UserDTO> GetUsersWhoModifiedThisNews(int newsId);
        User GetFullUser(int id);
        UserDTO GetUser(int id);
        bool CreateUser(string username, string password);
        bool UpdateUser(int userId, string oldPassword, string newUsername, string newPassword);
        bool UpdateUserName(int userId, string password, string newUsername);
        bool UpdateUserPassword(int userId, UserPasswordsDTO passwords);
        bool DeleteUser(int userId);
        bool ValidateUsername(ISession session, int id, string username);
    }
}
