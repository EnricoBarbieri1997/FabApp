package com.simonegherardi.enricobarbieri.fabapp.flyweightasync;

import android.util.Log;

import com.simonegherardi.enricobarbieri.fabapp.Resources.GroupUser;
import com.simonegherardi.enricobarbieri.fabapp.Resources.Photo;
import com.simonegherardi.enricobarbieri.fabapp.Resources.SingleUser;

/**
 * Created by Xxenr on 02/07/2018.
 */

public class DummyResourceConsumer implements IResourceConsumer {
    public ResourceResponse single;
    public ResourceResponse group;
    public ResourceResponse image;
    public Integer count = 0;
    @Override
    public void OnResourceReady(ResourceResponse response) {
        if(single == response)
        {
            Log.d("id", response.GetResource().GetId().toString());
            /*SingleUser su = (SingleUser)(response.GetResource());
            System.out.println();
            count++;*/
        }
        if(group == response)
        {
            GroupUser g = (GroupUser)(response.GetResource());
            System.out.println();
            count++;
        }
        if(image == response)
        {
            Photo p = (Photo)(response.GetResource());
            System.out.println();
            count++;
        }

        //Log.d("count", this.count.toString());
    }
}
