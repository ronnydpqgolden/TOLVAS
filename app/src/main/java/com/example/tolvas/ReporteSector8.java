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

public class ReporteSector8 extends AppCompatActivity {
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
        DatabaseReference piscina47Ref = databaseReference.child("Piscina 47");
        DatabaseReference piscina48Ref = databaseReference.child("Piscina 48");
        DatabaseReference piscina49Ref = databaseReference.child("Piscina 49");
        DatabaseReference piscina50Ref = databaseReference.child("Piscina 50");
        DatabaseReference piscina51Ref = databaseReference.child("Piscina 51");
        DatabaseReference piscina52Ref = databaseReference.child("Piscina 52");
        DatabaseReference piscina53Ref = databaseReference.child("Piscina 53");
        DatabaseReference piscina54Ref = databaseReference.child("Piscina 54");
        DatabaseReference piscina55Ref = databaseReference.child("Piscina 55");
        DatabaseReference piscina56Ref = databaseReference.child("Piscina 56");

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html><body>");
        htmlBuilder.append("<h1>DATOS PISCINAS SECTOR 8</h1>");
        htmlBuilder.append("<table border='1'>");
        htmlBuilder.append("<tr><th>PISCINA</th><th>DIETA (KG)</th><th>SOBRANTE TOLVA (KG)</th><th>RECARGA (KG)</th><th>AL VOLEO (KG)</th><th>SOBRANTE CASETA (KG)</th><th>TIPO BALANCEADO</th><th>TOLVA 1</th><th>TOLVA 2</th><th>TOLVA 3</th><th>TOLVA 4</th><th>TOLVA 5</th><th>TOLVA 6</th><th>TOLVA 7</th><th>TOLVA 8</th><th>TOLVA 9</th><th>TOLVA 10</th></tr>");

        // Consultar Piscina 47
        piscina47Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                        Integer p47t1 = snapshot.child("t1").getValue(Integer.class);
                        Integer p47t2 = snapshot.child("t2").getValue(Integer.class);
                        Integer p47t3 = snapshot.child("t3").getValue(Integer.class);
                        Integer p47t4 = snapshot.child("t4").getValue(Integer.class);
                        Integer p47t5 = snapshot.child("t5").getValue(Integer.class);
                        Integer p47t6 = snapshot.child("t6").getValue(Integer.class);
                        Integer p47t7 = snapshot.child("t7").getValue(Integer.class);
                        Integer p47t8 = snapshot.child("t8").getValue(Integer.class);

                        p47t1 = (p47t1 != null) ? p47t1 : 0;
                        p47t2 = (p47t2 != null) ? p47t2 : 0;
                        p47t3 = (p47t3 != null) ? p47t3 : 0;
                        p47t4 = (p47t4 != null) ? p47t4 : 0;
                        p47t5 = (p47t5 != null) ? p47t5 : 0;
                        p47t6 = (p47t6 != null) ? p47t6 : 0;
                        p47t7 = (p47t7 != null) ? p47t7 : 0;
                        p47t8 = (p47t8 != null) ? p47t8 : 0;

                        dieta = (dieta != null) ? dieta * 25 : 0;
                        recarga = (recarga != null) ? recarga * 25 : 0;
                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                        alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                        htmlBuilder.append("<tr><td>47</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p47t1).append("</td><td>").append(p47t2).append("</td><td>").append(p47t3).append("</td><td>").append(p47t4).append("</td><td>").append(p47t5).append("</td><td>").append(p47t6).append("</td><td>").append(p47t7).append("</td><td>").append(p47t8).append("</td></tr>");
                    }
                }

                // Consultar Piscina 48
                piscina48Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                Integer p48t1 = snapshot.child("t1").getValue(Integer.class);
                                Integer p48t2 = snapshot.child("t2").getValue(Integer.class);
                                Integer p48t3 = snapshot.child("t3").getValue(Integer.class);
                                Integer p48t4 = snapshot.child("t4").getValue(Integer.class);
                                Integer p48t5 = snapshot.child("t5").getValue(Integer.class);
                                Integer p48t6 = snapshot.child("t6").getValue(Integer.class);

                                p48t1 = (p48t1 != null) ? p48t1 : 0;
                                p48t2 = (p48t2 != null) ? p48t2 : 0;
                                p48t3 = (p48t3 != null) ? p48t3 : 0;
                                p48t4 = (p48t4 != null) ? p48t4 : 0;
                                p48t5 = (p48t5 != null) ? p48t5 : 0;
                                p48t6 = (p48t6 != null) ? p48t6 : 0;

                                dieta = (dieta != null) ? dieta * 25 : 0;
                                recarga = (recarga != null) ? recarga * 25 : 0;
                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                htmlBuilder.append("<tr><td>48</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p48t1).append("</td><td>").append(p48t2).append("</td><td>").append(p48t3).append("</td><td>").append(p48t4).append("</td><td>").append(p48t5).append("</td><td>").append(p48t6).append("</td></tr>");
                            }
                        }

                        // Consultar Piscina 49
                        piscina49Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                        Integer p49t1 = snapshot.child("t1").getValue(Integer.class);
                                        Integer p49t2 = snapshot.child("t2").getValue(Integer.class);
                                        Integer p49t3 = snapshot.child("t3").getValue(Integer.class);
                                        Integer p49t4 = snapshot.child("t4").getValue(Integer.class);
                                        Integer p49t5 = snapshot.child("t5").getValue(Integer.class);
                                        Integer p49t6 = snapshot.child("t6").getValue(Integer.class);

                                        p49t1 = (p49t1 != null) ? p49t1 : 0;
                                        p49t2 = (p49t2 != null) ? p49t2 : 0;
                                        p49t3 = (p49t3 != null) ? p49t3 : 0;
                                        p49t4 = (p49t4 != null) ? p49t4 : 0;
                                        p49t5 = (p49t5 != null) ? p49t5 : 0;
                                        p49t6 = (p49t6 != null) ? p49t6 : 0;

                                        dieta = (dieta != null) ? dieta * 25 : 0;
                                        recarga = (recarga != null) ? recarga * 25 : 0;
                                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                        alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                        htmlBuilder.append("<tr><td>49</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p49t1).append("</td><td>").append(p49t2).append("</td><td>").append(p49t3).append("</td><td>").append(p49t4).append("</td><td>").append(p49t5).append("</td><td>").append(p49t6).append("</td></tr>");
                                    }
                                }


                                //Piscina 50
                                piscina50Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                Integer p50t1 = snapshot.child("t1").getValue(Integer.class);
                                                Integer p50t2 = snapshot.child("t2").getValue(Integer.class);
                                                Integer p50t3 = snapshot.child("t3").getValue(Integer.class);
                                                Integer p50t4 = snapshot.child("t4").getValue(Integer.class);
                                                Integer p50t5 = snapshot.child("t5").getValue(Integer.class);
                                                Integer p50t6 = snapshot.child("t6").getValue(Integer.class);
                                                Integer p50t7 = snapshot.child("t7").getValue(Integer.class);
                                                Integer p50t8 = snapshot.child("t8").getValue(Integer.class);

                                                p50t1 = (p50t1 != null) ? p50t1 : 0;
                                                p50t2 = (p50t2 != null) ? p50t2 : 0;
                                                p50t3 = (p50t3 != null) ? p50t3 : 0;
                                                p50t4 = (p50t4 != null) ? p50t4 : 0;
                                                p50t5 = (p50t5 != null) ? p50t5 : 0;
                                                p50t6 = (p50t6 != null) ? p50t6 : 0;
                                                p50t7 = (p50t7 != null) ? p50t7 : 0;
                                                p50t8 = (p50t8 != null) ? p50t8 : 0;

                                                dieta = (dieta != null) ? dieta * 25 : 0;
                                                recarga = (recarga != null) ? recarga * 25 : 0;
                                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                                alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                htmlBuilder.append("<tr><td>50</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p50t1).append("</td><td>").append(p50t2).append("</td><td>").append(p50t3).append("</td><td>").append(p50t4).append("</td><td>").append(p50t5).append("</td><td>").append(p50t6).append("</td><td>").append(p50t7).append("</td><td>").append(p50t8).append("</td></tr>");
                                            }
                                        }
                                        //PISCINA 51
                                        piscina51Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                        Integer p51t1 = snapshot.child("t1").getValue(Integer.class);
                                                        Integer p51t2 = snapshot.child("t2").getValue(Integer.class);
                                                        Integer p51t3 = snapshot.child("t3").getValue(Integer.class);
                                                        Integer p51t4 = snapshot.child("t4").getValue(Integer.class);
                                                        Integer p51t5 = snapshot.child("t5").getValue(Integer.class);
                                                        Integer p51t6 = snapshot.child("t6").getValue(Integer.class);
                                                        Integer p51t7 = snapshot.child("t7").getValue(Integer.class);
                                                        Integer p51t8 = snapshot.child("t8").getValue(Integer.class);

                                                        p51t1 = (p51t1 != null) ? p51t1 : 0;
                                                        p51t2 = (p51t2 != null) ? p51t2 : 0;
                                                        p51t3 = (p51t3 != null) ? p51t3 : 0;
                                                        p51t4 = (p51t4 != null) ? p51t4 : 0;
                                                        p51t5 = (p51t5 != null) ? p51t5 : 0;
                                                        p51t6 = (p51t6 != null) ? p51t6 : 0;
                                                        p51t7 = (p51t7 != null) ? p51t7 : 0;
                                                        p51t8 = (p51t8 != null) ? p51t8 : 0;

                                                        dieta = (dieta != null) ? dieta * 25 : 0;
                                                        recarga = (recarga != null) ? recarga * 25 : 0;
                                                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                                        alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                        htmlBuilder.append("<tr><td>51</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p51t1).append("</td><td>").append(p51t2).append("</td><td>").append(p51t3).append("</td><td>").append(p51t4).append("</td><td>").append(p51t5).append("</td><td>").append(p51t6).append("</td><td>").append(p51t7).append("</td><td>").append(p51t8).append("</td></tr>");
                                                    }
                                                }

                                                piscina52Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                                Integer p52t1 = snapshot.child("t1").getValue(Integer.class);
                                                                Integer p52t2 = snapshot.child("t2").getValue(Integer.class);
                                                                Integer p52t3 = snapshot.child("t3").getValue(Integer.class);
                                                                Integer p52t4 = snapshot.child("t4").getValue(Integer.class);
                                                                Integer p52t5 = snapshot.child("t5").getValue(Integer.class);
                                                                Integer p52t6 = snapshot.child("t6").getValue(Integer.class);
                                                                Integer p52t7 = snapshot.child("t7").getValue(Integer.class);
                                                                Integer p52t8 = snapshot.child("t8").getValue(Integer.class);
                                                                Integer p52t9 = snapshot.child("t9").getValue(Integer.class);
                                                                Integer p52t10 = snapshot.child("t10").getValue(Integer.class);

                                                                p52t1 = (p52t1 != null) ? p52t1 : 0;
                                                                p52t2 = (p52t2 != null) ? p52t2 : 0;
                                                                p52t3 = (p52t3 != null) ? p52t3 : 0;
                                                                p52t4 = (p52t4 != null) ? p52t4 : 0;
                                                                p52t5 = (p52t5 != null) ? p52t5 : 0;
                                                                p52t6 = (p52t6 != null) ? p52t6 : 0;
                                                                p52t7 = (p52t7 != null) ? p52t7 : 0;
                                                                p52t8 = (p52t8 != null) ? p52t8 : 0;
                                                                p52t9 = (p52t9 != null) ? p52t9 : 0;
                                                                p52t10 = (p52t10 != null) ? p52t10 : 0;

                                                                dieta = (dieta != null) ? dieta * 25 : 0;
                                                                recarga = (recarga != null) ? recarga * 25 : 0;
                                                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                                                alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                                htmlBuilder.append("<tr><td>52</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p52t1).append("</td><td>").append(p52t2).append("</td><td>").append(p52t3).append("</td><td>").append(p52t4).append("</td><td>").append(p52t5).append("</td><td>").append(p52t6).append("</td><td>").append(p52t7).append("</td><td>").append(p52t8).append("</td><td>").append(p52t9).append("</td><td>").append(p52t10).append("</td></tr>");
                                                            }
                                                        }

                                                        piscina53Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                                        Integer p53t1 = snapshot.child("t1").getValue(Integer.class);
                                                                        Integer p53t2 = snapshot.child("t2").getValue(Integer.class);
                                                                        Integer p53t3 = snapshot.child("t3").getValue(Integer.class);
                                                                        Integer p53t4 = snapshot.child("t4").getValue(Integer.class);
                                                                        Integer p53t5 = snapshot.child("t5").getValue(Integer.class);
                                                                        Integer p53t6 = snapshot.child("t6").getValue(Integer.class);

                                                                        p53t1 = (p53t1 != null) ? p53t1 : 0;
                                                                        p53t2 = (p53t2 != null) ? p53t2 : 0;
                                                                        p53t3 = (p53t3 != null) ? p53t3 : 0;
                                                                        p53t4 = (p53t4 != null) ? p53t4 : 0;
                                                                        p53t5 = (p53t5 != null) ? p53t5 : 0;
                                                                        p53t6 = (p53t6 != null) ? p53t6 : 0;

                                                                        dieta = (dieta != null) ? dieta * 25 : 0;
                                                                        recarga = (recarga != null) ? recarga * 25 : 0;
                                                                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                                                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                                                        alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                                                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                                        htmlBuilder.append("<tr><td>53</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p53t1).append("</td><td>").append(p53t2).append("</td><td>").append(p53t3).append("</td><td>").append(p53t4).append("</td><td>").append(p53t5).append("</td><td>").append(p53t6).append("</td></tr>");
                                                                    }
                                                                }

                                                                piscina54Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                                                Integer p54t1 = snapshot.child("t1").getValue(Integer.class);
                                                                                Integer p54t2 = snapshot.child("t2").getValue(Integer.class);
                                                                                Integer p54t3 = snapshot.child("t3").getValue(Integer.class);
                                                                                Integer p54t4 = snapshot.child("t4").getValue(Integer.class);
                                                                                Integer p54t5 = snapshot.child("t5").getValue(Integer.class);
                                                                                Integer p54t6 = snapshot.child("t6").getValue(Integer.class);

                                                                                p54t1 = (p54t1 != null) ? p54t1 : 0;
                                                                                p54t2 = (p54t2 != null) ? p54t2 : 0;
                                                                                p54t3 = (p54t3 != null) ? p54t3 : 0;
                                                                                p54t4 = (p54t4 != null) ? p54t4 : 0;
                                                                                p54t5 = (p54t5 != null) ? p54t5 : 0;
                                                                                p54t6 = (p54t6 != null) ? p54t6 : 0;

                                                                                dieta = (dieta != null) ? dieta * 25 : 0;
                                                                                recarga = (recarga != null) ? recarga * 25 : 0;
                                                                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                                                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                                                                alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                                                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                                                htmlBuilder.append("<tr><td>54</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p54t1).append("</td><td>").append(p54t2).append("</td><td>").append(p54t3).append("</td><td>").append(p54t4).append("</td><td>").append(p54t5).append("</td><td>").append(p54t6).append("</td></tr>");
                                                                            }
                                                                        }


                                                                        piscina55Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                                                        Integer p55t1 = snapshot.child("t1").getValue(Integer.class);
                                                                                        Integer p55t2 = snapshot.child("t2").getValue(Integer.class);
                                                                                        Integer p55t3 = snapshot.child("t3").getValue(Integer.class);
                                                                                        Integer p55t4 = snapshot.child("t4").getValue(Integer.class);
                                                                                        Integer p55t5 = snapshot.child("t5").getValue(Integer.class);
                                                                                        Integer p55t6 = snapshot.child("t6").getValue(Integer.class);

                                                                                        p55t1 = (p55t1 != null) ? p55t1 : 0;
                                                                                        p55t2 = (p55t2 != null) ? p55t2 : 0;
                                                                                        p55t3 = (p55t3 != null) ? p55t3 : 0;
                                                                                        p55t4 = (p55t4 != null) ? p55t4 : 0;
                                                                                        p55t5 = (p55t5 != null) ? p55t5 : 0;
                                                                                        p55t6 = (p55t6 != null) ? p55t6 : 0;

                                                                                        dieta = (dieta != null) ? dieta * 25 : 0;
                                                                                        recarga = (recarga != null) ? recarga * 25 : 0;
                                                                                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                                                                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                                                                        alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                                                                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                                                        htmlBuilder.append("<tr><td>55</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p55t1).append("</td><td>").append(p55t2).append("</td><td>").append(p55t3).append("</td><td>").append(p55t4).append("</td><td>").append(p55t5).append("</td><td>").append(p55t6).append("</td></tr>");
                                                                                    }
                                                                                }

                                                                                piscina56Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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

                                                                                                htmlBuilder.append("<tr><td>56</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p45t1).append("</td><td>").append(p45t2).append("</td><td>").append(p45t3).append("</td><td>").append(p45t4).append("</td><td>").append(p45t5).append("</td><td>").append(p45t6).append("</td></tr>");
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