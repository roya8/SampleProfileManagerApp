package com.communere.testapplication.model.repository;

import com.communere.testapplication.model.Bean.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface UserRepository {

    public Single<Long> insert(User user);

    public Completable update(User user);

    public Completable delete(User user);

    public Completable deleteAllUsers();

    public LiveData<User> getUser(long id);

    public LiveData<List<User>> getAllUsers();

    public Single<User> getUser(String usernameOrEmail, String pass);

    public Single<Integer> getUserCount(String userName);


}
