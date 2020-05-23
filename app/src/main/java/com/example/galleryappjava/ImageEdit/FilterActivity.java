package com.example.galleryappjava.ImageEdit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

        recyclerView= findViewById(R.id.Filter_imgs);
        mainImageView= findViewById(R.id.photoFilterView);
        s=getResources().getStringArray(R.array.Filter_Name);

        FiltersAdapter filtersAdapter = new FiltersAdapter(this, s, images,this);
        recyclerView.setAdapter(filtersAdapter);


        getData();

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
