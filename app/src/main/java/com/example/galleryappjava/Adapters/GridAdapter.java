package com.example.galleryappjava.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.galleryappjava.ImageEdit.SecondActivity;
import com.example.galleryappjava.R;
import com.example.galleryappjava.Storage.Constant;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyViewHolder> {

    Context context;


    public GridAdapter(Context ct){
        context=ct;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        //we will load thumbnail using glid library
        Uri uri = Uri.fromFile(Constant.allMediaList.get(position));

        Glide.with(context)
                .load(uri).thumbnail(0.1f)
                .into(((MyViewHolder)holder).image);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SecondActivity.class);
                intent.putExtra("myImage", position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Constant.allMediaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        CardView mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.img_id);
            mainLayout = itemView.findViewById(R.id.cardview_id);
        }
    }
}
