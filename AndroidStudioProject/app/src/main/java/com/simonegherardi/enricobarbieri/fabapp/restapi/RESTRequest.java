package com.simonegherardi.enricobarbieri.fabapp.restapi;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Xxenr on 16/06/2018.
 */

public class RESTRequest implements Runnable
{
    private String body;
    private String url;
    private String method;
    private IRESTable callback;
    private RESTResponse response;

    public RESTResponse Init(JSON body, HttpMethod method, IRESTable callback, String baseUrl, String ... params)
    {
        setBody(body);
        setMethod(method);
        setCallback(callback);
        setUrl(baseUrl, params);
        this.response = new RESTResponse();
        this.response.SetRequestType(method);
        return this.response;
    }
    public void setBody(JSON json) {
        this.body = json.GetValue();
    }

    public void setUrl(String base, String ... params) {
        this.url = base;
        for (String s:params)
        {
            this.url+=(s+"/");
        }
    }

    public void setMethod(HttpMethod method) {
        this.method = method.GetMethod();
    }

    public void setCallback(IRESTable callback) {
        this.callback = callback;
    }

    @Override
    public void run() {
        String urlParameters = body;
        String targetURL = this.url;
        HttpsURLConnection connection = null;
        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            if(!method.equals(HttpMethod.GET.GetMethod()))
            {
                connection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");

                connection.setRequestProperty("Content-Length",
                        Integer.toString(urlParameters.getBytes().length));
                connection.setRequestProperty("Content-Language", "en-US");

                connection.setUseCaches(false);
                connection.setDoOutput(true);

                //Send request
                DataOutputStream wr = new DataOutputStream (
                        connection.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.close();
            }

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append(System.getProperty("line.separator"));
            }
            response.deleteCharAt(response.length()-1);
            rd.close();
            //return response.toString();
            String result = response.toString();
            this.response.SetResponse(result);
            this.callback.Success(this.response);
        } catch (Exception e) {
            e.printStackTrace();
            //return null;
            this.response.SetResponse(e.getMessage());
            callback.Error(this.response);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
