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
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    private static final String TAG = "UserAdapter";

    Context context;
    private List<User> users = new ArrayList<>();

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




    public UserAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_item, parent, false);

        return new UserHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {

        User currentUser = users.get(position);
        holder.usernameTextView.setText(currentUser.getUsername());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public void setUsers(List<User> users){
        this.users = users;
        notifyDataSetChanged();
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
                            listItemClickListener.onItemClicked(itemView, users.get(position));
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
                            listItemClickListener.onItemClicked(deleteButton, users.get(position));
                        }
                    }
                }
            });


            //**************************************************************************************************
        }
    }
}
