package com.simonegherardi.enricobarbieri.fabapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DisplayFullScreen extends AppCompatActivity {
    private Context context;
    private int idImage;

    public DisplayFullScreen()
    {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_full_screen);
        final ImageView fsImage = (ImageView) findViewById(R.id.fullscreenImage);
        context = getApplicationContext();
        Bundle b = getIntent().getExtras();
        idImage = b.getInt("image_id");
        fsImage.setImageResource(idImage);
    }
}
