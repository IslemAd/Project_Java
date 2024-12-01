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

public class ManageAppSec extends AppCompatActivity {

    private Button btnBackDoc, btnDeleteAppointment, btnAddAppointment, btnModifyAppointment;
    private EditText appointmentDetails;
    private CalendarView calendarView;

    private DatabaseHelper dbHelper;
    private String selectedDate; // Stocker la date sélectionnée par le médecin

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

        // Initialiser la date sélectionnée par défaut
        selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // Gérer les changements de date dans le calendrier
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
        });

        // Bouton Retour
        btnBackDoc.setOnClickListener(v -> {
            Intent intent = new Intent(ManageAppSec.this, Interfacesec.class);
            startActivity(intent);
        });

        // Bouton Ajouter un rendez-vous
        btnAddAppointment.setOnClickListener(v -> {
            // Récupérer les détails du rendez-vous
            String details = appointmentDetails.getText().toString().trim();

            // Vérifiez si les détails ne sont pas vides
            if (!details.isEmpty()) {
                // Récupérer la date et l'heure du rendez-vous (par exemple depuis un picker ou un autre champ)
                String appointmentDate = selectedDate;  // Assurez-vous que selectedDate contient une valeur valide
                String appointmentTime = "14:30"; // Par exemple, l'heure peut être récupérée à partir d'un autre champ de l'UI
                String patientName = this.toString();

                // Supposons que vous avez l'ID du médecin et de l'utilisateur patient
                int doctorId = 1;  // Remplacez-le par l'ID réel du médecin sélectionné
                int patientId = 2; // Remplacez-le par l'ID réel du patient sélectionné

                // Ajouter le rendez-vous dans la base de données
                boolean isAdded = dbHelper.addAppointment(doctorId, patientId, appointmentDate, appointmentTime, patientName);

                if (isAdded) {
                    // Afficher un message de succès
                    Toast.makeText(this, "Rendez-vous ajouté avec succès", Toast.LENGTH_SHORT).show();
                } else {
                    // Afficher un message d'erreur
                    Toast.makeText(this, "Erreur lors de l'ajout du rendez-vous", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Si les détails sont vides, afficher un message d'erreur
                Toast.makeText(this, "Veuillez entrer des détails pour le rendez-vous", Toast.LENGTH_SHORT).show();
            }
        });


        // Bouton Modifier un rendez-vous
        btnModifyAppointment.setOnClickListener(v -> {
            String details = appointmentDetails.getText().toString().trim();
            if (!details.isEmpty()) {
                // Exemple : modification du rendez-vous en fonction de son ID (à adapter selon votre logique)
                boolean isUpdated = dbHelper.updateAppointment(1, selectedDate, details);
                if (isUpdated) {
                    Toast.makeText(this, "Rendez-vous modifié avec succès", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Erreur lors de la modification du rendez-vous", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Veuillez entrer des détails pour le rendez-vous", Toast.LENGTH_SHORT).show();
            }
        });

        // Bouton Supprimer un rendez-vous
        btnDeleteAppointment.setOnClickListener(v -> {
            // Exemple : suppression du rendez-vous en fonction de son ID (à adapter selon votre logique)
            boolean isDeleted = dbHelper.deleteAppointment(1); // ID fictif
            if (isDeleted) {
                Toast.makeText(this, "Rendez-vous supprimé avec succès", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Aucun rendez-vous à supprimer", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
