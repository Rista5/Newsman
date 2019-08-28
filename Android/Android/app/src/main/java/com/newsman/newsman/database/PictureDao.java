package com.newsman.newsman.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import com.newsman.newsman.auxiliary.PictureLoader;
import com.newsman.newsman.server_entities.Picture;

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
                if(pictures == null) return;
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
