using ObjectModel.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ObjectModel.DTOs
{
    public class SimpleNewsDTO
    {
        public int Id { get; set; }
        public string Title { get; set; }
        public string Content { get; set; }
        public DateTime LastModified { get; set; }
        public UserDTO LastModifiedUser { get; set; }
        public PictureDTO BackgroundPicture { get; set; }

        public SimpleNewsDTO()
        {
        }

        public SimpleNewsDTO(News news)
        {
            Id = news.Id;
            Title = news.Title;
            Content = news.Content;
            LastModified = news.LastModified;
            var newestdate = news.Modifications.Max(x => x.ModificationDate);
            User modifier = news.Modifications.First(x => x.ModificationDate == newestdate).User;
            LastModifiedUser = new UserDTO(modifier);
            if (news.BackgroundPicture != null)
                BackgroundPicture = new PictureDTO(news.BackgroundPicture);
            else BackgroundPicture = null;
        }

        public override string ToString()
        {
            return String.Format("Id: " + Id +
                "\nTitle: " + Title +
                "\nContetn: " + Content +
                "\nLastModified: " + LastModified
                );
        }
    }
}
