package com.newsman.newsman.picture_management;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.rest_connection.ConnectionStrategy.GetBitmap;
import com.newsman.newsman.rest_connection.ConnectionStrategy.PutBitmap;
import com.newsman.newsman.rest_connection.RestConnector;
import com.newsman.newsman.server_entities.Picture;

import java.util.List;

public class BitmapCache {
    private LruCache<Integer,BitmapObservable> cache;
    private static BitmapCache _instance = null;
    private static int NonValidIdentifiers = -1;

    public BitmapCache() {
        int maxSize = (int)Runtime.getRuntime().maxMemory();
        maxSize = maxSize/1024/2;
        cache = new LruCache<Integer,BitmapObservable>(maxSize){
          protected int sizeof(Integer id,BitmapObservable bmp){
              return bmp.getBitmap().getByteCount()/1024;
          }
        };
    }

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

    public void setBitmap(int pictureId, int newsId, Bitmap bitmap){
        BitmapObservable cachedBitmap = cache.get(pictureId);
        if(cachedBitmap != null)
            cachedBitmap.setBitmap(bitmap);
        else {
            cachedBitmap = new BitmapObservable();
            cachedBitmap.setBitmap(bitmap);
            cachedBitmap.setNewsId(newsId);
        }

    }

    public void deleteBitmap(int pictureId){
        if(cache.get(pictureId)!=null)
            cache.remove(pictureId);
    }

    public void put(int pictureId){
        BitmapObservable bmpObservable = cache.get(pictureId);
        if(bmpObservable!=null){
            putBitmapToRest(pictureId,bmpObservable.getNewsId(),bmpObservable.getBitmap());
        }
    }

    public BitmapObservable getBitmap(Context context, int picId, int newsId){
        BitmapObservable cachedBitmap = cache.get(picId);
        if(cachedBitmap == null){
            cachedBitmap = new BitmapObservable();
            cachedBitmap.setBitmap(null);
            cachedBitmap.setNewsId(newsId);
            cache.put(picId,cachedBitmap);

            getBitmapFromRest(context, picId, newsId);

            return cachedBitmap;
        }
        else
            return cachedBitmap;
    }

    private void getBitmapFromRest(Context context, int picId, int newsId) {
        new RestConnector(new GetBitmap(picId, newsId, context), Constant.getRawPictureRoute(picId,newsId))
                .execute();
    }

    private void putBitmapToRest(int picId, int newsId, Bitmap bmp){
        new RestConnector(new PutBitmap(bmp), Constant.getRawPictureRoute(picId,newsId))
                .execute();
    }

    public void putBitmapsCreateNews(List<Integer> oldId, List<Integer> newId, int newsId){
        for(int i=0;i<oldId.size();i++){
            BitmapObservable bmp = cache.get(oldId.get(i));
            bmp.setNewsId(newsId);
            cache.remove(oldId.get(i));
            cache.put(newId.get(i),bmp);
            this.putBitmapToRest(newId.get(i),newsId,bmp.getBitmap());
        }
    }

    public void loadPicturesInCache(Context context, List<Picture> pictureList){
        for(Picture p : pictureList){
            loadPictureInCache(context, p.getId(), p.getBelongsToNewsId(), p.getOnDisc());
//            BitmapObservable observable = cache.get(p.getId());
//            if(p.getOnDisc() == Constant.PICTURE_ON_DISC && observable.getBitmap()==null){
//                new LoadPictureTask().execute(new InParam(p.getId(), p.getBelongsToNewsId(), context));
//            }
//            else if(p.getOnDisc() == Constant.PICRURE_NOT_ON_DISC ) {
//                getBitmapFromRest(context, p.getId(), p.getBelongsToNewsId());
//            }
        }
    }

    public void loadPictureInCache(Context context, int pictureId, int newsId, int onDisc) {
        BitmapObservable observable = cache.get(pictureId);
        if(onDisc == Constant.PICTURE_ON_DISC && observable.getBitmap()==null){
            new LoadPictureTask().execute(new InParam(pictureId, newsId, context));
        }
        else if(onDisc == Constant.PICRURE_NOT_ON_DISC ) {
            getBitmapFromRest(context, pictureId, newsId);
        }
    }
}
