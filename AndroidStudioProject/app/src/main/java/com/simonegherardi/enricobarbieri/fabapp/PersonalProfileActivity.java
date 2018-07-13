package com.simonegherardi.enricobarbieri.fabapp;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class PersonalProfileActivity extends AppCompatActivity {

    private SingleUser user;

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

    public void startPersonalDataActivity()
    {
        final Intent startPersonalData = new Intent(getApplicationContext() , PersonalDataActivity.class);
        getApplicationContext().startActivity(startPersonalData);
    }


    public void getUserData()
    {
        // Questa parte verrà sostituita dall'acquisizione dell'id della risorsa
        Bundle b = getIntent().getExtras();
        this.user.info.phone = b.getString("phone_number");
        this.user.info.email = b.getString("email");
        this.user.info.username = b.getString("username");
        this.user.info.name = b.getString("name");
        this.user.info.surname = b.getString("surname");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_profile);

        getUserData();

        TextView profile_username = (TextView) findViewById(R.id.profile_username);
        profile_username.setText(this.user.info.username);


        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<CreateList> createLists = prepareData();
        MyAdapter adapter = new MyAdapter(getApplicationContext(), createLists);
        recyclerView.setAdapter(adapter);

        ImageButton expand = (ImageButton) findViewById(R.id.expand);
        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPersonalDataActivity();
            }
        });

    }
}
