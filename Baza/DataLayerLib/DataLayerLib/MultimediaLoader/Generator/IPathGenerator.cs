using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.MultimediaLoader.Generator
{
    public interface IPathGenerator
    {
        string GeneratePath(int id, int newsId, string name);
    }
}
