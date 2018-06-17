package com.simonegherardi.enricobarbieri.fabapp.restapi;

/**
 * Created by Xxenr on 16/06/2018.
 */

public class JSON extends Parser
{
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
        Integer startIndex = GetIndexOfValueStart(key);
        if((str.charAt(startIndex) != '{' && str.charAt(startIndex) != '['))
        {
            throw new JSONParseException("Not a string");
        }
        Integer graphCount=1;
        Integer currentIndex = startIndex+1;
        do
        {
            Integer c = str.indexOf('}', currentIndex);
            Integer o = str.indexOf('{', currentIndex);
            if(o != -1 && o < c)
            {
                graphCount++;
            }
            if(c != -1)
            {
                graphCount--;
            }
            currentIndex = c+1;
        }while(graphCount != 0);
        JSON result;
        result = new JSON(str.substring(startIndex,currentIndex));

        return result;
    }

    @Override
    public String GetString(String key) throws JSONParseException {
        Integer startIndex = GetIndexOfValueStart(key);
        Integer endIndex = GetIndexOfValueEnd(key);
        String result;
        if((str.charAt(startIndex) == '{' || str.charAt(startIndex) == '['))
        {
            throw new JSONParseException("Not a string");
        }
        result = str.substring(startIndex,endIndex);

        return result;
    }

    @Override
    public Integer GetInt(String key) throws JSONParseException{
        Integer startIndex = GetIndexOfValueStart(key);
        Integer endIndex = GetIndexOfValueEnd(key);
        Integer result;

        try
        {
            result = Integer.parseInt(str.substring(startIndex,endIndex));
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
    private Integer GetIndexOfValueStart(String key)
    {
        Integer index = this.str.indexOf(key)+key.length()+2;
        if(str.charAt(index) == '"')
        {
            index++;
        }
        return index;
    }
    private Integer GetIndexOfValueEnd(String key)
    {
        Integer startIndex = GetIndexOfValueStart(key);
        Integer index = MinIndex(str.indexOf(',',startIndex),str.indexOf('"',startIndex),str.indexOf('}',startIndex),str.indexOf(']',startIndex));
        return index;
    }
    private Integer MinIndex(Integer ... nums)
    {
        Integer min = nums[0];
        for (Integer num:nums)
        {
            if((num> -1) && ((num < min)||(min < 0)))
            {
                min = num;
            }
        }
        return min;
    }
}
