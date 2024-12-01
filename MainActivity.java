package com.example.myapplication01;

import android.content.Intent; // Import nécessaire pour utiliser Inten
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Refers to a layout resource defined in XML

        // Récupérer le bouton Get Started
        Button btnGetStarted = findViewById(R.id.button1);
        // Ajouter un événement clic pour lancer IdentityActivity
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention pour lancer IdentityActivity
                Intent intent = new Intent(MainActivity.this, IdentityActivity.class);
                startActivity(intent); // Lancer l'activité
            }
        });
    }
}

