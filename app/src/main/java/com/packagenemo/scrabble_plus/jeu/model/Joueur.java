package com.packagenemo.scrabble_plus.jeu.model;

import java.util.List;

public class Joueur {

    private String nom;
    private int score;
    private MainJoueur mainJ;

    /**
     * Constructeur par défaut, par défaut un joueur n'a pas ne nom et un score de 0
     */
    public Joueur() {
        this("", 0);
    }

    /**
     * Constructeur d'un joueur à partir de son score et de son nom
     *
     * @param name nom du joueur
     * @param sco  score du joueur
     */
    public Joueur(String name, int sco) {
        nom = name;
        score = sco;
        mainJ = new MainJoueur();
    }

    public void stringToJoueur(String info){
        String[] split = info.split(",");
        this.nom = split[0];
        this.score = Integer.parseInt(split[1]);
    }

    @Override
    public String toString(){
        String info = "";
        info = info + this.nom + "," + this.score;
        return info;
    }

    public void ajoutScore(List<Mot> motsJouees){
        int score = 0;
        for (Mot mot : motsJouees){
            int scoremot = 0;
            int multiM = 1;
            for (Case c : mot.getCasesMot()){
                scoremot = scoremot + c.getLettre().getScore() * c.getMultiplL();
                multiM = multiM * c.getMultiplM();
                c.setMultiplL(1);
                c.setMultiplM(1);
            }
            scoremot = scoremot * multiM;
            score = score + scoremot;
        }
        this.score = this.score + score;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public MainJoueur getMainJ() {
        return mainJ;
    }

    public void setMainJ(MainJoueur mainJ) {
        this.mainJ = mainJ;
    }
}
