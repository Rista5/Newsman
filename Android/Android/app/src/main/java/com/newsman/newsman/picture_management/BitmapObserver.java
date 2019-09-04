package com.newsman.newsman.picture_management;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.Observable;
import java.util.Observer;

public class BitmapObserver implements Observer {
    private ImageView imageView;

    public BitmapObserver(ImageView imageView)
    {
        this.imageView = imageView;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Bitmap) {
            this.imageView.setImageBitmap((Bitmap) arg);
        } else {
            //mozda pravi problem, treba da resetuje view i da se koristi jedna slika
            imageView.setImageBitmap(BitmapCache.getDefaultBitmap(imageView.getContext()));
        }
    }
}
