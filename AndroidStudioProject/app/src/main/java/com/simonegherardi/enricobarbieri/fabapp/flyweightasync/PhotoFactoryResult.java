package com.simonegherardi.enricobarbieri.fabapp.flyweightasync;

import com.simonegherardi.enricobarbieri.fabapp.Photo;
import com.simonegherardi.enricobarbieri.fabapp.restapi.IRESTable;
import com.simonegherardi.enricobarbieri.fabapp.Resource;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSON;

public class PhotoFactoryResult implements IRESTable {
    IResourceConsumer callback;
    public PhotoFactoryResult(IResourceConsumer callback)
    {
        this.callback = callback;
    }
    @Override
    public void Get(String result) {
        Resource su = ResourceFlyweightAsync.Intern(Photo.FromJSON(new JSON(result)));
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
