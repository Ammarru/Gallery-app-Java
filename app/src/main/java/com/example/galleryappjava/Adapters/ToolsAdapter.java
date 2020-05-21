package com.example.galleryappjava.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.galleryappjava.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ToolsAdapter extends RecyclerView.Adapter<ToolsAdapter.MyViewHolder> {

    String data[];
    int images[];
    Context context;

    public ToolsAdapter(Context ct, String titles[], int tools[]){
        context = ct;
        data = titles;
        images = tools;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.editting_tools,parent,false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txtTool);
            tool = itemView.findViewById(R.id.imgToolIcon);
        }

    }
}
