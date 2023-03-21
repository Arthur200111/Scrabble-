package com.packagenemo.scrabble_plus.join;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
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
    TextInputLayout mJoinInputPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        mJoinInputPass = findViewById(R.id.joinInputPass);
    }

    public void goToParty(View view) {
        Intent toPageLobbyIntent = new Intent(JoinActivity.this, LobbyActivity.class);
        String partyId = String.valueOf(mJoinInputPass.getEditText().getText());
        toPageLobbyIntent.putExtra("partyId", partyId);
        startActivity(toPageLobbyIntent);
    }
}