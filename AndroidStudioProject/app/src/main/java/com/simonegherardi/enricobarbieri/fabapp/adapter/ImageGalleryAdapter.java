package com.simonegherardi.enricobarbieri.fabapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.simonegherardi.enricobarbieri.fabapp.DisplayFullScreen;
import com.simonegherardi.enricobarbieri.fabapp.GlideApp;
import com.simonegherardi.enricobarbieri.fabapp.MyGlideApp;
import com.simonegherardi.enricobarbieri.fabapp.R;
import com.simonegherardi.enricobarbieri.fabapp.resources.Image;

import java.util.ArrayList;

public class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.ImageViewHolder>
{
    private ArrayList<Image> galleryList;
    private Context context;

    public ImageGalleryAdapter(Context context, ArrayList<Image> galleryList) {
        this.galleryList = galleryList;
        this.context = context;
    }

    @Override
    public ImageGalleryAdapter.ImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_layout, viewGroup, false);
        ImageViewHolder imageViewHolder = new ImageGalleryAdapter.ImageViewHolder(view);
        return imageViewHolder;
    }


    public void displayFullScreen(int id_photo)
    {
        final Intent fullscreen = new Intent(context , DisplayFullScreen.class);
        fullscreen.putExtra("image_id",id_photo);
        context.startActivity(fullscreen);
    }

    @Override
    public void onBindViewHolder(final ImageGalleryAdapter.ImageViewHolder viewHolder, int i) {
        final int id = galleryList.get(i).GetId();
        viewHolder.image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide
                .with(context)
                .load(galleryList.get(i).GetUrl())
                .thumbnail(0.1f)
                .into(viewHolder.image);

        viewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFullScreen(id);
            }
        });
    }


    @Override
    public int getItemCount() {
        return this.galleryList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        public ImageViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.cellImage);
        }
    }
}
