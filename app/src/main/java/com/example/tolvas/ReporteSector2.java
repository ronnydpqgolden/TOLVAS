package com.example.tolvas;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ReporteSector2 extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    EditText editTextDate;
    Calendar calendar;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    WebView webView;
    private String selectedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_sector);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Piscina 9");

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        webView = findViewById(R.id.webView1);

        actualizarDatosDesdeFirebase();

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Habilita JavaScript si es necesario
        webSettings.setBuiltInZoomControls(true); // Habilita los controles de zoom
        webSettings.setDisplayZoomControls(false); // Opcional: oculta los controles de zoom en pantalla (solo se permite el zoom con pellizco)

        // Opcional: Configuración adicional para mejorar la compatibilidad y la usabilidad
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);


        swipeRefreshLayout.setOnRefreshListener(() -> {
            refreshWebView();
            loadFirebaseData();
        });



        editTextDate = findViewById(R.id.editTextDate);
        calendar = Calendar.getInstance();


        updateDateLabel();

        editTextDate.setOnClickListener(v -> showDatePickerDialog());

        loadFirebaseData();
    }

    private void actualizarDatosDesdeFirebase() {
        // Lista de referencias a las piscinas
        List<String> piscinas = Arrays.asList("Piscina 9", "Piscina 10A", "Piscina 10B", "Piscina 11", "Piscina 12", "Piscina 13", "Piscina 14", "Piscina 15");

        for (String piscina : piscinas) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(piscina);

            // Consulta a Firebase
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Procesa los datos obtenidos
                    Map<String, Object> datos = (Map<String, Object>) dataSnapshot.getValue();
                    if (datos != null) {
                        // Actualiza la interfaz de usuario o maneja los datos

                    } else {
                        Log.w("FirebaseWarning", "No hay datos disponibles para " + piscina);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Maneja los errores
                    Log.e("FirebaseError", "Error al leer los datos de " + piscina, databaseError.toException());
                }
            });
        }
    }

    private void refreshWebView() {
        // Aquí puedes actualizar el contenido de tu WebView
        webView.reload();
        loadFirebaseData();
        // Termina el estado de actualización cuando el contenido esté listo
        swipeRefreshLayout.setRefreshing(false);
    }

    private void loadFirebaseData() {
        String formattedDate = selectedDate; // Usa el formato de fecha que tienes en Firebase
        DatabaseReference piscina9Ref = databaseReference.child("Piscina 9");
        DatabaseReference piscina10ARef = databaseReference.child("Piscina 10A");
        DatabaseReference piscina10BRef = databaseReference.child("Piscina 10B");
        DatabaseReference piscina11Ref = databaseReference.child("Piscina 11");
        DatabaseReference piscina12Ref = databaseReference.child("Piscina 12");
        DatabaseReference piscina13Ref = databaseReference.child("Piscina 13");
        DatabaseReference piscina14Ref = databaseReference.child("Piscina 14");
        DatabaseReference piscina15Ref = databaseReference.child("Piscina 15");

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html><body>");
        htmlBuilder.append("<h1>DATOS PISCINAS SECTOR 2</h1>");
        htmlBuilder.append("<table border='1'>");
        htmlBuilder.append("<tr><th>PISCINA</th><th>DIETA (KG)</th><th>SOBRANTE TOLVA (KG)</th><th>RECARGA (KG)</th><th>AL VOLEO (KG)</th><th>SOBRANTE CASETA (KG)</th><th>TIPO BALANCEADO</th><th>TOLVA 1</th><th>TOLVA 2</th><th>TOLVA 3</th><th>TOLVA 4</th><th>TOLVA 5</th><th>TOLVA 6</th><th>TOLVA 7</th><th>TOLVA 8</th><th>TOLVA 9</th><th>TOLVA 10</th></tr>");

        // Consultar Piscina 9
        piscina9Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String fecha = snapshot.child("fecha").getValue(String.class);
                    if (formattedDate.equals(fecha)) {
                        Integer alVoleo = snapshot.child("alvoleo").getValue(Integer.class);
                        Integer dieta = snapshot.child("dieta").getValue(Integer.class);
                        Integer recarga = snapshot.child("recarga").getValue(Integer.class);
                        Integer sobranteCaseta = snapshot.child("sobrantecaseta").getValue(Integer.class);
                        Integer sobranteTolva = snapshot.child("sobrantetolva").getValue(Integer.class);
                        String tipoBalanceado = snapshot.child("tipobalanceado").getValue(String.class);

                        // Inicializar tolvas a 0 si los valores son nulos
                        Integer p9t1 = snapshot.child("t1").getValue(Integer.class);
                        Integer p9t2 = snapshot.child("t2").getValue(Integer.class);
                        Integer p9t3 = snapshot.child("t3").getValue(Integer.class);
                        Integer p9t4 = snapshot.child("t4").getValue(Integer.class);
                        Integer p9t5 = snapshot.child("t5").getValue(Integer.class);
                        Integer p9t6 = snapshot.child("t6").getValue(Integer.class);

                        p9t1 = (p9t1 != null) ? p9t1 : 0;
                        p9t2 = (p9t2 != null) ? p9t2 : 0;
                        p9t3 = (p9t3 != null) ? p9t3 : 0;
                        p9t4 = (p9t4 != null) ? p9t4 : 0;
                        p9t5 = (p9t5 != null) ? p9t5 : 0;
                        p9t6 = (p9t6 != null) ? p9t6 : 0;

                        dieta = (dieta != null) ? dieta * 25 : 0;
                        recarga = (recarga != null) ? recarga * 25 : 0;
                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                        alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                        htmlBuilder.append("<tr><td>9</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p9t1).append("</td><td>").append(p9t2).append("</td><td>").append(p9t3).append("</td><td>").append(p9t4).append("</td><td>").append(p9t5).append("</td><td>").append(p9t6).append("</td></tr>");
                    }
                }

                // Consultar Piscina 10A
                piscina10ARef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String fecha = snapshot.child("fecha").getValue(String.class);
                            if (formattedDate.equals(fecha)) {
                                Integer alVoleo = snapshot.child("alvoleo").getValue(Integer.class);
                                Integer dieta = snapshot.child("dieta").getValue(Integer.class);
                                Integer recarga = snapshot.child("recarga").getValue(Integer.class);
                                Integer sobranteCaseta = snapshot.child("sobrantecaseta").getValue(Integer.class);
                                Integer sobranteTolva = snapshot.child("sobrantetolva").getValue(Integer.class);
                                String tipoBalanceado = snapshot.child("tipobalanceado").getValue(String.class);

                                // Inicializar tolvas a 0 si los valores son nulos
                                Integer p10At1 = snapshot.child("t1").getValue(Integer.class);
                                Integer p10At2 = snapshot.child("t2").getValue(Integer.class);
                                Integer p10At3 = snapshot.child("t3").getValue(Integer.class);
                                Integer p10At4 = snapshot.child("t4").getValue(Integer.class);
                                Integer p10At5 = snapshot.child("t5").getValue(Integer.class);
                                Integer p10At6 = snapshot.child("t6").getValue(Integer.class);
                                Integer p10At7 = snapshot.child("t7").getValue(Integer.class);
                                Integer p10At8 = snapshot.child("t8").getValue(Integer.class);
                                Integer p10At9 = snapshot.child("t9").getValue(Integer.class);
                                Integer p10At10 = snapshot.child("t10").getValue(Integer.class);

                                p10At1 = (p10At1 != null) ? p10At1 : 0;
                                p10At2 = (p10At2 != null) ? p10At2 : 0;
                                p10At3 = (p10At3 != null) ? p10At3 : 0;
                                p10At4 = (p10At4 != null) ? p10At4 : 0;
                                p10At5 = (p10At5 != null) ? p10At5 : 0;
                                p10At6 = (p10At6 != null) ? p10At6 : 0;
                                p10At7 = (p10At7 != null) ? p10At7 : 0;
                                p10At8 = (p10At8 != null) ? p10At8 : 0;
                                p10At9 = (p10At9 != null) ? p10At9 : 0;
                                p10At10 = (p10At10 != null) ? p10At10 : 0;

                                dieta = (dieta != null) ? dieta * 25 : 0;
                                recarga = (recarga != null) ? recarga * 25 : 0;
                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                htmlBuilder.append("<tr><td>10A</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p10At1).append("</td><td>").append(p10At2).append("</td><td>").append(p10At3).append("</td><td>").append(p10At4).append("</td><td>").append(p10At5).append("</td><td>").append(p10At6).append("</td><td>").append(p10At7).append("</td><td>").append(p10At8).append("</td><td>").append(p10At9).append("</td><td>").append(p10At10).append("</td></tr>");
                            }
                        }

                        // Consultar Piscina 10B
                        piscina10BRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String fecha = snapshot.child("fecha").getValue(String.class);
                                    if (formattedDate.equals(fecha)) {
                                        Integer alVoleo = snapshot.child("alvoleo").getValue(Integer.class);
                                        Integer dieta = snapshot.child("dieta").getValue(Integer.class);
                                        Integer recarga = snapshot.child("recarga").getValue(Integer.class);
                                        Integer sobranteCaseta = snapshot.child("sobrantecaseta").getValue(Integer.class);
                                        Integer sobranteTolva = snapshot.child("sobrantetolva").getValue(Integer.class);
                                        String tipoBalanceado = snapshot.child("tipobalanceado").getValue(String.class);

                                        // Inicializar tolvas a 0 si los valores son nulos
                                        Integer p3t1 = snapshot.child("t1").getValue(Integer.class);
                                        Integer p3t2 = snapshot.child("t2").getValue(Integer.class);
                                        Integer p3t3 = snapshot.child("t3").getValue(Integer.class);
                                        Integer p3t4 = snapshot.child("t4").getValue(Integer.class);
                                        Integer p3t5 = snapshot.child("t5").getValue(Integer.class);
                                        Integer p3t6 = snapshot.child("t6").getValue(Integer.class);

                                        p3t1 = (p3t1 != null) ? p3t1 : 0;
                                        p3t2 = (p3t2 != null) ? p3t2 : 0;
                                        p3t3 = (p3t3 != null) ? p3t3 : 0;
                                        p3t4 = (p3t4 != null) ? p3t4 : 0;
                                        p3t5 = (p3t5 != null) ? p3t5 : 0;
                                        p3t6 = (p3t6 != null) ? p3t6 : 0;

                                        dieta = (dieta != null) ? dieta * 25 : 0;
                                        recarga = (recarga != null) ? recarga * 25 : 0;
                                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                        alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                        htmlBuilder.append("<tr><td>10B</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p3t1).append("</td><td>").append(p3t2).append("</td><td>").append(p3t3).append("</td><td>").append(p3t4).append("</td><td>").append(p3t5).append("</td><td>").append(p3t6).append("</td></tr>");
                                    }
                                }

                                piscina11Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            String fecha = snapshot.child("fecha").getValue(String.class);
                                            if (formattedDate.equals(fecha)) {
                                                Integer alVoleo = snapshot.child("alvoleo").getValue(Integer.class);
                                                Integer dieta = snapshot.child("dieta").getValue(Integer.class);
                                                Integer recarga = snapshot.child("recarga").getValue(Integer.class);
                                                Integer sobranteCaseta = snapshot.child("sobrantecaseta").getValue(Integer.class);
                                                Integer sobranteTolva = snapshot.child("sobrantetolva").getValue(Integer.class);
                                                String tipoBalanceado = snapshot.child("tipobalanceado").getValue(String.class);

                                                // Inicializar tolvas a 0 si los valores son nulos
                                                Integer p11t1 = snapshot.child("t1").getValue(Integer.class);
                                                Integer p11t2 = snapshot.child("t2").getValue(Integer.class);
                                                Integer p11t3 = snapshot.child("t3").getValue(Integer.class);
                                                Integer p11t4 = snapshot.child("t4").getValue(Integer.class);
                                                Integer p11t5 = snapshot.child("t5").getValue(Integer.class);
                                                Integer p11t6 = snapshot.child("t6").getValue(Integer.class);

                                                p11t1 = (p11t1 != null) ? p11t1 : 0;
                                                p11t2 = (p11t2 != null) ? p11t2 : 0;
                                                p11t3 = (p11t3 != null) ? p11t3 : 0;
                                                p11t4 = (p11t4 != null) ? p11t4 : 0;
                                                p11t5 = (p11t5 != null) ? p11t5 : 0;
                                                p11t6 = (p11t6 != null) ? p11t6 : 0;

                                                dieta = (dieta != null) ? dieta * 25 : 0;
                                                recarga = (recarga != null) ? recarga * 25 : 0;
                                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                                alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                htmlBuilder.append("<tr><td>11</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p11t1).append("</td><td>").append(p11t2).append("</td><td>").append(p11t3).append("</td><td>").append(p11t4).append("</td><td>").append(p11t5).append("</td><td>").append(p11t6).append("</td></tr>");
                                            }
                                        }

                                        piscina12Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                    String fecha = snapshot.child("fecha").getValue(String.class);
                                                    if (formattedDate.equals(fecha)) {
                                                        Integer alVoleo = snapshot.child("alvoleo").getValue(Integer.class);
                                                        Integer dieta = snapshot.child("dieta").getValue(Integer.class);
                                                        Integer recarga = snapshot.child("recarga").getValue(Integer.class);
                                                        Integer sobranteCaseta = snapshot.child("sobrantecaseta").getValue(Integer.class);
                                                        Integer sobranteTolva = snapshot.child("sobrantetolva").getValue(Integer.class);
                                                        String tipoBalanceado = snapshot.child("tipobalanceado").getValue(String.class);

                                                        // Inicializar tolvas a 0 si los valores son nulos
                                                        Integer p12t1 = snapshot.child("t1").getValue(Integer.class);
                                                        Integer p12t2 = snapshot.child("t2").getValue(Integer.class);
                                                        Integer p12t3 = snapshot.child("t3").getValue(Integer.class);
                                                        Integer p12t4 = snapshot.child("t4").getValue(Integer.class);
                                                        Integer p12t5 = snapshot.child("t5").getValue(Integer.class);
                                                        Integer p12t6 = snapshot.child("t6").getValue(Integer.class);

                                                        p12t1 = (p12t1 != null) ? p12t1 : 0;
                                                        p12t2 = (p12t2 != null) ? p12t2 : 0;
                                                        p12t3 = (p12t3 != null) ? p12t3 : 0;
                                                        p12t4 = (p12t4 != null) ? p12t4 : 0;
                                                        p12t5 = (p12t5 != null) ? p12t5 : 0;
                                                        p12t6 = (p12t6 != null) ? p12t6 : 0;

                                                        dieta = (dieta != null) ? dieta * 25 : 0;
                                                        recarga = (recarga != null) ? recarga * 25 : 0;
                                                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                                        alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                        htmlBuilder.append("<tr><td>12</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p12t1).append("</td><td>").append(p12t2).append("</td><td>").append(p12t3).append("</td><td>").append(p12t4).append("</td><td>").append(p12t5).append("</td><td>").append(p12t6).append("</td></tr>");
                                                    }
                                                }

                                                piscina13Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                            String fecha = snapshot.child("fecha").getValue(String.class);
                                                            if (formattedDate.equals(fecha)) {
                                                                Integer alVoleo = snapshot.child("alvoleo").getValue(Integer.class);
                                                                Integer dieta = snapshot.child("dieta").getValue(Integer.class);
                                                                Integer recarga = snapshot.child("recarga").getValue(Integer.class);
                                                                Integer sobranteCaseta = snapshot.child("sobrantecaseta").getValue(Integer.class);
                                                                Integer sobranteTolva = snapshot.child("sobrantetolva").getValue(Integer.class);
                                                                String tipoBalanceado = snapshot.child("tipobalanceado").getValue(String.class);

                                                                // Inicializar tolvas a 0 si los valores son nulos
                                                                Integer p13t1 = snapshot.child("t1").getValue(Integer.class);
                                                                Integer p13t2 = snapshot.child("t2").getValue(Integer.class);
                                                                Integer p13t3 = snapshot.child("t3").getValue(Integer.class);
                                                                Integer p13t4 = snapshot.child("t4").getValue(Integer.class);
                                                                Integer p13t5 = snapshot.child("t5").getValue(Integer.class);
                                                                Integer p13t6 = snapshot.child("t6").getValue(Integer.class);

                                                                p13t1 = (p13t1 != null) ? p13t1 : 0;
                                                                p13t2 = (p13t2 != null) ? p13t2 : 0;
                                                                p13t3 = (p13t3 != null) ? p13t3 : 0;
                                                                p13t4 = (p13t4 != null) ? p13t4 : 0;
                                                                p13t5 = (p13t5 != null) ? p13t5 : 0;
                                                                p13t6 = (p13t6 != null) ? p13t6 : 0;

                                                                dieta = (dieta != null) ? dieta * 25 : 0;
                                                                recarga = (recarga != null) ? recarga * 25 : 0;
                                                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                                                alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                                htmlBuilder.append("<tr><td>13</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p13t1).append("</td><td>").append(p13t2).append("</td><td>").append(p13t3).append("</td><td>").append(p13t4).append("</td><td>").append(p13t5).append("</td><td>").append(p13t6).append("</td></tr>");
                                                            }
                                                        }

                                                        piscina14Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                                    String fecha = snapshot.child("fecha").getValue(String.class);
                                                                    if (formattedDate.equals(fecha)) {
                                                                        Integer alVoleo = snapshot.child("alvoleo").getValue(Integer.class);
                                                                        Integer dieta = snapshot.child("dieta").getValue(Integer.class);
                                                                        Integer recarga = snapshot.child("recarga").getValue(Integer.class);
                                                                        Integer sobranteCaseta = snapshot.child("sobrantecaseta").getValue(Integer.class);
                                                                        Integer sobranteTolva = snapshot.child("sobrantetolva").getValue(Integer.class);
                                                                        String tipoBalanceado = snapshot.child("tipobalanceado").getValue(String.class);

                                                                        // Inicializar tolvas a 0 si los valores son nulos
                                                                        Integer p14t1 = snapshot.child("t1").getValue(Integer.class);
                                                                        Integer p14t2 = snapshot.child("t2").getValue(Integer.class);
                                                                        Integer p14t3 = snapshot.child("t3").getValue(Integer.class);
                                                                        Integer p14t4 = snapshot.child("t4").getValue(Integer.class);
                                                                        Integer p14t5 = snapshot.child("t5").getValue(Integer.class);
                                                                        Integer p14t6 = snapshot.child("t6").getValue(Integer.class);

                                                                        p14t1 = (p14t1 != null) ? p14t1 : 0;
                                                                        p14t2 = (p14t2 != null) ? p14t2 : 0;
                                                                        p14t3 = (p14t3 != null) ? p14t3 : 0;
                                                                        p14t4 = (p14t4 != null) ? p14t4 : 0;
                                                                        p14t5 = (p14t5 != null) ? p14t5 : 0;
                                                                        p14t6 = (p14t6 != null) ? p14t6 : 0;

                                                                        dieta = (dieta != null) ? dieta * 25 : 0;
                                                                        recarga = (recarga != null) ? recarga * 25 : 0;
                                                                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                                                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                                                        alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                                                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                                        htmlBuilder.append("<tr><td>14</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p14t1).append("</td><td>").append(p14t2).append("</td><td>").append(p14t3).append("</td><td>").append(p14t4).append("</td><td>").append(p14t5).append("</td><td>").append(p14t6).append("</td></tr>");
                                                                    }
                                                                }

                                                                piscina15Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                                            String fecha = snapshot.child("fecha").getValue(String.class);
                                                                            if (formattedDate.equals(fecha)) {
                                                                                Integer alVoleo = snapshot.child("alvoleo").getValue(Integer.class);
                                                                                Integer dieta = snapshot.child("dieta").getValue(Integer.class);
                                                                                Integer recarga = snapshot.child("recarga").getValue(Integer.class);
                                                                                Integer sobranteCaseta = snapshot.child("sobrantecaseta").getValue(Integer.class);
                                                                                Integer sobranteTolva = snapshot.child("sobrantetolva").getValue(Integer.class);
                                                                                String tipoBalanceado = snapshot.child("tipobalanceado").getValue(String.class);

                                                                                // Inicializar tolvas a 0 si los valores son nulos
                                                                                Integer p15t1 = snapshot.child("t1").getValue(Integer.class);
                                                                                Integer p15t2 = snapshot.child("t2").getValue(Integer.class);
                                                                                Integer p15t3 = snapshot.child("t3").getValue(Integer.class);
                                                                                Integer p15t4 = snapshot.child("t4").getValue(Integer.class);
                                                                                Integer p15t5 = snapshot.child("t5").getValue(Integer.class);
                                                                                Integer p15t6 = snapshot.child("t6").getValue(Integer.class);
                                                                                Integer p15t7 = snapshot.child("t7").getValue(Integer.class);
                                                                                Integer p15t8 = snapshot.child("t8").getValue(Integer.class);

                                                                                p15t1 = (p15t1 != null) ? p15t1 : 0;
                                                                                p15t2 = (p15t2 != null) ? p15t2 : 0;
                                                                                p15t3 = (p15t3 != null) ? p15t3 : 0;
                                                                                p15t4 = (p15t4 != null) ? p15t4 : 0;
                                                                                p15t5 = (p15t5 != null) ? p15t5 : 0;
                                                                                p15t6 = (p15t6 != null) ? p15t6 : 0;
                                                                                p15t7 = (p15t7 != null) ? p15t7 : 0;
                                                                                p15t8 = (p15t8 != null) ? p15t8 : 0;

                                                                                dieta = (dieta != null) ? dieta * 25 : 0;
                                                                                recarga = (recarga != null) ? recarga * 25 : 0;
                                                                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                                                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                                                                alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                                                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                                                htmlBuilder.append("<tr><td>15</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p15t1).append("</td><td>").append(p15t2).append("</td><td>").append(p15t3).append("</td><td>").append(p15t4).append("</td><td>").append(p15t5).append("</td><td>").append(p15t6).append("</td><td>").append(p15t7).append("</td><td>").append(p15t8).append("</td></tr>");
                                                                            }
                                                                        }

                                                                        // Cerrar la segunda tabla
                                                                        htmlBuilder.append("</table></body></html>");





                                                                        htmlBuilder.append("<h1>NOVEDADES</h1>");
                                                                        htmlBuilder.append("<table border='3'>");
                                                                        htmlBuilder.append("<tr><th>PISCINA</th><th>ROJO</th><th>FRESCO</th><th>REPORTADO</th><th>OBSERVACIONES</th></tr>"); // Agregar "OBSERVACIONES"

// Consultar Piscina 1

                                                                        piscina9Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                                                    String fecha = snapshot.child("fecha").getValue(String.class);
                                                                                    if (formattedDate.equals(fecha)) {
                                                                                        Integer rojo = snapshot.child("rojo").getValue(Integer.class);
                                                                                        Integer fresco = snapshot.child("fresco").getValue(Integer.class);
                                                                                        Integer reportado = snapshot.child("reportado").getValue(Integer.class); // Corrige el nombre "reportadp"
                                                                                        String observaciones = snapshot.child("observaciones").getValue(String.class);

                                                                                        // Inicializar tolvas a 0 si los valores son nulos
                                                                                        rojo = (rojo != null) ? rojo : 0;
                                                                                        fresco = (fresco != null) ? fresco : 0;
                                                                                        reportado = (reportado != null) ? reportado : 0;
                                                                                        observaciones = (observaciones != null) ? observaciones : "";

                                                                                        htmlBuilder.append("<tr><td>9</td><td>").append(rojo).append("</td><td>").append(fresco).append("</td><td>").append(reportado).append("</td><td>").append(observaciones).append("</td></tr>");
                                                                                    }
                                                                                }

                                                                                piscina10ARef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                    @Override
                                                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                                                            String fecha = snapshot.child("fecha").getValue(String.class);
                                                                                            if (formattedDate.equals(fecha)) {
                                                                                                Integer rojo = snapshot.child("rojo").getValue(Integer.class);
                                                                                                Integer fresco = snapshot.child("fresco").getValue(Integer.class);
                                                                                                Integer reportado = snapshot.child("reportado").getValue(Integer.class); // Corrige el nombre "reportadp"
                                                                                                String observaciones = snapshot.child("observaciones").getValue(String.class);

                                                                                                // Inicializar tolvas a 0 si los valores son nulos
                                                                                                rojo = (rojo != null) ? rojo : 0;
                                                                                                fresco = (fresco != null) ? fresco : 0;
                                                                                                reportado = (reportado != null) ? reportado : 0;
                                                                                                observaciones = (observaciones != null) ? observaciones : "";

                                                                                                htmlBuilder.append("<tr><td>10A</td><td>").append(rojo).append("</td><td>").append(fresco).append("</td><td>").append(reportado).append("</td><td>").append(observaciones).append("</td></tr>");
                                                                                            }
                                                                                        }

                                                                                        piscina10BRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                            @Override
                                                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                                                                    String fecha = snapshot.child("fecha").getValue(String.class);
                                                                                                    if (formattedDate.equals(fecha)) {
                                                                                                        Integer rojo = snapshot.child("rojo").getValue(Integer.class);
                                                                                                        Integer fresco = snapshot.child("fresco").getValue(Integer.class);
                                                                                                        Integer reportado = snapshot.child("reportado").getValue(Integer.class); // Corrige el nombre "reportadp"
                                                                                                        String observaciones = snapshot.child("observaciones").getValue(String.class);

                                                                                                        // Inicializar tolvas a 0 si los valores son nulos
                                                                                                        rojo = (rojo != null) ? rojo : 0;
                                                                                                        fresco = (fresco != null) ? fresco : 0;
                                                                                                        reportado = (reportado != null) ? reportado : 0;
                                                                                                        observaciones = (observaciones != null) ? observaciones : "";

                                                                                                        htmlBuilder.append("<tr><td>10B</td><td>").append(rojo).append("</td><td>").append(fresco).append("</td><td>").append(reportado).append("</td><td>").append(observaciones).append("</td></tr>");
                                                                                                    }
                                                                                                }

                                                                                                // Cerrar la segunda tabla
                                                                                                piscina11Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                    @Override
                                                                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                                                                            String fecha = snapshot.child("fecha").getValue(String.class);
                                                                                                            if (formattedDate.equals(fecha)) {
                                                                                                                Integer rojo = snapshot.child("rojo").getValue(Integer.class);
                                                                                                                Integer fresco = snapshot.child("fresco").getValue(Integer.class);
                                                                                                                Integer reportado = snapshot.child("reportado").getValue(Integer.class); // Corrige el nombre "reportadp"
                                                                                                                String observaciones = snapshot.child("observaciones").getValue(String.class);

                                                                                                                // Inicializar tolvas a 0 si los valores son nulos
                                                                                                                rojo = (rojo != null) ? rojo : 0;
                                                                                                                fresco = (fresco != null) ? fresco : 0;
                                                                                                                reportado = (reportado != null) ? reportado : 0;
                                                                                                                observaciones = (observaciones != null) ? observaciones : "";

                                                                                                                htmlBuilder.append("<tr><td>11</td><td>").append(rojo).append("</td><td>").append(fresco).append("</td><td>").append(reportado).append("</td><td>").append(observaciones).append("</td></tr>");
                                                                                                            }
                                                                                                        }

                                                                                                        // Cerrar la segunda tabla
                                                                                                        piscina12Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                            @Override
                                                                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                                                                                    String fecha = snapshot.child("fecha").getValue(String.class);
                                                                                                                    if (formattedDate.equals(fecha)) {
                                                                                                                        Integer rojo = snapshot.child("rojo").getValue(Integer.class);
                                                                                                                        Integer fresco = snapshot.child("fresco").getValue(Integer.class);
                                                                                                                        Integer reportado = snapshot.child("reportado").getValue(Integer.class); // Corrige el nombre "reportadp"
                                                                                                                        String observaciones = snapshot.child("observaciones").getValue(String.class);

                                                                                                                        // Inicializar tolvas a 0 si los valores son nulos
                                                                                                                        rojo = (rojo != null) ? rojo : 0;
                                                                                                                        fresco = (fresco != null) ? fresco : 0;
                                                                                                                        reportado = (reportado != null) ? reportado : 0;
                                                                                                                        observaciones = (observaciones != null) ? observaciones : "";

                                                                                                                        htmlBuilder.append("<tr><td>12</td><td>").append(rojo).append("</td><td>").append(fresco).append("</td><td>").append(reportado).append("</td><td>").append(observaciones).append("</td></tr>");
                                                                                                                    }
                                                                                                                }

                                                                                                                // Cerrar la segunda tabla
                                                                                                                piscina13Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                    @Override
                                                                                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                                                                                            String fecha = snapshot.child("fecha").getValue(String.class);
                                                                                                                            if (formattedDate.equals(fecha)) {
                                                                                                                                Integer rojo = snapshot.child("rojo").getValue(Integer.class);
                                                                                                                                Integer fresco = snapshot.child("fresco").getValue(Integer.class);
                                                                                                                                Integer reportado = snapshot.child("reportado").getValue(Integer.class); // Corrige el nombre "reportadp"
                                                                                                                                String observaciones = snapshot.child("observaciones").getValue(String.class);

                                                                                                                                // Inicializar tolvas a 0 si los valores son nulos
                                                                                                                                rojo = (rojo != null) ? rojo : 0;
                                                                                                                                fresco = (fresco != null) ? fresco : 0;
                                                                                                                                reportado = (reportado != null) ? reportado : 0;
                                                                                                                                observaciones = (observaciones != null) ? observaciones : "";

                                                                                                                                htmlBuilder.append("<tr><td>13</td><td>").append(rojo).append("</td><td>").append(fresco).append("</td><td>").append(reportado).append("</td><td>").append(observaciones).append("</td></tr>");
                                                                                                                            }
                                                                                                                        }

                                                                                                                        // Cerrar la segunda tabla
                                                                                                                        piscina14Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                            @Override
                                                                                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                                                                                                    String fecha = snapshot.child("fecha").getValue(String.class);
                                                                                                                                    if (formattedDate.equals(fecha)) {
                                                                                                                                        Integer rojo = snapshot.child("rojo").getValue(Integer.class);
                                                                                                                                        Integer fresco = snapshot.child("fresco").getValue(Integer.class);
                                                                                                                                        Integer reportado = snapshot.child("reportado").getValue(Integer.class); // Corrige el nombre "reportadp"
                                                                                                                                        String observaciones = snapshot.child("observaciones").getValue(String.class);

                                                                                                                                        // Inicializar tolvas a 0 si los valores son nulos
                                                                                                                                        rojo = (rojo != null) ? rojo : 0;
                                                                                                                                        fresco = (fresco != null) ? fresco : 0;
                                                                                                                                        reportado = (reportado != null) ? reportado : 0;
                                                                                                                                        observaciones = (observaciones != null) ? observaciones : "";

                                                                                                                                        htmlBuilder.append("<tr><td>14</td><td>").append(rojo).append("</td><td>").append(fresco).append("</td><td>").append(reportado).append("</td><td>").append(observaciones).append("</td></tr>");
                                                                                                                                    }
                                                                                                                                }

                                                                                                                                piscina15Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                                    @Override
                                                                                                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                                                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                                                                                                            String fecha = snapshot.child("fecha").getValue(String.class);
                                                                                                                                            if (formattedDate.equals(fecha)) {
                                                                                                                                                Integer rojo = snapshot.child("rojo").getValue(Integer.class);
                                                                                                                                                Integer fresco = snapshot.child("fresco").getValue(Integer.class);
                                                                                                                                                Integer reportado = snapshot.child("reportado").getValue(Integer.class); // Corrige el nombre "reportadp"
                                                                                                                                                String observaciones = snapshot.child("observaciones").getValue(String.class);

                                                                                                                                                // Inicializar tolvas a 0 si los valores son nulos
                                                                                                                                                rojo = (rojo != null) ? rojo : 0;
                                                                                                                                                fresco = (fresco != null) ? fresco : 0;
                                                                                                                                                reportado = (reportado != null) ? reportado : 0;
                                                                                                                                                observaciones = (observaciones != null) ? observaciones : "";

                                                                                                                                                htmlBuilder.append("<tr><td>15</td><td>").append(rojo).append("</td><td>").append(fresco).append("</td><td>").append(reportado).append("</td><td>").append(observaciones).append("</td></tr>");
                                                                                                                                            }
                                                                                                                                        }

                                                                                                                                        // Cerrar la segunda tabla
                                                                                                                                        htmlBuilder.append("</table>");

                                                                                                                                        // Cerrar el HTML
                                                                                                                                        htmlBuilder.append("</body></html>");

                                                                                                                                        // Cargar los datos en el WebView
                                                                                                                                        webView.loadDataWithBaseURL(null, htmlBuilder.toString(), "text/html", "UTF-8", null);
                                                                                                                                        swipeRefreshLayout.setRefreshing(false);
                                                                                                                                    }

                                                                                                                                    @Override
                                                                                                                                    public void onCancelled(DatabaseError databaseError) {
                                                                                                                                        Log.e("FirebaseError", "Error al cargar datos", databaseError.toException());
                                                                                                                                        swipeRefreshLayout.setRefreshing(false);
                                                                                                                                    }
                                                                                                                                });
                                                                                                                            }

                                                                                                                            @Override
                                                                                                                            public void onCancelled(DatabaseError databaseError) {
                                                                                                                                Log.e("FirebaseError", "Error al cargar datos", databaseError.toException());
                                                                                                                                swipeRefreshLayout.setRefreshing(false);
                                                                                                                            }
                                                                                                                        });
                                                                                                                    }

                                                                                                                    @Override
                                                                                                                    public void onCancelled(DatabaseError databaseError) {
                                                                                                                        Log.e("FirebaseError", "Error al cargar datos", databaseError.toException());
                                                                                                                        swipeRefreshLayout.setRefreshing(false);
                                                                                                                    }
                                                                                                                });
                                                                                                            }

                                                                                                            @Override
                                                                                                            public void onCancelled(DatabaseError databaseError) {
                                                                                                                Log.e("FirebaseError", "Error al cargar datos", databaseError.toException());
                                                                                                                swipeRefreshLayout.setRefreshing(false);
                                                                                                            }
                                                                                                        });
                                                                                                    }

                                                                                                    @Override
                                                                                                    public void onCancelled(DatabaseError databaseError) {
                                                                                                        Log.e("FirebaseError", "Error al cargar datos", databaseError.toException());
                                                                                                        swipeRefreshLayout.setRefreshing(false);
                                                                                                    }
                                                                                                });
                                                                                            }

                                                                                            @Override
                                                                                            public void onCancelled(DatabaseError databaseError) {
                                                                                                Log.e("FirebaseError", "Error al cargar datos", databaseError.toException());
                                                                                                swipeRefreshLayout.setRefreshing(false);
                                                                                            }
                                                                                        });
                                                                                    }

                                                                                    @Override
                                                                                    public void onCancelled(DatabaseError databaseError) {
                                                                                        Log.e("FirebaseError", "Error al cargar datos", databaseError.toException());
                                                                                        swipeRefreshLayout.setRefreshing(false);
                                                                                    }
                                                                                });
                                                                            }

                                                                            @Override
                                                                            public void onCancelled(DatabaseError databaseError) {
                                                                                Log.e("FirebaseError", "Error al cargar datos", databaseError.toException());
                                                                                swipeRefreshLayout.setRefreshing(false);
                                                                            }
                                                                        });
                                                                    }

                                                                    @Override
                                                                    public void onCancelled(DatabaseError databaseError) {
                                                                        Log.e("FirebaseError", "Error al cargar datos", databaseError.toException());
                                                                        swipeRefreshLayout.setRefreshing(false);
                                                                    }
                                                                });
                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {
                                                                Log.e("FirebaseError", "Error al cargar datos", databaseError.toException());
                                                                swipeRefreshLayout.setRefreshing(false);
                                                            }
                                                        });
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {
                                                        Log.e("FirebaseError", "Error al cargar datos", databaseError.toException());
                                                        swipeRefreshLayout.setRefreshing(false);
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                Log.e("FirebaseError", "Error al cargar datos", databaseError.toException());
                                                swipeRefreshLayout.setRefreshing(false);
                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Log.e("FirebaseError", "Error al cargar datos", databaseError.toException());
                                        swipeRefreshLayout.setRefreshing(false);
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.e("FirebaseError", "Error al cargar datos", databaseError.toException());
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("FirebaseError", "Error al cargar datos", databaseError.toException());
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FirebaseError", "Error al cargar datos", databaseError.toException());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }







    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void showDatePickerDialog() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    calendar.set(year1, monthOfYear, dayOfMonth);
                    updateDateLabel();
                    loadFirebaseData(); // Reload data when the date changes
                }, year, month, day);

        datePickerDialog.show();
    }

    private void updateDateLabel() {
        String format = "dd/MM/yyyy"; // Or any other date format you prefer
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        selectedDate = sdf.format(calendar.getTime()); // Guarda la fecha en formato de cadena
        editTextDate.setText(selectedDate);
    }
    @Override
    public void onBackPressed() {
        // Aquí puedes agregar cualquier lógica que necesites antes de cerrar la actividad
        // Por ejemplo, mostrar un mensaje o realizar limpieza
        super.onBackPressed(); // Esto cerrará la actividad actual
    }
}