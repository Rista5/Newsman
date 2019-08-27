package com.newsman.newsman.thread_management;

import android.content.Context;

import com.newsman.newsman.message_queue.MQClient;

public class MQClientThread {
    private static Thread instance;

    static void startNewInstance(String host, Context context, int[] newsIds){
        if(instance != null)
            interruptInstance();
        instance = new Thread(new MQClient(host, context, newsIds), "RabbitMQ_Client");
        instance.start();
    }

    public static Thread getInstance(){
        return instance;
    }

    private static void interruptInstance(){
        if(instance != null && instance.isAlive()) {
            instance.interrupt();
        }
    }

    static void deleteThread() {
        interruptInstance();
        instance = null;
    }

}
