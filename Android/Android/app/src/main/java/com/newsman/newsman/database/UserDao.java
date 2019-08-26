package com.newsman.newsman.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.newsman.newsman.server_entities.User;

import java.util.List;

@Dao
public abstract class UserDao {

    @Query("SELECT * FROM user WHERE id = :id")
    public abstract LiveData<User> loadUserById(int id);

    @Query("SELECT * FROM user WHERE id = :id")
    public abstract User getUserByIdNonLive(int id);

    @Query("SELECT * FROM user")
    public abstract LiveData<List<User>> loadUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertUser(User user);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void updateUser(User user);

    @Delete
    public abstract void deleteUser(User user);

    @Query("DELETE FROM user WHERE id = :userId")
    public abstract void deleteUserById(int userId);
}
