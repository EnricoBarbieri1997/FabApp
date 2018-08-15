package com.simonegherardi.enricobarbieri.fabapp.Resources;

import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.IResourceConsumer;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.ResourceFlyweightAsync;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.ResourceResponse;

abstract public class Resource {
    protected Integer id;
    protected boolean reported;

    public Resource()
    {
        this.reported = false;
    }

    public void SetReported(boolean status)
    {
        this.reported = status;
    }
    public Integer GetId()
    {
        return id;
    }
    public void SetId(Integer id)
    {
        this.id = id;
    }
    public abstract ResourceResponse Upload(IResourceConsumer callback);
    public abstract <T extends Resource> T Downcast();
}
