package com.packagenemo.scrabble_plus.jeu.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe qui est appelée dans JeuView et qui gère les graphismes de la main
 *  du joueur UNIQUEMENT et les interractions avec celles ci
 */
public class EncartMainJoueur extends Encart {

    public EncartMainJoueur(JeuView jeuView, int left, int top, int right, int bottom) {
        super(jeuView, left, top, right, bottom);
    }

    /**
     * Met à jour le dessin de l'encart sur la SurfaceView
     */
    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();

        // On dessine un fond noir pour le plateau
        paint.setColor(Color.WHITE);
        canvas.drawRect(mLeft, mTop, mRight, mBottom, paint);

        super.draw(canvas);
    }

    /**
     * Transmet la commande que le joueur vient d'effectuer au jeu
     */
    @Override
    protected void transmissionDeLaCommande(int[] position) {
        // On ne transmet que le premier élément du fait que la main est affichée sur
        // une seule dimension
        mJeuView.getPartie().giveInputJoueurMain(position[0]);
    }

    /**
     * Demande à la partie le dernier état de la main
     */
    @Override
    protected void metAJourStringJeu(){
        String stringEncart = mJeuView.getPartie().getStringMainJoueur();
        String[] stringEncartSplitted = stringEncart.split(";");

        mArrayEncartSplitted = new ArrayList<>();
        mArrayEncartSplitted.addAll(Arrays.asList(stringEncartSplitted));

        // On supprime les deux éléments qui nous renseignent sur le nombre de cases en
        // en Largeur seulement
        mNbCaseLargeurEncart = Integer.parseInt(mArrayEncartSplitted.remove(0));
        mNbCaseHauteurEncart = 1;
    }

    @Override
    public void onTouchEvent(Curseur curseur){
        super.onTouchEvent(curseur);


    }
}
