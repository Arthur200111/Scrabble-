package com.packagenemo.scrabble_plus.jeu.ui;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.packagenemo.scrabble_plus.R;
import com.packagenemo.scrabble_plus.jeu.model.Case;
import com.packagenemo.scrabble_plus.jeu.model.Lettre;

/**
 * Cette classe sert aux affichages graphiques du jeu
 * Permet de convertir certaines classes en image
 */
public class BanqueImages {

    private String path;
    private Resources mRessource;

    /**
     * Initialise l'instance avec le chemin contenant les images
     * @param path
     */
    public BanqueImages(Resources resources, String path){
        // TODO : Je ne sais pas si Android fonctionne avec des chemins, à voir

        mRessource = resources;
    }

    /**
     * Converti une instance de case en image
     *
     * @param stringCase
     * @param dslWidth
     * @param dstHeight
     * @return
     */
     public Bitmap convertCaseToBitmap(String stringCase, int dslWidth, int dstHeight){
        // TODO

        // Le truc qu'on veut :
        Bitmap bitmap = BitmapFactory.decodeResource(mRessource, R.drawable.image_test);

        return Bitmap.createScaledBitmap(bitmap, dslWidth, dstHeight, false);
    }

    /**
     * Converti une instance de Lettre en image
     *
     * @param lettre
     * @param dslWidth
     * @param dstHeight
     * @return
     */
    public Bitmap convertLettreToBitmap(Lettre lettre, int dslWidth, int dstHeight){
        // TODO

        // Le truc qu'on veut :
        // Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_test);
        // bitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);

        return null;
    }
}
