package com.packagenemo.scrabble_plus.jeu.model;

import java.util.ArrayList;
import java.util.List;

public class Plateau {

    private int largeur;
    private int longueur;
    private ArrayList<ArrayList<Case>> listCase;

    public Plateau(){
        this(0,0);
    }

    /**
     * Constructeur du plateau selon une largeur et une longueur que l'on peut fixer
     * Ce constructeur appelle la fonction loadPlateau
     *
     * @param lar
     * @param lon
     */
    public Plateau(int lar, int lon) {
        largeur = lar;
        longueur = lon;
        buildPlateau();
    }

    public void buildPlateau(){
        listCase = new ArrayList<>();
        ArrayList<Case> ligne;
        Case newCase;

        for (int j = 0; j < longueur; j++) {
            ligne = new ArrayList<>();
            for (int i = 0; i < largeur; i++) {
                newCase = new Case();
                ligne.add(newCase);
            }
            listCase.add(ligne);
        }
    }

    public void stringToPlateau(String info){
        String[] split = info.split(";");
        largeur = Integer.parseInt(split[0]);
        longueur = Integer.parseInt(split[1]);
        buildPlateau();

        int posString = 2;
        for (int i = 0; i<largeur; i++) {
            for (int j = 0; j < longueur; j++) {
                listCase.get(i).get(j).stringToCase(split[posString]);
                posString++;
            }
        }
    }

    @Override
    public String toString(){
        String info = this.largeur + ";" + this.longueur + ";";
        for (int i = 0; i<largeur; i++) {
            for (int j = 0; j < longueur; j++) {
                info = info + listCase.get(i).get(j) + ";";
            }
        }
        return info;
    }
}
