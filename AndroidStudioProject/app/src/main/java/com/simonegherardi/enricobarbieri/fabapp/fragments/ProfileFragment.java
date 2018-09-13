package com.simonegherardi.enricobarbieri.fabapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.simonegherardi.enricobarbieri.fabapp.R;
import com.simonegherardi.enricobarbieri.fabapp.Resources.SingleUser;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.IResourceConsumer;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.ResourceFlyweightAsync;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.ResourceResponse;
import com.simonegherardi.enricobarbieri.fabapp.restapi.HttpMethod;
import com.simonegherardi.enricobarbieri.fabapp.restapi.Table;
import com.simonegherardi.enricobarbieri.fabapp.restapi.WebServer;

public class ProfileFragment extends IntegratedFragment implements IResourceConsumer{
    private SingleUser user;
    private int userId;
    private boolean isUser;
    private ImageView picture;
    private TextView email;
    private TextView phone;

    @Override
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        userId = getArguments().getInt(getString(R.string.idKey), 0);
        if(userId == userSharedPref.getInt(getString(R.string.idKey),0))
        {
            isUser = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        email = getView().findViewById(R.id.profileEmail);
        phone = getView().findViewById(R.id.profilePhone);
        picture = getView().findViewById(R.id.profilePicture);

        picture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        ResourceFlyweightAsync.Main().GetSingleUser(this.userId, this);
    }

    @Override
    public void OnResourceReady(ResourceResponse response) {
        this.user = (SingleUser)response.GetResource();
        this.SetTextTextView(this.email,user.GetEmail());
        this.SetTextTextView(this.phone,user.GetPhone());
    }
}
