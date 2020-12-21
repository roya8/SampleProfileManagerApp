package com.communere.testapplication.model.localDatabase;

import android.content.Context;

import com.communere.testapplication.model.Bean.User;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    private static UserDatabase instance;

    public abstract UserDao getUserDao();

    public static synchronized UserDatabase getInstance(Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class,
                    "user_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
