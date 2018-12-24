using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.MultimediaLoader.Generator
{
    class ClassicPath : IPathGenerator
    {
        public string GeneratePath(int id, int newsId, string name)
        {
            string path;
            path = @".\..\Data\"+ newsId + @"\" + name;
            return path;
        }
    }
}
