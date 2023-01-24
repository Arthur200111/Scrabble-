package com.packagenemo.scrabble_plus.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.packagenemo.scrabble_plus.R;
import com.packagenemo.scrabble_plus.jeu.ui.JeuActivity;

public class LoginActivity extends AppCompatActivity {

    EditText mEditTextLogin, mEditTextMdp;
    Button mButtonInscription, mButtonConnexion;
    VerifConnexion mVerifConnexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mVerifConnexion = new VerifConnexion();

        mButtonConnexion = findViewById(R.id.login_bouton_connexion);
        mButtonConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {verifLogin();
            }
        });

        // TODO : Gérer les boutons, les champs, et gérer les appels avec id ok et pas ok
    }

    /**
     * Méthode appelée lorsque le joueur valide ses informations de connexion
     */
    private void verifLogin(){

        // TODO : Faire les vérifications sur les logins des usagers
        if (mVerifConnexion.verifInformationsDeConnexion("Greta", "Thunberg")){
            procederConnexion();
        } else {
            // Informer l'utilisateur que connexion pas ok et pourquoi
        }


    }

    /**
     * Scénario dans lequel l'utilisateur saisi de mauvaises informations de connexion
     */
    private void connexionInvalide(){
        // TODO
    }

    /**
     * Appelé pour rediriger l'utilisateur vers la page d'inscription
     */
    private void procederInscription(){
        // TODO
    }

    /**
     * Valide la connexion demandée lors de l'appui sur le bouton connexion
     */
    private void procederConnexion(){

        // FIXME : L'activité redirige vers le jeu directement, mais ceci devra changer
        Intent toPageMenuIntent = new Intent(LoginActivity.this, JeuActivity.class);
        startActivity(toPageMenuIntent);
    }
}