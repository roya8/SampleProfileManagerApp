package com.communere.testapplication.model.localDatabase;


import com.communere.testapplication.model.Bean.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insert(User user);

    @Update
    Completable update(User user);

    @Delete
    Completable delete(User user);

    @Query("DELETE FROM user_table")
    Completable deleteAllUsers();

    @Query("SELECT * FROM user_table WHERE id = :userID")
    LiveData<User> getUser(long userID);

    @Query("SELECT * FROM user_table WHERE ( username = :usernameOrEmail  OR email = :usernameOrEmail )  AND password LIKE :pass")
    Single<User> getUser(String usernameOrEmail, String pass);


    @Query("SELECT * FROM user_table")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT COUNT(*) from user_table WHERE username = :userName")
    Single<Integer> getUserCount(String userName);


}
