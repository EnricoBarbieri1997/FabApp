package com.simonegherardi.enricobarbieri.fabapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.DummyResourceConsumer;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.ResourceFlyweightAsync;
import com.simonegherardi.enricobarbieri.fabapp.restapi.DummyRestable;
import com.simonegherardi.enricobarbieri.fabapp.restapi.HttpMethod;
import com.simonegherardi.enricobarbieri.fabapp.restapi.Table;
import com.simonegherardi.enricobarbieri.fabapp.restapi.WebServer;

import java.util.ArrayList;

import static android.graphics.Color.BLACK;

public class MainActivity extends AppCompatActivity {


    private final String image_titles[] = {
            "Img1",
            "Img2",
            "Img3",
            "Img4",
            "Img5",
            "Img6",
            "Img7",
            "Img8",
            "Img9",
            "Img10",
    };

    private final Integer image_ids[] = {
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img6,
            R.drawable.img7,
            R.drawable.img8,
            R.drawable.img9,
            R.drawable.img10,

    };

    private ArrayList<CreateList> prepareData(){

        ArrayList<CreateList> theimage = new ArrayList<>();
        for(int i = 0; i< image_titles.length; i++){
            CreateList createList = new CreateList();
            createList.setImage_title(image_titles[i]);
            createList.setImage_ID(image_ids[i]);
            theimage.add(createList);
        }
        return theimage;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        setContentView(R.layout.personal_profile);


        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<CreateList> createLists = prepareData();
        MyAdapter adapter = new MyAdapter(getApplicationContext(), createLists);
        recyclerView.setAdapter(adapter);











        DummyResourceConsumer a = new DummyResourceConsumer();
        a.single = ResourceFlyweightAsync.Main().GetSingleUser(4, a);
        a.group = ResourceFlyweightAsync.Main().GetGroup(2, a);
        a.image = ResourceFlyweightAsync.Main().GetPhoto(3, a);
    }
}
