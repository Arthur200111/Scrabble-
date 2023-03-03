package com.packagenemo.scrabble_plus.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.packagenemo.scrabble_plus.R;
import com.packagenemo.scrabble_plus.join.JoinActivity;
import com.packagenemo.scrabble_plus.login.LoginActivity;
import com.packagenemo.scrabble_plus.partyCreation.PartyCreationActivity;
import com.packagenemo.scrabble_plus.register.RegisterActivity;
import com.packagenemo.scrabble_plus.resume.ResumeActivity;

/**
 * Activity de la page du menu principal, permet d'accéder aux pages suivantes :
 * Création de partie
 * Rejoindre une partie en cours
 * Rejoindre une nouvelle partie
 */
public class MenuActivity extends AppCompatActivity {
    Button mMenuButtonCreation;
    Button mMenuButtonJoin;
    Button mMenuButtonResume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Gestion des redirections
        mMenuButtonCreation = findViewById(R.id.menuButtonCreation);
        mMenuButtonJoin = findViewById(R.id.menuButtonJoin);
        mMenuButtonResume = findViewById(R.id.menuButtonResume);
        
        mMenuButtonCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPageMenuIntent = new Intent(MenuActivity.this, PartyCreationActivity.class);
                startActivity(toPageMenuIntent);
            }
        });
        mMenuButtonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPageMenuIntent = new Intent(MenuActivity.this, JoinActivity.class);
                startActivity(toPageMenuIntent);
            }
        });
        mMenuButtonResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPageMenuIntent = new Intent(MenuActivity.this, ResumeActivity.class);
                startActivity(toPageMenuIntent);
            }
        });
    }
}