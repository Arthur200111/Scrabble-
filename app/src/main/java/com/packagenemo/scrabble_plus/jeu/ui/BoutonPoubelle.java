package com.packagenemo.scrabble_plus.jeu.ui;

/**
 * Bouton de défausse
 */
public class BoutonPoubelle extends Bouton {
    /**
     * Constructeur, prend en entrée la view et les limites du Encart
     */
    public BoutonPoubelle(JeuView jeuView, int left, int top, int right, int bottom) {
        super(jeuView, left, top, right, bottom);

        chargeBitmap("jeu_ui_poubelle");
    }


    /**
     * Appelé lorsque l'utilisateur touche l'écran
     * @param curseur
     */
    public void onTouchEvent(Curseur curseur){

        // On vérifie que le clic est sur la vue et que c'est un premier appui,
        // sinon, on ne prend pas en compte l'interraction
        if (!touchIsOnView(curseur)){
            return;
        }

        if (curseur.isDrop){
            mJeuView.getPartie().giveInputJoueurDefausser();
        }
    }
}
