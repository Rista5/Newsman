package com.newsman.newsman.picture_management;

import android.graphics.Bitmap;

import java.util.Observable;
import java.util.Observer;

public class BitmapObservable extends Observable {
    private Bitmap bitmap;
    private int newsId;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.setChanged();
        this.notifyObservers(this.bitmap);
    }

    public void setNewsId(int newsId){
        this.newsId = newsId;
    }

    public int getNewsId(){
        return this.newsId;
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
        o.update(this, bitmap);
    }
}
