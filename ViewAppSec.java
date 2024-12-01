package com.example.myapplication01;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewAppSec extends AppCompatActivity {

    private LinearLayout appointmentsContainer;
    private EditText searchAppointments;
    private List<String> appointmentsList; // Liste des rendez-vous

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewappsec);

        // Initialisation des composants
        appointmentsContainer = findViewById(R.id.appointmentsContainer);
        searchAppointments = findViewById(R.id.searchAppointments);
        Button btnBackSec1 = findViewById(R.id.buttonBack1sec);

        // Initialiser la liste des rendez-vous (données fictives ou dynamiques)
        appointmentsList = loadAppointments();

        // Afficher les rendez-vous initiaux
        displayAppointments(appointmentsList);

        // Configurer le bouton Retour
        btnBackSec1.setOnClickListener(v -> {
            Intent intent = new Intent(ViewAppSec.this, Interfacesec.class);
            startActivity(intent);
        });

        // Configurer la barre de recherche
        searchAppointments.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterAppointments(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    /**
     * Charge la liste des rendez-vous (fictive ou depuis une base de données).
     * Remplacez cette méthode par une récupération dynamique si nécessaire.
     *
     * @return Liste des rendez-vous.
     */
    private List<String> loadAppointments() {
        List<String> appointments = new ArrayList<>();
        // Données fictives
        appointments.add("Rendez-vous avec M. Dupont à 10h00");
        appointments.add("Rendez-vous avec Mme Durand à 11h30");
        appointments.add("Rendez-vous avec M. Martin à 14h00");
        appointments.add("Rendez-vous avec Mme Petit à 16h00");
        return appointments;
    }

    /**
     * Méthode pour afficher la liste des rendez-vous dans le conteneur.
     *
     * @param appointments Liste des rendez-vous à afficher.
     */
    private void displayAppointments(List<String> appointments) {
        appointmentsContainer.removeAllViews(); // Efface les éléments existants

        for (String appointment : appointments) {
            TextView textView = new TextView(this);
            textView.setText(appointment);
            textView.setTextSize(18f);
            textView.setPadding(8, 8, 8, 8);

            textView.setOnClickListener(v -> {
                // Show a Toast with the appointment details
                Toast.makeText(ViewAppSec.this, "Details: " + appointment, Toast.LENGTH_SHORT).show();
            });


            // Ajouter chaque rendez-vous au conteneur
            appointmentsContainer.addView(textView);
        }

        // Si aucune donnée, afficher un message
        if (appointments.isEmpty()) {
            TextView emptyMessage = new TextView(this);
            emptyMessage.setText("Aucun rendez-vous trouvé.");
            emptyMessage.setTextSize(16f);
            emptyMessage.setPadding(8, 8, 8, 8);
            appointmentsContainer.addView(emptyMessage);
        }
    }

    /**
     * Méthode pour filtrer les rendez-vous selon le texte saisi.
     *
     * @param query Texte de recherche.
     */
    private void filterAppointments(String query) {
        List<String> filteredList = new ArrayList<>();
        for (String appointment : appointmentsList) {
            if (appointment.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(appointment);
            }
        }
        displayAppointments(filteredList); // Afficher les résultats filtrés
    }
}
