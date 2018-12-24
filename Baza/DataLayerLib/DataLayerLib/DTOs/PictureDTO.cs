using DataLayerLib.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.DTOs
{
    public class PictureDTO
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public int BelongsToNewsId { get; set; }
        public byte[] PictureData { get; set; }
        public PictureDTO() { }
        public PictureDTO(Picture picture)
        {
            Id = picture.Id;
            Name = picture.Name;
            Description = picture.Description;
            BelongsToNewsId = picture.BelongsTo.Id;
        }

        public override string ToString()
        {
            return String.Format("Id: " + Id +
                "\nName: " + Name +
                "\nDescription: " + Description+
                "\nNewsId: "+ BelongsToNewsId);
        }
    }
}
