package com.simonegherardi.enricobarbieri.fabapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class DisplayFullScreen extends Activity {
    private Context context;
    protected final ImageView fullScreenImage;

    public DisplayFullScreen(ImageView fullScreenImage, Context context) {
        this.fullScreenImage = fullScreenImage;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.displayfullscreen);
        Bundle b = getIntent().getExtras();
        int id = b.getInt("image_id");
        Glide
                .with(context)
                .load(id)
                .into(this.fullScreenImage);

    }
}
