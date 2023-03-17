package com.packagenemo.scrabble_plus.jeu.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.packagenemo.scrabble_plus.jeu.manager.PartieManager;
import com.packagenemo.scrabble_plus.jeu.model.Partie;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PartieRepository {

    private final String TAG = this.getClass().toString();
    private static final String PARTIE_COLLECTION = "jeux";
    private static final String JOUEUR_COLLECTION = "joueurs";
    private static final String COUP_COLLECTION = "coups";
    private static final String PIOCHE_COLLECTION = "pioche";
    private static final String PIOCHE_DOCUMENT = "lettres";
    private static final String PARAMETRE_COLLECTION = "parametres";

    private static volatile PartieRepository instance;
    private PartieManager partieManager;



    private PartieRepository() { }

    public static PartieRepository getInstance() {
        PartieRepository result = instance;
        if (result != null) {
            return result;
        }
        synchronized(PartieRepository.class) {
            if (instance == null) {
                instance = new PartieRepository();
            }
            return instance;
        }
    }

    // Get the Collection Reference
    private CollectionReference getPartieCollection(){
        return FirebaseFirestore.getInstance().collection(PARTIE_COLLECTION);
    }

    // Get Game Info
    public Task<DocumentSnapshot> getPartieInfo(String numero_partie) {
        Task<DocumentSnapshot> result = null;
        if (numero_partie != null){
            DocumentReference docRef = this.getPartieCollection().document(numero_partie);
            /*
            result = docRef.get().addOnSuccessListener( DocumentSnapshot -> {
                DocumentSnapshot document = result.getResult();
                Log.d("TAG", document.getData().toString());
            });
            result = partieManager.getPartieInfo("jeux000001");
            Info.addOnSuccessListener( DocumentSnapshot -> {
                DocumentSnapshot document = Info.getResult();
                Log.d("Partie recup", document.getData().toString());
            });
             */
            result = docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {

                            //TODO get Map data from document
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            Log.d(TAG, document.getData().get("code").toString() );
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }
        return result;
    }


    public CollectionReference getAllJoueurs(String numero_partie){
        return this.getPartieCollection()
                .document(numero_partie)
                .collection(JOUEUR_COLLECTION);
    }

    public void getJoueursInfo(String numero_partie, int nombre_joueurs) {
        if (numero_partie != null && nombre_joueurs > 0){
            for (int i=0; i < nombre_joueurs; i++){
                String nom_joueur = "joueur" + String.valueOf(i);
                DocumentReference docRef = this.getAllJoueurs(numero_partie).document(nom_joueur);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                //TODO get data from document
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                Log.d(TAG, document.getData().get("pseudo").toString() +
                                        " : " + document.getData().get("score").toString());
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
            }

        }else{
        }
    }

    public CollectionReference getAllCoups(String numero_partie){
        return this.getPartieCollection()
                .document(numero_partie)
                .collection(COUP_COLLECTION);
    }

    public void getLastCoupInfo(String numero_partie, int nombre_coups){
        if (numero_partie != null && nombre_coups > 0){
            String nom_coup = "joueur" + String.valueOf(nombre_coups);
            DocumentReference docRef = this.getAllCoups(numero_partie).document(nom_coup);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            //TODO get Map data from document
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            Log.d(TAG, document.getData().get("date").toString() +
                                    " ; " + document.getData().get("pseudo").toString() +
                                    " ; " + document.getData().get("mouvement").toString());
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }
    }

    public CollectionReference getParametres(String numero_partie){
        return this.getPartieCollection()
                .document(numero_partie)
                .collection(PARAMETRE_COLLECTION);
    }

    public CollectionReference getPiocheCollection(String numero_partie){
        return this.getPartieCollection()
                .document(numero_partie)
                .collection(PIOCHE_COLLECTION);
    }

    public String getPiocheInfo(String numero_partie){
        if (numero_partie != null){
            final String[] pioche_total = new String[1];
            DocumentReference docRef = this.getPiocheCollection(numero_partie).document(PIOCHE_DOCUMENT);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            //TODO get Map data from document
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            pioche_total[0] = document.get("consonnes").toString() + document.get("consonnes").toString();

                            Log.d(TAG, document.getData().get("date").toString() +
                                    " ; " + document.getData().get("pseudo").toString() +
                                    " ; " + document.getData().get("mouvement").toString());
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
            return pioche_total[0];
        }
        return null;
    }

    /*
    public void createMessageForChat(String textMessage, String chat){

        partieManager.getUserData().addOnSuccessListener(user -> {
            // Create the Message object
            Message message = new Message(textMessage, user);

            // Store Message to Firestore
            this.getChatCollection()
                    .document(chat)
                    .collection(MESSAGE_COLLECTION)
                    .add(message);
        });

    }

     */






}
