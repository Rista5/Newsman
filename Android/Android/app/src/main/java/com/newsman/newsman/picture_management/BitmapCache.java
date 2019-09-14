package com.newsman.newsman.picture_management;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

import com.newsman.newsman.R;
import com.newsman.newsman.auxiliary.picture_helpers.PictureConverter;
import com.newsman.newsman.picture_management.ConnectionDI.BitmapRestConnection;
import com.newsman.newsman.picture_management.ConnectionDI.DaggerBitmapCacheComponent;
import com.newsman.newsman.rest_connection.rest_connectors.BitmapConnector;
import com.newsman.newsman.model.db_entities.Picture;
import com.newsman.newsman.thread_management.AppExecutors;

import java.util.List;

import javax.inject.Inject;

public class BitmapCache {
    private LruCache<Integer,BitmapObservable> cache;
    private static BitmapCache _instance = null;
    private static int NonValidIdentifiers = -1;
    private static Bitmap defaultBitmap;
    private BitmapRestConnection restConnection;

    public BitmapCache() {
        int maxSize = (int)Runtime.getRuntime().maxMemory();
//        maxSize = maxSize/2048;
        maxSize = maxSize / (1024*1024*20); //da se odredi max velicina kesa, posto btimap ima promenljivu velicinu
        cache = new LruCache<Integer,BitmapObservable>(maxSize){
//            @Override
//            protected int sizeOf(Integer key, BitmapObservable value) {
//                if(value.getBitmap() != null)
//                    return value.getBitmap().getByteCount()/1024;
//                else
//                    return 0;
//            }
        };
    }

    @Inject
    public BitmapCache(BitmapRestConnection restConnection){
        this();
        this.restConnection = restConnection;
    }

    public static Bitmap getDefaultBitmap(Context context) {
        if(defaultBitmap == null) {
            defaultBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.mountain);
        }
        return defaultBitmap;
    }

    public static BitmapCache getInstance()
    {
        if(_instance == null)
            _instance = DaggerBitmapCacheComponent.create().createBitmapCache();
        return _instance;
    }

    public static int getNonValidIdentifier(){
       NonValidIdentifiers--;
       return NonValidIdentifiers;
    }

    public void setBitmap(int pictureId, int newsId, Bitmap bitmap){
        BitmapObservable cachedBitmap = cache.get(pictureId);
        if(cachedBitmap != null) {
            cachedBitmap.setBitmap(bitmap);
            if(bitmap == null)
                getBitmapFromRest(null,pictureId,newsId);
        }
        else {
            cachedBitmap = new BitmapObservable(bitmap);
            cache.put(pictureId,cachedBitmap);
        }

    }

    public void deleteBitmap(int pictureId){
        if(cache.get(pictureId)!=null)
            cache.remove(pictureId);
    }

    public BitmapObservable getBitmapObservable(Context context, int picId, int newsId){
        BitmapObservable cachedBitmap = cache.get(picId);
        if(cachedBitmap == null){
            cachedBitmap = new BitmapObservable(null);
            cache.put(picId,cachedBitmap);

            getBitmapFromRest(context, picId, newsId);

            return cachedBitmap;
        }
        else{
            if(cachedBitmap.getBitmap() == null)
                getBitmapFromRest(context,picId,newsId);
            return cachedBitmap;
        }
    }

    private void getBitmapFromRest(Context context, int picId, int newsId) {
        if(picId<=0)
            return;
        restConnection.getBitmap(picId,newsId);
    }

    private void putBitmapToRest(int picId, int newsId,Bitmap bitmap){
        restConnection.putBitmap(picId,newsId,bitmap);
    }

    public void loadPicturesInCache(Context context, List<Picture> pictureList){
        for(Picture p : pictureList){
            BitmapObservable observable = cache.get(p.getId());
        }
    }

    public void updateBitmap(Context context, int pictureId, int newsId){
        getBitmapFromRest(context,pictureId,newsId);
    }
}
