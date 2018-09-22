package com.simonegherardi.enricobarbieri.fabapp.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.simonegherardi.enricobarbieri.fabapp.R;
import com.simonegherardi.enricobarbieri.fabapp.activity.ProfileActivity;
import com.simonegherardi.enricobarbieri.fabapp.fragments.IntegratedFragment;
import com.simonegherardi.enricobarbieri.fabapp.fragments.PendingFriendRequestsFragment;
import com.simonegherardi.enricobarbieri.fabapp.resources.SingleUser;

import java.util.ArrayList;

public class PendingFriendRequestsAdapter extends RecyclerView.Adapter<PendingFriendRequestsAdapter.UserEmailHolder> {
    private ArrayList<SingleUser> friendRequestsList;
    private Context context;
    private Activity activity;
    private android.support.v4.app.Fragment fragment;

    public PendingFriendRequestsAdapter(android.support.v4.app.Fragment fragment, Context context, ArrayList<SingleUser> friendRequestsList) {
        this.friendRequestsList = friendRequestsList;
        this.activity = (Activity)context;
        this.context = this.activity.getApplicationContext();
        this.fragment = fragment;
    }

    @Override
    public PendingFriendRequestsAdapter.UserEmailHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pending_friend_requests_cell, viewGroup, false);
        PendingFriendRequestsAdapter.UserEmailHolder userEmailHolder = new PendingFriendRequestsAdapter.UserEmailHolder(view);
        return userEmailHolder;
    }


    public void AcceptFriendRequest(int userId)
    {
        ((PendingFriendRequestsFragment)fragment).AcceptFriendRequest(userId);
    }

    @Override
    public void onBindViewHolder(final PendingFriendRequestsAdapter.UserEmailHolder viewHolder, int i) {
        final int id = friendRequestsList.get(i).GetId();
        viewHolder.email.setText(friendRequestsList.get(i).GetUsername());

        viewHolder.pendingFriendRequestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AcceptFriendRequest(id);
            }
        });
    }


    @Override
    public int getItemCount() {
        return this.friendRequestsList.size();
    }

    public static class UserEmailHolder extends RecyclerView.ViewHolder{
        private TextView email;
        private Button pendingFriendRequestsButton;
        public UserEmailHolder(View view) {
            super(view);
            email = (TextView) view.findViewById(R.id.pendingFriendRequestsEmail);
            pendingFriendRequestsButton = (Button)view.findViewById(R.id.pendingFriendRequestsAcceptButton);
        }
    }
}