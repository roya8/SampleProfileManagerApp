package com.communere.testapplication.model.localDatabase;


import com.communere.testapplication.model.Bean.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Completable;

@Dao
public interface UserDao {

    @Insert
    Completable insert(User user);

    @Update
    Completable update(User user);

    @Delete
    Completable delete(User user);

    @Query("DELETE FROM user_table")
    Completable deleteAllUsers();

    @Query("SELECT * FROM user_table WHERE id = :userID")
    LiveData<User> getUser(long userID);


    @Query("SELECT * FROM user_table")
    LiveData<List<User>> getAllUsers();
}
