package com.simonegherardi.enricobarbieri.fabapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.IResourceConsumer;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.ResourceFlyweightAsync;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.ResourceResponse;
import com.simonegherardi.enricobarbieri.fabapp.resources.Image;

public class DisplayFullScreen extends AppCompatActivity implements IResourceConsumer {
    private Context context;
    private int idImage;
    private ImageView fsImage;

    public DisplayFullScreen()
    {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_full_screen);
        fsImage = (ImageView) findViewById(R.id.fullscreenImage);
        context = getApplicationContext();
        Bundle b = getIntent().getExtras();
        idImage = b.getInt("image_id");

        ResourceFlyweightAsync.Main().GetPhoto(idImage, this);
    }

    @Override
    public void OnResourceReady(ResourceResponse response) {
        Image image = (Image)response.GetResource();

        Glide
                .with(context)
                .load(image.GetUrl())
                .into(fsImage);
    }
}
