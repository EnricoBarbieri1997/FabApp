package com.simonegherardi.enricobarbieri.fabapp.restapi;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.List;

/**
 * Created by Xxenr on 16/06/2018.
 */

public class WebServer
{
    static private WebServer singleton;
    String url = "";
    String base = "";
    String basePath = "";
    String sessionId = "";
    String headerName = "cookie";
    String propertyName = "PHPSESSID";

    IRESTable connectCallback;
    IRESTable connectHandler = new IRESTable() {
        int fieldLength = 26;
        @Override
        public void Success(RESTResponse response) {
            List<String> setCookie = response.GetHeader("set-cookie");
            for (String field:setCookie) {
                int start = field.indexOf(propertyName);
                if(start != -1)
                {
                    int idStart = start + propertyName.length() + 1;
                    sessionId = field.substring(idStart, idStart + fieldLength);
                    connectCallback.Success(response);
                    return;
                }
            }
            connectCallback.Error(response);
        }

        @Override
        public void Error(RESTResponse response) {
            connectCallback.Error(response);
        }
    };
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
    public RESTResponse Connect(IRESTable callback)
    {
        this.connectCallback = callback;
        return GenericRequest(HttpMethod.GET, Table.connect, connectHandler);
    }
    public RESTResponse Login(JSON body, IRESTable callback)
    {
        return GenericRequest(HttpMethod.POST, Table.connect, body, callback);
    }
    public RESTResponse GenericRequest(HttpMethod method, Table table, String attribute, String value, String min, String max, JSON body, IRESTable callback)
    {
        RESTRequest rr = new RESTRequest();
        RESTResponse response = rr.Init(body, method, callback, "");
        AddSessionId(rr);
        rr.setUrl(this.url, table.GetTable(), attribute, value, min, max);
        this.StartRequest(rr);
        return response;
    }
    public RESTResponse GenericRequest(HttpMethod method, Table table, String attribute, String value, JSON body, IRESTable callback)
    {
        RESTRequest rr = new RESTRequest();
        RESTResponse response = rr.Init(body, method, callback, "");
        AddSessionId(rr);
        rr.setUrl(this.url, table.GetTable(), attribute, value);
        this.StartRequest(rr);
        return response;
    }
    public RESTResponse GenericRequest(HttpMethod method, Table table, String attribute, String value, String min, String max, IRESTable callback)
    {
        RESTRequest rr = new RESTRequest();
        RESTResponse response = rr.Init(new JSON(""), method, callback, "");
        AddSessionId(rr);
        rr.setUrl(this.url, table.GetTable(), attribute, value, min, max);
        this.StartRequest(rr);
        return response;
    }
    public RESTResponse GenericRequest(HttpMethod method, Table table, String attribute, String value, IRESTable callback)
    {
        RESTRequest rr = new RESTRequest();
        RESTResponse response = rr.Init(new JSON(""), method, callback, "");
        AddSessionId(rr);
        rr.setUrl(this.url, table.GetTable(), attribute, value);
        this.StartRequest(rr);
        return response;
    }
    public RESTResponse GenericRequest(HttpMethod method, Table table, IRESTable callback)
    {
        RESTRequest rr = new RESTRequest();
        RESTResponse response = rr.Init(new JSON(""), method, callback, "");
        AddSessionId(rr);
        rr.setUrl(this.url, table.GetTable());
        this.StartRequest(rr);
        return response;
    }
    public RESTResponse GenericRequest(HttpMethod method, Table table, JSON body, IRESTable callback)
    {
        RESTRequest rr = new RESTRequest();
        RESTResponse response = rr.Init(body, method, callback, "");
        AddSessionId(rr);
        rr.setUrl(this.url, table.GetTable());
        this.StartRequest(rr);
        return response;
    }
    public RESTResponse GenericRequest(HttpMethod method, Table table, JSON body, String contentType, IRESTable callback)
    {
        RESTRequest rr = new RESTRequest();
        RESTResponse response = rr.Init(body, method, callback, "");
        AddSessionId(rr);
        rr.setUrl(this.url, table.GetTable());
        rr.setContentType(contentType);
        this.StartRequest(rr);
        return response;
    }
    public RESTResponse BitmapUpload(Bitmap photo, IRESTable callback)
    {
        RESTBitmapUpload rr = new RESTBitmapUpload();
        RESTResponse response = rr.Init(photo, callback);
        this.StartRequest(rr);
        return response;
    }
    private void AddSessionId(RESTRequest rr)
    {
        if(!sessionId.equals("")) {

            rr.AddHeader(headerName, propertyName + "=" + sessionId);
        }
    }
    private void StartRequest(Runnable rr)
    {
        Thread t = new Thread(rr);
        t.start();
    }
}
