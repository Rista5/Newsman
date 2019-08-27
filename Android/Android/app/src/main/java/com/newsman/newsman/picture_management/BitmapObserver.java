package com.newsman.newsman.picture_management;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.Observable;
import java.util.Observer;

public class BitmapObserver implements Observer {
    private ImageView imageView;

    private BitmapObserver() {}
    public BitmapObserver(ImageView imageView)
    {
        this.imageView = imageView;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg != null && arg instanceof Bitmap) {
            this.imageView.setImageBitmap((Bitmap) arg);
        }
    }
}
