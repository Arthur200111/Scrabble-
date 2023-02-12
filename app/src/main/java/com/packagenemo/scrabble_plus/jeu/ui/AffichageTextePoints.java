package com.packagenemo.scrabble_plus.jeu.ui;

public class AffichageTextePoints extends AffichageTexte{

    private String TEXTE_AVANT_POINTS = "Points : ";

    public AffichageTextePoints(JeuView jeuView, int left, int top, int right, int bottom) {
        super(jeuView, left, top, right, bottom);
    }

    public void update(){
        mTexteAAfficher = TEXTE_AVANT_POINTS + mJeuView.getPartie().getPointsDuJoueur();
    }
}
