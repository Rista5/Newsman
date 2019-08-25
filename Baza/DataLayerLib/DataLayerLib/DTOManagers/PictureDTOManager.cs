﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DataLayerLib.DTOs;
using NHibernate;
using DataLayerLib.Entities;
using DataLayerLib.MultimediaLoader;

namespace DataLayerLib.DTOManagers
{
    public class PictureDTOManager
    {
        private static IMultimediaLoader _loader = null;

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
                    picDto.SetPictureBytes(Loader.GetMedia(p.Id, p.BelongsTo.Id));
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
                MultimediaLoader.IMultimediaLoader loader = new MultimediaLoader.FileSystemLoader();
                result.SetPictureBytes(loader.GetMedia(picture.Id,picture.BelongsTo.Id));
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

                if (pictureData != null)
                {
                    MultimediaLoader.IMultimediaLoader loader = new MultimediaLoader.FileSystemLoader();
                    loader.SaveMedia(picture.Id,picture.BelongsTo.Id, pictureData);
                }

                PictureDTO dto = new PictureDTO(picture);
                //dto.PictureData = pictureData;
                dto.SetPictureBytes(pictureData);
                MessageQueueManager menager = MessageQueueManager.Instance;
                menager.PublishMessage(picture.BelongsTo.Id, picture.Id, dto, MessageOperation.Insert);

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

        public static bool CreatePicture(PictureDTO picturedto)
        {
            bool result = false;
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
                    MultimediaLoader.IMultimediaLoader loader = new MultimediaLoader.FileSystemLoader();
                    loader.SaveMedia(picture.Id, picturedto.BelongsToNewsId, picturedto.GetPictureBytes());

                    MessageQueueManager menager = MessageQueueManager.Instance;
                    PictureDTO message = new PictureDTO(picture);
                    message.PictureData = picturedto.PictureData;
                    menager.PublishMessage(picture.BelongsTo.Id, picture.Id, message, MessageOperation.Insert);
                }

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
                
                if (pictureData != null)
                {
                    MultimediaLoader.IMultimediaLoader loader = new MultimediaLoader.FileSystemLoader();
                    loader.SaveMedia(picture.Id, picture.BelongsTo.Id, pictureData);
                }

                PictureDTO dto = new PictureDTO(picture);
                dto.SetPictureBytes(pictureData);
                MessageQueueManager manager = MessageQueueManager.Instance;
                manager.PublishMessage(picture.BelongsTo.Id, picture.Id, dto, MessageOperation.Update);

                session.Close();

                result = true;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }

            return result;
        }

        public static bool UpdatePicture(PictureDTO pic)
        {
            ISession session = null;
            bool result = false;
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

                if (pic.PictureData != null)
                {
                    MultimediaLoader.IMultimediaLoader loader = new MultimediaLoader.FileSystemLoader();
                    loader.SaveMedia(picture.Id,pic.BelongsToNewsId, pic.GetPictureBytes());
                }

                MessageQueueManager manager = MessageQueueManager.Instance;
                manager.PublishMessage(picture.BelongsTo.Id, picture.Id, pic, MessageOperation.Update);

                session.Close();

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

                IMultimediaLoader loader = new FileSystemLoader();
                loader.DeleteMedia(picture.Id,picture.BelongsTo.Id);

                PictureDTO dto = new PictureDTO(picture);
                session.Delete(picture);

                MessageQueueManager manager = MessageQueueManager.Instance;
                manager.PublishMessage(picture.BelongsTo.Id, picture.Id, dto, MessageOperation.Delete);

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

        static IMultimediaLoader Loader
        {
            get
            {
                if (_loader == null)
                    _loader = new FileSystemLoader();
                return _loader;
            }
        }

    }
}
