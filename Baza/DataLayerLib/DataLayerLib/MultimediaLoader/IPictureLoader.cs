using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.MultimediaLoader
{
    interface IMultimediaLoader
    {
        byte[] GetMedia(int id, string name);
        bool SaveMedia(int id, string name, byte[] data);
        bool DeleteMedia(int id, string name);
    }
}
