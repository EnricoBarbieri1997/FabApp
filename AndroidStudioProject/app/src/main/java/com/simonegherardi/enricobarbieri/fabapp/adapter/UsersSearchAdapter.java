package com.simonegherardi.enricobarbieri.fabapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.simonegherardi.enricobarbieri.fabapp.DisplayFullScreen;
import com.simonegherardi.enricobarbieri.fabapp.R;
import com.simonegherardi.enricobarbieri.fabapp.activity.ProfileActivity;
import com.simonegherardi.enricobarbieri.fabapp.resources.Image;
import com.simonegherardi.enricobarbieri.fabapp.resources.SingleUser;

import java.util.ArrayList;

public class UsersSearchAdapter extends RecyclerView.Adapter<UsersSearchAdapter.UserEmailHolder> {
    private ArrayList<SingleUser> usersList;
    private Context context;

    public UsersSearchAdapter(Context context, ArrayList<SingleUser> usersList) {
        this.usersList = usersList;
        this.context = context;
    }

    @Override
    public UsersSearchAdapter.UserEmailHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_list_cell, viewGroup, false);
        UsersSearchAdapter.UserEmailHolder userEmailHolder = new UsersSearchAdapter.UserEmailHolder(view);
        return userEmailHolder;
    }


    public void OpenProfile(int userId)
    {
        Intent intent = new Intent(context, ProfileActivity.class);
        Bundle b = new Bundle();
        b.putInt(context.getString(R.string.idKey), userId); //Your id
        intent.putExtras(b);
        context.startActivity(intent);
    }

    @Override
    public void onBindViewHolder(final UsersSearchAdapter.UserEmailHolder viewHolder, int i) {
        final int id = usersList.get(i).GetId();
        viewHolder.email.setText(usersList.get(i).GetUsername());


        viewHolder.email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenProfile(id);
            }
        });
    }


    @Override
    public int getItemCount() {
        return this.usersList.size();
    }

    public static class UserEmailHolder extends RecyclerView.ViewHolder{
        private TextView email;
        public UserEmailHolder(View view) {
            super(view);
            email = (TextView) view.findViewById(R.id.users_list_email);
        }
    }
}
