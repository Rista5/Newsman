package com.newsman.newsman.message_queue.update_objects;

import android.content.Context;

import com.newsman.newsman.message_queue.MQClient;
import com.newsman.newsman.message_queue.MessageInfo;

import org.json.JSONException;
import org.json.JSONObject;

import static com.newsman.newsman.message_queue.update_objects.JSONParser.parsePicture;

public class UpdatePictureRaw extends DBUpdate{

    private int pictureId;
    private int newsId;
    UpdatePictureRaw(MessageInfo info, Context context) throws JSONException {
        super(info, context);
        if(info.getJsonObject() != null) {
            JSONObject obj = info.getJsonObject();
            this.pictureId = obj.getInt("PictureId");
            this.newsId = obj.getInt("NewsId");
        }
    }
    @Override
    public void update() {
        switch (messageInfo.getOperation()){
            case MQClient.opRawPitctureUpdate:
                break;
        }
    }
}
