using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ObjectModel.DTOs;
using NHibernate;
using ObjectModel.Entities;
using BuisnessLogicLayer.DAOInterfaces;

namespace DataLayerLib.DTOManagers
{
    public class PictureDTOManager : PictureData
    {
        public List<PictureDTO> GetAllPictures()
        {
            List<PictureDTO> pictures = new List<PictureDTO>();
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                IEnumerable<Picture> retData = from p in session.Query<Picture>()
                                               select p;
                foreach (Picture p in retData)
                {
                    PictureDTO picDto = new PictureDTO(p);
                    pictures.Add(picDto);
                }
                session.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return pictures;
        }

        public List<PictureDTO> GetPicturesForNews(int newsId)
        {
            List<PictureDTO> pictures = new List<PictureDTO>();
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                IEnumerable<Picture> retData = from p in session.Query<Picture>()
                                               where p.BelongsTo.Id == newsId
                                               select p;
                foreach (Picture p in retData)
                {
                    PictureDTO pictureDto = new PictureDTO(p);
                    pictures.Add(pictureDto);
                }
                session.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return pictures;
        }

        public PictureDTO GetPicture(int pictureId)
        {
            ISession session = null;
            PictureDTO result = null;
            try
            {
                session = DataLayer.GetSession();
                Picture picture = session.Load<Picture>(pictureId);
                result = new PictureDTO(picture);
                session.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return result;
        }

        public PictureDTO CreatePicture(PictureDTO picturedto)
        {
            PictureDTO result = null;
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                Picture picture = new Picture();
                picture.Description = picturedto.Description;
                picture.Name = picturedto.Name;


                News news = session.Load<News>(picturedto.BelongsToNewsId);
                picture.BelongsTo = news;

                session.Save(picture);
                session.Flush();

                if (picturedto.PictureData != null)
                {
                    result = picturedto;
                }

                session.Close();
                result = new PictureDTO(picture);
            }
            catch (Exception ex)
            {

                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return result;
        }

        public PictureDTO UpdatePicture(PictureDTO pic)
        {
            ISession session = null;
            PictureDTO result = null;
            try
            {
                session = DataLayer.GetSession();

                Picture picture = new Picture();
                picture = session.Load<Picture>(pic.Id);
                picture.Name = pic.Name;
                picture.BelongsTo = session.Load<News>(pic.BelongsToNewsId);
                picture.Description = pic.Description;

                session.SaveOrUpdate(picture);
                session.Flush();

                session.Close();

                result = pic;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }

            return result;
        }

        public PictureDTO DeletePicture(int pictureId)
        {
            ISession session = null;
            PictureDTO result = null;
            try
            {
                session = DataLayer.GetSession();
                Picture picture = session.Load<Picture>(pictureId);
                
                result = new PictureDTO(picture);

                session.Delete(picture);
                session.Flush();
                session.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }

            return result;
        }

    }
}
