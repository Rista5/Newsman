package com.newsman.newsman.thread_management;

import android.content.Context;

import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.database.AppDatabase;
import com.newsman.newsman.message_queue.MQClient;

public class ClientSingleton {

    private static MQClient client;

    public static MQClient getClient(Context context) {
        if(client == null) {
            client = new MQClient(Constant.getIpAddress(),context);
        }
        return client;
    }

    public static void removeClient() {
        if(client != null) {
            client.stopService();
            client = null;
        }
    }
}
