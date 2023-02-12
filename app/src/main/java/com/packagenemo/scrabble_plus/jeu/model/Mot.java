package com.packagenemo.scrabble_plus.jeu.model;

import java.util.List;

public class Mot {

    private List<Case> casesMot;
    private String mot;

    /**
     * constructeur d'un Mot
     *
     * @param casesMot les cases constituants le mot
     */
    public Mot(List<Case> casesMot) {
        this.casesMot = casesMot;
        init();
    }

    /**
     * Permet d'initialiser un mot
     */
    private void init() {
        mot = "";
        for (Case c : casesMot) {
            mot += c.getLettre().getLettre();
        }
    }

    /**
     * permet d'afficher le mot dans la console
     *
     */
    public void affiche() {
        System.out.println(mot);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        return mot;
    }

    /**
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == (Mot) obj) {
            Mot m = (Mot) obj;
            return m.getCasesMot().equals(casesMot);
        }
        return false;
    }

    /**
     * @return List<Case>
     */
    public List<Case> getCasesMot() {
        return this.casesMot;
    }

    /**
     * @param casesMot
     */
    public void setCasesMot(List<Case> casesMot) {
        this.casesMot = casesMot;
    }

    /**
     * @return String
     */
    public String getMot() {
        return this.mot;
    }

    /**
     * @param mot
     */
    public void setMot(String mot) {
        this.mot = mot;
    }

}
