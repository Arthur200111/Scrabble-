package com.packagenemo.scrabble_plus.jeu.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe qui est appelée dans JeuView et qui gère les graphismes du plateau UNIQUEMENT et les
 * interractions avec celui ci
 */
public class EncartPlateau extends Encart {

    public EncartPlateau(JeuView jeuView, int left, int top, int right, int bottom) {
        super(jeuView, left, top, right, bottom);
    }

    /**
     * Met à jour le dessin de l'encart sur la SurfaceView
     */
    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();

        // On dessine un fond noir pour le plateau
        paint.setColor(Color.BLACK);
        canvas.drawRect(mLeft, mTop, mRight, mBottom, paint);

        super.draw(canvas);
    }

    /**
     * Transmet la commande que le joueur vient d'effectuer au jeu
     */
    protected void transmissionDeLaCommande(int[] position){
        mJeuView.getPartie().giveInputJoueurPlateau(position);
    }

    /**
     * Demande à la partie le dernier état du plateau
     */
    @Override
    protected void metAJourStringJeu(){
        String stringEncart = mJeuView.getPartie().getStringPlateau();
        String[] stringEncartSplitted = stringEncart.split(";");

        mArrayEncartSplitted = new ArrayList<>();
        mArrayEncartSplitted.addAll(Arrays.asList(stringEncartSplitted));

        // On supprime les deux éléments qui nous renseignent sur le nombre de cases en
        // Hauteur et en Largeur
        mNbCaseLargeurEncart = Integer.parseInt(mArrayEncartSplitted.remove(1));
        mNbCaseHauteurEncart = Integer.parseInt(mArrayEncartSplitted.remove(0));
    }
}
