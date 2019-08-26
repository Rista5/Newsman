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

    public static final String EXCHANGE_NAME = "Newsman";
    public static final String EXCHAMGE_TYPE = "topic";

    private String host = null;
    private ConnectionFactory factory;
    private Context context;
    private Connection connection;
    private Channel channel;
    private int[] newsIds;

    public MQClient(String host, Context context, int[] newsIds){
        this.host = host;
        this.context = context;
        this.newsIds = newsIds;
        factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
    }

    @Override
    public void run() {
        try{
            while(!Thread.interrupted()){
                try {
                    connection = factory.newConnection();
                    channel = connection.createChannel();
                    channel.exchangeDeclare(EXCHANGE_NAME, EXCHAMGE_TYPE);
                    AMQP.Queue.DeclareOk q = channel.queueDeclare();
//                channel.queueBind(q.getQueue(),"Newsman","#");
                    bindChannelTopic(channel, q.getQueue());
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
                    while(!Thread.interrupted()){
                        Thread.sleep(5000);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
            if(connection != null && connection.isOpen()){
                try {
                    channel.close();
                    connection.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (TimeoutException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    private void bindChannelTopic(Channel channel, String queue) throws IOException {
        for(int i: newsIds) {
            String routeKey = "News."+i+".#";
            channel.queueBind(queue, EXCHANGE_NAME, routeKey);
        }
        String newsInsert = "News.*."+ opInsert + ".NewsDTO.#";
        String newsDelete = "News.*."+ opDelete+ ".NewsDTO.#";
        String updateSimpleNews = "News.*."+ opUpdate+ ".SimpleNewsDTO.#";
        channel.queueBind(queue, EXCHANGE_NAME, newsInsert);
        channel.queueBind(queue, EXCHANGE_NAME, newsDelete);
        channel.queueBind(queue, EXCHANGE_NAME, updateSimpleNews);
    }

}
