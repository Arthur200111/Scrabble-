package com.packagenemo.scrabble_plus.resume;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.packagenemo.scrabble_plus.R;
import com.packagenemo.scrabble_plus.jeu.ui.JeuActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.LinkedList;

/**
 * Activité pour reprendre une partie
 * Sur celle ci, on retrouve les différentes parties auquel le joueur a accès
 */
public class ResumeActivity extends AppCompatActivity {
    private RecyclerView mResumeRecyclerView;
    private PartyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);

        // Initialisation du menu déroulant contenant toutes les parties en cours
        mResumeRecyclerView = findViewById(R.id.resumeRecyclerView);

        adapter = new PartyAdapter(new LinkedList<>(), this);
        mResumeRecyclerView.setHasFixedSize(true);
        mResumeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mResumeRecyclerView.setAdapter(adapter);

        // Implémentation d'exemples de joueur
        // TODO Ici à la place d'une implémentation brut de parties il faut les charger
        // Il faudrait donc pouvoir avoir le titre et savoir si c'est à l'utilisateur de jouer ou non
        addNewParty("XVGTEDFFF","jeu 1", "à toi de jouer", "");
        addNewParty("HUFOZLKFF", "jeu 2", "tu joues dans 2min", "");
        addNewParty("FEQF4EFFF", "jeu 3", "à Tristan de jouer", "");
        addNewParty("KFJZ7ZFJI", "jeu 4", "bientôt à toi", "");
        addNewParty("ZFFFE5ZDZ", "jeu 5", "en attente de Thomas ...", "");
        addNewParty("ZDFZF5ZFZ", "jeu 6", "Il te reste 15min", "");
    }

    public void redirectToPArty(String id) {
        // TODO fonction appelé lorsque l'on clique sur l'une des parties pour aller sur la gameActivity
        Intent toPageGameIntent = new Intent(ResumeActivity.this, JeuActivity.class);
        toPageGameIntent.putExtra("partyId",id);
        startActivity(toPageGameIntent);
    }

    /**
     * Permet d'ajouter une nouvelle au menu déroulant
     *
     * @param partyName nom de la partie
     * @param partyState état de la partie
     * @param partyImgLabel nom de l'image utilisé pour la partie
     */
    public void addNewParty(String id, String partyName, String partyState, String partyImgLabel) {
        adapter.update(new PartyData(partyName,partyState,getPartyImgFromLabel(partyImgLabel), id));
    }

    /**
     * Permet de supprimer une partie de la liste déroulante à partir de sa position
     * @param position position de la partie à supprimer
     */
    public void deleteParty(int position) {
        if (position < adapter.getItemCount() && position > 0) {
            adapter.delete(position);
        }
    }

    /**
     * Permet d'obtenir l'id d'une image à partir de son label
     * @param label label de l'image
     * @return id de l'image
     */
    public int getPartyImgFromLabel(String label) {
        int id = getResources().getIdentifier(label, "drawable", getPackageName());
        if (id == 0) {
            id = R.drawable.scrabble;
        }
        return id;
    }
}