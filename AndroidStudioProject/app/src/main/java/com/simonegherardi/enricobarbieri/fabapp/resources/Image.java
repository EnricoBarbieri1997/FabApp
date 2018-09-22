package com.simonegherardi.enricobarbieri.fabapp.resources;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.simonegherardi.enricobarbieri.fabapp.restapi.JSON;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSONParseException;
import com.simonegherardi.enricobarbieri.fabapp.restapi.Table;

public class Image extends Resource implements Comparable<Image> {

    protected Bitmap img;
    protected String url;
    protected int likes;
    protected Integer idPhotographer;
    protected Integer idPhotographed;



    public Image()
    {
        super();
    }

    @Override
    public Table GetTable() {
        return Table.Image;
    }

    @Override
    public JSON GetData() {
        JSON data = new JSON();
        JSON json = new JSON();

        try {
            data.Set("id", id);
            data.Set("url", this.GetUrl());
            data.Set("photographed", this.GetPhotographed());
            data.Set("photographer", this.GetPhotographer());
            json.Set("data", data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        json.Set("data", data);
        return json;
    }

    @Override
    public void SetData(JSON data) {
        this.url = data.GetString("url");
        try {
            this.SetId(data.GetInt("id"));
            this.idPhotographer = data.GetInt("photographer");
            this.idPhotographer = data.GetInt("photographed");
        } catch (JSONParseException e) {
            e.printStackTrace();
        }

    }
    public static Image FromJSON(JSON json)
    {
        Image p = new Image();
        try
        {
            p.Init(json.GetInt("id"), json.GetInt("photographer"), json.GetInt("photographed"), json.GetString("url"));
        }
        catch (JSONParseException e)
        {
            e.printStackTrace();
        }
        return p;
    }

    private void Init(Integer id, Integer idPhotographer, Integer idPhotograpghed, String url)
    {
        this.id = id;
        this.url = url;
        this.idPhotographer = idPhotographer;
        this.idPhotographed = idPhotograpghed;
    }

    @Override
    public int compareTo(@NonNull Image o) {
        if (this.id == o.id)
        {
            return 0;
        } else if (this.id < o.id)
        {
            return 1;
        } else
        {
            return -1;
        }
    }

    public String GetUrl()
    {
        return this.url;
    }
    public int GetPhotographer()
    {
        return this.idPhotographer;
    }
    public int GetPhotographed()
    {
        return this.idPhotographed;
    }
    public void SetUrl(String url)
    {
        this.url = url;
    }
    public void SetPhotographed(int id)
    {
        this.idPhotographed = id;
    }
    public void SetPhotographer(int id)
    {
        this.idPhotographer = id;
    }
}
