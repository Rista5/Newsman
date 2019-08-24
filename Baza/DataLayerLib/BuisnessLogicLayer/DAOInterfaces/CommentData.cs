using ObjectModel.DTOs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BuisnessLogicLayer.DAOInterfaces
{
    public interface CommentData
    {
        List<CommentDTO> GetAllComments();

        List<CommentDTO> GetCommentsForNews(int newsId);

        CommentDTO GetComment(int commentId);

        CommentDTO CreateComment(int userId, int newsId, string content);

        CommentDTO CreateComment(CommentDTO commentDTO);

        CommentDTO UpdateComment(int commntId, string content);

        CommentDTO UpdateComment(CommentDTO commentDTO);

        bool DeleteComment(int commentId);
    }
}
