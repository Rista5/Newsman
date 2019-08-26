using BuisnessLogicLayer.DAOInterfaces;
using ObjectModel.DTOs;
using ObjectModel.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BuisnessLogicLayer.Services
{
    public class NewsService
    {
        private NewsData newsData;
        private IMultimediaLoader loader;

        private NewsService() { }
        public NewsService(NewsData newsData, IMultimediaLoader loader)
        {
            this.loader = loader;
            this.newsData = newsData;
        }

        public IEnumerable<NewsDTO> GetAllNews()
        { 
            IList<NewsDTO> news = newsData.GetAllNews();
            return news;
        }

        public IEnumerable<SimpleNewsDTO> GetAllNewsSimple()
        {
            IList<SimpleNewsDTO> news = newsData.GetAllNewsSimple();
            return news;
        }

        public IEnumerable<NewsDTO> GetNewsModifiedByUser(int id)
        {
            return newsData.GetNewsModifiedByUser(id);
        }
        
        public NewsDTO GetNewsById(int id)
        {
            return newsData.GetNews(id);
        }
        

        //This Shit needs testing
        public bool CreateNews(NewsDTO news, int userId)
        {
            bool result = false;
            NewsDTO dataResult = newsData.CreateNews(news, userId);

            if (news.BackgroundPicture != null)
            {
                loader.SaveMedia(news.BackgroundPicture.Id, news.Id, news.BackgroundPicture.GetPictureBytes());
                dataResult.BackgroundPicture = news.BackgroundPicture;
            }

            //TODO ovo uopste ne izgleda dobro, ne znam kako bi bolje mogle da se ucitavaju slike

            //for (int i = 0; i < news.Pictures.Count; i++)
            //{
            //    loader.SaveMedia(news.Pictures[i].Id, news.Id, news.Pictures[i].GetPictureBytes());

            //    PictureDTO dto = dataResult.Pictures[i];
            //    dto.PictureData = news.Pictures[i].PictureData;

            //}

            if (dataResult!= null)
            {
                MessageQueueManager manager = MessageQueueManager.Instance;
                manager.PublishMessage(dataResult.Id, dataResult.Id, dataResult, MessageOperation.Insert);
                result = true;
            }

            return result;
        }
        
        public bool UpdateNews(SimpleNewsDTO simpleDTO, int userId)
        {
            bool result = false;
            SimpleNewsDTO dataResult = newsData.UpdateNews(simpleDTO, userId);
            
            //if (simpleDTO.BackgroundPicture != null)
            //{
            //    loader.SaveMedia(simpleDTO.BackgroundPicture.Id, simpleDTO.Id, simpleDTO.BackgroundPicture.GetPictureBytes());
            //    dataResult.BackgroundPicture.PictureData = simpleDTO.BackgroundPicture.PictureData;
            //}

            if (dataResult != null)
            {
                MessageQueueManager manager = MessageQueueManager.Instance;
                manager.PublishMessage(dataResult.Id, dataResult.Id, dataResult, MessageOperation.Update);
                result = true;
            }
            return result;
        }
        
        public bool DeleteNews(int newsId)
        {
            NewsDTO dataResult = newsData.DeleteNews(newsId);
            bool result = false;
            if (dataResult != null)
            {
                foreach (PictureDTO p in dataResult.Pictures)
                {
                    loader.DeleteMedia(p.Id, dataResult.Id);
                }
                if (dataResult.BackgroundPicture != null)
                    loader.DeleteMedia(dataResult.BackgroundPicture.Id, dataResult.Id);


                MessageQueueManager manager = MessageQueueManager.Instance;
                manager.PublishMessage(dataResult.Id, dataResult.Id, dataResult, MessageOperation.Delete);
                result = true;
            }
            return result;
        }
    }
}
