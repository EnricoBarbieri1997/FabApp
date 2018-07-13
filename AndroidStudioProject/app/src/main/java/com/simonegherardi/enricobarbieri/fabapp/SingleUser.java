package com.simonegherardi.enricobarbieri.fabapp;

import com.simonegherardi.enricobarbieri.fabapp.restapi.JSON;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSONParseException;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SingleUser extends User implements Comparable<SingleUser> {
    protected PersonalData info;
    protected PostedPhotos postedPhotos;
    protected MyPhotos myPhotos;
    protected List<SingleUser> followedUsers;
    protected List<Group> followedGroups;
    protected List<Photo> likedPhotos;
    protected boolean isPrivate;



    public static SingleUser FromJSON(JSON json)
    {
        SingleUser su = new SingleUser();
        try
        {
            su.Init(json.GetInt("id"), json.GetString("username"), json.GetString("phone"), json.GetString("email"));
        }
        catch (JSONParseException e)
        {
            e.printStackTrace();
        }
        return su;
    }
    private void Init(Integer id, String username, String phone, String email)
    {
        this.id = id;
        this.info = this.info = new PersonalData(phone, email, username, "", "");

    }


    public SingleUser()
    {
        super();
        this.info = new PersonalData();
        this.postedPhotos = new PostedPhotos();
        this.myPhotos = new MyPhotos();
        this.followedGroups = new ArrayList<Group>();
        this.followedUsers = new ArrayList<SingleUser>();
        this.isPrivate = false;
    }

    public SingleUser(String phone, String email, String username, String name, String surname)
    {
        super();
        this.info = new PersonalData(phone, email, username, name, surname);
        this.postedPhotos = new PostedPhotos();
        this.myPhotos = new MyPhotos();
        this.followedGroups = new ArrayList<Group>();
        this.followedUsers = new ArrayList<SingleUser>();
        this.isPrivate = false;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void acceptOrDeny(Photo newPhoto, SingleUser photographer)
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
    public void addPhoto(Photo newPhoto, SingleUser owner)
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

    public void like(Photo likedPhoto)
    {
        likedPhoto.likes++;
        this.likedPhotos.add(likedPhoto);
    }



    public void setPrivate(boolean status)
    {
        this.isPrivate = status;
    }

    public void reportResource(Resource resource)
    {
        resource.setReported(true);
    }

    public void follow(SingleUser newfollowed)
    {
        followedUsers.add(newfollowed);
    }

    public void follow(Group newfollowed)
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

}
