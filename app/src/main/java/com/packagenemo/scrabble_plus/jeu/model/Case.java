package com.packagenemo.scrabble_plus.jeu.model;

import android.util.Log;

/**
 * Classe qui représente
 */
public class Case {


    private  Position pos;
    private int multiplL;
    private int multiplM;
    private Lettre lettre;
    private int typeCase;
    private int isSelected;

    /**
     * Constructeur d'une case du plateau
     */
    public Case() {
        pos = new Position(0,0);
        multiplL = 1;
        multiplM = 1;
        lettre = null;
        typeCase = 0;
        isSelected = 0;
    }

    /**
     * Fonction transformant une chaîne de caractère en case selon des conventions
     * @param info
     */
    public void stringToCase(String info){
        String[] split = info.split(",");
        if (Integer.parseInt(split[0])==0){
            this.setTypeCase(Integer.parseInt(split[1]));
            switch (typeCase){
                case 1:
                    multiplL = 2;
                    multiplM = 1;
                    break;
                case 2:
                    multiplL = 3;
                    multiplM = 1;
                    break;
                case 3:
                case 5:
                    multiplL = 1;
                    multiplM = 2;
                    break;
                case 4:
                    multiplL = 1;
                    multiplM = 3;
                    break;
                default:
                    multiplL = 1;
                    multiplM = 1;
                    break;
            }
        }
        else if (Integer.parseInt(split[0])==2){
            Lettre l = new Lettre(split[1],Integer.parseInt(split[2]));
            this.setLettre(l);
        }
        else{
            // Peut-être thrown Exception
        }
    }

    /**
     * Fonction transformant une case en chaîne de caractère selon des conventions
     * @return
     */
    @Override
    public String toString(){
        String info = "";
        if (this.lettre==null){
            info =  0 + "," + this.typeCase + "," + 0 + "," + this.isSelected;
        }
        else {
            info = 2 + "," + this.lettre.getLettre() + "," + this.lettre.getScore() + "," + this.isSelected;
        }
        return  info;
    }

    /**
     * Renvoie la position de la case sur le plateau
     * @return
     */
    public Position getPos() {
        return pos;
    }

    /**
     * Modifie la position de la case sur le plateau
     * @param pos
     */
    public void setPos(Position pos) {
        this.pos = pos;
    }

    /**
     * Renvoie la lettre de la case (null si case vide)
     * @return
     */
    public Lettre getLettre() {
        return lettre;
    }

    /**
     * Modifie la lettre de la case
     * @param lettre
     */
    public void setLettre(Lettre lettre) {
        this.lettre = lettre;
    }

    /**
     * Renvoie le type de la case
     * @return
     */
    public int getTypeCase() {
        return typeCase;
    }

    /**
     * Modifie le type de la case
     * @param typeCase
     */
    public void setTypeCase(int typeCase) {
        this.typeCase = typeCase;
    }

    /**
     * Renvoie le multiplicateur de lettre de la case
     * @return
     */
    public int getMultiplL() {
        return multiplL;
    }

    /**
     * Modifie le multiplicateur de lettre de la case
     * @param multiplL
     */
    public void setMultiplL(int multiplL) {
        this.multiplL = multiplL;
    }

    /**
     * Renvoie le multiplicateur de mot de la case
     * @return
     */
    public int getMultiplM() {
        return multiplM;
    }

    /**
     * Modifie le multiplicateur de mot de la case
     * @param multiplM
     */
    public void setMultiplM(int multiplM) {
        this.multiplM = multiplM;
    }
}
