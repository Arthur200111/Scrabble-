package com.packagenemo.scrabble_plus.jeu.ui;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.logging.Logger;

public class Curseur {

    private Case mCaseAttrapee;

    private int mXInitialCase;
    private int mYInitialCase;

    private int mXDepartDrag;
    private int mYDepartDrag;

    private int mXActuel;
    private int mYActuel;

    // Quand ce boolean est en true, le curseur sera "collant" pour les cases qui seront sur son chemin
    private boolean mSticky;

    // Si ce booleen est en true, cela signifie que l'action est une action drag
    public boolean isDrag;
    public boolean isMooving;
    public boolean isDrop;

    private static Logger logger = Logger.getLogger(String.valueOf(Curseur.class));

    public Curseur(){
        // Les paramètres sont réglés à -1 pour être en dehors de l'écran
        mXDepartDrag = -1;
        mYDepartDrag = -1;

        mXActuel = -1;
        mYActuel = -1;

        clear();
    }

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
            clear();
        }
    }

    public void update(){
        if (mCaseAttrapee == null){
            return;
        }

        mCaseAttrapee.mX = mXActuel - mXDepartDrag + mXInitialCase;
        mCaseAttrapee.mY = mYActuel - mYDepartDrag + mYInitialCase;
    }

    public void draw(Canvas canvas){
        if (mCaseAttrapee == null){
            return;
        }

        Paint paint = new Paint();

        // On dessine la case sélectionnée
        // Cela permet de l'avoir au dessus de tous les autres éléments sélectionnés
        Bitmap imageCase = mCaseAttrapee.getImageContenu();
        canvas.drawBitmap(imageCase, mCaseAttrapee.getX(), mCaseAttrapee.getY(), paint);
    }

    public void drag(Case caseAttrapee){
        if (!caseAttrapee.mEstLettre){
            logger.info("La case que l'utilisateur essaie d'attraper n'est pas déplaçable");
            clear();
            return;
        }

        mCaseAttrapee = caseAttrapee;

        mXInitialCase = mCaseAttrapee.mX;
        mYInitialCase = mCaseAttrapee.mY;

        mXDepartDrag = mXActuel;
        mYDepartDrag = mYActuel;

        mSticky = false;
    }

    private void drop(){
        mCaseAttrapee.mX = mXInitialCase;
        mCaseAttrapee.mY = mYInitialCase;

        mCaseAttrapee = null;
    }

    private void enregisterCoordonnees(MotionEvent event){
        // FIXME : attention, peut être des problèmes dûs au cast int
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
