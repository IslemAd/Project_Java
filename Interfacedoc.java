package com.example.myapplication01; // Remplacez par votre package

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Interfacedoc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interdoc); // Lier le fichier XML

        // Récupérer le bouton Get Started
        Button btnManageAppDoc = findViewById(R.id.button11);
        Button btnViewAppDoc = findViewById(R.id.button14);
        Button btnViewPatDoc = findViewById(R.id.button12);
        Button btnSendRemDoc = findViewById(R.id.button13);
        Button btnMnAvDoc = findViewById(R.id.button15);
        Button btnMnRecDoc = findViewById(R.id.button16);

        // Ajouter un événement clic pour lancer AuthentificationActivity
        btnManageAppDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention pour lancer AuthentificationActivity
                Intent intent = new Intent(Interfacedoc.this, ManageAppDoc.class);
                startActivity(intent); // Lancer l'activité
            }
        });

        btnViewAppDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention pour lancer AuthentificationActivity
                Intent intent = new Intent(Interfacedoc.this, ViewAppDoc.class);
                startActivity(intent); // Lancer l'activité
            }
        });

        btnViewPatDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention pour lancer AuthentificationActivity
                Intent intent = new Intent(Interfacedoc.this, ViewPatDoc.class);
                startActivity(intent); // Lancer l'activité
            }
        });

        btnSendRemDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention pour lancer AuthentificationActivity
                Intent intent = new Intent(Interfacedoc.this, SendRemDoc.class);
                startActivity(intent); // Lancer l'activité
            }
        });

        btnMnAvDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention pour lancer AuthentificationActivity
                Intent intent = new Intent(Interfacedoc.this, ManAvDoc.class);
                startActivity(intent); // Lancer l'activité
            }
        });

        btnMnRecDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention pour lancer AuthentificationActivity
                Intent intent = new Intent(Interfacedoc.this, ManRecDoc.class);
                startActivity(intent); // Lancer l'activité
            }
        });
    }
}
