package com.example.galleryappjava.ImageEdit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.galleryappjava.Adapters.RecyclerViewClickInterface;
import com.example.galleryappjava.Adapters.ToolsAdapter;
import com.example.galleryappjava.Base.BaseActivity;
import com.example.galleryappjava.R;
import com.example.galleryappjava.Storage.Constant;

import java.io.File;
import java.io.FileOutputStream;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SecondActivity extends AppCompatActivity implements RecyclerViewClickInterface {

    RecyclerView recyclerView;
    String s[];
    int images[]={R.drawable.ic_rotate,R.drawable.ic_filter,R.drawable.ic_scale,R.drawable.ic_segment,
            R.drawable.ic_retouch,R.drawable.ic_mask,R.drawable.ic_3d};
    int pos;
    ImageView  mainImageView;
    File image;
    ImageView saveButton;
    FileOutputStream outStream = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_image);

        saveButton = findViewById(R.id.imgSave);
        recyclerView=findViewById(R.id.rvConstraintTools);
        mainImageView = findViewById(R.id.photoEditorView);
        s=getResources().getStringArray(R.array.Tools_Name);

        ToolsAdapter toolsAdapter = new ToolsAdapter(this, s, images,this);
        recyclerView.setAdapter(toolsAdapter);

        getData();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void getData(){
        if (getIntent().hasExtra("myImage") ){
            pos= getIntent().getIntExtra("myImage",1);
            image = Constant.allMediaList.get(getIntent().getIntExtra("myImage",1));
            setData(image);
        }
    }


    private void setData(File image) {

        Glide.with(this)
                .load(image)
                .into(mainImageView);
    }


    @Override
    public void onItemClick(int position) {
        switch (position){
            case 0:
                RotatePic();
                break;
            case 1:
                Intent intent = new Intent(this, FilterActivity.class);
                intent.putExtra("Image",image);
                startActivityForResult(intent,1);
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                String uriString = data.getStringExtra("Image");
                Uri uri = Uri.parse(uriString);
                File resultImage = new File(uri.getPath());
                setData(resultImage);
            }
        }
    }

    public void RotatePic(){
        mainImageView.invalidate();
        BitmapDrawable drawable = (BitmapDrawable) mainImageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        mainImageView.setImageBitmap(Rotate.rotate(bitmap,90));
    }
}
