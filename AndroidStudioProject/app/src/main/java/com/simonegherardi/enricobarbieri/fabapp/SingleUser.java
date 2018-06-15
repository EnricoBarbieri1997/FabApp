package com.simonegherardi.enricobarbieri.fabapp;

public class SingleUser extends User {
    protected PersonalData myInfo;
    protected Album myAlbum;
    protected boolean isPrivate;

    public SingleUser()
    {
        super();
        this.myInfo = new PersonalData();
        this.myAlbum = new Album();
        this.isPrivate = false;
    }

    public void setPrivate(boolean status)
    {
        this.isPrivate = status;
    }

    public void reportResource(Resource resource)
    {
        resource.setReported(true);
    }
}
