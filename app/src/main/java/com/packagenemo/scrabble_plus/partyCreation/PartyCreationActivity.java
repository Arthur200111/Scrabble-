package com.packagenemo.scrabble_plus.partyCreation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.packagenemo.scrabble_plus.R;
import com.packagenemo.scrabble_plus.lobby.LobbyActivity;

import java.util.Random;

/**
 * Activité qui gère la création d'une partie sous la forme d'un formulaire
 */
public class PartyCreationActivity extends AppCompatActivity {
    Spinner mDropdownMenu;
    TextInputLayout mCreationInputName;
    TextInputLayout mCreationInputNbPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_creation);

        // Dropdown menu configuration
        mDropdownMenu = (Spinner) findViewById(R.id.creationSpinnerMode);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.choice_mode, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDropdownMenu.setAdapter(adapter);

        mCreationInputName = findViewById(R.id.creationInputName);
        mCreationInputNbPlayer = findViewById(R.id.creationInputNbPlayer);
    }

    /**
     * Fonction appelée lorsque l'on submit le form de création d'une partie
     * @param view
     */
    public void submitCreationForm(View view) {
        Intent toPageMenuIntent = new Intent(PartyCreationActivity.this, LobbyActivity.class);
        String partyName = String.valueOf(mCreationInputName.getEditText().getText());
        String gameMode = mDropdownMenu.getSelectedItem().toString();
        String gameId = generateCode(8);
        if (partyCreation(gameId, partyName, gameMode)) {
            toPageMenuIntent.putExtra("partyId", gameId);
            startActivity(toPageMenuIntent);
        } else {
            Toast.makeText(this, "Party creation failed", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Permet de créer une partie et de la mettre dans la base de donnée, en fonction de la valeur
     * retournée, la création a été ou non un succes
     * @param gameId id de la partie que l'on souhaite créer
     * @param partyName nom de la partie que l'on souhaite créer
     * @param gameMode mode de jeu de la partie
     * @return true: la partie a été crée, false: non
     */
    private boolean partyCreation(String gameId, String partyName, String gameMode) {
        // TODO créer une partie à partie de son id, son nom et de son mode de jeu
        // Il faut voir avec Tristan pour tout ce qui est récup' d'un plateau basique, je ne sais pas trop comment il fait.
        // Aussi il faut vérifier que le gameId n'ait pas déjà été généré
        return false;
    }

    /**
     * Permet de génerer un code/id pour la création d'une partie
     * @param length longueur du code
     * @return code généré
     */
    private String generateCode(int length) {
        String[] acceptedChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".split("");
        int nbAcceptedChar = acceptedChar.length;
        String code = "";
        Random rd = new Random();
        for (int i = 0; i<length; i++) {
            code += acceptedChar[rd.nextInt(nbAcceptedChar)];
        }
        return code;
    }
}