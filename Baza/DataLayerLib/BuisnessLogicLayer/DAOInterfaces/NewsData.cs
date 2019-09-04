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
        List<NewsDTO> GetAllNews();

        List<SimpleNewsDTO> GetAllNewsSimple();

        List<NewsDTO> GetNewsModifiedByUser(int userId);

        NewsDTO GetNews(int newsId);

        SimpleNewsDTO GetSimpleNewsById(int newsId);

        News GetFullNews(int newsId);

        NewsDTO CreateNews(NewsDTO news, int userId);

        SimpleNewsDTO CreateNews(SimpleNewsDTO news, int userId);

        SimpleNewsDTO UpdateNews(SimpleNewsDTO simpleDTO, int userId);

        NewsDTO DeleteNews(int newsId);

        bool DeleteNewsModifiaction(int modificationId);
    }
}
