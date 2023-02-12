package com.packagenemo.scrabble_plus.jeu.model;

import java.util.ArrayList;
import java.util.List;

public class Plateau {

    private int largeur;
    private int longueur;
    private ArrayList<ArrayList<Case>> listCase;

    private List<Case> lettresJouees;

    private Case caseFocused;

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

    public Case getCaseFocused() {
        return caseFocused;
    }

    public void setCaseFocused(Case c) {
        this.caseFocused = c;
    }

    /**
     * Fonction vérifiant que la case sur laquelle l'utilisateur a cliqué est libre,
     * si la case l'est la fonction renvoie la valeur null
     * autrement la fonction renvoie la lettre que l'utilisateur voulait placer
     *
     * @param p position
     * @param mainJ
     * @param focused_letter
     * @param pioche
     * @return Lettre
     */
    public Lettre caseLibre(Position p, MainJoueur mainJ, Lettre focused_letter, Pioche pioche) {
        Case c = listCase.get(p.getY()).get(p.getX());
        if (c.getLettre() == null) {
            c.setLettre(focused_letter);
            mainJ.supprLettre(focused_letter);
            lettresJouees.add(c);
            return null;
        }
        return focused_letter;
    }


    /**
     * Fonction vérifiant si la case sur laquelle l'utilisateur a cliqué est occupée,
     * si la case l'est la fonction renvoie la lettre contenu sur cette case (si cela respecte les règles du Scrabble)
     * autrement la fonction renvoie la valeur null
     *
     * @param p position
     * @param mainJ
     * @return Lettre
     */
    public Lettre caseOccupee(Position p, MainJoueur mainJ){
        Case c = listCase.get(p.getY()).get(p.getX());
        Lettre l = c.getLettre();
        if (getLettresJouees().contains(c) && l!=null) {
            l.setFocused(true);
            lettresJouees.remove(c);
            setCaseFocused(c);
            return l;
        }
        return null;
    }

    /**
     * Renvoie la liste des cases sur lesquelles une lettre a été jouée ce tour ci
     *
     * @return List<Case>
     */
    public List<Case> getLettresJouees() {
        return this.lettresJouees;
    }


    /**
     * Modifie la liste des cases sur lesquelles une lettre a été jouée ce tour ci
     *
     * @param lettresJouees
     */
    public void setLettresJouees(List<Case> lettresJouees) {
        this.lettresJouees = lettresJouees;
    }


    /**
     * Renvoie la case d'indice (i,j), si elle n'existe pas on renvoie une case null
     *
     * @param i
     * @param j
     * @return Case
     */
    public Case getCase(int i, int j) {
        try{
            return listCase.get(j).get(i);
        } catch (Exception e){
            Case c = new Case();
            return c;
        }
    }

    /**
     * Renvoie la largeur du plateau
     *
     * @return int
     */
    public int getLargeur() {
        return largeur;
    }


    /**
     * Renvoie la longueur du plateau
     *
     * @return int
     */
    public int getLongueur() {
        return longueur;
    }
}
