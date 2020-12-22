package com.communere.testapplication.viewmodel;

import android.app.Application;
import android.util.Log;

import com.communere.testapplication.exception.SignupException;
import com.communere.testapplication.exception.UpdateUserException;
import com.communere.testapplication.model.Bean.User;
import com.communere.testapplication.model.repository.UserRepository;
import com.communere.testapplication.model.repository.UserRepositoryImp;
import com.communere.testapplication.util.AlertDialogUtil;
import com.communere.testapplication.util.ToastUtil;
import com.communere.testapplication.view.activity.EditUserProfileActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserProfileViewModelImp extends AndroidViewModel implements UserProfileViewModel {

    private static final String TAG = "UserProfileViewModelImp";

    private UserRepository userRepository;
//    private LiveData<User> user;


    public UserProfileViewModelImp(@NonNull Application application) {
        super(application);
        userRepository = new UserRepositoryImp(application);
    }

    @Override
    public LiveData<User> getUser(long id) {
        return userRepository.getUser(id);
    }

    @Override
    public Completable removeUser(User user) {
        return userRepository.delete(user);
    }

    @Override
    public Completable updateUser(User user) {

        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws UpdateUserException {
                if (!emitter.isDisposed()) {


                    //Check if user already exists
                    (userRepository.getUserCount(user.getUsername()))
                            .subscribe(count -> {
                                        if(count > 0)
                                            emitter.onError(new UpdateUserException("You can't use this username, it has been already taken."));

                                    }, throwable -> {
                                        Log.d(TAG, throwable.getMessage());
                                    }
                            );

                    //**************************************************************************************************

                    //Update user
                    userRepository.update(user)
                            .subscribeOn(Schedulers.io())
                            .observeOn(Schedulers.io())
                            .subscribe(() -> // completed with success
                                    {

                                        emitter.onComplete();
                                    }
                                    ,
                                    throwable -> // there was an error
                                            emitter.onError(new UpdateUserException("Updating user failed."))

                            );


                }
            }
        });

    }
}
