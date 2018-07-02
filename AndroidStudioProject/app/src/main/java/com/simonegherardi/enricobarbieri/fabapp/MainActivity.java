package com.simonegherardi.enricobarbieri.fabapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.DummyResourceConsumer;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.ResourceFlyweightAsync;
import com.simonegherardi.enricobarbieri.fabapp.restapi.DummyRestable;
import com.simonegherardi.enricobarbieri.fabapp.restapi.HttpMethod;
import com.simonegherardi.enricobarbieri.fabapp.restapi.Table;
import com.simonegherardi.enricobarbieri.fabapp.restapi.WebServer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info_page);

        DummyResourceConsumer a = new DummyResourceConsumer();
        a.single = ResourceFlyweightAsync.Main().GetSingleUser(4, a);
        a.group = ResourceFlyweightAsync.Main().GetGroup(2, a);
        a.image = ResourceFlyweightAsync.Main().GetPhoto(3, a);
    }
}
