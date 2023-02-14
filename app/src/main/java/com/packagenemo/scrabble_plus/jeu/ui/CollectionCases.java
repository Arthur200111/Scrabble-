package com.packagenemo.scrabble_plus.jeu.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Collection de cases
 * Implémente des méthodes qui permettent de naviguer dans cette collection
 */
public class CollectionCases {

    // Largeur de la bordure autour de chaque case de la collection
    private final int LARGEUR_BORDURE_ELEMENTS = 4;
    private final int mNbCaseLargeur;
    private final int mNbCaseHauteur;

    private int mLargeurCase;
    private int mHauteurcase;

    private final int mLeft;
    private final int mTop;
    private final int mRight;
    private final int mBottom;

    private final List<Case> mCaseList;
    private final BanqueImages mBanqueImages;

    private Case mDerniereCaseHighlight;

    private static final Logger logger = Logger.getLogger(String.valueOf(CollectionCases.class));

    /**
     * Constructeur
     * @param banqueImages : Banque d'images associée à la collection
     * @param nbCaseLargeur : nb de cases en largeur
     * @param nbCaseHauteur : nb de cases en hauteur
     * @param left : position de la partie gauche de la zone de définition
     * @param top : position de la partie haute de la zone de définition
     * @param right : position de la partie droite de la zone de définition
     * @param bottom : position de la partie basse de la zone de définition
     */
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
     * Initialise une instance de la classe Case par case
     */
    private void miseAJourAllocationCases(){

        // Défini la largeur et la hauteur d'une case
        mLargeurCase = getLargeurAffichage() / mNbCaseLargeur;
        mHauteurcase = getHauteurAffichage() / mNbCaseHauteur;

        Case caseTemp;

        int x, y, largeur, hauteur;

        // Crée une case par élément du quadrillage
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

    /**
     * Retourne la case sous les coordonnées indiquées
     * @param x : x sur l'écran
     * @param y : y sur l'écran
     * @return Case à ces coordonnées
     */
    public Case getCaseAtCoordonneesAbsolues(int x, int y){
        int[] coord = coordonneesAbsoluesEnCoordonneesCases(x, y);

        return mCaseList.get(coord[1]*mNbCaseLargeur + coord[0]);
    }

    /**
     * Retourne la position relative des cases par rapport au quadrillage
     * @param x : x sur l'écran
     * @param y : y sur l'écran
     * @return Position en coordonnées quadrillage
     */
    public int[] coordonneesAbsoluesEnCoordonneesCases(int x, int y){
        int[] coordonneeCase = new int[2];

        coordonneeCase[0] = (x - mLeft)/mLargeurCase;
        coordonneeCase[1] = (y - mTop)/mHauteurcase;

        // Ces cas se produisent au bord du plateau.
        // L'arrondissement à l'int de la taille de chaque case crée une petite marge
        if (coordonneeCase[0] >= mNbCaseLargeur){
            coordonneeCase[0] = mNbCaseLargeur - 1;
        }
        if (coordonneeCase[1] >= mNbCaseHauteur){
            coordonneeCase[1] = mNbCaseHauteur - 1;
        }

        if ((x < mLeft || x > mRight) ||
                (y < mTop || y > mBottom)){
            logger.warning("coordonneesAbsoluesEnRelatives a renvoyé une position en dehors de" +
                    "son périmètre de définition" + mLeft + " " + mRight + " " + mTop + " " +mBottom);
        }

        return coordonneeCase;
    }

    /**
     * Mise à jour du contenu de chauque case
     * @param contenuCases : Liste de String représentant le contenu de chaque case
     */
    public void majCases(List<String> contenuCases){

        int index = 0;
        for (String contenu : contenuCases){
            mCaseList.get(index).update(contenu);

            index++;
        }
    }

    /**
     * Highlight la case aux coordonnées indiquées
     * @param x : x de la SurfaceView
     * @param y : y de la SurfaceView
     */
    public void highlightCaseAtCoordonnees(int x, int y){
        Case caseAHighlight = getCaseAtCoordonneesAbsolues(x, y);

        if (caseAHighlight != mDerniereCaseHighlight){
            if (mDerniereCaseHighlight == null){
                mDerniereCaseHighlight = caseAHighlight;
                mDerniereCaseHighlight.setHighlight(true);
            } else {
                mDerniereCaseHighlight.setHighlight(false);
                caseAHighlight.setHighlight(true);
                mDerniereCaseHighlight = caseAHighlight;
            }
        }
    }

    /**
     * Supprime les Highlight dans la collection
     */
    public void supprimerHighlights(){
        if (mDerniereCaseHighlight != null){
            mDerniereCaseHighlight.setHighlight(false);
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
