package com.simonegherardi.enricobarbieri.fabapp;

public class PersonalData {
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
