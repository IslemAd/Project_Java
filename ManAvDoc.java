package com.example.myapplication01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ManAvDoc extends AppCompatActivity {

    private CheckBox checkboxMonday, checkboxTuesday, checkboxWednesday, checkboxThursday, checkboxFriday, checkboxSaturday;
    private TimePicker timePickerStart, timePickerEnd;
    private Button saveAvailabilityButton, btnBackToInterface;

    private DatabaseHelper databaseHelper; // Instance de la base de données
    private int doctorId = 1; // Remplacez par une valeur dynamique si nécessaire

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manageavaildoc);

        checkboxMonday = findViewById(R.id.checkboxMonday);
        checkboxTuesday = findViewById(R.id.checkboxTuesday);
        checkboxWednesday = findViewById(R.id.checkboxWednesday);
        checkboxThursday = findViewById(R.id.checkboxThursday);
        checkboxFriday = findViewById(R.id.checkboxFriday);
        checkboxSaturday = findViewById(R.id.checkboxSaturday);
        timePickerStart = findViewById(R.id.timePickerStart);
        timePickerEnd = findViewById(R.id.timePickerEnd);
        saveAvailabilityButton = findViewById(R.id.saveAvailabilityButton);
        btnBackToInterface = findViewById(R.id.btnbackinter);

        databaseHelper = new DatabaseHelper(this);

        saveAvailabilityButton.setOnClickListener(v -> saveAvailability());

        btnBackToInterface.setOnClickListener(v -> goBackToInterface());
    }

    private void saveAvailability() {
        List<String> selectedDays = new ArrayList<>();

        if (checkboxMonday.isChecked()) {
            selectedDays.add("Monday");
        }
        if (checkboxTuesday.isChecked()) {
            selectedDays.add("Tuesday");
        }
        if (checkboxWednesday.isChecked()) {
            selectedDays.add("Wednesday");
        }
        if (checkboxThursday.isChecked()) {
            selectedDays.add("Thursday");
        }
        if (checkboxFriday.isChecked()) {
            selectedDays.add("Friday");
        }
        if (checkboxSaturday.isChecked()) {
            selectedDays.add("Saturday");
        }

        int startHour = timePickerStart.getCurrentHour();
        int startMinute = timePickerStart.getCurrentMinute();
        int endHour = timePickerEnd.getCurrentHour();
        int endMinute = timePickerEnd.getCurrentMinute();

        boolean success = true;
        for (String day : selectedDays) {
            String startTime = String.format("%02d:%02d", startHour, startMinute);
            String endTime = String.format("%02d:%02d", endHour, endMinute);

            // Enregistrer les disponibilités dans la base de données
            boolean result = databaseHelper.insertAvailability(doctorId, day, startTime, endTime);
            if (!result) {
                success = false;
            }
        }

        if (success) {
            Toast.makeText(this, "Disponibilités enregistrées avec succès.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Erreur lors de l'enregistrement des disponibilités.", Toast.LENGTH_LONG).show();
        }
    }

    private void goBackToInterface() {
        Intent intent = new Intent(ManAvDoc.this, Interfacedoc.class);
        startActivity(intent);
    }
}
