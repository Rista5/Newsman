using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.Codes
{
    class UsernameTaken : ICode
    {
        public string GetDesciption()
        {
            return "Username already taken";
        }
    }
}
