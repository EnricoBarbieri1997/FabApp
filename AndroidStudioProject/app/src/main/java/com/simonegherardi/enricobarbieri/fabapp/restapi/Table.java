package com.simonegherardi.enricobarbieri.fabapp.restapi;

/**
 * Created by Xxenr on 16/06/2018.
 */

public enum Table {
    Resource("Resource"),
    Image("Image"),
    User("User"),
    Group("GroupUser"),
    SingleUser("SingleUser"),
    userImageId("userImageId"),
    userSearchList("userSearchList"),
    upload("upload");

    private String table;

    Table(String table) {
        this.table = table;
    }

    public String GetTable() {
        return this.table;
    }
}
