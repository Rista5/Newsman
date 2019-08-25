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
        public NewsService(NewsData newsData)
        {
            this.newsData = newsData;
        }

        public IEnumerable<NewsDTO> GetAllNews()
        {
            IEnumerable<News> retData = newsData.GetAllNews();


            IList<NewsDTO> news = new List<NewsDTO>();
            foreach (News n in retData)
                news.Add(new NewsDTO(n));


            foreach (NewsDTO n in news)
            {
                if (n.BackgroundPicture != null)
                    n.BackgroundPicture.SetPictureBytes(loader.GetMedia(n.BackgroundPicture.Id,
                                                                    n.BackgroundPicture.BelongsToNewsId));
                foreach (PictureDTO p in n.Pictures)
                    p.SetPictureBytes(loader.GetMedia(p.Id, p.BelongsToNewsId));
            }
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
        
        public bool CreateNews(NewsDTO news, int userId)
        {
            bool result = false;
            NewsDTO dataResult = newsData.CreateNews(news, userId);
            if(dataResult!= null)
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
            if (dataResult != null)
            {
                MessageQueueManager manager = MessageQueueManager.Instance;
                manager.PublishMessage(dataResult.Id, dataResult.Id, dataResult, MessageOperation.Update);
                result = true;
            }
            return result;
        }
        
        public bool DeleteNews(int userId)
        {
            return newsData.DeleteNews(userId);
        }
    }
}
