using FluentNHibernate.Mapping;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.Mappers
{
    class NewsModifiedMapper:ClassMap<ObjectModel.Entities.NewsModified>
    {
        public NewsModifiedMapper()
        {
            Table("modified");

            Id(x => x.Id, "Id_modified").GeneratedBy.Increment();

            Map(x => x.ModificationDate, "Modified_date");

            References(x => x.News, "Id_news");
            References(x => x.User, "Id_user");
        }
    }
}
