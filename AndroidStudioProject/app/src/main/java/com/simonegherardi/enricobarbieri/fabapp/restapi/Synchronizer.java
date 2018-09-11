package com.simonegherardi.enricobarbieri.fabapp.restapi;

import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.IResourceConsumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Synchronizer implements IRESTable{
    ISyncable item;
    boolean isBusy;
    ArrayList<ISyncObserver> observers;
    HashMap<RESTResponse, HttpMethod> requests;
    public Synchronizer(ISyncable item)
    {
        this.item = item;
        requests = new HashMap<>();
        observers = new ArrayList<>();
    }
    public Synchronizer(ISyncable item, ISyncObserver observer)
    {
        this(item);
        observers.add(observer);
    }

    public void Upload()
    {
        this.NotifyStart(HttpMethod.POST);
        this.item.Upload();
        requests.put(WebServer.Main().GenericRequest(HttpMethod.POST, item.GetTable(), item.GetData(), this), HttpMethod.POST);
    }
    public void Update()
    {
        this.NotifyStart(HttpMethod.PUT);
        requests.put(WebServer.Main().GenericRequest(HttpMethod.PUT, item.GetTable(), item.GetIdentifierName(), item.GetIdentifierValue(), item.GetData(), this), HttpMethod.PUT);
    }
    public void Download()
    {
        this.NotifyStart(HttpMethod.GET);
        requests.put(WebServer.Main().GenericRequest(HttpMethod.GET, item.GetTable(), item.GetIdentifierName(), item.GetIdentifierValue(), this), HttpMethod.GET);
    }
    public void Delete()
    {
        this.NotifyStart(HttpMethod.DELETE);
        requests.put(WebServer.Main().GenericRequest(HttpMethod.DELETE, item.GetTable(), item.GetIdentifierName(), item.GetIdentifierValue(), this), HttpMethod.DELETE);
    }

    @Override
    public void Success(RESTResponse response) {
        switch(requests.get(response))
        {
            case GET:
                this.item.SetData(new JSON(response.GetResponse()));
                this.item.Download();
                break;
        }
        NotifyCompletition(response, true);
    }

    @Override
    public void Error(RESTResponse response)
    {
        NotifyCompletition(response, false);
    }
    protected void NotifyCompletition(RESTResponse response, boolean result)
    {
        HttpMethod method = requests.get(response);
        for (ISyncObserver observer:observers) {
            switch (method) {
                case POST:
                    observer.UploadComplete(result);
                    break;
                case PUT:
                    observer.UpdateComplete(result);
                    break;
                case GET:
                    observer.DownloadComplete(result);
                    break;
                case DELETE:
                    observer.DeleteComplete(result);
                    break;
            }
        }
    }
    protected void NotifyStart(HttpMethod method)
    {
        for (ISyncObserver observer:observers) {
            switch (method) {
                case POST:
                    observer.UploadStart();
                    break;
                case PUT:
                    observer.UpdateStart();
                    break;
                case GET:
                    observer.DownloadStart();
                    break;
                case DELETE:
                    observer.DeleteStart();
                    break;
            }
        }
    }
}
