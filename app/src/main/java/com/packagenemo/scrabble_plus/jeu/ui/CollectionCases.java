package com.packagenemo.scrabble_plus.jeu.ui;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

/**
 * Collection de cases
 */
public class CollectionCases {

    private final int LARGEUR_BORDURE_ELEMENTS = 4;
    private int mNbCaseLargeur;
    private int mNbCaseHauteur;

    private int mLeft, mTop, mRight, mBottom;

    private List<Case> mCaseList;
    private BanqueImages mBanqueImages;


    public CollectionCases(BanqueImages banqueImages, int nbCaseLargeur, int nbCaseHauteur, int left, int top, int right, int bottom) {
        mNbCaseLargeur = nbCaseLargeur;
        mNbCaseHauteur = nbCaseHauteur;
        mLeft = left;
        mTop = top;
        mRight = right;
        mBottom = bottom;

        mBanqueImages = banqueImages;

        mCaseList = new ArrayList<>();

        miseAJourAllocationCases();
    }

    /**
     * Met à jour l'allocation en pixels des cases en modifiant la liste mCoordonneesPixelsCases
     */
    private void miseAJourAllocationCases(){

        int largeurCase = getLargeurAffichage() / mNbCaseLargeur;
        int hauteurCase = getHauteurAffichage() / mNbCaseHauteur;

        Case caseTemp;

        int x, y, largeur, hauteur;

        for (int i = 0; i < mNbCaseHauteur ; i++){
            for (int h = 0; h < mNbCaseLargeur ; h++){
                x = mLeft + h*largeurCase + LARGEUR_BORDURE_ELEMENTS;
                y = mTop + i*hauteurCase + LARGEUR_BORDURE_ELEMENTS;
                largeur = largeurCase - 2*LARGEUR_BORDURE_ELEMENTS;
                hauteur = hauteurCase - 2*LARGEUR_BORDURE_ELEMENTS;

                caseTemp = new Case(mBanqueImages, x, y, largeur, hauteur);
                mCaseList.add(caseTemp);
            }
        }
    }

    public void majContenuCases(List<String> contenuCases){

        int index = 0;
        for (String contenu : contenuCases){
            mCaseList.get(index).setContenuCase(contenu);

            index++;
        }
    }

    /**
     * Renvoie la largeur de l'affichage en pixels
     * @return
     */
    private int getLargeurAffichage(){
        return mRight - mLeft;
    }

    /**
     * Renvoie la hauteur de l'affichage en pixels
     * @return
     */
    private int getHauteurAffichage(){
        return mBottom - mTop;
    }

    public List<Case> getCaseList() {
        return mCaseList;
    }
}
