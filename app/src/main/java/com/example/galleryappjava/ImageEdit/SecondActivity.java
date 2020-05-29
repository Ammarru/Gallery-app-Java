package com.example.galleryappjava.ImageEdit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.galleryappjava.Adapters.FiltersAdapter;
import com.example.galleryappjava.Adapters.RecyclerViewClickInterface;
import com.example.galleryappjava.Adapters.ToolsAdapter;
import com.example.galleryappjava.R;
import com.example.galleryappjava.Storage.Constant;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class SecondActivity extends AppCompatActivity implements RecyclerViewClickInterface {

    RecyclerView recyclerView;
    RecyclerView filterRecyclerView;
    String s[];
    String s2[];
    int filterImages[]=
                    {R.drawable.ic_filter
                    ,R.drawable.ic_filter
                    ,R.drawable.ic_filter
                    ,R.drawable.ic_filter
                    ,R.drawable.ic_filter};
    int images[]=
            {R.drawable.ic_rotate
            ,R.drawable.ic_filter
            ,R.drawable.ic_scale
            ,R.drawable.ic_segment
            ,R.drawable.ic_retouch
            ,R.drawable.ic_mask
            ,R.drawable.ic_3d};
    int pos;
    ImageView  mainImageView;
    File image;
    ImageView saveButton;
    ImageView clearButton;
    File resultImage = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_image);

        initialize();

        //Save Button Functionality
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Inside The Filter View
                if(filterRecyclerView.getVisibility()==View.VISIBLE){
                    new AlertDialog.Builder(SecondActivity.this)
                            .setTitle("Save")
                            .setMessage("Are you sure you want to save this Filter?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    filterRecyclerView.setVisibility(View.INVISIBLE);
                                    recyclerView.setVisibility(View.VISIBLE);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create().show();

                }
                //Inside the Tools View
                else{
                    new AlertDialog.Builder(SecondActivity.this)
                            .setTitle("Save")
                            .setMessage("Are you sure you want to save this Photo?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    BitmapDrawable drawable = (BitmapDrawable) mainImageView.getDrawable();
                                    Bitmap bitmap = drawable.getBitmap();
                                    save(bitmap);
                                    finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create().show();

                }
            }
        });

        //Close Button Functionality
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Inside the Filters View
                if(filterRecyclerView.getVisibility() == View.VISIBLE){
                    new AlertDialog.Builder(SecondActivity.this)
                            .setTitle("Clear")
                            .setMessage("Applied Filter will be cleared.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Glide.with(SecondActivity.this).load(image).into(mainImageView);
                                    filterRecyclerView.setVisibility(View.INVISIBLE);
                                    recyclerView.setVisibility(View.VISIBLE);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create().show();
                }
                //Inside the Tools View
                else{
                    new AlertDialog.Builder(SecondActivity.this)
                            .setTitle("Cancel")
                            .setMessage("All adjustments will be deleted!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create().show();
                }
            }
        });

    }


    //Main Functionality
    @Override
    public void onItemClick(int position) {
        //Edit Tools
        if(filterRecyclerView.getVisibility() == View.INVISIBLE) {
            switch (position) {
                case 0:
                    RotatePic();

                    break;
                case 1:
                    recyclerView.setVisibility(View.GONE);
                    filterRecyclerView.setVisibility(View.VISIBLE);
                    break;

            }
        }
        //Filters
        else{
            switch (position){
                case 0:
                    Glide.with(this).load(image).into(mainImageView);
                    FilterBlackWhite();
                    break;

                case 1:
                    Glide.with(this).load(image).into(mainImageView);
                    FilterBlue();
                    break;
                case 2:
                    Glide.with(this).load(image).into(mainImageView);
                    FilterRed();
                    break;
                case 3:
                    Glide.with(this).load(image).into(mainImageView);
                    FilterNegative();
                    break;
                case 4:
                    Glide.with(this).load(image).into(mainImageView);
                    FilterSepia();
                    break;
            }
        }
    }




    public void RotatePic(){
        mainImageView.invalidate();
        BitmapDrawable drawable = (BitmapDrawable) mainImageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        mainImageView.setImageBitmap(Rotate.rotate(bitmap,90));
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

    public void FilterNegative(){
        mainImageView.invalidate();
        BitmapDrawable drawable = (BitmapDrawable) mainImageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        mainImageView.setImageBitmap(NegativeFilter.ConvertNegative(bitmap));
    }

    public void FilterSepia(){
        mainImageView.invalidate();
        BitmapDrawable drawable = (BitmapDrawable) mainImageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        mainImageView.setImageBitmap(SepiaFilter.FilterSepia(bitmap));
    }

    //Building the Ui
    private void initialize() {
        saveButton = findViewById(R.id.imgSave);
        recyclerView=findViewById(R.id.rvConstraintTools);
        filterRecyclerView = findViewById(R.id.filterTools);
        mainImageView = findViewById(R.id.photoEditorView);
        clearButton = findViewById(R.id.imgClose);
        s=getResources().getStringArray(R.array.Tools_Name);
        s2=getResources().getStringArray(R.array.Filter_Name);

        ToolsAdapter toolsAdapter = new ToolsAdapter(this, s, images,this);
        recyclerView.setAdapter(toolsAdapter);

        FiltersAdapter filtersAdapter = new FiltersAdapter(this, s2, filterImages,this);
        filterRecyclerView.setAdapter(filtersAdapter);

        getData();
    }


    private void save(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
                .asBitmap()
                .load(image)
                .into(mainImageView);
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}
