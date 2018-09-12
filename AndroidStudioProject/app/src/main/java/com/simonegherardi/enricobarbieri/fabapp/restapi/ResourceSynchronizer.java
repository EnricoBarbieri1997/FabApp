package com.simonegherardi.enricobarbieri.fabapp.restapi;

public class ResourceSynchronizer extends Synchronizer {
    RESTResponse idResponse;
    public ResourceSynchronizer(ISyncable item)
    {
        super(item);
    }
    public ResourceSynchronizer(ISyncable item, ISyncObserver syncObserver)
    {
        super(item, syncObserver);
    }
    @Override
    public void Upload()
    {
        idResponse = WebServer.Main().GenericRequest(HttpMethod.POST, Table.Resource, this);
    }

    @Override
    public void Success(RESTResponse response) {
        if(response == idResponse)
        {
            this.item.SetIdentifier(idResponse.GetResponse());
            super.Upload();
            return;
        }
        super.Success(response);
    }

    @Override
    public void Error(RESTResponse response) {
        if(response == idResponse)
        {
            this.NotifyCompletition(response, false);
        }
        super.Error(response);
    }
}
