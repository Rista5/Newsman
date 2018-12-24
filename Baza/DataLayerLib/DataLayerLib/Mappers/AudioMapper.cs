using FluentNHibernate.Mapping;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.Mappers
{
    class AudioMapper:ClassMap<DataLayerLib.Entities.Audio>
    {
        public AudioMapper()
        {
            Table("audio");

            Id(x => x.Id, "Id_audio").GeneratedBy.Increment();

            Map(x => x.Name, "Name");
            Map(x => x.Description, "Description");

            References(x => x.BelongsTo, "Id_news").LazyLoad();
        }
    }
}
