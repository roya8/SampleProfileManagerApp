package com.communere.testapplication.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.communere.testapplication.R;
import com.communere.testapplication.exception.SignupException;
import com.communere.testapplication.model.Bean.User;
import com.communere.testapplication.util.AlertDialogUtil;
import com.communere.testapplication.util.GlideUtil;
import com.communere.testapplication.util.ToastUtil;
import com.communere.testapplication.viewmodel.SignUpViewModel;
import com.communere.testapplication.viewmodel.SignUpViewModelImp;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignupActivity";

    EditText fullName;
    EditText email;
    EditText username;
    EditText password;
    EditText passwordConfirmation;

    String profileImage;
    com.mikhaellopez.circularimageview.CircularImageView profileImageView;
    ImageView cameraImageView;


    private SignUpViewModel viewModel;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        fullName = findViewById(R.id.full_name_edit_text);
        email = findViewById(R.id.email_edit_text);
        username = findViewById(R.id.username_edit_text);
        password = findViewById(R.id.pass_word_edit_text);
        passwordConfirmation = findViewById(R.id.confirm_pass_word_edit_text);
        profileImageView = findViewById(R.id.profile_image_view);
        cameraImageView = findViewById(R.id.camera_image_view);

        Button signupButton = findViewById(R.id.signup_button);
        Button haveAccountButton = findViewById(R.id.login_button);

        //**************************************************************************************************
        //**************************************************************************************************
        //Buttons onClickListeners
        signupButton.setOnClickListener(this::onClick);
        haveAccountButton.setOnClickListener(this::onClick);
        cameraImageView.setOnClickListener(this::onClick);

        //**************************************************************************************************
        //**************************************************************************************************
        viewModel = ViewModelProviders.of(this).get(SignUpViewModelImp.class);


        //**************************************************************************************************
        //**************************************************************************************************
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup_button:
                User inputUser = new User(fullName.getText().toString(), username.getText().toString(),
                        email.getText().toString(), password.getText().toString(), profileImage);

                viewModel.signup(inputUser, passwordConfirmation.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(user -> {
                                    Log.d(TAG, user.toString());
                                    goToUserProfileActivity(user.getId());

                                }, exception ->
                                        AlertDialogUtil.showSimpleAlertDialog(SignupActivity
                                                .this, ((SignupException) exception).getMessage())
                        );
                break;


            case R.id.login_button:
                goToLoginActivity();
                break;


            case R.id.camera_image_view:
                checkPermissionAndSelectImage();
                break;

        }

    }

    private void goToLoginActivity() {
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
        SignupActivity.this.finish();
    }

    private void goToUserProfileActivity(long userId) {
        Intent intent = new Intent(SignupActivity.this, EditUserProfileActivity.class);
        intent.putExtra("id", userId);
        startActivity(intent);
        SignupActivity.this.finish();
    }


    //**************************************************************************************************
    //**************************************************************************************************
    //Setting profile image start
    private static int SELECT_PICTURE = 1;
    private static int REQUEST_READ_STORAGE_PERMISSION = 2;

    void getImageFromGallery() {

        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, SELECT_PICTURE);
    }

    //**************************************************************************************************
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                if (data != null) {
                    Log.d(TAG, "manageFileMsg: REQUEST_GALLERY, data is not null");


                    Bitmap bm = null;
                    if (data != null) {
                        try {
                            bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    //This way of loading image is not memory efficient, Using libraries like glide would be a better solution
                    profileImageView.setImageBitmap(bm);

                }

            }
        }
    }

    //**************************************************************************************************


    //Check runtime permission
    public void checkPermissionAndSelectImage(){

        if (Build.VERSION.SDK_INT >= 23) {
            Log.d(TAG, "Build.VERSION.SDK_INT >= 23");

            if (ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "NOT Permission Granted");

                ActivityCompat.requestPermissions(this,
                        new String[]{READ_EXTERNAL_STORAGE},
                        REQUEST_READ_STORAGE_PERMISSION);
                return;
            } else {
                Log.d(TAG, "Permission Granted");

                getImageFromGallery();
            }
        } else {
            Log.d(TAG, "Build.VERSION.SDK_INT < 23");
            getImageFromGallery();
        }

    }

    //**************************************************************************************************

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

            if(requestCode == REQUEST_READ_STORAGE_PERMISSION)

                if (grantResults.length > 0) {

                    boolean readPermissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (readPermissionGranted)
                        getImageFromGallery();
                    else
                        AlertDialogUtil.showSimpleAlertDialog(this, "Permission denied, you cannot access images");


                }

    }

    //Setting profile image end

    //**************************************************************************************************
    //**************************************************************************************************

}