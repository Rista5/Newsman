package com.newsman.newsman.rest_connection;

import android.content.Context;
import android.graphics.Bitmap;

import com.newsman.newsman.auxiliary.LoginState;
import com.newsman.newsman.rest_connection.rest_connectors.CommentConnector;
import com.newsman.newsman.rest_connection.rest_connectors.NewsConnector;
import com.newsman.newsman.rest_connection.rest_connectors.PictureConnector;
import com.newsman.newsman.picture_management.BitmapCache;
import com.newsman.newsman.model.db_entities.Comment;
import com.newsman.newsman.model.db_entities.Picture;
import com.newsman.newsman.model.db_entities.SimpleNews;

public class CompositeBuilder extends UpdateBuilder {

    private RunnableComposite composite;

    public CompositeBuilder(Context context) {
        super(context);
    }

    @Override
    public void newBuild() {
        composite = new RunnableComposite();
    }

    @Override
    public void createNews(SimpleNews news) {
        composite.add(NewsConnector
                .saveNews(context, LoginState.getInstance().getUserId(), news));
    }

    @Override
    public void addPicture(Picture picture) {
        Bitmap bmp = BitmapCache.getInstance().getBitmapObservable(context, picture.getId(),
                picture.getBelongsToNewsId()).getBitmap();
        composite.add(PictureConnector.savePicture(context, picture, bmp));
    }

    @Override
    public void updatePicture(Picture picture) {
        Bitmap bmp = BitmapCache.getInstance().getBitmapObservable(context, picture.getId(),
                picture.getBelongsToNewsId()).getBitmap();
        composite.add(PictureConnector.savePicture(context, picture, bmp));
    }

    @Override
    public void deletePicture(Picture picture) {
        composite.add(PictureConnector.deletePicture(context, picture.getId()));
    }

    @Override
    public void addComment(Comment comment) {
        composite.add(CommentConnector.saveComment(context, comment));
    }

    @Override
    public void deleteComment(Comment comment) {
        composite.add(CommentConnector.deleteComment(context, comment.getId()));
    }

    @Override
    public Runnable getResult() {
        return composite;
    }
}
