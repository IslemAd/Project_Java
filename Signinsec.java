package com.example.myapplication01;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Signinsec extends AppCompatActivity {

    private EditText Emailsigninsec, Passwordsigninsec;
    private DatabaseHelper databaseHelper; // Ajout du helper pour interagir avec la base de données

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signinsec);

        // Initialisation des vues
        Emailsigninsec = findViewById(R.id.Emailsigninsec);
        Passwordsigninsec = findViewById(R.id.Passwordsigninsec);
        Button btnSignInsec2 = findViewById(R.id.btnsec2);

        // Initialisation du DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Action lors du clic sur le bouton
        btnSignInsec2.setOnClickListener(v -> {
            String email = Emailsigninsec.getText().toString().trim();
            String password = Passwordsigninsec.getText().toString().trim();

            // Vérification des champs
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(Signinsec.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else {
                // Vérifier les identifiants dans la base de données
                boolean isValidUser = databaseHelper.checkUserCredentials(email, password);
                if (isValidUser) {
                    // Si l'utilisateur est valide, démarrer l'activité principale
                    Toast.makeText(Signinsec.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Signinsec.this, Interfacesec.class));
                    finish(); // Fermer l'écran de connexion
                } else {
                    // Si les identifiants sont incorrects
                    Toast.makeText(Signinsec.this, "Identifiants incorrects", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
