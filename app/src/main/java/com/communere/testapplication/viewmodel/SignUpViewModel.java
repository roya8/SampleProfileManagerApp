package com.communere.testapplication.viewmodel;

import com.communere.testapplication.model.Bean.User;

import io.reactivex.Single;

public interface SignUpViewModel {
    Single<User> signup(User user, String passwordConfirmation);

}
