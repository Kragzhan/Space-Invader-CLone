package com.example.spaceinvadersslutprojekt;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

//i princip simple game engine
public class Engine extends Activity {

    //vår gameview
    SpaceInvaders gameView;

    //Variabler för att lagra skärmens dimensioner
    int screenWidth;
    int screenHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Kod som gör att appen körs i helskärmsläge
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Läs in skärmens dimensioner
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // eftersom det är landscape blir höjden faktiska bredden och bredden faktiska höjden
        int screenWidth = size.y;
        int screenHeight = size.x;

        //sätter contentview
        gameView = new SpaceInvaders(this, screenHeight, screenWidth);
        setContentView(gameView);
    }

    //när spelet körs
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    //när spelet inte körs
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

}