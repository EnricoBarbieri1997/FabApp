package com.simonegherardi.enricobarbieri.fabapp.restapi;

import android.graphics.Point;

/**
 * Created by Xxenr on 16/06/2018.
 */

public class JSON implements Parser, Composer
{
    String str="";
    public JSON()
    {
        this.str = "{}";
    }
    public JSON(String str)
    {
        if(!(str.endsWith("}") || str.endsWith("]")))
        {
            int endIndex = str.lastIndexOf("}");
            if(str.lastIndexOf("]") > endIndex)
            {
                endIndex=str.lastIndexOf("]");
            }
            if(endIndex > -1)
            {
                str = str.substring(0, endIndex+1);
            }
        }
        this.str = str;
    }

    @Override
    public Parser GetParser(String key) throws JSONParseException{
        Point indexes = GetIndexes(key);
        if((str.charAt(indexes.x) != '{' && str.charAt(indexes.y) != '[')) {
            throw new JSONParseException("Not a parser");
        }
        JSON result;
        result = new JSON(str.substring(indexes.x,indexes.y));

        return result;
    }

    @Override
    public String GetString(String key){
        Point indexes = GetIndexes(key);
        String result;
        result = str.substring(indexes.x,indexes.y);

        return result;
    }

    @Override
    public Integer GetInt(String key) throws JSONParseException{
        Point indexes = GetIndexes(key);
        Integer result;
        try
        {
            result = Integer.parseInt(str.substring(indexes.x,indexes.y));
        }
        catch (Exception e)
        {
            throw new JSONParseException("Not an int");
        }

        return result;
    }

    public String GetValue()
    {
        return this.str;
    }
    private Point GetIndexes(String key)
    {
        Point index;
        Integer start = 0;
        Integer graph = 0;
        do {
            start = this.str.indexOf(key, start+1)+key.length()+2;
            graph = this.str.indexOf('{', graph+1);
        }while(graph != -1 && graph < start);

        Integer end = start+1;
        char ch = str.charAt(start);
        switch(ch)
        {
            case '{':
            case '[':
                Integer count=1;
                do
                {
                    Integer c = str.indexOf(ch+2, end);
                    Integer o = str.indexOf(ch, end);
                    if(o != -1 && o < c)
                    {
                        count++;
                    }
                    if(c != -1)
                    {
                        count--;
                    }
                    end = c+1;
                }while(count != 0);
                break;
            case '"':
                end = this.str.indexOf('"', start+1);
                start++;
                break;
            default:
                end = this.str.indexOf(',',start);
                if(end == -1 || end < this.str.indexOf('}',start))
                {
                    end = this.str.indexOf('}',start);
                }
                break;
        }
        index = new Point(start, end);
        return index;
    }

    @Override
    public void Update(String key, String s) {
        Point indexes = GetIndexes(key);
        if(indexes.x > -1)
        {
            str = this.str.substring(0,indexes.x) + s + this.str.substring(indexes.y, this.str.length());
        }
    }

    @Override
    public void Set(String key, Composer c){
        String str = "";
        Integer endIndex = this.str.lastIndexOf('}');
        String comma = "";
        if(this.str.charAt(endIndex-1) != '{')
        {
            comma = ",";
        }
        str = this.str.substring(0,endIndex) + comma + "\"" + key + "\":" + c.GetValue() + "}";

        this.str = str;
    }

    @Override
    public void Set(String key, String s){
        String str = "";
        Integer endIndex = this.str.lastIndexOf('}');
        String comma = "";
        if(this.str.charAt(endIndex-1) != '{')
        {
            comma = ",";
        }
        str = this.str.substring(0,endIndex) + comma + "\"" + key + "\":\"" + s + "\"}";

        this.str = str;
    }

    @Override
    public void Set(String key, Integer i) throws Exception {
        String str = "";
        Integer endIndex = this.str.lastIndexOf('}');
        String comma = "";
        if(this.str.charAt(endIndex-1) != '{')
        {
            comma = ",";
        }
        str = this.str.substring(0,endIndex) + comma + "\"" + key + "\":" + i.toString() + "}";

        this.str = str;
    }

    @Override
    public void Set(String s) {
        this.str = s;
    }
}
