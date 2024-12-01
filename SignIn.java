package com.example.myapplication01;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignIn extends AppCompatActivity {

    private EditText editEmailsigninpat, editPasswordsignpat;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);  // Assurez-vous que le nom du layout correspond à celui de votre fichier XML

        // Initialisation des vues
        editEmailsigninpat = findViewById(R.id.Emailsigninpat);
        editPasswordsignpat = findViewById(R.id.editPasswordsignpat);
        Button btnSignIn = findViewById(R.id.buttonsg);

        // Initialisation du DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Listener pour le bouton de connexion
        btnSignIn.setOnClickListener(v -> {
            String email = editEmailsigninpat.getText().toString().trim();
            String password = editPasswordsignpat.getText().toString().trim();

            // Vérifier que les champs ne sont pas vides
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(SignIn.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else {
                // Vérifier les identifiants dans la base de données
                boolean isValidUser = databaseHelper.checkUserCredentials(email, password);
                if (isValidUser) {
                    // Si l'utilisateur est valide, démarrer l'activité principale
                    Toast.makeText(SignIn.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignIn.this, Interfacepat.class));
                    finish(); // Fermer l'écran de connexion
                } else {
                    // Si les identifiants sont incorrects
                    Toast.makeText(SignIn.this, "Identifiants incorrects", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
