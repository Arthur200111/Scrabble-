package com.packagenemo.scrabble_plus.login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Collections;
import java.util.List;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.firebase.ui.auth.AuthUI;
import com.packagenemo.scrabble_plus.R;
import com.packagenemo.scrabble_plus.jeu.ui.JeuActivity;
import com.packagenemo.scrabble_plus.register.RegisterActivity;

import com.packagenemo.scrabble_plus.login.BaseActivity;
import com.packagenemo.scrabble_plus.databinding.ActivityLoginBinding;

import com.firebase.ui.auth.AuthUI;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    EditText mEditTextLogin, mEditTextMdp;
    Button mButtonInscription, mButtonConnexion;
    VerifConnexion mVerifConnexion;

    @Override
    ActivityLoginBinding getViewBinding() {
        return ActivityLoginBinding.inflate(getLayoutInflater());
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        setupListeners();

        mEditTextLogin = (EditText) findViewById(R.id.login_champ_login);
        mEditTextMdp = (EditText) findViewById(R.id.editTextTextPassword);
    }

    private void setupListeners(){
        // Login Button
        binding.loginBoutonInscription.setOnClickListener(view -> {
            startLogInActivity();
        });

        binding.loginBoutonConnexion.setOnClickListener(view -> {
            testSigningIn();
        });
    }

    private void startLogInActivity(){
        System.out.println("wesh on y est là");
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers =
                Collections.singletonList(new AuthUI.IdpConfig.EmailBuilder().build());

       // Launch the activity
        startActivity(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false, true)
                        .setLogo(R.drawable.blurbg)
                        .build());
    }

    private void testSigningIn(){
        String email, password;

        email = mEditTextLogin.getText().toString();
        password = mEditTextMdp.getText().toString();

        signIn(email, password);
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    /**
     * Méthode appelée lorsque le joueur valide ses informations de connexion
     */
    /*private void verifLogin(){

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
   /* private void connexionInvalide(){
        // TODO
    }

    /**
     * Appelé pour rediriger l'utilisateur vers la page d'inscription
     */
    /*private void procederInscription(){
        // TODO
    }

    /**
     * Valide la connexion demandée lors de l'appui sur le bouton connexion
     */
   /* private void procederConnexion(){

        // FIXME : L'activité redirige vers le jeu directement, mais ceci devra changer
        Intent toPageMenuIntent = new Intent(LoginActivity.this, JeuActivity.class);
        startActivity(toPageMenuIntent);
    }*/
}