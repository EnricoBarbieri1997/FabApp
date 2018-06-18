package com.simonegherardi.enricobarbieri.fabapp;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

public class Photo extends Resource implements Comparable<Photo> {

    protected Bitmap img;
    protected Integer id;
    protected int likes;
    protected SingleUser photographer;
    protected SingleUser photograpghed;



    public Photo(String pathname, SingleUser photographer, SingleUser photographed)
    {
        super();
        this.photographer=photographer;
        this.photograpghed=photographed;
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
