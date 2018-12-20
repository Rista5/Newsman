using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.MultimediaLoader
{
    interface IPictureLoader
    {
        byte[] GetPicture(int id, string name);
        bool SavePicture(int id, string name, byte[] data);
    }
}
