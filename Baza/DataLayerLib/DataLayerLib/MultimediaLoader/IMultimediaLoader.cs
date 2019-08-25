using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.MultimediaLoader
{
    interface IMultimediaLoader
    {
        byte[] GetMedia(int id,int newsId);
        bool SaveMedia(int id,int newsId, byte[] data);
        bool DeleteMedia(int id,int newsId);
    }
}
