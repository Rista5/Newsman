package com.newsman.newsman.Database;

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


    public News getNewsById(int newsId) {
        News news = loadNewsById(newsId);
        populateNews(news);
        return news;
    }

    private void populateNews(News news) {
        news.setComments(getCommentsForNews(news.getId()));
    }

    public List<News> getAllNews() {
        List<News> newsList = loadNews();
        for(News news: newsList) {
            populateNews(news);
        }
        return newsList;
    }

    public List<Comment> getCommentsForNews(int newsId) {
        List<Comment> comments = loadCommentsForNews(newsId);
        for(Comment comment: comments){
            comment.setCreatedBy(loadUserById(comment.getCreatedById()));
        }
        return comments;
    }

    public void saveNews(News news) {
        insertNews(news);
        List<Comment> comments = news.getComments();
        if (comments != null ) {
            saveCommentList(comments);
        }
    }

    public void saveComment(Comment comment) {
        if (comment.getCreatedBy() != null) {
            insertUser(comment.getCreatedBy());
        }
        insertComment(comment);
    }

    public void saveCommentList(List<Comment> commentList) {
        for(Comment comment: commentList) {
            saveComment(comment);
        }
    }

    @Transaction
    @Query("SELECT * FROM news WHERE id = :id")
    abstract News loadNewsById(int id);

    @Transaction
    @Query("SELECT * FROM news ORDER BY lastModified")
    public abstract List<News> loadNews();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertNews(News news);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void updateNews(News news);

    @Delete
    public abstract void deleteNews(News news);

    // helper methods
    @Query("SELECT * FROM comment WHERE belongsToNewsId = :newsId")
    public abstract List<Comment> loadCommentsForNews(int newsId);

    @Query("SELECT * FROM user WHERE id = :userId")
    abstract User loadUserById(int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertComment(Comment comment);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertUser(User user);

    //TODO thik about injecting dependency to other Dao objects insted of copy code
}
