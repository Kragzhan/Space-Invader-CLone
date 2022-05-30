package com.example.spaceinvadersslutprojekt;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//utility används för att göra en bild i drawable till en bitmap
public class Utility {
    public static Bitmap createBitmap(Resources resources, int drawableId, int newHeight, int newWidth) {
        Bitmap myBitmap = BitmapFactory.decodeResource(resources, drawableId);

        myBitmap = Bitmap.createScaledBitmap(myBitmap, newWidth, newHeight, false);

        return myBitmap;
    }

}
