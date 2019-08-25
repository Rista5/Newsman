using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using DataLayerLib.DTOs;
using DataLayerLib.DTOManagers;

namespace API.Controllers
{
    public class NewsController : ApiController
    {
        //GET /api/News/
        public IEnumerable<NewsDTO> Get()
        {
            return NewsDTOManager.GetAllNews();
        }

        [HttpGet]
        [Route("api/NewsModifiedByUser/{id}")]
        public IEnumerable<NewsDTO> Get(int id)
        {
            return NewsDTOManager.GetNewsModifiedByUser(id);
        }

        [HttpGet]
        [Route("api/News/{id}")]
        public NewsDTO GetNewsById(int id)
        {
            return NewsDTOManager.GetNews(id);
        }

        [HttpPut]
        [Route("api/News/{userId}")]
        public bool CreateNews([FromBody]NewsDTO news, int userId)
        {
            return NewsDTOManager.CreateNews(news, userId);
        }

        [HttpPost]
        [Route("api/NewsUpdate/{userId}")]
        public bool UpdateNews([FromBody] NewsDTO news, int userId)
        {
            return NewsDTOManager.UpdateNews(news, userId);
        }

        [HttpPost]
        [Route("api/News/{userId}")]
        public bool UpdateNews([FromBody] SimpleNewsDTO simpleDTO, int userId)
        {
            return NewsDTOManager.UpdateNews(simpleDTO, userId);
        }

        [HttpDelete]
        [Route("api/News/{newsId}")]
        public bool DeleteNews(int newsId)
        {
            return NewsDTOManager.DeleteNews(newsId);
        }
    }
}
