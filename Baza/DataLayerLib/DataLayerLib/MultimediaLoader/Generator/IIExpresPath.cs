using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.MultimediaLoader.Generator
{
    class IIExpresPath : IPathGenerator
    {
        public string GeneratePath(int id, int newsId, string name)
        {
            string path = @"C:\Data\" + +newsId + @"\";
            return path;
        }
    }
}
