package com.packagenemo.scrabble_plus.jeu.model;

/**
 * Classe qui gère toute la partie.
 * Cette classe sera initialisée différemment selon le joueur présent sur l'interface courante
 *
 * Les caractéristiques d'initialisation (nb de joueurs, parametres,.. se feront directement grace à la BDD)
 */
public class Partie {
    private boolean alternateur;

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

        alternateur = true;
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
    public void giveInputJoueurPlateau(int[] position, String typeAction){
        // TODO : Cette méthode est appelée notamment lorsque le joueur appuie sur l'image du plateau dans l'app

        System.out.println(typeAction + "  " +position[0] + " " + position[1]);
    }

    /**
     * Gère le clic sur la main du joueur courant
     * @param position : Coordonnées du plateau où le joueur a appuyé
     */
    public void giveInputJoueurMain(int position, String typeAction){
        // TODO : Cette méthode est appelée notamment lorsque le joueur appuie sur l'image de la main dans l'app

        System.out.println(typeAction + "  " +position);
    }

    /**
     * Lorsque le joueur appuie sur le bouton défausser
     * @return
     */
    public void giveInputJoueurDefausser(){
        // TODO
        System.out.println("Défausse");
    }

    /**
     * Lorsque le joueur appuie sur le bouton "fin de tour"
     * @return
     */
    public void giveInputJoueurFinTour(){
        // TODO
        System.out.println("Fin de tour");
    }

    /**
     * Méthode appelée par l'interface graphique pour obtenir les informations d'affichage
     * @return
     */
    public String getStringPlateau(){
        // TODO
        String string1 = "4;6;0,1,0,1;0,2,0,0;0,3,0,0;0,4,0,0;0,5,0,0;0,0,0,0;0,0,0,1;0,0,0,0;0,0,0,0;" +
                "2,E,4,0;2,D,2,0;2,F,3,1;0,0,0,0;0,0,0,0;0,4,0,0;0,2,0,0;2,D,2,0;0,0,0,0;0,0,0,0;" +
                "0,0,0,0;0,4,0,0;0,2,0,0;2,D,2,1;0,0,0,0;";

        String string2 = "4;6;0,1,0,1;0,2,0,0;0,3,0,0;2,D,2,0;0,0,0,0;0,0,0,0;" +
                "0,0,0,0;0,4,0,0;0,2,0,0;2,D,2,1;0,0,0,0;0,4,0,0;0,5,0,0;0,0,0,0;0,0,0,1;0,0,0,0;0,0,0,0;" +
                "2,E,4,0;2,D,2,0;2,F,3,1;0,0,0,0;0,0,0,0;0,4,0,0;0,2,0,0;";

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
        // TODO

        return "7;2,A,1,0;2,I,1,0;2,G,2,0;2,A,1,1;2,P,3,0;1,0,0,0;1,0,0,0;";
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
