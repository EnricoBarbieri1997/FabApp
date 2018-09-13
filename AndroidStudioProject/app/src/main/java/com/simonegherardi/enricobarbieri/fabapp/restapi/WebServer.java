package com.simonegherardi.enricobarbieri.fabapp.restapi;

/**
 * Created by Xxenr on 16/06/2018.
 */

public class WebServer
{
    static private WebServer singleton;
    String url = "";
    String base = "";
    String basePath = "";
    public static WebServer Main()
    {
        if(WebServer.singleton == null)
        {
            WebServer.singleton = new WebServer();
            WebServer.singleton.SetUrl("https://www.casagioia.org/resttest/");
            WebServer.singleton.SetBase("www.casagioia.org");
            WebServer.singleton.SetBasePath("resttest");
        }
        return WebServer.singleton;
    }

    private void SetBasePath(String basePath) {
        this.basePath = basePath;
    }

    private void SetBase(String base) {
        this.base = base;
    }

    public void SetUrl(String url)
    {
        if(!url.endsWith("/"))
        {
            url += "/";
        }
        this.url = url;
    }
    public RESTResponse GenericRequest(HttpMethod method, Table table, String attribute, String value, String min, String max, JSON body, IRESTable callback)
    {
        RESTRequest rr = new RESTRequest();
        RESTResponse response = rr.Init(body, method, callback, "");
        rr.setUrl(this.url, table.GetTable(), attribute, value, min, max);
        this.StartRequest(rr);
        return response;
    }
    public RESTResponse GenericRequest(HttpMethod method, Table table, String attribute, String value, JSON body, IRESTable callback)
    {
        RESTRequest rr = new RESTRequest();
        RESTResponse response = rr.Init(body, method, callback, "");
        rr.setUrl(this.url, table.GetTable(), attribute, value);
        this.StartRequest(rr);
        return response;
    }
    public RESTResponse GenericRequest(HttpMethod method, Table table, String attribute, String value, String min, String max, IRESTable callback)
    {
        RESTRequest rr = new RESTRequest();
        RESTResponse response = rr.Init(new JSON(""), method, callback, "");
        rr.setUrl(this.url, table.GetTable(), attribute, value, min, max);
        this.StartRequest(rr);
        return response;
    }
    public RESTResponse GenericRequest(HttpMethod method, Table table, String attribute, String value, IRESTable callback)
    {
        RESTRequest rr = new RESTRequest();
        RESTResponse response = rr.Init(new JSON(""), method, callback, "");
        rr.setUrl(this.url, table.GetTable(), attribute, value);
        this.StartRequest(rr);
        return response;
    }
    public RESTResponse GenericRequest(HttpMethod method, Table table, IRESTable callback)
    {
        RESTRequest rr = new RESTRequest();
        RESTResponse response = rr.Init(new JSON(""), method, callback, "");
        rr.setUrl(this.url, table.GetTable());
        this.StartRequest(rr);
        return response;
    }
    public RESTResponse GenericRequest(HttpMethod method, Table table, JSON body, IRESTable callback)
    {
        RESTRequest rr = new RESTRequest();
        RESTResponse response = rr.Init(body, method, callback, "");
        rr.setUrl(this.url, table.GetTable());
        this.StartRequest(rr);
        return response;
    }
    private void StartRequest(RESTRequest rr)
    {
        Thread t = new Thread(rr);
        t.start();
    }
}
