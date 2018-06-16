package com.simonegherardi.enricobarbieri.fabapp.restapi;

/**
 * Created by Xxenr on 16/06/2018.
 */

public class DummyRestable implements IRESTable{

    @Override
    public void Get(String result) {
        System.out.println(result);
    }

    @Override
    public void Post(String result) {
        System.out.println(result);
    }

    @Override
    public void Put(String result) {
        System.out.println(result);
    }

    @Override
    public void Delete(String result) {
        System.out.println(result);
    }

    @Override
    public void Error(String result) {
        System.out.println(result);
    }
}
