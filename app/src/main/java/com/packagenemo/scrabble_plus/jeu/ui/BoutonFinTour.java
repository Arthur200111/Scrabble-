package com.packagenemo.scrabble_plus.jeu.ui;

import android.view.MotionEvent;

public class BoutonFinTour extends Bouton {

    /**
     * Constructeur, prend en entrée la view et les limites du Encart
     */
    public BoutonFinTour(JeuView jeuView, int left, int top, int right, int bottom) {
        super(jeuView, left, top, right, bottom);

        chargeBitmap("jeu_ui_fin_tour");
    }


    /**
     * Appelé lorsque l'utilisateur touche l'écran
     * @param event
     */
    public void onTouchEvent(MotionEvent event){

        // On vérifie que le clic est sur la vue et que c'est un premier appui,
        // sinon, on ne prend pas en compte l'interraction
        if (!touchIsOnView(event) || event.getAction() != MotionEvent.ACTION_DOWN){
            return;
        }

        mJeuView.getPartie().giveInputJoueurFinTour();
    }
}

