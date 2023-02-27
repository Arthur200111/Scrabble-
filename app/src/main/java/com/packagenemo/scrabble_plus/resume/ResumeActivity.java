package com.packagenemo.scrabble_plus.resume;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.packagenemo.scrabble_plus.R;
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

        adapter = new PartyAdapter(new LinkedList<>());
        mResumeRecyclerView.setHasFixedSize(true);
        mResumeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mResumeRecyclerView.setAdapter(adapter);

        // Implémentation d'exemples de joueur
        addNewParty("jeu 1", "à toi de jouer", "");
        addNewParty("jeu 2", "tu joues dans 2min", "");
        addNewParty("jeu 3", "à Tristan de jouer", "");
        addNewParty("jeu 4", "bientôt à toi", "");
        addNewParty("jeu 5", "en attente de Thomas ...", "");
        addNewParty("jeu 6", "Il te reste 15min", "");
    }

    /**
     * Permet d'ajouter une nouvelle au menu déroulant
     *
     * @param partyName nom de la partie
     * @param partyState état de la partie
     * @param partyImgLabel nom de l'image utilisé pour la partie
     */
    public void addNewParty(String partyName, String partyState, String partyImgLabel) {
        adapter.update(new PartyData(partyName,partyState,getPartyImgFromLabel(partyImgLabel)));
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