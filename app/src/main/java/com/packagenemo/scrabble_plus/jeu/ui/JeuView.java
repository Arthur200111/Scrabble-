package com.packagenemo.scrabble_plus.jeu.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.packagenemo.scrabble_plus.R;
import com.packagenemo.scrabble_plus.jeu.model.Partie;

/**
 * Gère la fenêtre de jeu
 */
public class JeuView extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    private Thread thread;
    private boolean isPlaying;
    private Partie mPartie;
    private EncartPlateau mEncartPlateau;
    private EncartMainJoueur mEncartMainJoueur;
    private BoutonPoubelle mBoutonPoubelle;
    private BoutonFinTour mBoutonFinTour;
    private static int TEMPS_REFRESH = 17;
    private static long TIME_OUT_MILLIS = 1000;

    // FIXME: Ces variables ci dessous seront à supprimer
    int mPosX,mPosY;
    int mDeltaX, mDeltaY;

    public JeuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getHolder().addCallback(this);


        // TODO : Récupérer les infos pour la co BDD
        this.mPartie = new Partie("idPartieBDD","loginJoueurCourant");

        mPosX = 0;
        mPosY = 0;
        mDeltaX = 5;
        mDeltaY = 5;

    }

    private void creationDesElementsDeJeu(){
        // Nombre de cases en horizontal dans la main
        int tailleMain = 7;

        // Paramètres de taille de tous les éléments de la fenêtre de jeu
        int largeurFenetre = getWidth();
        int milieuFenetre = largeurFenetre/2;
        int margeEntreEncartsEtEcran = 10;
        int basEncartMain = largeurFenetre + margeEntreEncartsEtEcran +
                (largeurFenetre - 2*margeEntreEncartsEtEcran)/tailleMain;
        int distanceIconesMain = 30;
        double ratioIcones = 1.4;
        int largeurIcones = 100;
        int ecartIconesEntreElles = 100;

        mEncartPlateau = new EncartPlateau(
                this, margeEntreEncartsEtEcran,
                margeEntreEncartsEtEcran,
                largeurFenetre - margeEntreEncartsEtEcran,
                largeurFenetre - margeEntreEncartsEtEcran);

        mEncartMainJoueur = new EncartMainJoueur(
                this,
                margeEntreEncartsEtEcran,
                largeurFenetre + margeEntreEncartsEtEcran,
                largeurFenetre - margeEntreEncartsEtEcran,
                basEncartMain);

        mBoutonFinTour = new BoutonFinTour(
                this,
                milieuFenetre + ecartIconesEntreElles,
                basEncartMain + distanceIconesMain,
                milieuFenetre + ecartIconesEntreElles + largeurIcones,
                basEncartMain + distanceIconesMain + (int) (largeurIcones * ratioIcones));

        mBoutonPoubelle = new BoutonPoubelle(
                this,
                milieuFenetre - ecartIconesEntreElles - largeurIcones,
                basEncartMain + distanceIconesMain,
                milieuFenetre - ecartIconesEntreElles,
                basEncartMain + distanceIconesMain + (int) (largeurIcones * ratioIcones));
    }

    /**
     * Ce qui est run pendant le Thread
     */
    @Override
    public void run() {
        long timestamp = System.currentTimeMillis();


        // Vérifie que la surface est initialisée
        while (!getHolder().getSurface().isValid()){
            if (System.currentTimeMillis() - timestamp > TIME_OUT_MILLIS){
                throw new RuntimeException("Timeout. " +
                        "Il y a eu un problème dans l'initialisation de la fenêtre de jeu");
            }
        }

        creationDesElementsDeJeu();

        while (isPlaying) {
            update ();
            draw ();
            sleep ();
        }
    }

    /**
     * Update de la position de tous les éléments du jeu
     */
    private void update () {

        mEncartPlateau.update();
        mEncartMainJoueur.update();

        // TODO : supprimer ci dessous et eventuellement update les icones
        if (mPosX +200 > this.getWidth()){
            mDeltaX = - Math.abs(mDeltaX);
        } else if (mPosX < 0){
            mDeltaX = Math.abs(mDeltaX);
        }
        if (mPosY +200 > getHeight()){
            mDeltaY = - Math.abs(mDeltaY);
        } else if (mPosY < 0){
            mDeltaY = Math.abs(mDeltaY);
        }

        mPosX += mDeltaX;
        mPosY += mDeltaY;
    }

    /**
     * Update de tous les graphismes
     */
    private void draw () {

        Canvas canvas = getHolder().lockCanvas();
        Paint paint = new Paint();

        // Fond blanc
        paint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), paint);

        // Draw des éléments sur la surface
        mEncartPlateau.draw(canvas);
        mEncartMainJoueur.draw(canvas);
        mBoutonPoubelle.draw(canvas);
        mBoutonFinTour.draw(canvas);

        // Image qui bouge pour le fun
        // TODO : Les supprimer à terme
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_test);
        bitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, false);

        canvas.drawBitmap(bitmap, mPosX, mPosY, paint);

        // ça, on garde
        getHolder().unlockCanvasAndPost(canvas);
    }


    /**
     * Pause entre chaque mise à jour de l'écran
     */
    private void sleep () {
        try {
            Thread.sleep(TEMPS_REFRESH);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Appelé quand l'activity
     */
    public void resume () {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Stop le Thread si l'activity se met en pause
     */
    public void pause () {
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mEncartPlateau.onTouchEvent(event);
        mEncartMainJoueur.onTouchEvent(event);
        mBoutonPoubelle.onTouchEvent(event);
        mBoutonFinTour.onTouchEvent(event);

        return true;
    }

    /**
     * Cette méthode est appelée lors de la création du SurfaceView
     * Les éléments graphiques qui s'y trouvent sont initialement chargés sur la surface
     * @param holder The SurfaceHolder whose surface is being created.
     */
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();
        Paint paint = new Paint();

        // Fond blanc
        paint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), paint);

        getHolder().unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {}


    public Partie getPartie() {
        return mPartie;
    }
}
