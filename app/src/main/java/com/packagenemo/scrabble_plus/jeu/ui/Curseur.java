package com.packagenemo.scrabble_plus.jeu.ui;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

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
    // Boolean qui indique quand une action s'est déroulée. Pour le besoin du jeu,
    // on considère qu'une action s'est déroulée si je joueur appuie initialement (Down) ou
    // drop une case (up + caseAttrapee)
    private boolean mEventHappened;

    public Curseur(){
        mEventHappened = false;
        mSticky = false;
    }

    public void onTouchEvent(MotionEvent event){
        enregisterCoordonnees(event);
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN){
            mSticky = true;
            mEventHappened = true;
        } else if (action == MotionEvent.ACTION_MOVE) {
            mSticky = false;
            mEventHappened = false;
        } else if (action == MotionEvent.ACTION_UP){
            mSticky = false;
            mEventHappened = true;

            drop();
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
        Paint paint = new Paint();

        // On dessine la case sélectionnée
        // Cela permet de l'avoir au dessus de tous les autres éléments sélectionnés
        Bitmap imageCase = mCaseAttrapee.getImageContenu();
        canvas.drawBitmap(imageCase, mCaseAttrapee.getX(), mCaseAttrapee.getY(), paint);
    }

    public void drag(Case caseAttrapee){
        mCaseAttrapee = caseAttrapee;

        mXInitialCase = mCaseAttrapee.mX;
        mYInitialCase = mCaseAttrapee.mY;

        mXDepartDrag = mXActuel;
        mYDepartDrag = mYActuel;

        mSticky = false;
    }

    private void drop(){

        if (mCaseAttrapee == null){
            return;
        }

        mEventHappened = true;

        mCaseAttrapee.mX = mXInitialCase;
        mCaseAttrapee.mY = mYInitialCase;

        mCaseAttrapee = null;

        mXDepartDrag = Integer.parseInt(null);
        mYDepartDrag = Integer.parseInt(null);

        mXActuel = Integer.parseInt(null);
        mYActuel = Integer.parseInt(null);
    }

    private void enregisterCoordonnees(MotionEvent event){
        // FIXME : attention, peut être des problèmes dûs au cast int
        mXActuel = (int) event.getX(0);
        mYActuel = (int) event.getY(0);
    }

    public boolean isSticky(){
        return mSticky;
    }

    public boolean isEventHappened(){
        return mEventHappened;
    }

    public int getX() {
        return mXActuel;
    }

    public int getY() {
        return mYActuel;
    }
}
