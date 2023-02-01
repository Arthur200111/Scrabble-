package com.packagenemo.scrabble_plus.jeu.ui;

import android.graphics.Bitmap;

import java.util.logging.Logger;

/**
 * Classe qui représente
 */
public class Case {

    public int mX;
    public int mY;
    public int mLargeur;
    public int mHauteur;

    private BanqueImages mBanqueImages;

    private String mContenuCase;

    private Bitmap mImageContenu;

    private static Logger logger = Logger.getLogger(String.valueOf(Case.class));

    public Case(BanqueImages banqueImages, int x, int y, int largeur, int hauteur) {
        mX = x;
        mY = y;
        mLargeur = largeur;
        mHauteur = hauteur;
        mBanqueImages = banqueImages;
    }


    public void setContenuCase(String contenuCase){

        // Si le contenu de la case n'a pas changé, on ne fait rien
        if(contenuCase.equals(mContenuCase)){
            return;
        } else {
            mContenuCase = contenuCase;

            majImageCase();
        }
    }

    private void majImageCase(){
        mImageContenu = mBanqueImages.convertCaseToBitmap(mContenuCase, mLargeur, mHauteur);
    }

    public Bitmap getImageContenu() {
        return mImageContenu;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }
}
