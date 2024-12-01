package com.example.myapplication01;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.List;

public class ViewAppDoc extends AppCompatActivity {

    private LinearLayout appointmentsContainer;
    private EditText searchAppointments;
    private List<Appointment> appointmentsList; // Liste des rendez-vous
    private int doctorId; // ID du médecin
    private DatabaseHelper dbHelper; // Instance de la base de données

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewappdoc);

        // Initialisation des composants
        appointmentsContainer = findViewById(R.id.appointmentsContainer);
        searchAppointments = findViewById(R.id.searchAppointments);
        Button btnBackDoc3 = findViewById(R.id.buttonBackdoc3);

        // Initialiser la base de données
        dbHelper = new DatabaseHelper(this);

        // Récupérer l'ID du médecin depuis l'Intent
        doctorId = getIntent().getIntExtra("DOCTOR_ID", 0);

        // Charger les rendez-vous depuis la base de données
        appointmentsList = loadAppointments();

        // Afficher les rendez-vous initiaux
        displayAppointments(appointmentsList);

        // Configurer le bouton Retour
        btnBackDoc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAppDoc.this, Interfacedoc.class);

                // Passez l'ID du médecin dans l'Intent
                intent.putExtra("doctorId", doctorId);

                startActivity(intent);
            }
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
     * Charge la liste des rendez-vous depuis la base de données.
     *
     * @return Liste des rendez-vous.
     */
    private List<Appointment> loadAppointments() {
        List<Appointment> appointments;

        try {
            // Charger les rendez-vous depuis la base de données pour le doctorId -1
            appointments = dbHelper.getAppointmentsByDoctorId(-1);

            if (appointments == null || appointments.isEmpty()) {
                // Si aucun rendez-vous n'est trouvé, afficher un message et retourner une liste vide
                Toast.makeText(this, "Aucun rendez-vous trouvé pour ce médecin (ID: -1).", Toast.LENGTH_SHORT).show();
                appointments = Collections.emptyList();
            }
        } catch (Exception e) {
            // Gérer les exceptions et afficher un message d'erreur
            Toast.makeText(this, "Erreur lors du chargement des rendez-vous : " + e.getMessage(), Toast.LENGTH_LONG).show();
            appointments = Collections.emptyList();
        }

        return appointments;
    }



    /**
     * Méthode pour afficher la liste des rendez-vous dans le conteneur.
     *
     * @param appointments Liste des rendez-vous à afficher.
     */
    private void displayAppointments(List<Appointment> appointments) {
        appointmentsContainer.removeAllViews(); // Efface les éléments existants

        if (appointments == null || appointments.isEmpty()) {
            // Afficher un message si aucune donnée
            TextView emptyMessage = new TextView(this);
            emptyMessage.setText("Aucun rendez-vous trouvé.");
            emptyMessage.setTextSize(16f);
            emptyMessage.setPadding(8, 8, 8, 8);
            emptyMessage.setGravity(Gravity.CENTER);
            appointmentsContainer.addView(emptyMessage);
            return;
        }

        for (Appointment appointment : appointments) {
            // Créer un conteneur pour chaque rendez-vous
            LinearLayout appointmentLayout = new LinearLayout(this);
            appointmentLayout.setOrientation(LinearLayout.VERTICAL);
            appointmentLayout.setPadding(16, 16, 16, 16);// Ajouter un style ou un fond

            // Ajouter les informations du rendez-vous
            TextView dateView = new TextView(this);
            dateView.setText("Date : " + appointment.getDate());
            dateView.setTextSize(16f);
            dateView.setPadding(0, 4, 0, 4);

            TextView timeView = new TextView(this);
            timeView.setText("Heure : " + appointment.getTime());
            timeView.setTextSize(16f);
            timeView.setPadding(0, 4, 0, 4);

            TextView detailsView = new TextView(this);
            detailsView.setText("Détails : " + appointment.getAppointmentId());
            detailsView.setTextSize(16f);
            detailsView.setPadding(0, 4, 0, 4);

            // Ajouter un gestionnaire d'événement au clic
            appointmentLayout.setOnClickListener(v -> {
                Toast.makeText(ViewAppDoc.this, "Rendez-vous sélectionné :\n" + appointment, Toast.LENGTH_SHORT).show();
            });

            // Ajouter les vues au conteneur
            appointmentLayout.addView(dateView);
            appointmentLayout.addView(timeView);
            appointmentLayout.addView(detailsView);

            // Ajouter une marge entre les rendez-vous
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0, 8, 0, 8);

            // Ajouter le conteneur principal au `appointmentsContainer`
            appointmentsContainer.addView(appointmentLayout, layoutParams);
        }
    }


    /**
     * Méthode pour filtrer les rendez-vous selon le texte saisi.
     *
     * @param query Texte de recherche.
     */
    private void filterAppointments(String query) {
        List<Appointment> filteredList = dbHelper.searchAppointmentsByDoctorId(doctorId, query);
        displayAppointments(filteredList); // Afficher les résultats filtrés
    }
}
