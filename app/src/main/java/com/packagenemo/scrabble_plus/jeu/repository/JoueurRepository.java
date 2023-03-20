package com.packagenemo.scrabble_plus.jeu.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.packagenemo.scrabble_plus.jeu.manager.PartieManager;
import com.packagenemo.scrabble_plus.jeu.model.Joueur;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class JoueurRepository {


    private static volatile JoueurRepository instance;
    private static final String JOUEUR_COLLECTION = "joueurs";
    private final String TAG = this.getClass().toString();
    private PartieManager partieManager = PartieManager.getInstance();

    private JoueurRepository() { }

    public static JoueurRepository getInstance() {
        JoueurRepository result = instance;
        if (result != null) {
            return result;
        }
        synchronized(JoueurRepository.class) {
            if (instance == null) {
                instance = new JoueurRepository();
            }
            return instance;
        }
    }

    public ArrayList<Joueur> getJoueursInfo(String numero_partie, int nombre_joueurs) {
        ArrayList<Joueur> joueurs = new ArrayList<>();
        if (numero_partie != null && nombre_joueurs > 0){
            for (int i=0; i < nombre_joueurs; i++) {
                String nom_joueur = "joueur" + String.valueOf(i);
                DocumentReference docRef = partieManager.getJoueursCollection(numero_partie).document(nom_joueur);
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Joueur joueur = documentSnapshot.toObject(Joueur.class);
                        joueurs.add(joueur);
                    }
                });

            }
        }
        return joueurs;
    }

    public void addJoueur(String numero_partie,int nombre_joueurs,Joueur joueur){
        if (numero_partie != null){
            String nom_joueur = "joueur" + String.valueOf(nombre_joueurs + 1);
            partieManager.getJoueursCollection(numero_partie).document(nom_joueur)
                    .set(joueur)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });
        }
    }

    /*
    public void addJoueurFromCode(String code_partie,int nombre_joueurs,Joueur joueur){
        if (code_partie != null) {
            Task<QuerySnapshot> task = partieManager.getPartieFromCode(code_partie);
            String nom_joueur = "joueur" + String.valueOf(nombre_joueurs + 1);

            task.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            Log.d(TAG, document.getId() + " => " + document.getData());
                            DocumentReference docRef = document.getReference();
                            docRef.collection(JOUEUR_COLLECTION)
                                    .document(nom_joueur)
                                    .set(joueur)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d(TAG,"joueur ajouté VRAIMENT");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG,"joueur pas ajouté :( à cause de " + e);

                                        }
                                    });

                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });
        }

    }

     */

    public void deleteJoueur(String numero_partie, int nombre_joueurs, String nom){
        if (numero_partie != null && nom != null && nombre_joueurs > 0){
            for (int i=0; i < nombre_joueurs; i++) {
                String nom_joueur = "joueur" + String.valueOf(i);
                DocumentReference docRef = partieManager.getJoueursCollection(numero_partie).document(nom_joueur);
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Joueur joueur = documentSnapshot.toObject(Joueur.class);
                        if (joueur.getNom().equals(nom)) {
                            partieManager.getJoueursCollection(numero_partie).document(nom_joueur).delete();
                        }
                    }
                });
            }
        }
    }

}
