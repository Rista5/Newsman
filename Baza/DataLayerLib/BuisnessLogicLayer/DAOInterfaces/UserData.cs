using ObjectModel.DTOs;
using ObjectModel.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BuisnessLogicLayer.DAOInterfaces
{
    public interface UserData
    {
        List<UserDTO> GetAllUsers();

        List<UserDTO> GetUsersWhoModifiedThisNews(int newsId);

        User GetFullUser(int userId);

        UserDTO GetUser(int userId);

        bool CreateUser(string username, string password);

        UserDTO UpdateUser(int userId, string oldPassword,
            string newUsername, string newPassword);

        bool UpdateUserName(int userId, string password, string newUsername);

        bool UpdateUserPassword(int userId, UserPasswordsDTO passwords);

        bool DeleteUser(int userId);
    }
}
