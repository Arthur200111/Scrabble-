/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.packagenemo.scrabble_plus.jeu.model;

/**
 * Classe représentant un point dans un plan à 2 dimensions
 * 
 * @author Tristan & Arthur
 */
public class Position {

    private int posx;
    private int posy;

    /**
     * Constructeur par défaut, on considère le point comme étant à l'origine
     * 
     */
    public Position() {
        this(0, 0);
    }

    /**
     * Constructeur permettant une copie d'une position
     *
     * @param p
     */
    public Position(Position p) {
        this(p.getX(), p.getY());
    }

    /**
     * Constructeur du point d'abscisse x et d'ordonnée y
     *
     * @param x abscisse
     * @param y ordonnée
     */
    public Position(int x, int y) {
        posx = x;
        posy = y;
    }

    
    /** 
     * Renvoie l'abscisse du point
     * 
     * @return int
     */
    public int getX() {
        return posx;
    }

    
    /** 
     * Renvoie l'ordonnée du point
     * 
     * @return int
     */
    public int getY() {
        return posy;
    }

    
    /** 
     * Modifie l'abscisse du point
     * 
     * @param posx
     */
    public void setPosx(int posx) {
        this.posx = posx;
    }

    
    /** 
     * Modifie l'ordonnée du point
     * 
     * @param posy
     */
    public void setPosy(int posy) {
        this.posy = posy;
    }

    /**
     * Affiche dans la console : [x,y]
     *
     */
    public void affiche() {
        System.out.println("[" + posx + "," + posy + "]");
    }

    
    /** 
     * Renvoie la chaine de caractère [x,y]
     * 
     * @return String
     */
    public String toString() {
        return "[" + posx + "," + posy + "]";
    }

}
