using FluentNHibernate.Mapping;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.Mappers
{
    class UserMapper:ClassMap<DataLayerLib.Entities.User>
    {
        public UserMapper()
        {
            Table("user");

            Id(x => x.Id, "Id_user").GeneratedBy.Increment();

            Map(x => x.Username, "Username");
            Map(x => x.Password, "Password");
        }
    }
}
