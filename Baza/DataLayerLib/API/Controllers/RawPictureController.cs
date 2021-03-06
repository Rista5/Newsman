﻿using DataLayerLib.MultimediaLoader;
using ObjectModel.DTOs;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Web.Http;

namespace API.Controllers
{
    public class RawPictureController : BaseController
    {
        [HttpGet]
        [Route("api/picture/raw/")]
        public HttpResponseMessage Get([FromUri]int picId, [FromUri]int newsId)
        {        
            HttpResponseMessage Response = new HttpResponseMessage(HttpStatusCode.OK);
            byte[] picture = Service.RawPictureService.GetPicture(picId, newsId);
            if (picture == null)
            {
                Response.StatusCode = HttpStatusCode.NoContent;
                return Response;
            }
            Response.Content = new ByteArrayContent(picture);
            Response.Content.Headers.ContentType = new System.Net.Http.Headers.MediaTypeHeaderValue("application/octet-stream");
            return Response;
        }

        [HttpGet]
        [Route("api/picture/raw/")]
        public HttpResponseMessage Get([FromBody]PictureDTO pic)
        {
            HttpResponseMessage Response = new HttpResponseMessage(HttpStatusCode.OK);
            byte[] picture = Service.RawPictureService.GetPicture(pic.Id, pic.BelongsToNewsId);
            Response.Content = new ByteArrayContent(picture);
            Response.Content.Headers.ContentType = new System.Net.Http.Headers.MediaTypeHeaderValue("application/octet-stream");
            return Response;
        }

        [HttpGet]
        [Route("api/picture/raw/")]
        public HttpResponseMessage Get([FromUri]int picId)
        {
            HttpResponseMessage Response = new HttpResponseMessage(HttpStatusCode.OK);
            int newsId = Service.PictureService.GetPictureById(picId).BelongsToNewsId;
            byte[] picture = Service.RawPictureService.GetPicture(picId, newsId);
            Response.Content = new ByteArrayContent(picture);
            Response.Content.Headers.ContentType = new System.Net.Http.Headers.MediaTypeHeaderValue("application/octet-stream");
            return Response;
        }

        [HttpPut]
        [Route("api/picture/raw/")]
        public async System.Threading.Tasks.Task<HttpResponseMessage> PutAsync(HttpRequestMessage msg, [FromUri]int picId,[FromUri]int newsId)
        {
            HttpResponseMessage response = new HttpResponseMessage();
            if (msg.Content.Headers.ContentType.MediaType != "application/octet-stream")
            {
                response.StatusCode = HttpStatusCode.UnsupportedMediaType;
                return response;
            }
            //byte[] pic = await msg.Content.ReadAsByteArrayAsync();
            var ms = new MemoryStream();
            try
            {
                await msg.Content.CopyToAsync(ms);
            }
            catch(Exception ex)
            {
                string s = ex.Message;
            }
            ms.Seek(0, SeekOrigin.Begin);

            var pic = ms.ToArray();
            Service.RawPictureService.PutPicture(picId, newsId, pic);
            response.StatusCode = HttpStatusCode.OK;
            return response;
        }
    }
}
