package com.example.tolvas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class SectorReportes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_reportes_zonas);
        MaterialButton z1=findViewById(R.id.botonz1);
        MaterialButton z2=findViewById(R.id.botonz2);
        MaterialButton z3=findViewById(R.id.botonz3);
        MaterialButton z4=findViewById(R.id.botonz4);
        MaterialButton z5=findViewById(R.id.botonz5);
        MaterialButton z6=findViewById(R.id.botonz6);
        MaterialButton z7=findViewById(R.id.botonz7);
        MaterialButton z8=findViewById(R.id.botonz8);

        z1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SectorReportes.this, ReporteSector1.class);
                startActivity(intent);
            }
        });
        z2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SectorReportes.this, ReporteSector2.class);
                startActivity(intent);
            }
        });
        z3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SectorReportes.this, ReporteSector3.class);
                startActivity(intent);
            }
        });
        z4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SectorReportes.this, ReporteSector4.class);
                startActivity(intent);
            }
        });
        z5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SectorReportes.this, ReporteSector5.class);
                startActivity(intent);
            }
        });
        z6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SectorReportes.this, ReporteSector6.class);
                startActivity(intent);
            }
        });
        z7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SectorReportes.this, ReporteSector7.class);
                startActivity(intent);
            }
        });
        z8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SectorReportes.this, ReporteSector8.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        // Aquí puedes agregar cualquier lógica que necesites antes de cerrar la actividad
        // Por ejemplo, mostrar un mensaje o realizar limpieza
        super.onBackPressed(); // Esto cerrará la actividad actual
    }

}