package com.newsman.newsman.picture_management;

import android.graphics.Bitmap;

import com.newsman.newsman.activities.MainActivity;

import java.util.Observable;
import java.util.Observer;

public class BitmapObservable extends Observable {
    private Bitmap bitmap;

    public BitmapObservable(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.setChanged();
        MainActivity.runOnUI(new Runnable() {
            @Override
            public void run() {
                notifyObservers(bitmap);
            }
        });

    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
        o.update(this, bitmap);
    }
}
