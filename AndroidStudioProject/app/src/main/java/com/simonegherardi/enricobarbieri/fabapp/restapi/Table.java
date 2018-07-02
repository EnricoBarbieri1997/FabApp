package com.simonegherardi.enricobarbieri.fabapp.restapi;

import com.simonegherardi.enricobarbieri.fabapp.SingleUser;

/**
 * Created by Xxenr on 16/06/2018.
 */

public enum Table {
    Resource("Resource"),
    Image("Image"),
    Group("Groupuser"),
    SingleUser("Single");

    private String table;

    Table(String table) {
        this.table = table;
    }

    public String GetTable() {
        return this.table;
    }
}
