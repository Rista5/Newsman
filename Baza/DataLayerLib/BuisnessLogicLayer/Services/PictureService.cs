using BuisnessLogicLayer.DAOInterfaces;
using ObjectModel.DTOs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BuisnessLogicLayer.Services
{
    public class PictureService
    {
        private PictureData pictureData;
        private PictureService() { }
        public PictureService(PictureData pictureData)
        {
            this.pictureData = pictureData;
        }
        
        public IEnumerable<PictureDTO> GetAllPictures()
        {
            return pictureData.GetAllPictures();
        }
        
        public IEnumerable<PictureDTO> GetPictureByNews(int newsID)
        {
            return pictureData.GetPicturesForNews(newsID);
        }
        
        public PictureDTO GetPictureById(int id)
        {
            return pictureData.GetPicture(id);
        }
        
        public bool CreatePicture(PictureDTO pic)
        {
            bool resault = false;
            PictureDTO dataResult = pictureData.CreatePicture(pic);
            if (dataResult != null)
            {
                MessageQueueManager menager = MessageQueueManager.Instance;
                menager.PublishMessage(dataResult.BelongsToNewsId, dataResult.Id, dataResult, MessageOperation.Insert);
                resault = true;
            }
            return resault;
        }
        
        public bool DeletePicture(int id)
        {
            return pictureData.DeletePicture(id);
        }
        
        public bool UpdatePicture(PictureDTO pic)
        {
            bool resault = false;
            PictureDTO dataResult = pictureData.UpdatePicture(pic);
            if (dataResult != null)
            {
                MessageQueueManager menager = MessageQueueManager.Instance;
                menager.PublishMessage(dataResult.BelongsToNewsId, dataResult.Id, dataResult, MessageOperation.Update);
                resault = true;
            }
            return resault;
        }
    }
}
