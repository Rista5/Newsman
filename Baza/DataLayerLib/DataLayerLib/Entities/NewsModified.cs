using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.Entities
{
    class NewsModified
    {
        public virtual int Id { get; protected set; }
        public virtual User User { get; protected set; }
        public virtual News News { get; protected set; }
        public virtual DateTime ModificationDate { get; set; }

        public NewsModified() { }
    }
}
