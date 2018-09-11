package com.simonegherardi.enricobarbieri.fabapp.Resources;

import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.IResourceConsumer;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.ResourceFlyweightAsync;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.ResourceResponse;
import com.simonegherardi.enricobarbieri.fabapp.restapi.HttpMethod;
import com.simonegherardi.enricobarbieri.fabapp.restapi.IRESTable;
import com.simonegherardi.enricobarbieri.fabapp.restapi.ISyncable;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSON;
import com.simonegherardi.enricobarbieri.fabapp.restapi.RESTResponse;
import com.simonegherardi.enricobarbieri.fabapp.restapi.Table;
import com.simonegherardi.enricobarbieri.fabapp.restapi.WebServer;

abstract public class Resource implements ISyncable
{
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

    public abstract Table GetTable();
    public abstract JSON GetData();
    public String GetIdentifierName()
    {
        return "Id";
    }
    public String GetIdentifierValue()
    {
        return (this.GetId().toString());
    }
    public void SetIdentifier(String identifier)
    {
        this.SetId(Integer.parseInt(identifier));
    }
    public abstract void SetData(JSON data);

    public void Upload()
    {}
    public void Download()
    {}

    /*public static RESTResponse GetNewId(IRESTable callback)
    {
        return WebServer.Main().GenericRequest(HttpMethod.PUT, Table.Resource, callback);
    }
    public RESTResponse Delete(IRESTable callback)
    {
        return WebServer.Main().GenericRequest(HttpMethod.DELETE, Table.Resource, "Id", this.id.toString(), callback);
    }*/
}
