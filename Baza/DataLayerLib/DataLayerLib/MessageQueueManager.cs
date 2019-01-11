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
    // predlog za routing - News.newsId.Update/Delete.Obj.objId
    public class MessageQueueManager
    {
        private static MessageQueueManager _instance;
        private static object objLock = new object();
        private ConnectionFactory factory;
        private IConnection connection;
        private IModel channel;

        static string host = "localhost";
        static string exchangeName = "Newsman";

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
            connection = factory.CreateConnection();
            channel = connection.CreateModel();
            
            channel.ExchangeDeclare(exchange: exchangeName, type: "topic");
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

        public void PublishMessage(string routingKey, byte[] data)
        {
            channel.BasicPublish(exchange: exchangeName,
                    routingKey: routingKey,
                    basicProperties: null,
                    body: data);
        }

        public void PublishMessage(int newsId,int objId,  object obj,bool delete)
        {
            string routingKey = GenerateRoutingKey(newsId, objId, obj, delete);
            byte[] data = SerializeObject(obj);
            PublishMessage(routingKey, data);
        }

        public static string GenerateRoutingKey(int newsId, int objId, object obj, bool delete = false)
        {
            string result = "";
            result = "News." + newsId + ".";
            if (delete)
                result += "Delete.";
            else
                result += "Update.";
            result += obj.GetType().Name + "." + objId;
            return result;
        }

        public static byte[] SerializeObject (object obj)
        {
            byte[] result = null;
            string json = Newtonsoft.Json.JsonConvert.SerializeObject(obj);
            result = Encoding.Default.GetBytes(json);
            return result;
        }


        //public void DeclareExchange(string name)
        //{
        //    using (var connection = factory.CreateConnection())
        //    {
        //        using (var channel = connection.CreateModel())
        //        {
        //            channel.ExchangeDeclare(exchange: name, type: "topic");
        //        }
        //    }
        //}

        //public void DeclareExchange(string name,string type)
        //{
        //    using (var connection = factory.CreateConnection())
        //    {
        //        using (var channel = connection.CreateModel())
        //        {
        //            channel.ExchangeDeclare(exchange: name, type: type);
        //        }
        //    }
        //}

        //public void PublishMessage(string exchangeName)
        //{
        //    using (var connection = factory.CreateConnection())
        //    {
        //        using (var channel = connection.CreateModel())
        //        {
        //            string message = exchangeName;//"News updated";
        //            var body = Encoding.Default.GetBytes(message);
        //            channel.BasicPublish(exchange: exchangeName,
        //                    routingKey: "",
        //                    basicProperties: null,
        //                    body: body);
        //        }
        //    }
        //}

        //public void PublishMessage(string exchangeName, byte[] body)
        //{
        //    using (var connection = factory.CreateConnection())
        //    {
        //        using (var channel = connection.CreateModel())
        //        {
        //            channel.BasicPublish(exchange: exchangeName,
        //                    routingKey: "",
        //                    basicProperties: null,
        //                    body: body);
        //        }
        //    }
        //}
        
        //public void DeleteExchange(string name)
        //{
        //    using (var connection = factory.CreateConnection())
        //    {
        //        using (var channel = connection.CreateModel())
        //        {
        //            channel.ExchangeDelete(exchange: name);
        //        }
        //    }
        //}
    }
}
