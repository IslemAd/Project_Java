package com.example.myapplication01;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    private EditText editTextUsername, editTextEmail, editTextDOB, editTextPassword, editTextConfirmPassword;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup); // Lier le fichier XML

        // Initialiser les vues
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextDOB = findViewById(R.id.editTextDOB);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        Button btnSignUp = findViewById(R.id.buttonpat); // Assurez-vous que l'ID du bouton est correct

        // Initialiser le DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Ajouter un événement clic pour le bouton d'inscription
        btnSignUp.setOnClickListener(v -> {
            // Récupérer les valeurs saisies
            String username = editTextUsername.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String dob = editTextDOB.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String confirmPassword = editTextConfirmPassword.getText().toString().trim();

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
            } else {
                // Insérer les données dans la base pour un patient (pas de spécialité ici)
                boolean isInserted = databaseHelper.insertUser(username, email, password, "patient", "", "", ""); // Rôle patient sans spécialité
                if (isInserted) {
                    Toast.makeText(SignUp.this, "Inscription réussie", Toast.LENGTH_SHORT).show();

                    // Lancer l'activité SignIn après inscription réussie
                    Intent intent = new Intent(SignUp.this, SignIn.class); // Rediriger vers SignIn pour les patients
                    startActivity(intent);
                    finish(); // Fermer l'activité actuelle
                } else {
                    Toast.makeText(SignUp.this, "Erreur lors de l'inscription. Réessayez.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
