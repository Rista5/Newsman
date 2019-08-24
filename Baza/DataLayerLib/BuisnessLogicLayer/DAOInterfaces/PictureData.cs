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

        PictureDTO CreatePicture(int newsId, string name, string description, byte[] pictureData = null);

        PictureDTO CreatePicture(PictureDTO picturedto);

        bool UpdatePicture(int pictureId, string name, string description, byte[] pictureData);

        PictureDTO UpdatePicture(PictureDTO pic);

        bool DeletePicture(int pictureId);
    }
}
