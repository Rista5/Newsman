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
            Table("Modified");

            Id(x => x.Id, "Id_modified").GeneratedBy.Increment();

            Map(x => x.ModificationDate, "Modified_date");

            HasOne(x => x.NewsId).ForeignKey("Id_news");
            HasOne(x => x.UserId).ForeignKey("Id_user");
        }
    }
}
