using DataLayerLib.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.DTOs
{
    public class CommentDTO
    {
        public int Id { get; set; }
        public string Content { get; set; }
        public int BelongsToNewsId { get; set; }
        public UserDTO CreatedBy { get; set; }
        public DateTime PostDate { get; set; }

        public CommentDTO() { }
        public CommentDTO(Comment comment)
        {
            Id = comment.Id;
            Content = comment.Content;
            BelongsToNewsId = comment.BelongsTo.Id;
            CreatedBy = new UserDTO(comment.CreatedBy);
            PostDate = comment.PostDate;
        }

        public override string ToString()
        {
            return String.Format("Id: " + Id +
                "\nContent: " + Content +
                "\nNewsId: " + BelongsToNewsId +
                "\nCreator: " + CreatedBy +
                "\nPostDate: " + PostDate);
        }
    }
}
