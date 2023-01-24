package com.packagenemo.scrabble_plus.login;

/**
 * TODO : Cette classe interroge la base de données pour vérifier les informations de connexion des utilisateurs
 *
 */

public class VerifConnexion {

    public VerifConnexion() {
        //TODO : Connexion à la base de données
    }

    public boolean verifInformationsDeConnexion(String login, String motDePasse){

        //TODO : Interroger la base pour savoir si une occurence de la paire id mdp est valide
        //Ne pas oublier de Hasher et saler le mdp avat d'interroger la base grâce à BCrypt

        return true;
    }
}
