package com.example.myapplication01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class IdentityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identity); // Refers to a layout resource defined in XML

        // Récupérer le bouton Get Started
        Button btnImadoctor = findViewById(R.id.button3);
        Button btnImasecretary = findViewById(R.id.button4);
        Button btnImapatient = findViewById(R.id.button5);


        // Ajouter un événement clic pour lancer AuthentificationActivity
        btnImadoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention pour lancer AuthentificationActivity
                Intent intent = new Intent(IdentityActivity.this, Authentificationdoc.class);
                startActivity(intent); // Lancer l'activité
            }
        });
        // Ajouter un événement clic pour lancer AuthentificationActivity
        btnImasecretary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention pour lancer AuthentificationActivity
                Intent intent = new Intent(IdentityActivity.this, Authentificationsec.class);
                startActivity(intent); // Lancer l'activité
            }
        });

        // Ajouter un événement clic pour lancer AuthentificationActivity
        btnImapatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention pour lancer AuthentificationActivity
                Intent intent = new Intent(IdentityActivity.this, Authentification.class);
                startActivity(intent); // Lancer l'activité
            }
        });


    }
}
