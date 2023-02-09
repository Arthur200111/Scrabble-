package com.packagenemo.scrabble_plus.jeu.ui;

import android.graphics.Bitmap;

/**
 * Classe qui représente
 */
public class Case {
    public int mX;
    public int mY;
    public int mLargeur;
    public int mHauteur;

    public boolean mEstLettre;
    private boolean mHighlight;

    private final BanqueImages mBanqueImages;

    private String mContenuCase;

    private Bitmap mImageContenu;

    public Case(BanqueImages banqueImages, int x, int y, int largeur, int hauteur) {
        mX = x;
        mY = y;
        mLargeur = largeur;
        mHauteur = hauteur;

        mHighlight = false;

        mBanqueImages = banqueImages;
    }


    public void update(String contenuCase){

        // Si la case est en hightlight, on modifie son string pour le prendre en compte
        if (mHighlight) {
            // On modifie artificiellement le status "highlight" du string de la case envoyé par la partie
            String[] arrayContenu = contenuCase.split(",");
            arrayContenu[arrayContenu.length - 1] = "1";

            contenuCase = String.join(",", arrayContenu);
        }

        // Si le contenu de la case n'a pas changé, on ne fait rien
        if(!contenuCase.equals(mContenuCase)){
            mContenuCase = contenuCase;

            majImageCase();

            // On met à jour le statut de la classe
            isCaseLettre();
        }
    }

    /**
     * Défini le status de la case en Highlight pendant un nombre de tours défini
     */
    public void setHighlight(boolean highlight){
        mHighlight = highlight;
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
