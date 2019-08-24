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
            return newsData.CreateNews(news, userId);
        }
        
        public bool UpdateNews(NewsDTO news, int userId)
        {
            return newsData.UpdateNews(news, userId);
        }
        
        public bool UpdateNews(SimpleNewsDTO simpleDTO, int userId)
        {
            return newsData.UpdateNews(simpleDTO, userId);
        }
        
        public bool DeleteNews(int userId)
        {
            return newsData.DeleteNews(userId);
        }
    }
}
