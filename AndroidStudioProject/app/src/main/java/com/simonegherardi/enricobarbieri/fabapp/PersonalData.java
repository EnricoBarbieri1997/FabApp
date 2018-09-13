package com.simonegherardi.enricobarbieri.fabapp;

import com.simonegherardi.enricobarbieri.fabapp.Resources.Photo;

public class PersonalData {
    public String phone;
    public String email;
    public String username;
    public String name;
    public String surname;
    public String password;


    public PersonalData()
    {
    }

    public PersonalData(String phone, String email, String username, String password, String name, String surname)
    {
        this.phone=phone;
        this.email=email;
        this.username=username;
        this.name=name;
        this.surname=surname;
        this.password = password;
    }
}
