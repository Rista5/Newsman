package com.newsman.newsman.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import com.newsman.newsman.auxiliary.date_helpers.DateConverter;
import com.newsman.newsman.model.db_entities.Comment;
import com.newsman.newsman.model.db_entities.News;
import com.newsman.newsman.model.db_entities.Picture;
import com.newsman.newsman.model.db_entities.User;

@Database(entities = {News.class, Comment.class, Picture.class}, version = 14, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABSE_NAME = "newsman";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {

        if(sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABSE_NAME)
                        //TODO remove main thread queries, only enabled for debugging purpose
                        //TODO remove destructive migration
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return sInstance;
    }

    public abstract NewsDao newsDao();
    public abstract CommentDao commentDao();
    public abstract PictureDao pictureDao();
}
