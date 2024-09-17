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

public class ReporteSector6 extends AppCompatActivity {
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

    private void refreshWebView() {
        // Aquí puedes actualizar el contenido de tu WebView
        webView.reload();
        loadFirebaseData();
        // Termina el estado de actualización cuando el contenido esté listo
        swipeRefreshLayout.setRefreshing(false);
    }

    private void loadFirebaseData() {
        String formattedDate = selectedDate; // Usa el formato de fecha que tienes en Firebase
        DatabaseReference piscina32Ref = databaseReference.child("Piscina 32");
        DatabaseReference piscina33Ref = databaseReference.child("Piscina 33");
        DatabaseReference piscina34Ref = databaseReference.child("Piscina 34");
        DatabaseReference piscina35ARef = databaseReference.child("Piscina 35A");
        DatabaseReference piscina35BRef = databaseReference.child("Piscina 35B");
        DatabaseReference piscina36ARef = databaseReference.child("Piscina 36A");
        DatabaseReference piscina36BRef = databaseReference.child("Piscina 36B");

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html><body>");
        htmlBuilder.append("<h1>DATOS PISCINAS SECTOR 2</h1>");
        htmlBuilder.append("<table border='1'>");
        htmlBuilder.append("<tr><th>PISCINA</th><th>DIETA</th><th>SOBRANTE TOLVA</th><th>RECARGA</th><th>AL VOLEO</th><th>SOBRANTE CASETA</th><th>TIPO BALANCEADO</th><th>TOLVA 1</th><th>TOLVA 2</th><th>TOLVA 3</th><th>TOLVA 4</th><th>TOLVA 5</th><th>TOLVA 6</th><th>TOLVA 7</th><th>TOLVA 8</th><th>TOLVA 9</th><th>TOLVA 10</th></tr>");

        // Consultar Piscina 32
        piscina32Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                        Integer p32t1 = snapshot.child("t1").getValue(Integer.class);
                        Integer p32t2 = snapshot.child("t2").getValue(Integer.class);
                        Integer p32t3 = snapshot.child("t3").getValue(Integer.class);
                        Integer p32t4 = snapshot.child("t4").getValue(Integer.class);
                        Integer p32t5 = snapshot.child("t5").getValue(Integer.class);

                        p32t1 = (p32t1 != null) ? p32t1 : 0;
                        p32t2 = (p32t2 != null) ? p32t2 : 0;
                        p32t3 = (p32t3 != null) ? p32t3 : 0;
                        p32t4 = (p32t4 != null) ? p32t4 : 0;
                        p32t5 = (p32t5 != null) ? p32t5 : 0;

                        dieta = (dieta != null) ? dieta : 0;
                        recarga = (recarga != null) ? recarga : 0;
                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta : 0;
                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva : 0;
                        alVoleo = (alVoleo != null) ? alVoleo : 0;
                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                        htmlBuilder.append("<tr><td>32</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p32t1).append("</td><td>").append(p32t2).append("</td><td>").append(p32t3).append("</td><td>").append(p32t4).append("</td><td>").append(p32t5).append("</td></tr>");
                    }
                }

                // Consultar Piscina 33
                piscina33Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                Integer p33t1 = snapshot.child("t1").getValue(Integer.class);
                                Integer p33t2 = snapshot.child("t2").getValue(Integer.class);
                                Integer p33t3 = snapshot.child("t3").getValue(Integer.class);
                                Integer p33t4 = snapshot.child("t4").getValue(Integer.class);
                                Integer p33t5 = snapshot.child("t5").getValue(Integer.class);
                                Integer p33t6 = snapshot.child("t6").getValue(Integer.class);
                                Integer p33t7 = snapshot.child("t7").getValue(Integer.class);
                                Integer p33t8 = snapshot.child("t8").getValue(Integer.class);

                                p33t1 = (p33t1 != null) ? p33t1 : 0;
                                p33t2 = (p33t2 != null) ? p33t2 : 0;
                                p33t3 = (p33t3 != null) ? p33t3 : 0;
                                p33t4 = (p33t4 != null) ? p33t4 : 0;
                                p33t5 = (p33t5 != null) ? p33t5 : 0;
                                p33t6 = (p33t6 != null) ? p33t6 : 0;
                                p33t7 = (p33t7 != null) ? p33t7 : 0;
                                p33t8 = (p33t8 != null) ? p33t8 : 0;

                                dieta = (dieta != null) ? dieta : 0;
                                recarga = (recarga != null) ? recarga : 0;
                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta : 0;
                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva : 0;
                                alVoleo = (alVoleo != null) ? alVoleo : 0;
                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                htmlBuilder.append("<tr><td>33</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p33t1).append("</td><td>").append(p33t2).append("</td><td>").append(p33t3).append("</td><td>").append(p33t4).append("</td><td>").append(p33t5).append("</td><td>").append(p33t6).append("</td><td>").append(p33t7).append("</td><td>").append(p33t8).append("</td></tr>");
                            }
                        }

                        // Consultar Piscina 34
                        piscina34Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                        Integer p34t1 = snapshot.child("t1").getValue(Integer.class);
                                        Integer p34t2 = snapshot.child("t2").getValue(Integer.class);
                                        Integer p34t3 = snapshot.child("t3").getValue(Integer.class);
                                        Integer p34t4 = snapshot.child("t4").getValue(Integer.class);
                                        Integer p34t5 = snapshot.child("t5").getValue(Integer.class);
                                        Integer p34t6 = snapshot.child("t6").getValue(Integer.class);
                                        Integer p34t7 = snapshot.child("t7").getValue(Integer.class);
                                        Integer p34t8 = snapshot.child("t8").getValue(Integer.class);


                                        p34t1 = (p34t1 != null) ? p34t1 : 0;
                                        p34t2 = (p34t2 != null) ? p34t2 : 0;
                                        p34t3 = (p34t3 != null) ? p34t3 : 0;
                                        p34t4 = (p34t4 != null) ? p34t4 : 0;
                                        p34t5 = (p34t5 != null) ? p34t5 : 0;
                                        p34t6 = (p34t6 != null) ? p34t6 : 0;
                                        p34t7 = (p34t7 != null) ? p34t7 : 0;
                                        p34t8 = (p34t8 != null) ? p34t8 : 0;

                                        dieta = (dieta != null) ? dieta : 0;
                                        recarga = (recarga != null) ? recarga : 0;
                                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta : 0;
                                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva : 0;
                                        alVoleo = (alVoleo != null) ? alVoleo : 0;
                                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                        htmlBuilder.append("<tr><td>34</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p34t1).append("</td><td>").append(p34t2).append("</td><td>").append(p34t3).append("</td><td>").append(p34t4).append("</td><td>").append(p34t5).append("</td><td>").append(p34t6).append("</td><td>").append(p34t7).append("</td><td>").append(p34t8).append("</td></tr>");
                                    }
                                }

                                piscina35ARef.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                Integer p35At1 = snapshot.child("t1").getValue(Integer.class);
                                                Integer p35At2 = snapshot.child("t2").getValue(Integer.class);
                                                Integer p35At3 = snapshot.child("t3").getValue(Integer.class);
                                                Integer p35At4 = snapshot.child("t4").getValue(Integer.class);
                                                Integer p35At5 = snapshot.child("t5").getValue(Integer.class);
                                                Integer p35At6 = snapshot.child("t6").getValue(Integer.class);
                                                Integer p35At7 = snapshot.child("t7").getValue(Integer.class);
                                                Integer p35At8 = snapshot.child("t8").getValue(Integer.class);

                                                p35At1 = (p35At1 != null) ? p35At1 : 0;
                                                p35At2 = (p35At2 != null) ? p35At2 : 0;
                                                p35At3 = (p35At3 != null) ? p35At3 : 0;
                                                p35At4 = (p35At4 != null) ? p35At4 : 0;
                                                p35At5 = (p35At5 != null) ? p35At5 : 0;
                                                p35At6 = (p35At6 != null) ? p35At6 : 0;
                                                p35At7 = (p35At7 != null) ? p35At7 : 0;
                                                p35At8 = (p35At8 != null) ? p35At8 : 0;

                                                dieta = (dieta != null) ? dieta : 0;
                                                recarga = (recarga != null) ? recarga : 0;
                                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta : 0;
                                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva : 0;
                                                alVoleo = (alVoleo != null) ? alVoleo : 0;
                                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                htmlBuilder.append("<tr><td>35A</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>")
                                                        .append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p35At1).append("</td><td>")
                                                        .append(p35At2).append("</td><td>").append(p35At3).append("</td><td>").append(p35At4).append("</td><td>").append(p35At5).append("</td><td>").append(p35At6)
                                                        .append("</td><td>").append(p35At7).append("</td><td>").append(p35At8).append("</td></tr>");
                                            }
                                        }

                                        piscina35BRef.addListenerForSingleValueEvent(new ValueEventListener() {
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

                                                        dieta = (dieta != null) ? dieta : 0;
                                                        recarga = (recarga != null) ? recarga : 0;
                                                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta : 0;
                                                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva : 0;
                                                        alVoleo = (alVoleo != null) ? alVoleo : 0;
                                                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                        htmlBuilder.append("<tr><td>35B</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p12t1).append("</td><td>").append(p12t2).append("</td><td>").append(p12t3).append("</td><td>").append(p12t4).append("</td><td>").append(p12t5).append("</td><td>").append(p12t6).append("</td></tr>");
                                                    }
                                                }

                                                piscina36ARef.addListenerForSingleValueEvent(new ValueEventListener() {
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

                                                                p13t1 = (p13t1 != null) ? p13t1 : 0;
                                                                p13t2 = (p13t2 != null) ? p13t2 : 0;
                                                                p13t3 = (p13t3 != null) ? p13t3 : 0;
                                                                p13t4 = (p13t4 != null) ? p13t4 : 0;
                                                                p13t5 = (p13t5 != null) ? p13t5 : 0;

                                                                dieta = (dieta != null) ? dieta : 0;
                                                                recarga = (recarga != null) ? recarga : 0;
                                                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta : 0;
                                                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva : 0;
                                                                alVoleo = (alVoleo != null) ? alVoleo : 0;
                                                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                                htmlBuilder.append("<tr><td>36A</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p13t1).append("</td><td>").append(p13t2).append("</td><td>").append(p13t3).append("</td><td>").append(p13t4).append("</td><td>").append(p13t5).append("</td></tr>");
                                                            }
                                                        }

                                                        piscina36BRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                                        Integer p36Bt1 = snapshot.child("t1").getValue(Integer.class);
                                                                        Integer p36Bt2 = snapshot.child("t2").getValue(Integer.class);
                                                                        Integer p36Bt3 = snapshot.child("t3").getValue(Integer.class);
                                                                        Integer p36Bt4 = snapshot.child("t4").getValue(Integer.class);
                                                                        Integer p36Bt5 = snapshot.child("t5").getValue(Integer.class);
                                                                        Integer p36Bt6 = snapshot.child("t6").getValue(Integer.class);
                                                                        Integer p36Bt7 = snapshot.child("t7").getValue(Integer.class);

                                                                        p36Bt1 = (p36Bt1 != null) ? p36Bt1 : 0;
                                                                        p36Bt2 = (p36Bt2 != null) ? p36Bt2 : 0;
                                                                        p36Bt3 = (p36Bt3 != null) ? p36Bt3 : 0;
                                                                        p36Bt4 = (p36Bt4 != null) ? p36Bt4 : 0;
                                                                        p36Bt5 = (p36Bt5 != null) ? p36Bt5 : 0;
                                                                        p36Bt6 = (p36Bt6 != null) ? p36Bt6 : 0;
                                                                        p36Bt7 = (p36Bt7 != null) ? p36Bt7 : 0;

                                                                        dieta = (dieta != null) ? dieta : 0;
                                                                        recarga = (recarga != null) ? recarga : 0;
                                                                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta : 0;
                                                                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva : 0;
                                                                        alVoleo = (alVoleo != null) ? alVoleo : 0;
                                                                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                                        htmlBuilder.append("<tr><td>35B</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p36Bt1).append("</td><td>").append(p36Bt2).append("</td><td>").append(p36Bt3).append("</td><td>").append(p36Bt4).append("</td><td>").append(p36Bt5).append("</td><td>").append(p36Bt6).append("</td><td>").append(p36Bt7).append("</td></tr>");
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