package com.simonegherardi.enricobarbieri.fabapp.flyweightasync;

import com.simonegherardi.enricobarbieri.fabapp.Group;
import com.simonegherardi.enricobarbieri.fabapp.Photo;
import com.simonegherardi.enricobarbieri.fabapp.Resource;
import com.simonegherardi.enricobarbieri.fabapp.SingleUser;
import com.simonegherardi.enricobarbieri.fabapp.restapi.HttpMethod;
import com.simonegherardi.enricobarbieri.fabapp.restapi.IRESTable;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSON;
import com.simonegherardi.enricobarbieri.fabapp.restapi.RESTResponse;
import com.simonegherardi.enricobarbieri.fabapp.restapi.Table;
import com.simonegherardi.enricobarbieri.fabapp.restapi.WebServer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Xxenr on 16/06/2018.
 */

public class ResourceFlyweightAsync implements IRESTable{
    private ConcurrentMap<Integer, Resource> resources = new ConcurrentHashMap<Integer, Resource>();
    private ConcurrentMap<RESTResponse, IResourceConsumer> requestsCallbacks = new ConcurrentHashMap<RESTResponse, IResourceConsumer>();
    private ConcurrentMap<RESTResponse, ResourceResponse> requestsResponses = new ConcurrentHashMap<RESTResponse, ResourceResponse>();
    static private ResourceFlyweightAsync singleton;
    public static ResourceFlyweightAsync Main()
    {
        if(ResourceFlyweightAsync.singleton == null)
        {
            ResourceFlyweightAsync.singleton = new ResourceFlyweightAsync();
        }
        return ResourceFlyweightAsync.singleton;
    }
    public ResourceResponse GetSingleUser(Integer id, IResourceConsumer callback)
    {
        ResourceResponse response = new ResourceResponse();
        response.SetResourceType(ResourcesTypes.SingleUser);
        if(this.resources.containsKey(id))
        {
            response.SetResource(this.resources.get(id));
            callback.OnResourceReady(response);
        }
        else
        {
            RESTResponse resp = WebServer.Main().GenericRequest(HttpMethod.GET, Table.SingleUser, "id",id.toString(), this);
            this.requestsCallbacks.put(resp, callback);
            this.requestsResponses.put(resp, response);
        }
        return response;
    }

    public ResourceResponse GetGroup(Integer id, IResourceConsumer callback)
    {
        ResourceResponse response = new ResourceResponse();
        response.SetResourceType(ResourcesTypes.GroupUser);
        if(this.resources.containsKey(id))
        {
            response.SetResource(this.resources.get(id));
            callback.OnResourceReady(response);
        }
        else
        {
            RESTResponse resp = WebServer.Main().GenericRequest(HttpMethod.GET, Table.Group, "id",id.toString(), this);
            this.requestsCallbacks.put(resp, callback);
            this.requestsResponses.put(resp, response);
        }
        return response;
    }

    public ResourceResponse GetPhoto(Integer id, IResourceConsumer callback)
    {
        ResourceResponse response = new ResourceResponse();
        response.SetResourceType(ResourcesTypes.Photo);
        if(this.resources.containsKey(id))
        {
            response.SetResource(this.resources.get(id));
            callback.OnResourceReady(response);
        }
        else
        {
            RESTResponse resp = WebServer.Main().GenericRequest(HttpMethod.GET, Table.Image, "id",id.toString(), this);
            this.requestsCallbacks.put(resp, callback);
            this.requestsResponses.put(resp, response);
        }
        return response;
    }


    public Resource Intern(Resource resource)
    {
        Resource result = this.resources.get(resource.getId());
        if(result == null)
        {
            result = this.resources.putIfAbsent(resource.getId(),resource);
            if (result == null)
            {
                result = resource;
            }
        }
        return result;
    }

    @Override
    public void Success(RESTResponse response)
    {
        String result = response.GetResponse();
        IResourceConsumer callback = this.requestsCallbacks.get(response);
        Resource resource;
        ResourceResponse resp = this.requestsResponses.get(response);
        switch(resp.GetResourceType())
        {
            case SingleUser:
                resource = ResourceFlyweightAsync.Main().Intern(SingleUser.FromJSON(new JSON(result)));
                break;
            case GroupUser:
                resource = ResourceFlyweightAsync.Main().Intern(Group.FromJSON(new JSON(result)));
                break;
            case Photo:
                resource = ResourceFlyweightAsync.Main().Intern(Photo.FromJSON(new JSON(result)));
                break;
            default:
                resource = null;
        }
        this.requestsCallbacks.remove(response);
        this.requestsResponses.remove(response);
        resp.SetResource(resource);
        callback.OnResourceReady(resp);
    }

    @Override
    public void Error(RESTResponse result) {

    }
}
