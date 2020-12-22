package com.communere.testapplication.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.communere.testapplication.R;
import com.communere.testapplication.exception.LoginException;
import com.communere.testapplication.model.Bean.User;
import com.communere.testapplication.util.AlertDialogUtil;
import com.communere.testapplication.util.ToastUtil;
import com.communere.testapplication.viewmodel.LoginViewModel;
import com.communere.testapplication.viewmodel.LoginViewModelImp;
import com.communere.testapplication.viewmodel.UserProfileViewModel;
import com.communere.testapplication.viewmodel.UserProfileViewModelImp;
import com.communere.testapplication.viewmodel.UsersViewModel;
import com.communere.testapplication.viewmodel.UsersViewModelImp;

public class EditUserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EditUserProfileActivity";

    TextView fullName;
    EditText username;
    EditText email;

    com.mikhaellopez.circularimageview.CircularImageView profileImageView;


    private UserProfileViewModel viewModel;
    LiveData<User> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);


        fullName = findViewById(R.id.full_name_edit_text);
        username = findViewById(R.id.user_name_edit_text);
        email = findViewById(R.id.email_edit_text);

        profileImageView = findViewById(R.id.profile_image_view);

        Button updateButton = findViewById(R.id.update_button);
        Button deleteButton = findViewById(R.id.delete_button);
        Button chooseImageButton = findViewById(R.id.update_image_button);

        //**************************************************************************************************
        //**************************************************************************************************
        //Buttons onClickListeners
        updateButton.setOnClickListener(this::onClick);
        deleteButton.setOnClickListener(this::onClick);
        chooseImageButton.setOnClickListener(this::onClick);

        //**************************************************************************************************
        //**************************************************************************************************
        long userId = getIntent().getExtras().getLong("id");

        //**************************************************************************************************
        //**************************************************************************************************
        viewModel = ViewModelProviders.of(this).get(UserProfileViewModelImp.class);

        user = viewModel.getUser(userId);
        user.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                fullName.setText(user.getFullName());
                username.setText(user.getUsername());
                email.setText(user.getEmail());

            }
        });

        //**************************************************************************************************
        //**************************************************************************************************
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update_button:

                User updatingUser = user.getValue();
                updatingUser.setUsername(username.getText().toString());
                updatingUser.setEmail(email.getText().toString());

                viewModel.updateUser(updatingUser)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> // completed with success

                                ToastUtil.showToast(this, "User info updated successfully." ),
                                updateException -> // there was an error
                                        AlertDialogUtil.showSimpleAlertDialog(EditUserProfileActivity.this, updateException.getMessage())

                        );

                break;

            case R.id.delete_button:
                viewModel.removeUser(user.getValue())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> // completed with success
                                {

                                    ToastUtil.showToast(this, "User removed successfully." );
                                    goToLoginActivity();
                                }
                                ,
                                throwable -> // there was an error
                                        AlertDialogUtil.showSimpleAlertDialog(EditUserProfileActivity.this, "Remove failed.")

                        );
                break;

        }

    }


    private void goToLoginActivity() {
        Intent intent = new Intent(EditUserProfileActivity.this, LoginActivity.class);
        startActivity(intent);
        EditUserProfileActivity.this.finish();
    }
}