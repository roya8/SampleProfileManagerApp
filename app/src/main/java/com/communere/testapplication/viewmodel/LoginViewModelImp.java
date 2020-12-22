package com.communere.testapplication.viewmodel;

import android.app.Application;
import android.util.Log;

import com.communere.testapplication.exception.LoginException;
import com.communere.testapplication.model.Bean.User;
import com.communere.testapplication.model.repository.UserRepository;
import com.communere.testapplication.model.repository.UserRepositoryImp;
import com.communere.testapplication.util.PasswordValidator;
import com.communere.testapplication.util.UsernameValidator;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModelImp extends AndroidViewModel implements LoginViewModel<User> {


    private UserRepository userRepository;


    public LoginViewModelImp(@NonNull Application application) {
        super(application);
        userRepository = new UserRepositoryImp(application);


    }

    @Override
    public Single<User> login(String usernameOREmail, String password){

        return Single.create(new SingleOnSubscribe<User>() {
            @Override
            public void subscribe(SingleEmitter<User> emitter) throws LoginException {

                boolean usernameIsEmpty = UsernameValidator.isEmpty(usernameOREmail);
                boolean passwordIsEmpty = PasswordValidator.isEmpty(usernameOREmail);
                boolean usernameIsValid = UsernameValidator.validateUsername(usernameOREmail);
                boolean passwordIsValid = PasswordValidator.validatePassword(password);

                if(usernameIsEmpty || passwordIsEmpty){
                    emitter.onError(new LoginException("Pleade enter your username/email and password, these fields can't be empty."));

                }
                else if(!usernameIsValid){
                    emitter.onError(new LoginException("Enter  username or email, this field can't be empty."));

                }
                else if(!passwordIsValid){
                    emitter.onError(new LoginException("Password is not valid."));

                }
                else {

                    (userRepository.getUser(usernameOREmail, password)).subscribe(user -> {
                        emitter.onSuccess(user);
                    }, throwable -> emitter.onError(new LoginException("User does not exist, Please sign up first then try again.")));
                }

            }
        });


    }
}
