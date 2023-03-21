package com.packagenemo.scrabble_plus.jeu.repository;

import com.packagenemo.scrabble_plus.jeu.model.Partie;

import java.util.List;

// TEST FIREBASE : callback pour le test (il faudra créer un dossier a part)

public interface PartieInterface {
        void onCallback(int nb);
}
