using DataLayerLib.DTOManagers;
using DataLayerLib.DTOs;
using DataLayerLib.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace API.Controllers
{
    public class UserController : ApiController
    {
        private UserDTOManager Manager = new UserDTOManager();

        public IEnumerable<UserDTO> GetAllUsers()
        {
            return UserDTOManager.GetAllUsers();
        }

        public UserDTO GetUserbyID(int id)
        {
            return UserDTOManager.GetUser(id);
        }

        public bool Put(int id, [FromBody] string password, string newName)
        {
            return UserDTOManager.UpdateUserName(id, password, newName);
        }

        public bool Put(int id, [FromBody] UserPasswordsDTO passwords)
        {
            return UserDTOManager.UpdateUserPassword(id, passwords);
        }
        public bool DeleteUser(int id)
        {
            return UserDTOManager.DeleteUser(id);
        }

        public bool Put([FromBody] UserWithPassword user)
        {
            return UserDTOManager.CreateUser(user.Username, user.Password);
        }
    }

    // POCO class for Put method
    public class UserWithPassword
    {
        public string Username { get; set; }
        public string Password { get; set; }
    }
}
