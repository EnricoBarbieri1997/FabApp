package com.simonegherardi.enricobarbieri.fabapp.flyweightasync;

import com.simonegherardi.enricobarbieri.fabapp.Resource;
import com.simonegherardi.enricobarbieri.fabapp.restapi.RESTResponse;

/**
 * Created by Xxenr on 02/07/2018.
 */

public class ResourceResponse {
    private Resource resource;
    private ResourcesTypes resourceType;
    public Resource GetResource()
    {
        return this.resource;
    }
    public void SetResource(Resource resource)
    {
        this.resource = resource;
    }
    public ResourcesTypes GetResourceType()
    {
        return this.resourceType;
    }
    public void SetResourceType(ResourcesTypes resourceType)
    {
        this.resourceType = resourceType;
    }
}
