package com.simonegherardi.enricobarbieri.fabapp;

import java.util.ArrayList;

public class PostedPhotos{
    protected ArrayList<Photo>  Photolist;

    public void deletePhoto(Photo deleted)
    {
        deleted.photograpghed.myPhotos.Photolist.remove(deleted);
        deleted.photographer.postedPhotos.Photolist.remove(deleted);
    }
}
