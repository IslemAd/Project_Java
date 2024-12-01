package com.example.myapplication01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SendRemDoc extends AppCompatActivity {

    private Spinner patientSpinner;
    private DatePicker datePicker;
    private EditText reminderMessage;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendremdoc);

        // Initialiser les composants de l'interface
        Button btnSendReminder = findViewById(R.id.sendReminderButton);
        Button btnBackDoc4 = findViewById(R.id.buttonBackdoc4);
        patientSpinner = findViewById(R.id.patientSpinner);
        datePicker = findViewById(R.id.datePicker);
        reminderMessage = findViewById(R.id.reminderMessage);

        // Initialiser DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Action pour le bouton "Retour"
        btnBackDoc4.setOnClickListener(v -> {
            // Retour à l'écran Interfacedoc
            Intent intent = new Intent(SendRemDoc.this, Interfacedoc.class);
            startActivity(intent);
        });

        // Action pour le bouton "Envoyer le rappel"
        btnSendReminder.setOnClickListener(v -> {
            // Récupérer les données du formulaire
            String selectedPatient = patientSpinner.getSelectedItem().toString();
            String message = reminderMessage.getText().toString();
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1; // Mois commence à 0
            int year = datePicker.getYear();

            if (message.isEmpty()) {
                // Vérifier si le champ de message est vide
                Toast.makeText(SendRemDoc.this, "Veuillez entrer un message", Toast.LENGTH_SHORT).show();
            } else {
                // Envoi du rappel
                sendReminder(selectedPatient, day, month, year, message);
            }
        });
    }

    /**
     * Méthode pour envoyer un rappel
     * @param patient Nom du patient
     * @param day Jour sélectionné
     * @param month Mois sélectionné
     * @param year Année sélectionnée
     * @param message Message à envoyer
     */
    private void sendReminder(String patient, int day, int month, int year, String message) {
        // Convertir la date en format string
        String reminderDate = day + "/" + month + "/" + year;

        // Enregistrer le rappel dans la base de données
        boolean isInserted = dbHelper.insertReminder(patient, reminderDate, message);

        if (isInserted) {
            // Afficher un message de confirmation
            String confirmationMessage = "Rappel envoyé à " + patient +
                    " pour le " + reminderDate + " avec le message :\n" + message;
            Toast.makeText(this, confirmationMessage, Toast.LENGTH_LONG).show();
        } else {
            // Afficher un message d'erreur si l'insertion échoue
            Toast.makeText(this, "Erreur lors de l'envoi du rappel. Veuillez réessayer.", Toast.LENGTH_SHORT).show();
        }
    }
}
