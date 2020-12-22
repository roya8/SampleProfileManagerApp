package com.communere.testapplication.viewmodel;

import com.communere.testapplication.model.Bean.User;

import androidx.lifecycle.LiveData;
import io.reactivex.Completable;
import io.reactivex.Single;


/*
 T defines the type of response which login method returns, for exp. an object including user's session token,
received from server after successful login
*/

public interface UserProfileViewModel {

    LiveData<User> getUser(long id);
    Completable removeUser(User user);
    Completable updateUser(User user);

}
