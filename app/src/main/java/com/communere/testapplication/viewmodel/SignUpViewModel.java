package com.communere.testapplication.viewmodel;

import com.communere.testapplication.model.Bean.User;

import io.reactivex.Single;

public interface SignUpViewModel<T> {
    Single<T> signup(User user, String passwordConfirmation);

}
