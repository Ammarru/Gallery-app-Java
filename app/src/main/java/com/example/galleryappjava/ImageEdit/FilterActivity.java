package com.example.galleryappjava.ImageEdit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.galleryappjava.Adapters.FiltersAdapter;
import com.example.galleryappjava.Adapters.RecyclerViewClickInterface;
import com.example.galleryappjava.Adapters.ToolsAdapter;
import com.example.galleryappjava.R;
import com.example.galleryappjava.Storage.Constant;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class FilterActivity extends AppCompatActivity implements RecyclerViewClickInterface {
    int STORAGE_PERMISSION_CODE = 2;
    RecyclerView recyclerView;
    String s[];
    int images[]={R.drawable.ic_filter,R.drawable.ic_filter,R.drawable.ic_filter,R.drawable.ic_filter,R.drawable.ic_filter};
    ImageView mainImageView;
    File img;
    ImageView saveButton;
    ImageView closeButton;
    Intent returnIntent = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        recyclerView= findViewById(R.id.Filter_imgs);
        mainImageView= findViewById(R.id.photoFilterView);
        saveButton = findViewById(R.id.imgSave);
        closeButton = findViewById(R.id.imgClose);
        s=getResources().getStringArray(R.array.Filter_Name);
        FiltersAdapter filtersAdapter = new FiltersAdapter(this, s, images,this);
        recyclerView.setAdapter(filtersAdapter);

        getData();



        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setResult(Activity.RESULT_CANCELED,returnIntent);
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(FilterActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {
                    BitmapDrawable drawable = (BitmapDrawable) mainImageView.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    returnIntent.putExtra("Image", getImageUri(FilterActivity.this, bitmap));
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
                else{
                    requestStoragePermission();
                }
            }
        });



    }

    private void requestStoragePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                new AlertDialog.Builder(this)
                        .setTitle("Permission needed")
                        .setMessage("This Permission is needed to save the edited image.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(FilterActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);

                            }
                        })
                        .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
        }
        else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                BitmapDrawable drawable = (BitmapDrawable) mainImageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                returnIntent.putExtra("Image", getImageUri(FilterActivity.this, bitmap));
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
            else{

                Toast.makeText(this,"Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return path;
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
            case 3:
                Glide.with(this).load(img).into(mainImageView);
                FilterNegative();
                break;
            case 4:
                Glide.with(this).load(img).into(mainImageView);
                FilterSepia();
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


}
