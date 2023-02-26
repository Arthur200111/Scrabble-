package com.packagenemo.scrabble_plus.jeu.ui;

public class AffichageTexteMessageAuJoueur extends AffichageTexte{

    private String TEXTE_AVANT_MESSAGE = "Message : ";

    public AffichageTexteMessageAuJoueur(JeuView jeuView, int left, int top, int right, int bottom) {
        super(jeuView, left, top, right, bottom);
    }

    public void update(){
        mTexteAAfficher = TEXTE_AVANT_MESSAGE + mJeuView.getPartie().getStringMessageAuJoueur();
    }
}
