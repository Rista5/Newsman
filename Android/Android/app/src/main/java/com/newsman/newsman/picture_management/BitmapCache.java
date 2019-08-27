package com.newsman.newsman.picture_management;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.rest_connection.ConnectionStrategy.GetBitmap;
import com.newsman.newsman.rest_connection.ConnectionStrategy.PutBitmap;
import com.newsman.newsman.rest_connection.RestConnector;

import java.util.HashMap;
import java.util.List;

public class BitmapCache {
    private LruCache<Integer,BitmapObservable> cache;
    private static BitmapCache _instance = null;
    private static int NonValidIdentifiers = -1;

    public static BitmapCache getInstance()
    {
        if(_instance == null)
            _instance = new BitmapCache();
        return _instance;
    }

    public static int getNonValidIdentifier(){
       NonValidIdentifiers--;
       return NonValidIdentifiers;
    }

    public void setBitmap(int id, Bitmap bitmap){
        BitmapObservable cachedBitmap = cache.get(id);
        if(cachedBitmap != null)
            cachedBitmap.setBitmap(bitmap);
    }

    public void deleteBitmap(int id){
        if(cache.get(id)!=null)
            cache.remove(id);
    }

    public void Put(int picId){
        BitmapObservable bmpObservable = cache.get(picId);
        if(bmpObservable!=null){
            PutBitmapToRest(picId,bmpObservable.getNewsId(),bmpObservable.getBitmap());
        }
    }

    public BitmapObservable GetBitmap(int picId, int newsId){
        BitmapObservable cachedBitmap = cache.get(picId);
        if(cachedBitmap == null){
            cachedBitmap = new BitmapObservable();
            cachedBitmap.setBitmap(null);
            cachedBitmap.setNewsId(newsId);
            cache.put(picId,cachedBitmap);

            GetBitmapFromRest(picId, newsId);

            return cachedBitmap;
        }
        else
            return cachedBitmap;
    }

    private void GetBitmapFromRest(int picId, int newsId) {
        new RestConnector(new GetBitmap(picId), Constant.getRawPictureRoute(picId,newsId))
                .execute();
    }

    private void PutBitmapToRest(int picId,int newsId, Bitmap bmp){
        new RestConnector(new PutBitmap(bmp), Constant.getRawPictureRoute(picId,newsId))
                .execute();
    }

    public void PutBitmapsCreateNews(List<Integer> oldId, List<Integer> newId, int newsId){
        for(int i=0;i<oldId.size();i++){
            BitmapObservable bmp = cache.get(oldId.get(i));
            bmp.setNewsId(newsId);
            cache.remove(oldId.get(i));
            cache.put(newId.get(i),bmp);
            this.PutBitmapToRest(newId.get(i),newsId,bmp.getBitmap());
        }
    }
}
