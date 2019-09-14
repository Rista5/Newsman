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

        public AudioDTO CreateAudio(AudioDTO audioDTO)
        {
            AudioDTO result = null;
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

                result = new AudioDTO(audio);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }
            return result;
        }

        public AudioDTO UpdateAudio(AudioDTO audioDTO)
        {
            ISession session = null;
            AudioDTO result = null;
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
                
                result = new AudioDTO(audio);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                if (session != null)
                    session.Close();
            }

            return result;
        }

        public AudioDTO DeleteAudio(int audioId)
        {
            ISession session = null;
            AudioDTO result = null;
            try
            {
                session = DataLayer.GetSession();
                Audio audio = session.Load<Audio>(audioId);

                session.Delete(audio);
                session.Flush();
                session.Close();
                result = new AudioDTO(audio);
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
