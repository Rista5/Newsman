package com.newsman.newsman.Database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.newsman.newsman.ServerEntities.Comment;
import com.newsman.newsman.ServerEntities.News;
import com.newsman.newsman.ServerEntities.User;

import java.util.List;

@Dao
public abstract class NewsDao {

    @Transaction
    @Query("SELECT * FROM news WHERE id = :id")
    public abstract LiveData<News> loadNewsById(int id);

    @Transaction
    @Query("SELECT * FROM news ORDER BY lastModified DESC")
    public abstract LiveData<List<News>> loadAllNews();

    @Transaction
    @Query("SELECT * FROM news WHERE id = :id")
    public abstract News loadNewsByIdNonLive(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertNews(News news);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void updateNews(News news);

    @Delete
    public abstract void deleteNews(News news);

}
