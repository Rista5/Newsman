using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.Entities
{
    class News
    {
        public virtual int Id { get; protected set; }
        public virtual String Title { get; set; }
        public virtual String Content { get; set; }

        public virtual DateTime LastModified { get; set; }

        public News() { }
    }
}
