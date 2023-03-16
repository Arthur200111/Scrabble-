package com.packagenemo.scrabble_plus.jeu.model;

import java.util.List;
import java.util.ArrayList;

public class MainJoueur {

    private List<Lettre> cartes;
    private String repMain;

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

    /**
     * permet de supprimer la lettre dans la main du joueur
     *
     * @param focused_letter lettre à supprimer
     */
    public void supprLettre(Lettre focused_letter) {
        focused_letter.setFocused(false);
        cartes.remove(focused_letter);
    }

    /**
     * permet de focus une lettre
     *
     * @param i position de la lettre à focus
     * @return Lettre lettre focus
     */
    public Lettre newFocus(int i) {
        if (cartes.size() > i) {
            cartes.get(i).setFocused(true);
            return cartes.get(i);
        }
        return null;
    }

    /**
     * permet de compléter la main avec les cartes de la pioche
     *
     * @param pioche pioche
     */
    public void complete(Pioche pioche) {
        int l = cartes.size();
        if (7 - l > 0) {
            pioche.piocher(7 - l, cartes);
        }
    }

    /**
     * permet de récupérer les lettres qui ont été jouées pendant le tour si les
     * lettres qui ont été posées ne respectent pas les règles du Scrabble
     *
     * @param lettresJouees
     */
    public void recup(List<Case> lettresJouees) {
        for (Case c : lettresJouees) {
            cartes.add(c.getLettre());
            c.setLettre(null);
        }
    }

    public String getRepMain() {
        return repMain;
    }

    public void setRepMain() {
        this.repMain = this.toString();
    }

}
