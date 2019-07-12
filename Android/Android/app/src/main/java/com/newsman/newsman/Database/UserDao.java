package com.newsman.newsman.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.newsman.newsman.ServerEntities.User;

import java.util.List;

@Dao
public abstract class UserDao {

    @Query("SELECT * FROM user WHERE id = :id")
    public abstract LiveData<User> loadUser(int id);

    @Query("SELECT * FROM user")
    public abstract LiveData<List<User>> loadUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertUser(User user);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void updateUser(User user);

    @Delete
    public abstract void deleteUser(User user);
}