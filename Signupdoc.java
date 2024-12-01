package com.example.myapplication01;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Signupdoc extends AppCompatActivity {

    private EditText editTextUsername, editTextEmail, editTextDOB, editTextPassword, editTextConfirmPassword;
    private Spinner spinnerSpecialty; // Spinner pour sélectionner la spécialité
    private Button btnSignUpdoc;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupdoc); // Lier le fichier XML

        // Initialiser les vues
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextDOB = findViewById(R.id.editTextDOB);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        spinnerSpecialty = findViewById(R.id.spinnerSpecialty); // Récupérer le spinner pour la spécialité
        btnSignUpdoc = findViewById(R.id.button2);

        // Initialiser le DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Ajouter un événement clic pour le bouton d'inscription
        btnSignUpdoc.setOnClickListener(v -> {
            // Récupérer les valeurs saisies
            String username = editTextUsername.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String dob = editTextDOB.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String confirmPassword = editTextConfirmPassword.getText().toString().trim();
            String specialty = spinnerSpecialty.getSelectedItem().toString(); // Récupérer la spécialité sélectionnée

            // Valider les champs
            if (TextUtils.isEmpty(username)) {
                editTextUsername.setError("Veuillez entrer votre nom");
                editTextUsername.requestFocus();
            } else if (TextUtils.isEmpty(email)) {
                editTextEmail.setError("Veuillez entrer votre email");
                editTextEmail.requestFocus();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTextEmail.setError("Veuillez entrer un email valide");
                editTextEmail.requestFocus();
            } else if (TextUtils.isEmpty(dob)) {
                editTextDOB.setError("Veuillez entrer votre date de naissance");
                editTextDOB.requestFocus();
            } else if (TextUtils.isEmpty(password)) {
                editTextPassword.setError("Veuillez entrer un mot de passe");
                editTextPassword.requestFocus();
            } else if (password.length() < 6) {
                editTextPassword.setError("Le mot de passe doit comporter au moins 6 caractères");
                editTextPassword.requestFocus();
            } else if (!password.equals(confirmPassword)) {
                editTextConfirmPassword.setError("Les mots de passe ne correspondent pas");
                editTextConfirmPassword.requestFocus();
            } else if (specialty.equals("Sélectionner une spécialité")) {
                Toast.makeText(Signupdoc.this, "Veuillez sélectionner une spécialité", Toast.LENGTH_SHORT).show();
            } else {
                // Insérer les données dans la base
                boolean isInserted = databaseHelper.insertUser(username, email, password, "doctor", specialty, "", ""); // Insérer avec spécialité
                if (isInserted) {
                    Toast.makeText(Signupdoc.this, "Inscription réussie", Toast.LENGTH_SHORT).show();

                    // Lancer l'activité SignIn après inscription réussie
                    Intent intent = new Intent(Signupdoc.this, Signindoc.class);
                    startActivity(intent); // Lancer l'activité
                    //finish(); // Fermer l'activité actuelle
                } else {
                    Toast.makeText(Signupdoc.this, "Erreur lors de l'inscription. Réessayez.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
