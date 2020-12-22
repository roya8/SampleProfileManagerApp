package com.communere.testapplication.model.localDatabase;

import android.content.Context;

import com.communere.testapplication.model.Bean.Admin;
import com.communere.testapplication.model.Bean.User;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase{

    private static UserDatabase instance;

    public abstract UserDao getUserDao();

    public static synchronized UserDatabase getInstance(Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class,
                    "user_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(rooCallback)
                    .build();
        }
        return instance;
    }


    private static RoomDatabase.Callback rooCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);


            //Create the admin user and add it to database once database created
//            User adminUser = new User("", Admin.getUsername(),"", Admin.getPassword(), "");
            User adminUser = new User(Admin.getUsername(), Admin.getPassword());
            instance.getUserDao().insert(adminUser);
        }
    };

}
