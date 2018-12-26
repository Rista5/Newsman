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
            return UserDTOManager.GetALlUsers();
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
            //return UserDTOManager.UpdateUser(id, passwords.OldPassword,username)
        }
        public bool DeleteUser(int id)
        {
            return UserDTOManager.DeleteUser(id);
        }

        public bool Post([FromUri] string username, [FromBody]string password)
        {
            return UserDTOManager.CreateUser(username, password);//.GetDesciption();
        }
    }
}
