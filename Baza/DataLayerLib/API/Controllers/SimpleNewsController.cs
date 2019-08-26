using ObjectModel.DTOs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace API.Controllers
{
    public class SimpleNewsController : BaseController
    {
        [HttpGet]
        [Route("api/News/{string}")]
        public IEnumerable<SimpleNewsDTO> Get(string simple)
        {
            return Service.NewsService.GetAllNewsSimple();
        }
    }
}
