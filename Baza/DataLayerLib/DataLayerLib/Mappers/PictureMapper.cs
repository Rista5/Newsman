using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using FluentNHibernate.Mapping;

namespace DataLayerLib.Mappers
{
    class PictureMapper:ClassMap<DataLayerLib.Entities.Picture>
    {
        public PictureMapper()
        {
            Table("picture");

            Id(x => x.Id, "Id_picture").GeneratedBy.Increment();

            Map(x => x.Name, "Name");
            Map(x => x.Description, "Description");

            HasOne(x => x.BelongsTo).ForeignKey("Id_news");

        }
    }
}
