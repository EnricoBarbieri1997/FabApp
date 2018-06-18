package com.simonegherardi.enricobarbieri.fabapp;

import java.util.ArrayList;
import java.util.List;

public class Group extends User {
    protected Integer iD;
    protected String groupName;
    protected ArrayList<MyPhotos> groupPhotos;
    protected List<SingleUser> members;
    protected List<SingleUser> admins;

    public void removeMember(SingleUser banned, SingleUser admin)
    {
        for (SingleUser member : admins )
        {
            if ( member == admin )
            {
                this.members.remove(banned);
            }
        }
    }
}
