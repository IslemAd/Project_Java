package com.example.myapplication01; // Remplacez par votre package

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Authentification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentification); // Lier le fichier XML

        // Récupérer le bouton Get Started
        Button btnSignIn = findViewById(R.id.btnsignin);
        Button btnSignUp = findViewById(R.id.btnsignup);

        // Ajouter un événement clic pour lancer AuthentificationActivity
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention pour lancer AuthentificationActivity
                Intent intent = new Intent(Authentification.this, SignIn.class);
                startActivity(intent); // Lancer l'activité
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Authentification.this, SignUp.class);
                startActivity(intent);

            }
        });



    }
}

