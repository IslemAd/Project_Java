package com.example.myapplication01;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CalendarView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ManageAppPat extends AppCompatActivity {

    private CalendarView calendarView;
    private EditText appointmentDetails;
    private Button buttonAdd;
    private Button buttonModify;
    private Button buttonDelete;
    private Button buttonBackPat1;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manageapppat); // Assurez-vous que le nom du fichier XML est correct

        // Initialisation des composants
        calendarView = findViewById(R.id.calendarView);
        appointmentDetails = findViewById(R.id.appointmentDetails);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonModify = findViewById(R.id.buttonModify);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonBackPat1 = findViewById(R.id.buttonBackpat1);

        databaseHelper = new DatabaseHelper(this);

        // Écouteur pour l'ajout de rendez-vous
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String details = appointmentDetails.getText().toString().trim();
                long dateInMillis = calendarView.getDate();
                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(dateInMillis));
                String time = new SimpleDateFormat("HH:mm").format(new Date(dateInMillis)); // Placeholder pour l'heure
                String patientName = this.toString();

                if (details.isEmpty()) {
                    Toast.makeText(ManageAppPat.this, "Veuillez entrer les détails du rendez-vous", Toast.LENGTH_SHORT).show();
                } else {
                    // Vous devriez inclure la logique pour obtenir l'ID du médecin et du patient
                    int doctorId = 1; // Remplacez par l'ID réel du médecin
                    int patientId = 1; // Remplacez par l'ID réel du patient

                    boolean isInserted = databaseHelper.addAppointment(doctorId, patientId, date, time,patientName);
                    if (isInserted) {
                        Toast.makeText(ManageAppPat.this, "Rendez-vous ajouté avec succès", Toast.LENGTH_SHORT).show();
                        appointmentDetails.setText("");
                    } else {
                        Toast.makeText(ManageAppPat.this, "Erreur lors de l'ajout du rendez-vous", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Écouteur pour la modification de rendez-vous
        buttonModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ajoutez la logique pour récupérer l'ID du rendez-vous à modifier
                int appointmentId = 1; // Remplacez par l'ID réel
                String newDate = "2024-12-01"; // Remplacez par la nouvelle date
                String newTime = "14:30"; // Remplacez par la nouvelle heure

                boolean isUpdated = databaseHelper.updateAppointment(appointmentId, newDate, newTime);
                if (isUpdated) {
                    Toast.makeText(ManageAppPat.this, "Rendez-vous modifié avec succès", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ManageAppPat.this, "Erreur lors de la modification du rendez-vous", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Écouteur pour la suppression de rendez-vous
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ajoutez la logique pour récupérer l'ID du rendez-vous à supprimer
                int appointmentId = 1; // Remplacez par l'ID réel

                boolean isDeleted = databaseHelper.deleteAppointment(appointmentId);
                if (isDeleted) {
                    Toast.makeText(ManageAppPat.this, "Rendez-vous supprimé avec succès", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ManageAppPat.this, "Erreur lors de la suppression du rendez-vous", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Écouteur pour revenir à l'écran précédent
        buttonBackPat1.setOnClickListener(v -> {
            Intent intent = new Intent(ManageAppPat.this, Interfacepat.class);
            startActivity(intent);
        });
    }
}

