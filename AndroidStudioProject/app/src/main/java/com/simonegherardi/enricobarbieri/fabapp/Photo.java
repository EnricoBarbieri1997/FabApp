package com.simonegherardi.enricobarbieri.fabapp;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSON;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSONParseException;

public class Photo extends Resource implements Comparable<Photo> {

    protected Bitmap img;
    protected int likes;
    protected Integer idPhotographer;
    protected Integer idPhotograpghed;



    public Photo()
    {
        super();
    }

    public static Photo FromJSON(JSON json)
    {
        Photo p = new Photo();
        try
        {
            p.Init(json.GetInt("Id"), json.GetInt("idPhotographer"), json.GetInt("idPhotographed"));
        }
        catch (JSONParseException e)
        {
            e.printStackTrace();
        }
        return p;
    }

    private void Init(Integer id, Integer idPhotographer, Integer idPhotograpghed)
    {
        this.id = id;
        this.idPhotographer = idPhotographer;
        this.idPhotograpghed = idPhotograpghed;
    }

    @Override
    public int compareTo(@NonNull Photo o) {
        if (this.id == o.id)
        {
            return 0;
        } else if (this.id < o.id)
        {
            return -1;
        } else
        {
            return 1;
        }
    }
}
