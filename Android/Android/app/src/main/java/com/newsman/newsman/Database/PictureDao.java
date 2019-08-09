package com.newsman.newsman.Database;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.newsman.newsman.ServerEntities.Picture;

import java.util.List;

@Dao
public abstract class PictureDao {

    @Transaction
    @Query("SELECT * FROM picture WHERE belongsToNewsId = :newsId")
    public abstract LiveData<List<Picture>> getPicturesForNews(int newsId);

    @Transaction
    @Query("SELECT * FROM picture WHERE id = :id")
    public abstract LiveData<Picture> getPicture(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertPicture(Picture picture);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void updatePicture(Picture picture);

    @Delete
    public abstract void deletePicture(Picture picture);
}
