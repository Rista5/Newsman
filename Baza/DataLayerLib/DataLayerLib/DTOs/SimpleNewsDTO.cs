﻿using DataLayerLib.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.DTOs
{
    public class SimpleNewsDTO
    {
        public int Id { get; set; }
        public string Title { get; set; }
        public string Content { get; set; }
        public DateTime LasModified { get; set; }
        public PictureDTO BackgroundPicture { get; set; }

        public SimpleNewsDTO()
        {
        }

        public SimpleNewsDTO(News news)
        {
            Id = news.Id;
            Title = news.Title;
            Content = news.Content;
            LasModified = news.LastModified;
            if (news.BackgroundPicture != null)
                BackgroundPicture = new PictureDTO(news.BackgroundPicture);
            else BackgroundPicture = null;
        }

        public override string ToString()
        {
            return String.Format("Id: " + Id +
                "\nTitle: " + Title +
                "\nContetn: " + Content +
                "\nLastModified: " + LasModified
                );
        }
    }
}