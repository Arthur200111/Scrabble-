package com.packagenemo.scrabble_plus.resume;

import com.packagenemo.scrabble_plus.jeu.manager.*;
import com.packagenemo.scrabble_plus.jeu.callback.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.packagenemo.scrabble_plus.R;
import com.packagenemo.scrabble_plus.jeu.ui.JeuActivity;
import com.packagenemo.scrabble_plus.lobby.LobbyActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

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

        adapter = new PartyAdapter(new LinkedList<>(), this);
        mResumeRecyclerView.setHasFixedSize(true);
        mResumeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mResumeRecyclerView.setAdapter(adapter);

        // DONE Ici à la place d'une implémentation brut de parties il faut les charger
        this.partieManager.getPartieFromUser(new PartieInterface() {
                                                 @Override
                                                 public void onCallback(ArrayList<String> parties) {
                                                     for(int i = 0; i<parties.size()/3;i+=3){
                                                         System.out.println(parties.get(i) + "-" +parties.get(i+1) + "-" +"à " +parties.get(i+2) + " de joueur");
                                                         addNewParty(parties.get(i),parties.get(i+1),"à " +parties.get(i+2) + " de joueur","");
                                                         //addNewParty(parties.get(i+1), parties.get(i+2),"");
                                                     }
                                                 }
                                             }
        );

        // Il faudrait donc pouvoir avoir le titre et savoir si c'est à l'utilisateur de jouer ou non
        //addNewParty("XVGTEDFFF","jeu 1", "à toi de jouer", "");
    }

    public void redirectToPArty(String id) {
        // DONE fonction appelé lorsque l'on clique sur l'une des parties pour aller sur la gameActivity
        /*partieManager.isItMyTurn(
                id,
                new BooleanInterface() {
                    @Override
                    public void onCallback(boolean result) {
                        Intent startingPartieIntent;

                        if(result){
                            startingPartieIntent = new Intent(ResumeActivity.this, JeuActivity.class);
                            startingPartieIntent.putExtra("partyId",id);
                            startActivity(startingPartieIntent);
                        }
                        else{
                            displayNotMyTurnToast();
                        }

                    }
                }
        );*/

        Intent startingPartieIntent;

        startingPartieIntent = new Intent(ResumeActivity.this, JeuActivity.class);
        System.out.println("L'id de la partie dans Resume Activity : " + id.toString());
        startingPartieIntent.putExtra("partyId",id);
        startActivity(startingPartieIntent);
    }

    public void displayNotMyTurnToast(){
        Toast.makeText(this, "Ce n'est pas encore à vous de jouer", Toast.LENGTH_SHORT).show();
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