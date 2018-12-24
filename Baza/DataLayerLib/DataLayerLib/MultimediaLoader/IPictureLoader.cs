using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.MultimediaLoader
{
    interface IMultimediaLoader
    {
        byte[] GetMedia(int id,int newsId, string name);
        bool SaveMedia(int id,int newsId, string name, byte[] data);
        bool DeleteMedia(int id,int newsId, string name);
    }
}
