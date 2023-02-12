package com.packagenemo.scrabble_plus.join;

import androidx.appcompat.app.AppCompatActivity;

import com.packagenemo.scrabble_plus.R;
import com.packagenemo.scrabble_plus.lobby.LobbyActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        findViewById(R.id.joinButtonLobby).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPageMenuIntent = new Intent(JoinActivity.this, LobbyActivity.class);
                startActivity(toPageMenuIntent);
            }
        });
    }
}