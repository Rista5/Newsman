using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ObjectModel.DTOs
{
    public class UserWithPassword
    {
        public int Id { get; set; }
        public string Username { get; set; }
        public string Password { get; set; }

        public UserWithPassword() { }

        public UserWithPassword(Entities.User user)
        {
            Id = user.Id;
            Username = user.Username;
            Password = user.Password;
        }
    }
}
