package com.communere.testapplication.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.communere.testapplication.R;
import com.communere.testapplication.model.Bean.User;
import com.communere.testapplication.util.AlertDialogUtil;
import com.communere.testapplication.util.ToastUtil;
import com.communere.testapplication.viewmodel.UserProfileViewModel;
import com.communere.testapplication.viewmodel.UserProfileViewModelImp;

public class ReadOnlyUserActivity extends AppCompatActivity {

    private static final String TAG = "ReadOnlyUserActivity";

    TextView fullName;
    TextView username;
    TextView email;

    com.mikhaellopez.circularimageview.CircularImageView profileImageView;


    private UserProfileViewModel viewModel;
    LiveData<User> user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_only_user);


        fullName = findViewById(R.id.full_name_text_view);
        username = findViewById(R.id.user_name_readonly_text_view);
        email = findViewById(R.id.email_readonly_text_view);

        profileImageView = findViewById(R.id.profile_image_view);

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

       }

}