package com.packagenemo.scrabble_plus.jeu.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GestionMots {
    private List<Mot> motsListe;
    private Dictionnaire dico;

    /**
     * Constructeur de la classe
     *
     */
    public GestionMots() {
        motsListe = new LinkedList<>();
        dico = new Dictionnaire();
    }

    /**
     * Permet d'ajouter un mot à la liste des mots joués à partir de la liste des
     * cases jouées ainsi que du plateau de jeu
     *
     * @param lettresJouees liste des cases jouées
     * @param plateau       plateau de jeu
     * @return List<Mot> liste des mots ajoutés
     */
    public List<Mot> ajoutMot(List<Case> lettresJouees, Plateau plateau) {
        try {
            LinkedList<Mot> nouveauxMots = new LinkedList<>(verifPlacement(lettresJouees, plateau));
            return nouveauxMots;
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
            return null;
        }

    }

    /**
     * Permet de vérifier que les lettres jouées obéissent bien aux règles du
     * Scrabble
     *
     * @param lettresJouees liste des lettres jouées
     * @param plateau       plateau de jeu
     * @return List<Mot> liste des mots formés par les lettre jouées
     * @throws Exception si le tour du joueur ne correspond pas aux règles, une
     *                   exception est envoyée avec pour message le détail du
     *                   problème
     */
    private List<Mot> verifPlacement(List<Case> lettresJouees, Plateau plateau) throws Exception {
        int i = lettresJouees.get(0).getPos().getX(); // colonne de la première lettre jouée
        int j = lettresJouees.get(0).getPos().getY(); // ligne de la première lettre jouée

        // On commence par vérifier que les lettres jouées sont bien sur la même ligne
        // (ou la même colonne)
        for (Case c : lettresJouees) {
            if (i != -1 && i != c.getPos().getX()) {
                i = -1;
            }
            if (j != -1 && j != c.getPos().getY()) {
                j = -1;
            }
            if (i == -1 && j == -1) {
                throw new Exception("Les cases ne sont pas alignées");
            }
        }

        // On vérifie que dans toutes les lettres posées il y en a au moins une qui est
        // collée aux lettres sur le plateau
        Mot m;
        Mot m2;
        List<Mot> nouveauxMots = new LinkedList<>();
        boolean goodPlace = false;
        if (lettresJouees.size() == 1) {
            // Ici on est dans le cas spécifique ou l'utilisateur ne plasse qu'une lettre
            m = getWordCol(lettresJouees.get(0), plateau);
            if (m.getMot().length() > 1) {
                nouveauxMots.add(m);
                goodPlace = true;
            }
            m = getWordRow(lettresJouees.get(0), plateau);
            if (m.getMot().length() > 1) {
                nouveauxMots.add(m);
                goodPlace = true;
            }
        } else if (i != -1) {
            // Ici on se trouve dans le cas ou les lettres placées se trouvent sur la même
            // ligne
            m = getWordCol(lettresJouees.get(0), plateau);
            nouveauxMots.add(m);
            for (Case c : lettresJouees) {
                if (!m.equals(getWordCol(c, plateau))) {
                    throw new Exception("Les lettres ne sont pas collées colonnes");
                }
                if (!goodPlace && c.getTypeCase() == 5) {
                    goodPlace = true;
                }
                m2 = getWordRow(c, plateau);
                if (m2.getMot().length() > 1) {
                    nouveauxMots.add(m2);
                    goodPlace = true;
                }
            }
        } else {
            // Ici on se trouve dans le cas où les lettres placées se trouvent sur la même
            // colonne
            m = getWordRow(lettresJouees.get(0), plateau);
            nouveauxMots.add(m);
            for (Case c : lettresJouees) {
                if (!m.equals(getWordRow(c, plateau))) {
                    throw new Exception("Les lettres ne sont pas collées lignes");
                }
                if (!goodPlace && c.getTypeCase() == 5) {
                    goodPlace = true;
                }
                m2 = getWordCol(c, plateau);
                if (m2.getMot().length() > 1) {
                    nouveauxMots.add(m2);
                    goodPlace = true;
                }
            }
        }
        if (!goodPlace && m.getMot().length() <= lettresJouees.size()) {
            throw new Exception("Placement incorrect, il faut coller le mot à un autre !");
        }
        System.out.println("Liste des mots jouées : ");
        for (Mot m45 : nouveauxMots) {
            System.out.println(m45);
            if (!dico.verifMot(m45.getMot())) {
                throw new Exception("Le mot : " + m45.getMot() + " n'existe pas ! ");
            }
        }

        return nouveauxMots;
    }

    /**
     * Permet d'obtenir le mot que forme la lettre présente dans une case en colonne
     *
     * @param c       case contenant une lettre
     * @param plateau plateau de jeu
     * @return Mot mot que forme la case
     */
    private Mot getWordCol(Case c, Plateau plateau) {
        int i = c.getPos().getX();
        int j = c.getPos().getY();
        List<Case> mot = new ArrayList<>();

        while (j > 0 && plateau.getCase(i, j - 1).getLettre() != null) {
            j -= 1;
        }

        c = plateau.getCase(i, j);
        mot.add(c);
        while (j < plateau.getLongueur() && plateau.getCase(i, j + 1).getLettre() != null) {
            j += 1;
            c = plateau.getCase(i, j);
            mot.add(c);
        }
        return new Mot(mot);
    }

    /**
     * Permet d'obtenir le mot que forme la lettre présente dans une case en ligne
     *
     * @param c       case contenant une lettre
     * @param plateau plateau de jeu
     * @return Mot mot que forme la case
     */
    private Mot getWordRow(Case c, Plateau plateau) {
        int i = c.getPos().getX();
        int j = c.getPos().getY();
        List<Case> mot = new ArrayList<>();

        while (i > 0 && plateau.getCase(i - 1, j).getLettre() != null) {
            i -= 1;
        }

        c = plateau.getCase(i, j);
        mot.add(c);
        while (i < plateau.getLongueur() && plateau.getCase(i + 1, j).getLettre() != null) {
            i += 1;
            c = plateau.getCase(i, j);
            mot.add(c);
        }
        return new Mot(mot);
    }

    /**
     * getters de motListe (la liste des mots joués)
     *
     * @return List<Mot> liste des mots joués
     */
    public List<Mot> getMotsListe() {
        return this.motsListe;
    }

    /**
     * Setters de motsListe (la liste des mots joués)
     *
     * @param motsListe liste des mots joués
     */
    public void setMotsListe(List<Mot> motsListe) {
        this.motsListe = motsListe;
    }
}
