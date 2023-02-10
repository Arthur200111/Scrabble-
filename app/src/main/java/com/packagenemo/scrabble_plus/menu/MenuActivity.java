package com.packagenemo.scrabble_plus.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.packagenemo.scrabble_plus.R;
import com.packagenemo.scrabble_plus.join.JoinActivity;
import com.packagenemo.scrabble_plus.login.LoginActivity;
import com.packagenemo.scrabble_plus.partyCreation.PartyCreationActivity;
import com.packagenemo.scrabble_plus.register.RegisterActivity;
import com.packagenemo.scrabble_plus.resume.ResumeActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViewById(R.id.menuButtonCreation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPageMenuIntent = new Intent(MenuActivity.this, PartyCreationActivity.class);
                startActivity(toPageMenuIntent);
            }
        });
        findViewById(R.id.menuButtonJoin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPageMenuIntent = new Intent(MenuActivity.this, JoinActivity.class);
                startActivity(toPageMenuIntent);
            }
        });
        findViewById(R.id.menuButtonResume).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPageMenuIntent = new Intent(MenuActivity.this, ResumeActivity.class);
                startActivity(toPageMenuIntent);
            }
        });
    }
}