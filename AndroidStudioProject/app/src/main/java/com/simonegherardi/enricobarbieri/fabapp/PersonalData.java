package com.simonegherardi.enricobarbieri.fabapp;

import android.app.Activity;
import android.widget.ImageView;

public class PersonalData extends Activity {
    protected String phone;
    protected String email;
    protected String username;
    protected String name;
    protected String surname;



    public PersonalData(String phone, String email, String username, String name, String surname)
    {
        this.phone=phone;
        this.email=email;
        this.username=username;
        this.name=name;
        this.surname=surname;
    }


}
