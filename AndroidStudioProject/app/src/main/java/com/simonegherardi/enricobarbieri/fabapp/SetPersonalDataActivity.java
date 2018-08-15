package com.simonegherardi.enricobarbieri.fabapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SetPersonalDataActivity extends AppCompatActivity {


    public void finishEditing()
    {
        final Intent finished = new Intent();
        finished.putExtra("name",R.id.edit_namevalue);
        finished.putExtra("surname",R.id.edit_surnamevalue);
        finished.putExtra("phonenumber", R.id.edit_phonenumbervalue);
        setResult(RESULT_OK, finished);
        finish();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_personal_data);

        Button doneButton = (Button) findViewById(R.id.profile_donebutton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishEditing();
            }
        });
    }
}
