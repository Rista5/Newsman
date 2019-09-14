package com.newsman.newsman.model.db_entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.auxiliary.picture_helpers.PictureLoader;
import com.newsman.newsman.R;

import java.util.Date;

public class SimpleNews {
    private int id;
    private String title;
    private String content;
    private Date lastModified;
    private Bitmap backgroundPicture;
    private int backgroundId;
    private int subscribed;
    private int modifierId;
    private String modifierUsername;

    public SimpleNews(int id, String title, String content, Date lastModified, Bitmap background,
                      int backgroundId, int modifierId, String modifierUsername){
        this.id = id;
        this.title = title;
        this.content = content;
        this.lastModified = lastModified;
        this.backgroundPicture = background;
        this.backgroundId = backgroundId;
        this.modifierId = modifierId;
        this.modifierUsername = modifierUsername;
        this.subscribed = Constant.UNSUBSCRIBED;
    }

    public static void populateNews(News news, SimpleNews simpleNews){
        news.setId(simpleNews.getId());
        news.setTitle(simpleNews.getTitle());
        news.setContent(simpleNews.getContent());
        news.setLastModified(simpleNews.getLastModified());
        news.setBackgroundId(simpleNews.getBackgroundId());
        news.setSubscribed(simpleNews.subscribed);
        news.setModifierId(simpleNews.getModifierId());
        news.setModifierUsername(simpleNews.getModifierUsername());
    }

    public static SimpleNews getSimpleNews(News news, Context context) {
        Bitmap background = null;
//        if(news.getBackgroundId() != Constant.INVALID_PICTURE_ID){
//            background = PictureLoader.loadPictureData(context, news.getBackgroundId());
//        } else {
//            background = BitmapFactory.decodeResource(context.getResources(), R.drawable.mountain);
//        }
        SimpleNews result = new SimpleNews(
                news.getId(),
                news.getTitle(),
                news.getContent(),
                news.getLastModified(),
                background,
                news.getBackgroundId(),
                news.getModifierId(),
                news.getModifierUsername());
        result.subscribed = news.getSubscribed();
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Bitmap getBackgroundPicture() {
        return backgroundPicture;
    }

    public void setBackgroundPicture(Bitmap backgroundPicture) {
        this.backgroundPicture = backgroundPicture;
    }

    public int getBackgroundId() {
        return backgroundId;
    }

    public void setBackgroundId(int backgroundId) {
        this.backgroundId = backgroundId;
    }

    public int getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(int subscribed) {
        this.subscribed = subscribed;
    }

    public int getModifierId() {
        return modifierId;
    }

    public void setModifierId(int modifierId) {
        this.modifierId = modifierId;
    }

    public String getModifierUsername() {
        return modifierUsername;
    }

    public void setModifierUsername(String modifierUsername) {
        this.modifierUsername = modifierUsername;
    }
}
