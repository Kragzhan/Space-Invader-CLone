package com.example.spaceinvadersslutprojekt;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

public class Invader {
    //startvariabler
    int screenWidth, screenHeight;
    int spriteHeight = 91;
    int spriteWidth = 66;
    //definierar var spriten startar
    private int x, y = spriteHeight;

    Resources resources;

    //skapar en array för alla bullets som invaders skjutit
    private ArrayList<Bullet> bullets;

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    //skapar en ny bitmap som kommer användas för att göra om spriten för invader till en bitmap
    Bitmap invaderBitMap;

    //variabler för invaderbitmapen
    Invader(int screenWidth, int screenHeight, Resources resources) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        invaderBitMap = Utility.createBitmap(resources, R.drawable.invader, spriteHeight, spriteWidth);
        bullets = new ArrayList<>();
        this.resources = resources;
    }

    //draw metoden som ritar ut en invader på skärmen
    public void draw(Canvas canvas) {
        canvas.drawBitmap(invaderBitMap, x, y, null);
    }


    //returnerar false ifall båda if-satserna inte stämmer
    public boolean isCollision(Bullet bullet) {
        if (x >= bullet.getX() - 30 && x <= bullet.getX() + 100) {
            if (y >= screenHeight - bullet.getY()) {
                return true;
            }

        }
        return false;
    }

    //shoot metoden som får invaders att skjuta
    public void shoot() {
        bullets.add(new Bullet(x, y, screenWidth, screenHeight, resources, true));
    }

    //metod för att radera en viss bullet
    public void removeBullet(int index) {
        bullets.remove(index);
    }

    //metod för at tsätta x-värdet
    public void setX(int x) {
        this.x = x;
    }
}

