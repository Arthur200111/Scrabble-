package com.packagenemo.scrabble_plus.jeu.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.List;

public abstract class Encart {
    protected JeuView mJeuView;
    protected List<String> mArrayEncartSplitted;
    protected int mNbCaseLargeurEncart;
    protected int mNbCaseHauteurEncart;
    private CollectionCases mCollectionCases;
    private BanqueImages mBanqueImages;

    // Limites du Encart sur le jeuView
    protected int mLeft, mTop, mRight, mBottom;
    private List<Case> mCoordonneesPixelsCases;

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

        mCollectionCases.majContenuCases(mArrayEncartSplitted);
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
     * @param event
     */
    public void onTouchEvent(MotionEvent event){

        // On vérifie que le clic est sur la vue et que c'est un premier appui,
        // sinon, on ne prend pas en compte l'interraction
        if (!touchIsOnView(event) || event.getAction() != MotionEvent.ACTION_DOWN){
            return;
        }

        // On converti l'event en coordonnées cases
        int[] position = convertisseurCoordonneesCases(event);

        // On envoie la position touchée sur le Encart à la partie
        transmissionDeLaCommande(position);
    }

    /**
     * Informe si l'interraction est sur le Encart
     * @return
     */
    private boolean touchIsOnView(MotionEvent event){
        if ((event.getX() < mLeft || event.getX() > mRight) ||
                (event.getY() < mTop || event.getY() > mBottom)){
            return false;
        }
        return true;
    }

    /**
     * Converti les informations d'un event en position sur le Encart
     * @param event
     * @return
     */
    private int[] convertisseurCoordonneesCases(MotionEvent event){
        int[] coordonnees = mCollectionCases.coordonneesAbsoluesEnCoordonneesCases(
                (int) event.getX(0), (int) event.getY(0));

        return coordonnees;
    }

    /**
     * Transmet la commande que le joueur vient d'effectuer au jeu
     */
    protected abstract void transmissionDeLaCommande(int[] position);

    /**
     * Demande à la partie le dernier état du Encart
     */
    protected abstract void metAJourStringJeu();
}
