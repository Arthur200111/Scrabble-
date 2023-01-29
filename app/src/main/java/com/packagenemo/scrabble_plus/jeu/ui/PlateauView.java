package com.packagenemo.scrabble_plus.jeu.ui;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe qui est appelée dans JeuView et qui gère les graphismes du plateau UNIQUEMENT et les
 * interractions avec celui ci
 */
public class PlateauView {

    private JeuView mJeuView;
    private List<String> mArrayPlateauSplitted;
    private int mNbCaseLargeurPlateau;
    private int mNbCaseHauteurPlateau;
    private CollectionCases mCollectionCases;

    // Limites du plateau sur le jeuView
    private int mLeft, mTop, mRight, mBottom;
    private List<Case> mCoordonneesPixelsCases;

    /**
     * Constructeur, prend en entrée la view et les limites du plateau
     */
    public PlateauView(JeuView jeuView, int left, int top, int right, int bottom) {
        mJeuView = jeuView;
        mLeft = left;
        mTop = top;
        mRight = right;
        mBottom = bottom;

        // On récupère une première fois le string du plateau pour connaitre ses dimensions et set les cases
        metAJourStringJeu();

        // On set la collection de cases qui répertorie la position de toutes les cases et leur image associée
        mCollectionCases = new CollectionCases(mJeuView.getResources(),
                mNbCaseLargeurPlateau, mNbCaseHauteurPlateau, mLeft, mTop, mRight, mBottom);

    }

    /**
     * Appelé lorsque l'utilisateur touche l'écran
     * @param event
     */
    public void onTouchEvent(MotionEvent event){

        // On vérifie que le clic est sur la vue, sinon, on ne prend pas en compte l'interraction
        if (!touchIsOnView()){
            return;
        }

        // On converti l'event en coordonnées plateau
        int[] position = convertisseurCoordonneesPlateau(event);

        // On envoie la position touchée sur le plateau à la partie
        transmissionDeLaCommande(position);
    }

    /**
     * Informe si l'interraction est sur le plateau
     * @return
     */
    private boolean touchIsOnView(){
        // TODO : Vérifie que l'interraction se produit sur le plateau

        return true;
    }

    /**
     * Converti les informations d'un event en position sur le plateau
     * @param event
     * @return
     */
    private int[] convertisseurCoordonneesPlateau(MotionEvent event){

        // TODO

        return new int[3];
    }

    /**
     * Transmet la commande que le joueur vient d'effectuer au jeu
     */
    private void transmissionDeLaCommande(int[] position){
        // TODO : Transmet à la partie que le plateau vient d'être touché et où il a été touché

        mJeuView.getPartie().giveInputJoueurPlateau(position);
    }

    /**
     * Update de la position de tous les éléments du plateau
     */
    public void update () {

        metAJourStringJeu();

        mCollectionCases.majContenuCases(mArrayPlateauSplitted);
    }

    /**
     * Met à jour le dessin du plateau sur la SurfaceView
     */
    public void draw(Canvas canvas){
        Paint paint = new Paint();

        paint.setColor(Color.YELLOW);
        canvas.drawRect(mLeft, mTop, mRight, mBottom, paint);

        for (Case uneCase : mCollectionCases.getCaseList()){
            Bitmap imageCase = uneCase.getImageContenu();
            canvas.drawBitmap(imageCase, uneCase.mX, uneCase.mY, paint);
        }
    }


    /**
     * Demande à la partie le dernier état du plateau
     */
    private void metAJourStringJeu(){
        String stringPlateau = mJeuView.getPartie().getStringPlateau();
        String[] stringPlateauSplitted = stringPlateau.split(";");

        mArrayPlateauSplitted = new LinkedList<>();
        mArrayPlateauSplitted.addAll(Arrays.asList(stringPlateauSplitted));

        mNbCaseLargeurPlateau = Integer.parseInt(mArrayPlateauSplitted.remove(1));
        mNbCaseHauteurPlateau = Integer.parseInt(mArrayPlateauSplitted.remove(0));
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
        return mTop - mBottom;
    }


}
