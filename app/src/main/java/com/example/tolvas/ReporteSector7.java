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

public class ReporteSector7 extends AppCompatActivity {
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

        actualizarDatosDesdeFirebase();
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        webView = findViewById(R.id.webView1);
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

        // Set initial date
        updateDateLabel();

        editTextDate.setOnClickListener(v -> showDatePickerDialog());

        loadFirebaseData();
    }

    private void actualizarDatosDesdeFirebase() {
        // Lista de referencias a las piscinas
        List<String> piscinas = Arrays.asList("Piscina 37", "Piscina 38A", "Piscina 38B", "Piscina 39", "Piscina 40", "Piscina 41", "Piscina 42", "Piscina 43", "Piscina 44", "Piscina 45", "Piscina 46A", "Piscina 46B");

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
        DatabaseReference piscina37Ref = databaseReference.child("Piscina 37");
        DatabaseReference piscina38ARef = databaseReference.child("Piscina 38A");
        DatabaseReference piscina38BRef = databaseReference.child("Piscina 38B");
        DatabaseReference piscina39Ref = databaseReference.child("Piscina 39");
        DatabaseReference piscina40Ref = databaseReference.child("Piscina 40");
        DatabaseReference piscina41Ref = databaseReference.child("Piscina 41");
        DatabaseReference piscina42Ref = databaseReference.child("Piscina 42");
        DatabaseReference piscina43Ref = databaseReference.child("Piscina 43");
        DatabaseReference piscina44Ref = databaseReference.child("Piscina 44");
        DatabaseReference piscina45Ref = databaseReference.child("Piscina 45");
        DatabaseReference piscina46ARef = databaseReference.child("Piscina 46A");
        DatabaseReference piscina46BRef = databaseReference.child("Piscina 46B");

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html><body>");
        htmlBuilder.append("<h1>DATOS PISCINAS SECTOR 7</h1>");
        htmlBuilder.append("<table border='1'>");
        htmlBuilder.append("<tr><th>PISCINA</th><th>DIETA (KG)</th><th>SOBRANTE TOLVA (KG)</th><th>RECARGA (KG)</th><th>AL VOLEO (KG)</th><th>SOBRANTE CASETA (KG)</th><th>TIPO BALANCEADO</th><th>TOLVA 1</th><th>TOLVA 2</th><th>TOLVA 3</th><th>TOLVA 4</th><th>TOLVA 5</th><th>TOLVA 6</th><th>TOLVA 7</th><th>TOLVA 8</th><th>TOLVA 9</th><th>TOLVA 10</th></tr>");

        // Consultar Piscina 37
        piscina37Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                        Integer p37t1 = snapshot.child("t1").getValue(Integer.class);
                        Integer p37t2 = snapshot.child("t2").getValue(Integer.class);
                        Integer p37t3 = snapshot.child("t3").getValue(Integer.class);
                        Integer p37t4 = snapshot.child("t4").getValue(Integer.class);
                        Integer p37t5 = snapshot.child("t5").getValue(Integer.class);
                        Integer p37t6 = snapshot.child("t6").getValue(Integer.class);

                        p37t1 = (p37t1 != null) ? p37t1 : 0;
                        p37t2 = (p37t2 != null) ? p37t2 : 0;
                        p37t3 = (p37t3 != null) ? p37t3 : 0;
                        p37t4 = (p37t4 != null) ? p37t4 : 0;
                        p37t5 = (p37t5 != null) ? p37t5 : 0;
                        p37t6 = (p37t6 != null) ? p37t6 : 0;

                        dieta = (dieta != null) ? dieta * 25 : 0;
                        recarga = (recarga != null) ? recarga * 25 : 0;
                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                        alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                        htmlBuilder.append("<tr><td>37</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p37t1).append("</td><td>").append(p37t2).append("</td><td>").append(p37t3).append("</td><td>").append(p37t4).append("</td><td>").append(p37t5).append("</td><td>").append(p37t6).append("</td></tr>");
                    }
                }

                // Consultar Piscina 38A
                piscina38ARef.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                Integer p38At1 = snapshot.child("t1").getValue(Integer.class);
                                Integer p38At2 = snapshot.child("t2").getValue(Integer.class);
                                Integer p38At3 = snapshot.child("t3").getValue(Integer.class);
                                Integer p38At4 = snapshot.child("t4").getValue(Integer.class);
                                Integer p38At5 = snapshot.child("t5").getValue(Integer.class);
                                Integer p38At6 = snapshot.child("t6").getValue(Integer.class);

                                p38At1 = (p38At1 != null) ? p38At1 : 0;
                                p38At2 = (p38At2 != null) ? p38At2 : 0;
                                p38At3 = (p38At3 != null) ? p38At3 : 0;
                                p38At4 = (p38At4 != null) ? p38At4 : 0;
                                p38At5 = (p38At5 != null) ? p38At5 : 0;
                                p38At6 = (p38At6 != null) ? p38At6 : 0;

                                dieta = (dieta != null) ? dieta * 25 : 0;
                                recarga = (recarga != null) ? recarga * 25 : 0;
                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                htmlBuilder.append("<tr><td>38A</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p38At1).append("</td><td>").append(p38At2).append("</td><td>").append(p38At3).append("</td><td>").append(p38At4).append("</td><td>").append(p38At5).append("</td><td>").append(p38At6).append("</td></tr>");
                            }
                        }

                        // Consultar Piscina 38B
                        piscina38BRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                        Integer p38Bt1 = snapshot.child("t1").getValue(Integer.class);
                                        Integer p38Bt2 = snapshot.child("t2").getValue(Integer.class);
                                        Integer p38Bt3 = snapshot.child("t3").getValue(Integer.class);
                                        Integer p38Bt4 = snapshot.child("t4").getValue(Integer.class);
                                        Integer p38Bt5 = snapshot.child("t5").getValue(Integer.class);
                                        Integer p38Bt6 = snapshot.child("t6").getValue(Integer.class);

                                        p38Bt1 = (p38Bt1 != null) ? p38Bt1 : 0;
                                        p38Bt2 = (p38Bt2 != null) ? p38Bt2 : 0;
                                        p38Bt3 = (p38Bt3 != null) ? p38Bt3 : 0;
                                        p38Bt4 = (p38Bt4 != null) ? p38Bt4 : 0;
                                        p38Bt5 = (p38Bt5 != null) ? p38Bt5 : 0;
                                        p38Bt6 = (p38Bt6 != null) ? p38Bt6 : 0;

                                        dieta = (dieta != null) ? dieta * 25 : 0;
                                        recarga = (recarga != null) ? recarga * 25 : 0;
                                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                        alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                        htmlBuilder.append("<tr><td>38B</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p38Bt1).append("</td><td>").append(p38Bt2).append("</td><td>").append(p38Bt3).append("</td><td>").append(p38Bt4).append("</td><td>").append(p38Bt5).append("</td><td>").append(p38Bt6).append("</td></tr>");
                                    }
                                }


                                //Piscina 39
                                piscina39Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                Integer p39t1 = snapshot.child("t1").getValue(Integer.class);
                                                Integer p39t2 = snapshot.child("t2").getValue(Integer.class);
                                                Integer p39t3 = snapshot.child("t3").getValue(Integer.class);
                                                Integer p39t4 = snapshot.child("t4").getValue(Integer.class);
                                                Integer p39t5 = snapshot.child("t5").getValue(Integer.class);
                                                Integer p39t6 = snapshot.child("t6").getValue(Integer.class);

                                                p39t1 = (p39t1 != null) ? p39t1 : 0;
                                                p39t2 = (p39t2 != null) ? p39t2 : 0;
                                                p39t3 = (p39t3 != null) ? p39t3 : 0;
                                                p39t4 = (p39t4 != null) ? p39t4 : 0;
                                                p39t5 = (p39t5 != null) ? p39t5 : 0;
                                                p39t6 = (p39t6 != null) ? p39t6 : 0;

                                                dieta = (dieta != null) ? dieta * 25 : 0;
                                                recarga = (recarga != null) ? recarga * 25 : 0;
                                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                                alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                htmlBuilder.append("<tr><td>39</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p39t1).append("</td><td>").append(p39t2).append("</td><td>").append(p39t3).append("</td><td>").append(p39t4).append("</td><td>").append(p39t5).append("</td><td>").append(p39t6).append("</td></tr>");
                                            }
                                        }
                                        //PISCINA 40
                                        piscina40Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                        Integer p40t1 = snapshot.child("t1").getValue(Integer.class);
                                                        Integer p40t2 = snapshot.child("t2").getValue(Integer.class);
                                                        Integer p40t3 = snapshot.child("t3").getValue(Integer.class);
                                                        Integer p40t4 = snapshot.child("t4").getValue(Integer.class);
                                                        Integer p40t5 = snapshot.child("t5").getValue(Integer.class);
                                                        Integer p40t6 = snapshot.child("t6").getValue(Integer.class);
                                                        Integer p40t7 = snapshot.child("t7").getValue(Integer.class);
                                                        Integer p40t8 = snapshot.child("t8").getValue(Integer.class);

                                                        p40t1 = (p40t1 != null) ? p40t1 : 0;
                                                        p40t2 = (p40t2 != null) ? p40t2 : 0;
                                                        p40t3 = (p40t3 != null) ? p40t3 : 0;
                                                        p40t4 = (p40t4 != null) ? p40t4 : 0;
                                                        p40t5 = (p40t5 != null) ? p40t5 : 0;
                                                        p40t6 = (p40t6 != null) ? p40t6 : 0;
                                                        p40t7 = (p40t7 != null) ? p40t7 : 0;
                                                        p40t8 = (p40t8 != null) ? p40t8 : 0;

                                                        dieta = (dieta != null) ? dieta * 25 : 0;
                                                        recarga = (recarga != null) ? recarga * 25 : 0;
                                                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                                        alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                        htmlBuilder.append("<tr><td>40</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p40t1).append("</td><td>").append(p40t2).append("</td><td>").append(p40t3).append("</td><td>").append(p40t4).append("</td><td>").append(p40t5).append("</td><td>").append(p40t6).append("</td><td>").append(p40t7).append("</td><td>").append(p40t8).append("</td></tr>");
                                                    }
                                                }

                                                piscina41Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                                Integer p41t1 = snapshot.child("t1").getValue(Integer.class);
                                                                Integer p41t2 = snapshot.child("t2").getValue(Integer.class);
                                                                Integer p41t3 = snapshot.child("t3").getValue(Integer.class);
                                                                Integer p41t4 = snapshot.child("t4").getValue(Integer.class);
                                                                Integer p41t5 = snapshot.child("t5").getValue(Integer.class);
                                                                Integer p41t6 = snapshot.child("t6").getValue(Integer.class);
                                                                Integer p41t7 = snapshot.child("t7").getValue(Integer.class);
                                                                Integer p41t8 = snapshot.child("t8").getValue(Integer.class);
                                                                Integer p41t9 = snapshot.child("t9").getValue(Integer.class);
                                                                Integer p41t10 = snapshot.child("t10").getValue(Integer.class);

                                                                p41t1 = (p41t1 != null) ? p41t1 : 0;
                                                                p41t2 = (p41t2 != null) ? p41t2 : 0;
                                                                p41t3 = (p41t3 != null) ? p41t3 : 0;
                                                                p41t4 = (p41t4 != null) ? p41t4 : 0;
                                                                p41t5 = (p41t5 != null) ? p41t5 : 0;
                                                                p41t6 = (p41t6 != null) ? p41t6 : 0;
                                                                p41t7 = (p41t7 != null) ? p41t7 : 0;
                                                                p41t8 = (p41t8 != null) ? p41t8 : 0;
                                                                p41t9 = (p41t9 != null) ? p41t9 : 0;
                                                                p41t10 = (p41t10 != null) ? p41t10 : 0;

                                                                dieta = (dieta != null) ? dieta * 25 : 0;
                                                                recarga = (recarga != null) ? recarga * 25 : 0;
                                                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                                                alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                                htmlBuilder.append("<tr><td>41</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p41t1).append("</td><td>").append(p41t2).append("</td><td>").append(p41t3).append("</td><td>").append(p41t4).append("</td><td>").append(p41t5).append("</td><td>").append(p41t6).append("</td><td>").append(p41t7).append("</td><td>").append(p41t8).append("</td><td>").append(p41t9).append("</td><td>").append(p41t10).append("</td></tr>");
                                                            }
                                                        }

                                                        piscina42Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                                        Integer p42t1 = snapshot.child("t1").getValue(Integer.class);
                                                                        Integer p42t2 = snapshot.child("t2").getValue(Integer.class);
                                                                        Integer p42t3 = snapshot.child("t3").getValue(Integer.class);
                                                                        Integer p42t4 = snapshot.child("t4").getValue(Integer.class);
                                                                        Integer p42t5 = snapshot.child("t5").getValue(Integer.class);
                                                                        Integer p42t6 = snapshot.child("t6").getValue(Integer.class);
                                                                        Integer p42t7 = snapshot.child("t7").getValue(Integer.class);
                                                                        Integer p42t8 = snapshot.child("t8").getValue(Integer.class);
                                                                        Integer p42t9 = snapshot.child("t9").getValue(Integer.class);

                                                                        p42t1 = (p42t1 != null) ? p42t1 : 0;
                                                                        p42t2 = (p42t2 != null) ? p42t2 : 0;
                                                                        p42t3 = (p42t3 != null) ? p42t3 : 0;
                                                                        p42t4 = (p42t4 != null) ? p42t4 : 0;
                                                                        p42t5 = (p42t5 != null) ? p42t5 : 0;
                                                                        p42t6 = (p42t6 != null) ? p42t6 : 0;
                                                                        p42t7 = (p42t7 != null) ? p42t7 : 0;
                                                                        p42t8 = (p42t8 != null) ? p42t8 : 0;
                                                                        p42t9 = (p42t9 != null) ? p42t9 : 0;

                                                                        dieta = (dieta != null) ? dieta * 25 : 0;
                                                                        recarga = (recarga != null) ? recarga * 25 : 0;
                                                                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                                                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                                                        alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                                                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                                        htmlBuilder.append("<tr><td>42</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p42t1).append("</td><td>").append(p42t2).append("</td><td>").append(p42t3).append("</td><td>").append(p42t4).append("</td><td>").append(p42t5).append("</td><td>").append(p42t6).append("</td><td>").append(p42t7).append("</td><td>").append(p42t8).append("</td><td>").append(p42t9).append("</td></tr>");
                                                                    }
                                                                }

                                                                piscina43Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                                                Integer p43t1 = snapshot.child("t1").getValue(Integer.class);
                                                                                Integer p43t2 = snapshot.child("t2").getValue(Integer.class);
                                                                                Integer p43t3 = snapshot.child("t3").getValue(Integer.class);
                                                                                Integer p43t4 = snapshot.child("t4").getValue(Integer.class);
                                                                                Integer p43t5 = snapshot.child("t5").getValue(Integer.class);

                                                                                p43t1 = (p43t1 != null) ? p43t1 : 0;
                                                                                p43t2 = (p43t2 != null) ? p43t2 : 0;
                                                                                p43t3 = (p43t3 != null) ? p43t3 : 0;
                                                                                p43t4 = (p43t4 != null) ? p43t4 : 0;
                                                                                p43t5 = (p43t5 != null) ? p43t5 : 0;

                                                                                dieta = (dieta != null) ? dieta * 25 : 0;
                                                                                recarga = (recarga != null) ? recarga * 25 : 0;
                                                                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                                                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                                                                alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                                                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                                                htmlBuilder.append("<tr><td>43</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p43t1).append("</td><td>").append(p43t2).append("</td><td>").append(p43t3).append("</td><td>").append(p43t4).append("</td><td>").append(p43t5).append("</td></tr>");
                                                                            }
                                                                        }


                                                                        piscina44Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                                                        Integer p44t1 = snapshot.child("t1").getValue(Integer.class);
                                                                                        Integer p44t2 = snapshot.child("t2").getValue(Integer.class);
                                                                                        Integer p44t3 = snapshot.child("t3").getValue(Integer.class);
                                                                                        Integer p44t4 = snapshot.child("t4").getValue(Integer.class);
                                                                                        Integer p44t5 = snapshot.child("t5").getValue(Integer.class);
                                                                                        Integer p44t6 = snapshot.child("t6").getValue(Integer.class);
                                                                                        Integer p44t7 = snapshot.child("t7").getValue(Integer.class);

                                                                                        p44t1 = (p44t1 != null) ? p44t1 : 0;
                                                                                        p44t2 = (p44t2 != null) ? p44t2 : 0;
                                                                                        p44t3 = (p44t3 != null) ? p44t3 : 0;
                                                                                        p44t4 = (p44t4 != null) ? p44t4 : 0;
                                                                                        p44t5 = (p44t5 != null) ? p44t5 : 0;
                                                                                        p44t6 = (p44t6 != null) ? p44t6 : 0;
                                                                                        p44t7 = (p44t7 != null) ? p44t7 : 0;

                                                                                        dieta = (dieta != null) ? dieta * 25 : 0;
                                                                                        recarga = (recarga != null) ? recarga * 25 : 0;
                                                                                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                                                                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                                                                        alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                                                                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                                                        htmlBuilder.append("<tr><td>44</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p44t1).append("</td><td>").append(p44t2).append("</td><td>").append(p44t3).append("</td><td>").append(p44t4).append("</td><td>").append(p44t5).append("</td><td>").append(p44t6).append("</td><td>").append(p44t7).append("</td></tr>");
                                                                                    }
                                                                                }

                                                                                piscina45Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                                                                Integer p45t1 = snapshot.child("t1").getValue(Integer.class);
                                                                                                Integer p45t2 = snapshot.child("t2").getValue(Integer.class);
                                                                                                Integer p45t3 = snapshot.child("t3").getValue(Integer.class);
                                                                                                Integer p45t4 = snapshot.child("t4").getValue(Integer.class);
                                                                                                Integer p45t5 = snapshot.child("t5").getValue(Integer.class);
                                                                                                Integer p45t6 = snapshot.child("t6").getValue(Integer.class);

                                                                                                p45t1 = (p45t1 != null) ? p45t1 : 0;
                                                                                                p45t2 = (p45t2 != null) ? p45t2 : 0;
                                                                                                p45t3 = (p45t3 != null) ? p45t3 : 0;
                                                                                                p45t4 = (p45t4 != null) ? p45t4 : 0;
                                                                                                p45t5 = (p45t5 != null) ? p45t5 : 0;
                                                                                                p45t6 = (p45t6 != null) ? p45t6 : 0;

                                                                                                dieta = (dieta != null) ? dieta * 25 : 0;
                                                                                                recarga = (recarga != null) ? recarga * 25 : 0;
                                                                                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                                                                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                                                                                alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                                                                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                                                                htmlBuilder.append("<tr><td>45</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p45t1).append("</td><td>").append(p45t2).append("</td><td>").append(p45t3).append("</td><td>").append(p45t4).append("</td><td>").append(p45t5).append("</td><td>").append(p45t6).append("</td></tr>");
                                                                                            }
                                                                                        }

                                                                                        piscina46ARef.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                                                                        Integer p46At1 = snapshot.child("t1").getValue(Integer.class);
                                                                                                        Integer p46At2 = snapshot.child("t2").getValue(Integer.class);
                                                                                                        Integer p46At3 = snapshot.child("t3").getValue(Integer.class);
                                                                                                        Integer p46At4 = snapshot.child("t4").getValue(Integer.class);
                                                                                                        Integer p46At5 = snapshot.child("t5").getValue(Integer.class);
                                                                                                        Integer p46At6 = snapshot.child("t6").getValue(Integer.class);
                                                                                                        Integer p46At7 = snapshot.child("t7").getValue(Integer.class);

                                                                                                        p46At1 = (p46At1 != null) ? p46At1 : 0;
                                                                                                        p46At2 = (p46At2 != null) ? p46At2 : 0;
                                                                                                        p46At3 = (p46At3 != null) ? p46At3 : 0;
                                                                                                        p46At4 = (p46At4 != null) ? p46At4 : 0;
                                                                                                        p46At5 = (p46At5 != null) ? p46At5 : 0;
                                                                                                        p46At6 = (p46At6 != null) ? p46At6 : 0;
                                                                                                        p46At7 = (p46At7 != null) ? p46At7 : 0;

                                                                                                        dieta = (dieta != null) ? dieta * 25 : 0;
                                                                                                        recarga = (recarga != null) ? recarga * 25 : 0;
                                                                                                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                                                                                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                                                                                        alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                                                                                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                                                                        htmlBuilder.append("<tr><td>46A</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p46At1).append("</td><td>").append(p46At2).append("</td><td>").append(p46At3).append("</td><td>").append(p46At4).append("</td><td>").append(p46At5).append("</td><td>").append(p46At6).append("</td><td>").append(p46At7).append("</td></tr>");
                                                                                                    }
                                                                                                }

                                                                                                piscina46BRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                                                                                Integer p46Bt1 = snapshot.child("t1").getValue(Integer.class);
                                                                                                                Integer p46Bt2 = snapshot.child("t2").getValue(Integer.class);
                                                                                                                Integer p46Bt3 = snapshot.child("t3").getValue(Integer.class);
                                                                                                                Integer p46Bt4 = snapshot.child("t4").getValue(Integer.class);
                                                                                                                Integer p46Bt5 = snapshot.child("t5").getValue(Integer.class);
                                                                                                                Integer p46Bt6 = snapshot.child("t6").getValue(Integer.class);
                                                                                                                Integer p46Bt7 = snapshot.child("t7").getValue(Integer.class);

                                                                                                                p46Bt1 = (p46Bt1 != null) ? p46Bt1 : 0;
                                                                                                                p46Bt2 = (p46Bt2 != null) ? p46Bt2 : 0;
                                                                                                                p46Bt3 = (p46Bt3 != null) ? p46Bt3 : 0;
                                                                                                                p46Bt4 = (p46Bt4 != null) ? p46Bt4 : 0;
                                                                                                                p46Bt5 = (p46Bt5 != null) ? p46Bt5 : 0;
                                                                                                                p46Bt6 = (p46Bt6 != null) ? p46Bt6 : 0;
                                                                                                                p46Bt7 = (p46Bt7 != null) ? p46Bt7 : 0;

                                                                                                                dieta = (dieta != null) ? dieta * 25 : 0;
                                                                                                                recarga = (recarga != null) ? recarga * 25 : 0;
                                                                                                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                                                                                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                                                                                                alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                                                                                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                                                                                htmlBuilder.append("<tr><td>46B</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p46Bt1).append("</td><td>").append(p46Bt2).append("</td><td>").append(p46Bt3).append("</td><td>").append(p46Bt4).append("</td><td>").append(p46Bt5).append("</td><td>").append(p46Bt6).append("</td><td>").append(p46Bt7).append("</td></tr>");
                                                                                                            }
                                                                                                        }

                                                                                                        // Cerrar la segunda tabla
                                                                                                        htmlBuilder.append("</table></body></html>");





                                                                                                        htmlBuilder.append("<h1>NOVEDADES</h1>");
                                                                                                        htmlBuilder.append("<table border='3'>");
                                                                                                        htmlBuilder.append("<tr><th>PISCINA</th><th>ROJO</th><th>FRESCO</th><th>REPORTADO</th><th>OBSERVACIONES</th></tr>"); // Agregar "OBSERVACIONES"

// Consultar Piscina 1

                                                                                                        piscina37Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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

                                                                                                                        htmlBuilder.append("<tr><td>37</td><td>").append(rojo).append("</td><td>").append(fresco).append("</td><td>").append(reportado).append("</td><td>").append(observaciones).append("</td></tr>");
                                                                                                                    }
                                                                                                                }

                                                                                                                piscina38ARef.addListenerForSingleValueEvent(new ValueEventListener() {
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

                                                                                                                                htmlBuilder.append("<tr><td>38A</td><td>").append(rojo).append("</td><td>").append(fresco).append("</td><td>").append(reportado).append("</td><td>").append(observaciones).append("</td></tr>");
                                                                                                                            }
                                                                                                                        }

                                                                                                                        piscina38BRef.addListenerForSingleValueEvent(new ValueEventListener() {
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

                                                                                                                                        htmlBuilder.append("<tr><td>38B</td><td>").append(rojo).append("</td><td>").append(fresco).append("</td><td>").append(reportado).append("</td><td>").append(observaciones).append("</td></tr>");
                                                                                                                                    }
                                                                                                                                }

                                                                                                                                piscina39Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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

                                                                                                                                                htmlBuilder.append("<tr><td>39</td><td>").append(rojo).append("</td><td>").append(fresco).append("</td><td>").append(reportado).append("</td><td>").append(observaciones).append("</td></tr>");
                                                                                                                                            }
                                                                                                                                        }

                                                                                                                                        piscina40Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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

                                                                                                                                                        htmlBuilder.append("<tr><td>40</td><td>").append(rojo).append("</td><td>").append(fresco).append("</td><td>").append(reportado).append("</td><td>").append(observaciones).append("</td></tr>");
                                                                                                                                                    }
                                                                                                                                                }

                                                                                                                                                piscina41Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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

                                                                                                                                                                htmlBuilder.append("<tr><td>41</td><td>").append(rojo).append("</td><td>").append(fresco).append("</td><td>").append(reportado).append("</td><td>").append(observaciones).append("</td></tr>");
                                                                                                                                                            }
                                                                                                                                                        }

                                                                                                                                                        piscina42Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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

                                                                                                                                                                        htmlBuilder.append("<tr><td>42</td><td>").append(rojo).append("</td><td>").append(fresco).append("</td><td>").append(reportado).append("</td><td>").append(observaciones).append("</td></tr>");
                                                                                                                                                                    }
                                                                                                                                                                }

                                                                                                                                                                piscina43Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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

                                                                                                                                                                                htmlBuilder.append("<tr><td>43</td><td>").append(rojo).append("</td><td>").append(fresco).append("</td><td>").append(reportado).append("</td><td>").append(observaciones).append("</td></tr>");
                                                                                                                                                                            }
                                                                                                                                                                        }

                                                                                                                                                                        piscina44Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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

                                                                                                                                                                                        htmlBuilder.append("<tr><td>44</td><td>").append(rojo).append("</td><td>").append(fresco).append("</td><td>").append(reportado).append("</td><td>").append(observaciones).append("</td></tr>");
                                                                                                                                                                                    }
                                                                                                                                                                                }

                                                                                                                                                                                piscina45Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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

                                                                                                                                                                                                htmlBuilder.append("<tr><td>45</td><td>").append(rojo).append("</td><td>").append(fresco).append("</td><td>").append(reportado).append("</td><td>").append(observaciones).append("</td></tr>");
                                                                                                                                                                                            }
                                                                                                                                                                                        }

                                                                                                                                                                                        piscina46ARef.addListenerForSingleValueEvent(new ValueEventListener() {
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

                                                                                                                                                                                                        htmlBuilder.append("<tr><td>46A</td><td>").append(rojo).append("</td><td>").append(fresco).append("</td><td>").append(reportado).append("</td><td>").append(observaciones).append("</td></tr>");
                                                                                                                                                                                                    }
                                                                                                                                                                                                }

                                                                                                                                                                                                piscina46BRef.addListenerForSingleValueEvent(new ValueEventListener() {
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

                                                                                                                                                                                                                htmlBuilder.append("<tr><td>46B</td><td>").append(rojo).append("</td><td>").append(fresco).append("</td><td>").append(reportado).append("</td><td>").append(observaciones).append("</td></tr>");
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