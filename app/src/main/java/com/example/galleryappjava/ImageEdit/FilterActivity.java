package com.example.galleryappjava.ImageEdit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    ImageView saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        recyclerView= findViewById(R.id.Filter_imgs);
        mainImageView= findViewById(R.id.photoFilterView);
        saveButton = findViewById(R.id.imgSave);
        s=getResources().getStringArray(R.array.Filter_Name);
        FiltersAdapter filtersAdapter = new FiltersAdapter(this, s, images,this);
        recyclerView.setAdapter(filtersAdapter);

        getData();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }


   private void getData(){
        if (getIntent().hasExtra("Image") ){
            img = (File)getIntent().getSerializableExtra("Image");
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
        switch (position){
            case 0:
                Glide.with(this).load(img).into(mainImageView);
                FilterBlackWhite();
                break;
            case 1:
                Glide.with(this).load(img).into(mainImageView);
                FilterBlue();
                break;
            case 2:
                Glide.with(this).load(img).into(mainImageView);
                FilterRed();
                break;
        }
    }

    public void FilterRed(){
        mainImageView.invalidate();
        BitmapDrawable drawable = (BitmapDrawable) mainImageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        mainImageView.setImageBitmap(Filter.doColorFilter(bitmap,1,0,0));
    }

    public void FilterBlue(){
        mainImageView.invalidate();
        BitmapDrawable drawable = (BitmapDrawable) mainImageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        mainImageView.setImageBitmap(Filter.doColorFilter(bitmap,0,0,1));
    }

    public void FilterBlackWhite(){
        mainImageView.invalidate();
        BitmapDrawable drawable = (BitmapDrawable) mainImageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        mainImageView.setImageBitmap(BlackWhiteFilter.ConvertBlackWhite(bitmap));
    }


}
