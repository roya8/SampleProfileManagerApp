package com.communere.testapplication.util;

import android.text.TextUtils;
import android.util.Patterns;

public class EmailValidator {

    public static boolean validateEmail(String email){
        return (!isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }


    public static boolean isEmpty(String email){
        return TextUtils.isEmpty(email);
    }

}
