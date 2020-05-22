package com.example.galleryappjava.ImageEdit;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.galleryappjava.Adapters.RecyclerViewClickInterface;
import com.example.galleryappjava.Adapters.ToolsAdapter;
import com.example.galleryappjava.Base.BaseActivity;
import com.example.galleryappjava.R;
import com.example.galleryappjava.Storage.Constant;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SecondActivity extends AppCompatActivity implements RecyclerViewClickInterface {

    RecyclerView recyclerView;
    String s[];
    int images[]={R.drawable.ic_rotate,R.drawable.ic_filter,R.drawable.ic_scale,R.drawable.ic_segment,
            R.drawable.ic_retouch,R.drawable.ic_mask,R.drawable.ic_3d};

    ImageView  mainImageView;
    File imagePos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_image);


        recyclerView=findViewById(R.id.rvConstraintTools);
        mainImageView = findViewById(R.id.photoEditorView);
        s=getResources().getStringArray(R.array.Tools_Name);

        ToolsAdapter toolsAdapter = new ToolsAdapter(this, s, images,this);
        recyclerView.setAdapter(toolsAdapter);

        getData();

    }

    private void getData(){
        if (getIntent().hasExtra("myImage") ){
            imagePos = Constant.allMediaList.get(getIntent().getIntExtra("myImage",1));
            setData();
        }
    }


    private void setData() {

        Glide.with(this)
                .load(imagePos)
                .into(mainImageView);
    }

    @Override
    public void onItemClick(int position) {
        switch (position){
            case 0:
            RotatePic();
        }
    }

    public void RotatePic(){
        mainImageView.invalidate();
        BitmapDrawable drawable = (BitmapDrawable) mainImageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        mainImageView.setImageBitmap(Rotate.rotate(bitmap,90));
    }
}
