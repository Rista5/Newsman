package com.newsman.newsman.message_queue;

import android.content.Context;

import com.newsman.newsman.AppExecutors;
import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.ServerEntities.Comment;
import com.newsman.newsman.ServerEntities.News;
import com.newsman.newsman.ServerEntities.User;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class MQClient implements Runnable {

    private String host = null;
    private ConnectionFactory factory;
    private Context context;

    public MQClient(String host, Context context){
        this.host = host;
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
                                String[] routes = routingKey.split("\\.");
                                try {
                                    UpdateObject updateObject = UpdateFactory.CreateUpdateObject(routes[3], new JSONObject(new String(body)), context);
                                    updateObject.updateRecord();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                while(true){
                    int a =5;
                    a+=3;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

}
