package com.packagenemo.scrabble_plus.jeu.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Collection de cases
 */
public class CollectionCases {

    private final int LARGEUR_BORDURE_ELEMENTS = 4;
    private int mNbCaseLargeur;
    private int mNbCaseHauteur;

    private int mLargeurCase;
    private int mHauteurcase;

    private int mLeft, mTop, mRight, mBottom;

    private List<Case> mCaseList;
    private BanqueImages mBanqueImages;

    private static Logger logger = Logger.getLogger(String.valueOf(CollectionCases.class));

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

        mLargeurCase = getLargeurAffichage() / mNbCaseLargeur;
        mHauteurcase = getHauteurAffichage() / mNbCaseHauteur;

        Case caseTemp;

        int x, y, largeur, hauteur;

        for (int i = 0; i < mNbCaseHauteur ; i++){
            for (int h = 0; h < mNbCaseLargeur ; h++){
                x = mLeft + h* mLargeurCase + LARGEUR_BORDURE_ELEMENTS;
                y = mTop + i* mHauteurcase + LARGEUR_BORDURE_ELEMENTS;
                largeur = mLargeurCase - 2*LARGEUR_BORDURE_ELEMENTS;
                hauteur = mHauteurcase - 2*LARGEUR_BORDURE_ELEMENTS;

                caseTemp = new Case(mBanqueImages, x, y, largeur, hauteur);
                mCaseList.add(caseTemp);
            }
        }
    }

    public Case getCaseAtCoordonneesAbsolues(int x, int y){
        int[] coord = coordonneesAbsoluesEnCoordonneesCases(x, y);

        return mCaseList.get(coord[1]*mNbCaseLargeur + coord[0]);
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public int[] coordonneesAbsoluesEnCoordonneesCases(int x, int y){
        int[] coordonneeCase = new int[2];

        coordonneeCase[0] = (x - mLeft)/mLargeurCase;
        coordonneeCase[1] = (y - mTop)/mHauteurcase;

        if ((x < mLeft || x > mRight) ||
                (y < mTop || y > mBottom)){
            logger.warning("coordonneesAbsoluesEnRelatives a renvoyé une position en dehors de" +
                    "son périmètre de définition" + mLeft + " " + mRight + " " + mTop + " " +mBottom);
        }

        return coordonneeCase;
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
