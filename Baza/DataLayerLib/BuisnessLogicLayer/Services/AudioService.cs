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
        private IMultimediaLoader loader;

        private AudioService() { }
        public AudioService(AudioData dataAccessObject, IMultimediaLoader loader)
        {
            this.loader = loader;
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
            bool result = false;
            AudioDTO resultData = audioDataAccess.CreateAudio(dto);
            if (resultData.AudioData != null)
            {
                result = loader.SaveMedia(resultData.Id, resultData.BelongsToNewsId, resultData.GetAudioBytes());

                MessageQueueManager manager = MessageQueueManager.Instance;
                manager.PublishMessage(resultData.BelongsToNewsId, resultData.Id, resultData, MessageOperation.Insert);
            }
            return result;
        }

        public bool UpdateAudio(AudioDTO dto)
        {
            bool result = false;
            AudioDTO resultData = audioDataAccess.UpdateAudio(dto);
            if (resultData.AudioData != null)
            {
                result = loader.SaveMedia(resultData.Id, resultData.BelongsToNewsId, resultData.GetAudioBytes());

                MessageQueueManager manager = MessageQueueManager.Instance;
                manager.PublishMessage(resultData.BelongsToNewsId, resultData.Id, resultData, MessageOperation.Update);
            }
            return result;
        }

        public bool DeleteAudio(int audioId)
        {
            bool result = false;
            AudioDTO resultData = audioDataAccess.DeleteAudio(audioId);
            if (resultData.AudioData != null)
            {
                result = loader.SaveMedia(resultData.Id, resultData.BelongsToNewsId, resultData.GetAudioBytes());

                MessageQueueManager manager = MessageQueueManager.Instance;
                manager.PublishMessage(resultData.BelongsToNewsId, resultData.Id, resultData, MessageOperation.Delete);
            }
            return result;
        }
    }
}
