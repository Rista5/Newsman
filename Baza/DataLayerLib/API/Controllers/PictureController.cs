using BuisnessLogicLayer.Services;
using DataLayerLib.DTOManagers;
using ObjectModel.DTOs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace API.Controllers
{
    public class PictureController : ApiController
    {
        private PictureService service;

        public IEnumerable<PictureDTO> GetAllPictures()
        {
            service = new PictureService(new PictureDTOManager());
            return service.GetAllPictures();
        }

        [HttpGet]
        [Route("api/Picture/FromNews/{id}")]
        public IEnumerable<PictureDTO> GetPictureByNews(int newsID)
        {
            service = new PictureService(new PictureDTOManager());
            return service.GetPictureByNews(newsID);
        }

        [HttpGet]
        [ActionName("DefaultAction")]
        public PictureDTO GetPictureById(int id)
        {
            service = new PictureService(new PictureDTOManager());
            return service.GetPictureById(id);
        }

        [HttpPut]
        [ActionName("DefaultAction")]
        public bool CreatePicture(PictureDTO pic)
        {
            service = new PictureService(new PictureDTOManager());
            return service.CreatePicture(pic);
        }

        [HttpDelete]
        [ActionName("DefaultAction")]
        public bool DeletePicture(int id)
        {
            service = new PictureService(new PictureDTOManager());
            return service.DeletePicture(id);
        }

        [HttpPost]
        [ActionName("DefaultAction")]
        public bool UpdatePicture(PictureDTO pic)
        {
            service = new PictureService(new PictureDTOManager());
            return service.UpdatePicture(pic);
        }
    }
}
