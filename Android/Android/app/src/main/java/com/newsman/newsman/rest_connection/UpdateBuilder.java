package com.newsman.newsman.rest_connection;

import com.newsman.newsman.server_entities.Picture;
import com.newsman.newsman.server_entities.SimpleNews;

public abstract class UpdateBuilder {

    public UpdateBuilder() {
        newBuild();
    }

    public abstract void newBuild();
    public abstract void createNews(SimpleNews news);
    public abstract void addPicture(Picture picture);
    public abstract void updatePicture(Picture picture);
    public abstract void deletePicture(Picture picture);

    public abstract Runnable getResult();
}
