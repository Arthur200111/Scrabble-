package com.packagenemo.scrabble_plus.lobby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.packagenemo.scrabble_plus.R;
import com.packagenemo.scrabble_plus.jeu.ui.JeuActivity;

public class LobbyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        findViewById(R.id.lobbyButtonPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPageMenuIntent = new Intent(LobbyActivity.this, JeuActivity.class);
                startActivity(toPageMenuIntent);
            }
        });


    }
}