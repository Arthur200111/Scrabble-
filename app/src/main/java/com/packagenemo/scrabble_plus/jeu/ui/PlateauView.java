package com.packagenemo.scrabble_plus.jeu.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.packagenemo.scrabble_plus.jeu.model.Plateau;

/**
 * Classe qui est appelée dans JeuView et qui gère les graphismes du plateau UNIQUEMENT et les
 * interractions avec celui ci
 */
public class PlateauView {

    private JeuView mJeuView;
    private Plateau mPlateau;

    // Limites du plateau sur le jeuView
    int mLeft, mTop, mRight, mBottom;

    /**
     * Constructeur, prend en entrée la view et les limites du plateau
     */
    public PlateauView(JeuView jeuView, int left, int top, int right, int bottom) {
        mJeuView = jeuView;
        mLeft = left;
        mTop = top;
        mRight = right;
        mBottom = bottom;

        //mPlateau = mJeuView.getPartie().getPlateau();
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
     * Met à jour le dessin du plateau sur la SurfaceView
     */
    public void draw(Canvas canvas){
        Paint paint = new Paint();

        // TODO
        // On utilise mPlateau et on le décompose en image

        paint.setColor(Color.YELLOW);
        canvas.drawRect(mLeft, mTop, mRight, mBottom, paint);

        //mJeuView.getBanqueImages().convertCaseToBitmap();
    }
}
