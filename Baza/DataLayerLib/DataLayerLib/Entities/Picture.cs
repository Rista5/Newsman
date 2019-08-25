using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.Entities
{
    public class Picture
    {
        public virtual int Id { get; protected set; }
        public virtual string Name { get; set; }
        public virtual string Description { get; set; }
        public virtual News BelongsTo { get; set; }
        //public byte[] Data { get; set; }
        public Picture() { }
        public Picture(int id, string name, string description, News belongsTo)
        {
            Id = id;
            Name = name;
            Description = description;
            BelongsTo = belongsTo;
        }
    }
}
