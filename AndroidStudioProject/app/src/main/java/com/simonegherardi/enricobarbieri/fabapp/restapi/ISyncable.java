package com.simonegherardi.enricobarbieri.fabapp.restapi;

public interface ISyncable {
    public Table GetTable();
    public JSON GetData();
    public String GetIdentifierName();
    public String GetIdentifierValue();
    public void SetIdentifier(String identifier);
    public void SetData(JSON data);
    public void Upload();
    public void Download();
}
