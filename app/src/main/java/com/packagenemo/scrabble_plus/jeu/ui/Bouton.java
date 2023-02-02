package com.packagenemo.scrabble_plus.jeu.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.packagenemo.scrabble_plus.jeu.ui.JeuView;

public abstract class Bouton {
    protected JeuView mJeuView;
    protected int mLeft, mTop, mRight, mBottom;
    protected Bitmap mBitmap;


    /**
     * Constructeur, prend en entrée la view et les limites du Encart
     */
    public Bouton(JeuView jeuView, int left, int top, int right, int bottom) {
        mJeuView = jeuView;
        mLeft = left;
        mTop = top;
        mRight = right;
        mBottom = bottom;
    }

    /**
     * Met à jour le dessin du bouton sur la SurfaceView
     */
    public void draw(Canvas canvas){
        Paint paint = new Paint();

        canvas.drawBitmap(mBitmap, mLeft, mTop, paint);
    }

    /**
     * Appelé lorsque l'utilisateur touche l'écran
     * @param curseur
     */
    public abstract void onTouchEvent(Curseur curseur);

    /**
     * Informe si l'interraction est sur le Encart
     * @return
     */
    protected boolean touchIsOnView(Curseur curseur){
        if ((curseur.getX() < mLeft || curseur.getX() > mRight) ||
                (curseur.getY() < mTop || curseur.getY() > mBottom)){
            return false;
        }
        return true;
    }

    protected void chargeBitmap(String nomImage){
        int dslWidth = mRight - mLeft;
        int dstHeight = mBottom - mTop;
        Context context = mJeuView.getContext();

        int resID = context.getResources().getIdentifier(
                nomImage , "drawable", context.getPackageName());
        Bitmap bitmap = BitmapFactory.decodeResource(
                context.getResources(), resID);

        mBitmap = Bitmap.createScaledBitmap(bitmap, dslWidth, dstHeight,false);
    }
}
