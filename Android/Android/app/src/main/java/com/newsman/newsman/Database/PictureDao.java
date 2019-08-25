package com.newsman.newsman.Database;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;

import com.newsman.newsman.Auxiliary.PictureConverter;
import com.newsman.newsman.Auxiliary.PictureLoader;
import com.newsman.newsman.ServerEntities.Picture;

import java.util.List;

@Dao
public abstract class PictureDao {

    @Transaction
    @Query("SELECT * FROM picture WHERE belongsToNewsId = :newsId")
    public abstract LiveData<List<Picture>> getPicturesForNews(int newsId);

    public LiveData<List<Picture>> getLoadedPicturesForNews(final Context context, int newsId) {
        LiveData<List<Picture>> livePic = getPicturesForNews(newsId);
        MediatorLiveData<List<Picture>> mediator = new MediatorLiveData<>();
        mediator.addSource(livePic, new Observer<List<Picture>>() {
            @Override
            public void onChanged(@Nullable List<Picture> pictures) {
                for(Picture p: pictures) {
                    PictureLoader.loadPictureData(context, p.getId());
                }
            }
        });
        return mediator;
    }

    @Transaction
    @Query("SELECT * FROM picture WHERE id = :id")
    public abstract LiveData<Picture> getPicture(int id);

    @Transaction
    @Query("SELECT * FROM picture WHERE id = :id")
    public abstract Picture getPictureNonLive(int id);

    @Transaction
    @Query("SELECT * FROM picture WHERE belongsToNewsId = :newsId")
    public abstract List<Picture> getPicturesForNewsNonLive(int newsId);

    public List<Picture> getPicturesForNewsLoaded(Context context, int newsId) {
        List<Picture> pictures = getPicturesForNewsNonLive(newsId);
        for(Picture p: pictures) {
            Bitmap bmp = PictureLoader.loadPictureData(context, p.getId());
            if(bmp != null)
                p.setPictureData(bmp);
        }
        return pictures;
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertPicture(Picture picture);

    public void insertPictureWithLoader(Context context, Picture picture) {
        insertPicture(picture);
        Bitmap bmp = picture.getPictureData();
        PictureLoader.savePictureData(context, picture.getId(), bmp);
    }

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void updatePicture(Picture picture);

    public void updatePictureWithLoader(Context context, Picture picture) {
        updatePicture(picture);
        Bitmap bmp = picture.getPictureData();
        PictureLoader.savePictureData(context, picture.getId(), bmp);
    }

    @Delete
    public abstract void deletePicture(Picture picture);

    @Query("DELETE FROM picture WHERE id = :pictureId")
    public abstract void deletePictureById(int pictureId);

    public void deletePictureByIdWithData(Context context, int pictureId) {
        deletePictureById(pictureId);
        PictureLoader.deletePictureData(context, pictureId);
    }

    @Query("DELETE FROM picture WHERE belongsToNewsId = :newsId")
    public abstract void deletePicturesForNews(int newsId);

    public void deletePicturesForNewsWithData(Context context, int newsId) {
        List<Picture> pics = getPicturesForNewsNonLive(newsId);
        for(Picture p: pics) {
            PictureLoader.deletePictureData(context, p.getId());
        }
        deletePicturesForNews(newsId);
    }
}
