package com.packagenemo.scrabble_plus.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.packagenemo.scrabble_plus.R;
import com.packagenemo.scrabble_plus.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.registerButtonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPageMenuIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(toPageMenuIntent);
            }
        });
    }
}
/*

package com.packagenemo.scrabble_plus.register;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.firebase.ui.auth.AuthUI;
import com.packagenemo.scrabble_plus.R;
import com .packagenemo.scrabble_plus.databinding.ActivityRegisterBinding;
import com.packagenemo.scrabble_plus.login.BaseActivity;

import java.util.Collections;
import java.util.List;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupListeners();
    }



    private void startSignInActivity(){

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
                        .setLogo(R.drawable.ic_logo_auth)
                        .build());
    }
}
*/
