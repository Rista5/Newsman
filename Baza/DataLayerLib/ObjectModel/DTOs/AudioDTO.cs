using ObjectModel.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ObjectModel.DTOs
{
    public class AudioDTO
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public int BelongsToNewsId { get; set; }
        public string AudioData { get; set; }

        public AudioDTO() { AudioData = null; }
        public AudioDTO(Audio audio)
        {
            Id = audio.Id;
            Name = audio.Name;
            Description = audio.Description;
            BelongsToNewsId = audio.BelongsTo.Id;
        }

        public void SetAudioBytes(byte[] data)
        {
            AudioData = Encoding.Default.GetString(data);
        }

        public byte[] GetAudioBytes()
        {
            return Encoding.Default.GetBytes(AudioData);
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
