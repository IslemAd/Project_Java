package com.example.myapplication01;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewPatSec extends AppCompatActivity {

    private LinearLayout PatContainer; // Nom corrigé pour correspondre au findViewById
    private EditText searchpatient;

    // Liste des patients récupérée depuis la base de données
    private List<String> patientsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpatsec);

        // Initialisation des composants
        PatContainer = findViewById(R.id.PatContainer);
        searchpatient = findViewById(R.id.searchpatient);
        Button btnBackSec = findViewById(R.id.buttonBacksec);

        // Initialisation de la liste des patients depuis la base de données
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        patientsList = dbHelper.getAllPatients();  // Récupère les patients depuis la base de données

        // Afficher les patients au démarrage
        displayPatients(patientsList);

        // Action sur le bouton retour
        btnBackSec.setOnClickListener(v -> {
            Intent intent = new Intent(ViewPatSec.this, Interfacesec.class);
            startActivity(intent);
        });

        // Écoute des changements dans la barre de recherche
        searchpatient.addTextChangedListener(new TextWatcher() {
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
     * Affiche une liste de patients dans le conteneur.
     * @param patients Liste des patients à afficher
     */
    private void displayPatients(List<String> patients) {
        PatContainer.removeAllViews(); // Nettoyer l'affichage précédent

        if (patients == null || patients.isEmpty()) {
            // Afficher un message si la liste est vide
            TextView noResults = new TextView(this);
            noResults.setText("Aucun patient trouvé.");
            noResults.setTextSize(16f);
            noResults.setPadding(8, 8, 8, 8);
            PatContainer.addView(noResults);
        } else {
            // Ajouter chaque patient au conteneur
            for (String patient : patients) {
                TextView patientView = new TextView(this);
                patientView.setText(patient);
                patientView.setTextSize(18f);
                patientView.setPadding(8, 8, 8, 8);
                PatContainer.addView(patientView);
            }
        }
    }

    /**
     * Filtre les patients en fonction d'une requête de recherche.
     * @param query Texte de la recherche
     */
    private void filterPatients(String query) {
        List<String> filteredList = new ArrayList<>();
        for (String patient : patientsList) {
            if (patient.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(patient);
            }
        }
        displayPatients(filteredList);
    }
}
