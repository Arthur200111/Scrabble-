package com.packagenemo.scrabble_plus.jeu.ui;

import android.graphics.Bitmap;

/**
 * Classe qui représente une case
 *
 * Une case peut être un élément du plateau comme une case vide, une lettre ou une case vide de la main
 */
public class Case {

    //Coordonnées du point en haut à gauche de la case sur la SurfaceView
    public int mX;
    public int mY;

    public int mLargeur;
    public int mHauteur;

    public boolean mEstAttrapee;
    public boolean mEstLettre;
    private boolean mHighlight;
    private boolean mTransparence;

    private final BanqueImages mBanqueImages;

    // String qui décrit le contenu de la case
    private String mContenuCase;

    // Image de la case
    private Bitmap mImageContenu;

    /**
     * Constructeur
     * @param banqueImages : Banque d'images
     * @param x : Coordonnées x du point en haut à gauche de la case sur la SurfaceView
     * @param y : Coordonnées y du point en haut à gauche de la case sur la SurfaceView
     * @param largeur : Largeur de la case
     * @param hauteur : Hauteur de la case
     */
    public Case(BanqueImages banqueImages, int x, int y, int largeur, int hauteur) {
        mX = x;
        mY = y;
        mLargeur = largeur;
        mHauteur = hauteur;

        mHighlight = false;
        mTransparence = false;

        mBanqueImages = banqueImages;
    }

    /**
     * Uptade du contenu de la case
     * Met à jour son image si le contenu a changé
     * @param contenuCase : String décrivant le design de la case
     */
    public void update(String contenuCase){

        // Si la case est en hightlight, on modifie son string pour le prendre en compte
        if (mHighlight) {
            // On modifie artificiellement le status "highlight" du string de la case envoyé par la partie
            String[] arrayContenu = contenuCase.split(",");
            arrayContenu[arrayContenu.length - 1] = "1";

            contenuCase = String.join(",", arrayContenu);
        }

        // pareil pour la tansparence
        if (mTransparence){
            contenuCase += ",T";
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
     * Set le status du Highlight
     * @param highlight : true si la case est surbrillante
     */
    public void setHighlight(boolean highlight){
        mHighlight = highlight;
    }

    /**
     * Set le statut de transparence de la case
     * @param transparence : true si transparente
     */
    public void setTransparence(boolean transparence){
        mTransparence = transparence;
    }

    /**
     * Mise à jour de l'image de la case grâce à la Banque de données
     */
    private void majImageCase(){
        mImageContenu = mBanqueImages.convertCaseToBitmap(mContenuCase, mTransparence, mLargeur, mHauteur);
    }

    /**
     * Met à jour le status de lettre de la case
     * Sert pour savoir si une case est attrapable par le curseur
     */
    private void isCaseLettre(){
        if (mContenuCase.split(",")[0].equals("2")) {
            mEstLettre = true;
        } else {
            mEstLettre = false;
        }
    }

    /**
     * Retourne un booleen qui décrit si la case est attrapable
     * @return le status de la case
     */
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
