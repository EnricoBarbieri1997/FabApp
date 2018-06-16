package com.simonegherardi.enricobarbieri.fabapp;

import com.simonegherardi.enricobarbieri.fabapp.restapi.JSON;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSONParseException;

import java.util.ArrayList;
import java.util.List;

public class SingleUser extends User {
    protected PersonalData info;
    protected PostedPhotos postedPhotos;
    protected MyPhotos myPhotos;
    protected List<SingleUser> followedUsers;
    protected List<Group> followedGroups;
    protected boolean isPrivate;
    public static SingleUser FromJSON(JSON json)
    {
        SingleUser su = new SingleUser();
        try
        {
            su.Init(json.GetInt("Id"), json.GetString("username"), json.GetString("phone"), json.GetString("email"));
        }
        catch (JSONParseException e)
        {
            e.printStackTrace();
        }
        return su;
    }
    private void Init(Integer id, String username, String phone, String email)
    {
        this.id = id;
        this.info.username = username;
        this.info.phone = phone;
        this.info.email = email;

    }
    private SingleUser()
    {
        super();
        this.info = new PersonalData();
        this.postedPhotos = new PostedPhotos();
        this.myPhotos = new MyPhotos();
        this.followedGroups = new ArrayList<Group>();
        this.followedUsers = new ArrayList<SingleUser>();
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
