package com.packagenemo.scrabble_plus.jeu.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.packagenemo.scrabble_plus.jeu.callback.PartieInterface;
import com.packagenemo.scrabble_plus.jeu.manager.PartieManager;
import com.packagenemo.scrabble_plus.jeu.model.Joueur;
import com.packagenemo.scrabble_plus.jeu.model.Lettre;
import com.packagenemo.scrabble_plus.jeu.model.Parametres;
import com.packagenemo.scrabble_plus.jeu.model.Pioche;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PartieRepository {

    private final String TAG = this.getClass().toString();
    private static final String PARTIE_COLLECTION = "jeux";
    private static final String JOUEUR_COLLECTION = "joueur";
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

    private FirebaseFirestore getFirestoreInstance(){
        return FirebaseFirestore.getInstance();
    }

    /**
     * Get the Collection Reference
     * @return
     */
    private CollectionReference getPartieCollection(){
        return getFirestoreInstance().collection(PARTIE_COLLECTION);
    }

    /**
     * Get the Collection Reference
     * @return
     */
    private CollectionReference getJoueurCollection(){
        return getFirestoreInstance().collection(JOUEUR_COLLECTION);
    }


    public Task<DocumentSnapshot> getPartieInfo(String numero_partie) {
        if (numero_partie != null){
            DocumentReference docRef = this.getPartieCollection().document(numero_partie);
            return docRef.get();
        }
        return null;
    }


    /**
     * Get info from the game with the same code
     * Get String nom, int nombre de coups, int nombre de joueurs, String plateau, String prochain joueur
     * @param code_partie
     * @return
     */
    public Task<QuerySnapshot> getPartieFromCode(String code_partie) {
        if (code_partie != null) {
            Task<QuerySnapshot> task = this.getPartieCollection()
                    .whereEqualTo("code", code_partie)
                    .limit(1)
                    .get();
            return task;
        } else {
            return null;
        }
    }









    /**
     * Generate an alphanumeric code
     * @param length
     * @return
     */
    private String generateCode(int length){
        Random random1 = new Random();
        int ALPHABET_ASCII = 65;
        String result = "";
        for (int i=0; i<length; i++){
            int n = random1.nextInt(36);
            if (n>=26){
                result = result + Integer.toString(n-26);
            } else {
                result = result + (char) (n + ALPHABET_ASCII);
            }
        }
        return result;
    }

    /**
     * Crée une partie dans la base de données
     * Utilise un nom de partie, le joueur qui crée la partie, le plateau et la pioche
     * Renvoie le document contenant la partie
     * @param nom_partie
     * @param joueur
     * @param plateau
     * @param pioche
     * @return
     */
    public DocumentReference createPartie(String nom_partie, Joueur joueur, String plateau, Pioche pioche, Parametres parametres){
        // Création d'un nouveau document contenant la partie et récupération de sa référence
        DocumentReference docRef = this.getPartieCollection().document();

        // Ajoute les informations sur la partie
        Map<String, Object> partie = new HashMap<>();
        partie.put("nom", nom_partie);
        partie.put("prochain joueur", joueur.getNom());
        partie.put("nombre de coups", 0);
        partie.put("nombre de joueurs", 1);
        partie.put("plateau", plateau);
        partie.put("code",generateCode(8));
        partie.put("temps", FieldValue.serverTimestamp());
        docRef.set(partie);

        // Ajoute les paramètres
        docRef.collection(PARAMETRE_COLLECTION).add(parametres);

        // Ajoute les informations sur la pioche
        Map<String, Object> piocheStr = new HashMap<>();
        piocheStr.put("consonnes","");
        piocheStr.put("voyelles","");
        piocheStr.put("joker",0);
        for (Lettre l : pioche.getConsonnes()) {
            if (l.getLettre().equals("_")) {
                piocheStr.get("joker");
                piocheStr.put("joker", (Integer) piocheStr.get("joker") + 1);
            } else {
                piocheStr.put("consonnes", piocheStr.get("consonnes") + l.getLettre());
            }
        }
        for (Lettre l : pioche.getVoyelles()) {
            piocheStr.put("voyelles", piocheStr.get("voyelles") + l.getLettre());
        }
        docRef.collection(PIOCHE_COLLECTION).document(PIOCHE_DOCUMENT).set(piocheStr);

        // Ajoute le joueur qui a créé la partie
        docRef.collection(JOUEUR_COLLECTION).document("joueur1").set(joueur);

        return docRef;
    }

    public void joinPartie(String code_partie, Joueur joueur){
        Task<QuerySnapshot> task = getPartieFromCode(code_partie);
        task.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DocumentReference docRef = document.getReference();
                        Log.d(TAG, document.getId() + " => " + document.getData());

                        int nombre_joueurs = Math.toIntExact((long) document.get("nombre de joueurs"));
                        docRef.update("nombre de joueurs", FieldValue.increment(1));

                        String nom_joueur = "joueur" + String.valueOf(nombre_joueurs + 1);

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


    public CollectionReference getJoueursCollection(String numero_partie){
        return this.getPartieCollection()
                .document(numero_partie)
                .collection(JOUEUR_COLLECTION);
    }



    public CollectionReference getAllCoups(String numero_partie){
        return this.getPartieCollection()
                .document(numero_partie)
                .collection(COUP_COLLECTION);
    }

    public DocumentSnapshot getLastCoupInfo(String numero_partie, int nombre_coups){
        if (numero_partie != null && nombre_coups > 0){
            String nom_coup = "coups" + String.valueOf(nombre_coups);
            DocumentReference docRef = this.getAllCoups(numero_partie).document(nom_coup);
            return docRef.get().getResult();
            /*
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            //TODO get Map data from document
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            Log.d(TAG, document.getData().get("date").toString() +
                                    " ; " + document.getData().get("joueur").toString() +
                                    " ; " + document.getData().get("mouvement").toString());
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });

             */
        }
        return null;
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

    // Fait rien
    public Task<DocumentSnapshot> getPiocheConsonnes(String code){
        this.getPartieFromCode(code).continueWith(new Continuation<QuerySnapshot, Task<DocumentSnapshot>>() {
            @Override
            public Task<DocumentSnapshot> then(@NonNull Task<QuerySnapshot> task) throws Exception {
                task.getResult().getDocuments();
                return null;
            }
        });
        return null;
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


    // TEST FIREBASE : Défintion de la méthode à laquelle on passe plus tard le callback
    /**
     * Get info from the game with the same code
     * Get String nom, int nombre de coups, int nombre de joueurs, String plateau, String prochain joueur
     * @param cb
     * @return
     */
    public void getPartieFromUser(PartieInterface cb) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            System.out.println("Trying to access players by User");
            this.getJoueurCollection().whereEqualTo("utilisateur", "aaaa").get().addOnSuccessListener( //FirebaseAuth.getInstance().getCurrentUser()
                    new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            System.out.println("If we are here, we succeded. The first player is : " + queryDocumentSnapshots.getDocuments().get(0).get("nom", String.class));

                            for(DocumentSnapshot q : queryDocumentSnapshots.getDocuments()) {


                                getPartieCollection().document(q.get("partie", DocumentReference.class).toString()).get().addOnSuccessListener(
                                        new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                String[] champs = {"code", "nom", "prochain joueur"};
                                                ArrayList<String> parties = new ArrayList<>();

                                                for (DocumentSnapshot q : queryDocumentSnapshots.getDocuments()) {
                                                    for (String str : Arrays.asList(champs)) {
                                                        parties.add(q.get(str, String.class));
                                                    }
                                                }
                                                cb.onCallback(parties);
                                            }
                                        }
                                );
                            }

                        }
                    }
            );

        }
    }
}


