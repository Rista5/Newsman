package com.newsman.newsman.picture_management;

import android.content.Context;

public class InParam{
    private int pictureId;
    private int newsId;
    private Context context;

    public InParam(int pictureId, int newsId, Context context) {
        this.pictureId = pictureId;
        this.newsId = newsId;
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public int getPictureId() {
        return pictureId;
    }

    public int getNewsId() {
        return newsId;
    }
}

