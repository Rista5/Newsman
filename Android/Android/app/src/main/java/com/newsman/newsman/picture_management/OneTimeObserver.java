package com.newsman.newsman.picture_management;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.Observable;
import java.util.Observer;

public class OneTimeObserver implements Observer {

    private ImageView imageView;

    public OneTimeObserver(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Bitmap) {
            this.imageView.setImageBitmap((Bitmap)arg);
            o.deleteObserver(this);
        }
    }
}
