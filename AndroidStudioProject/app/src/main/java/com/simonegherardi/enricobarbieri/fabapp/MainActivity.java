package com.simonegherardi.enricobarbieri.fabapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.simonegherardi.enricobarbieri.fabapp.restapi.DummyRestable;
import com.simonegherardi.enricobarbieri.fabapp.restapi.HttpMethod;
import com.simonegherardi.enricobarbieri.fabapp.restapi.Table;
import com.simonegherardi.enricobarbieri.fabapp.restapi.WebServer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info_page);

        WebServer.Main().GenericRequest(HttpMethod.GET, Table.Resource, new DummyRestable());
    }
}
