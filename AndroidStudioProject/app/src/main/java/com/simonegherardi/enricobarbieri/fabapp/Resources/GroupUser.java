package com.simonegherardi.enricobarbieri.fabapp.Resources;

import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.IResourceConsumer;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.ResourceResponse;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSON;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSONParseException;

import java.util.ArrayList;
import java.util.List;

public class GroupUser extends User {
    protected String groupName;
    protected ArrayList<MyPhotos> groupPhotos;
    protected List<Integer> members;
    protected List<Integer> admins;


    public static GroupUser FromJSON(JSON json)
    {
        GroupUser g = new GroupUser();
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
    @Override
    public GroupUser Downcast() {
        return (GroupUser)this;
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

    @Override
    public ResourceResponse Upload(IResourceConsumer callback) {
        return null;
    }
}
