package com.example.myapplication01;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewPatDoc extends AppCompatActivity {

    private static final String TAG = "ViewPatDoc";
    private LinearLayout patientsContainer; // Correct casing
    private EditText searchPatients;
    private List<String> patientsList; // Liste des patients récupérés depuis la base de données

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpatdoc);

        // Initialisation des composants avec des IDs corrects
        patientsContainer = findViewById(R.id.PatientContainer);
        searchPatients = findViewById(R.id.searchpat1);
        Button btnBackDoc2 = findViewById(R.id.buttonBackdoc2);

        // Charger les patients depuis la base de données
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        try {
            patientsList = dbHelper.getAllPatients();
            Log.d(TAG, "Patients récupérés : " + patientsList.size());
        } catch (Exception e) {
            Log.e(TAG, "Erreur lors de la récupération des patients", e);
            patientsList = new ArrayList<>(); // Gestion du cas où la DB est inaccessible
        }

        // Affichage initial des patients
        displayPatients(patientsList);

        // Configurer le bouton Retour
        btnBackDoc2.setOnClickListener(v -> {
            Intent intent = new Intent(ViewPatDoc.this, Interfacedoc.class);
            startActivity(intent);
        });

        // Ajouter un écouteur pour filtrer les patients à chaque modification du texte
        searchPatients.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterPatients(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    /**
     * Méthode pour afficher les patients dans le conteneur.
     *
     * @param PATIENT Liste des patients à afficher.
     */
    private void displayPatients(List<String> PATIENT) {
        Log.d(TAG, "Affichage de " + PATIENT.size() + " patients.");
        patientsContainer.removeAllViews(); // Nettoyer les patients affichés

        if (PATIENT.isEmpty()) {
            TextView emptyMessage = new TextView(this);
            emptyMessage.setText("Aucun patient trouvé.");
            emptyMessage.setTextSize(16f);
            emptyMessage.setPadding(8, 8, 8, 8);
            patientsContainer.addView(emptyMessage);
        } else {
            for (String patient : PATIENT) {
                TextView textView = new TextView(this);
                textView.setText(patient);
                textView.setTextSize(18f);
                textView.setPadding(8, 8, 8, 8);

                // Ajouter chaque patient au conteneur
                patientsContainer.addView(textView);
            }
        }
    }

    /**
     * Méthode pour filtrer les patients par mot-clé.
     *
     * @param query Texte saisi dans la barre de recherche.
     */
    private void filterPatients(String query) {
        List<String> filteredList = new ArrayList<>();
        for (String patient : patientsList) {
            if (patient.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(patient);
            }
        }
        Log.d(TAG, "Liste filtrée : " + filteredList.size() + " résultats.");
        displayPatients(filteredList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadPatients();
    }

    private void reloadPatients() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        try {
            patientsList = dbHelper.getAllPatients();
            Log.d(TAG, "Patients rechargés : " + patientsList.size());
            displayPatients(patientsList);
        } catch (Exception e) {
            Log.e(TAG, "Erreur lors du rechargement des patients", e);
        }
    }
}
