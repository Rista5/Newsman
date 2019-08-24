using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using BuisnessLogicLayer.Services;
using DataLayerLib.DTOManagers;
using ObjectModel.DTOs;

namespace API.Controllers
{

    public class CommentController : ApiController
    {
        public CommentService service;

        [HttpGet]
        [ActionName("DefaultAction")]
        public IEnumerable<CommentDTO> Get()
        {
            service = new CommentService(new CommentDTOManager());
            IEnumerable<CommentDTO> returnValue = service.GetAllComments();
            return returnValue;
        }

        [HttpGet]
        [ActionName("DefaultAction")]
        public CommentDTO Get(int id)
        {
            service = new CommentService(new CommentDTOManager());
            return service.GetCommentById(id);
        }

        [Route("api/Comment/FromNews/{id}")]
        //[ActionName("FromNews")]
        public IEnumerable<CommentDTO> GetCommentsFromNews(int id)
        {
            service = new CommentService(new CommentDTOManager());
            return service.GetCommentsFromNews(id);
        }

        // POST api/values
        public bool Post(int id, string content)
        {
            service = new CommentService(new CommentDTOManager());
            return service.UpdateComment(id, content);
        }

        // PUT api/values
        public bool Put([FromBody]CommentSimpleDTO value)
        {
            service = new CommentService(new CommentDTOManager());
            return service.CreateComment(value);
        }

        // DELETE api/values/5
        public bool Delete(int id)
        {
            service = new CommentService(new CommentDTOManager());
            return service.DeleteComment(id);
        }
    }
}
