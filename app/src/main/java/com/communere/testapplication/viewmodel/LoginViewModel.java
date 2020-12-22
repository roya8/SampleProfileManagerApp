package com.communere.testapplication.viewmodel;

import com.communere.testapplication.model.Bean.User;

import io.reactivex.Single;


/*
 T defines the type of response which login method returns, for exp. an object including user's session token,
received from server after successful login
*/

public interface LoginViewModel {

    Single<User> login(String username, String password);

}
