package com.simonegherardi.enricobarbieri.fabapp.requester;

import com.simonegherardi.enricobarbieri.fabapp.restapi.IRESTable;
import com.simonegherardi.enricobarbieri.fabapp.restapi.RESTResponse;

public abstract class Requester {
    protected IRESTable callback;
    public Requester(IRESTable callback)
    {
        this.callback = callback;
    }
    public Requester()
    {
        this(null);
    }
    public abstract RESTResponse Request();
    public void SetCallback(IRESTable callback)
    {
        this.callback = callback;
    }
}
