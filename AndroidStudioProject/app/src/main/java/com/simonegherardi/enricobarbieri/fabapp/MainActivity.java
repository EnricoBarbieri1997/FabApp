package com.simonegherardi.enricobarbieri.fabapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.simonegherardi.enricobarbieri.fabapp.Resources.SingleUser;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.DummyResourceConsumer;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.ResourceFlyweightAsync;
import com.simonegherardi.enricobarbieri.fabapp.restapi.DummyRestable;
import com.simonegherardi.enricobarbieri.fabapp.restapi.HttpMethod;
import com.simonegherardi.enricobarbieri.fabapp.restapi.ResourceSynchronizer;
import com.simonegherardi.enricobarbieri.fabapp.restapi.Synchronizer;
import com.simonegherardi.enricobarbieri.fabapp.restapi.Table;
import com.simonegherardi.enricobarbieri.fabapp.restapi.WebServer;

import java.util.ArrayList;

import static android.graphics.Color.BLACK;

public class MainActivity extends AppCompatActivity {

    private SingleUser test1;


    /*public void startPersonalProfileActivity()
    {
        final Intent personalProfile = new Intent(getApplicationContext(), PersonalProfileActivity.class);
        getApplicationContext().startActivity(personalProfile);
    }*/

    public void startPersonalProfileActivity() {
        final Intent personalProfile = new Intent(getApplicationContext(), PersonalProfileActivity.class);

        // tutto questo blocco verrà sostituito dal passaggio dell'id della risorsa
        personalProfile.putExtra("phone_number", this.test1.info.phone);
        personalProfile.putExtra("email", this.test1.info.email);
        personalProfile.putExtra("username", this.test1.info.username);
        personalProfile.putExtra("name", this.test1.info.name);
        personalProfile.putExtra("surname", this.test1.info.surname);

        getApplicationContext().startActivity(personalProfile);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test1 = SingleUser.Empty();
        test1.Init("Shadowing","3345850585", "simone.gherardi2@gmail.com");
        test1.SetName("Simone");
        test1.SetSurname("Gherardi");

        ImageButton personal_profile_imageButton = (ImageButton) findViewById(R.id.personal_profile_imageButton);
        personal_profile_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPersonalProfileActivity();
           }
        });

    }


}
