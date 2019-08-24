using BuisnessLogicLayer.DAOInterfaces;
using ObjectModel.DTOs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BuisnessLogicLayer.Services
{
    public class CommentService
    {
        private CommentData commentDataAccess;
        private CommentService()
        {

        }
        public CommentService(CommentData commentData)
        {
            commentDataAccess = commentData;
        }

        public IEnumerable<CommentDTO> GetAllComments()
        {
            return commentDataAccess.GetAllComments();
        }

        public CommentDTO GetCommentById(int id)
        {
            return commentDataAccess.GetComment(id);
        }
        
        public IEnumerable<CommentDTO> GetCommentsFromNews(int newsId)
        {
            return commentDataAccess.GetCommentsForNews(newsId);
        }
        
        public bool UpdateComment(int id, string content)
        {
            bool result = false;
            CommentDTO dataResult = commentDataAccess.UpdateComment(id, content);
            if (dataResult != null)
            {
                MessageQueueManager menager = MessageQueueManager.Instance;
                menager.PublishMessage(dataResult.BelongsToNewsId, dataResult.Id, dataResult, MessageOperation.Update);
                result = true;
            }
            return result;
        }
        
        public bool CreateComment(CommentSimpleDTO value)
        {
            bool result = false;
            CommentDTO dataResult = commentDataAccess.CreateComment(value.CreatedBy, value.BelongsToNewsId, value.Content);
            if(dataResult != null)
            {
                MessageQueueManager menager = MessageQueueManager.Instance;
                menager.PublishMessage(dataResult.BelongsToNewsId, dataResult.Id, dataResult, MessageOperation.Insert);
                result = true;
            }
            return result;
        }
        
        public bool DeleteComment(int id)
        {
            return commentDataAccess.DeleteComment(id);
        }
    }
}
