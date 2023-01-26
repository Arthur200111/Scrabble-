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

        findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPageMenuIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(toPageMenuIntent);
            }
        });
    }
}