package com.simonegherardi.enricobarbieri.fabapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.simonegherardi.enricobarbieri.fabapp.R;
import com.simonegherardi.enricobarbieri.fabapp.adapter.ImageGalleryAdapter;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.IResourceConsumer;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.ResourceFlyweightAsync;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.ResourceResponse;
import com.simonegherardi.enricobarbieri.fabapp.requester.UserImageRequester;
import com.simonegherardi.enricobarbieri.fabapp.resources.Image;
import com.simonegherardi.enricobarbieri.fabapp.restapi.IRESTable;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSON;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSONParseException;
import com.simonegherardi.enricobarbieri.fabapp.restapi.RESTResponse;
import com.simonegherardi.enricobarbieri.fabapp.utility.SortedArrayList;

import java.util.ArrayList;

public class ImageGalleryFragment extends IntegratedFragment implements IResourceConsumer, IRESTable
{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Integer colCount = 2;
    private SortedArrayList<Image> imageList;
    private Integer imageCount = 0;
    public SwipeRefreshLayout imageRefresh;
    public UserImageRequester imageRequester;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_gallery, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageList = new SortedArrayList<>();

        SetUpRyclerView();

        imageRefresh = this.parentActivity.findViewById(R.id.imageGalleryRefresh);

        imageRefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        UpdateImageList();
                    }
                }
        );

        UpdateImageList();
    }
    public void SetColumnCount(Integer col)
    {
        this.colCount = col;
    }
    public void SetUpRyclerView()
    {
        adapter = new ImageGalleryAdapter(this.parentActivity, imageList);
        SetUpRyclerView(R.id.imageGallery, colCount, adapter);

    }



    @Override
    public void OnResourceReady(ResourceResponse response)
    {
        Image image = (Image)response.GetResource();
        imageCount--;
        if(!imageList.contains(image))
        {
            imageList.insertSorted(image);
        }
        if(imageCount <= 0) {
            SetRefreshing(false);
            UpdateRecyclerView(adapter);
        }
    }
    public RESTResponse UpdateImageList()
    {
        SetRefreshing(true);
        return imageRequester.Request();
    }
    @Override
    public void Success(RESTResponse response) {
        RequestImages(new JSON(response.GetResponse()));
    }

    @Override
    public void Error(RESTResponse response) {

    }
    private void RequestImages(JSON imagesId)
    {
        if(imagesId.GetValue().equals(""))
        {
            SetRefreshing(false);
        }
        int id = -1;
        try
        {
            id = imagesId.GetInt("id");
            imageCount++;
            ResourceFlyweightAsync.Main().GetPhoto(id, this);
        }
        catch (Exception e1)
        {
            ArrayList<Integer> ids = new ArrayList<>();
            while(imagesId.HasNext())
            {
                try {
                    id = imagesId.Next().GetInt("id");
                    imageCount++;
                    ids.add(id);
                } catch (JSONParseException e) {
                    e.printStackTrace();
                }
            }
            for(int i = 0; i < ids.size(); i++)
            {
                ResourceFlyweightAsync.Main().GetPhoto(ids.get(i), this);
            }
        }
    }

    public void SetRefreshing(final boolean status)
    {
        this.parentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageRefresh.setRefreshing(status);
            }
        });
    }
    /*
     * Listen for option item selections so that we receive a notification
     * when the user requests a refresh by selecting the refresh action bar item.
     */
    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // Check if user triggered a refresh:
            case R.id.image_gallery_menu_refresh:

                // Signal SwipeRefreshLayout to start the progress indicator
                imageRefresh.setRefreshing(true);

                // Start the refresh background task.
                // This method calls setRefreshing(false) when it's finished.
                UpdateImageList();

                return true;
        }

        // User didn't trigger a refresh, let the superclass handle this action
        return super.onOptionsItemSelected(item);

    }*/
}
