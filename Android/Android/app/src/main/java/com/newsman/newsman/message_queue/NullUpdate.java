package com.newsman.newsman.message_queue;

import android.content.Context;

import org.json.JSONObject;

public class NullUpdate extends DBUpdate {

    NullUpdate(MessageInfo info, Context context) {
        super(info, context);
    }

    @Override
    public void update() {

    }
}
