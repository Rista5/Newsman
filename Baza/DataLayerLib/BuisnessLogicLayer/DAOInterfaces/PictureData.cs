using ObjectModel.DTOs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BuisnessLogicLayer.DAOInterfaces
{
    public interface PictureData
    {
        List<PictureDTO> GetAllPictures();

        List<PictureDTO> GetPicturesForNews(int newsId);

        PictureDTO GetPicture(int pictureId);

        bool CreatePicture(int newsId, string name, string description, byte[] pictureData = null);

        bool CreatePicture(PictureDTO picturedto);

        bool UpdatePicture(int pictureId, string name, string description, byte[] pictureData);

        bool UpdatePicture(PictureDTO pic);

        bool DeletePicture(int pictureId);
    }
}
