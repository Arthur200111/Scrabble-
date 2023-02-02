package com.packagenemo.scrabble_plus.jeu.model;

public class Case {

    private  Position pos;
    private int multiplL;
    private int multiplM;
    private Lettre lettre;
    private int typeCase;
    private int isSelected;

    public Case() {
        pos = new Position(0,0);
        multiplL = 1;
        multiplM = 1;
        lettre = null;
        typeCase = 0;
        isSelected = 0;
    }

    public void stringToCase(String info){
        String[] split = info.split(",");
        if (Integer.parseInt(split[0])==0){
            this.setTypeCase(Integer.parseInt(split[1]));
        }
        else if (Integer.parseInt(split[0])==1){
            Lettre l = new Lettre(split[1],Integer.parseInt(split[2]));
            this.setLettre(l);
        }
        else{
            // Peut-être thrown Exception
        }
    }

    @Override
    public String toString(){
        String info = "";
        if (this.lettre==null){
            info =  0 + "," + this.typeCase + "," + 0 + "," + this.isSelected;
        }
        else {
            info = 2 + "," + this.lettre + "," + this.lettre.getScore() + "," + this.isSelected;
        }
        return  info;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public Lettre getLettre() {
        return lettre;
    }

    public void setLettre(Lettre lettre) {
        this.lettre = lettre;
    }

    public int getTypeCase() {
        return typeCase;
    }

    public void setTypeCase(int typeCase) {
        this.typeCase = typeCase;
    }

    public int getMultiplL() {
        return multiplL;
    }

    public void setMultiplL(int multiplL) {
        this.multiplL = multiplL;
    }

    public int getMultiplM() {
        return multiplM;
    }

    public void setMultiplM(int multiplM) {
        this.multiplM = multiplM;
    }
}
