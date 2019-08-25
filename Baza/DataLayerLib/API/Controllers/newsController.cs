using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using ObjectModel.DTOs;
using DataLayerLib.DTOManagers;
using BuisnessLogicLayer.Services;

namespace API.Controllers
{
    public class NewsController : BaseController/*ApiController*/
    {
        private NewsService service;
        //GET /api/News/
        public IEnumerable<NewsDTO> Get()
        {
            service = Service.NewsService;
            return service.GetAllNews();
        }

        [HttpGet]
        [Route("api/NewsModifiedByUser/{id}")]
        public IEnumerable<NewsDTO> Get(int id)
        {
            service = Service.NewsService;
            return service.GetNewsModifiedByUser(id);
        }

        [HttpGet]
        [Route("api/News/{id}")]
        public NewsDTO GetNewsById(int id)
        {
            service = Service.NewsService;
            return service.GetNewsById(id);
        }

        [HttpPut]
        [Route("api/CreateNews/{userId}")]
        public bool CreateNews([FromBody]NewsDTO news, int userId)
        {
            service = Service.NewsService;
            return service.CreateNews(news, userId);
        }

        [HttpPost]
        [Route("api/UpdateNews/{userId}")]
        public bool UpdateNews([FromBody] NewsDTO news, int userId)
        {
            service = Service.NewsService;
            return service.UpdateNews(news, userId);
        }

        [HttpPost]
        [Route("api/News/{userId}")]
        public bool UpdateNews([FromBody] SimpleNewsDTO simpleDTO, int userId)
        {
            service = Service.NewsService;
            return service.UpdateNews(simpleDTO, userId);
        }

        [HttpDelete]
        [Route("api/DeleteNews/{userId}")]
        public bool DeleteNews(int userId)
        {
            service = Service.NewsService;
            return service.DeleteNews(userId);
        }
    }
}
