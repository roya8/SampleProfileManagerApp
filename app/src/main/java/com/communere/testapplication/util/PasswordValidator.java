package com.communere.testapplication.util;

import android.text.TextUtils;
import android.util.Patterns;

public class PasswordValidator {

    public static boolean validatePassword(String password){
        String pattern = "^(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{8,}$";

        return (!(password == null) && !isEmpty(password) && password.matches(pattern));
    }

    public static boolean arePasswordsMatched(String password, String confirmation){
        return (!(password == null) && !(confirmation == null) && password.equals(confirmation));
    }

    public static boolean isEmpty(String password){
        return TextUtils.isEmpty(password);
    }
}
