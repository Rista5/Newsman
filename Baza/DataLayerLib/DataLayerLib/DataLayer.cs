using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using FluentNHibernate;
using NHibernate;
using FluentNHibernate.Cfg;
using FluentNHibernate.Cfg.Db;

namespace DataLayerLib
{
    class DataLayer
    {
        private static ISessionFactory _factory = null;
        private static object objLock = new object();
        private static String connectionString = "Server = localhost; Port = 3307; Database = aips; Uid = root; Pwd = root";

        public static ISession GetSession()
        {
            if(_factory == null)
            {
                lock(objLock)
                {
                    if (_factory == null)
                        _factory = CreateSessionFactory();

                }
            }

            return _factory.OpenSession();
        }

        private static ISessionFactory CreateSessionFactory()
        {
            var cfg = Fluently.Configure()
                .Database(MySQLConfiguration.Standard.ConnectionString(connectionString))
                .Mappings(m => m.FluentMappings.AddFromAssemblyOf<Mappers.NewsMapper>());

            try
            {
                return cfg.BuildSessionFactory();
            }
            catch(Exception e)
            {
                Console.Write(e.Message);
                return null;
            }
        }
    }
}
