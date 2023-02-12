package com.packagenemo.scrabble_plus.jeu.model;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public class Pioche {
    private LinkedList<Lettre> voyelles;
    private LinkedList<Lettre> consonnes;

    /**
     * Constructeur de la pioche
     */
    public Pioche() {
        voyelles = new LinkedList<Lettre>();
        consonnes = new LinkedList<Lettre>();
        loadPioche();
    }


    /**
     * Prend en entrée une liste de lettres et ajoute, au hasard, un nombre déterminé de
     * lettres se trouvant dans la pioche (qui sont ensuite retirées de celle-ci)
     *
     * @param nb      : Nombre de lettres que l'on veut ajouter à la liste de lettres
     * @param lettres : Liste de lettres qui sera modifiée au sein de la méthode
     */
    public void piocher(int nb, List<Lettre> lettres) {
        if (nb < 1) {
            return;
        }
        if (consonnes.isEmpty() && voyelles.isEmpty()) {
            System.out.println("La pioche est vide");
            return;
        }
        int nbVoyelles = voyelles.size();
        int nbConsonnes = consonnes.size();
        int nbCartesPioche = nbConsonnes + nbVoyelles;
        Random rd = new Random();

        // La fonction random permet de pondérer les chances de piocher soit une voyelle, soit une
        // consonne en fonction de leur nombre relatif dans la pioche
        if (rd.nextInt(nbCartesPioche) < nbVoyelles) {
            lettres.add(voyelles.pop()); // Pop pour retirer de la pioche
        } else {
            lettres.add(consonnes.pop());
        }
        piocher(nb - 1, lettres);
    }


    /**
     * Permet d'ajouter à la liste de lettres en entrée un nombre déterminé de
     * consonnes et de voyelles
     *
     * @param nbVoyelles
     * @param nbConsonnes
     * @param lettres
     */
    public void piocher(int nbVoyelles, int nbConsonnes, List<Lettre> lettres) {
        for (int i = 0; i < nbVoyelles; i++) {
            try {
                Lettre carte = voyelles.pop();
                lettres.add(carte);
            } catch (NoSuchElementException e) {
                System.out.println("Plus de lettres dans les voyelles !");
            }
        }
        for (int i = 0; i < nbConsonnes; i++) {
            try {
                Lettre carte = consonnes.pop();
                lettres.add(carte);
            } catch (NoSuchElementException e) {
                System.out.println("Plus de lettres dans les consonnes !");
            }
        }
    }


    /**
     * Permet de charger toutes les lettres qui se trouvent dans la pioche
     * Connexion à la base de donnée
     * On charge tout ça et nous implémentons dans les listes "voyelles" et "consonnes"
     *
     */
    public void loadPioche() {
        //TODO : Connection à la base de donnée pour charger la pioche
    }

    /**
     * @return LinkedList<Lettre> return the voyelles
     */
    public LinkedList<Lettre> getVoyelles() {
        return voyelles;
    }

    /**
     * @param voyelles the voyelles to set
     */
    public void setVoyelles(LinkedList<Lettre> voyelles) {
        this.voyelles = voyelles;
    }

    /**
     * @return LinkedList<Lettre> return the consonnes
     */
    public LinkedList<Lettre> getConsonnes() {
        return consonnes;
    }

    /**
     * @param consonnes the consonnes to set
     */
    public void setConsonnes(LinkedList<Lettre> consonnes) {
        this.consonnes = consonnes;
    }

    /**
     * permet de savoir si la pioche est vide ou non
     *
     * @return boolean true : la pioche est vide, false : non
     */
    public boolean isEmpty() {
        return (this.getConsonnes().isEmpty() && this.getVoyelles().isEmpty());
    }
}
