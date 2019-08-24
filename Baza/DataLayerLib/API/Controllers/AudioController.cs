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
    //Testirati!!!!
    public class AudioController : ApiController
    {
        private AudioService service;

        [HttpGet]
        [ActionName("DefaultAction")]
        public IEnumerable<AudioDTO> GetAllAudios()
        {
            service = new AudioService(new AudioDTOManager());
            return service.GetAllAudioDTOs();
        }

        [HttpGet]
        [Route("api/Audio/FromNews/{id}")]
        public IEnumerable<AudioDTO> GetAudioByNews(int newsID)
        {
            service = new AudioService(new AudioDTOManager());
            return service.GetAudioForNews(newsID);
        }

        [HttpGet]
        [ActionName("DefaultAction")]
        public AudioDTO GetAudioById(int id)
        {
            service = new AudioService(new AudioDTOManager());
            return service.GetAudioByID(id);
        }

        [HttpPut]
        [Route("api/Audio")]
        public bool PutAudio(AudioDTO audio)
        {
            service = new AudioService(new AudioDTOManager());
            return service.CreateAudio(audio);
        }

        [HttpDelete]
        [Route("api/Audio")]
        public bool DeleteAudio(int id)
        {
            service = new AudioService(new AudioDTOManager());
            return service.DeleteAudio(id);
        }

        [HttpPost]
        [Route("api/Audio")]
        public bool UpdateAudio(AudioDTO audio)
        {
            service = new AudioService(new AudioDTOManager());
            return service.UpdateAudio(audio);
        }
    }
}
