package com.newsman.newsman.rest_connection;

import android.content.Context;

import com.newsman.newsman.model.db_entities.Comment;
import com.newsman.newsman.model.db_entities.Picture;
import com.newsman.newsman.model.db_entities.SimpleNews;

public abstract class UpdateBuilder {

    protected Context context;
    public UpdateBuilder(Context context) {
        this.context = context;
        newBuild();
    }

    public abstract void newBuild();
    public abstract void createNews(SimpleNews news);
    public abstract void addPicture(Picture picture);
    public abstract void updatePicture(Picture picture);
    public abstract void deletePicture(Picture picture);
    public abstract void addComment(Comment comment);
    public abstract void deleteComment(Comment comment);

    public abstract Runnable getResult();
}
