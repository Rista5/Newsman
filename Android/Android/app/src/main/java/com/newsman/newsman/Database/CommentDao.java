package com.newsman.newsman.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.newsman.newsman.ServerEntities.Comment;

import java.util.List;

@Dao
public abstract class CommentDao {

    @Query("SELECT * FROM comment")
    public abstract List<Comment> loadComments();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertComment(Comment comment);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void updateComment(Comment comment);

    @Delete
    public abstract void deleteComment(Comment comment);
}
