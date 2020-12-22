package com.communere.testapplication.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.communere.testapplication.R;
import com.communere.testapplication.exception.LoginException;
import com.communere.testapplication.exception.SignupException;
import com.communere.testapplication.model.Bean.User;
import com.communere.testapplication.util.AlertDialogUtil;
import com.communere.testapplication.viewmodel.SignUpViewModel;
import com.communere.testapplication.viewmodel.SignUpViewModelImp;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "SignupActivity";

    EditText fullName;
    EditText email;
    EditText username;
    EditText password;
    EditText passwordConfirmation;

    com.mikhaellopez.circularimageview.CircularImageView profileImageView;
    ImageView cameraImageView;


    private SignUpViewModel viewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        fullName = findViewById(R.id.full_name_edit_text);
        email = findViewById(R.id.email_edit_text);
        username = findViewById(R.id.username_edit_text);
        password = findViewById(R.id.pass_word_edit_text);
        passwordConfirmation = findViewById(R.id.confirm_pass_word_edit_text);
        cameraImageView = findViewById(R.id.camera_image_view);

        Button signupButton = findViewById(R.id.signup_button);
        Button haveAccountButton = findViewById(R.id.login_button);

        //**************************************************************************************************
        //**************************************************************************************************
        //Buttons onClickListeners
        signupButton.setOnClickListener(this::onClick);
        haveAccountButton.setOnClickListener(this::onClick);

        //**************************************************************************************************
        //**************************************************************************************************
        viewModel = ViewModelProviders.of(this).get(SignUpViewModelImp.class);


        //**************************************************************************************************
        //**************************************************************************************************
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signup_button:
                String profileImage = "";
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
}