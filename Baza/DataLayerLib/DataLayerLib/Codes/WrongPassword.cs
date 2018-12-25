using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.Codes
{
    class WrongPassword : ICode
    {
        public string GetDesciption()
        {
            return "Wrong Password";
        }
    }
}
