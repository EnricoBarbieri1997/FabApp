package com.simonegherardi.enricobarbieri.fabapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.simonegherardi.enricobarbieri.fabapp.activity.FragmentAwareActivity;
import com.simonegherardi.enricobarbieri.fabapp.resources.SingleUser;
import com.simonegherardi.enricobarbieri.fabapp.fragments.SignUpFragment;

public class MainActivity extends FragmentAwareActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.container = R.id.maincontainer;

        SignUpFragment fragment = new SignUpFragment();
        AddFragment(fragment);


    }

}
