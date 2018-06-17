package com.simonegherardi.enricobarbieri.fabapp.flyweightasync;

import com.simonegherardi.enricobarbieri.fabapp.Resource;

/**
 * Created by Xxenr on 16/06/2018.
 */

public interface IResourceConsumer {
    public void OnResourceReady(Resource resource);
}
