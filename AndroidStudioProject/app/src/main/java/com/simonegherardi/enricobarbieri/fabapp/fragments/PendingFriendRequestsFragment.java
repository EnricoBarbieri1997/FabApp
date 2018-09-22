package com.simonegherardi.enricobarbieri.fabapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.simonegherardi.enricobarbieri.fabapp.R;
import com.simonegherardi.enricobarbieri.fabapp.adapter.PendingFriendRequestsAdapter;
import com.simonegherardi.enricobarbieri.fabapp.adapter.UsersSearchAdapter;
import com.simonegherardi.enricobarbieri.fabapp.resources.Follow;
import com.simonegherardi.enricobarbieri.fabapp.resources.SingleUser;
import com.simonegherardi.enricobarbieri.fabapp.restapi.HttpMethod;
import com.simonegherardi.enricobarbieri.fabapp.restapi.IRESTable;
import com.simonegherardi.enricobarbieri.fabapp.restapi.ISyncObserver;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSON;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSONParseException;
import com.simonegherardi.enricobarbieri.fabapp.restapi.RESTResponse;
import com.simonegherardi.enricobarbieri.fabapp.restapi.Synchronizer;
import com.simonegherardi.enricobarbieri.fabapp.restapi.Table;
import com.simonegherardi.enricobarbieri.fabapp.restapi.WebServer;

import java.util.ArrayList;

public class PendingFriendRequestsFragment extends IntegratedFragment implements IRESTable {
    ArrayList<SingleUser> friendRequestsList;
    RecyclerView.Adapter adapter;
    Integer userId;
    public PendingFriendRequestsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.friendRequestsList = new ArrayList<>();
        userId = this.parentActivity.userSharedPref.getInt(getString(R.string.idKey), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_pending_friend_requests, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new PendingFriendRequestsAdapter(this, this.parentActivity, this.friendRequestsList);
        this.SetUpRyclerView(R.id.pendingFriendRequests, 1, adapter);
        GetPendingFriendRequests();
    }

    private void GetPendingFriendRequests()
    {
        WebServer.Main().GenericRequest(HttpMethod.POST, Table.pendingFriendRequests, new JSON("{\"data\":{\"userId\":" + this.userId + "}}"), this);
    }
    public void AcceptFriendRequest(int id)
    {
        Follow follow = new Follow(userId, id);
        (new Synchronizer(follow, new ISyncObserver() {
            @Override
            public void UploadStart() {

            }

            @Override
            public void UpdateStart() {

            }

            @Override
            public void DownloadStart() {

            }

            @Override
            public void DeleteStart() {

            }

            @Override
            public void UploadComplete(boolean result) {
                GetPendingFriendRequests();
            }

            @Override
            public void UpdateComplete(boolean result) {

            }

            @Override
            public void DownloadComplete(boolean result) {

            }

            @Override
            public void DeleteComplete(boolean result) {

            }
        })).Upload();
    }
    @Override
    public void Success(RESTResponse response) {
        JSON users = new JSON(response.GetResponse());
        this.friendRequestsList.clear();
        try
        {
            SingleUser su = SingleUser.Empty();
            su.SetId(users.GetInt("id"));
            su.SetEmail(users.GetString("email"));
            friendRequestsList.add(su);
            this.UpdateRecyclerView(adapter);
        }
        catch (Exception e1)
        {
            while(users.HasNext())
            {
                try {
                    SingleUser su = SingleUser.Empty();
                    JSON next = users.Next();
                    su.SetId(next.GetInt("id"));
                    su.SetEmail(next.GetString("email"));
                    friendRequestsList.add(su);
                } catch (JSONParseException e) {
                    e.printStackTrace();
                }
            }
            this.UpdateRecyclerView(adapter);
        }
    }

    @Override
    public void Error(RESTResponse response) {

    }
}
