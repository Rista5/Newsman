using FluentNHibernate.Mapping;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.Mappers
{
    class NewsModifiedMapper:ClassMap<DataLayerLib.Entities.NewsModified>
    {
        public NewsModifiedMapper()
        {
            Table("modified");

            Id(x => x.Id, "Id_modified").GeneratedBy.Increment();

            Map(x => x.ModificationDate, "Modified_date");

            HasOne(x => x.News).ForeignKey("Id_news");
            HasOne(x => x.User).ForeignKey("Id_user");
        }
    }
}
