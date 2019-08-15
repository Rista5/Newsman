using DataLayerLib.DTOs;
using DataLayerLib.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.DTOManagersInterfaces
{
    public interface INewsDTOManager
    {
        List<NewsDTO> GetAllNews();

        List<NewsDTO> GetNewsModifiedByUser(int userId);

        NewsDTO GetNews(int newsId);

        News GetFullNews(int newsId);

        bool CreateNews(string title, string content,
            int userId);

        bool CreateNews(NewsDTO news, int userId);

        bool UpdateNews(int userId, int newsId,
            string title, string content);

        bool UpdateNews(NewsDTO newsDTO, int userId);

        bool DeleteNews(int newsId);

        bool DeleteNewsModifiaction(int modificationId);

        News ExpandDTO(NewsDTO newsDTO);
    }
}
