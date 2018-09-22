package com.simonegherardi.enricobarbieri.fabapp.resources;

import com.simonegherardi.enricobarbieri.fabapp.restapi.ISyncable;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSON;
import com.simonegherardi.enricobarbieri.fabapp.restapi.Table;

public class Follow implements ISyncable {
    private int follower;
    private int following;
    public Follow(int follower, int following)
    {
        this.follower = follower;
        this.following = following;
    }
    public Follow(String follower, String following)
    {
        this(Integer.parseInt(follower), Integer.parseInt(following));
    }
    @Override
    public Table GetTable() {
        return Table.Follow;
    }

    @Override
    public JSON GetData() {
        JSON data = new JSON();
        JSON tmp = new JSON();
        try {
            tmp.Set("follower", this.follower);
            tmp.Set("following", this.following);
        } catch (Exception e) {
            e.printStackTrace();
        }
        data.Set("data", tmp);
        return data;
    }

    @Override
    public String GetIdentifierName() {
        return "follower*following";
    }

    @Override
    public String GetIdentifierValue() {
        return this.follower+"*"+this.following;
    }

    @Override
    public void SetIdentifier(String identifier) {
        String[] parts = identifier.split("\\*");
        this.follower = Integer.parseInt(parts[0]);
        this.following = Integer.parseInt(parts[1]);
    }

    @Override
    public void SetData(JSON data) {
        this.follower = Integer.parseInt(data.GetString("follower"));
        this.following = Integer.parseInt(data.GetString("following"));
    }

    @Override
    public void Upload() {

    }

    @Override
    public void Download() {

    }
}
