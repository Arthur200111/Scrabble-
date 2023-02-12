package com.packagenemo.scrabble_plus.jeu.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public abstract class AffichageTexte {
    protected JeuView mJeuView;
    protected int mLeft, mTop, mRight, mBottom;

    protected String mTexteAAfficher;
    protected int COULEUR_DU_TEXTE = Color.BLACK;

    public AffichageTexte(JeuView jeuView, int left, int top, int right, int bottom) {
        mJeuView = jeuView;

        mTexteAAfficher = "";

        mLeft = left;
        mTop = top;
        mRight = right;
        mBottom = bottom;
    }

    public abstract void update();

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(COULEUR_DU_TEXTE);
        paint.setTextSize(80);

        canvas.drawText(mTexteAAfficher, mLeft, mTop, paint);
    }
}
