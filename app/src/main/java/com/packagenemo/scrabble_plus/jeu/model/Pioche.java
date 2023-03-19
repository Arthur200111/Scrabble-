package com.packagenemo.scrabble_plus.jeu.model;

import android.content.res.Resources;

import com.packagenemo.scrabble_plus.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
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
        loadPioche(R.raw.fr);
    }

    /**
     * Permet de charger toutes les lettres qui se trouvent dans la pioche
     * Le fichier utilisé répertorie toutes les lettres, le nombre occurrence de
     * chacune de ces lettres et leur score associé.
     * On charge tout ça et nous implémentons dans les listes "voyelles" et "consonnes"
     *
     * @param path
     */
    public void loadPioche(int path) {
        try {
            String val_pioche = "100 6 20;"
            +"A 9 1;"
            +"E 15 1;"
            +"I 8 1;"
            +"O 6 1;"
            +"U 6 1;"
            +"Y 1 10;"
            +"B 2 3;"
            +"C 2 3;"
            +"D 3 2;"
            +"F 2 4;"
            +"G 2 2;"
            +"H 2 4;"
            +"J 1 8;"
            +"K 1 10;"
            +"L 5 1;"
            +"M 3 2;"
            +"N 6 1;"
            +"P 2 3;"
            +"Q 1 8;"
            +"R 6 1;"
            +"S 6 1;"
            +"T 6 1;"
            +"V 2 4;"
            +"W 1 10;"
            +"X 1 10;"
            +"Z 1 10;"
            +"_ 2 0;";

            // On commence par charger le fichier qui décrit le contenu de la pioche
            /*InputStream is = Resources.getSystem().openRawResource(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));*/

            String lines[] = val_pioche.split(";");
            String firstLine = lines[0];
            String values[] = firstLine.split(" ");
            int nbVoyelles = Integer.parseInt(values[1]);
            int nbConsonnes = Integer.parseInt(values[2]);
            int nbLettresDiff = nbVoyelles + nbConsonnes + 1;
            int k = 1;

            // Ensuite, on charge toutes les lettres
            for (int i = 0; i < nbLettresDiff; i++) {
                String line = lines[k];
                k++;
                String piece[] = line.split(" ");
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
            //br.close();

            // On mélange pour créer une pioche équitable
            Collections.shuffle(voyelles);
            Collections.shuffle(consonnes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
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
