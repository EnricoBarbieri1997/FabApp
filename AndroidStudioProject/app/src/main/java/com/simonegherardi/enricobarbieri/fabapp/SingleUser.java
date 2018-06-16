package com.simonegherardi.enricobarbieri.fabapp;

import java.util.ArrayList;

public class SingleUser extends User {
    protected PersonalData myInfo;
    protected ArrayList<SingleUser> followedUsers;
    protected ArrayList<Group> followedGroups;
    protected boolean isPrivate;
    protected int iD;

    public SingleUser()
    {
        super();
        this.myInfo = new PersonalData();
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

    public void follow(SingleUser newfollowed)
    {
        followedUsers.add(newfollowed);
    }

    public void follow(Group newfollowed)
    {
        followedGroups.add(newfollowed);
    }


}
