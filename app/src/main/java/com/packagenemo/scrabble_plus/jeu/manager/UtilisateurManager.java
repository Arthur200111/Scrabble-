package com.packagenemo.scrabble_plus.jeu.manager;

import android.content.Context;
import android.os.Debug;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.packagenemo.scrabble_plus.jeu.model.Utilisateur;
import com.packagenemo.scrabble_plus.jeu.repository.UtilisateurRepository;
import com.packagenemo.scrabble_plus.jeu.repository.UtilisateurRepository.*;


public class UtilisateurManager {

    private static volatile UtilisateurManager instance;
    private static UtilisateurRepository utilisateurRepository;

    private UtilisateurManager() {
        utilisateurRepository = UtilisateurRepository.getInstance();
    }
    public static UtilisateurManager getInstance() {
        UtilisateurManager result = instance;
        if (result != null) {
            return result;
        }
        synchronized(UtilisateurManager.class) {
            if (instance == null) {
                instance = new UtilisateurManager();
            }
            return instance;
        }
    }


    public FirebaseUser GetCurrentUser(){
        return utilisateurRepository.GetCurrentUser();
    }

    public boolean IsCurrentLogged(){
        return (this.GetCurrentUser()!=null);
    }


    public void createUser(){
        utilisateurRepository.createUtilisateur();
    }

    public Task<Utilisateur> getUserData(){
        // Get the user from Firestore and cast it to a User model Object
        return utilisateurRepository.getUserData().continueWith(task -> task.getResult().toObject(Utilisateur.class)) ;
    }

    public Task<Void> updateUsername(String username){
        return utilisateurRepository.updateUsername(username);
    }

    public Task<Void> deleteUser(Context context){
        // Delete the user account from the Auth
        return FirebaseAuth.getInstance().getCurrentUser().delete().addOnCompleteListener(task -> {
            utilisateurRepository.deleteUserFromFirestore();
         });
    }

    public Task<Void> SignOut(Context context){
        return utilisateurRepository.signOut(context);
    }
}