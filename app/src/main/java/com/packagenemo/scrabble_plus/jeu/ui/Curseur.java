package com.packagenemo.scrabble_plus.jeu.ui;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.logging.Logger;

/**
 * Une instance de cette classe représente l'appui d'un utilisateur sur l'écran
 */
public class Curseur {

    // Case "drag" par le curseur
    private Case mCaseAttrapee;

    // Coordonnées initiales de la case dragée
    private int mXInitialCase;
    private int mYInitialCase;

    // Coordonnées initiales du curseur lorsqu'il "drag"
    private int mXDepartDrag;
    private int mYDepartDrag;

    // Coordonnées à jour du curseur
    private int mXActuel;
    private int mYActuel;

    // Quand ce boolean est en true, le curseur sera "collant" pour les cases qui seront sur son chemin
    private boolean mSticky;

    // Si ce booleen est en true, cela signifie que l'action est une action drag
    public boolean isDrag;
    public boolean isMooving;
    public boolean isDrop;

    private static final Logger logger = Logger.getLogger(String.valueOf(Curseur.class));

    /**
     * Constructeur
     */
    public Curseur(){
        // Les paramètres sont réglés à -1 pour être en dehors de l'écran
        mXDepartDrag = -1;
        mYDepartDrag = -1;

        mXActuel = -1;
        mYActuel = -1;

        clear();
    }

    /**
     * Met le curseur en position initiale, hors de l'écran et dans un état neutre
     */
    private void clear(){
        mSticky = false;
        isDrag = false;
        isDrop = false;
        isMooving = false;

        mXDepartDrag = -1;
        mYDepartDrag = -1;

        mXActuel = -1;
        mYActuel = -1;
    }

    /**
     * Appelé lorque l'utilisateur appuie sur l'écran
     * Décrit les différents comportements associés à ces actions
     * @param event : event décrivant l'action de l'utilisateur sur l'écran
     */
    public void onTouchEvent(MotionEvent event){
        enregisterCoordonnees(event);
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN){
            mSticky = true;

            isDrag = true;
            isDrop = false;
            isMooving = false;

        } else if (action == MotionEvent.ACTION_MOVE && mCaseAttrapee != null) {
            mSticky = false;

            isDrag = false;
            isDrop = false;
            isMooving = true;

        } else if (action == MotionEvent.ACTION_UP && mCaseAttrapee != null){
            mSticky = false;

            isDrag = false;
            isDrop = true;
            isMooving = false;

            drop();
        } else {
            // On met le curseur dans un état neutre en cas d'action non reconnue
            clear();
        }
    }

    /**
     * Uptdate de la position de la case attrapée par le curseur
     */
    public void update(){
        if (mCaseAttrapee == null){
            return;
        }

        mCaseAttrapee.mX = mXActuel - mXDepartDrag + mXInitialCase;
        mCaseAttrapee.mY = mYActuel - mYDepartDrag + mYInitialCase;
    }

    /**
     * Dessine la case attrapée par le curseur
     * @param canvas : canvas
     */
    public void draw(Canvas canvas){
        if (mCaseAttrapee == null){
            return;
        }

        Paint paint = new Paint();

        // On redessine la case sélectionnée
        // Cela permet de l'avoir au dessus de tous les autres éléments sélectionnés
        Bitmap imageCase = mCaseAttrapee.getImageContenu();
        canvas.drawBitmap(imageCase, mCaseAttrapee.getX(), mCaseAttrapee.getY(), paint);
    }

    /**
     * Le curseur tâche d'attraper la case sur sa position
     * @param caseAttrapee : case sous le curseur
     */
    public void drag(Case caseAttrapee){
        if (!caseAttrapee.mEstLettre){
            logger.info("La case que l'utilisateur essaie d'attraper n'est pas déplaçable");
            clear();
            return;
        }

        mCaseAttrapee = caseAttrapee;
        mCaseAttrapee.mEstAttrapee = true;

        mXInitialCase = mCaseAttrapee.mX;
        mYInitialCase = mCaseAttrapee.mY;

        mXDepartDrag = mXActuel;
        mYDepartDrag = mYActuel;

        mSticky = false;

        mCaseAttrapee.setTransparence(true);
    }

    /**
     * Drop de la case attrapée
     */
    private void drop(){
        mCaseAttrapee.mX = mXInitialCase;
        mCaseAttrapee.mY = mYInitialCase;

        mCaseAttrapee.mEstAttrapee = false;
        mCaseAttrapee.setTransparence(false);

        mCaseAttrapee = null;
    }

    /**
     * Enregistrement de la position de l'event dans le curseur
     * @param event : event
     */
    private void enregisterCoordonnees(MotionEvent event){
        mXActuel = (int) event.getX(0);
        mYActuel = (int) event.getY(0);
    }

    public boolean isSticky(){
        return mSticky;
    }

    public int getX() {
        return mXActuel;
    }

    public int getY() {
        return mYActuel;
    }
}
