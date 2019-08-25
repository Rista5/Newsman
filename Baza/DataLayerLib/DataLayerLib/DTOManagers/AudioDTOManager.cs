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
    public class AudioDTOManager : AudioData
    {
        public List<AudioDTO> GetAllAudio()
        {
            List<AudioDTO> audios = new List<AudioDTO>();
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                IEnumerable<Audio> retData = from a in session.Query<Audio>()
                                             select a;
                foreach (Audio a in retData)
                {
                    AudioDTO audioDto = new AudioDTO(a);
                    audios.Add(audioDto);
                }
                session.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return audios;
        }

        public List<AudioDTO> GetAudiosForNews(int newsId)
        {
            List<AudioDTO> audios = new List<AudioDTO>();
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();
                IEnumerable<Audio> retData = from a in session.Query<Audio>()
                                             where a.BelongsTo.Id == newsId
                                             select a;

                foreach (Audio a in retData)
                {
                    AudioDTO audioDto = new AudioDTO(a);

                    audios.Add(audioDto);
                }
                session.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return audios;
        }

        public AudioDTO GetAudio(int audioId)
        {
            ISession session = null;
            AudioDTO result = null;
            try
            {
                session = DataLayer.GetSession();
                Audio user = session.Load<Audio>(audioId);
                result = new AudioDTO(user);
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

        public bool CreateAudio(int newsId, string name,
            string description, byte[] audioData = null)
        {
            bool result = false;
            ISession session = null;
            try
            {
                Audio audio = new Audio();
                audio.Name = name;
                audio.Description = description;

                session = DataLayer.GetSession();
                News belongsTo = session.Load<News>(newsId);
                audio.BelongsTo = belongsTo;
                session.Save(audio);
                session.Flush();
                session.Close();

                //mora da se napravi poseban za audio
                if (audioData != null)
                {
                    MultimediaLoader.IMultimediaLoader loader = new MultimediaLoader.FileSystemLoader();
                    loader.SaveMedia(audio.Id,audio.BelongsTo.Id, audioData);
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

        public bool CreateAudio(AudioDTO audioDTO)
        {
            bool result = false;
            ISession session = null;
            try
            {
                session = DataLayer.GetSession();

                Audio audio = new Audio();
                audio.Description = audioDTO.Description;
                audio.Name = audioDTO.Name;
                audio.BelongsTo = session.Load<News>(audioDTO.BelongsToNewsId);

                session.Save(audio);
                session.Flush();
                session.Close();

                if (audioDTO.AudioData != null)
                {
                    MultimediaLoader.IMultimediaLoader loader = new MultimediaLoader.FileSystemLoader();
                    loader.SaveMedia(audio.Id, audioDTO.BelongsToNewsId, audioDTO.GetAudioBytes());
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

        public bool UpdateAudio(int audioId, string name,
            string description)
        {
            ISession session = null;
            bool result = false;
            try
            {
                session = DataLayer.GetSession();
                Audio audio = session.Load<Audio>(audioId);
                audio.Name = name;
                audio.Description = description;
                session.SaveOrUpdate(audio);
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

        public bool UpdateAudio(AudioDTO audioDTO)
        {
            ISession session = null;
            bool result = false;
            try
            {
                session = DataLayer.GetSession();

                Audio audio = new Audio();
                audio.Description = audioDTO.Description;
                audio.Name = audioDTO.Name;
                audio.BelongsTo = session.Load<News>(audioDTO.BelongsToNewsId);

                session.SaveOrUpdate(audio);
                session.Flush();
                session.Close();
                if(audioDTO.AudioData!=null)
                {
                    MultimediaLoader.IMultimediaLoader loader = new MultimediaLoader.FileSystemLoader();
                    loader.SaveMedia(audio.Id,audioDTO.BelongsToNewsId, audioDTO.GetAudioBytes());
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

        public bool DeleteAudio(int audioId)
        {
            ISession session = null;
            bool result = false;
            try
            {
                session = DataLayer.GetSession();
                Audio audio = session.Load<Audio>(audioId);

                MultimediaLoader.IMultimediaLoader loader = new MultimediaLoader.FileSystemLoader();
                loader.DeleteMedia(audio.Id, audio.BelongsTo.Id);

                session.Delete(audio);
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
