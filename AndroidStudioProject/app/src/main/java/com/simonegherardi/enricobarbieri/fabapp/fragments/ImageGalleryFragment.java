package com.simonegherardi.enricobarbieri.fabapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.simonegherardi.enricobarbieri.fabapp.R;
import com.simonegherardi.enricobarbieri.fabapp.activity.FragmentAwareActivity;
import com.simonegherardi.enricobarbieri.fabapp.adapter.ImageGalleryAdapter;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.IResourceConsumer;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.ResourceFlyweightAsync;
import com.simonegherardi.enricobarbieri.fabapp.flyweightasync.ResourceResponse;
import com.simonegherardi.enricobarbieri.fabapp.resources.Image;
import com.simonegherardi.enricobarbieri.fabapp.restapi.HttpMethod;
import com.simonegherardi.enricobarbieri.fabapp.restapi.IRESTable;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSON;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSONParseException;
import com.simonegherardi.enricobarbieri.fabapp.restapi.RESTResponse;
import com.simonegherardi.enricobarbieri.fabapp.restapi.Table;
import com.simonegherardi.enricobarbieri.fabapp.restapi.WebServer;

import java.util.ArrayList;

public class ImageGalleryFragment extends IntegratedFragment implements IResourceConsumer, IRESTable
{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Image> imageList;
    private Integer userId;
    private Integer imageCount = 0;
    public SwipeRefreshLayout imageRefresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_gallery, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageList = new ArrayList<>();

        SetUpRyclerView();

        userId = getArguments().getInt(getString(R.string.idKey), 0);

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

    public void SetUpRyclerView()
    {
        adapter = new ImageGalleryAdapter(getActivity().getApplicationContext(), imageList);
        SetUpRyclerView(R.id.imageGallery, 2, adapter);

    }
    @Override
    public void OnResourceReady(ResourceResponse response)
    {
        Image image = (Image)response.GetResource();
        imageCount--;
        if(!imageList.contains(image))
        {
            imageList.add(image);
        }
        if(imageCount <= 0)
        {
            imageRefresh.setRefreshing(false);
            UpdateRecyclerView(adapter);
        }
    }
    public RESTResponse UpdateImageList()
    {
        return WebServer.Main().GenericRequest(HttpMethod.GET, Table.userImageId, "userId", userId.toString(), this);
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
        int id = -1;
        try
        {
            id = imagesId.GetInt("id");
            imageCount++;
            ResourceFlyweightAsync.Main().GetPhoto(id, this);
        }
        catch (Exception e1)
        {
            while(imagesId.HasNext())
            {
                try {
                    id = imagesId.Next().GetInt("id");
                    imageCount++;
                    ResourceFlyweightAsync.Main().GetPhoto(id, this);
                } catch (JSONParseException e) {
                    e.printStackTrace();
                }
            }
        }
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
