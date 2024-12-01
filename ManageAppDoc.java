package com.example.myapplication01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ManageAppDoc extends AppCompatActivity {

    private Button btnBackDoc, btnDeleteAppointment, btnAddAppointment, btnModifyAppointment;
    private EditText appointmentDetails;
    private CalendarView calendarView;

    private DatabaseHelper dbHelper;
    private String selectedDate; // Stocker la date sélectionnée par le médecin
    public int doctorId; // ID du médecin connecté ou sélectionné

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manageappdoc);

        // Initialiser les vues
        btnBackDoc = findViewById(R.id.buttonBackdoc);
        btnDeleteAppointment = findViewById(R.id.buttonDelete);
        btnAddAppointment = findViewById(R.id.buttonAdd);
        btnModifyAppointment = findViewById(R.id.buttonModify);
        appointmentDetails = findViewById(R.id.appointmentDetails);
        calendarView = findViewById(R.id.calendarView);

        // Initialiser la base de données
        dbHelper = new DatabaseHelper(this);

        // Récupérer l'ID du médecin connecté (vous pouvez le passer via un Intent ou autre mécanisme)
        doctorId = getIntent().getIntExtra("DOCTOR_ID", -1);

        // Initialiser la date sélectionnée par défaut
        selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // Gérer les changements de date dans le calendrier
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
        });

        // Bouton Retour
        btnBackDoc.setOnClickListener(v -> {
            Intent intent = new Intent(ManageAppDoc.this, Interfacedoc.class);
            startActivity(intent);
        });

        // Bouton Ajouter un rendez-vous
        btnAddAppointment.setOnClickListener(v -> {
            String details = appointmentDetails.getText().toString().trim();

            if (!details.isEmpty()) {

                String appointmentDate = this.selectedDate;
                String appointmentTime = "14:30";
                String patientName = "this patient";// Par exemple, fixe pour cet exemple

                int patientId = 2; // À remplacer avec le patient sélectionné
                boolean isAdded = dbHelper.addAppointment(doctorId, patientId, appointmentDate, appointmentTime, patientName);


                if (isAdded) {
                    Toast.makeText(this, "Rendez-vous ajouté avec succès", Toast.LENGTH_SHORT).show();
                    updateViewAppDoc(); // Mettre à jour la liste
                } else {
                    Toast.makeText(this, "Erreur lors de l'ajout du rendez-vous", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Veuillez entrer des détails pour le rendez-vous", Toast.LENGTH_SHORT).show();
            }

        });

        // Bouton Modifier un rendez-vous
        btnModifyAppointment.setOnClickListener(v -> {
            String details = appointmentDetails.getText().toString().trim();
            if (!details.isEmpty()) {
                int appointmentId = 1; // ID fictif à remplacer par l'ID réel
                boolean isUpdated = dbHelper.updateAppointment(appointmentId, selectedDate, details);
                if (isUpdated) {
                    Toast.makeText(this, "Rendez-vous modifié avec succès", Toast.LENGTH_SHORT).show();
                    updateViewAppDoc(); // Mettre à jour la liste
                } else {
                    Toast.makeText(this, "Erreur lors de la modification du rendez-vous", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Veuillez entrer des détails pour le rendez-vous", Toast.LENGTH_SHORT).show();
            }
        });

        // Bouton Supprimer un rendez-vous
        btnDeleteAppointment.setOnClickListener(v -> {
            int appointmentId = 1; // ID fictif à remplacer par l'ID réel
            boolean isDeleted = dbHelper.deleteAppointment(appointmentId);
            if (isDeleted) {
                Toast.makeText(this, "Rendez-vous supprimé avec succès", Toast.LENGTH_SHORT).show();
                updateViewAppDoc(); // Mettre à jour la liste
            } else {
                Toast.makeText(this, "Aucun rendez-vous à supprimer", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Méthode pour notifier l'interface ViewAppDoc de mettre à jour la liste des rendez-vous.
     */
    private void updateViewAppDoc() {
        Intent intent = new Intent(ManageAppDoc.this, ViewAppDoc.class);
        intent.putExtra("DOCTOR_ID", doctorId); // Passer l'ID du médecin si nécessaire
        startActivity(intent);
    }
}



