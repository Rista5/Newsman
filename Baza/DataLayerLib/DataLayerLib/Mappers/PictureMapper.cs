using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using FluentNHibernate.Mapping;

namespace DataLayerLib.Mappers
{
    class PictureMapper:ClassMap<ObjectModel.Entities.Picture>
    {
        public PictureMapper()
        {
            Table("picture");

            Id(x => x.Id, "Id_picture").GeneratedBy.Increment();

            Map(x => x.Name, "Name");
            Map(x => x.Description, "Description");

            References(x => x.BelongsTo, "Id_news").LazyLoad();
        }
    }
}
