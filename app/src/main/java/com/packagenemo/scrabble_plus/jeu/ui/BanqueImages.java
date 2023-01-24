package com.packagenemo.scrabble_plus.jeu.ui;

import android.content.res.Resources;
import android.graphics.Bitmap;

import com.packagenemo.scrabble_plus.jeu.model.Case;
import com.packagenemo.scrabble_plus.jeu.model.Lettre;

/**
 * Cette classe sert aux affichages graphiques du jeu
 * Permet de convertir certaines classes en image
 */
public class BanqueImages {

    String path;
    Resources mRessource;

    /**
     * Initialise l'instance avec le chemin contenant les images
     * @param path
     */
    public BanqueImages(Resources mResources, String path){
        // TODO : Je ne sais pas si Android fonctionne avec des chemins, à voir
    }

    /**
     * Converti une instance de case en image
     *
     * @param cas
     * @param dslWidth
     * @param dstHeight
     * @return
     */
    public Bitmap convertCaseToBitmap(Case cas, int dslWidth, int dstHeight){
        // TODO

        // Le truc qu'on veut :
        // Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_test);
        // bitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);

        return null;
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
