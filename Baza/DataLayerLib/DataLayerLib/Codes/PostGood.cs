using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.Codes
{
    class PostGood : ICode
    {
        public string GetDesciption()
        {
            return "Post executed without problem.";
        }
    }
}
