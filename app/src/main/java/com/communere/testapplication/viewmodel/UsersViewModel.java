package com.communere.testapplication.viewmodel;

import com.communere.testapplication.model.Bean.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import io.reactivex.Completable;

interface UsersViewModel {


    LiveData<List<User>> getUserList();

    LiveData<User> getUser(long id);
    Completable addUser(User user);
    Completable removeUser(User user);
    Completable updateUser(User user);

}
