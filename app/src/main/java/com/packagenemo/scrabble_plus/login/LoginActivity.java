package com.packagenemo.scrabble_plus.login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.firebase.ui.auth.AuthUI;

import java.util.Collections;
import java.util.List;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.tasks.Task;

import android.widget.Button;
import android.widget.EditText;
import com.google.android.material.textfield.TextInputLayout;


import androidx.appcompat.app.AppCompatActivity;
import com.packagenemo.scrabble_plus.R;
import com.packagenemo.scrabble_plus.jeu.ui.JeuActivity;
import com.packagenemo.scrabble_plus.menu.MenuActivity;
import com.packagenemo.scrabble_plus.register.RegisterActivity;
import com.packagenemo.scrabble_plus.login.BaseActivity;
import com.packagenemo.scrabble_plus.databinding.ActivityLoginBinding;




public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    TextInputLayout mEditTextLogin, mEditTextMdp;
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

        mEditTextLogin = (TextInputLayout) findViewById(R.id.loginInputMail);
        mEditTextMdp = (TextInputLayout) findViewById(R.id.loginInputPassword);
    }

    private void setupListeners(){
        // Login Button
        binding.loginButtonRegister.setOnClickListener(view -> {
            startLogInActivity();
        });

        binding.loginButton.setOnClickListener(view -> {
            testSigningIn();
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

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser currentUser = mAuth.getCurrentUser();

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
}