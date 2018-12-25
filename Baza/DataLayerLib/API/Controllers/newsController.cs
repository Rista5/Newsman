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
    }
}
