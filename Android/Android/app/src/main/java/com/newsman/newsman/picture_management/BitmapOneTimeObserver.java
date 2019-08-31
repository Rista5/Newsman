package com.newsman.newsman.picture_management;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.Observable;
import java.util.Observer;

public class BitmapOneTimeObserver implements Observer {
    private ImageView imageView;

    private BitmapOneTimeObserver() {}
    public BitmapOneTimeObserver(ImageView imageView)
    {
        this.imageView = imageView;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Bitmap) {
            this.imageView.setImageBitmap((Bitmap) arg);
            o.deleteObserver(this);
        }
    }
}
