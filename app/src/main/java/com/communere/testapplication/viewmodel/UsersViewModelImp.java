package com.communere.testapplication.viewmodel;

import android.app.Application;

import com.communere.testapplication.model.Bean.User;
import com.communere.testapplication.model.repository.UserRepository;
import com.communere.testapplication.model.repository.UserRepositoryImp;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import io.reactivex.Completable;
import io.reactivex.Single;

public class UsersViewModelImp extends AndroidViewModel implements UsersViewModel {


    private UserRepository userRepository;
    private LiveData<List<User>> userList;


    public UsersViewModelImp(@NonNull Application application) {
        super(application);
        userRepository = new UserRepositoryImp(application);
        userList = userRepository.getAllUsers();
    }

    public Completable removeUser(User toDo) {
        return userRepository.delete(toDo);

    }

    public LiveData<List<User>> getUserList(){
        return userList;
    }

    @Override
    public LiveData<User> getUser(long id) {
        return userRepository.getUser(id);
    }


}
