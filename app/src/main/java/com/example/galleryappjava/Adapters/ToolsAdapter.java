package com.example.galleryappjava.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.galleryappjava.ImageEdit.Rotate;
import com.example.galleryappjava.ImageEdit.SecondActivity;
import com.example.galleryappjava.R;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


public class ToolsAdapter extends RecyclerView.Adapter<ToolsAdapter.MyViewHolder> {

    String data[];
    int images[];
    Context context;
    RecyclerViewClickInterface recyclerViewClickInterface;
    public ToolsAdapter(Context ct, String titles[], int tools[],RecyclerViewClickInterface recyclerViewClickInterface){
        context = ct;
        data = titles;
        images = tools;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.editting_tools,parent,false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.title.setText(data[position]);
        holder.tool.setImageResource(images[position]);

    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView tool;
        ConstraintLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txtTool);
            tool = itemView.findViewById(R.id.imgToolIcon);
            mainLayout = itemView.findViewById(R.id.fulltool);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });
        }

    }
}
