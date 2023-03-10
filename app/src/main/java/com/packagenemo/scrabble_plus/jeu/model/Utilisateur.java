package com.packagenemo.scrabble_plus.jeu.model;

import java.util.ArrayList;
import java.util.List;

public class Utilisateur {
    private String uid;
    private String nom;

    private List<Partie> PartiesJouees;
    private List<Partie> PartiesEnCours;

    public Utilisateur(String uid, String nom){
        this.uid = uid;
        this.nom = nom;
        PartiesJouees = new ArrayList<>();
        PartiesEnCours = new ArrayList<>();
    }
    public Utilisateur(String uid, String nom, List<Partie> partiesJouees, List<Partie> partiesEnCours) {
        this.uid = uid;
        this.nom = nom;
        PartiesJouees = partiesJouees;
        PartiesEnCours = partiesEnCours;
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

    public List<Partie> getPartiesJouees() {
        return PartiesJouees;
    }

    public void setPartiesJouees(List<Partie> partiesJouees) {
        PartiesJouees = partiesJouees;
    }

    public List<Partie> getPartiesEnCours() {
        return PartiesEnCours;
    }

    public void setPartiesEnCours(List<Partie> partiesEnCours) {
        PartiesEnCours = partiesEnCours;
    }
}