package com.packagenemo.scrabble_plus.jeu.ui;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

/**
 * Collection de cases
 */
public class CollectionCases {

    private int mNbCaseLargeur;
    private int mNbCaseHauteur;

    private int mLeft, mTop, mRight, mBottom;

    private List<Case> mCaseList;
    private BanqueImages mBanqueImages;


    public CollectionCases(Resources resources, int nbCaseLargeur, int nbCaseHauteur, int left, int top, int right, int bottom) {
        mNbCaseLargeur = nbCaseLargeur;
        mNbCaseHauteur = nbCaseHauteur;
        mLeft = left;
        mTop = top;
        mRight = right;
        mBottom = bottom;

        mBanqueImages = new BanqueImages(resources, "path");

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

        for (int i = 0; i < mNbCaseHauteur ; i++){
            for (int y = 0; y < mNbCaseLargeur ; y++){
                caseTemp = new Case(mBanqueImages,
                        mLeft + y*largeurCase, mTop + i*hauteurCase, largeurCase, hauteurCase);
                mCaseList.add(caseTemp);

                System.out.println(y*largeurCase);
                System.out.println(i*hauteurCase);
                System.out.println(largeurCase);
                System.out.println(hauteurCase);
                System.out.println("blanc");
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
