package com.newsman.newsman.message_queue;

import org.json.JSONObject;

public class MessageInfo {
    private int newsId;
    private int objectId;
    private String operation;
    private JSONObject jsonObject;

    public MessageInfo(int newsId, int objectId, String operation, JSONObject jsonObject){
        this.newsId = newsId;
        this.objectId = objectId;
        this.operation = operation;
        this.jsonObject = jsonObject;
    }

    public int getNewsId() {
        return newsId;
    }

    public int getObjectId() {
        return objectId;
    }

    public String getOperation() {
        return operation;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }
}
