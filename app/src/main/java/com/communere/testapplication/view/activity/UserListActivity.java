package com.communere.testapplication.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.communere.testapplication.R;
import com.communere.testapplication.model.Bean.User;
import com.communere.testapplication.util.AlertDialogUtil;
import com.communere.testapplication.util.ToastUtil;
import com.communere.testapplication.view.UserAdapter;
import com.communere.testapplication.viewmodel.UserProfileViewModelImp;
import com.communere.testapplication.viewmodel.UsersViewModel;
import com.communere.testapplication.viewmodel.UsersViewModelImp;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    private static final String TAG = "UserListActivity";

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    UsersViewModel viewModel;

    LiveData<List<User>> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        //**************************************************************************************************
        //**************************************************************************************************
        initRecyclerView();

        //**************************************************************************************************
        //**************************************************************************************************
        viewModel = ViewModelProviders.of(this).get(UsersViewModelImp.class);

        users = viewModel.getUserList();
        users.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userAdapter.submitList(users);

            }
        });


        //**************************************************************************************************
        //**************************************************************************************************


    }


    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.users_recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        userAdapter= new UserAdapter();

        recyclerView.setAdapter(userAdapter);

        handleRecyclerVeiwItemClick();
    }

    private void handleRecyclerVeiwItemClick() {

        userAdapter.setItemClickListener(new UserAdapter.ListItemClickListener() {
            @Override
            public void onItemClicked(View view, User user) {


                final long viewId = view.getId();
                Log.d("filter_viewId", String.valueOf(viewId));

                if(viewId == R.id.remove_user_image_button){

                    viewModel.removeUser(user)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(() -> // completed with success

                                            ToastUtil.showToast(UserListActivity.this, "User removed successfully." ),
                                    updateException -> // there was an error
                                            ToastUtil.showToast(UserListActivity.this, "Removing user failed." )

                            );

                }
                else{
                    
                    goToReadOnlyUserActivity(user.getId());

                }


            }
        });


    }


    private void goToReadOnlyUserActivity(long userId) {
        Intent intent = new Intent(UserListActivity.this, ReadOnlyUserActivity.class);
        intent.putExtra("id", userId);
        startActivity(intent);
    }

}