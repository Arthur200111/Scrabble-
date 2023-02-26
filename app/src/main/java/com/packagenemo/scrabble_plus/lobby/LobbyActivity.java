package com.packagenemo.scrabble_plus.lobby;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.packagenemo.scrabble_plus.R;
import com.packagenemo.scrabble_plus.jeu.ui.JeuActivity;

import java.util.LinkedList;
import java.util.List;


public class LobbyActivity extends AppCompatActivity {

    private Button mLobbyButtonPlay;
    private TextView mPasswordText;
    private RecyclerView mLobbyRecyclerView;
    PlayerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        // Initialisation of the different graphic components
        mLobbyButtonPlay = findViewById(R.id.lobbyButtonPlay);
        mLobbyRecyclerView = findViewById(R.id.lobbyRecyclerView);
        mPasswordText = findViewById(R.id.lobbyPwdText);


        // Set redirections
        mLobbyButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPageMenuIntent = new Intent(LobbyActivity.this, JeuActivity.class);
                startActivity(toPageMenuIntent);
            }
        });

        // Initialisation Recycler View
        adapter = new PlayerAdapter(new LinkedList<>());
        mLobbyRecyclerView.setHasFixedSize(true);
        mLobbyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mLobbyRecyclerView.setAdapter(adapter);

        // Manual add of players
        this.addNewPlayer("Arthur","turtle");
        this.addNewPlayer("Bryan","turtle");
        this.addNewPlayer("Nicolas","turtle");
        this.addNewPlayer("Thomas","turtle");
        this.addNewPlayer("Tristan","turtle");

        // Manual set of password
        this.setPassword("XVQPT45FD");
    }

    /**
     * Fonction permetant d'ajouter un nouveau joueur sur le RecyclerView
     * @param name nom du joueur
     * @param pictureLabel nom de la photo utilisée par le joueur
     */
    public void addNewPlayer(String name, String pictureLabel) {
        adapter.update(new PlayerData(name,getImgFromLabel(pictureLabel)));
    }

    /**
     * Permet de supprimer un joueur du RecyclerView à l'aide de sa position
     * @param position position du joueur à supprimer dans le RecyclerView
     */
    public void deletePlayer(int position) {
        if (position < adapter.getItemCount() && position > 0) {
            adapter.delete(position);
        }
    }

    /**
     * Permet d'obtenir l'identifiant d'une image à partir de son label
     * Si le label ne correspond à aucune image, l'identifiant de l'image par défaut est renvoyée
     * L'image par défaut est celle d'un ours
     * @param pictureLabel nom de l'image
     * @return renvoie l'identifiant de l'image
     */
    public int getImgFromLabel(String pictureLabel) {
        int id = getResources().getIdentifier(pictureLabel, "drawable", getPackageName());
        if (id == 0) {
            id = R.drawable.bear;
        }
        return id;
    }

    /**
     * Permet de modifier le mot de passe permettant de se connecter au lobby
     * @param pw mot de passe
     */
    public void setPassword(String pw) {
        mPasswordText.setText(pw);
    }
}