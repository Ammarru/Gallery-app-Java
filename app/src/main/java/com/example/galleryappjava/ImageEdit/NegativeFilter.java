package com.example.galleryappjava.ImageEdit;

import android.graphics.Bitmap;
import android.graphics.Color;

public class NegativeFilter {

    public static Bitmap ConvertNegative(Bitmap Orlignalimg) {


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

                //substract from RGB

                R = 255 - R;
                G = 255 - G;
                B = 255 - B;

                pixel = (A<<24) | (R<<16)|(G<<8)| B;

                newImage.setPixel(x,y,pixel);
            }
        }

        return newImage;
    }

}