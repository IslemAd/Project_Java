package com.example.myapplication01; // Remplacez par votre package

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ManRecDoc extends AppCompatActivity {

    private Button btnBackToMain, btnAddRecord, btnUpdateRecord;
    private EditText editMedicalRecordDetails;
    private Spinner patientSpinner;
    private DatabaseHelper dbHelper; // Instance de la base de données
    private int patientId;
    private String patientName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.managerecdoc); // Lier le fichier XML

        // Initialiser les éléments de l'interface
        btnBackToMain = findViewById(R.id.buttonBackToMain);
        btnAddRecord = findViewById(R.id.buttonAddRecord);
        btnUpdateRecord = findViewById(R.id.buttonUpdateRecord);
        editMedicalRecordDetails = findViewById(R.id.medicalRecordDetails);
        patientSpinner = findViewById(R.id.patientSpinner);

        // Initialisation de la base de données
        dbHelper = new DatabaseHelper(this);

        // Action pour le bouton "Retour"
        btnBackToMain.setOnClickListener(v -> {
            // Retour à l'écran Interfacedoc
            Intent intent = new Intent(ManRecDoc.this, Interfacedoc.class);
            startActivity(intent); // Lancer l'activité
        });

        // Action pour le bouton "Ajouter un dossier médical"
        btnAddRecord.setOnClickListener(v -> {
            // Ajouter un dossier médical
            String patientName = patientSpinner.getSelectedItem().toString();
            String recordDetails = editMedicalRecordDetails.getText().toString();

            // Logique pour ajouter le dossier médical
            if (!recordDetails.isEmpty()) {
                // Ajouter le dossier médical dans la base de données
                int patientId = getPatientIdFromSpinner(patientName);
                boolean isInserted = dbHelper.addMedicalRecord(patientId, recordDetails);

                if (isInserted) {
                    // Afficher un message de confirmation
                    Toast.makeText(ManRecDoc.this, "Dossier médical ajouté pour " + patientName, Toast.LENGTH_SHORT).show();
                } else {
                    // Afficher un message d'erreur si l'ajout échoue
                    Toast.makeText(ManRecDoc.this, "Erreur lors de l'ajout du dossier médical", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Afficher un message d'erreur si le champ est vide
                Toast.makeText(ManRecDoc.this, "Veuillez entrer les détails du dossier médical", Toast.LENGTH_SHORT).show();
            }
        });

        // Action pour le bouton "Mettre à jour un dossier médical"
        btnUpdateRecord.setOnClickListener(v -> {
            // Mettre à jour un dossier médical
            String patientName = patientSpinner.getSelectedItem().toString();
            String recordDetails = editMedicalRecordDetails.getText().toString();

            // Logique pour mettre à jour le dossier médical
            if (!recordDetails.isEmpty()) {
                // Mettre à jour le dossier médical dans la base de données
                int patientId = getPatientIdFromSpinner(patientName);
                int recordId = getRecordIdFromPatient(patientId); // Méthode pour récupérer l'ID du dossier médical

                boolean isUpdated = dbHelper.updateMedicalRecord(recordId, recordDetails);

                if (isUpdated) {
                    // Afficher un message de confirmation
                    Toast.makeText(ManRecDoc.this, "Dossier médical mis à jour pour " + patientName, Toast.LENGTH_SHORT).show();
                } else {
                    // Afficher un message d'erreur si la mise à jour échoue
                    Toast.makeText(ManRecDoc.this, "Erreur lors de la mise à jour du dossier médical", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Afficher un message d'erreur si le champ est vide
                Toast.makeText(ManRecDoc.this, "Veuillez entrer les détails du dossier médical", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Méthode pour obtenir l'ID du patient à partir du nom sélectionné dans le spinner
    private int getPatientIdFromSpinner(String patientName) {
        this.patientName = patientName;
        // Vous devez implémenter une méthode qui récupère l'ID du patient en fonction du nom
        return 1; // Remplacez par la logique pour obtenir l'ID réel
    }

    // Méthode pour récupérer l'ID du dossier médical à partir du patient
    private int getRecordIdFromPatient(int patientId) {
        this.patientId = patientId;
        // Vous devez récupérer l'ID du dossier médical lié au patient (si plusieurs dossiers existent, vous devrez probablement les afficher pour sélection)
        return 1; // Remplacez par la logique réelle pour récupérer le dossier médical
    }
}
