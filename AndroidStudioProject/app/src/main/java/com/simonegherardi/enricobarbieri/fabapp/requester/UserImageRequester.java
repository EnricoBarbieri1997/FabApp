package com.simonegherardi.enricobarbieri.fabapp.requester;

import com.simonegherardi.enricobarbieri.fabapp.restapi.IRESTable;

public abstract class UserImageRequester extends Requester {
    protected Integer userId;

    public void SetUserId(Integer userId)
    {
        this.userId = userId;
    }
}
