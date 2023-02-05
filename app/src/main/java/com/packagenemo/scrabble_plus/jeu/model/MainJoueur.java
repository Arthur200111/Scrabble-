package com.packagenemo.scrabble_plus.jeu.model;

import java.util.List;
import java.util.ArrayList;

public class MainJoueur {

    private List<Lettre> cartes;

    public MainJoueur() {
        cartes = new ArrayList<Lettre>();
    }

    public List<Lettre> getCartes() {
        return cartes;
    }

    public void setCartes(List<Lettre> cartes) {
        this.cartes = cartes;
    }

    public void stringToMain(String info){
        info = info.substring(2);
        String[] split = info.split(";");
        for (String infoLettre : split){
            Lettre lettreMain = new Lettre();
            lettreMain.stringToLettre(infoLettre);
            this.cartes.add(lettreMain);
        }
    }

    @Override
    public String toString(){
        String info = this.cartes.size() + ";";
        for (Lettre lettre : this.cartes){
            info = info + lettre + ";";
        }
        return info;
    }
}
