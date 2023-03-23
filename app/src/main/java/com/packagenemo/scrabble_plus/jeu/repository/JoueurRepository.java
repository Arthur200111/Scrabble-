package com.packagenemo.scrabble_plus.jeu.repository;

import android.util.Log;

import androidx.annotation.NonNull;

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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.packagenemo.scrabble_plus.jeu.manager.PartieManager;
import com.packagenemo.scrabble_plus.jeu.model.Joueur;
import com.packagenemo.scrabble_plus.jeu.model.Lettre;
import com.packagenemo.scrabble_plus.jeu.model.MainJoueur;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JoueurRepository {

    /**
     * CONVENTION NOMMAGES DES DOCUMENTS DE LA COLLECTION JOUEUR : L'ID EST LA CONCATENATION DU CODE DELA PARTIE ET DE L'ID De l'USER
     */
    private static volatile JoueurRepository instance;
    private static final String JOUEUR_COLLECTION = "joueur";
    private static final String PIOCHE_COLLECTION = "pioche";
    private final String TAG = this.getClass().toString();
    private PartieManager partieManager = PartieManager.getInstance();

    private JoueurRepository() { }


    private FirebaseFirestore getFirestoreInstance(){
        return FirebaseFirestore.getInstance();
    }

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


    /**
     * Get the Collection Reference
     * @return
     */
    private CollectionReference getJoueurCollection(){
        return getFirestoreInstance().collection(JOUEUR_COLLECTION);
    }

    /**
     * Méthode qui retourne le document lié a un joueur en fonction de l'utilsateur et de la partie dans laquelle il évolue
     * @param codePartie
     * @return
     */
    private DocumentReference getJoueurDocument(String codePartie){
        return this.getJoueurCollection().document(codePartie + FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
    }

    //public void get
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

    public void addJoueurs(String numero_partie,int nombre_joueurs,Joueur joueur){
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

    /**
     * Créé un joueur : utiliser dans la fonction de création de partie ou bien de rejoindre partie
     * @param codePartie
     * @param joueur
     */
    public DocumentReference addJoueur(String codePartie, Joueur joueur){
        DocumentReference docRef = null;

        if (codePartie != null){

            docRef = getJoueurCollection().document(codePartie + FirebaseAuth.getInstance().getCurrentUser().getUid());

            String utilisateur = FirebaseAuth.getInstance().getCurrentUser().getUid();
            String partieCode = codePartie;
            String mainJoueur;
            String nom;
            int score;

            if(joueur==null){
                mainJoueur = new MainJoueur().getRepMain();
                nom = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                score = 0;
            }
            else{
                mainJoueur = joueur.getMainJ().getRepMain();
                nom = joueur.getNom();
                score = 0;
            }

            // Ajoute les informations sur la partie
            Map<String, Object> joueurMap = new HashMap<>();
            joueurMap.put("main", mainJoueur);
            joueurMap.put("nom", nom);
            joueurMap.put("partie", partieCode);
            joueurMap.put("score", score);
            joueurMap.put("utilisateur", utilisateur);


            docRef.set(joueurMap);
        }
        return docRef;
    }

    /**
     * Retrieve sscore of the current player
     * @param codePartie
     */
    /*public int getScore(String codePartie){
        DocumentReference joueur = this.getJoueurDocument(codePartie);
        return joueur.get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    }
                }
        )
    }*/

    /**
     * Update the score of the current player that is link to an utilsateur in a specifier partie
     * @param score
     * @param id_partie
     */
    public void updateScore(int score, String id_partie){
        String id_utilisateur = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Task<QuerySnapshot> getJoueurByIds = this.getJoueurCollection().whereEqualTo("idpartie", id_partie).whereEqualTo("idUtilisateur", id_utilisateur).get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Task<Void> score1 = document.getReference().update("score", score);
                            }

                        }
                    }
                }
        );
    }

    /**
     * Update the main of the current player that is link to an utilsateur in a specifier partie qui n'est constitué que des lettres du joueurs
     * @param mainJoueur
     * @param id_partie
     */
    public void updateMain(String mainJoueur, String id_partie){
        String id_utilisateur = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Task<QuerySnapshot> getJoueurByIds = this.getJoueurCollection().whereEqualTo("idpartie", id_partie).whereEqualTo("idUtilisateur", id_utilisateur).get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Task<Void> main = document.getReference().update("main", mainJoueur);
                            }

                        }
                    }
                }
        );
    }

    //public void getJoueur

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
