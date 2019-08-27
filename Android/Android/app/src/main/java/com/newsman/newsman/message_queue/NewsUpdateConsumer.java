package com.newsman.newsman.message_queue;

import android.content.Context;

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
        try {
            JSONObject jsonObject = null;
            String data = new String(body);
            if(!data.equals(""))
                jsonObject = new JSONObject(data);
            MessageInfo info = new MessageInfo(Integer.parseInt(routes[1]),
                    Integer.parseInt(routes[4]), routes[2], jsonObject);
            DBUpdate updateDB = DBUpdateFactory.createInstance(routes[3], info, context);
            updateDB.update();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
