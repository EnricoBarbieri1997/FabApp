package com.simonegherardi.enricobarbieri.fabapp.restapi;

/**
 * Created by Xxenr on 16/06/2018.
 */

public abstract class Parser {
    String str="";
    public abstract Parser GetParser(String key) throws Exception;
    public abstract String GetString(String key) throws Exception;
    public abstract Integer GetInt(String key) throws Exception;
    public abstract String GetValue();
}
