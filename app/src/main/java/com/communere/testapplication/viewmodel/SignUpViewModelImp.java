package com.communere.testapplication.viewmodel;

import android.app.Application;
import android.util.Log;

import com.communere.testapplication.exception.LoginException;
import com.communere.testapplication.exception.SignupException;
import com.communere.testapplication.model.Bean.User;
import com.communere.testapplication.model.repository.UserRepository;
import com.communere.testapplication.model.repository.UserRepositoryImp;
import com.communere.testapplication.util.EmailValidator;
import com.communere.testapplication.util.PasswordValidator;
import com.communere.testapplication.util.UsernameValidator;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class SignUpViewModelImp extends AndroidViewModel implements SignUpViewModel {


    private static final String TAG = "SignUpViewModelImp";

    private UserRepository userRepository;


    public SignUpViewModelImp(@NonNull Application application) {
        super(application);
        userRepository = new UserRepositoryImp(application);


    }

    @Override
    public Single<User> signup(User user, String passwordConfirmation){

        return Single.create(new SingleOnSubscribe<User>() {
            @Override
            public void subscribe(SingleEmitter<User> emitter) throws SignupException {

                boolean usernameIsEmpty = UsernameValidator.isEmpty(user.getUsername());
                boolean passwordIsEmpty = PasswordValidator.isEmpty(user.getPassword());
                boolean emailIsEmpty = EmailValidator.isEmpty(user.getEmail());

                boolean passwordIsValid = PasswordValidator.validatePassword(user.getPassword());
                boolean passwordsAreMatched = PasswordValidator.arePasswordsMatched(user.getPassword(), passwordConfirmation);
                boolean emailIsValid = EmailValidator.validateEmail(user.getEmail());

                if(usernameIsEmpty || passwordIsEmpty){
                    emitter.onError(new SignupException("Please fill in username and password, these fields can't be empty."));

                }
                else if(!passwordIsValid){
                    emitter.onError(new SignupException("Password is not valid, it must contain numerical and capital values."));
                }
                else if(!passwordsAreMatched){
                    emitter.onError(new SignupException("Passwords are not matched, please confirm password again."));
                }
                else if(!emailIsEmpty && !emailIsValid) {
                    emitter.onError(new SignupException("Email is not valid."));

                }
                else {

                    //Check if user already exists
                    (userRepository.getUserCount(user.getUsername()))
                            .subscribe(count -> {
                                if(count > 0)
                                    emitter.onError(new SignupException("You can't use this username, it has been already taken."));

                            }, throwable -> {
                                Log.d(TAG, throwable.getMessage());
                            }
                    );

                    //**************************************************************************************************

                    //add user if not exists
                    (userRepository.insert(user))
                            .subscribe(userId -> {
                        user.setId(userId);
                        emitter.onSuccess(user);
                    }, throwable -> {
                        throwable.printStackTrace();
                        emitter.onError(new SignupException("Signup failed."));
                    }
                    );
                }

            }
        });


    }
}
