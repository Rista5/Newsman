using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.Codes
{
    class UserCreated : ICode
    {
        public string GetDesciption()
        {
            return "User successfully created!";
        }
    }
}
