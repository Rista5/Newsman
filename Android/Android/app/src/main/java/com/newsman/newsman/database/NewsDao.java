package com.newsman.newsman.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.model.db_entities.News;

import java.util.List;

@Dao
public abstract class NewsDao {

    @Transaction
    @Query("SELECT * FROM news WHERE id = :id")
    public abstract LiveData<News> getNewsById(int id);

    @Transaction
    @Query("SELECT * FROM news ORDER BY lastModified DESC")
    public abstract LiveData<List<News>> getAllNews();

    @Query("SELECT * FROM news ORDER BY lastModified DESC")
    public abstract List<News> getAllNewsNonLive();

    @Transaction
    @Query("SELECT * FROM news WHERE id = :id")
    public abstract News getNewsByIdNonLive(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertNewsReplace(News news);

    public void insertNews(News news) {
        news.setSubscribed(getSubscriptionStatus(news.getId()));
        insertNewsReplace(news);
    }


    public void updateNews(News news) {
        int subscribed = getSubscriptionStatus(news.getId());
        news.setSubscribed(subscribed);
        updateNewsReplace(news);
    }

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract void updateNewsReplace(News news);

    @Delete
    public abstract void deleteNews(News news);

    @Query("DELETE FROM news WHERE id = :newsId")
    public abstract void deleteNewsById(int newsId);

    @Query("DELETE FROM news")
    public abstract void deleteAllNews();

    @Query("SELECT subscribed FROM news WHERE id = :newsId")
    public abstract int getSubscriptionStatus(int newsId);

    @Query("SELECT * FROM news WHERE subscribed = :value")
    abstract LiveData<List<News>> getNewsWhereSubscribed(int value);

    public LiveData<List<News>> getSubscribedNews() {
        return getNewsWhereSubscribed(Constant.SUBSCRIBED);
    }

    @Query("UPDATE news SET subscribed = :value WHERE id = :newsId")
    abstract void setSubscrition(int newsId, int value);

    public void subscribeToNews(int newsId){
        setSubscrition(newsId, Constant.SUBSCRIBED);
    }

    public void unsubscribeFromNews(int newsId) {
        setSubscrition(newsId, Constant.UNSUBSCRIBED);
    }

    @Query("SELECT id FROM news WHERE subscribed = :value")
    abstract int[] getBySubscibeStatus(int value);

    public int[] getSubscribedNewsIds(){
        return getBySubscibeStatus(Constant.SUBSCRIBED);
    }

}
