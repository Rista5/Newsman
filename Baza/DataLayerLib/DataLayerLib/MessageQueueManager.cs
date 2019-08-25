using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RabbitMQ.Client;
using Newtonsoft.Json;


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

        //TODO razmisli da li ovo moze nekako bolje
        public void PublishMessage(int newsId, int objId,  object obj, MessageOperation operation)
        {
            string routingKey = GenerateRoutingKey(newsId, objId, obj, operation);
            byte[] data = null;
            if(operation!=MessageOperation.Delete)
                data = SerializeObject(obj);
            PublishMessage(routingKey, data);
        }

        public static string GenerateRoutingKey(int newsId, int objId, object obj, MessageOperation operation)
        {
            string result = "";
            result = "News." + newsId + ".";
            switch (operation)
            {
                case MessageOperation.Insert: result += "Insert.";
                    break;
                case MessageOperation.Update: result += "Update.";
                    break;
                case MessageOperation.Delete: result += "Delete.";
                    break;
                default: result += "Update.";
                    break;
            }
            result += obj.GetType().Name + "." + objId;
            return result;
        }

        public static byte[] SerializeObject (object obj)
        {
            byte[] result = null;
            string json = JsonConvert.SerializeObject(obj);
            result = Encoding.Default.GetBytes(json);
            return result;
        }
    }

    public enum MessageOperation
    {
        Insert = 0,
        Update = 1,
        Delete = 2,
    }
}
