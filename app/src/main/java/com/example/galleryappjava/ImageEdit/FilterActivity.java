package com.example.galleryappjava.ImageEdit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.galleryappjava.Adapters.FiltersAdapter;
import com.example.galleryappjava.Adapters.RecyclerViewClickInterface;
import com.example.galleryappjava.Adapters.ToolsAdapter;
import com.example.galleryappjava.R;
import com.example.galleryappjava.Storage.Constant;

import java.io.File;

public class FilterActivity extends AppCompatActivity implements RecyclerViewClickInterface {

    RecyclerView recyclerView;
    String s[];
    int images[]={R.drawable.ic_filter,R.drawable.ic_filter,R.drawable.ic_filter};
    ImageView mainImageView;
    File img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        recyclerView.findViewById(R.id.Filter_imgs);
        mainImageView.findViewById(R.id.photoFilterView);
        s=getResources().getStringArray(R.array.Filter_Name);

        FiltersAdapter filtersAdapter = new FiltersAdapter(this, s, images,this);
        recyclerView.setAdapter(filtersAdapter);




    }


   private void getData(){
        if (getIntent().hasExtra("Image") ){
            img = Constant.allMediaList.get(getIntent().getIntExtra("Image",1));
            setData();
        }
    }

    private void setData() {
        Glide.with(this)
                .load(img)
                .into(mainImageView);
    }


    @Override
    public void onItemClick(int position) {

    }
}
