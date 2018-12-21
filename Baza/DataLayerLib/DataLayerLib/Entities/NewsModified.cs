using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.Entities
{
    public class NewsModified
    {
        public virtual int Id { get; protected set; }
        public virtual User User { get;  set; }
        public virtual News News { get;  set; }
        public virtual DateTime ModificationDate { get; set; }

        public NewsModified() { }
    }
}
