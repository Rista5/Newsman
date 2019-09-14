package com.newsman.newsman.message_queue;

import android.content.Context;

import com.newsman.newsman.message_queue.update_objects.DBUpdate;
import com.newsman.newsman.message_queue.update_objects.DBUpdateFactory;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class MQClient {

    public final static String opInsert = "Insert";
    public final static String opUpdate = "Update";
    public final static String opDelete = "Delete";
    public final static String opRawPitctureUpdate = "RawPictureUpdate";

    public static final String EXCHANGE_NAME = "Newsman";
    public static final String EXCHAMGE_TYPE = "topic";

    private String host = null;
    private ConnectionFactory factory;
    private Context context;
    private Connection connection;
    private Channel channel;
    private String queue;
    private String consumerTag;
    private List<Integer> newsIds;
    private List<Integer> tempSubscription;

    public MQClient(String host, Context context){
        this.host = host;
        this.context = context;
        this.newsIds = new ArrayList<>();
        this.tempSubscription = new ArrayList<>();
        initConnFactory();
    }

    public void startService(int[] newsIds) {
        try{
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHAMGE_TYPE);
            AMQP.Queue.DeclareOk q = channel.queueDeclare();
            queue = q.getQueue();
            bindChannelTopic(newsIds);
            consumerTag = channel.basicConsume(q.getQueue(),true,
                    new NewsUpdateConsumer(channel, context));

        }catch (Exception e) {
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

    public void stopService() {
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

    public void subscribeToNews(int newsId) throws IOException {
        if(channel == null || !channel.isOpen())
            return;
        newsIds.add(newsId);
        String routeKey = getRoutingKey(newsId);
        channel.queueBind(queue, EXCHANGE_NAME, routeKey);
    }

    public void unsubscribeFromNews(int newsId) throws IOException {
        if(channel == null || !channel.isOpen())
            return;
        newsIds.remove(Integer.valueOf(newsId));
        String routeKey = getRoutingKey(newsId);
        channel.queueUnbind(queue, EXCHANGE_NAME, routeKey);
    }

    public void subscribeTempNews(int newsId) throws IOException {
        if(channel == null || !channel.isOpen())
            return;
        if(!tempSubscription.contains(newsId))
            tempSubscription.add(newsId);
        if(newsIds.contains(newsId)) return;
        String routeKey = getRoutingKey(newsId);
        channel.queueBind(queue, EXCHANGE_NAME, routeKey);
    }

    public void unsubscribeFromTempNews(int newsId) throws IOException {
        if(channel == null || !channel.isOpen())
            return;
        if(tempSubscription.contains(newsId))
            tempSubscription.remove(Integer.valueOf(newsId));
        if(newsIds.contains(newsId)) return;
        String routeKey = getRoutingKey(newsId);
        channel.queueUnbind(queue, EXCHANGE_NAME, routeKey);
    }

    private void initConnFactory() {
        factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
    }

    private void bindChannelTopic(int[] newsIds) throws IOException {
        for(int i: newsIds) {
            subscribeToNews(i);
        }
        String newsInsert = "News.*."+ opInsert + ".NewsDTO.#";
        String newsDelete = "News.*."+ opDelete+ ".NewsDTO.#";
        String updateSimpleNews = "News.*."+ opUpdate+ ".SimpleNewsDTO.#";
        channel.queueBind(queue, EXCHANGE_NAME, newsInsert);
        channel.queueBind(queue, EXCHANGE_NAME, newsDelete);
        channel.queueBind(queue, EXCHANGE_NAME, updateSimpleNews);
    }

    private static String getRoutingKey(int newsId) {
        return "News."+newsId+".#";
    }
}
