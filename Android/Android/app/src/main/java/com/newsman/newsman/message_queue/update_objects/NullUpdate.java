package com.newsman.newsman.message_queue.update_objects;

import android.content.Context;

import com.newsman.newsman.message_queue.MessageInfo;

public class NullUpdate extends DBUpdate {

    NullUpdate(MessageInfo info, Context context) {
        super(info, context);
    }

    @Override
    public void update() {

    }
}
