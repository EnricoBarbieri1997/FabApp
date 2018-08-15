package com.simonegherardi.enricobarbieri.fabapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.simonegherardi.enricobarbieri.fabapp.Resources.SingleUser;

public class PersonalDataActivity extends AppCompatActivity {
    private SingleUser user;

    public PersonalDataActivity(SingleUser user)
    {
        this.user = user;
    }

    public void startSetPersonalDataActivity()
    {
        final Intent startSetPersonalData = new Intent(getApplicationContext(), SetPersonalDataActivity.class);
        getApplicationContext().startActivity(startSetPersonalData);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        Button editButton = (Button) findViewById(R.id.edit_editbutton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSetPersonalDataActivity();
            }
        });
    }
}
