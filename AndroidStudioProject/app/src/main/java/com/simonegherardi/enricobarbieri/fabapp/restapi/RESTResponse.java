package com.simonegherardi.enricobarbieri.fabapp.restapi;

import java.util.List;
import java.util.Map;

/**
 * Created by Xxenr on 30/06/2018.
 */

public class RESTResponse
{
    private String response;
    private HttpMethod requestType;
    private Map<String,List<String>> headers;

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
    public void SetHeaders( Map<String,List<String>> headers)
    {
        this.headers = headers;
    }
    public  Map<String,List<String>> GetHeaders()
    {
        return this.headers;
    }
    public List<String> GetHeader(String header)
    {
        return headers.get(header);
    }
}
