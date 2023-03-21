package com.packagenemo.scrabble_plus.jeu.repository;


import android.content.Context;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.packagenemo.scrabble_plus.jeu.model.Utilisateur;

import javax.annotation.Nullable;


public final class UtilisateurRepository {

    private static final String COLLECTION_NAME = "utilisateur";
    private static final String USERNAME_FIELD = "nom";

    private static volatile UtilisateurRepository instance;

    private UtilisateurRepository() { }


    public static UtilisateurRepository getInstance() {
        UtilisateurRepository result = instance;
        if (result != null) {
            return result;
        }
        synchronized(UtilisateurRepository.class) {
            if (instance == null) {
                instance = new UtilisateurRepository();
            }
            return instance;
        }
    }


    @Nullable
    public FirebaseUser GetCurrentUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }




    // Get the Collection Reference
    private CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    public void createUtilisateur() {
        FirebaseUser utilisateur = FirebaseAuth.getInstance().getCurrentUser();
        if(utilisateur != null){
            String name = utilisateur.getDisplayName();
            String uid = utilisateur.getUid();

            Utilisateur userToCreate = new Utilisateur(uid, name);

            Task<DocumentSnapshot> userData = getUserData();
            // If the user already exist in Firestore, we get his data (isMentor)
            userData.addOnSuccessListener(documentSnapshot -> {
                this.getUsersCollection().document(uid).set(userToCreate);
            });
        }
    }

    // Get User Data from Firestore
    public Task<DocumentSnapshot> getUserData(){

        String uid = "0";
        if(uid != null){
            return this.getUsersCollection().document(uid).get();
        }else{
            return null;
        }
    }

    // Update User Username
    public Task<Void> updateUsername(String username) {
        String uid = "0";
        if(uid != null){
            return this.getUsersCollection().document(uid).update(USERNAME_FIELD, username);
        }else{
            return null;
        }
    }



    // Delete the User from Firestore
    public void deleteUserFromFirestore() {
        String uid = "0";
        if(uid != null){
            this.getUsersCollection().document(uid).delete();
        }
    }

    public Task<Void> signOut(Context context){
        return AuthUI.getInstance().signOut(context);
    }

    public Task<Void> deleteUser(Context context){
        return AuthUI.getInstance().delete(context);
    }

}
