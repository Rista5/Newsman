using ObjectModel.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ObjectModel.DTOs
{
    public class UserDTO
    {
        public int Id { get; set; }
        public string Username { get; set; }

        public UserDTO() { }
        public UserDTO(User user)
        {
            Id = user.Id;
            Username = user.Username;
        }

        public override string ToString()
        {
            return String.Format("Id: " + Id +
                "\nUsername: " + Username
                );
        }
    }
}
