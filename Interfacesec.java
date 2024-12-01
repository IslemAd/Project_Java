package com.example.myapplication01; // Remplacez par votre package

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Interfacesec extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intersec); // Lier le fichier XML

        // Récupérer le bouton Get Started
        Button btnManageAppSec = findViewById(R.id.button6);
        Button btnViewAppSec = findViewById(R.id.button10);
        Button btnSendRemSec = findViewById(R.id.button8);
        Button btnViewPatSec = findViewById(R.id.button9);

        // Ajouter un événement clic pour lancer AuthentificationActivity
        btnManageAppSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention pour lancer AuthentificationActivity
                Intent intent = new Intent(Interfacesec.this, ManageAppSec.class);
                startActivity(intent); // Lancer l'activité
            }
        });

        btnViewAppSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention pour lancer AuthentificationActivity
                Intent intent = new Intent(Interfacesec.this, ViewAppSec.class);
                startActivity(intent); // Lancer l'activité
            }
        });

        btnSendRemSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention pour lancer AuthentificationActivity
                Intent intent = new Intent(Interfacesec.this, SendRemSec.class);
                startActivity(intent); // Lancer l'activité
            }
        });

        btnViewPatSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention pour lancer AuthentificationActivity
                Intent intent = new Intent(Interfacesec.this, ViewPatSec.class);
                startActivity(intent); // Lancer l'activité
            }
        });
    }
}

