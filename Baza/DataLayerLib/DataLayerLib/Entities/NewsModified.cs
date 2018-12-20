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
        public virtual int UserId { get; protected set; }
        public virtual int NewsId { get; protected set; }
        public virtual DateTime ModificationDate { get; set; }

        public NewsModified() { }
    }
}
