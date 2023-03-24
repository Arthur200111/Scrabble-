package com.packagenemo.scrabble_plus.jeu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.ImageView;

import com.packagenemo.scrabble_plus.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

/**
 * Instancie les éléments affichés à l'écran
 */
public class JeuActivity extends AppCompatActivity {

    private JeuView mJeuView;
    private static Context context;
    private static Logger logger = Logger.getLogger(String.valueOf(JeuActivity.class));

    /**
     * On instancie la surface de jeu
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String partyId = getIntent().getStringExtra("partyId");
        Intent intent = getIntent();
        partyId = intent.getStringExtra("partyId");

        logger.info("L'id de la partie est " + partyId);

        context = getApplicationContext();
        setContentView(R.layout.activity_jeu);
        mJeuView = findViewById(R.id.jeu_zone_jeu);
        mJeuView.initialiserPartie(partyId);
    }


    /**
     * Lorsque l'activité est en pause, la vue du jeu est en pause
     */
    @Override
    protected void onPause() {
        super.onPause();
        mJeuView.pause();
    }

    /**
     * Lorsque l'activité reprend, la vue du jeu reprend.
     */
    @Override
    protected void onResume() {
        System.out.println("onresume");
        super.onResume();

        // TODO : navré ...
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mJeuView.resume();
            }
        }, 2000);
    }

    public void resume(){

    }

    /**
     * Renvoie le contexte afin de pouvoir gérer certaine fonction dans le modèel
     * @return
     */
    public static Context getContext() {
        return context;
    }
}