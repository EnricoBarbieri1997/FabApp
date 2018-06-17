package com.simonegherardi.enricobarbieri.fabapp.restapi;

/**
 * Created by Xxenr on 16/06/2018.
 */

public class WebServer
{
    static private WebServer singleton;
    String url = "";
    public static WebServer Main()
    {
        if(WebServer.singleton == null)
        {
            WebServer.singleton = new WebServer();
            WebServer.singleton.SetUrl("https://www.casagioia.org/resttest/");
        }
        return WebServer.singleton;
    }
    public void SetUrl(String url)
    {
        if(!url.endsWith("/"))
        {
            url += "/";
        }
        this.url = url;
    }
    public void GenericRequest(HttpMethod method, Table table, String attribute, String value, String min, String max, JSON body, IRESTable callback)
    {
        RESTRequest rr = new RESTRequest();
        rr.Init(body, method, callback, "");
        rr.setUrl(this.url, table.GetTable(), attribute, value, min, max);
        this.StartRequest(rr);
    }
    public void GenericRequest(HttpMethod method, Table table, String attribute, String value, JSON body, IRESTable callback)
    {
        RESTRequest rr = new RESTRequest();
        rr.Init(body, method, callback, "");
        rr.setUrl(this.url, table.GetTable(), attribute, value);
        this.StartRequest(rr);
    }
    public void GenericRequest(HttpMethod method, Table table, String attribute, String value, String min, String max, IRESTable callback)
    {
        RESTRequest rr = new RESTRequest();
        rr.Init(new JSON(""), method, callback, "");
        rr.setUrl(this.url, table.GetTable(), attribute, value, min, max);
        this.StartRequest(rr);
    }
    public void GenericRequest(HttpMethod method, Table table, String attribute, String value, IRESTable callback)
    {
        RESTRequest rr = new RESTRequest();
        rr.Init(new JSON(""), method, callback, "");
        rr.setUrl(this.url, table.GetTable(), attribute, value);
        this.StartRequest(rr);
    }
    public void GenericRequest(HttpMethod method, Table table, IRESTable callback)
    {
        RESTRequest rr = new RESTRequest();
        rr.Init(new JSON(""), method, callback, "");
        rr.setUrl(this.url, table.GetTable());
        this.StartRequest(rr);
    }
    public void GenericRequest(HttpMethod method, Table table, JSON body, IRESTable callback)
    {
        RESTRequest rr = new RESTRequest();
        rr.Init(body, method, callback, "");
        rr.setUrl(this.url, table.GetTable());
        this.StartRequest(rr);
    }
    private void StartRequest(RESTRequest rr)
    {
        Thread t = new Thread(rr);
        t.start();
    }
}
