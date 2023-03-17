package com.packagenemo.scrabble_plus.jeu.manager;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.packagenemo.scrabble_plus.jeu.repository.PartieRepository;

import java.util.HashMap;

public class PartieManager {

    private static volatile PartieManager instance;
    private PartieRepository partieRepository;

    private PartieManager() {
        partieRepository = PartieRepository.getInstance();
    }

    public static PartieManager getInstance() {
        PartieManager result = instance;
        if (result != null) {
            return result;
        }
        synchronized(PartieManager.class) {
            if (instance == null) {
                instance = new PartieManager();
            }
            return instance;
        }
    }

    public CollectionReference getJoueurs(String numero_partie){
        return partieRepository.getAllJoueurs(numero_partie);
    }
    public CollectionReference getPioche(String numero_partie){
        return partieRepository.getPiocheCollection(numero_partie);
    }
    public CollectionReference getCoups(String numero_partie){
        return partieRepository.getAllCoups(numero_partie);
    }
    public CollectionReference getParametres(String numero_partie){
        return partieRepository.getParametres(numero_partie);
    }

    public Task<DocumentSnapshot> getPartieInfo(String numero_partie){
        return partieRepository.getPartieInfo(numero_partie);
    }

    public CollectionReference getAllJoueurs(String numero_partie){
        return partieRepository.getAllJoueurs(numero_partie);
    }

}
