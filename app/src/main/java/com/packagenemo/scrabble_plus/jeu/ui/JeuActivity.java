package com.packagenemo.scrabble_plus.jeu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.packagenemo.scrabble_plus.R;

public class JeuActivity extends AppCompatActivity {

    private JeuView mJeuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);

        mJeuView = findViewById(R.id.jeu_zone_jeu);
        new Thread(mJeuView).start();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mJeuView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mJeuView.resume();
    }
}