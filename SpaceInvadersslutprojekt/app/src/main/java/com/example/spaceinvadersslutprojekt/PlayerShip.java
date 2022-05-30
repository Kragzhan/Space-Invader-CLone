package com.example.spaceinvadersslutprojekt;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

public class PlayerShip {
    //startvariabler
    int screenWidth, screenHeight;
    int spriteHeight = 55;
    int spriteWidth = 90;
    private int score = 0;

    // y är alltid 100 från botten och x är en variabel, direction har inget värde i början
    private int x, y = 100;
    private String direction = "";

    //resources
    Resources resources;

    //skapar en array för alla bullets
    private ArrayList<Bullet> bullets;

    //skapar en ny bitmap som kommer användas för att göra om spriten för playership till en bitmap
    Bitmap playerShipBitMap;

    //variabler för playership
    PlayerShip(int screenWidth, int screenHeight, Resources resources) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        playerShipBitMap = Utility.createBitmap(resources, R.drawable.playership, spriteHeight, spriteWidth);
        bullets = new ArrayList<>();
        this.resources = resources;
    }

    //metod för att sätta score så det kan ändras när man skjuter en invader
    public void setScore(int score) {
        this.score = score;
    }

    //getter för score så vi kan skriva ut det
    public int getScore() {
        return score;
    }

    //metod för kollision med invaderbullets
    public boolean isCollision(Bullet bullet) {
        //Log.d("TEST", "bullet.getY():" + bullet.getY()+ " y:" + y);
        if (x >= bullet.getX() - 30 && x <= bullet.getX() + 100) {
            if (y >= screenHeight - bullet.getY()) {
                return true;
            }
        }
        return false;
    }

    // sätter spelaren i defalt-positionen i spelet, 100 pixlar från botten av skärmen och x som är
    //i mitten
    public void resetPosition() {
        x = screenWidth / 2 - (spriteWidth / 2);
    }

    //metoden som ritar ut playerhsip på rätt ställe
    public void draw(Canvas canvas) {
        canvas.drawBitmap(playerShipBitMap, x, screenHeight - y, null);
    }

    //sätta x värdet, det kan inte vara precis vid kanterna
    public void setX(int x) {
        if (x <= screenWidth - spriteWidth && x > 0) {
            this.x = x;
        }
    }

    //hämta x-värdet
    public int getX() {
        return x;
    }

    //metoder för riktning, används för att styra skeppet
    public void setDirection(String direction) {
        this.direction = direction;
    }

    //getter för direction
    public String getDirection() {
        return direction;
    }

    //metod för att skjuta
    public void shoot() {
        bullets.add(new Bullet(x, y, screenWidth, screenHeight, resources, false));
    }

    //metod för att ta bort en viss bullet
    public void removeBullet(int index) {
        bullets.remove(index);
    }

    //metod för att radera alla bullets, används när man förlorar
    public void removeBullets() {
        bullets.removeAll(bullets);
    }

    //getter som returnerar hur många bullets som finns
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
}
