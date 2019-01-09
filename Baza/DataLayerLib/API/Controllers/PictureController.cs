﻿using DataLayerLib.DTOManagers;
using DataLayerLib.DTOs;
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
        [HttpGet]
        [ActionName("DefaultAction")]
        public IEnumerable<PictureDTO> GetAllPictures()
        {
            return PictureDTOManager.GetAllPictures();
        }

        [HttpGet]
        [Route("api/Picture/FromNews/{id}")]
        public IEnumerable<PictureDTO> GetPictureByNews(int newsID)
        {
            return PictureDTOManager.GetPicturesForNews(newsID);
        }

        [HttpGet]
        [ActionName("DefaultAction")]
        public PictureDTO GetPictureById(int id)
        {
            return PictureDTOManager.GetPicture(id);
        }

        public bool PostPic(PictureDTO pic)
        {
            return PictureDTOManager.CreatePicture(pic);
        }

        public bool DeletePicture(int id)
        {
            return PictureDTOManager.DeletePicture(id);
        }

        public bool UpdatePicture(PictureDTO pic)
        {
            return PictureDTOManager.UpdatePicture(pic);
        }
    }
}