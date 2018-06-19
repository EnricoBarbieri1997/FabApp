package com.simonegherardi.enricobarbieri.fabapp.flyweightasync;

import com.simonegherardi.enricobarbieri.fabapp.Group;
import com.simonegherardi.enricobarbieri.fabapp.Resource;
import com.simonegherardi.enricobarbieri.fabapp.SingleUser;
import com.simonegherardi.enricobarbieri.fabapp.restapi.IRESTable;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSON;

public class GroupFactoryResult implements IRESTable {
    IResourceConsumer callback;
    public GroupFactoryResult(IResourceConsumer callback)
    {
        this.callback = callback;
    }
    @Override
    public void Get(String result) {
        Resource su = ResourceFlyweightAsync.Intern(Group.FromJSON(new JSON(result)));
        callback.OnResourceReady(su);
    }

    @Override
    public void Post(String result) {

    }

    @Override
    public void Put(String result) {

    }

    @Override
    public void Delete(String result) {

    }

    @Override
    public void Error(String result) {

    }
}
