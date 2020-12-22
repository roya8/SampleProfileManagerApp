package com.communere.testapplication.util;

import android.text.TextUtils;
import android.util.Patterns;

public class EmailValidator {

    public static boolean validateEmail(String email){
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
}
