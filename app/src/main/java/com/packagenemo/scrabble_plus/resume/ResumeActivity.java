package com.packagenemo.scrabble_plus.resume;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.packagenemo.scrabble_plus.R;
import android.os.Bundle;

import java.util.LinkedList;

public class ResumeActivity extends AppCompatActivity {
    private RecyclerView mResumeRecyclerView;
    private PartyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);

        mResumeRecyclerView = findViewById(R.id.resumeRecyclerView);

        adapter = new PartyAdapter(new LinkedList<>());
        mResumeRecyclerView.setHasFixedSize(true);
        mResumeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mResumeRecyclerView.setAdapter(adapter);

        addNewParty("jeu 1", "à toi de jouer", "");
        addNewParty("jeu 2", "tu joues dans 2min", "");
        addNewParty("jeu 3", "à Tristan de jouer", "");
        addNewParty("jeu 4", "bientôt à toi", "");
        addNewParty("jeu 5", "en attente de Thomas ...", "");
        addNewParty("jeu 6", "Il te reste 15min", "");
    }

    public void addNewParty(String partyName, String partyState, String partyImgLabel) {
        adapter.update(new PartyData(partyName,partyState,getPartyImgFromLabel(partyImgLabel)));
    }

    public void deleteParty(int position) {
        if (position < adapter.getItemCount() && position > 0) {
            adapter.delete(position);
        }
    }

    public int getPartyImgFromLabel(String label) {
        int id = getResources().getIdentifier(label, "drawable", getPackageName());
        if (id == 0) {
            id = R.drawable.scrabble;
        }
        return id;
    }
}