package com.example.myapplication01; // Remplacez par votre package

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
public class Authentificationsec extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authensec); // Lier le fichier XML

        // Récupérer le bouton Get Started
        Button btnSignInsec = findViewById(R.id.btnsigninsec);
        Button btnSignUpsec = findViewById(R.id.btnsignupsec);

        // Ajouter un événement clic pour lancer AuthentificationActivity
        btnSignInsec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention pour lancer AuthentificationActivity
                Intent intent = new Intent(Authentificationsec.this, Signinsec.class);
                startActivity(intent); // Lancer l'activité
            }
        });
        btnSignUpsec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Authentificationsec.this, Signupsec.class);
                startActivity(intent);

            }
        });
    }}