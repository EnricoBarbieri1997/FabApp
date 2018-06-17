package com.simonegherardi.enricobarbieri.fabapp.restapi;

/**
 * Created by Xxenr on 16/06/2018.
 */

public interface IRESTable
{
    public void Get(String result);
    public void Post(String result);
    public void Put(String result);
    public void Delete(String result);
    public void Error(String result);
}
