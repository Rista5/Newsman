package com.newsman.newsman.message_queue;

import android.content.Context;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MQClient implements Runnable {

    final static String opInsert = "Insert";
    final static String opUpdate = "Update";
    final static String opDelete = "Delete";

    private String host = null;
    private ConnectionFactory factory;
    private Context context;

    public MQClient(String host, Context context){
        this.host = host;
        this.context = context;
        factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
    }

    @Override
    public void run() {
        while(true){
            try {
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();
                channel.exchangeDeclare("Newsman", "topic");
                AMQP.Queue.DeclareOk q = channel.queueDeclare();
                channel.queueBind(q.getQueue(),"Newsman","#");
                channel.basicConsume(q.getQueue(),true,
                        new DefaultConsumer(channel){
                            @Override
                            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                                String routingKey = envelope.getRoutingKey();
                                // route: News.1.Update.Picture.1
                                String[] routes = routingKey.split("\\.");
                                try {
                                    JSONObject jsonObject = null;
                                    String data = new String(body);
                                    if(!data.equals(""))
                                        jsonObject = new JSONObject(data);
                                    MessageInfo info = new MessageInfo(Integer.parseInt(routes[1]),
                                            Integer.parseInt(routes[4]), routes[2], jsonObject);
                                    DBUpdate updateDB = DBUpdateFactory.createInstance(routes[3],
                                            info, context);
                                    updateDB.update();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                while(true){
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

}
