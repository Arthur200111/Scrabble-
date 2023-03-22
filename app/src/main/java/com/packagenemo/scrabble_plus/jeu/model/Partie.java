package com.packagenemo.scrabble_plus.jeu.model;

import android.util.Log;

import com.packagenemo.scrabble_plus.jeu.callback.StringInterface;
import com.packagenemo.scrabble_plus.jeu.manager.PartieManager;

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

    // Code attribué à la partie qui permet aux joueurs de joindre le lobby
    private final String code;

    private static PartieManager partieManager = PartieManager.getInstance();


    /**
     * Initialise la partie pour chaque joueur
     * @param idPartieBDD : ID de la partie pour la BDD
     * @param loginJoueurCourant : login du joueur utilisant l'interface
     */
    public Partie(String idPartieBDD, String loginJoueurCourant, String code) {
        // TODO : Initialiser la partie avec les informations contenues sur la BDD
        // L'initialisation de la partie devra pouvoir se faire à tout moment du jeu (jeu en cours)

        //TODO Récupérer les Strings du plateau, joueur (main, score) et pioche dans la base de données
        // ensuite je peux me débrouiller pour les traiter
        listJoueur = new LinkedList<Joueur>();
        listJoueur.add(new Joueur());
        plateau = new Plateau();
        pioche = new Pioche();
        focused_lettre = null;
        gestionM = new GestionMots();
        defausse = false;
        joueurActuel = 0;
        pioche.piocher(7,listJoueur.get(joueurActuel).getMainJ().getCartes());
        listJoueur.get(joueurActuel).getMainJ().setRepMain();
        alternateur = true;
        this.code = code;

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

    /**
     * Fonction faisant tourner le Thread du modèle afin de vérifier les actions à effectuer et
     * de mettre à jour
     */
    @Override
    public void run() {
        while (true) {
            changementAction(position, typeAction);
            //updatePartie();
            sleep();
        }
    }

    /**
     * Cette fonction vérifie si l'utilisateur a effectué une action, si c'est le cas on va chercher
     * à savoir quelle action a-t-il fait afin de mettre à jour le modèle
     *
     * @param position information sur la position de l'appui sur le plateau (x,y) ou dans la main
     *                 (dans ce cas seul la première composante nous intéresse)
     * @param typeAction distinction entre le "drag" et le "drop" pour pouvoir traiter l'action
     */
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

    /**
     * Fonction présent dans la boucle du run afin de faire fonctionner le Thread
     */
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
     *  Il faut récupérer le plateau de la partie (idpartie)
     *  Main, score et pioche du joueur (idpartie + currentuseruid)
     *
     *  Utiliser les méthode set dans le callback qu'il faut créer
     */
    public void updatePartie(String idPartie){
        //TODO Récupérer les Strings du plateau, joueur (main, score) et pioche dans la base de données
        // ensuite je peux me débrouiller pour les traiter
        // Set le plateau en fonction de la DTB
        //this.setPlateau(plateauStr);

        // On appelle cette méthode
        partieManager.getPlateauFromPartie(idPartie, new StringInterface() {
            @Override
            public void onCallback(String str) {
                updatePartie(str);
            }
        });


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
            boolean cLibre = true;
            if (focused_lettre != null){
                focused_lettre.setFocused(false);
                cLibre = plateau.caseLibre(position1, getCurrentJoueur().getMainJ(), focused_lettre, pioche);
            }
            if (plateau.getCaseFocused() != null && cLibre) {
                plateau.getCaseFocused().setLettre(null);
                plateau.setCaseFocused(null);
            }
        }
    }

    /**
     * Gère le clic sur la main du joueur courant
     * @param position : Position de la main où le joueur a appuyé (seul la première composante nous intéresse)
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
            Log.d("MAIN", focused_lettre.getLettre());
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

    /**
     * Méthode appelée par l'interface graphique pour obtenir les informations
     * d'affichage du plateau
     * @return une chaîne de caractère pouvant être lu par l'UI
     */
    public String getStringPlateau(){
        return plateau.getRepPlateau();
    }

    /**
     * Méthode appelée par l'interface graphique pour obtenir les informations
     * d'affichage de la main du joueur courant
     * @return une chaîne de caractère pouvant être lu par l'UI
     */
    public String getStringMainJoueur(){
        return getCurrentJoueur().getMainJ().getRepMain();
    }

    /**
     * Renvoie les points du joueur actuel
     * @return son score
     */
    public int getPointsDuJoueur(){
        return getCurrentJoueur().getScore();
    }

    /**
     * Méthode appelée par l'interface graphique pour obtenir les messages adressés au joueur
     * @return une chaîne de caractère si besoin
     */
    public String getStringMessageAuJoueur(){
        // TODO

        return null;
    }

    /**
     * Méthode appelée par l'interface graphique pour savoir quel
     * est le joueur actuel
     * @return le joueur actuel
     */
    public Joueur getCurrentJoueur(){
        return listJoueur.get(joueurActuel);
    }

    public String getCode(){
        return this.code;
    }

    public void setPlateau(String str) { this.plateau.loadPlateau(str);}
}
