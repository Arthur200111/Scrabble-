package com.packagenemo.scrabble_plus.jeu.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Classe qui gère toute la partie.
 * Cette classe sera initialisée différemment selon le joueur présent sur l'interface courante
 *
 * Les caractéristiques d'initialisation (nb de joueurs, parametres,.. se feront directement grace à la BDD)
 */
public class Partie implements Runnable{

    private static final int FPS_VOULU = 35;
    private boolean alternateur;

    // Code de la dernière mise à jour, sert à déterminer si le plateau est à jour
    private int codeLastUpdate;

    // Boolean qui indique si la partie sur cette machine a subit des changements depuis le dernier download BDD
    private boolean desChangementsSeSontProduits;

    private List<Joueur> listJoueur;
    private int joueurActuel;
    private Pioche pioche;
    private Plateau plateau;
    private Dictionnaire dictionnaire;
    // Indique la lettre selectionné, vaut null si aucune ne l'est
    private Lettre focused_lettre;
    private GestionMots gestionM;
    private boolean defausse;
    private Thread modelThread;
    private long mTempsDerniereFrame;
    private boolean mActionJoueur; // Indique que le joueur a effectuer une action
    private int[] position; // Donne la position de cette action (si besoin)
    private String typeAction; // Donne le type de cette action : "drag" ou "drop"
    private boolean mChoixPlateau; // Indique que l'action concerne le plateau
    private boolean mChoixMain; // Indique que l'action concerne la main du joueur
    private boolean mChoixDefausse; // Indique que l'action concerne la défausse
    private boolean mChoixFin; // Indique que l'action concerne la fin de tour

    /**
     * Initialise la partie pour chaque joueur
     * @param idPartieBDD : ID de la partie pour la BDD
     * @param loginJoueurCourant : login du joueur utilisant l'interface
     */
    public Partie(String idPartieBDD, String loginJoueurCourant) {
        // TODO : Initialiser la partie avec les informations contenues sur la BDD
        // L'initialisation de la partie devra pouvoir se faire à tout moment du jeu (jeu en cours)
        listJoueur = new LinkedList<Joueur>();
        listJoueur.add(new Joueur());
        plateau = new Plateau();
        pioche = new Pioche();
        dictionnaire = new Dictionnaire();
        focused_lettre = null;
        gestionM = new GestionMots();
        defausse = false;
        joueurActuel = 0;
        alternateur = true;

        mActionJoueur = false;
        position = new int[2];
        typeAction = "drag";
        mChoixPlateau = false;
        mChoixMain = false;
        mChoixDefausse = false;
        mChoixFin = false;

        modelThread = new Thread(this);
        modelThread.start();
    }

    @Override
    public void run() {
        // TODO : transformer la classe partie en runnable pour pouvoir la lacer sur un thread à part
        while (true){
            changementAction(position, typeAction);
            updatePartie();
            sleep();
        }
    }

    private void changementAction(int[] position, String typeAction) {
        if (mActionJoueur){
            if (mChoixPlateau){
                plateau(position, typeAction);
                mChoixPlateau = false;
            }
            if (mChoixMain){
                main(position[0], typeAction);
                mChoixMain = false;
            }
            if (mChoixDefausse){
                defausse();
                mChoixDefausse = false;
            }
            if (mChoixFin){
                finDeTour();
                mChoixFin = false;
                desChangementsSeSontProduits = true;
            }
            mActionJoueur = false;
            plateau.setRepPlateau();
            getCurrentJoueur().getMainJ().setRepMain();
        }
    }

    private void sleep () {
        long intervalle = System.currentTimeMillis() - mTempsDerniereFrame;

        while (intervalle < 1000/FPS_VOULU){
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            intervalle = System.currentTimeMillis() - mTempsDerniereFrame;
        }

        mTempsDerniereFrame = System.currentTimeMillis();
    }

    /**
     * Synchronise le plateau avec la BDD
     *
     */
    public void updatePartie(){

    }

    /**
     * Envoie true si la partie est à jour avec ce qui est présent sur la BDD
     * @return
     */
    public boolean partieAJour(){

        // TODO

        return true;
    }

    /**
     * Gère le clic sur le plateau du joueur courant
     * @param position : Coordonnées du plateau où le joueur a appuyé
     */
    public void giveInputJoueurPlateau(int[] position, String typeAction){
        this.position = position;
        this.typeAction = typeAction;
        mActionJoueur = true;
        mChoixPlateau = true;
    }

    /**
     * Action a effectuer lors d'une action sur le plateau
     */
    public void plateau(int[] position, String typeAction){
        Position position1 = new Position(position[0],position[1]);
        if (typeAction.equals("drag")){
            focused_lettre = plateau.caseOccupee(position1, getCurrentJoueur().getMainJ());
        }
        else if (typeAction.equals("drop") && !defausse){
            if (plateau.getCaseFocused() != null) {
                plateau.getCaseFocused().setLettre(null);
                plateau.setCaseFocused(null);
            }
            focused_lettre.setFocused(false);
            plateau.caseLibre(position1, getCurrentJoueur().getMainJ(), focused_lettre, pioche);
        }
    }

    /**
     * Gère le clic sur la main du joueur courant
     * @param position : Coordonnées du plateau où le joueur a appuyé
     */
    public void giveInputJoueurMain(int position, String typeAction){
        this.position[0] = position;
        this.typeAction = typeAction;
        mActionJoueur = true;
        mChoixMain = true;
    }

    /**
     * Action a effectuer lors d'une action sur la main du joueur
     */
    public void main(int position, String typeAction){
        if (typeAction.equals("drag")){
            focused_lettre = getCurrentJoueur().getMainJ().newFocus(position);
        }
        else if (typeAction.equals("drop")){
            if (plateau.getCaseFocused() != null) {
                getCurrentJoueur().getMainJ().getCartes().add(focused_lettre);
                plateau.getCaseFocused().setLettre(null);
                plateau.setCaseFocused(null);
            }
            focused_lettre.setFocused(false);
        }
    }

    /**
     * Lorsque le joueur appuie sur le bouton défausser
     */
    public void giveInputJoueurDefausser(){
        mActionJoueur = true;
        mChoixDefausse = true;
    }

    /**
     * Action a effectuer lors d'une défausse
     */
    public void defausse(){
        if (focused_lettre != null) {
            if (plateau.getCaseFocused() != null) {
                plateau.getCaseFocused().setLettre(null);
                plateau.setCaseFocused(null);
            } else {
                getCurrentJoueur().getMainJ().supprLettre(focused_lettre);
            }
            focused_lettre = null;
            defausse = true;
        }
    }

    /**
     * Lorsque le joueur appuie sur le bouton "fin de tour"
     */
    public void giveInputJoueurFinTour(){
        mActionJoueur = true;
        mChoixFin = true;
    }

    /**
     * Action a effectuer lors d'une fin de tour
     */
    public void finDeTour(){
        if (defausse) {
            for (Case c : plateau.getLettresJouees()) {
                getCurrentJoueur().getMainJ().getCartes().add(c.getLettre());
                c.setLettre(null);
            }
            getCurrentJoueur().getMainJ().complete(pioche);
            defausse = false;
        } else {
            List<Mot> nouveauxMots = gestionM.ajoutMot(plateau.getLettresJouees(), plateau);
            if (nouveauxMots != null) {
                getCurrentJoueur().getMainJ().complete(pioche);
                getCurrentJoueur().ajoutScore(nouveauxMots);
            } else {
                getCurrentJoueur().getMainJ().recup(plateau.getLettresJouees());
            }
        }
        plateau.setLettresJouees(new ArrayList<>());
    }

    public String getStringPlateau(){
        // TODO
        String string1 = plateau.getRepPlateau();

        String string2 = "15;15;0,0,10,0;0,4,6,0;0,2,2,0;1,1,8,0;0,2,5,0;0,3,6,0;1,1,9,0;" +
                "2,D,9,0;2,D,5,0;1,4,1,0;0,2,10,0;1,1,5,0;2,E,4,0;0,0,7,0;0,4,7,0;1,1,8,0;2,E,2,0;" +
                "2,F,10,0;1,5,1,0;0,1,4,0;1,2,2,0;1,1,1,0;2,A,9,0;1,3,3,0;0,0,6,0;2,D,10,0;0,3,8,0;" +
                "0,3,3,0;1,3,6,0;1,4,8,0;1,1,7,0;0,0,6,0;0,4,2,0;0,0,4,0;2,D,3,0;0,3,5,0;1,0,3,0;" +
                "1,2,4,0;0,5,2,0;2,B,9,0;1,2,5,0;1,4,1,0;2,B,3,0;1,1,5,0;0,3,5,0;2,B,10,0;2,D,8,0;" +
                "2,B,9,0;2,C,9,0;2,A,7,0;2,A,6,0;1,0,9,0;2,E,2,0;2,E,2,0;2,F,6,0;1,1,8,0;1,5,3,0;" +
                "2,E,7,0;1,4,4,0;1,1,4,0;1,1,3,0;2,F,10,0;1,1,6,0;2,A,6,0;2,B,8,0;1,4,4,0;2,D,5,0;" +
                "2,A,3,0;2,B,9,0;0,1,6,0;1,3,3,0;2,C,6,0;0,1,1,0;2,F,1,0;2,A,1,0;2,F,4,0;2,B,6,0;" +
                "0,4,4,0;0,3,7,0;1,4,4,0;2,F,5,0;1,0,4,0;1,4,9,0;2,A,2,0;2,C,9,0;1,3,7,0;0,4,6,0;" +
                "2,C,9,0;2,D,10,0;2,D,9,0;1,5,1,0;0,0,3,0;1,4,10,0;1,4,2,0;0,3,9,0;1,1,3,0;1,1,10,0;" +
                "0,1,3,0;0,1,9,0;0,0,5,0;2,B,7,0;2,C,3,0;2,C,9,0;0,2,6,0;" +
                "2,C,8,0;2,B,1,0;1,4,10,0;0,4,1,0;2,A,6,0;1,4,8,0;0,4,7,0;2,E,8,0;" +
                "2,A,1,0;0,1,1,0;1,0,5,0;0,4,6,0;0,5,1,0;1,1,3,0;2,A,4,0;0,5,1,0;0,3,3,0;1,2,10,0;" +
                "1,2,7,0;2,A,4,0;1,5,6,0;1,4,3,0;1,5,8,0;1,2,5,0;2,B,8,0;1,1,2,0;1,1,7,0;1,3,1,0;" +
                "1,3,4,0;2,D,7,0;2,C,1,0;2,B,5,0;0,4,7,0;2,C,5,0;2,E,8,0;2,D,4,0;2,E,7,0;2,E,4,0;" +
                "0,5,9,0;2,B,10,0;1,0,1,0;1,5,10,0;2,A,1,0;0,2,2,0;0,4,8,0;2,C,1,0;2,D,4,0;0,4,2,0;" +
                "1,2,10,0;2,B,6,0;2,E,9,0;1,2,1,0;2,C,4,0;2,C,7,0;2,C,3,0;2,F,2,0;0,0,3,0;1,0,5,0;" +
                "0,1,6,0;1,5,10,0;2,B,8,0;2,B,7,0;1,4,10,0;1,5,7,0;1,0,2,0;1,2,6,0;2,D,4,0;0,5,6,0;" +
                "1,0,5,0;2,C,2,0;0,4,4,0;2,C,4,0;2,D,9,0;1,4,10,0;2,C,9,0;1,2,10,0;1,5,5,0;2,F,2,0;" +
                "1,1,9,0;0,5,6,0;1,0,10,0;1,1,7,0;2,B,5,0;1,2,7,0;2,B,10,0;2,A,7,0;1,4,3,0;0,4,4,0;" +
                "0,3,2,0;2,D,10,0;0,0,6,0;1,5,6,0;2,D,6,0;2,C,5,0;2,B,4,0;2,B,8,0;1,4,8,0;1,1,8,0;" +
                "0,4,3,0;2,B,9,0;0,3,3,0;1,1,1,0;0,1,3,0;2,F,1,0;0,2,1,0;0,2,5,0;2,D,4,0;2,C,4,0;" +
                "2,D,6,0;2,B,4,0;1,5,2,0;2,B,5,0;1,3,6,0;1,4,3,0;1,1,3,0;2,F,9,0;0,0,8,0;2,F,9,0;" +
                "0,2,8,0;1,2,9,0;0,0,1,0;";

        if (alternateur){
            alternateur = !alternateur;
            return string1;
        } else {
            alternateur = !alternateur;
            return string1;
        }
    }

    /**
     * Méthode appelée par l'interface graphique pour obtenir les informations
     * d'affichage de la main du joueur courant
     * @return
     */
    public String getStringMainJoueur(){
        //return getCurrentJoueur().getMainJ().getRepMain();
        return "7;1,1,3,0;2,F,9,0;0,0,8,0;2,F,9,0;0,2,8,0;1,2,9,0;0,0,1,0;";
    }

    public int getPointsDuJoueur(){
        return getCurrentJoueur().getScore();
    }

    /**
     * Méthode appelée par l'interface graphique pour obtenir les messages adressés au joueur
     * @return
     */
    public String getStringMessageAuJoueur(){
        // TODO

        return null;
    }

    /**
     * Méthode appelée par l'interface graphique pour savoir quel
     * est le joueur actuel
     * @return
     */
    public Joueur getCurrentJoueur(){
        return listJoueur.get(joueurActuel);
    }

}
