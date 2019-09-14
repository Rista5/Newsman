package com.newsman.newsman.message_queue;

import android.content.Context;

import com.newsman.newsman.auxiliary.notification.NotificationHelper;
import com.newsman.newsman.message_queue.update_objects.DBUpdate;
import com.newsman.newsman.message_queue.update_objects.DBUpdateFactory;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class NewsUpdateConsumer extends DefaultConsumer {
    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    private Context context;
    public NewsUpdateConsumer(Channel channel, Context context) {
        super(channel);
        this.context = context;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String routingKey = envelope.getRoutingKey();
        // route: News.1.Update.Picture.1
        String[] routes = routingKey.split("\\.");
        String data = new String(body);
        if(data.equals("")) return;
        MessageInfo info = new MessageInfo(Integer.parseInt(routes[1]),
                Integer.parseInt(routes[4]), routes[2], data);
        DBUpdate updateDB = DBUpdateFactory.createInstance(routes[3], info, context);
        updateDB.update();
        NotificationHelper.newsUpdateNotification(
                context,
                Integer.parseInt(routes[1]),
                getNotificationText(routes[2], routes[3]),
                shouldStartActivity(routes[2], routes[3])
        );
    }

    private String getNotificationText(String operation, String object) {
        String result = "";
        switch(operation) {
            case MQClient.opInsert:
                if(object.equals("NewsDTO") || object.equals("SimpleNewsDTO"))
                    result = "New news";
                else
                    result = "News updated";
                break;
            case MQClient.opUpdate:
                result = "News updated";
                break;
            case MQClient.opDelete:
                if(object.equals("NewsDTO"))
                    result = "News deleted";
                else
                    result = "News updated";
                break;
                default:
                    result = "Newsman";
                    break;
        }
        return result;
    }

    private boolean shouldStartActivity(String operation, String object) {
        boolean result = false;
        switch (operation){
            case MQClient.opInsert:
                result = true;
                break;
            case MQClient.opUpdate:
                result = true;
                break;
            case MQClient.opDelete:
                result = !object.equals("NewsDTO") && !object.equals("SimpleNewsDTO");
                break;
            default:
                result = false;
                break;
        }
        return result;
    }

}
