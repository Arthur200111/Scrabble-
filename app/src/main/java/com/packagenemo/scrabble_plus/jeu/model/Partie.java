package com.packagenemo.scrabble_plus.jeu.model;

/**
 * Classe qui gère toute la partie.
 * Cette classe sera initialisée différemment selon le joueur présent sur l'interface courante
 *
 * Les caractéristiques d'initialisation (nb de joueurs, parametres,.. se feront directement grace à la BDD)
 */
public class Partie {

    // Code de la dernière mise à jour, sert à déterminer si le plateau est à jour
    private int codeLastUpdate;

    // Boolean qui indique si la partie sur cette machine a subit des changements depuis le dernier download BDD
    private boolean desChangementsSeSontProduits;

    /**
     * Initialise la partie pour chaque joueur
     * @param idPartieBDD : ID de la partie pour la BDD
     * @param loginJoueurCourant : login du joueur utilisant l'interface
     */
    public Partie(String idPartieBDD, String loginJoueurCourant) {
        // TODO : Initialiser la partie avec les informations contenues sur la BDD
        // L'initialisation de la partie devra pouvoir se faire à tout moment du jeu (jeu en cours)
    }

    /**
     * Synchronise le plateau avec la BDD
     *
     */
    public void updatePartie(){

        // TODO

        if (!partieAJour()){
            // TODO : Cas si la partie n'est pas à jour
            // Il faudra gérer les cas spécifiques où le joueur a effectué une action,
            // mais où la partie n'est pas à jour. Est-ce qu'on conserve les données de la machine ou de la BDD ?
            // Ce cas se présentera lorsque le joueur aura mis trop de temps à jouer par exemple

            return;
        }

        // TODO : Cas où la partie est à jour

        if (desChangementsSeSontProduits){
            // TODO : Upload sur la BDD des changements locaux
        }
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
    public void giveInputJoueurPlateau(int[] position){
        // TODO : Cette méthode est appelée notamment lorsque le joueur appuie sur l'image du plateau dans l'app
    }

    /**
     * Gère le clic sur la main du joueur courant
     * @param position : Coordonnées du plateau où le joueur a appuyé
     */
    public void giveInputJoueurMain(int[] position){
        // TODO : Cette méthode est appelée notamment lorsque le joueur appuie sur l'image de la main dans l'app
    }

    /**
     * Lorsque le joueur appuie sur le bouton défausser
     * @return
     */
    public void giveInputJoueurDefausser(){
        // TODO
    }

    /**
     * Lorsque le joueur appuie sur le bouton "fin de tour"
     * @return
     */
    public void giveInputJoueurFinTour(){
        // TODO
    }

    /**
     * Méthode appelée par l'interface graphique pour obtenir les informations d'affichage
     * @return
     */
    public String getStringPlateau(){
        // TODO

        return null;
    }

    /**
     * Méthode appelée par l'interface graphique pour obtenir les informations
     * d'affichage de la main du joueur courant
     * @return
     */
    public String getStringMainJoueur(){
        // TODO

        return null;
    }

    /**
     * Méthode appelée par l'interface graphique pour obtenir les messages adressés au joueur
     * @return
     */
    public String getStringMessageAuJoueur(){
        // TODO

        return null;
    }


}
