package com.communere.testapplication.util;


import android.content.Context;
import android.content.DialogInterface;

import com.communere.testapplication.R;

import androidx.appcompat.app.AlertDialog;

public class AlertDialogUtil {

    public static void showSimpleAlertDialog(Context context, String message){

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(context.getString(R.string.app_name));
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }
}
