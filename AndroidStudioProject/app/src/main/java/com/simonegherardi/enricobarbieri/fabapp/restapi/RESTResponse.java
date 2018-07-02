package com.simonegherardi.enricobarbieri.fabapp.restapi;

/**
 * Created by Xxenr on 30/06/2018.
 */

public class RESTResponse
{
    private String response;
    private HttpMethod requestType;

    public String GetResponse()
    {
        return this.response;
    }
    public void SetResponse(String response)
    {
        this.response = response;
    }
    public HttpMethod GetRequestType()
    {
        return this.requestType;
    }
    public void SetRequestType(HttpMethod requestType)
    {
        this.requestType = requestType;
    }
}
