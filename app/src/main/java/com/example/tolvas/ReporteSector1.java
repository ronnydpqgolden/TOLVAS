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
import java.util.Calendar;
import java.util.Locale;

public class ReporteSector1 extends AppCompatActivity {
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
    private void refreshWebView() {
        // Aquí puedes actualizar el contenido de tu WebView
        webView.reload();
        loadFirebaseData();
        // Termina el estado de actualización cuando el contenido esté listo
        swipeRefreshLayout.setRefreshing(false);
    }

    private void loadFirebaseData() {

        String formattedDate = selectedDate; // Usa el formato de fecha que tienes en Firebase
        DatabaseReference piscina1Ref = databaseReference.child("Piscina 1");
        DatabaseReference piscina2Ref = databaseReference.child("Piscina 2");
        DatabaseReference piscina3ARef = databaseReference.child("Piscina 3A");
        DatabaseReference piscina3BRef = databaseReference.child("Piscina 3B");
        DatabaseReference piscina4Ref = databaseReference.child("Piscina 4");
        DatabaseReference piscina5ARef = databaseReference.child("Piscina 5A");
        DatabaseReference piscina5BRef = databaseReference.child("Piscina 5B");
        DatabaseReference piscina6Ref = databaseReference.child("Piscina 6");
        DatabaseReference piscina7ARef = databaseReference.child("Piscina 7A");
        DatabaseReference piscina7BRef = databaseReference.child("Piscina 7B");
        DatabaseReference piscina8ARef = databaseReference.child("Piscina 8A");
        DatabaseReference piscina8BRef = databaseReference.child("Piscina 8B");

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html><body>");
        htmlBuilder.append("<h1>DATOS PISCINAS SECTOR 1</h1>");
        htmlBuilder.append("<table border='1'>");
        htmlBuilder.append("<tr><th>PISCINA</th><th>DIETA</th><th>SOBRANTE TOLVA</th><th>RECARGA</th><th>AL VOLEO</th><th>SOBRANTE CASETA</th><th>TIPO BALANCEADO</th><th>TOLVA 1</th><th>TOLVA 2</th><th>TOLVA 3</th><th>TOLVA 4</th><th>TOLVA 5</th><th>TOLVA 6</th><th>TOLVA 7</th><th>TOLVA 8</th><th>TOLVA 9</th><th>TOLVA 10</th></tr>");

        // Consultar Piscina 1
        piscina1Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                        Integer p1t1 = snapshot.child("t1").getValue(Integer.class);
                        Integer p1t2 = snapshot.child("t2").getValue(Integer.class);
                        Integer p1t3 = snapshot.child("t3").getValue(Integer.class);
                        Integer p1t4 = snapshot.child("t4").getValue(Integer.class);
                        Integer p1t5 = snapshot.child("t5").getValue(Integer.class);
                        Integer p1t6 = snapshot.child("t6").getValue(Integer.class);
                        Integer p1t7 = snapshot.child("t7").getValue(Integer.class);
                        Integer p1t8 = snapshot.child("t8").getValue(Integer.class);
                        Integer p1t9 = snapshot.child("t9").getValue(Integer.class);
                        Integer p1t10 = snapshot.child("t10").getValue(Integer.class);

                        p1t1 = (p1t1 != null) ? p1t1 : 0;
                        p1t2 = (p1t2 != null) ? p1t2 : 0;
                        p1t3 = (p1t3 != null) ? p1t3 : 0;
                        p1t4 = (p1t4 != null) ? p1t4 : 0;
                        p1t5 = (p1t5 != null) ? p1t5 : 0;
                        p1t6 = (p1t6 != null) ? p1t6 : 0;
                        p1t7 = (p1t7 != null) ? p1t7 : 0;
                        p1t8 = (p1t8 != null) ? p1t8 : 0;
                        p1t9 = (p1t9 != null) ? p1t9 : 0;
                        p1t10 = (p1t10 != null) ? p1t10 : 0;

                        dieta = (dieta != null) ? dieta : 0;
                        recarga = (recarga != null) ? recarga : 0;
                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta : 0;
                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva : 0;
                        alVoleo = (alVoleo != null) ? alVoleo : 0;
                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                        htmlBuilder.append("<tr><td>1</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p1t1).append("</td><td>").append(p1t2).append("</td><td>").append(p1t3).append("</td><td>").append(p1t4).append("</td><td>").append(p1t5).append("</td><td>").append(p1t6).append("</td><td>").append(p1t7).append("</td><td>").append(p1t8).append("</td><td>").append(p1t9).append("</td><td>").append(p1t10).append("</td></tr>");
                    }
                }

                // Consultar Piscina 2
                piscina2Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                Integer p9t7 = snapshot.child("t7").getValue(Integer.class);

                                p9t1 = (p9t1 != null) ? p9t1 : 0;
                                p9t2 = (p9t2 != null) ? p9t2 : 0;
                                p9t3 = (p9t3 != null) ? p9t3 : 0;
                                p9t4 = (p9t4 != null) ? p9t4 : 0;
                                p9t5 = (p9t5 != null) ? p9t5 : 0;
                                p9t6 = (p9t6 != null) ? p9t6 : 0;
                                p9t7 = (p9t7 != null) ? p9t7 : 0;

                                dieta = (dieta != null) ? dieta : 0;
                                recarga = (recarga != null) ? recarga : 0;
                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta : 0;
                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva : 0;
                                alVoleo = (alVoleo != null) ? alVoleo : 0;
                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                htmlBuilder.append("<tr><td>2</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p9t1).append("</td><td>").append(p9t2).append("</td><td>").append(p9t3).append("</td><td>").append(p9t4).append("</td><td>").append(p9t5).append("</td><td>").append(p9t6).append("</td><td>").append(p9t7).append("</td></tr>");
                            }
                        }



                        // Consultar Piscina 3A
                        piscina3ARef.addListenerForSingleValueEvent(new ValueEventListener() {
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

                                        dieta = (dieta != null) ? dieta : 0;
                                        recarga = (recarga != null) ? recarga : 0;
                                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta : 0;
                                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva : 0;
                                        alVoleo = (alVoleo != null) ? alVoleo : 0;
                                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                        htmlBuilder.append("<tr><td>3A</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p3t1).append("</td><td>").append(p3t2).append("</td><td>").append(p3t3).append("</td><td>").append(p3t4).append("</td><td>").append(p3t5).append("</td><td>").append(p3t6).append("</td></tr>");
                                    }
                                }



                                piscina3BRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                Integer p3Bt1 = snapshot.child("t1").getValue(Integer.class);
                                                Integer p3Bt2 = snapshot.child("t2").getValue(Integer.class);
                                                Integer p3Bt3 = snapshot.child("t3").getValue(Integer.class);
                                                Integer p3Bt4 = snapshot.child("t4").getValue(Integer.class);
                                                Integer p3Bt5 = snapshot.child("t5").getValue(Integer.class);
                                                Integer p3Bt6 = snapshot.child("t6").getValue(Integer.class);

                                                p3Bt1 = (p3Bt1 != null) ? p3Bt1 : 0;
                                                p3Bt2 = (p3Bt2 != null) ? p3Bt2 : 0;
                                                p3Bt3 = (p3Bt3 != null) ? p3Bt3 : 0;
                                                p3Bt4 = (p3Bt4 != null) ? p3Bt4 : 0;
                                                p3Bt5 = (p3Bt5 != null) ? p3Bt5 : 0;
                                                p3Bt6 = (p3Bt6 != null) ? p3Bt6 : 0;

                                                dieta = (dieta != null) ? dieta : 0;
                                                recarga = (recarga != null) ? recarga : 0;
                                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta : 0;
                                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva : 0;
                                                alVoleo = (alVoleo != null) ? alVoleo : 0;
                                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                htmlBuilder.append("<tr><td>3B</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p3Bt1).append("</td><td>").append(p3Bt2).append("</td><td>").append(p3Bt3).append("</td><td>").append(p3Bt4).append("</td><td>").append(p3Bt5).append("</td><td>").append(p3Bt6).append("</td></tr>");
                                            }
                                        }

                                        piscina4Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                        Integer p4t1 = snapshot.child("t1").getValue(Integer.class);
                                                        Integer p4t2 = snapshot.child("t2").getValue(Integer.class);
                                                        Integer p4t3 = snapshot.child("t3").getValue(Integer.class);
                                                        Integer p4t4 = snapshot.child("t4").getValue(Integer.class);
                                                        Integer p4t5 = snapshot.child("t3").getValue(Integer.class);
                                                        Integer p4t6 = snapshot.child("t4").getValue(Integer.class);

                                                        p4t1 = (p4t1 != null) ? p4t1 : 0;
                                                        p4t2 = (p4t2 != null) ? p4t2 : 0;
                                                        p4t3 = (p4t3 != null) ? p4t3 : 0;
                                                        p4t4 = (p4t4 != null) ? p4t4 : 0;
                                                        p4t5 = (p4t5 != null) ? p4t5 : 0;
                                                        p4t6 = (p4t6 != null) ? p4t6 : 0;

                                                        dieta = (dieta != null) ? dieta : 0;
                                                        recarga = (recarga != null) ? recarga : 0;
                                                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta : 0;
                                                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva : 0;
                                                        alVoleo = (alVoleo != null) ? alVoleo : 0;
                                                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                        htmlBuilder.append("<tr><td>4</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p4t1).append("</td><td>").append(p4t2).append("</td><td>").append(p4t3).append("</td><td>").append(p4t4).append("</td><td>").append(p4t5).append("</td><td>").append(p4t6).append("</td></tr>");
                                                    }
                                                }

                                                piscina5ARef.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                                Integer p5At1 = snapshot.child("t1").getValue(Integer.class);
                                                                Integer p5At2 = snapshot.child("t2").getValue(Integer.class);
                                                                Integer p5At3 = snapshot.child("t3").getValue(Integer.class);
                                                                Integer p5At4 = snapshot.child("t4").getValue(Integer.class);
                                                                Integer p5At5 = snapshot.child("t5").getValue(Integer.class);
                                                                Integer p5At6 = snapshot.child("t6").getValue(Integer.class);
                                                                Integer p5At7 = snapshot.child("t7").getValue(Integer.class);
                                                                Integer p5At8 = snapshot.child("t8").getValue(Integer.class);
                                                                Integer p5At9 = snapshot.child("t9").getValue(Integer.class);

                                                                p5At1 = (p5At1 != null) ? p5At1 : 0;
                                                                p5At2 = (p5At2 != null) ? p5At2 : 0;
                                                                p5At3 = (p5At3 != null) ? p5At3 : 0;
                                                                p5At4 = (p5At4 != null) ? p5At4 : 0;
                                                                p5At5 = (p5At5 != null) ? p5At5 : 0;
                                                                p5At6 = (p5At6 != null) ? p5At6 : 0;
                                                                p5At7 = (p5At7 != null) ? p5At7 : 0;
                                                                p5At8 = (p5At8 != null) ? p5At8 : 0;
                                                                p5At9 = (p5At9 != null) ? p5At9 : 0;

                                                                dieta = (dieta != null) ? dieta : 0;
                                                                recarga = (recarga != null) ? recarga : 0;
                                                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta : 0;
                                                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva : 0;
                                                                alVoleo = (alVoleo != null) ? alVoleo : 0;
                                                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                                htmlBuilder.append("<tr><td>5A</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p5At1).append("</td><td>").append(p5At2).append("</td><td>").append(p5At3).append("</td><td>").append(p5At4).append("</td><td>").append(p5At5).append("</td><td>").append(p5At6).append("</td><td>").append(p5At7).append("</td><td>").append(p5At8).append("</td><td>").append(p5At9).append("</td></tr>");
                                                            }
                                                        }

                                                        piscina5BRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                                        Integer p7t1 = snapshot.child("t1").getValue(Integer.class);
                                                                        Integer p7t2 = snapshot.child("t2").getValue(Integer.class);
                                                                        Integer p7t3 = snapshot.child("t3").getValue(Integer.class);
                                                                        Integer p7t4 = snapshot.child("t4").getValue(Integer.class);
                                                                        Integer p7t5 = snapshot.child("t5").getValue(Integer.class);
                                                                        Integer p7t6 = snapshot.child("t6").getValue(Integer.class);
                                                                        Integer p7t7 = snapshot.child("t7").getValue(Integer.class);
                                                                        Integer p7t8 = snapshot.child("t8").getValue(Integer.class);
                                                                        Integer p7t9 = snapshot.child("t9").getValue(Integer.class);
                                                                        Integer p7t10 = snapshot.child("t10").getValue(Integer.class);

                                                                        p7t1 = (p7t1 != null) ? p7t1 : 0;
                                                                        p7t2 = (p7t2 != null) ? p7t2 : 0;
                                                                        p7t3 = (p7t3 != null) ? p7t3 : 0;
                                                                        p7t4 = (p7t4 != null) ? p7t4 : 0;
                                                                        p7t5 = (p7t5 != null) ? p7t5 : 0;
                                                                        p7t6 = (p7t6 != null) ? p7t6 : 0;
                                                                        p7t7 = (p7t7 != null) ? p7t7 : 0;
                                                                        p7t8 = (p7t8 != null) ? p7t8 : 0;
                                                                        p7t9 = (p7t9 != null) ? p7t9 : 0;
                                                                        p7t10 = (p7t10 != null) ? p7t10 : 0;

                                                                        dieta = (dieta != null) ? dieta : 0;
                                                                        recarga = (recarga != null) ? recarga : 0;
                                                                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta : 0;
                                                                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva : 0;
                                                                        alVoleo = (alVoleo != null) ? alVoleo : 0;
                                                                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                                        htmlBuilder.append("<tr><td>5B</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p7t1).append("</td><td>").append(p7t2).append("</td><td>").append(p7t3).append("</td><td>").append(p7t4).append("</td><td>").append(p7t5).append("</td><td>").append(p7t6).append("</td><td>").append(p7t7).append("</td><td>").append(p7t8).append("</td><td>").append(p7t9).append("</td><td>").append(p7t10).append("</td></tr>");
                                                                    }
                                                                }

                                                                piscina6Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                                                // Inicializar tolvas a 0 si los valores son nulos
                                                                                Integer p6t1 = snapshot.child("t1").getValue(Integer.class);
                                                                                Integer p6t2 = snapshot.child("t2").getValue(Integer.class);
                                                                                Integer p6t3 = snapshot.child("t3").getValue(Integer.class);
                                                                                Integer p6t4 = snapshot.child("t4").getValue(Integer.class);
                                                                                Integer p6t5 = snapshot.child("t5").getValue(Integer.class);
                                                                                Integer p6t6 = snapshot.child("t6").getValue(Integer.class);
                                                                                Integer p6t7 = snapshot.child("t7").getValue(Integer.class);
                                                                                Integer p6t8 = snapshot.child("t8").getValue(Integer.class);
                                                                                Integer p6t9 = snapshot.child("t9").getValue(Integer.class);
                                                                                Integer p6t10 = snapshot.child("t10").getValue(Integer.class);

                                                                                p6t1 = (p6t1 != null) ? p6t1 : 0;
                                                                                p6t2 = (p6t2 != null) ? p6t2 : 0;
                                                                                p6t3 = (p6t3 != null) ? p6t3 : 0;
                                                                                p6t4 = (p6t4 != null) ? p6t4 : 0;
                                                                                p6t5 = (p6t5 != null) ? p6t5 : 0;
                                                                                p6t6 = (p6t6 != null) ? p6t6 : 0;
                                                                                p6t7 = (p6t7 != null) ? p6t7 : 0;
                                                                                p6t8 = (p6t8 != null) ? p6t8 : 0;
                                                                                p6t9 = (p6t9 != null) ? p6t9 : 0;
                                                                                p6t10 = (p6t10 != null) ? p6t10 : 0;

                                                                                dieta = (dieta != null) ? dieta : 0;
                                                                                recarga = (recarga != null) ? recarga : 0;
                                                                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta : 0;
                                                                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva : 0;
                                                                                alVoleo = (alVoleo != null) ? alVoleo : 0;
                                                                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                                                htmlBuilder.append("<tr><td>6</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p6t1).append("</td><td>").append(p6t2).append("</td><td>").append(p6t3).append("</td><td>").append(p6t4).append("</td><td>").append(p6t5).append("</td><td>").append(p6t6).append("</td><td>").append(p6t7).append("</td><td>").append(p6t8).append("</td><td>").append(p6t9).append("</td><td>").append(p6t10).append("</td></tr>");
                                                                            }
                                                                        }

                                                                        piscina7ARef.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                                                        Integer p7At1 = snapshot.child("t1").getValue(Integer.class);
                                                                                        Integer p7At2 = snapshot.child("t2").getValue(Integer.class);
                                                                                        Integer p7At3 = snapshot.child("t3").getValue(Integer.class);
                                                                                        Integer p7At4 = snapshot.child("t4").getValue(Integer.class);
                                                                                        Integer p7At5 = snapshot.child("t5").getValue(Integer.class);
                                                                                        Integer p7At6 = snapshot.child("t6").getValue(Integer.class);
                                                                                        Integer p7At7 = snapshot.child("t7").getValue(Integer.class);
                                                                                        Integer p7At8 = snapshot.child("t8").getValue(Integer.class);
                                                                                        Integer p7At9 = snapshot.child("t9").getValue(Integer.class);
                                                                                        Integer p7At10 = snapshot.child("t10").getValue(Integer.class);

                                                                                        p7At1 = (p7At1 != null) ? p7At1 : 0;
                                                                                        p7At2 = (p7At2 != null) ? p7At2 : 0;
                                                                                        p7At3 = (p7At3 != null) ? p7At3 : 0;
                                                                                        p7At4 = (p7At4 != null) ? p7At4 : 0;
                                                                                        p7At5 = (p7At5 != null) ? p7At5 : 0;
                                                                                        p7At6 = (p7At6 != null) ? p7At6 : 0;
                                                                                        p7At7 = (p7At7 != null) ? p7At7 : 0;
                                                                                        p7At8 = (p7At8 != null) ? p7At8 : 0;
                                                                                        p7At9 = (p7At9 != null) ? p7At9 : 0;
                                                                                        p7At10 = (p7At10 != null) ? p7At10 : 0;

                                                                                        dieta = (dieta != null) ? dieta : 0;
                                                                                        recarga = (recarga != null) ? recarga : 0;
                                                                                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta : 0;
                                                                                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva : 0;
                                                                                        alVoleo = (alVoleo != null) ? alVoleo : 0;
                                                                                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                                                        htmlBuilder.append("<tr><td>7A</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p7At1).append("</td><td>").append(p7At2).append("</td><td>").append(p7At3).append("</td><td>").append(p7At4).append("</td><td>").append(p7At5).append("</td><td>").append(p7At6).append("</td><td>").append(p7At7).append("</td><td>").append(p7At8).append("</td><td>").append(p7At9).append("</td><td>").append(p7At10).append("</td></tr>");
                                                                                    }
                                                                                }

                                                                                piscina7BRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                                                                Integer p8t1 = snapshot.child("t1").getValue(Integer.class);
                                                                                                Integer p8t2 = snapshot.child("t2").getValue(Integer.class);
                                                                                                Integer p8t3 = snapshot.child("t3").getValue(Integer.class);
                                                                                                Integer p8t4 = snapshot.child("t4").getValue(Integer.class);
                                                                                                Integer p8t5 = snapshot.child("t5").getValue(Integer.class);
                                                                                                Integer p8t6 = snapshot.child("t6").getValue(Integer.class);
                                                                                                Integer p8t7 = snapshot.child("t7").getValue(Integer.class);
                                                                                                Integer p8t8 = snapshot.child("t8").getValue(Integer.class);
                                                                                                Integer p8t9 = snapshot.child("t9").getValue(Integer.class);

                                                                                                p8t1 = (p8t1 != null) ? p8t1 : 0;
                                                                                                p8t2 = (p8t2 != null) ? p8t2 : 0;
                                                                                                p8t3 = (p8t3 != null) ? p8t3 : 0;
                                                                                                p8t4 = (p8t4 != null) ? p8t4 : 0;
                                                                                                p8t5 = (p8t5 != null) ? p8t5 : 0;
                                                                                                p8t6 = (p8t6 != null) ? p8t6 : 0;
                                                                                                p8t7 = (p8t7 != null) ? p8t7 : 0;
                                                                                                p8t8 = (p8t8 != null) ? p8t8 : 0;
                                                                                                p8t9 = (p8t9 != null) ? p8t9 : 0;

                                                                                                dieta = (dieta != null) ? dieta : 0;
                                                                                                recarga = (recarga != null) ? recarga : 0;
                                                                                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta : 0;
                                                                                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva : 0;
                                                                                                alVoleo = (alVoleo != null) ? alVoleo : 0;
                                                                                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                                                                htmlBuilder.append("<tr><td>8A</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p8t1).append("</td><td>").append(p8t2).append("</td><td>").append(p8t3).append("</td><td>").append(p8t4).append("</td><td>").append(p8t5).append("</td><td>").append(p8t6).append("</td><td>").append(p8t7).append("</td><td>").append(p8t8).append("</td><td>").append(p8t9).append("</td></tr>");
                                                                                            }
                                                                                        }

                                                                                        piscina8ARef.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                                                                        Integer p8t1 = snapshot.child("t1").getValue(Integer.class);
                                                                                                        Integer p8t2 = snapshot.child("t2").getValue(Integer.class);
                                                                                                        Integer p8t3 = snapshot.child("t3").getValue(Integer.class);
                                                                                                        Integer p8t4 = snapshot.child("t4").getValue(Integer.class);
                                                                                                        Integer p8t5 = snapshot.child("t5").getValue(Integer.class);
                                                                                                        Integer p8t6 = snapshot.child("t6").getValue(Integer.class);
                                                                                                        Integer p8t7 = snapshot.child("t7").getValue(Integer.class);
                                                                                                        Integer p8t8 = snapshot.child("t8").getValue(Integer.class);
                                                                                                        Integer p8t9 = snapshot.child("t9").getValue(Integer.class);

                                                                                                        p8t1 = (p8t1 != null) ? p8t1 : 0;
                                                                                                        p8t2 = (p8t2 != null) ? p8t2 : 0;
                                                                                                        p8t3 = (p8t3 != null) ? p8t3 : 0;
                                                                                                        p8t4 = (p8t4 != null) ? p8t4 : 0;
                                                                                                        p8t5 = (p8t5 != null) ? p8t5 : 0;
                                                                                                        p8t6 = (p8t6 != null) ? p8t6 : 0;
                                                                                                        p8t7 = (p8t7 != null) ? p8t7 : 0;
                                                                                                        p8t8 = (p8t8 != null) ? p8t8 : 0;
                                                                                                        p8t9 = (p8t9 != null) ? p8t9 : 0;

                                                                                                        dieta = (dieta != null) ? dieta : 0;
                                                                                                        recarga = (recarga != null) ? recarga : 0;
                                                                                                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta : 0;
                                                                                                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva : 0;
                                                                                                        alVoleo = (alVoleo != null) ? alVoleo : 0;
                                                                                                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                                                                        htmlBuilder.append("<tr><td>8A</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p8t1).append("</td><td>").append(p8t2).append("</td><td>").append(p8t3).append("</td><td>").append(p8t4).append("</td><td>").append(p8t5).append("</td><td>").append(p8t6).append("</td><td>").append(p8t7).append("</td><td>").append(p8t8).append("</td><td>").append(p8t9).append("</td></tr>");
                                                                                                    }
                                                                                                }

                                                                                                piscina8BRef.addListenerForSingleValueEvent(new ValueEventListener() {
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

                                                                                                                dieta = (dieta != null) ? dieta : 0;
                                                                                                                recarga = (recarga != null) ? recarga : 0;
                                                                                                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta : 0;
                                                                                                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva : 0;
                                                                                                                alVoleo = (alVoleo != null) ? alVoleo : 0;
                                                                                                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                                                                                htmlBuilder.append("<tr><td>8B</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p46Bt1).append("</td><td>").append(p46Bt2).append("</td><td>").append(p46Bt3).append("</td><td>").append(p46Bt4).append("</td><td>").append(p46Bt5).append("</td><td>").append(p46Bt6).append("</td><td>").append(p46Bt7).append("</td></tr>");
                                                                                                            }
                                                                                                        }

                                                                                                        htmlBuilder.append("</table></body></html>");
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
}