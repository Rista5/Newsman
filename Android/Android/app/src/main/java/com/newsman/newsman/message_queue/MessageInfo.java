package com.newsman.newsman.message_queue;

import org.json.JSONObject;

public class MessageInfo {
    private int newsId;
    private int objectId;
    private String operation;
    private String jsonString;
//    private JSONObject jsonObject;

    public MessageInfo(int newsId, int objectId, String operation, String jsonString){
        this.newsId = newsId;
        this.objectId = objectId;
        this.operation = operation;
        this.jsonString = jsonString;
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

    public String getJsonString() {
        return jsonString;
    }
}
