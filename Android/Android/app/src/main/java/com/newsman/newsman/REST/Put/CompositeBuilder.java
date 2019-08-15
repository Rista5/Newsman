package com.newsman.newsman.REST.Put;

import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.REST.Put.ConnectionStrategy.Delete;
import com.newsman.newsman.REST.Put.ConnectionStrategy.Post;
import com.newsman.newsman.REST.Put.ConnectionStrategy.Put;
import com.newsman.newsman.REST.Put.WriteJson.WritePicture;
import com.newsman.newsman.REST.Put.WriteJson.WriteSimpleNews;
import com.newsman.newsman.ServerEntities.Picture;
import com.newsman.newsman.ServerEntities.SimpleNews;

public class CompositeBuilder extends UpdateBuilder {

    private SendComposite composite;

    @Override
    public void newBuild() {
        composite = new SendComposite();
    }

    @Override
    public void createNews(SimpleNews news) {
        composite.add(new RestConnector(new Post(new WriteSimpleNews(news)), Constant.NEWS_ROUTE));
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
    public SendComponent getResult() {
        return composite;
    }
}
