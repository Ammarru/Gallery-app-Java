package com.example.galleryappjava.ImageEdit;

import android.graphics.Bitmap;
import android.graphics.Color;

public class SepiaFilter {

    public static Bitmap FilterSepia(Bitmap Orlignalimg) {


        // color information
        int A, R, G, B, Grey;
        int sepiaDepth = 20;
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
                Grey = (R+G+B) / 3;


                R=G=B = Grey;
                R = R + (sepiaDepth * 2);
                G = G + sepiaDepth;

                if (R > 255) { R = 255; }
                if (G > 255) { G = 255; }
                if (B > 255) { B = 255; }

                // normalize if out of bounds
                if (B < 0)   { B = 0; }


                pixel = (A<<24) | (R<<16)|(G<<8)| B;

                newImage.setPixel(x,y,pixel);
            }
        }

        return newImage;
    }
}
