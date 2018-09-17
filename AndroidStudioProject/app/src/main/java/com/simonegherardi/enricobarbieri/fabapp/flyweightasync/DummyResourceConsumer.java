package com.simonegherardi.enricobarbieri.fabapp.flyweightasync;

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
        }
        if(group == response)
        {
        }
        if(image == response)
        {
        }

        //Log.d("count", this.count.toString());
    }
}
