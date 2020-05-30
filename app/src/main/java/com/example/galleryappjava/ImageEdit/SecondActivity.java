package com.example.galleryappjava.ImageEdit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.galleryappjava.Adapters.FiltersAdapter;
import com.example.galleryappjava.Adapters.RecyclerViewClickInterface;
import com.example.galleryappjava.Adapters.ToolsAdapter;
import com.example.galleryappjava.R;
import com.example.galleryappjava.Storage.Constant;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;
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
    Bitmap undo;
    Bitmap result;

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
                                    save(result);
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
                                    if (result!=null)
                                        mainImageView.setImageBitmap(undo);
                                    else
                                        Glide.with(SecondActivity.this).load(image).into(mainImageView);
                                    result=undo;
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
                                    result=null;
                                    undo= null;
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
                    if (result!=null)
                        mainImageView.setImageBitmap(undo);
                    else
                        Glide.with(this).load(image).into(mainImageView);
                    FilterBlackWhite();
                    break;

                case 1:
                    if (result!=null)
                        mainImageView.setImageBitmap(undo);
                    else
                        Glide.with(this).load(image).into(mainImageView);
                    FilterBlue();
                    break;
                case 2:
                    if (result!=null)
                        mainImageView.setImageBitmap(undo);
                    else
                        Glide.with(this).load(image).into(mainImageView);
                    FilterRed();
                    break;
                case 3:
                    if (result!=null)
                        mainImageView.setImageBitmap(undo);
                    else
                        Glide.with(this).load(image).into(mainImageView);
                    FilterNegative();
                    break;
                case 4:
                    if (result!=null)
                        mainImageView.setImageBitmap(undo);
                    else
                        Glide.with(this).load(image).into(mainImageView);
                    FilterSepia();
                    break;
            }
        }
    }




    public void RotatePic(){
        mainImageView.invalidate();
        if (result!=null){
            undo = result;
            Bitmap bitmap = result;
            mainImageView.setImageBitmap(Rotate.rotate(bitmap,90));
            result = Rotate.rotate(bitmap,90);
        }
        else {
            BitmapDrawable drawable = (BitmapDrawable) mainImageView.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            mainImageView.setImageBitmap(Rotate.rotate(bitmap,90));
            result = Rotate.rotate(bitmap,90);
        }

    }

    public void FilterRed(){
        mainImageView.invalidate();
        if (result!=null){
            undo = result;
            Bitmap bitmap = result;
            mainImageView.setImageBitmap(Filter.doColorFilter(bitmap,1,0,0));
            result = Filter.doColorFilter(bitmap,1,0,0);
        }
        else {
            BitmapDrawable drawable = (BitmapDrawable) mainImageView.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            mainImageView.setImageBitmap(Filter.doColorFilter(bitmap,1,0,0));
            result = Filter.doColorFilter(bitmap,1,0,0);
        }

    }

    public void FilterBlue(){
        mainImageView.invalidate();
        if (result!=null){
            undo = result;
            Bitmap bitmap = result;
            mainImageView.setImageBitmap(Filter.doColorFilter(bitmap,0,0,1));
            result = Filter.doColorFilter(bitmap,0,0,1);
        }
        else {
            BitmapDrawable drawable = (BitmapDrawable) mainImageView.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            mainImageView.setImageBitmap(Filter.doColorFilter(bitmap,0,0,1));
            result = Filter.doColorFilter(bitmap,0,0,1);
        }
    }

    public void FilterBlackWhite(){
        mainImageView.invalidate();
        if (result!=null){
            undo = result;
            Bitmap bitmap = result;
            mainImageView.setImageBitmap(BlackWhiteFilter.ConvertBlackWhite(bitmap));
            result = BlackWhiteFilter.ConvertBlackWhite(bitmap);
        }
        else {
            BitmapDrawable drawable = (BitmapDrawable) mainImageView.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            mainImageView.setImageBitmap(BlackWhiteFilter.ConvertBlackWhite(bitmap));
            result = BlackWhiteFilter.ConvertBlackWhite(bitmap);
        }

    }

    public void FilterNegative(){
        mainImageView.invalidate();
        if (result!=null){
            undo = result;
            Bitmap bitmap = result;
            mainImageView.setImageBitmap(NegativeFilter.ConvertNegative(bitmap));
            result = NegativeFilter.ConvertNegative(bitmap);
        }
        else {
            BitmapDrawable drawable = (BitmapDrawable) mainImageView.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            mainImageView.setImageBitmap(NegativeFilter.ConvertNegative(bitmap));
            result = NegativeFilter.ConvertNegative(bitmap);
        }

    }

    public void FilterSepia(){
        mainImageView.invalidate();
        if (result!=null){
            undo = result;
            Bitmap bitmap = result;
            mainImageView.setImageBitmap(SepiaFilter.FilterSepia(bitmap));
            result = (SepiaFilter.FilterSepia(bitmap));
        }
        else {
            BitmapDrawable drawable = (BitmapDrawable) mainImageView.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            mainImageView.setImageBitmap(SepiaFilter.FilterSepia(bitmap));
            result = (SepiaFilter.FilterSepia(bitmap));
        }

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
            file.createNewFile();
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
            setData();
        }
    }


    private void setData() {

        Glide.with(this)
                .load(image)
                .into(mainImageView);
    }

    @Override
    public void onBackPressed() {

        if(filterRecyclerView.getVisibility() == View.VISIBLE){
            new AlertDialog.Builder(SecondActivity.this)
                    .setTitle("Clear")
                    .setMessage("Applied Filter will be cleared.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Glide.with(SecondActivity.this).load(image).into(mainImageView);
                            result=undo;
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
                            result=null;
                            undo= null;
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
}
