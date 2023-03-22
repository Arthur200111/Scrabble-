package com.packagenemo.scrabble_plus.jeu.model;

import java.util.List;
import java.util.ArrayList;

public class MainJoueur {

    private List<Lettre> cartes;
    private String repMain;

    /**
     * Constructeur de la main du joueur
     */
    public MainJoueur() {
        cartes = new ArrayList<Lettre>();
//        String val_Main = "7;2,A,1,0;2,I,1,0;2,G,2,0;2,A,1,0;2,P,3,0;2,A,1,0;2,G,2,0;";
//        this.stringToMain(val_Main);
//        this.setRepMain();
    }

    public MainJoueur(String mainFromDTB) {
        cartes = new ArrayList<Lettre>();
        //DONE Récupérer le String de la main dans la base de données
        this.stringToMain(mainFromDTB);
        this.setRepMain();
    }

    /**
     * Renvoie les éléments de la main du joueur
     * @return
     */
    public List<Lettre> getCartes() {
        return cartes;
    }

    /**
     * Modifie les éléments de la main du joueur
     */
    public void setCartes(List<Lettre> cartes) {
        this.cartes = cartes;
    }

    /**
     * Fonction transformant une chaîne de caractère en Main de joueur (selon des conventions), utiliser
     * en lisant la base de données pour initialiser/mettre à jour la main
     * @param info
     */
    public void stringToMain(String info){
        String[] split = info.split(";");
        for (int i=1; i<=Integer.parseInt(split[0]); i++){
            Lettre lettreMain = new Lettre();
            lettreMain.stringToLettre(split[i]);
            if (lettreMain.getLettre() != null){
                this.cartes.add(lettreMain);
            }
        }
    }

    /**
     * Fonction transformant la main du joueur en chaîne de caractère pouvant être lu par l'UI
     * @return la chaîne de caractère
     */
    @Override
    public String toString(){
        String info = 7 + ";";
        for (Lettre lettre : this.cartes){
            info = info + lettre + ";";
        }
        for (int i=0; i<(7-this.cartes.size()); i++){
            info = info + "1,0,0,0;";
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
