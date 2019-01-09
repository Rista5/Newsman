using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RabbitMQ.Client;

namespace DataLayerLib
{
    //razmisli da li treba da se svaki put kreira konekcija ili je server uvek povezan
    // mozda da postoji jedan exchange i da se postavljaju routing keyevi za vesti??
    public class MessageQueueManager
    {
        private static MessageQueueManager _instance;
        private static object objLock = new object();
        private ConnectionFactory factory;
        string host = "localhost";

        private MessageQueueManager()
        {
            factory = new ConnectionFactory()
            {
                HostName = host,
                UserName = "admin",
                Password = "admin",
                Port = Protocols.DefaultProtocol.DefaultPort,
                Protocol = Protocols.DefaultProtocol,
                VirtualHost = "/",
            };
            
        }

        public static MessageQueueManager Instance
        {
            get
            {
                if(_instance==null)
                {
                    lock(objLock)
                    {
                        _instance = new MessageQueueManager();
                    }
                }
                return _instance;
            }
        }

        public void DeclareExchange(string name)
        {
            using (var connection = factory.CreateConnection())
            {
                using (var channel = connection.CreateModel())
                {
                    channel.ExchangeDeclare(exchange: name, type: "fanout");
                }
            }
        }

        public void DeclareExchange(string name,string type)
        {
            using (var connection = factory.CreateConnection())
            {
                using (var channel = connection.CreateModel())
                {
                    channel.ExchangeDeclare(exchange: name, type: type);
                }
            }
        }

        public void PublishMessage(string exchangeName)
        {
            using (var connection = factory.CreateConnection())
            {
                using (var channel = connection.CreateModel())
                {
                    string message = exchangeName;//"News updated";
                    var body = Encoding.Default.GetBytes(message);
                    channel.BasicPublish(exchange: exchangeName,
                            routingKey: "",
                            basicProperties: null,
                            body: body);
                }
            }
        }

        public void PublishMessage(string exchangeName, byte[] body)
        {
            using (var connection = factory.CreateConnection())
            {
                using (var channel = connection.CreateModel())
                {
                    channel.BasicPublish(exchange: exchangeName,
                            routingKey: "",
                            basicProperties: null,
                            body: body);
                }
            }
        }
        
        public void DeleteExchange(string name)
        {
            using (var connection = factory.CreateConnection())
            {
                using (var channel = connection.CreateModel())
                {
                    channel.ExchangeDelete(exchange: name);
                }
            }
        }
    }
}
