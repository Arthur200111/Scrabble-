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

import java.util.logging.Logger;

/**
 * Gère la fenêtre de jeu
 */
public class JeuView extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    private Partie mPartie;

    private Thread thread;
    private boolean isPlaying;

    // Elements affichés sur la View
    private EncartPlateau mEncartPlateau;
    private EncartMainJoueur mEncartMainJoueur;
    private BoutonPoubelle mBoutonPoubelle;
    private BoutonFinTour mBoutonFinTour;
    private AffichageTextePoints mAffichageTextePoints;
    private AffichageTexteMessageAuJoueur mAffichageTexteMessageAuJoueur;

    private Curseur mCurseur;

    private long mTempsDerniereFrame;

    // Nombre de frames depuis le début du lancement de l'instance
    private int mNombreDeFrames;
    // Variable qui va stocker un temps pour le mesure du nombre d'images par secondes
    private long mMesureTempsFramerate;
    private long mFramerate;

    // Constantes
    private final int FPS_VOULU = 35;
    private final long TIME_OUT_MILLIS = 1000;
    private final int ECART_IMAGES_FRAMERATE = 100;

    private static Logger logger = Logger.getLogger(String.valueOf(JeuView.class));

    public JeuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getHolder().addCallback(this);

        // TODO : Récupérer les infos pour la co BDD
        this.mPartie = new Partie("idPartieBDD","loginJoueurCourant");

        mCurseur = new Curseur();
    }

    private void creationDesElementsDeJeu(){
        // Nombre de cases en horizontal dans la main
        int tailleMain = 7;

        // Paramètres modifiables de taille de tous les éléments de la fenêtre de jeu
        int margeEntreEncartsEtEcran = 10;
        int distanceIconesMain = 30;
        double ratioIcones = 1.4;
        int largeurIcones = 100;
        int ecartIconesEntreElles = 100;

        // Paramètres fixes de taille de tous les éléments de la fenêtre de jeu
        int largeurFenetre = getWidth();
        int milieuFenetre = largeurFenetre/2;
        int basEncartMain = largeurFenetre + margeEntreEncartsEtEcran +
                (largeurFenetre - 2*margeEntreEncartsEtEcran)/tailleMain;

        int basBoutons = basEncartMain + distanceIconesMain + (int) (largeurIcones * ratioIcones);

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
                basBoutons);

        mBoutonPoubelle = new BoutonPoubelle(
                this,
                milieuFenetre - ecartIconesEntreElles - largeurIcones,
                basEncartMain + distanceIconesMain,
                milieuFenetre - ecartIconesEntreElles,
                basBoutons);

        mAffichageTextePoints = new AffichageTextePoints(
                this,
                margeEntreEncartsEtEcran,
                basBoutons + 120,
                0,
                0);

        mAffichageTexteMessageAuJoueur = new AffichageTexteMessageAuJoueur(
                this,
                margeEntreEncartsEtEcran,
                basBoutons + 320,
                0,
                0
        );
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

        // Boucle principale qui update l'affichage
        while (isPlaying) {
            update ();
            draw ();
            sleep ();

            // Doit être appelé à chaque boucle
            calculeFrameRate();
        }
    }

    /**
     * Update de la position de tous les éléments du jeu
     */
    private void update () {
        mEncartPlateau.update();
        mEncartMainJoueur.update();

        mAffichageTextePoints.update();
        mAffichageTexteMessageAuJoueur.update();

        mCurseur.update();
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

        mAffichageTextePoints.draw(canvas);
        mAffichageTexteMessageAuJoueur.draw(canvas);

        mCurseur.draw(canvas);

        getHolder().unlockCanvasAndPost(canvas);
    }


    /**
     * Pause entre chaque mise à jour de l'écran
     */
    private void sleep () {

        long intervalle = System.currentTimeMillis() - mTempsDerniereFrame;


        while (intervalle < 1000/FPS_VOULU){
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            intervalle = System.currentTimeMillis() - mTempsDerniereFrame;
        }

        mTempsDerniereFrame = System.currentTimeMillis();
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
        mCurseur.onTouchEvent(event);

        mEncartPlateau.onTouchEvent(mCurseur);
        mEncartMainJoueur.onTouchEvent(mCurseur);
        mBoutonPoubelle.onTouchEvent(mCurseur);
        mBoutonFinTour.onTouchEvent(mCurseur);

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

    private void calculeFrameRate(){
        mNombreDeFrames ++;
        if (mNombreDeFrames < ECART_IMAGES_FRAMERATE){
            return;
        }

        mNombreDeFrames = 0;

        mFramerate = (ECART_IMAGES_FRAMERATE * 1000) /
                (System.currentTimeMillis() - mMesureTempsFramerate);

        mMesureTempsFramerate = System.currentTimeMillis();

        if (mFramerate != 0 && mFramerate < FPS_VOULU*0.9){
            logger.info("La fenêtre de jeu ne peut pas satisfaire " +
                    "les exigences données par FPS_VOULU");
        }
    }


    public Partie getPartie() {
        return mPartie;
    }

    public long getFramerate() {
        return mFramerate;
    }
}
