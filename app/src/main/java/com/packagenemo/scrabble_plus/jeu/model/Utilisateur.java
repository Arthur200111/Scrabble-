package com.packagenemo.scrabble_plus.jeu.model;

import java.util.ArrayList;
import java.util.List;

public class Utilisateur {
    private String uid;
    private String nom;

    private List<Joueur> joueursList;

    public Utilisateur(String uid, String nom){
        this.uid = uid;
        this.nom = nom;

        this.joueursList = new ArrayList<>();
    }
    public Utilisateur(String uid, String nom, List<Joueur> j) {
        this.uid = uid;
        this.nom = nom;

        this.joueursList = j;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Joueur> getJoueursList(){ return this.joueursList; }
    public void setJoueursList(List<Joueur> j){ this.joueursList = j; }

    public void addJoueur(Joueur j){ this.joueursList.add(j);}
}