package com.packagenemo.scrabble_plus.jeu.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.List;
import java.util.logging.Logger;

/**
 * Représente un Encart sur la fenêtre de jeu.
 *
 * Cet encart a une taille, une position, des graphismes, réagit à un appui sur l'écran par l'utilisateur
 */
public abstract class Encart {
    // JeuView dans lequel est instancié la classe
    protected JeuView mJeuView;

    // Liste qui contient les informations d'affichage données par la partie
    protected List<String> mArrayEncartSplitted;

    protected int mNbCaseLargeurEncart;
    protected int mNbCaseHauteurEncart;

    // Collection de case
    private CollectionCases mCollectionCases;

    // Contient les images utilisées dans l'encart
    private BanqueImages mBanqueImages;

    // Limites du Encart sur le jeuView
    protected int mLeft, mTop, mRight, mBottom;

    private static Logger logger = Logger.getLogger(String.valueOf(Encart.class));

    /**
     * Constructeur, prend en entrée la view et les limites du Encart
     */
    public Encart(JeuView jeuView, int left, int top, int right, int bottom) {
        mJeuView = jeuView;
        mLeft = left;
        mTop = top;
        mRight = right;
        mBottom = bottom;

        // On récupère une première fois le string du Encart pour connaitre ses dimensions et set les cases
        metAJourStringJeu();

        // On crée une banque d'images qui contient les images de tous les éléments du jeu
        mBanqueImages = new BanqueImages(jeuView.getContext());

        // On set la collection de cases qui répertorie la position de toutes les cases et leur image associée
        mCollectionCases = new CollectionCases(mBanqueImages,
                mNbCaseLargeurEncart, mNbCaseHauteurEncart, mLeft, mTop, mRight, mBottom);

    }

    /**
     * Update de la position de tous les éléments du Encart
     */
    public void update () {
        metAJourStringJeu();

        mCollectionCases.majCases(mArrayEncartSplitted);
    }

    /**
     * Met à jour le dessin de l'encart sur la SurfaceView
     */
    public void draw(Canvas canvas){
        Paint paint = new Paint();

        // On dessine toutes les cases présentes
        for (Case uneCase : mCollectionCases.getCaseList()){
            Bitmap imageCase = uneCase.getImageContenu();
            canvas.drawBitmap(imageCase, uneCase.getX(), uneCase.getY(), paint);
        }
    }

    /**
     * Appelé lorsque l'utilisateur touche l'écran
     * @param curseur
     */
    public void onTouchEvent(Curseur curseur){

        // On vérifie que le clic est sur la vue et que c'est un premier appui,
        // sinon, on ne prend pas en compte l'interraction et on supprime les highlights
        if (!touchIsOnView(curseur)){
            mCollectionCases.supprimerHighlights();
            return;
        }


        int[] position;
        if (curseur.isDrag){
            Case caseConcernee = getCaseAtPos(curseur.getX(), curseur.getY());

            boolean laCaseEstAttrapable = caseConcernee.estAttrapable();
            if (laCaseEstAttrapable && curseur.isSticky()){
                curseur.drag(caseConcernee);

                // On converti l'event en coordonnées cases
                position = convertisseurCoordonneesCases(curseur);
                // On envoie la position touchée sur l'Encart à la partie
                transmissionDeLaCommande(position, "drag");
            }
        } else if (curseur.isDrop) {
            position = convertisseurCoordonneesCases(curseur);
            transmissionDeLaCommande(position, "drop");

            mCollectionCases.supprimerHighlights();
        } else if (curseur.isMooving){
            mCollectionCases.highlightCaseAtCoordonnees(curseur.getX(), curseur.getY());
        } else {
            logger.warning("L'action enregistrée dans l'encart est de type inconnu");
        }

    }

    /**
     * Informe si l'interraction est sur le Encart
     * @return
     */
    private boolean touchIsOnView(Curseur curseur){
        if ((curseur.getX() < mLeft || curseur.getX() > mRight) ||
                (curseur.getY() < mTop || curseur.getY() > mBottom)){
            return false;
        }
        return true;
    }

    /**
     * Converti les informations d'un event en position sur l'Encart
     * @param curseur
     * @return
     */
    private int[] convertisseurCoordonneesCases(Curseur curseur){
        int[] coordonnees = mCollectionCases.coordonneesAbsoluesEnCoordonneesCases(
                (int) curseur.getX(), (int) curseur.getY());

        return coordonnees;
    }

    private Case getCaseAtPos(int x, int y){
        return mCollectionCases.getCaseAtCoordonneesAbsolues(x, y);
    }

    /**
     * Transmet la commande que le joueur vient d'effectuer au jeu
     */
    protected abstract void transmissionDeLaCommande(int[] position, String typeAction);

    /**
     * Demande à la partie le dernier état du Encart
     */
    protected abstract void metAJourStringJeu();
}
