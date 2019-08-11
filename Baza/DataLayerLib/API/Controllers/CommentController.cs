using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using DataLayerLib.DTOManagers;
using DataLayerLib.DTOs;

namespace API.Controllers
{

    public class CommentController : ApiController
    {
        public CommentDTOManager Manager = new CommentDTOManager();

        [HttpGet]
        [ActionName("DefaultAction")]
        public IEnumerable<CommentDTO> Get()
        {
            IEnumerable<CommentDTO> returnValue = CommentDTOManager.GetAllComments();
            return returnValue;
        }

        [HttpGet]
        [ActionName("DefaultAction")]
        public CommentDTO Get(int id)
        {
            return CommentDTOManager.GetComment(id);
        }

        [Route("api/Comment/FromNews/{id}")]
        //[ActionName("FromNews")]
        public IEnumerable<CommentDTO> GetCommentsFromNews(int id)
        {
            return CommentDTOManager.GetCommentsForNews(id);
        }

        [Route("api/Comment/PostObject")]
        public CommentSimpleDTO GetPutObject()
        {
            return new CommentSimpleDTO { BelongsToNewsId = -1, CreatedBy = -1, Content = "This is CommentSimpleDTO." +
                "It is used for creating new comments." };

        }

        // POST api/values
        public bool Post(int id, string content)
        {
            return CommentDTOManager.UpdateComment(id, content);
        }

        // PUT api/values
        public bool Put([FromBody]CommentSimpleDTO value)
        {
            return CommentDTOManager.CreateComment(value.CreatedBy, value.BelongsToNewsId, value.Content);
        }

        // DELETE api/values/5
        public bool Delete(int id)
        {
            return CommentDTOManager.DeleteComment(id);
        }
    }
}
