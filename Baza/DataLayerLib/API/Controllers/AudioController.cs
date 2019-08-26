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
    public class AudioController : BaseController/*ApiController*/
    {
        private AudioService service;

        [HttpGet]
        [ActionName("DefaultAction")]
        public IEnumerable<AudioDTO> GetAllAudios()
        {
            service = Service.AudioService;
            return service.GetAllAudioDTOs();
        }

        [HttpGet]
        [Route("api/Audio/FromNews/{id}")]
        public IEnumerable<AudioDTO> GetAudioByNews(int newsID)
        {
            service = Service.AudioService;
            return service.GetAudioForNews(newsID);
        }

        [HttpGet]
        [ActionName("DefaultAction")]
        public AudioDTO GetAudioById(int id)
        {
            service = Service.AudioService;
            return service.GetAudioByID(id);
        }

        [HttpPut]
        [Route("api/Audio")]
        public bool PutAudio(AudioDTO audio)
        {
            service = Service.AudioService;
            return service.CreateAudio(audio);
        }

        [HttpDelete]
        [Route("api/Audio")]
        public bool DeleteAudio(int id)
        {
            service = Service.AudioService;
            return service.DeleteAudio(id);
        }

        [HttpPost]
        [Route("api/Audio")]
        public bool UpdateAudio(AudioDTO audio)
        {
            service = Service.AudioService;
            return service.UpdateAudio(audio);
        }
    }
}
