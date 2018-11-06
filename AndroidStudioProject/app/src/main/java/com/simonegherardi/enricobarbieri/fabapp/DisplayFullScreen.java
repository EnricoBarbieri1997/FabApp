package com.simonegherardi.enricobarbieri.fabapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.OnViewTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.IResourceConsumer;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.ResourceFlyweightAsync;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.ResourceResponse;
import com.simonegherardi.enricobarbieri.fabapp.resources.Image;
import com.simonegherardi.enricobarbieri.fabapp.resources.SingleUser;

import java.util.ArrayList;

public class DisplayFullScreen extends AppCompatActivity implements IResourceConsumer {
    private Context context;
    private int idImage;
    private ArrayList<Integer> photoIdsArray;
    private PhotoView fsImage;
    private CharSequence photographerName;
    private CharSequence photographedName;
    private ResourceResponse photoid;
    private ResourceResponse photographer;
    private ResourceResponse photographed;
    private boolean photographerReady = false;
    private boolean photographedReady = false;
    private boolean toast_showed = false;

    public DisplayFullScreen()
    {
        super();
    }


    public void displayToast()
    {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        TextView photographedText = (TextView) layout.findViewById(R.id.photographedName);
        photographedText.setText(this.photographedName);

        TextView photographerText = (TextView) layout.findViewById(R.id.photographerName);
        photographerText.setText(this.photographerName);

        Toast toast = new Toast(getApplicationContext());
        //toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public void displayInstructionToast()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_toast_instructions,
                        (ViewGroup) findViewById(R.id.custom_toast_instructions));

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
            }
        });
    }

    public void displayFullScreen(int id_photo, ArrayList<Integer> photoIdsArray)
    {
        final Intent fullscreen = new Intent(context , DisplayFullScreen.class);
        fullscreen.putExtra("image_id",id_photo);
        fullscreen.putExtra("photoIdsArray",photoIdsArray);
        context.startActivity(fullscreen);
        this.finish();
        /*idImage = id_photo;
        GetMainImage();*/
    }

    private void GetMainImage()
    {
        photoid = new ResourceResponse();
        ResourceFlyweightAsync.Main().GetPhoto(idImage, photoid, this);
    }

    public void swipeLeft()
    {
        if(this.idImage == this.photoIdsArray.get(this.photoIdsArray.size()-1))
        {
            return;
        }
        int currentIndex = photoIdsArray.indexOf(this.idImage);

        displayFullScreen(this.photoIdsArray.get(currentIndex+1),this.photoIdsArray);
    }

    public void swipeRight()
    {
        if(this.idImage == this.photoIdsArray.get(0))
        {
            return;
        }
        int currentIndex = photoIdsArray.indexOf(this.idImage);

        displayFullScreen(this.photoIdsArray.get(currentIndex-1),this.photoIdsArray);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_full_screen);
        fsImage = (PhotoView) findViewById(R.id.fullscreenImage);
        context = getApplicationContext();
        Bundle b = getIntent().getExtras();
        idImage = b.getInt("image_id");
        photoIdsArray = b.getIntegerArrayList("photoIdsArray");

        GetMainImage();

        fsImage.setOnViewTapListener(new OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int height = displayMetrics.heightPixels;
                int width = displayMetrics.widthPixels;
                if (x < 0.20*width) {
                    swipeRight();
                }
                if (x > 0.80*width) {
                    swipeLeft();
                }
                if (x <= 0.80*width && x >= 0.20*width) {
                    displayToast();
                }
            }
        });

    }

    @Override
    public void OnResourceReady(ResourceResponse response) {
        if (photoid == response) {
            this.photographedName = "loading...";
            this.photographerName = "loading...";
            final Image image = (Image) response.GetResource();
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Glide.with(context).clear(fsImage);
                    Glide
                            .with(context)
                            .load(image.GetUrl())
                            .into(fsImage);
                }
            });
            photographed = new ResourceResponse();
            ResourceFlyweightAsync.Main().GetSingleUser(image.GetPhotographed(), photographed, this);
            photographer = new ResourceResponse();
            ResourceFlyweightAsync.Main().GetSingleUser(image.GetPhotographer(), photographer, this);
        }
        if (photographed == response) {
           SingleUser photographed = (SingleUser) response.GetResource();
           this.photographedName = photographed.GetUsername();
           this.photographedReady = true;
        }

        if (photographer == response) {
            SingleUser photographer = (SingleUser) response.GetResource();
            this.photographerName = photographer.GetUsername();
            this.photographerReady = true;
        }
        if(photographerReady == true && photographedReady == true && toast_showed == false)
        {
            this.photographedReady = false;
            this.photographerReady = false;
            toast_showed = true;
            displayInstructionToast();
        }
    }
}
