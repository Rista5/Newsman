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

    MQClient(String host, Context context){
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
                                if(routes[3].equals("UserDTO")){
                                    sendUserUpdate(body);
                                } else if(routes[3].equals("NewsDTO")){
                                    sendNewsUpdate(body);
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

    // TODO sta mislis da postoji posebna komponenta/ grupa komponenti koje sluze za update, da im se samo prosledi string, parametarski factory za kreiranje odgovarajuceg update-a
    public void sendUserUpdate(byte[] body){
        try {
            JSONObject json = new JSONObject(new String(body));
            final User user = parseUser(json);
            AppExecutors.getInstance().getDatabaseIO().execute(new Runnable() {
                @Override
                public void run() {
                    AppDatabase.getInstance(context).userDao().updateUser(user);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendNewsUpdate(byte[] body){
        try {

            JSONObject jsonObject = new JSONObject(new String(body));
            final News news = parseNews(jsonObject);
            AppExecutors.getInstance().getDatabaseIO().execute(new Runnable() {
                @Override
                public void run() {
                    AppDatabase.getInstance(context).newsDao().updateNews(news);
                }
            });
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
        String postDate = json.getString("postDate");
        return new Comment(commentId, commentContent, user, belongsToNews, postDate);
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

}
