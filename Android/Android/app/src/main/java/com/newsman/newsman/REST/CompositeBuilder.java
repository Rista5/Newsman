package com.newsman.newsman.REST;

import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.REST.ConnectionStrategy.Delete;
import com.newsman.newsman.REST.ConnectionStrategy.Post;
import com.newsman.newsman.REST.ConnectionStrategy.Put;
import com.newsman.newsman.REST.WriteJson.WritePicture;
import com.newsman.newsman.REST.WriteJson.WriteSimpleNews;
import com.newsman.newsman.ServerEntities.Picture;
import com.newsman.newsman.ServerEntities.SimpleNews;

public class CompositeBuilder extends UpdateBuilder {

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
        composite.add(new RestConnector(new Put(new WritePicture(picture)), Constant.PICTURE_ROUTE));
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
