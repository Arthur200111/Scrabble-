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
        String string1 = "15;15;2,C,8,0;2,B,1,0;1,4,10,0;0,4,1,0;2,A,6,0;1,4,8,0;0,4,7,0;2,E,8,0;" +
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
                "0,2,8,0;1,2,9,0;0,0,1,0;0,0,10,0;0,4,6,0;0,2,2,0;1,1,8,0;0,2,5,0;0,3,6,0;1,1,9,0;" +
                "2,D,9,0;2,D,5,0;1,4,1,0;0,2,10,0;1,1,5,0;2,E,4,0;0,0,7,0;0,4,7,0;1,1,8,0;2,E,2,0;" +
                "2,F,10,0;1,5,1,0;0,1,4,0;1,2,2,0;1,1,1,0;2,A,9,0;1,3,3,0;0,0,6,0;2,D,10,0;0,3,8,0;" +
                "0,3,3,0;1,3,6,0;1,4,8,0;1,1,7,0;0,0,6,0;0,4,2,0;0,0,4,0;2,D,3,0;0,3,5,0;1,0,3,0;" +
                "1,2,4,0;0,5,2,0;2,B,9,0;1,2,5,0;1,4,1,0;2,B,3,0;1,1,5,0;0,3,5,0;2,B,10,0;2,D,8,0;" +
                "2,B,9,0;2,C,9,0;2,A,7,0;2,A,6,0;1,0,9,0;2,E,2,0;2,E,2,0;2,F,6,0;1,1,8,0;1,5,3,0;" +
                "2,E,7,0;1,4,4,0;1,1,4,0;1,1,3,0;2,F,10,0;1,1,6,0;2,A,6,0;2,B,8,0;1,4,4,0;2,D,5,0;" +
                "2,A,3,0;2,B,9,0;0,1,6,0;1,3,3,0;2,C,6,0;0,1,1,0;2,F,1,0;2,A,1,0;2,F,4,0;2,B,6,0;" +
                "0,4,4,0;0,3,7,0;1,4,4,0;2,F,5,0;1,0,4,0;1,4,9,0;2,A,2,0;2,C,9,0;1,3,7,0;0,4,6,0;" +
                "2,C,9,0;2,D,10,0;2,D,9,0;1,5,1,0;0,0,3,0;1,4,10,0;1,4,2,0;0,3,9,0;1,1,3,0;1,1,10,0;" +
                "0,1,3,0;0,1,9,0;0,0,5,0;2,B,7,0;2,C,3,0;2,C,9,0;0,2,6,0;";

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
        // TODO

        return "7;2,A,1,0;2,I,1,0;2,G,2,0;2,A,1,1;2,P,3,0;1,0,0,0;1,0,0,0;";
    }

    public int getPointsDuJoueur(){
        return 12;
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
