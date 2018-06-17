package com.simonegherardi.enricobarbieri.fabapp.restapi;

/**
 * Created by Xxenr on 16/06/2018.
 */

public interface Parser {
    public Parser GetParser(String key) throws Exception;
    public String GetString(String key);
    public Integer GetInt(String key) throws Exception;
    public String GetValue();
}
