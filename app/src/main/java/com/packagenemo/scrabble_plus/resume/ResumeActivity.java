package com.packagenemo.scrabble_plus.resume;

import com.packagenemo.scrabble_plus.jeu.manager.*;
import com.packagenemo.scrabble_plus.jeu.callback.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.packagenemo.scrabble_plus.R;
import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * Activité pour reprendre une partie
 * Sur celle ci, on retrouve les différentes parties auquel le joueur a accès
 */
public class ResumeActivity extends AppCompatActivity {
    private RecyclerView mResumeRecyclerView;
    private PartyAdapter adapter;

    private static PartieManager partieManager = PartieManager.getInstance();

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

        // On va récupérer les parties en cours pour les afficher

        // Dans
        this.partieManager.getPartieFromUser(new PartieInterface() {
                                                 @Override
                                                 public void onCallback(ArrayList<String> parties) {
                                                     for(int i = 0; i<parties.size()/3;i+=3){
                                                         addNewParty(parties.get(i+1), parties.get(i+2),"");
                                                     }
                                                 }
                                             }
        );

        // Implémentation d'exemples de joueur
        /*addNewParty("jeu 1", "à toi de jouer", "");
        addNewParty("jeu 2", "tu joues dans 2min", "");
        addNewParty("jeu 3", "à Tristan de jouer", "");
        addNewParty("jeu 4", "bientôt à toi", "");
        addNewParty("jeu 5", "en attente de Thomas ...", "");
        addNewParty("jeu 6", "Il te reste 15min", "");*/
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