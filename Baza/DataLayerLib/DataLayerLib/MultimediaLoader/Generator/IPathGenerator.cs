using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.MultimediaLoader.Generator
{
    interface IPathGenerator
    {
        string GeneratePath(int id, int newsId, string name);
    }
}
