package com.simonegherardi.enricobarbieri.fabapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.IResourceConsumer;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.ResourceFlyweightAsync;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.ResourceResponse;
import com.simonegherardi.enricobarbieri.fabapp.resources.Image;
import com.simonegherardi.enricobarbieri.fabapp.resources.SingleUser;

public class DisplayFullScreen extends AppCompatActivity implements IResourceConsumer {
    private Context context;
    private int idImage;
    private PhotoView fsImage;
    private TextView photographer;
    private CharSequence photographerName;
    private CharSequence photographedName;
    private ResourceResponse photoid;
    private ResourceResponse userid;

    public DisplayFullScreen()
    {
        super();
    }


    public void displayToast()
    {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        TextView text = (TextView) layout.findViewById(R.id.photographedName);
        text.setText(this.photographedName);

        Toast toast = new Toast(getApplicationContext());
        //toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_full_screen);
        fsImage = (PhotoView) findViewById(R.id.fullscreenImage);
        photographer = (TextView) findViewById(R.id.fullscreenPhotographer);
        context = getApplicationContext();
        Bundle b = getIntent().getExtras();
        idImage = b.getInt("image_id");

        photoid = new ResourceResponse();
        ResourceFlyweightAsync.Main().GetPhoto(idImage, photoid, this);

        fsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            displayToast();
            }
        });

    }

    @Override
    public void OnResourceReady(ResourceResponse response) {
        if (photoid == response) {
            Image image = (Image) response.GetResource();
            Glide
                    .with(context)
                    .load(image.GetUrl())
                    .into(fsImage);
            userid = new ResourceResponse();
            ResourceFlyweightAsync.Main().GetSingleUser(image.GetPhotographed(), userid, this);
        }
        if (userid == response) {
           SingleUser photographed = (SingleUser) response.GetResource();
           this.photographedName = photographed.GetUsername();
           this.displayToast();
        }
    }
}
