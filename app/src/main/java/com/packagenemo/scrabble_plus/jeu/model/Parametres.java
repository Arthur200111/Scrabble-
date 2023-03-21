package com.packagenemo.scrabble_plus.jeu.model;

public class Parametres {

    private int mode;
    private int tempsJeu;

    public Parametres() {
        this.mode = 1;
        this.tempsJeu = 24;
    }

    public Parametres(int mode, int tempsJeu) {
        this.mode = mode;
        this.tempsJeu = tempsJeu;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getTempsJeu() {
        return tempsJeu;
    }

    public void setTempsJeu(int tempsJeu) {
        this.tempsJeu = tempsJeu;
    }
}
