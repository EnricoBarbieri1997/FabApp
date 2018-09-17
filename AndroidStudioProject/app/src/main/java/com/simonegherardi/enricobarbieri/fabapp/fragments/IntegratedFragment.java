package com.simonegherardi.enricobarbieri.fabapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.simonegherardi.enricobarbieri.fabapp.MainActivity;
import com.simonegherardi.enricobarbieri.fabapp.R;
import com.simonegherardi.enricobarbieri.fabapp.activity.FragmentAwareActivity;
import com.simonegherardi.enricobarbieri.fabapp.adapter.ImageGalleryAdapter;
import com.simonegherardi.enricobarbieri.fabapp.resources.Image;

import java.util.ArrayList;

public class IntegratedFragment extends Fragment {
    protected FragmentAwareActivity parentActivity;
    protected void DisplayToast(final String s)
    {
        this.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }
    protected void SetTextTextView(final TextView textView, final String text)
    {
        this.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                textView.setText(text);
            }
        });
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.parentActivity = (FragmentAwareActivity)context;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(context.toString() + " must be of type FragmentAwareActivity");
        }
    }
    public void SetImageInView(final Image image, final ImageView imageView)
    {
        this.parentActivity.runOnUiThread(new Runnable() {
            public void run() {
                Glide.with(getActivity().getApplicationContext())
                        .load(image.GetUrl())
                        .apply(new RequestOptions().dontAnimate())
                        .thumbnail(0.1f)
                        .into(imageView);
            }
        });
    }
    public void SetUpRyclerView(int viewResource, int cols, RecyclerView.Adapter adapter)
    {
        final FragmentAwareActivity activity = this.parentActivity;
        /*activity.runOnUiThread(new Runnable() {
            public void run() {*/
        RecyclerView recyclerView = (RecyclerView)activity.findViewById(viewResource);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(activity.getApplicationContext(),cols);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
            /*}
        });*/
    }
    protected void UpdateRecyclerView(final RecyclerView.Adapter adapter)
    {
        this.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }
}
