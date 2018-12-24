using DataLayerLib.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.DTOs
{
    public class AudioDTO
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public int BelongsToNewsId { get; set; }
        public byte[] AudioData { get; set; }

        public AudioDTO() { AudioData = null; }
        public AudioDTO(Audio audio)
        {
            Id = audio.Id;
            Name = audio.Name;
            Description = audio.Description;
            BelongsToNewsId = audio.BelongsTo.Id;
        }

        public override string ToString()
        {
            return String.Format("Id: " + Id +
                "\nName: " + Name +
                "\nDescription: " + Description +
                "\nNewsId: " + BelongsToNewsId);
        }
    }
}
