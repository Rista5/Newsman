using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.Codes
{
    class UserNotCreated : ICode
    {
        public string GetDesciption()
        {
            return "User not created. Please try again.";
        }
    }
}
