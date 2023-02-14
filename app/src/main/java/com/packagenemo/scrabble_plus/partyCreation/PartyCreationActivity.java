package com.packagenemo.scrabble_plus.partyCreation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.packagenemo.scrabble_plus.R;
import com.packagenemo.scrabble_plus.lobby.LobbyActivity;

public class PartyCreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_creation);

        // Dropdown menu configuration
        Spinner menu_dropdown = (Spinner) findViewById(R.id.creationSpinnerMode);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.choice_mode, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menu_dropdown.setAdapter(adapter);

        findViewById(R.id.creationButtonLobby).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPageMenuIntent = new Intent(PartyCreationActivity.this, LobbyActivity.class);
                startActivity(toPageMenuIntent);
            }
        });


    }
}