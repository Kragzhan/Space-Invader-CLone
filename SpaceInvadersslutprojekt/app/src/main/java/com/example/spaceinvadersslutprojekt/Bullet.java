package com.example.spaceinvadersslutprojekt;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Bullet {
    private int  y;
    //x private då det är konstant, kulan färdas ju bara i höjled
    private final int x;
    public int screenWidth, screenHeight;
    boolean isInvaderBullet = false;
    Resources resources;

    Bullet(int x, int y, int screenWidth, int screenHeight, Resources resources, boolean isInvaderBullet){
        // x-värdet är konstant
        this.x = x;
        this.y = y;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.isInvaderBullet = isInvaderBullet;
        this.resources = resources;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isOutOfBounds() {
        // x-värden kan inte vara out-of bounds då den har samma värde som PlayerShip
        // if-else sats skriven i en "ternary" operator
        return y < -50 || y > screenHeight;
    }

    public void draw(Canvas canvas){
        Bitmap bulletBitMap;

        if(isInvaderBullet){
            // ändra bilden för invader
           bulletBitMap = Utility.createBitmap(resources, R.drawable.bullet_invader, 90,30);
            canvas.drawBitmap(bulletBitMap, x + 20, y, null);
        } else {
            bulletBitMap = Utility.createBitmap(resources, R.drawable.bullet_player, 90,30);
            canvas.drawBitmap(bulletBitMap, x + 30, screenHeight - y, null);
        }




    }
}
