package com.newsman.newsman.ServerEntities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.newsman.newsman.Auxiliary.PictureConverter;
import com.newsman.newsman.R;

import java.util.Date;

public class SimpleNews {
    private int id;
    private String title;
    private String content;
    private Date lastModified;
    private Bitmap backgroundPicture;

    public SimpleNews(int id, String title, String content,
                      Date lastModified, Bitmap background){
        this.id = id;
        this.title = title;
        this.content = content;
        this.lastModified = lastModified;
        this.backgroundPicture = background;
    }


    public static void populateNews(News news, SimpleNews simpleNews){
        news.setId(simpleNews.getId());
        news.setTitle(simpleNews.getTitle());
        news.setContent(simpleNews.getContent());
        news.setLastModified(simpleNews.getLastModified());
    }

    public static SimpleNews getSimpleNews(News news, Context context) {
        Bitmap background;
        if(news.getPictures().size()>0){
            background = PictureConverter
                    .getBitmap(news.getPictures().get(0).getPictureData());
        } else {
            background = BitmapFactory
                    .decodeResource(context.getResources(), R.drawable.mountain);
        }
        return new SimpleNews(
                news.getId(),
                news.getTitle(),
                news.getContent(),
                news.getLastModified(),
                background
        );
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
}
