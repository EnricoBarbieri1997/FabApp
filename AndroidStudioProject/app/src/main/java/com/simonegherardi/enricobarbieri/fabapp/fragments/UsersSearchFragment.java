package com.simonegherardi.enricobarbieri.fabapp.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.simonegherardi.enricobarbieri.fabapp.R;
import com.simonegherardi.enricobarbieri.fabapp.adapter.UsersSearchAdapter;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.ResourceFlyweightAsync;
import com.simonegherardi.enricobarbieri.fabapp.resources.SingleUser;
import com.simonegherardi.enricobarbieri.fabapp.restapi.HttpMethod;
import com.simonegherardi.enricobarbieri.fabapp.restapi.IRESTable;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSON;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSONParseException;
import com.simonegherardi.enricobarbieri.fabapp.restapi.RESTResponse;
import com.simonegherardi.enricobarbieri.fabapp.restapi.Table;
import com.simonegherardi.enricobarbieri.fabapp.restapi.WebServer;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersSearchFragment extends IntegratedFragment implements IRESTable {
    ArrayList<SingleUser> usersList;
    RecyclerView.Adapter adapter;
    EditText searchBox;
    public UsersSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.usersList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_users_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchBox = this.parentActivity.findViewById(R.id.users_search_box);
        searchBox.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                SearchUser(s.toString());
            }
        });
        adapter = new UsersSearchAdapter(this.parentActivity, this.usersList);
        this.SetUpRyclerView(R.id.users_list, 1, adapter);
        SearchUser("");
    }

    private void SearchUser(String pattern)
    {
        WebServer.Main().GenericRequest(HttpMethod.POST, Table.userSearchList, new JSON("{\"data\":{\"search\":\"" + pattern + "\"}}"), this);
    }

    @Override
    public void Success(RESTResponse response) {
        JSON users = new JSON(response.GetResponse());
        this.usersList.clear();
        try
        {
            SingleUser su = SingleUser.Empty();
            su.SetId(users.GetInt("id"));
            su.SetEmail(users.GetString("email"));
            usersList.add(su);
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
                    usersList.add(su);
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
