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
        
        AudioDTO CreateAudio(AudioDTO audioDTO);

        AudioDTO UpdateAudio(AudioDTO audioDTO);

        AudioDTO DeleteAudio(int audioId);
    }
}
