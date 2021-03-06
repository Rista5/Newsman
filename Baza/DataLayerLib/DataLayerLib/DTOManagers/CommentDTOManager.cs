﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ObjectModel.DTOs;
using NHibernate;
using ObjectModel.Entities;
using BuisnessLogicLayer.DAOInterfaces;

namespace DataLayerLib.DTOManagers
{
    public class CommentDTOManager : CommentData
    {
        public List<CommentDTO> GetAllComments()
        {
            List<CommentDTO> comments = new List<CommentDTO>();
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                IEnumerable<Comment> retData = from c in session.Query<Comment>()
                                               select c;
                foreach (Comment c in retData)
                {
                    comments.Add(new CommentDTO(c));
                }
                session.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return comments;
        }

        public List<CommentDTO> GetCommentsForNews(int newsId)
        {
            List<CommentDTO> comments = new List<CommentDTO>();
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                IEnumerable<Comment> retData = from c in session.Query<Comment>()
                                               where c.BelongsTo.Id == newsId
                                               select c;
                foreach (Comment c in retData)
                {
                    comments.Add(new CommentDTO(c));
                }
                session.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return comments;
        }

        public CommentDTO GetComment(int commentId)
        {
            ISession session = null;
            CommentDTO result = null;
            try
            {
                session = DataLayer.GetSession();
                Comment user = session.Load<Comment>(commentId);
                result = new CommentDTO(user);
                session.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return result;
        }

        public CommentDTO CreateComment(CommentDTO commentDTO)
        {
            CommentDTO result = null;
            ISession session = null;
            try
            {
                Comment comment = new Comment();
                comment.Content = commentDTO.Content;
                comment.PostDate = DateTime.Now;

                session = DataLayer.GetSession();
                User creator = session.QueryOver<User>()
                    .Where(x => x.Id == commentDTO.CreatedBy.Id).SingleOrDefault();
                News belongsTo = session.QueryOver<News>()
                    .Where(x => x.Id == commentDTO.BelongsToNewsId).SingleOrDefault();
                comment.CreatedBy = creator;
                comment.BelongsTo = belongsTo;

                session.Save(comment);
                session.Flush();

                result = new CommentDTO(comment);

                session.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return result;
        }

        public CommentDTO UpdateComment(int commntId, string content)
        {
            ISession session = null;
            CommentDTO result = null;
            try
            {
                session = DataLayer.GetSession();
                Comment comment = session.Load<Comment>(commntId);
                comment.Content = content;
                session.SaveOrUpdate(comment);
                session.Flush();

                //MessageQueueManager menager = MessageQueueManager.Instance;
                //menager.PublishMessage(comment.BelongsTo.Id, comment.Id, new CommentDTO(comment), MessageOperation.Update);

                result = new CommentDTO(comment);

                session.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }

            return result;
        }

        public CommentDTO UpdateComment(CommentDTO commentDTO)
        {
            ISession session = null;
            CommentDTO result = null;
            try
            {
                session = DataLayer.GetSession();

                Comment comment = session.Load<Comment>(commentDTO.Id);
                comment.Content = commentDTO.Content;

                session.SaveOrUpdate(comment);
                session.Flush();

                //MessageQueueManager menager = MessageQueueManager.Instance;
                //menager.PublishMessage(comment.BelongsTo.Id, comment.Id, new CommentDTO(comment), MessageOperation.Update);

                result = new CommentDTO(comment);

                session.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }

            return result;
        }

        public bool DeleteComment(int commentId)
        {
            ISession session = null;
            bool result = false;
            try
            {
                session = DataLayer.GetSession();
                Comment comment = session.Load<Comment>(commentId);

                CommentDTO commentDTO = new CommentDTO(comment);
                session.Delete(comment);

                MessageQueueManager manager = MessageQueueManager.Instance;
                manager.PublishMessage(commentDTO.BelongsToNewsId, commentDTO.Id, commentDTO, MessageOperation.Delete);

                session.Flush();
                session.Close();
                result = true;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }

            return result;
        }

    }
}
