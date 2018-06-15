package com.simonegherardi.enricobarbieri.fabapp;

abstract public class Resource {
    protected boolean reported;

    public Resource()
    {
        this.reported = false;
    }

    public void setReported(boolean status)
    {
        this.reported = status;
    }
}
