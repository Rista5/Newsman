using DataLayerLib.DTOManagers;
using DataLayerLib.DTOs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace API.Controllers
{
    //Testirati!!!!
    public class AudioController : ApiController
    {
        [HttpGet]
        [ActionName("DefaultAction")]
        public IEnumerable<AudioDTO> GetAllAudios()
        {
            return AudioDTOManager.GetAllAudio();
        }

        [HttpGet]
        [Route("api/Picture/FromNews/{id}")]
        public IEnumerable<AudioDTO> GetPictureByNews(int newsID)
        {
            return AudioDTOManager.GetAudiosForNews(newsID);
        }

        [HttpGet]
        [ActionName("DefaultAction")]
        public AudioDTO GetPictureById(int id)
        {
            return AudioDTOManager.GetAudio(id);
        }

        public bool PostAudio(AudioDTO audio)
        {
            return AudioDTOManager.CreateAudio(audio);
        }

        public bool DeleteAudio(int id)
        {
            return AudioDTOManager.DeleteAudio(id);
        }

        public bool UpdateAudio(AudioDTO audio)
        {
            return AudioDTOManager.UpdateAudio(audio);
        }
    }
}
