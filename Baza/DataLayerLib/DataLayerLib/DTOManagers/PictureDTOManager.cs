using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DataLayerLib.DTOs;
using NHibernate;
using DataLayerLib.Entities;

namespace DataLayerLib.DTOManagers
{
    public class PictureDTOManager
    {
        public static List<PictureDTO> GetAllPictures()
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

        public static List<PictureDTO> GetPicturesForNews(int newsId)
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

        public static PictureDTO GetPicture(int pictureId)
        {
            ISession session = null;
            PictureDTO result = null;
            try
            {
                session = DataLayer.GetSession();
                Picture picture = session.Load<Picture>(pictureId);
                result = new PictureDTO(picture);
                MultimediaLoader.IPictureLoader loader = new MultimediaLoader.FileSystemPictureLoader();
                result.PictureData = loader.GetPicture(picture.Id, picture.Name);
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

        public static bool CreatePicture(int newsId, string name,
            string description, byte[] pictureData = null)
        {
            bool result = false;
            ISession session = null;
            try
            {
                Picture picture = new Picture();
                picture.Name = name;
                picture.Description = description;

                session = DataLayer.GetSession();
                News belongsTo = session.Load<News>(newsId);
                picture.BelongsTo = belongsTo;
                session.Save(picture);
                session.Flush();
                session.Close();

                if (pictureData != null)
                {
                    MultimediaLoader.IPictureLoader loader = new MultimediaLoader.FileSystemPictureLoader();
                    loader.SavePicture(picture.Id, picture.Name, pictureData);
                }

                result = true;
            }
            catch (Exception ex)
            {

                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return result;
        }

        public static bool CreatePicture(PictureDTO picture)
        {
            bool result = false;
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                
                session.Save(picture);
                session.Flush();
                session.Close();

                if (picture.PictureData != null)
                {
                    MultimediaLoader.IPictureLoader loader = new MultimediaLoader.FileSystemPictureLoader();
                    loader.SavePicture(picture.Id, picture.Name, picture.PictureData);
                }

                result = true;
            }
            catch (Exception ex)
            {

                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return result;
        }

        public static bool UpdatePicture(int pictureId, string name,
            string description, byte[] pictureData)
        {
            ISession session = null;
            bool result = false;
            try
            {
                session = DataLayer.GetSession();
                Picture picture = session.Load<Picture>(pictureId);
                picture.Name = name;
                picture.Description = description;
                session.SaveOrUpdate(picture);
                session.Flush();
                session.Close();
                if (pictureData != null)
                {
                    MultimediaLoader.IPictureLoader loader = new MultimediaLoader.FileSystemPictureLoader();
                    loader.SavePicture(picture.Id, picture.Name, pictureData);
                }
                result = true;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }

            return result;
        }

        public static bool UpdatePicture(PictureDTO picture)
        {
            ISession session = null;
            bool result = false;
            try
            {
                session = DataLayer.GetSession();
                session.SaveOrUpdate(picture);
                session.Flush();
                session.Close();
                if (picture.PictureData != null)
                {
                    MultimediaLoader.IPictureLoader loader = new MultimediaLoader.FileSystemPictureLoader();
                    loader.SavePicture(picture.Id, picture.Name, picture.PictureData);
                }
                result = true;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }

            return result;
        }

        public static bool DeletePicture(int pictureId)
        {
            ISession session = null;
            bool result = false;
            try
            {
                session = DataLayer.GetSession();
                Picture picture = session.Load<Picture>(pictureId);

                MultimediaLoader.IPictureLoader loader = new MultimediaLoader.FileSystemPictureLoader();
                loader.DeletePicture(picture.Id, picture.Name);

                session.Delete(picture);
                session.Flush();
                session.Close();
                result = true;
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
