package com.simonegherardi.enricobarbieri.fabapp.restapi;

/**
 * Created by Xxenr on 17/06/2018.
 */

public interface Composer {
    public void Update(String key, String s);
    public void Set(String key, Composer c);
    public void Set(String key, String s);
    public void Set(String key, Integer i) throws Exception;
    public void Set(String s);
    public String GetValue();
}
