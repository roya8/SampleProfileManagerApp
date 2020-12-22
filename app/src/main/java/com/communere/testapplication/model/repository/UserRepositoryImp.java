package com.communere.testapplication.model.repository;

import android.app.Application;

import com.communere.testapplication.model.Bean.User;
import com.communere.testapplication.model.localDatabase.UserDao;
import com.communere.testapplication.model.localDatabase.UserDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;
import io.reactivex.Completable;
import io.reactivex.Single;

public class UserRepositoryImp implements UserRepository {

    private LiveData<List<User>> userList;
    UserDao userDao;

    public UserRepositoryImp(Application application){
        UserDatabase userDatabase = UserDatabase.getInstance(application);
        userDao = userDatabase.getUserDao();
        userList = userDao.getAllUsers();
    }

    //**************************************************************************************************
    //**************************************************************************************************
    public Completable insert(User user){

        return userDao.insert(user);
    }

    public Completable update(User user){

        return userDao.update(user);
    }

    public Completable delete(User user){
        return userDao.delete(user);

    }

    public Completable deleteAllUsers(){
        return userDao.deleteAllUsers();

    }

    public LiveData<User> getUser(long id){
        return userDao.getUser(id);

    }

    public LiveData<List<User>> getAllUsers(){
        return userList;
    }


    public Single<User> getUser(String usernameOrEmail, String pass){
        return userDao.getUser(usernameOrEmail, pass);
    }

}
