package com.example.myapplication01;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Signupsec extends AppCompatActivity {

    private EditText editTextUsername, editTextEmail, editTextPassword, editTextConfirmPassword;
    private Spinner spinnerSex, spinnerEducation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupsec);

        // Initialiser les vues
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        spinnerSex = findViewById(R.id.spinnerSex);
        spinnerEducation = findViewById(R.id.spinnerEducation);
        Button btnSignUpsec = findViewById(R.id.btnintersec);

        // Initialiser le DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Ajouter un événement clic pour le bouton d'inscription
        btnSignUpsec.setOnClickListener(v -> {
            // Récupérer les valeurs saisies
            String username = editTextUsername.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String confirmPassword = editTextConfirmPassword.getText().toString().trim();
            String sex = spinnerSex.getSelectedItem().toString();
            String education = spinnerEducation.getSelectedItem().toString();

            // Valider les champs
            if (TextUtils.isEmpty(username)) {
                editTextUsername.setError("Veuillez entrer votre nom");
                editTextUsername.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(email)) {
                editTextEmail.setError("Veuillez entrer votre email");
                editTextEmail.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTextEmail.setError("Veuillez entrer un email valide");
                editTextEmail.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                editTextPassword.setError("Veuillez entrer un mot de passe");
                editTextPassword.requestFocus();
                return;
            }
            if (password.length() < 6) {
                editTextPassword.setError("Le mot de passe doit comporter au moins 6 caractères");
                editTextPassword.requestFocus();
                return;
            }
            if (!password.equals(confirmPassword)) {
                editTextConfirmPassword.setError("Les mots de passe ne correspondent pas");
                editTextConfirmPassword.requestFocus();
                return;
            }
            if (sex.equals("Sélectionner un sexe")) {
                Toast.makeText(Signupsec.this, "Veuillez sélectionner un sexe", Toast.LENGTH_SHORT).show();
                return;
            }
            if (education.equals("Sélectionner un niveau d'éducation")) {
                Toast.makeText(Signupsec.this, "Veuillez sélectionner un niveau d'éducation", Toast.LENGTH_SHORT).show();
                return;
            }

            // Insérer les données dans la base
            boolean isInserted = databaseHelper.insertUser(username, email, password, "secretary", "", education, sex);
            if (isInserted) {
                Toast.makeText(Signupsec.this, "Inscription réussie", Toast.LENGTH_SHORT).show();

                // Lancer l'activité SignIn après inscription réussie
                Intent intent = new Intent(Signupsec.this, Signinsec.class);
                startActivity(intent);
                finish(); // Fermer l'activité actuelle
            } else {
                Toast.makeText(Signupsec.this, "Erreur lors de l'inscription. Réessayez.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
