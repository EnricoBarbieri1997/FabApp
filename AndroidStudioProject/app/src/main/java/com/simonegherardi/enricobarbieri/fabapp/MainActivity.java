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

    private SingleUser test1;

    /*public void startPersonalProfileActivity()
    {
        final Intent personalProfile = new Intent(getApplicationContext(), PersonalProfileActivity.class);
        getApplicationContext().startActivity(personalProfile);
    }*/

    /*public void startPersonalProfileActivity() {
        final Intent personalProfile = new Intent(getApplicationContext(), PersonalProfileActivity.class);

        // tutto questo blocco verrà sostituito dal passaggio dell'id della risorsa
        personalProfile.putExtra("phone_number", this.test1.info.phone);
        personalProfile.putExtra("email", this.test1.info.email);
        personalProfile.putExtra("username", this.test1.info.username);
        personalProfile.putExtra("name", this.test1.info.name);
        personalProfile.putExtra("surname", this.test1.info.surname);

        getApplicationContext().startActivity(personalProfile);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.container = R.id.maincontainer;

        SignUpFragment fragment = new SignUpFragment();
        AddFragment(fragment);
        /*test1 = SingleUser.Empty();
        test1.Init("Shadowing","3345850585", "simone.gherardi2@gmail.com");
        test1.SetName("Simone");
        test1.SetSurname("Gherardi");

        ImageButton personal_profile_imageButton = (ImageButton) findViewById(R.id.personal_profile_imageButton);
        personal_profile_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPersonalProfileActivity();
           }
        });*/

    }

}
