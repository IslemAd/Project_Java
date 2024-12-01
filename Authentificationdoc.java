package com.example.myapplication01; // Remplacez par votre package

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
public class Authentificationdoc extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authendoc); // Lier le fichier XML

        // Récupérer le bouton Get Started
        Button btnSignIndoc = findViewById(R.id.btnsignin2);
        Button btnSignUpdoc = findViewById(R.id.btnsignup2);

        // Ajouter un événement clic pour lancer AuthentificationActivity
        btnSignIndoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention pour lancer AuthentificationActivity
                Intent intent = new Intent(Authentificationdoc.this, Signindoc.class);
                startActivity(intent); // Lancer l'activité
            }
        });
        btnSignUpdoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Authentificationdoc.this, Signupdoc.class);
                startActivity(intent);

            }
        });
    }
}