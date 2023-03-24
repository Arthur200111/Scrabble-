package com.packagenemo.scrabble_plus.jeu.model;

import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.google.rpc.Help;
import com.packagenemo.scrabble_plus.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;

public class Pioche {
    private LinkedList<Lettre> voyelles;
    private LinkedList<Lettre> consonnes;

    List<Lettre> contenuPioche;

    /**
     * Constructeur de la pioche
     */
    public Pioche() {
        voyelles = new LinkedList<Lettre>();
        consonnes = new LinkedList<Lettre>();
        loadPioche();
    }

    /**
     * Constructeur de la pioche avec les données de la dtb
     */
    public Pioche(LinkedList<Lettre> voyelles, LinkedList<Lettre> consonnes) {
        this.voyelles = voyelles;
        this.consonnes = consonnes;
        loadPioche();
    }

    /**
     * Permet de charger toutes les lettres qui se trouvent dans la pioche
     * Connexion à la base de donnée
     * On charge tout ça et nous implémentons dans les listes "voyelles" et "consonnes"
     *
     */
    public void loadPioche() {
        //TODO Récupérer le String de la pioche dans la base de données, tant pis pour l'instant si le format
        // est pas tout à fait correct
        String val_pioche = "102,6,20;"
        +"A,9,1;"
        +"E,15,1;"
        +"I,8,1;"
        +"O,6,1;"
        +"U,6,1;"
        +"Y,1,10;"
        +"B,2,3;"
        +"C,2,3;"
        +"D,3,2;"
        +"F,2,4;"
        +"G,2,2;"
        +"H,2,4;"
        +"J,1,8;"
        +"K,1,10;"
        +"L,5,1;"
        +"M,3,2;"
        +"N,6,1;"
        +"P,2,3;"
        +"Q,1,8;"
        +"R,6,1;"
        +"S,6,1;"
        +"T,6,1;"
        +"V,2,4;"
        +"W,1,10;"
        +"X,1,10;"
        +"Z,1,10;"
        +"_,2,0;";


        String lines[] = val_pioche.split(";");
        String firstLine = lines[0];
        String values[] = firstLine.split(",");
        int nbVoyelles = Integer.parseInt(values[1]);
        int nbConsonnes = Integer.parseInt(values[2]);
        int nbLettresDiff = nbVoyelles + nbConsonnes + 1;
        int k = 1;

        // Ensuite, on charge toutes les lettres
        for (int i = 0; i < nbLettresDiff; i++) {
            String line = lines[k];
            k++;
            String piece[] = line.split(",");
            String lettre = piece[0];
            int occurence = Integer.parseInt(piece[1]);
            int score = Integer.parseInt(piece[2]);
            for (int j = 0; j < occurence; j++) {
                Lettre l = new Lettre(lettre, score);
                if (i < nbVoyelles) {
                    this.voyelles.add(l);
                } else {
                    this.consonnes.add(l);
                }
            }
        }
        // On mélange pour créer une pioche équitable
        Collections.shuffle(voyelles);
        Collections.shuffle(consonnes);
    }

    /**
     * Fonction transformant la pioche en chaine de caractère afin
     * de pouvoir la sauvegarder dans la base de données
     *
     * @return
     */
    @Override
    @NonNull
    public String toString(){
        int taille = voyelles.size()+consonnes.size();
        String piocheString = "" + taille + ",6,20;";
        Map<String, Integer> piocheOccu = new HashMap<String, Integer>();
        Map<String, Integer> piocheScore = new HashMap<String, Integer>();
        for (Lettre l : voyelles){
            if (piocheOccu.get(l.getLettre()) == null){
                piocheOccu.put(l.getLettre(), 1);
                piocheScore.put(l.getLettre(), l.getScore());
            }
            else {
                piocheOccu.put(l.getLettre(), piocheOccu.get(l.getLettre())+1);
            }
        }
        for (Lettre l : consonnes){
            if (piocheOccu.get(l.getLettre()) == null){
                piocheOccu.put(l.getLettre(), 1);
                piocheScore.put(l.getLettre(), l.getScore());
            }
            else {
                piocheOccu.put(l.getLettre(), piocheOccu.get(l.getLettre())+1);
            }
        }
        for (String l : piocheOccu.keySet()){
            piocheString = piocheString + l + "," + piocheOccu.get(l) + "," + piocheScore.get(l) + ";";
        }
        return piocheString;
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


    // TODO THOMAS : Partie
    private List<Lettre> getPiocheBDD(){

        // Array de List<Lettre>



        return new ArrayList<>();
    }

    private void setPiocheBDD(){
        // Array de List<Lettre>
        //
        // Cette liste --> contenuPioche
    }
}
