package com.newsman.newsman.rest_connection;

import android.content.Context;

import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.rest_connection.ConnectionStrategy.Delete;
import com.newsman.newsman.rest_connection.ConnectionStrategy.Post;
import com.newsman.newsman.rest_connection.ConnectionStrategy.Put;
import com.newsman.newsman.rest_connection.WriteJson.WritePicture;
import com.newsman.newsman.rest_connection.WriteJson.WriteSimpleNews;
import com.newsman.newsman.server_entities.Picture;
import com.newsman.newsman.server_entities.SimpleNews;

public class CompositeBuilder extends UpdateBuilder {

    public CompositeBuilder(Context context) {
        this.context = context;
        composite = new RunnableComposite();
    }

    private Context context;
    private RunnableComposite composite;

    @Override
    public void newBuild() {
        composite = new RunnableComposite();
    }

    @Override
    public void createNews(SimpleNews news) {
        composite.add(new RestConnector(new Post(new WriteSimpleNews(news)), Constant.updateNewsRoute()));
    }

    @Override
    public void addPicture(Picture picture) {
        composite.add(new RestConnector(new Put(context, new WritePicture(picture)), Constant.PICTURE_ROUTE));
    }

    @Override
    public void updatePicture(Picture picture) {
        composite.add(new RestConnector(new Post(new WritePicture(picture)), Constant.PICTURE_ROUTE));
    }

    @Override
    public void deletePicture(Picture picture) {
        composite.add(new RestConnector(new Delete(picture.getId()), Constant.PICTURE_ROUTE));
    }

    @Override
    public Runnable getResult() {
        return composite;
    }
}
