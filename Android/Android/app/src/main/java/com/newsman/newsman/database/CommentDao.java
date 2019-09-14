package com.newsman.newsman.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.newsman.newsman.model.db_entities.Comment;

import java.util.List;

@Dao
public abstract class CommentDao {

    @Query("SELECT * FROM comment WHERE id = :commentId")
    public abstract LiveData<Comment> getCommentById(int commentId);

    @Query("SELECT * FROM comment WHERE belongsToNewsId = :newsId ORDER BY postDate")
    public abstract LiveData<List<Comment>> getCommentsForNews(int newsId);

    @Query("SELECT * FROM comment WHERE id = :commentId")
    public abstract Comment getCommentByIdNonLive(int commentId);

    @Query("SELECT * FROM comment WHERE belongsToNewsId = :newsId ORDER BY postDate")
    public abstract List<Comment> getCommentsForNewsNonLive(int newsId);

    @Query("SELECT * FROM comment")
    public abstract LiveData<List<Comment>> loadComments();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertComment(Comment comment);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void updateComment(Comment comment);

    @Delete
    public abstract void deleteComment(Comment comment);

    @Query("DELETE FROM comment WHERE id = :commentId")
    public abstract void deleteCommentById(int commentId);

    @Query("DELETE FROM comment WHERE belongsToNewsId = :newsId")
    public abstract void deleteCommentsForNews(int newsId);

    @Query("DELETE FROM comment")
    public abstract void deleteAllComments();
}
