using DataLayerLib.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.DTOs
{
    public class CommentSimpleDTO
    {
        public string Content { get; set; }
        public int BelongsToNewsId { get; set; }
        public int CreatedBy { get; set; }

        public CommentSimpleDTO() { }
        public CommentSimpleDTO(Comment comment)
        {
            Content = comment.Content;
            BelongsToNewsId = comment.BelongsTo.Id;
            CreatedBy = comment.CreatedBy.Id;
        }

        public override string ToString()
        {
            return String.Format(
                "\nContent: " + Content +
                "\nNewsId: " + BelongsToNewsId +
                "\nCreator: " + CreatedBy);
        }
    }
}
