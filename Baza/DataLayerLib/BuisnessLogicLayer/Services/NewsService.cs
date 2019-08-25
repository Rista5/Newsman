using BuisnessLogicLayer.DAOInterfaces;
using ObjectModel.DTOs;
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
        private NewsService() { }
        public NewsService(NewsData newsData)
        {
            this.newsData = newsData;
        }

        public IEnumerable<NewsDTO> GetAllNews()
        {
            return newsData.GetAllNews();
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
