using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ObjectModel.Entities
{
    public class Picture
    {
        public virtual int Id { get; protected set; }
        public virtual string Name { get; set; }
        public virtual string Description { get; set; }
        public virtual News BelongsTo { get; set; }
        //public byte[] Data { get; set; }
        public Picture() { }
    }
}
