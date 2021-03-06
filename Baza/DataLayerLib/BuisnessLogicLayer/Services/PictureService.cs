﻿using BuisnessLogicLayer.DAOInterfaces;
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
        private IMultimediaLoader loader;

        private PictureService() { }
        public PictureService(PictureData pictureData, IMultimediaLoader loader)
        {
            this.pictureData = pictureData;
            this.loader = loader;
        }

        public IEnumerable<PictureDTO> GetAllPictures()
        {
            List<PictureDTO> resultData = pictureData.GetAllPictures();
            if (resultData.Count > 0)
                foreach (PictureDTO dto in resultData)
                    dto.SetPictureBytes(loader.GetMedia(dto.Id, dto.BelongsToNewsId));
            return resultData;
        }

        public IEnumerable<PictureDTO> GetPictureByNews(int newsID)
        {
            List<PictureDTO> resultData = pictureData.GetPicturesForNews(newsID);
            if (resultData.Count > 0)
                foreach (PictureDTO dto in resultData)
                    dto.SetPictureBytes(loader.GetMedia(dto.Id, dto.BelongsToNewsId));
            return resultData;
        }

        public PictureDTO GetPictureById(int id)
        {
            PictureDTO result = pictureData.GetPicture(id);
            result.SetPictureBytes(loader.GetMedia(result.Id, result.BelongsToNewsId));
            return result;
        }

        public PictureDTO CreatePicture(PictureDTO pic)
        {
            PictureDTO dataResult = pictureData.CreatePicture(pic);
            if (dataResult != null)
            {
                MessageQueueManager menager = MessageQueueManager.Instance;
                menager.PublishMessage(dataResult.BelongsToNewsId, dataResult.Id, dataResult, MessageOperation.Insert);
            }
            return dataResult;
        }

        public bool DeletePicture(int id)
        {
            PictureDTO resultData = pictureData.DeletePicture(id);
            bool result = false;
            if (resultData != null)
            {
                result = loader.DeleteMedia(resultData.Id, resultData.BelongsToNewsId);
                MessageQueueManager manager = MessageQueueManager.Instance;
                manager.PublishMessage(resultData.BelongsToNewsId, resultData.Id, resultData, MessageOperation.Delete);
            }
            return result;
        }

        public bool UpdatePicture(PictureDTO pic)
        {
            bool resault = false;
            PictureDTO dataResult = pictureData.UpdatePicture(pic);
            if (dataResult != null)
            {
                loader.SaveMedia(dataResult.Id, dataResult.BelongsToNewsId, dataResult.GetPictureBytes());

                MessageQueueManager menager = MessageQueueManager.Instance;
                menager.PublishMessage(dataResult.BelongsToNewsId, dataResult.Id, dataResult, MessageOperation.Update);
                resault = true;
            }
            return resault;
        }
    }
}
