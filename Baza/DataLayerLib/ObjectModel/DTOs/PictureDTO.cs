﻿using ObjectModel.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ObjectModel.DTOs
{
    public class PictureDTO
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public int BelongsToNewsId { get; set; }
        public string PictureData { get; set; }

        public PictureDTO() { }
        public PictureDTO(Picture picture)
        {
            Id = picture.Id;
            Name = picture.Name;
            Description = picture.Description;
            if(picture.BelongsTo != null)
                BelongsToNewsId = picture.BelongsTo.Id;
        }

        public void SetPictureBytes(byte[] data)
        {
            if(data != null)
                PictureData = Encoding.Default.GetString(data);
        }

        public byte[] GetPictureBytes()
        {
            return Encoding.Default.GetBytes(PictureData);
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
