package com.simonegherardi.enricobarbieri.fabapp.requester;

import com.simonegherardi.enricobarbieri.fabapp.restapi.HttpMethod;
import com.simonegherardi.enricobarbieri.fabapp.restapi.IRESTable;
import com.simonegherardi.enricobarbieri.fabapp.restapi.RESTResponse;
import com.simonegherardi.enricobarbieri.fabapp.restapi.Table;
import com.simonegherardi.enricobarbieri.fabapp.restapi.WebServer;

public class ProfileImageRequester extends UserImageRequester {
    public ProfileImageRequester() {
        super();
    }

    @Override
    public RESTResponse Request() {
        return WebServer.Main().GenericRequest(HttpMethod.GET, Table.userImageId, "userId", userId.toString(), this.callback);
    }
}
