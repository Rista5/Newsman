using FluentNHibernate.Mapping;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.Mappers
{
    class NewsMapper :ClassMap<DataLayerLib.Entities.News>
    {
        public NewsMapper()
        {
            Table("news");

            Id(x => x.Id, "Id_news").GeneratedBy.Increment();

            Map(x => x.Title, "Title");
            Map(x => x.Content, "Content");
            Map(x => x.LastModified, "Last_modified");
        }
    }
}
