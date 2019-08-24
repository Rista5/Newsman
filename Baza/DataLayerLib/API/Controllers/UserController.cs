using BuisnessLogicLayer.Services;
using DataLayerLib.DTOManagers;
using ObjectModel.DTOs;
using ObjectModel.Entities;
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
        private UserService service;
        public IEnumerable<UserDTO> GetAllUsers()
        {
            service = new UserService(new UserDTOManager());
            return service.GetAllUsers();
        }

        public UserDTO GetUserbyID(int id)
        {

            service = new UserService(new UserDTOManager());
            return service.GetUserById(id);
        }

        public bool Put(int id, [FromBody] string password, string newName)
        { 
            service = new UserService(new UserDTOManager());
            return service.UpdateUserUsername(id, password, newName);
        }

        public bool Put(int id, [FromBody] UserPasswordsDTO passwords)
        {
            service = new UserService(new UserDTOManager());
            return service.UpdateUserPassword(id, passwords);
        }
        public bool DeleteUser(int id)
        {
            service = new UserService(new UserDTOManager());
            return service.DeleteUser(id);
        }

        public bool Put([FromBody] UserWithPassword user)
        {
            service = new UserService(new UserDTOManager());
            return service.CreateUser(user.Username, user.Password);
        }
    }

    // POCO class for Put method
    public class UserWithPassword
    {
        public string Username { get; set; }
        public string Password { get; set; }
    }
}
