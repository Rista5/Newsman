﻿using ObjectModel.DTOs;
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
        [Route("api/SimpleNews/")]
        public IEnumerable<SimpleNewsDTO> Get()
        {
            return Service.NewsService.GetAllNewsSimple();
        }

        [HttpPut]
        [Route("api/SimpleNews/{userId}")]
        public SimpleNewsDTO CreateNews([FromBody]SimpleNewsDTO news, int userId)
        {
            return Service.NewsService.CreateNews(news, userId);
        }
    }
}
