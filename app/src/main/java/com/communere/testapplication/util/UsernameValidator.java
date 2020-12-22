package com.communere.testapplication.util;

import android.text.TextUtils;

public class UsernameValidator {

    public static boolean validateUsername(String username){
        return !isEmpty(username);
    }

    public static boolean isEmpty(String username){
        return TextUtils.isEmpty(username);
    }
}
