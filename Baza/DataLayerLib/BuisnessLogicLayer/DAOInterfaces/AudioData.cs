using ObjectModel.DTOs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BuisnessLogicLayer.DAOInterfaces
{
    public interface AudioData
    {
        List<AudioDTO> GetAllAudio();

        List<AudioDTO> GetAudiosForNews(int newsId);

        AudioDTO GetAudio(int audioId);
        
        bool CreateAudio(AudioDTO audioDTO);

        bool UpdateAudio(int audioId, string name, string description);

        bool UpdateAudio(AudioDTO audioDTO);

        bool DeleteAudio(int audioId);
    }
}
