package com.newsman.newsman.REST;

import com.newsman.newsman.ServerEntities.Picture;
import com.newsman.newsman.ServerEntities.SimpleNews;

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
