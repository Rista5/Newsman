package com.newsman.newsman.picture_management;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.Observable;
import java.util.Observer;

public class BitmapObserver implements Observer {

//    final int MAX_WIDTH = 40;
//    final int MAX_HEIGHT = 30;
    private ImageView imageView;
    private BitmapObservable observable;

    public BitmapObserver(BitmapObservable observable, ImageView imageView) {
        this.imageView = imageView;
        this.observable = observable;
        observable.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Bitmap) {
            this.imageView.setImageBitmap((Bitmap) arg);
        } else {
            imageView.setImageBitmap(BitmapCache.getDefaultBitmap(imageView.getContext()));
        }
    }


    public void removeObserver() {
        imageView = null;
        observable.deleteObserver(this);
    }
}
