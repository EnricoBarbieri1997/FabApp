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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.simonegherardi.enricobarbieri.fabapp.R;
import com.simonegherardi.enricobarbieri.fabapp.activity.ProfileActivity;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.ResourcesTypes;
import com.simonegherardi.enricobarbieri.fabapp.resources.Image;
import com.simonegherardi.enricobarbieri.fabapp.resources.SingleUser;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.IResourceConsumer;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.ResourceFlyweightAsync;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.ResourceResponse;
import com.simonegherardi.enricobarbieri.fabapp.restapi.IRESTable;
import com.simonegherardi.enricobarbieri.fabapp.restapi.RESTResponse;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends IntegratedFragment implements IResourceConsumer{

    private SingleUser user;
    private Image profileImage;
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
                this.SetTextTextView(this.phone,user.GetPhone());
                break;
            case Image:
                this.profileImage = (Image)response.GetResource();
                SetImageInView(profileImage, picture);
        }
    }
}
