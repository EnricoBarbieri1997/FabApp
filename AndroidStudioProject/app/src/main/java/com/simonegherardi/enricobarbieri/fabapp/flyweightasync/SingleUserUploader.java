package com.simonegherardi.enricobarbieri.fabapp.flyweightasync;

import com.simonegherardi.enricobarbieri.fabapp.Resources.SingleUser;
import com.simonegherardi.enricobarbieri.fabapp.restapi.HttpMethod;
import com.simonegherardi.enricobarbieri.fabapp.restapi.IRESTable;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSON;
import com.simonegherardi.enricobarbieri.fabapp.restapi.RESTResponse;
import com.simonegherardi.enricobarbieri.fabapp.restapi.Table;
import com.simonegherardi.enricobarbieri.fabapp.restapi.WebServer;

public class SingleUserUploader implements IRESTable
{
    SingleUser singleUser;
    IResourceConsumer callback;
    RESTResponse resourceId;
    RESTResponse resource;
    Integer id;
    ResourceResponse resourceResponse;

    public SingleUserUploader(SingleUser singleUser, IResourceConsumer callback)
    {
        this.singleUser = singleUser;
        this.callback = callback;
    }

    public ResourceResponse Upload()
    {
        resourceId = WebServer.Main().GenericRequest(HttpMethod.PUT, Table.Resource, this);
        resourceResponse = new ResourceResponse();
        return resourceResponse;
    }

    @Override
    public void Success(RESTResponse response) {
        if(response == resourceId)
        {
            id = Integer.parseInt(response.GetResponse());
            JSON data = new JSON();
            JSON userData = new JSON();
            JSON singleUserData = new JSON();
            try {
                data.Set("id", id);
                userData.Set("data", data);
                data.Set("phone", singleUser.GetPhone());
                data.Set("username", singleUser.GetUsername());
                data.Set("email", singleUser.GetEmail());
                singleUserData.Set("data", data);
            } catch (Exception e) {
                e.printStackTrace();
            }
            WebServer.Main().GenericRequest(HttpMethod.PUT, Table.User, userData,this);
            this.resource = WebServer.Main().GenericRequest(HttpMethod.PUT, Table.SingleUser, singleUserData,this);
        }
        else if (this.resource == response)
        {
            singleUser.SetId(id);
            resourceResponse.SetResource(this.singleUser);
            resourceResponse.SetResourceType(ResourcesTypes.SingleUser);
            this.callback.OnResourceReady(resourceResponse);
        }
    }

    @Override
    public void Error(RESTResponse result) {

    }
}
