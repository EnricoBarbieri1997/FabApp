package com.simonegherardi.enricobarbieri.fabapp;

/**
 * Created by Xxenr on 16/06/2018.
 */

public class Utility {
    public static Integer Min(Integer ... nums)
    {
        Integer min = nums[0];
        for (Integer num:nums)
        {
            if(num < min)
            {
                min = num;
            }
        }
        return min;
    }
}
