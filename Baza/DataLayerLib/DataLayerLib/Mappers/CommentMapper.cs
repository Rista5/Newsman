using FluentNHibernate.Mapping;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.Mappers
{
    class CommentMapper:ClassMap<ObjectModel.Entities.Comment>
    {
        public CommentMapper()
        {
            Table("comment");

            Id(x => x.Id, "Id_comment").GeneratedBy.Increment();

            Map(x => x.Content, "Content");
            Map(x => x.PostDate, "Post_date");

            References(x => x.BelongsTo, "Id_news").LazyLoad();
            References(x => x.CreatedBy, "Id_user").LazyLoad();
        }
    }
}
