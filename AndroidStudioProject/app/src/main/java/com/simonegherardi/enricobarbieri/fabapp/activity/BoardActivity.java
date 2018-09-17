package com.simonegherardi.enricobarbieri.fabapp.activity;

import android.app.Activity;
import android.os.Bundle;

import com.simonegherardi.enricobarbieri.fabapp.R;
import com.simonegherardi.enricobarbieri.fabapp.fragments.MenuFragment;
import com.simonegherardi.enricobarbieri.fabapp.fragments.UsersSearchFragment;

public class BoardActivity extends FragmentAwareActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        this.container = R.id.board_menu;
        this.AddFragment(new MenuFragment());
        this.container = R.id.board_view;
        this.AddFragment(new UsersSearchFragment());
    }
}
