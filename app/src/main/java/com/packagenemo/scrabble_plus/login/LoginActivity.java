package com.packagenemo.scrabble_plus.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.packagenemo.scrabble_plus.R;
import com.packagenemo.scrabble_plus.jeu.ui.JeuActivity;
import com.packagenemo.scrabble_plus.menu.MenuActivity;
import com.packagenemo.scrabble_plus.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    EditText mEditTextLogin, mEditTextMdp;
    Button mButtonInscription, mButtonConnexion;
    VerifConnexion mVerifConnexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mVerifConnexion = new VerifConnexion();

        mButtonConnexion = findViewById(R.id.loginButton);
        mButtonConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifLogin();
            }
        });
        findViewById(R.id.loginButtonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputLayout textInputLayout = findViewById(R.id.loginInputMail);
                String pseudo = textInputLayout.getEditText().getText().toString();
                System.out.println(pseudo);
                if (pseudo == "admin") {
                    Intent toPageMenuIntent = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(toPageMenuIntent);
                }
                Intent toPageMenuIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(toPageMenuIntent);
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
        TextInputLayout textInputLayout = findViewById(R.id.loginInputMail);
        String pseudo = textInputLayout.getEditText().getText().toString();
        System.out.println(pseudo);
        if (pseudo.equals("admin")) {
            Intent toPageMenuIntent = new Intent(LoginActivity.this, JeuActivity.class);
            startActivity(toPageMenuIntent);
        } else {
            Intent toPageMenuIntent = new Intent(LoginActivity.this, MenuActivity.class);
            startActivity(toPageMenuIntent);
        }


        // FIXME : L'activité redirige vers le jeu directement, mais ceci devra changer
    }
}