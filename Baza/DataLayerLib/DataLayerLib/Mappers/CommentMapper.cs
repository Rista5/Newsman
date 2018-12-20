using FluentNHibernate.Mapping;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.Mappers
{
    class CommentMapper:ClassMap<DataLayerLib.Entities.Comment>
    {
        public CommentMapper()
        {
            Table("comment");

            Id(x => x.Id, "Id_comment").GeneratedBy.Increment();

            Map(x => x.Content, "Content");
            Map(x => x.PostDate, "Post_date");

            HasOne(x => x.BelongsTo).ForeignKey("Id_news").LazyLoad().Cascade.All();
            HasOne(x => x.CreatedBy).ForeignKey("Id_user").LazyLoad().Cascade.All();
        }
    }
}
