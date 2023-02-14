package com.packagenemo.scrabble_plus.jeu.model;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;

public class Dictionnaire {
    private ArrayList<String> listMot;
    private int longueur_dico;

    /**
     *
     */
    public Dictionnaire() {
        listMot = new ArrayList<>();
        this.loadDico();

    }


    /**
     * @return List<String>
     */
    public List<String> getListMot() {
        return this.listMot;
    }


    /**
     * @param listMot
     */
    public void setListMot(ArrayList<String> listMot) {
        this.listMot = listMot;
    }


    /**
     *
     */
    public void loadDico(){
        //TODO Chargement du dictionnaire
    }


    /**
     * @param mot
     * @return boolean
     */
    public boolean verifMot(String mot){

        int i = longueur_dico;
        int j = 0;
        int k;
        String compareMot;

        while (abs(i-j)>1){
            k = (i+j)/2;
            compareMot = listMot.get(k);
            if(compareMot.compareTo(mot)==0) {
                return true;
            }
            else if (compareMot.compareTo(mot)>0){
                j = min(i,j);
            }
            else if (compareMot.compareTo(mot)<0){
                j = max(i,j);
            }
            else{
                j = min(i,j);
            }

            i = k;
        }
        return listMot.get(j).equals(mot);
    }
}
