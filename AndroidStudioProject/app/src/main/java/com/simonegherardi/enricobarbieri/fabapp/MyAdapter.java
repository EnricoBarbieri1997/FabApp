package com.simonegherardi.enricobarbieri.fabapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<CreateList> galleryList;
    private Context context;

    public MyAdapter(Context context, ArrayList<CreateList> galleryList) {
        this.galleryList = galleryList;
        this.context = context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyAdapter.ViewHolder viewHolder, int i) {
        final int id = galleryList.get(i).getImage_ID();
        viewHolder.title.setText(galleryList.get(i).getImage_title());
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide
                .with(context)
                .load(galleryList.get(i).getImage_ID())
                .thumbnail(0.1f)
                .into(viewHolder.img);

        viewHolder.img.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Intent fullscreen;
            try {
                fullscreen = new Intent(context , DisplayFullScreen.class);
                fullscreen.putExtra("image_id",id);
                context.startActivity(fullscreen);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context,"Error in Intent/Extra",Toast.LENGTH_SHORT).show();
            }
            // Toast.makeText(context,"Image",Toast.LENGTH_SHORT).show();

        }
        });
    }


    @Override
    public int getItemCount() {
        return this.galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView title;
        private final ImageView img;
        public ViewHolder(View view) {
            super(view);

            title = (TextView)view.findViewById(R.id.title);
            img = (ImageView) view.findViewById(R.id.img);
        }
    }
}