﻿using ObjectModel.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ObjectModel.DTOs
{
    public class NewsDTO
    {
        public int Id { get; set; }
        public string Title { get; set; }
        public string Content { get; set; }
        public DateTime LastModified { get; set; }
        public UserDTO LastModifiedUser { get; set; }
        public PictureDTO BackgroundPicture { get; set; }
        public IList<CommentDTO> Comments { get; set; }
        public IList<PictureDTO> Pictures { get; set; }
        public IList<AudioDTO> AudioRecordings { get; set; }

        public NewsDTO()
        {
            Comments = new List<CommentDTO>();
            Pictures = new List<PictureDTO>();
            AudioRecordings = new List<AudioDTO>();
        }
        public NewsDTO(News news)
        {
            Id = news.Id;
            Title = news.Title;
            Content = news.Content;
            LastModified = news.LastModified;
            var newestdate = news.Modifications.Max(x => x.ModificationDate);
            User modifier = news.Modifications.First(x => x.ModificationDate == newestdate).User;
            LastModifiedUser = new UserDTO(modifier);
            //LastModifiedUser = new UserDTO() {
            //    Id = 0,
            //    Username = "John Doe"
            //};

            if (news.BackgroundPicture != null)
                BackgroundPicture = new PictureDTO(news.BackgroundPicture);
            else BackgroundPicture = null;

            Pictures = new List<PictureDTO>(news.Pictures.Count);
            foreach (Picture picture in news.Pictures)
                Pictures.Add(new PictureDTO(picture));
            AudioRecordings = new List<AudioDTO>(news.AudioRecordings.Count);
            foreach (Audio audio in news.AudioRecordings)
                AudioRecordings.Add(new AudioDTO(audio));
            Comments = new List<CommentDTO>();
            foreach (Comment comment in news.Comments)
                Comments.Add(new CommentDTO(comment));
        }

        public NewsDTO(SimpleNewsDTO dto)
        {
            Id = dto.Id;
            Title = dto.Title;
            Content = dto.Content;
            LastModified = dto.LastModified;
            LastModifiedUser = dto.LastModifiedUser;

            if (dto.BackgroundPicture != null)
                BackgroundPicture = dto.BackgroundPicture;
            else BackgroundPicture = null;

            Pictures = new List<PictureDTO>();
            AudioRecordings = new List<AudioDTO>();
            Comments = new List<CommentDTO>();
        }

        public override string ToString()
        {
            string pictures = "";
            foreach (PictureDTO p in Pictures)
                pictures += p.ToString();
            string audios = "";
            foreach (AudioDTO audioDTO in AudioRecordings)
                audios += audioDTO.ToString();
            string comments = "";
            foreach (CommentDTO comment in Comments)
                comments += comment.ToString();
            return String.Format("Id: " + Id +
                "\nTitle: " + Title +
                "\nContetn: " + Content +
                "\nLastModified: " + LastModified +
                "Comments: \n" + comments +
                "\nPictures: \n" + pictures +
                "\nAudios: \n" + audios
                );
        }
    }
}
