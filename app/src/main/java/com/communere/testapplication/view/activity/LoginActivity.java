package com.communere.testapplication.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.communere.testapplication.R;
import com.communere.testapplication.exception.LoginException;
import com.communere.testapplication.model.Bean.Admin;
import com.communere.testapplication.model.Bean.User;
import com.communere.testapplication.util.AlertDialogUtil;
import com.communere.testapplication.viewmodel.LoginViewModel;
import com.communere.testapplication.viewmodel.LoginViewModelImp;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "LoginActivity";

    EditText username;
    EditText password;


    private LoginViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        username = findViewById(R.id.user_name_edit_text);
        password = findViewById(R.id.pass_word_edit_text);

        Button signup = findViewById(R.id.signup_button);
        Button login = findViewById(R.id.login_button);

        //**************************************************************************************************
        //**************************************************************************************************
        //Buttons onClickListeners
        signup.setOnClickListener(this::onClick);
        login.setOnClickListener(this::onClick);

        //**************************************************************************************************
        //**************************************************************************************************
        viewModel = ViewModelProviders.of(this).get(LoginViewModelImp.class);


        //**************************************************************************************************
        //**************************************************************************************************
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signup_button:
                goToSignUpActivity();
                break;

            case R.id.login_button:
                viewModel.login(username.getText().toString(), password.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(user -> {
                    Log.d(TAG, user.toString());

                    goToNextPage(user);

                        }, exception ->
//                        Toast.makeText(this, ((LoginException) exception).getMessage(), Toast.LENGTH_LONG ).show()
                        AlertDialogUtil.showSimpleAlertDialog(LoginActivity.this, ((LoginException) exception).getMessage())
                );


                break;

        }

    }

    private void goToNextPage(User user) {
        if(user.getUsername().equals(Admin.getUsername())){  //Admin user logins
            goToUserListActivity();
        }
        else { //Regular user logins
            goToUserProfileActivity(user.getId());
        }
    }

    private void goToSignUpActivity() {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }

    private void goToUserProfileActivity(long userId) {
        Intent intent = new Intent(LoginActivity.this, EditUserProfileActivity.class);
        intent.putExtra("id", userId);
        startActivity(intent);
        LoginActivity.this.finish();
    }

    private void goToUserListActivity() {
        Intent intent = new Intent(LoginActivity.this, UserListActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }
}