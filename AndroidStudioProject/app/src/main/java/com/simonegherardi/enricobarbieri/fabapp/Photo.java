package com.simonegherardi.enricobarbieri.fabapp;

import com.simonegherardi.enricobarbieri.fabapp.restapi.HttpMethod;
import com.simonegherardi.enricobarbieri.fabapp.restapi.IRESTable;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSON;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSONParseException;
import com.simonegherardi.enricobarbieri.fabapp.restapi.Table;
import com.simonegherardi.enricobarbieri.fabapp.restapi.WebServer;

public class Photo extends Resource{

    protected User photographed;
    protected SingleUser photographer;
    protected String url;
    public Photo(Integer id, User photographed, SingleUser photographer, String url)
    {
        this.id = id;
        this.photographed = photographed;
        this.photographer = photographer;
        this.url = url;
    }
    public static void DownloadById(Integer id, IRESTable handler)
    {
        WebServer.Main().GenericRequest(HttpMethod.GET, Table.Image, "id", id.toString(), handler);
    }
    public static Photo FromJSON(JSON json)
    {
        Photo photo = null;
        try {
            photo = new Photo(json.GetInt("id"), null, null, json.GetString("url"));
        } catch (JSONParseException e) {
            e.printStackTrace();
        }
        return photo;
    }
    public void addTag (Tag newTag)
    {

    }
}
