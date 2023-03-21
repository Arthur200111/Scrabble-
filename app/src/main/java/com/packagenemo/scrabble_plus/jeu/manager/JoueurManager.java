package com.packagenemo.scrabble_plus.jeu.manager;

import com.google.firebase.firestore.CollectionReference;
import com.packagenemo.scrabble_plus.jeu.model.Joueur;
import com.packagenemo.scrabble_plus.jeu.repository.JoueurRepository;
import com.packagenemo.scrabble_plus.jeu.repository.PartieRepository;

import java.util.ArrayList;

public class JoueurManager {

    private static volatile JoueurManager instance;
    private JoueurRepository joueurRepository;

    private JoueurManager() {
        joueurRepository = JoueurRepository.getInstance();
    }

    public static JoueurManager getInstance() {
        JoueurManager result = instance;
        if (result != null) {
            return result;
        }
        synchronized(JoueurManager.class) {
            if (instance == null) {
                instance = new JoueurManager();
            }
            return instance;
        }
    }

    public ArrayList<Joueur> getJoueurs(String numero_partie, int nombre_joueurs){
        return joueurRepository.getJoueursInfo(numero_partie, nombre_joueurs);
    }

    public void addJoueur(String numero_partie, int nombre_joueurs, Joueur joueur){
        joueurRepository.addJoueur(numero_partie, nombre_joueurs, joueur);
    }

    /*
    public void addJoueurFromCode(String code_partie, int nombre_joueurs, Joueur joueur){
        joueurRepository.addJoueurFromCode(code_partie, nombre_joueurs, joueur);
    }

     */

    public void deleteJoueur(String numero_partie, int nombre_joueurs, String nom){
        joueurRepository.deleteJoueur(numero_partie,nombre_joueurs,nom);
    }



    public void updateScore(int score, String id_partie){
        this.joueurRepository.updateScore(score, id_partie);
    }

    public void updateMain(String main, String id_partie){
        this.joueurRepository.updateMain(main, id_partie);
    }
}
