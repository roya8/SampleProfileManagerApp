package com.communere.testapplication.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.communere.testapplication.R;
import com.communere.testapplication.model.Bean.User;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends ListAdapter<User, UserAdapter.UserHolder> {

    private static final String TAG = "UserAdapter";

    //**************************************************************************************************
    //ClickListener
    private ListItemClickListener listItemClickListener;

    public interface ListItemClickListener{

        void onItemClicked(View itemView, User user);
    }

    public void setItemClickListener(ListItemClickListener listener) {
        this.listItemClickListener = listener;
    }
    //**************************************************************************************************

    //constructor
    public UserAdapter() {
        super(diffCallback);
    }

    private static final DiffUtil.ItemCallback<User> diffCallback = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getId() == newItem.getId();
//            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.equals(newItem);
//            return false;
        }
    };

    //**************************************************************************************************
    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_item, parent, false);

        return new UserHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {

        User currentUser = getItem(position);
        holder.usernameTextView.setText(currentUser.getUsername());
    }

    class UserHolder extends RecyclerView.ViewHolder{

        com.mikhaellopez.circularimageview.CircularImageView profileImageView;
        private TextView usernameTextView;
        ImageButton deleteButton;

        public UserHolder(@NonNull View itemView) {
            super(itemView);

            profileImageView = itemView.findViewById(R.id.user_profile_image_view);
            usernameTextView = itemView.findViewById(R.id.username_text_view);
            deleteButton = itemView.findViewById(R.id.remove_user_image_button);

            //**************************************************************************************************
            //onClickListener
            //****************************************
            // Setup the click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listItemClickListener.onItemClicked(itemView, getItem(position));
                        }
                    }
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listItemClickListener.onItemClicked(deleteButton, getItem(position));
                        }
                    }
                }
            });


            //**************************************************************************************************
        }
    }
}
