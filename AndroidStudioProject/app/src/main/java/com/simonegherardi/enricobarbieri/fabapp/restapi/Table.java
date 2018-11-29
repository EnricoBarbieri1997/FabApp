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
    Follow("Follow"),
    userImageId("userImageId"),
    userFriendsImageId("userFriendsImageId"),
    userSearchList("userSearchList"),
    pendingFriendRequests("pendingFriendRequests"),
    upload("upload"),
    connect("connect");

    private String table;

    Table(String table) {
        this.table = table;
    }

    public String GetTable() {
        return this.table;
    }
}
