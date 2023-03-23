package com.packagenemo.scrabble_plus.jeu.model;

import java.util.ArrayList;
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

    /**
     * Constructeur d'un joueur à partir de son score et de son nom
     *
     * @param name nom du joueur
     * @param sco  score du joueur
     * @param mainFromDTB main du joueur récupéré avec firebase
     */
    public Joueur(String name, int sco, String mainFromDTB) {
        nom = name;
        score = sco;
        mainJ = new MainJoueur(mainFromDTB);
    }

    /**
     * Transforme une chaîne de caractère en joueur selon des conventions
     * Remarque : inutiliser dans cette application
     * @param info
     */
    public void stringToJoueur(String info){
        String[] split = info.split(",");
        this.nom = split[0];
        this.score = Integer.parseInt(split[1]);
    }

    /**
     * Renvoei une chaîne de caractère décrivant le joueur
     * @return
     */
    @Override
    public String toString(){
        String info = "";
        info = info + this.nom + "," + this.score;
        return info;
    }

    /**
     * Calcul le score à ajouter au joueur puis l'ajoute à son total
     * @param motsJouees
     */
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

    /**
     * Renvoie le nom du joueur
     * @return
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifie le nom du joueur
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Renovie le score du joueur
     * @return
     */
    public int getScore() {
        return score;
    }

    /**
     * Modifie le score du joueur
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Renvoie la main du joueur
     * @return
     */
    public MainJoueur getMainJ() {
        return mainJ;
    }

    /**
     * Modifie la main du joueur
     * @param mainJ
     */
    public void setMainJ(MainJoueur mainJ) {
        this.mainJ = mainJ;
    }

}
