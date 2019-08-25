using ObjectModel.DTOs;
using ObjectModel.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BuisnessLogicLayer.DAOInterfaces
{
    public interface NewsData
    {
        List<News> GetAllNews();

        List<NewsDTO> GetNewsModifiedByUser(int userId);

        NewsDTO GetNews(int newsId);

        News GetFullNews(int newsId);

        NewsDTO CreateNews(string title, string content, int userId);

        NewsDTO CreateNews(NewsDTO news, int userId);

        SimpleNewsDTO UpdateNews(SimpleNewsDTO simpleDTO, int userId);

        bool DeleteNews(int newsId);

        bool DeleteNewsModifiaction(int modificationId);
    }
}
