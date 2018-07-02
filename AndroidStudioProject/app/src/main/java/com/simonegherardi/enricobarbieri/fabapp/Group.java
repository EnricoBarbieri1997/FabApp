package com.simonegherardi.enricobarbieri.fabapp;

import com.simonegherardi.enricobarbieri.fabapp.restapi.JSON;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSONParseException;

import java.util.ArrayList;
import java.util.List;

public class Group extends User {
    protected String groupName;
    protected ArrayList<MyPhotos> groupPhotos;
    protected List<Integer> members;
    protected List<Integer> admins;


    public static Group FromJSON(JSON json)
    {
        Group g = new Group();
        try
        {
            g.Init(json.GetInt("id"), json.GetString("name"));
        }
        catch (JSONParseException e)
        {
            e.printStackTrace();
        }
        return g;
    }

    private void Init(Integer id, String groupName)
    {
        this.id = id;
        this.groupName = groupName;
    }

    public void removeMember(Integer banned, Integer admin)
    {
        for (Integer member : admins )
        {
            if ( member == admin )
            {
                this.members.remove(banned);
            }
        }
    }
}
