package com.simonegherardi.enricobarbieri.fabapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.simonegherardi.enricobarbieri.fabapp.activity.FragmentAwareActivity;
import com.simonegherardi.enricobarbieri.fabapp.resources.SingleUser;
import com.simonegherardi.enricobarbieri.fabapp.fragments.SignUpFragment;
import com.simonegherardi.enricobarbieri.fabapp.restapi.IRESTable;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSON;
import com.simonegherardi.enricobarbieri.fabapp.restapi.RESTResponse;
import com.simonegherardi.enricobarbieri.fabapp.restapi.WebServer;

public class MainActivity extends FragmentAwareActivity implements IRESTable{

    private RESTResponse connect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.container = R.id.maincontainer;
        connect = WebServer.Main().Connect(this);
    }

    @Override
    public void Success(RESTResponse response) {
        if(connect == response) {
            int userId = this.userSharedPref.getInt(getString(R.string.idKey), 0);
            if (userId != 0) {
                JSON data = new JSON();
                data.Set(getString(R.string.emailKey), userSharedPref.getString(getString(R.string.emailKey),""));
                data.Set(getString(R.string.passwordKey), userSharedPref.getString(getString(R.string.passwordKey),""));
                WebServer.Main().Login(data, this);
            } else {
                StartLogin();
            }
        }
        else
        {
            StartBoard();
        }
    }

    @Override
    public void Error(RESTResponse response) {
        StartLogin();
    }
    private void StartLogin()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SignUpFragment fragment = new SignUpFragment();
                AddFragment(fragment);
            }
        });
    }
    private void StartBoard()
    {
        startActivity(this.GetBoardActivity());
        finish();
    }
}
