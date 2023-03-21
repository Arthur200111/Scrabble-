package com.packagenemo.scrabble_plus.lobby;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.packagenemo.scrabble_plus.R;
import com.packagenemo.scrabble_plus.jeu.ui.JeuActivity;

import java.util.LinkedList;

/**
 * Activitédu lobby
 * Sur celle ci, on retrouve les différents joueurs qui ont rejoint la partie ainsi que le mot de passe pour accéder au lobby
 */
public class LobbyActivity extends AppCompatActivity {
    private TextView mPasswordText;
    private RecyclerView mLobbyRecyclerView;
    private PlayerAdapter adapter;
    private String partyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        // On récupère l'intent pour vérifier que le code est bon
        Intent intent = getIntent();
        partyId = intent.getStringExtra("partyId");
        if (!partyVerification()) {
            Toast.makeText(this, "Party Not Found", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Initialisation of the different graphic components
        mLobbyRecyclerView = findViewById(R.id.lobbyRecyclerView);
        mPasswordText = findViewById(R.id.lobbyPwdText);

        // Initialisation Recycler View
        adapter = new PlayerAdapter(new LinkedList<>());
        mLobbyRecyclerView.setHasFixedSize(true);
        mLobbyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mLobbyRecyclerView.setAdapter(adapter);

        // Manual set of password
        this.setPassword(partyId);

        addYourselfToPArty();
        updatePlayers();
    }

    // Permet de s'ajouter sur la base de données à la liste des joueurs concernés par la partie si non déjà présent
    private void addYourselfToPArty() {
        // TODO ajouter le joueur à la partie dans la bdd
    }

    /**
     * Méthode permettant d'ajouter les joueurs de la partie à la liste des joueurs
     */
    private void updatePlayers() {
        // TODO add player to recycler view
        // Par contre je sais pas quand la fonction doit être appelée : on doit la faire s'appeler
        // à la fin pour détecter l'arrivé de nouvelles personnes ?

        // Manual add of players
        this.addNewPlayer("Arthur","");
        this.addNewPlayer("Bryan","");
        this.addNewPlayer("Nicolas","");
        this.addNewPlayer("Thomas","");
        this.addNewPlayer("Tristan","");
    }

    /**
     * Permet de savoir si un id de partie correspond bien à une partie
     * @return true: partie existe bien, false: non
     */
    private boolean partyVerification() {
        // TODO vérifier que la partie est bien dans la base de données à partir de son id (ici partyId)
        // Aussi regarder si la partie a déjà commencé, si est déjà commencée: renvoyer false
        return false;
    }

    /**
     * Fonction permetant d'ajouter un nouveau joueur sur le RecyclerView
     * @param name nom du joueur
     * @param pictureLabel nom de la photo utilisée par le joueur
     */
    public void addNewPlayer(String name, String pictureLabel) {
        adapter.update(new PlayerData(name,getPlayerImgFromLabel(pictureLabel)));
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
    public int getPlayerImgFromLabel(String pictureLabel) {
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

    public void finishPartyCreation(View view) {
        // TODO vérifier que tout est ok mais normalement pas besoin de modifier la bdd
        // Peut être mettre une valeur pour dernierCoup pour la table partie dans la bdd pour
        // montrer que plus personne ne peut rejoindre la partie
        Intent toPageGameIntent = new Intent(LobbyActivity.this, JeuActivity.class);
        toPageGameIntent.putExtra("partyId",partyId);
        startActivity(toPageGameIntent);
    }
}