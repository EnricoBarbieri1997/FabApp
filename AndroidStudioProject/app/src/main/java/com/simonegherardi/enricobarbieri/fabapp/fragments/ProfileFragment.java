package com.simonegherardi.enricobarbieri.fabapp.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.simonegherardi.enricobarbieri.fabapp.R;
import com.simonegherardi.enricobarbieri.fabapp.activity.ProfileActivity;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.ResourcesTypes;
import com.simonegherardi.enricobarbieri.fabapp.resources.Follow;
import com.simonegherardi.enricobarbieri.fabapp.resources.Image;
import com.simonegherardi.enricobarbieri.fabapp.resources.SingleUser;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.IResourceConsumer;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.ResourceFlyweightAsync;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.ResourceResponse;
import com.simonegherardi.enricobarbieri.fabapp.restapi.HttpMethod;
import com.simonegherardi.enricobarbieri.fabapp.restapi.IRESTable;
import com.simonegherardi.enricobarbieri.fabapp.restapi.ISyncObserver;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSON;
import com.simonegherardi.enricobarbieri.fabapp.restapi.RESTResponse;
import com.simonegherardi.enricobarbieri.fabapp.restapi.Synchronizer;
import com.simonegherardi.enricobarbieri.fabapp.restapi.Table;
import com.simonegherardi.enricobarbieri.fabapp.restapi.WebServer;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends IntegratedFragment implements IResourceConsumer{

    private SingleUser user;
    private Image profileImage;
    private int userId;
    private boolean isUser;
    private ImageView picture;
    private TextView email;
    private Button friendButton;
    private ImageView pendingFriendRequestsButton;
    private boolean friendRequestSended ;
    private boolean friendRequestAccepted;
    private int followResponseReceived = 0;

    @Override
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        userId = getArguments().getInt(getString(R.string.idKey), 0);
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
        picture = getView().findViewById(R.id.profilePicture);
        friendButton = getView().findViewById(R.id.friendButton);
        pendingFriendRequestsButton = getView().findViewById(R.id.pendingFriendRequestsButton);

        ProfileActivity parent = (ProfileActivity)this.parentActivity;
        if(parent.isUser == true)
        {
            friendButton.setVisibility(View.INVISIBLE);
            pendingFriendRequestsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentActivity.SwapFragment(new PendingFriendRequestsFragment());
                }
            });
        }
        else
        {
            pendingFriendRequestsButton.setVisibility(View.INVISIBLE);
            Follow f1 = new Follow(parentActivity.userSharedPref.getInt(getString(R.string.idKey),0),userId);
            Follow f2 = new Follow(userId, parentActivity.userSharedPref.getInt(getString(R.string.idKey),0));

            (new Synchronizer(f1, new ISyncObserver() {
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

                }

                @Override
                public void UpdateComplete(boolean result) {

                }

                @Override
                public void DownloadComplete(boolean result) {
                    friendRequestSended = result;
                    FriendRequestAction();
                }

                @Override
                public void DeleteComplete(boolean result) {

                }
            })).Download();
            (new Synchronizer(f2, new ISyncObserver() {
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

                }

                @Override
                public void UpdateComplete(boolean result) {

                }

                @Override
                public void DownloadComplete(boolean result) {
                    friendRequestAccepted = result;
                    FriendRequestAction();
                }

                @Override
                public void DeleteComplete(boolean result) {

                }
            })).Download();
        }

        ResourceFlyweightAsync.Main().GetSingleUser(this.userId, this);
    }

    @Override
    public void OnResourceReady(ResourceResponse response) {
        switch (response.GetResourceType())
        {
            case SingleUser:
                this.user = (SingleUser)response.GetResource();
                if(user.GetPicture() > 0)
                {
                    ResourceFlyweightAsync.Main().GetPhoto(user.GetPicture(), this);
                }
                this.SetTextTextView(this.email,user.GetUsername());
                break;
            case Image:
                this.profileImage = (Image)response.GetResource();
                SetImageInView(profileImage, picture);
        }
    }

    public void FriendRequestAction()
    {
        followResponseReceived++;
        if(followResponseReceived == 2)
        {
            if(friendRequestSended)
            {
                DisableButton();
                if(friendRequestAccepted)
                {
                    ChangeButtonText("Accepted");
                }
                else
                {
                    ChangeButtonText("Pending");
                }
            }
            else
            {
                ActivateButton();
            }
        }
    }
    private void DisableButton()
    {
        this.parentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                friendButton.setClickable(false);
            }
        });
    }
    private void ChangeButtonText(final String text)
    {
        this.parentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                friendButton.setText(text);
            }
        });
    }
    private void ActivateButton()
    {
        friendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Follow follow = new Follow(parentActivity.userSharedPref.getInt(getString(R.string.idKey),0),userId);
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
                        DisableButton();
                        ChangeButtonText("Pending");
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
        });
    }
}
