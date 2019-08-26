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

        PictureDTO CreatePicture(PictureDTO picturedto);

        PictureDTO UpdatePicture(PictureDTO pic);

        PictureDTO DeletePicture(int pictureId);
    }
}
