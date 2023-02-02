package com.packagenemo.scrabble_plus.jeu.model;

public class Lettre {

    private String lettre;
    private int score;
    private int isSelected;

    public Lettre() {
        lettre = null;
        score = 0;
        this.isSelected = 0;
    }

    public Lettre(String lettre, int score){
        this.lettre = lettre;
        this.score = score;
        this.isSelected = 0;
    }

    public boolean equals(Lettre l) {
        return (l.getLettre() == this.lettre);
    }

    public void stringToLettre(String info){
        String[] split = info.split(",");
        this.lettre = split[0];
        this.score = Integer.parseInt(split[1]);
    }

    @Override
    public String toString(){
        String info =  2 + "," + this.lettre + "," + this.score + "," + this.isSelected;
        return info;
    }

    public String getLettre() {
        return lettre;
    }

    public void setLettre(String lettre) {
        this.lettre = lettre;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }
}
