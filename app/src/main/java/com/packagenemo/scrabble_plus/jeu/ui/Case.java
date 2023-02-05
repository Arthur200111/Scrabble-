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

    public boolean mEstLettre;

    private BanqueImages mBanqueImages;

    private String mContenuCase;

    private Bitmap mImageContenu;

    public Case(BanqueImages banqueImages, int x, int y, int largeur, int hauteur) {
        mX = x;
        mY = y;
        mLargeur = largeur;
        mHauteur = hauteur;
        mBanqueImages = banqueImages;
    }


    public void setContenuCase(String contenuCase){

        // Si le contenu de la case n'a pas changé, on ne fait rien
        if(!contenuCase.equals(mContenuCase)){
            mContenuCase = contenuCase;

            majImageCase();

            isCaseLettre();
        }
    }

    private void majImageCase(){
        mImageContenu = mBanqueImages.convertCaseToBitmap(mContenuCase, mLargeur, mHauteur);
    }

    private void isCaseLettre(){
        if (mContenuCase.split(",")[0].equals("2")) {
            mEstLettre = true;
        } else {
            mEstLettre = false;
        }
    }

    public boolean estAttrapable(){
        return this.mEstLettre;
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
