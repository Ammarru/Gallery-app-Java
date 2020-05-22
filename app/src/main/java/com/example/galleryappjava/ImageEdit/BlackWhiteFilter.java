package com.example.galleryappjava.ImageEdit;

import android.graphics.Bitmap;
import android.graphics.Color;

public class BlackWhiteFilter {

    public static Bitmap ConvertBlackWhite(Bitmap Orlignalimg) {


        // color information
        int A, R, G, B;
        int pixel;
        int width = Orlignalimg.getWidth();
        int height = Orlignalimg.getHeight();
        Bitmap newImage =Bitmap.createBitmap(width, height, Orlignalimg.getConfig());

        // rotation
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixel = Orlignalimg.getPixel(x,y);
                A = Color.alpha(pixel);
                R = (int)(Color.red(pixel));
                G = (int)(Color.green(pixel));
                B = (int)(Color.blue(pixel));


                R = (R+G+B)/3;
                G = R;
                B = R;


                newImage.setPixel(x,y,Color.argb(A ,R ,G ,B));
            }
        }

        return newImage;
    }

}