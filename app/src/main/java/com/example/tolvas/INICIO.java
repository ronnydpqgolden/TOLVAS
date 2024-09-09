package com.example.tolvas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class INICIO extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        MaterialButton botonActualizar = findViewById(R.id.btnUpdate);
        MaterialButton botonIngresar = findViewById(R.id.btnIng);
        MaterialButton botonRevisar = findViewById(R.id.btnRev);


        botonIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(INICIO.this, SectoresIngreso.class);
                startActivity(intent);
            }
        });
        botonRevisar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(INICIO.this, SectorReportes.class);
                startActivity(intent);
            }
        });
        botonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // URL de la carpeta en Google Drive
                String folderUrl = "https://drive.google.com/drive/folders/12leJoA6tn_dWadc4vwpjllU0SVpbxnKU?usp=share_link";

                // Crea una intención para abrir el navegador
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(folderUrl));

                // Inicia la actividad
                startActivity(browserIntent);
            }
        });




    }
}