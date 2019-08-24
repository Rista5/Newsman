package com.newsman.newsman.message_queue;

import android.content.Context;

import org.json.JSONException;

public abstract class DBUpdate {

    Context mContext;
    protected MessageInfo messageInfo;


    DBUpdate(MessageInfo info, Context context) {
        mContext = context;
        messageInfo = info;
    }

    public abstract void update();
}
