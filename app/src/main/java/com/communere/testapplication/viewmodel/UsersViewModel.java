package com.communere.testapplication.viewmodel;

import com.communere.testapplication.model.Bean.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import io.reactivex.Completable;
import io.reactivex.Single;

interface UsersViewModel {


    LiveData<List<User>> getUserList();

    LiveData<User> getUser(long id);
    Single<Long> addUser(User user);
    Completable removeUser(User user);
    Completable updateUser(User user);

}
