package com.simonegherardi.enricobarbieri.fabapp;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public class Photo extends Resource implements Comparable<Photo> {

    protected Bitmap img;
    protected int idPhoto;
    protected int likes;
    protected int iDPhotographer;
    protected int iDPhotograpghed;



    public Photo(String pathname, int iDPhotographer, int idPhotographed)
    {
        super();
        this.iDPhotographer=iDPhotographer;
        this.iDPhotograpghed=idPhotographed;
    }


    @Override
    public int compareTo(@NonNull Photo o) {
        if (this.idPhoto == o.idPhoto)
        {
            return 0;
        } else if (this.idPhoto < o.idPhoto)
        {
            return -1;
        } else
        {
            return 1;
        }
    }
}
