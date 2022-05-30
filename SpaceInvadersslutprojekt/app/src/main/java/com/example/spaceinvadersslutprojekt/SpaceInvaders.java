package com.example.spaceinvadersslutprojekt;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class SpaceInvaders extends SurfaceView implements Runnable {

    ////Startvariabler och array för invaders
    private int screenWidth; //Använd om du behöver veta skärmens bredd
    private int screenHeight; //Använd om du behöver veta skärmens höjd
    private Thread gameThread;
    private Canvas canvas; //Vi ritar på en Canvas
    private Paint paint; //För att rita måste man ha färg
    private SurfaceHolder ourHolder;
    public boolean playing = true;
    private PlayerShip player;
    public ArrayList<Invader> invaders = new ArrayList<>();
    private boolean isGameOver = false;

    //klassen SpaceInvaders
    public SpaceInvaders(Context context, int screenWidth, int screenHeight) {
        super(context);
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        paint = new Paint();
        ourHolder = getHolder();
        player = new PlayerShip(screenWidth, screenHeight, getResources());
        player.resetPosition();
    }

    //run metoden som gör så alla invaders och spelare skjuter vid ett visst definierat intervall
    @Override
    public void run() {
        if (playing) {
            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (isGameOver == false) {
                        player.shoot();
                    }
                }
                // körs varje 1000 millisekund
            }, 0, 1000);
            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (invaders.size() != 0 && isGameOver == false) {
                        for (Invader invader : invaders) {
                            invader.shoot();
                        }
                    }
                }
                // körs varje 1500 millisekund
            }, 0, 1500);
        }

        //kör update och draw när playing är true
        while (playing) {
            update();
            draw();
        }


    }


    //update metoden sköter ändringar i position för player samt bullets och kollisioner för bullets
    private void update() {
        String direction = player.getDirection();

        switch (direction) {
            case "LEFT":
                player.setX(player.getX() - 15);
                break;
            case "RIGHT":
                player.setX(player.getX() + 15);
                break;
        }


        //lägger till nya invaders om antalaet blir 0
        if (invaders.size() == 0) {
            for (int i = 0; i < 5; i++) {
                Invader invader = new Invader(screenWidth, screenHeight, getResources());
                invader.setX((screenWidth - 275) / 5 * (i + 1));
                invaders.add(invader);
            }
        }
        //arraylist för bullets som spelaren skjutit
        ArrayList<Bullet> playerBullets = player.getBullets();

        //loopar igenom alla playerbullets på skärmen och kollar först ifall de är innanför bounds
        //och tar annars bort dem, om de inte är det flyttas de uppåt och när de flyttats uppåt
        //jämför vi alla invaders koordinater med alla playerbullets koordinater och ser ifall de kolliderat
        //om de kolliderat tar vi bort invadern samt ökar score med 1
        //gör även samma sak för invaderbullets och ifall en invaderbullet kolliderar med player stoppas
        //spelet och activityn byts
        for (int i = 0; i < playerBullets.size(); i++) {
            Bullet bullet = playerBullets.get(i);
            if (bullet.isOutOfBounds()) {
                player.removeBullet(i);
            } else {
                bullet.setY(bullet.getY() + 10);
            }
        }
        for (int i = 0; i < invaders.size(); i++) {
            Invader invader = invaders.get(i);
            ArrayList<Bullet> invaderBullets = invader.getBullets();
            for (int j = 0; j < invaderBullets.size(); j++) {
                Bullet bullet = invaderBullets.get(j);
                if (bullet.isOutOfBounds()) {
                    invader.removeBullet(j);
                } else {
                    bullet.setY(bullet.getY() + 10);
                }
            }
            for (int j = 0; j < player.getBullets().size(); j++) {
                Bullet bullet = player.getBullets().get(j);
                if (invader.isCollision(bullet)) {
                    invaders.remove(i);
                    player.setScore(player.getScore() + 1);
                }
            }
            for (int j = 0; j < invader.getBullets().size(); j++) {
                Bullet bullet = invader.getBullets().get(j);
                if (player.isCollision(bullet)) {
                    isGameOver = true;
                }
            }
        }

    }

    //Metoden draw som ritar alla bullets, invaders och vår playership samt skärmen när man förlorar
    @SuppressLint("ResourceAsColor")
    private void draw() {
        if (ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();
            if (isGameOver == true) {
                paint.setColor(Color.BLACK);
                canvas.drawRect(0, 0, screenWidth, screenHeight, paint);
                paint.setColor(Color.RED);
                paint.setTextSize(15);
                canvas.drawText("Score: " + String.valueOf(player.getScore()), screenWidth / 2, screenHeight / 2 + 60, paint);
                paint.setColor(Color.YELLOW);
                paint.setTextSize(30);
                canvas.drawText("Game Over", screenWidth / 2, screenHeight / 2, paint);
                paint.setTextSize(20);
                canvas.drawText("Press anywhere to play again", screenWidth / 2, screenHeight / 2 + 30, paint);
            } else {
                paint.setColor(Color.BLACK);
                canvas.drawRect(0, 0, screenWidth, screenHeight, paint);
                paint.setColor(Color.RED);
                paint.setTextSize(20);
                canvas.drawText("Score: " + String.valueOf(player.getScore()), 10, 10 + paint.getTextSize(), paint);
                ArrayList<Bullet> playerBullets = player.getBullets();
                for (Bullet bullet : playerBullets) {
                    bullet.draw(canvas);
                }
                player.draw(canvas);
                for (Invader invader : invaders) {
                    invader.draw(canvas);
                    for (Bullet bullet : invader.getBullets()) {
                        bullet.draw(canvas);
                    }
                }
            }
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    //Hantera touch-händelser (Vad ska hända när användaren trycker på skärmen?)
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction()) {

            // Spelaren har touchat skärmen, både för vanliga activityn och för när spelet är över
            case MotionEvent.ACTION_DOWN:
                if (isGameOver == true) {
                    player.resetPosition();
                    invaders.removeAll(invaders);
                    player.setScore(0);
                    player.removeBullets();
                    isGameOver = false;
                } else {
                    if (motionEvent.getX() > screenWidth / 2) {
                        player.setDirection("RIGHT");
                    } else {
                        player.setDirection("LEFT");
                    }

                }
                break;

            // Spelarens finger har lyfts från skärmen
            case MotionEvent.ACTION_UP:
                player.setDirection("");
                break;
        }
        return true;
    }


    //Om spelet startar/återupptas - skapa och starta vår tråd
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    //Om spelet pausas/avslutas - stoppa vår tråd
    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("error", "joining thread");
        }
    }
}