using BuisnessLogicLayer.Services;
using DataLayerLib.DTOManagers;
using DataLayerLib.MultimediaLoader;
using ObjectModel.DTOs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Web.Http;

namespace API.Controllers
{
    public class PictureController : BaseController
    {
        private PictureService service;

        public IEnumerable<PictureDTO> GetAllPictures()
        {
            service = Service.PictureService;
            return service.GetAllPictures();
        }

        [HttpGet]
        [Route("api/Picture/FromNews/")]
        public IEnumerable<PictureDTO> GetPictureByNews([FromUri] int newsID)
        {
            service = Service.PictureService;
            return service.GetPictureByNews(newsID);
        }

        [HttpGet]
        [ActionName("DefaultAction")]
        public PictureDTO GetPictureById(int id)
        {
            service = Service.PictureService;
            return service.GetPictureById(id);
        }

        [HttpPut]
        [ActionName("DefaultAction")]
        public PictureDTO CreatePicture(PictureDTO pic)
        {
            service = Service.PictureService;
            return service.CreatePicture(pic);
        }

        [HttpDelete]
        [ActionName("DefaultAction")]
        public bool DeletePicture(int id)
        {
            service = Service.PictureService;
            return service.DeletePicture(id);
        }

        [HttpPost]
        [ActionName("DefaultAction")]
        public bool UpdatePicture(PictureDTO pic)
        {
            service = Service.PictureService;
            return service.UpdatePicture(pic);
        }

        [HttpGet]
        [Route("api/picture/test")]
        public HttpResponseMessage Get()
        {
            HttpResponseMessage Response = new HttpResponseMessage(HttpStatusCode.OK);
            byte[] arr = Encoding.ASCII.GetBytes("Sreten");
            FileSystemLoader loader = new FileSystemLoader();
            arr = loader.GetMedia(66, 18);
            Response.Content = new ByteArrayContent(arr);
            Response.Content.Headers.ContentType = new System.Net.Http.Headers.MediaTypeHeaderValue("application/octet-stream");
            return Response;
        }

        [HttpPut]
        [Route("api/picture/test")]
        public async System.Threading.Tasks.Task<HttpResponseMessage> PutAsync(HttpRequestMessage msg)
        {
            HttpResponseMessage response = new HttpResponseMessage();
            FileSystemLoader loader = new FileSystemLoader();
            if (msg.Content.Headers.ContentType.MediaType != "application/octet-stream")
            {
                response.StatusCode = HttpStatusCode.UnsupportedMediaType;
                return response;
            }
            byte[] pic = await msg.Content.ReadAsByteArrayAsync();
            loader.SaveMedia(67, 18, pic);
            response.StatusCode = HttpStatusCode.OK;
            return response;
        }
    }
}
