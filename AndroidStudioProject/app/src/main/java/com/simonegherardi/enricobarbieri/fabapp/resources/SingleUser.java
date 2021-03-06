package com.simonegherardi.enricobarbieri.fabapp.resources;

import com.simonegherardi.enricobarbieri.fabapp.PersonalData;
import com.simonegherardi.enricobarbieri.fabapp.restapi.DummyRestable;
import com.simonegherardi.enricobarbieri.fabapp.restapi.HttpMethod;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSON;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSONParseException;
import com.simonegherardi.enricobarbieri.fabapp.restapi.Table;
import com.simonegherardi.enricobarbieri.fabapp.restapi.WebServer;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SingleUser extends User implements Comparable<SingleUser> {
    public PersonalData info = new PersonalData();
    protected PostedPhotos postedPhotos;
    protected MyPhotos myPhotos;
    protected List<SingleUser> followedUsers;
    protected List<GroupUser> followedGroups;
    protected List<Image> likedPhotos;
    protected boolean isPrivate;
    private SingleUser()
    {
        super();
        // aggiungere info this.info = new PersonalData();
        this.postedPhotos = new PostedPhotos();
        this.myPhotos = new MyPhotos();
        this.followedGroups = new ArrayList<GroupUser>();
        this.followedUsers = new ArrayList<SingleUser>();
        this.isPrivate = false;
    }

    @Override
    public Table GetTable() {
        return Table.SingleUser;
    }

    @Override
    public JSON GetData() {
        JSON data = new JSON();
        JSON userData = new JSON();
        JSON singleUserData = new JSON();
        try {
            data.Set("id", id);
            userData.Set("data", data);
            data.Set("phone", this.GetPhone());
            data.Set("password", this.GetPassword());
            data.Set("email", this.GetEmail());
            singleUserData.Set("data", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return singleUserData;
    }

    private String GetPassword() {
        return this.info.password;
    }

    @Override
    public void SetData(JSON data) {
        this.SetEmail(data.GetString("email"));
        this.SetPhone(data.GetString("phone"));
        this.SetUsername(data.GetString("username"));
    }
    @Override
    public void Upload()
    {
        JSON data = new JSON();
        JSON userData = new JSON();
        try {
            data.Set("id", id);
            userData.Set("data", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        WebServer.Main().GenericRequest(HttpMethod.POST, Table.User, userData, new DummyRestable());
    }
    /*@Override
    public ResourceResponse Upload(IResourceConsumer callback) {
        SingleUserUploader singleUserUploader = new SingleUserUploader(this, callback);
        return singleUserUploader.Upload();
    }*/

    public static SingleUser Empty()
    {
        SingleUser singleUser = new SingleUser();
        singleUser.SetId(-1);
        return singleUser;
    }
    public static SingleUser FromJSON(JSON json)
    {
        SingleUser su = new SingleUser();
        try
        {
            su.Init(json.GetInt("id"), json.GetString("phone"), json.GetString("email"), json.GetInt("picture"), json.GetString("password"));
        }
        catch (JSONParseException e)
        {
            e.printStackTrace();
        }
        return su;
    }
    public void Init(Integer id, String phone, String email, int picture, String password)
    {
        this.id = id;
        this.info = this.info = new PersonalData(phone, email, picture, "", password, "", "");

    }
    public void Init(String phone, String email, int picture, String password)
    {
        this.info = this.info = new PersonalData(phone, email, picture, "", password, "", "");
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void acceptOrDeny(Image newPhoto, SingleUser photographer)
    {
        // Display photo
        Scanner scanner = new Scanner(System.in);
        String ris;
        System.out.println("Vuoi accettare la foto? Scrivi 's' o 'n'");
        ris = scanner.nextLine();
        if (ris.compareTo("s") == 0)
        {
            this.myPhotos.Photolist.add(newPhoto);
        } else
        {
            System.out.println("Foto rifiutata\n");
        }

    }
    public void addPhoto(Image newPhoto, SingleUser owner)
    {
        this.postedPhotos.Photolist.add(newPhoto);
        if (owner.isPrivate())
        {
            owner.acceptOrDeny(newPhoto, this);
        }
        else
        {
            owner.myPhotos.Photolist.add(newPhoto);
        }
    }

    public void like(Image likedPhoto)
    {
        likedPhoto.likes++;
        this.likedPhotos.add(likedPhoto);
    }

    public void SetName(String name)
    {
        this.info.name = name;
    }
    public void SetSurname(String surname)
    {
        this.info.surname = surname;
    }

    public void setPrivate(boolean status)
    {
        this.isPrivate = status;
    }

    public void reportResource(Resource resource)
    {
        resource.SetReported(true);
    }

    public void follow(SingleUser newfollowed)
    {
        followedUsers.add(newfollowed);
    }

    public void follow(GroupUser newfollowed)
    {
        followedGroups.add(newfollowed);
    }

    public int compareTo( SingleUser o) {
        if (this.id == o.id)
        {
            return 0;
        } else if (this.id < o.id)
        {
            return -1;
        } else
        {
            return 1;
        }
    }
    public String GetPhone()
    {
        return this.info.phone;
    }
    public String GetUsername()
    {
        return this.info.email.substring(0, this.info.email.indexOf('@'));
    }
    public String GetEmail()
    {
        return this.info.email;
    }
    public int GetPicture()
    {
        return this.info.picture;
    }
    public void SetPhone(String phone)
    {
        this.info.phone = phone;
    }
    public void SetUsername(String username)
    {
        this.info.username = username;
    }
    public void SetEmail(String email)
    {
        this.info.email = email;
    }
}
