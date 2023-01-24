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
    private BanqueImages mBanqueImages;
    private boolean isPlaying;
    private Partie mPartie;
    private PlateauView mPlateauView;
    private MainJoueurView mMainJoueurView;
    private static int TEMPS_REFRESH = 17;

    // FIXME: Ces variables ci dessous seront à supprimer
    int mPosX,mPosY;
    int mDeltaX, mDeltaY;

    public JeuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getHolder().addCallback(this);

        mPlateauView = new PlateauView(
                this, 10, 10, 1060, 1060);
        mMainJoueurView = new MainJoueurView(
                this, 10, 1060, 1060, 1300);

        isPlaying = true;

        mPosX = 0;
        mPosY = 0;
        mDeltaX = 5;
        mDeltaY = 5;

    }

    /**
     * Ce qui est run pendant le Thread
     */
    @Override
    public void run() {

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

        if (mPosX +500 > this.getWidth()){
            mDeltaX = - Math.abs(mDeltaX);
        } else if (mPosX < 0){
            mDeltaX = Math.abs(mDeltaX);
        }
        if (mPosY +500 > getHeight()){
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

        // Vérifie que la surface est initialisée
        if (!getHolder().getSurface().isValid()){
            return;
        }

        // Fond blanc
        paint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), paint);

        // Draw des éléments sur la surface
        mPlateauView.draw(canvas);
        mMainJoueurView.draw(canvas);

        // Image qui bouge pour le fun
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_test);
        bitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);

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

        // TODO : Ici, on récupère un event à chaque fois que le joueur touche l'écran
        // On se sert de cet event pour interragir avec la partie

        mPlateauView.onTouchEvent(event);

        return true;
    }

    /**
     * Cette méthode est appelée lors de la création du SurfaceView
     * Les éléments graphiques qui s'y trouvent sont initialement chargés sur la surface
     * @param holder The SurfaceHolder whose surface is being created.
     */
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {


        Canvas canvas = getHolder().lockCanvas();
        Paint paint = new Paint();

        // Fond blanc
        paint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), paint);

        // Ajout d'un carré bleu
        paint.setColor(Color.BLUE);
        canvas.drawRect(0, 0, this.getWidth()/2, this.getHeight()/2, paint);

        mPlateauView.draw(canvas);
        draw(canvas);
        getHolder().unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        // Normalement, on s'est fiche de ça
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        // Normalement, on s'est fiche de ça
    }

    public BanqueImages getBanqueImages() {
        return mBanqueImages;
    }

    public Partie getPartie() {
        return mPartie;
    }
}
