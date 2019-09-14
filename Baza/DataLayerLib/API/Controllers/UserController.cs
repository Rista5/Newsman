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
    public class UserController : BaseController
    {
        private UserService service;
        public IEnumerable<UserDTO> GetAllUsers()
        {
            service = Service.UserService;
            return service.GetAllUsers();
        }

        public UserDTO GetUserbyID(int id)
        {
            service = Service.UserService;
            return service.GetUserById(id);
        }

        public bool Put(int id, [FromBody] string password, string newName)
        {
            service = Service.UserService;
            return service.UpdateUserUsername(id, password, newName);
        }

        public bool Put(int id, [FromBody] UserPasswordsDTO passwords)
        {
            service = Service.UserService;
            return service.UpdateUserPassword(id, passwords);
        }
        public bool DeleteUser(int id)
        {
            service = Service.UserService;
            return service.DeleteUser(id);
        }

        public UserWithPassword Put([FromBody] ObjectModel.DTOs.UserWithPassword user)
        {
            service = Service.UserService;
            return service.CreateUser(user.Username, user.Password);
        }

        public UserWithPassword Post(UserWithPassword user)
        {
            service = Service.UserService;
            return service.GetUser(user.Username, user.Password);
        }
    }



}
