package com.example.galleryappjava.Storage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.galleryappjava.Base.BaseActivity;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private File storage;
    private String storagePaths[];

    @Override
    public void onCreate (Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);

        // loading date here

        storagePaths = StorageUtil.getStorageDirectories(this);

        for (String path: storagePaths){
            storage = new File(path);
            Method.load_Directory_Files(storage);
        }

        Intent intent = new Intent(SplashScreen.this, BaseActivity.class);
        startActivity(intent);
    }
}
