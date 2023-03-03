package com.packagenemo.scrabble_plus.join;

import androidx.appcompat.app.AppCompatActivity;

import com.packagenemo.scrabble_plus.R;
import com.packagenemo.scrabble_plus.lobby.LobbyActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Activité qui permet à un utilisateur de rejoindre un lobby à partir d'un code
 */
public class JoinActivity extends AppCompatActivity {
    Button mJoinButtonLobby;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        // Gestion des redirections
        mJoinButtonLobby = findViewById(R.id.joinButtonLobby);
        mJoinButtonLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPageMenuIntent = new Intent(JoinActivity.this, LobbyActivity.class);
                startActivity(toPageMenuIntent);
            }
        });
    }
}