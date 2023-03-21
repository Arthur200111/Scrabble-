package com.packagenemo.scrabble_plus.login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.firebase.ui.auth.AuthUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.tasks.Task;

import com.google.android.material.textfield.TextInputLayout;


import com.packagenemo.scrabble_plus.R;
import com.packagenemo.scrabble_plus.jeu.manager.UtilisateurManager;
import com.packagenemo.scrabble_plus.jeu.model.Partie;
import com.packagenemo.scrabble_plus.jeu.repository.PartieInterface;
import com.packagenemo.scrabble_plus.jeu.repository.PartieRepository;
import com.packagenemo.scrabble_plus.menu.MenuActivity;
import com.packagenemo.scrabble_plus.databinding.ActivityLoginBinding;




public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    //private static final int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    TextInputLayout mEditTextLogin, mEditTextMdp;

    private static PartieRepository partieRepository = PartieRepository.getInstance();

    private static UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();

    @Override
    ActivityLoginBinding getViewBinding() {
        return ActivityLoginBinding.inflate(getLayoutInflater());
    }

    // TEST FIREBASE : l'attribut a modifié avec les getter/setter + méthode qui l'affiche
    private int testint;

    public void setTestint(int testint){
        this.testint = testint;
    }

    public int getTestint(){ return testint;}


    public void displayTestint(){
        System.out.println(" ----------------------- \n \r  \n" +
                " La valeur de set int doit être égale à 10 mais est égale à " + getTestint());
    }

    // TEST FIREBASE : méthode qui appelle la méthode du repo (ça devrait etre dans le manager) et qui lui passe le callback (qui est sour la forme d'une interface)
    public void test(){
        List<Partie> games = new ArrayList<>();
        this.partieRepository.getPartieFromUser(new PartieInterface() {
                              @Override
                              public void onCallback(int nb){
                                   setTestint(nb);
                              }
                          }
        );
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        setupListeners();

        mEditTextLogin = (TextInputLayout) findViewById(R.id.loginInputMail);
        mEditTextMdp = (TextInputLayout) findViewById(R.id.loginInputPassword);

        testint = 0;

        // TEST FIREBASE : on appelle la fonction qui doit modifier l'attribut
        test();


    }

    private void setupListeners(){
        // Login Button
        binding.loginButtonRegister.setOnClickListener(view -> {
            startLogInActivity();
        });

        binding.loginButton.setOnClickListener(view -> {
            if(!mEditTextLogin.getEditText().getText().toString().isEmpty() &&
                    !mEditTextMdp.getEditText().getText().toString().isEmpty()){
                testSigningIn();
            }
        });
    }

    private void startLogInActivity(){
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

        email = mEditTextLogin.getEditText().getText().toString();
        password = mEditTextMdp.getEditText().getText().toString();

        System.out.println(email);
        System.out.println(password);

        signIn(email, password);
    }

    // Show Snack Bar with a message
    private void showSnackBar( String message){
        Snackbar.make(binding.loginButton, message, Snackbar.LENGTH_SHORT).show();
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser currentUser = mAuth.getCurrentUser();

                        if (task.isSuccessful()) {
                            // TEST FIREBASE : je regarde juste si l'attribut a bien été modifié
                            displayTestint();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                            utilisateurManager.createUser();
                            showSnackBar("Connexion réussie");
                            startActivity(intent);
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
}