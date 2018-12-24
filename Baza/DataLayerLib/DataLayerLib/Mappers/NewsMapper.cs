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

            HasMany(x => x.Pictures).KeyColumn("Id_news").LazyLoad().Cascade.All().Inverse();
            HasMany(x => x.AudioRecordings).KeyColumn("Id_news").LazyLoad().Cascade.All().Inverse();
            HasMany(x => x.Comments).KeyColumn("Id_news").LazyLoad().Cascade.All().Inverse();

            HasMany(x => x.Modifications).KeyColumn("Id_news").Cascade.All().Inverse();
            //HasManyToMany(x => x.Modifications)
            //    .Table("created")
            //    .ParentKeyColumn("Id_news")
            //    .ChildKeyColumn("Id_user")
            //    .Cascade.All();
        }
    }
}
