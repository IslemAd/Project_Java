package com.example.myapplication01;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Signindoc extends AppCompatActivity {

    private EditText editEmailsignindoc, editPasswordsignindoc;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signindoc);

        // Initialiser les vues
        editEmailsignindoc = findViewById(R.id.Emailsignindoc);
        editPasswordsignindoc = findViewById(R.id.Passwordsignindoc);
        Button btnSignIndoc2 = findViewById(R.id.btnsignindoc);

        // Initialiser la base de données
        databaseHelper = new DatabaseHelper(this);

        // Action lors du clic sur le bouton
        btnSignIndoc2.setOnClickListener(v -> {
            String email = editEmailsignindoc.getText().toString().trim();
            String password = editPasswordsignindoc.getText().toString().trim();

            // Vérification des champs
            if (TextUtils.isEmpty(email)) {
                editEmailsignindoc.setError("Veuillez entrer votre email");
                editEmailsignindoc.requestFocus();
            } else if (TextUtils.isEmpty(password)) {
                editPasswordsignindoc.setError("Veuillez entrer votre mot de passe");
                editPasswordsignindoc.requestFocus();
            } else {
                // Vérifier les identifiants dans la base de données
                boolean isValid = checkDoctor(email, password);

                if (isValid) {
                    // Identifiants valides
                    Toast.makeText(Signindoc.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Signindoc.this, Interfacedoc.class);
                    startActivity(intent);
                   // finish();
                } else {
                    // Identifiants incorrects
                    Toast.makeText(Signindoc.this, "Identifiants incorrects", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Vérifie si un utilisateur existe dans la base de données.
     */
    private boolean checkDoctor(String email, String password) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_USERS +
                " WHERE " + DatabaseHelper.COLUMN_EMAIL + " = ? AND " + DatabaseHelper.COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return exists;
    }
}
