package com.simonegherardi.enricobarbieri.fabapp.flyweightasync;

import com.simonegherardi.enricobarbieri.fabapp.Resource;
import com.simonegherardi.enricobarbieri.fabapp.SingleUser;
import com.simonegherardi.enricobarbieri.fabapp.restapi.HttpMethod;
import com.simonegherardi.enricobarbieri.fabapp.restapi.IRESTable;
import com.simonegherardi.enricobarbieri.fabapp.restapi.Table;
import com.simonegherardi.enricobarbieri.fabapp.restapi.WebServer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Xxenr on 16/06/2018.
 */

public class ResourceFlyweightAsync{
    private static ConcurrentMap<Integer, Resource> resources = new ConcurrentHashMap<Integer, Resource>();
    public static void GetSingleUser(Integer id, IResourceConsumer callback)
    {
        if(ResourceFlyweightAsync.resources.containsKey(id))
        {
            callback.OnResourceReady(ResourceFlyweightAsync.resources.get(id));
        }
        else
        {
            SingleUserFactoryResult sufr = new SingleUserFactoryResult(callback);
            WebServer.Main().GenericRequest(HttpMethod.GET, Table.SingleUser, "id",id.toString(), sufr);
        }
    }
    public static Resource Intern(Resource resource)
    {
        Resource result = ResourceFlyweightAsync.resources.get(resource.getId());
        if(result == null)
        {
            result = ResourceFlyweightAsync.resources.putIfAbsent(resource.getId(),resource);
            if (result == null)
            {
                result = resource;
            }
        }
        return result;
    }
}
