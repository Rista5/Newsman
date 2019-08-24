using BuisnessLogicLayer.DAOInterfaces;
using ObjectModel.DTOs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BuisnessLogicLayer.Services
{
    public class AudioService
    {
        private AudioData audioDataAccess;

        private AudioService() { }
        public AudioService(AudioData dataAccessObject)
        {
            this.audioDataAccess = dataAccessObject;
        }

        public IList<AudioDTO> GetAllAudioDTOs()
        {
            return audioDataAccess.GetAllAudio();
        }

        public IList<AudioDTO> GetAudioForNews(int newsID)
        {
            return audioDataAccess.GetAudiosForNews(newsID);
        }

        public AudioDTO GetAudioByID(int audioId)
        {
            return audioDataAccess.GetAudio(audioId);
        }

        public bool CreateAudio(AudioDTO dto)
        {
            return audioDataAccess.CreateAudio(dto);
        }

        public bool CreateAudio(int newsId, string name, string description, string audioData = null)
        {
            AudioDTO dto = new AudioDTO()
            {
                BelongsToNewsId = newsId,
                Description = description,
                Name = name,
                AudioData = audioData
            };
            return audioDataAccess.CreateAudio(dto);
        }

        public bool UpdateAudio(AudioDTO dto)
        {
            return audioDataAccess.UpdateAudio(dto);
        }

        public bool DeleteAudio(int audioId)
        {
            return audioDataAccess.DeleteAudio(audioId);
        }
    }
}
