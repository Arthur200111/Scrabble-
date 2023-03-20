package com.packagenemo.scrabble_plus.jeu.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Classe qui est appelée dans JeuView et qui gère les graphismes de la main
 *  du joueur UNIQUEMENT et les interractions avec celles ci
 */
public class EncartMainJoueur extends Encart {

    private static Logger logger = Logger.getLogger(String.valueOf(EncartMainJoueur.class));

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
    protected void transmissionDeLaCommande(int[] position, String typeAction) {
        // On ne transmet que le premier élément du fait que la main est affichée sur
        // une seule dimension
        mJeuView.getPartie().giveInputJoueurMain(position[0], typeAction);
    }

    /**
     * Demande à la partie le dernier état de la main et met en forme le résultat
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

        verifStringJeu(mNbCaseLargeurEncart, mNbCaseHauteurEncart, stringEncart);
    }

    /**
     * Appelé lorsque l'utilisateur touche l'écran au niveau de la main
     * @param curseur
     */
    @Override
    public void onTouchEvent(Curseur curseur){
        super.onTouchEvent(curseur);
    }

    /**
     * Vérifie le string que l'encart reçoit de la partie
     * Si ce string n'est pas conforme à ce qui est attendu, affiche un message dans les logs
     * @param mNbCaseLargeurEncart
     * @param mNbCaseHauteurEncart
     * @param stringEncart : String de l'encart
     */
    protected void verifStringJeu(int mNbCaseLargeurEncart,int mNbCaseHauteurEncart, String stringEncart) {

        // On met ce if pour éviter de devoir vérrifier le regex à chaque frame
        if (mOldStringEncart != stringEncart) {

            if (mRegex == null) {
                mRegex = mNbCaseLargeurEncart + ";([0-9]+,[0-9A-Z],[0-9]+,[0-9]+;){" +
                        mNbCaseLargeurEncart*mNbCaseHauteurEncart + "}";
                mParternRegex = Pattern.compile(mRegex);

                logger.info("Le regex des données attendues par la Partie est : " + mRegex);
            }

            mMatcher = mParternRegex.matcher(stringEncart);

            if (!mMatcher.matches()) {
                logger.warning("Le string renvoye par la partie n est pas de la forme attendue. Attendu : "
                        + mRegex + "   Recu : " + stringEncart);
            }

            mOldStringEncart = stringEncart;
        }
    }
}


