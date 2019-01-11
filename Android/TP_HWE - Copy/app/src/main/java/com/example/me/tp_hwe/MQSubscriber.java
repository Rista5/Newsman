package com.example.me.tp_hwe;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.rabbitmq.client.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;


public class MQSubscriber {

    private String host = null;
    Thread subscribeThread;
    private ConnectionFactory factory;

    MQSubscriber(String host){
        this.host = host;
        factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
    }

    public void subscribe(final Handler userHandler, final Handler newsHandler){
        subscribeThread = new Thread(new Runnable() {
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
                                        if(routes[3].equals("UserDTO")){
                                            sendUserUpdate(userHandler,body);
                                        } else if(routes[3].equals("NewsDTO")){
                                            sendNewsUpdate(newsHandler, body);
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
        });
        subscribeThread.start();
    }

    public void sendUserUpdate(final Handler handler, byte[] body){
        try {
            JSONObject json = new JSONObject(new String(body));
            User user = parseUser(json);
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            msg.setData(bundle);
            handler.sendMessage(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public User parseUser(JSONObject json) throws JSONException{
        return new User(
                Integer.parseInt(json.getString("Id")),
                json.getString("Username")
        );
    }

    public Comment parseComment(JSONObject json) throws JSONException{
        int commentId = json.getInt("Id");
        User user = parseUser(json.getJSONObject("CreatedBy"));
        String commentContent = json.getString("Content");
        int belongsToNews = json.getInt("BelongsToNewsId");
        return new Comment(commentId,commentContent,user,belongsToNews);
    }

    public ArrayList<Comment> parseCommentList(JSONArray jsonArray) throws JSONException{
        ArrayList<Comment> comments = new ArrayList<>();
        for(int j=0;j<jsonArray.length();j++){
            JSONObject singleCommentJson = jsonArray.getJSONObject(j);
            Comment newComment = parseComment(singleCommentJson);
            comments.add(newComment);
        }
        return  comments;
    }

    public News parseNews(JSONObject jsonObject) throws JSONException{
        //JSONObject jsonObject = jsonArray.getJSONObject(i);
        int id = jsonObject.getInt("Id");
        String title = jsonObject.getString("Title");
        String content = jsonObject.getString("Content");
        JSONArray commentsJson = jsonObject.getJSONArray("Comments");
        ArrayList<Comment> comments = parseCommentList(commentsJson);
        String lastModified = jsonObject.getString("LasModified");
        return new News(id,title,content,comments,lastModified);
    }

    public void sendNewsUpdate(final Handler handler, byte[] body){
        try {

            JSONObject jsonObject = new JSONObject(new String(body));
            News news = parseNews(jsonObject);

            Message msg = new Message();
            Bundle bundle = new Bundle();
            //obrati paznju mozda nece da se serializuje List<Comment
            bundle.putSerializable("news", news);//newsArrayList);
            msg.setData(bundle);
            handler.sendMessage(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
