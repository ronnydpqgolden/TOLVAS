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

public class ReporteSector5 extends AppCompatActivity {
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
        DatabaseReference piscina27Ref = databaseReference.child("Piscina 27");
        DatabaseReference piscina28Ref = databaseReference.child("Piscina 28");
        DatabaseReference piscina29Ref = databaseReference.child("Piscina 29");
        DatabaseReference piscina30Ref = databaseReference.child("Piscina 30");
        DatabaseReference piscina31Ref = databaseReference.child("Piscina 31");

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html><body>");
        htmlBuilder.append("<h1>DATOS PISCINAS SECTOR 5</h1>");
        htmlBuilder.append("<table border='1'>");
        htmlBuilder.append("<tr><th>PISCINA</th><th>DIETA</th><th>SOBRANTE TOLVA</th><th>RECARGA</th><th>AL VOLEO</th><th>SOBRANTE CASETA</th><th>TIPO BALANCEADO</th><th>TOLVA 1</th><th>TOLVA 2</th><th>TOLVA 3</th><th>TOLVA 4</th><th>TOLVA 5</th><th>TOLVA 6</th><th>TOLVA 7</th><th>TOLVA 8</th><th>TOLVA 9</th><th>TOLVA 10</th></tr>");

        // Consultar Piscina 27
        piscina27Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                        Integer p27t1 = snapshot.child("t1").getValue(Integer.class);
                        Integer p27t2 = snapshot.child("t2").getValue(Integer.class);
                        Integer p27t3 = snapshot.child("t3").getValue(Integer.class);
                        Integer p27t4 = snapshot.child("t4").getValue(Integer.class);
                        Integer p27t5 = snapshot.child("t5").getValue(Integer.class);
                        Integer p27t6 = snapshot.child("t6").getValue(Integer.class);
                        Integer p27t7 = snapshot.child("t7").getValue(Integer.class);

                        p27t1 = (p27t1 != null) ? p27t1 : 0;
                        p27t2 = (p27t2 != null) ? p27t2 : 0;
                        p27t3 = (p27t3 != null) ? p27t3 : 0;
                        p27t4 = (p27t4 != null) ? p27t4 : 0;
                        p27t5 = (p27t5 != null) ? p27t5 : 0;
                        p27t6 = (p27t6 != null) ? p27t6 : 0;
                        p27t7 = (p27t7 != null) ? p27t7 : 0;

                        dieta = (dieta != null) ? dieta : 0;
                        recarga = (recarga != null) ? recarga : 0;
                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta : 0;
                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva : 0;
                        alVoleo = (alVoleo != null) ? alVoleo : 0;
                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";
                        htmlBuilder.append("<tr><td>27</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p27t1).append("</td><td>").append(p27t2).append("</td><td>").append(p27t3).append("</td><td>").append(p27t4).append("</td><td>").append(p27t5).append("</td><td>").append(p27t6).append("</td><td>").append(p27t7).append("</td></tr>");
                    }
                }

                // Consultar Piscina 28
                piscina28Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                Integer p28t1 = snapshot.child("t1").getValue(Integer.class);
                                Integer p28t2 = snapshot.child("t2").getValue(Integer.class);
                                Integer p28t3 = snapshot.child("t3").getValue(Integer.class);
                                Integer p28t4 = snapshot.child("t4").getValue(Integer.class);
                                Integer p28t5 = snapshot.child("t5").getValue(Integer.class);

                                p28t1 = (p28t1 != null) ? p28t1 : 0;
                                p28t2 = (p28t2 != null) ? p28t2 : 0;
                                p28t3 = (p28t3 != null) ? p28t3 : 0;
                                p28t4 = (p28t4 != null) ? p28t4 : 0;
                                p28t5 = (p28t5 != null) ? p28t5 : 0;

                                dieta = (dieta != null) ? dieta : 0;
                                recarga = (recarga != null) ? recarga : 0;
                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta : 0;
                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva : 0;
                                alVoleo = (alVoleo != null) ? alVoleo : 0;
                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                htmlBuilder.append("<tr><td>28</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p28t1).append("</td><td>").append(p28t2).append("</td><td>").append(p28t3).append("</td><td>").append(p28t4).append("</td><td>").append(p28t5).append("</td></tr>");
                            }
                        }

                        // Consultar Piscina 29
                        piscina29Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                        Integer p29t1 = snapshot.child("t1").getValue(Integer.class);
                                        Integer p29t2 = snapshot.child("t2").getValue(Integer.class);
                                        Integer p29t3 = snapshot.child("t3").getValue(Integer.class);
                                        Integer p29t4 = snapshot.child("t4").getValue(Integer.class);
                                        Integer p29t5 = snapshot.child("t5").getValue(Integer.class);
                                        Integer p29t6 = snapshot.child("t6").getValue(Integer.class);
                                        Integer p29t7 = snapshot.child("t7").getValue(Integer.class);
                                        Integer p29t8 = snapshot.child("t8").getValue(Integer.class);

                                        p29t1 = (p29t1 != null) ? p29t1 : 0;
                                        p29t2 = (p29t2 != null) ? p29t2 : 0;
                                        p29t3 = (p29t3 != null) ? p29t3 : 0;
                                        p29t4 = (p29t4 != null) ? p29t4 : 0;
                                        p29t5 = (p29t5 != null) ? p29t5 : 0;
                                        p29t6 = (p29t6 != null) ? p29t6 : 0;
                                        p29t7 = (p29t7 != null) ? p29t7 : 0;
                                        p29t8 = (p29t8 != null) ? p29t8 : 0;

                                        dieta = (dieta != null) ? dieta : 0;
                                        recarga = (recarga != null) ? recarga : 0;
                                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta : 0;
                                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva : 0;
                                        alVoleo = (alVoleo != null) ? alVoleo : 0;
                                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                        htmlBuilder.append("<tr><td>29</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p29t1).append("</td><td>").append(p29t2).append("</td><td>").append(p29t3).append("</td><td>").append(p29t4).append("</td><td>").append(p29t5).append("</td><td>").append(p29t6).append("</td><td>").append(p29t7).append("</td><td>").append(p29t8).append("</td></tr>");
                                    }
                                }

                                piscina30Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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

                                                Integer p30t1 = snapshot.child("t1").getValue(Integer.class);
                                                Integer p30t2 = snapshot.child("t2").getValue(Integer.class);
                                                Integer p30t3 = snapshot.child("t3").getValue(Integer.class);
                                                Integer p30t4 = snapshot.child("t4").getValue(Integer.class);
                                                Integer p30t5 = snapshot.child("t5").getValue(Integer.class);
                                                Integer p30t6 = snapshot.child("t6").getValue(Integer.class);
                                                Integer p30t7 = snapshot.child("t7").getValue(Integer.class);

                                                p30t1 = (p30t1 != null) ? p30t1 : 0;
                                                p30t2 = (p30t2 != null) ? p30t2 : 0;
                                                p30t3 = (p30t3 != null) ? p30t3 : 0;
                                                p30t4 = (p30t4 != null) ? p30t4 : 0;
                                                p30t5 = (p30t5 != null) ? p30t5 : 0;
                                                p30t6 = (p30t6 != null) ? p30t6 : 0;
                                                p30t7 = (p30t7 != null) ? p30t7 : 0;

                                                dieta = (dieta != null) ? dieta : 0;
                                                recarga = (recarga != null) ? recarga : 0;
                                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta : 0;
                                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva : 0;
                                                alVoleo = (alVoleo != null) ? alVoleo : 0;
                                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                htmlBuilder.append("<tr><td>30</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p30t1).append("</td><td>").append(p30t2).append("</td><td>").append(p30t3).append("</td><td>").append(p30t4).append("</td><td>").append(p30t5).append("</td><td>").append(p30t6).append("</td><td>").append(p30t7).append("</td></tr>");
                                            }
                                        }

                                        piscina31Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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

                                                        // Inicial1zar tolvas a 0 si los valores son nulos
                                                        Integer p31t1 = snapshot.child("t1").getValue(Integer.class);
                                                        Integer p31t2 = snapshot.child("t2").getValue(Integer.class);
                                                        Integer p31t3 = snapshot.child("t3").getValue(Integer.class);
                                                        Integer p31t4 = snapshot.child("t4").getValue(Integer.class);
                                                        Integer p31t5 = snapshot.child("t5").getValue(Integer.class);
                                                        Integer p31t6 = snapshot.child("t6").getValue(Integer.class);
                                                        Integer p31t7 = snapshot.child("t7").getValue(Integer.class);

                                                        p31t1 = (p31t1 != null) ? p31t1 : 0;
                                                        p31t2 = (p31t2 != null) ? p31t2 : 0;
                                                        p31t3 = (p31t3 != null) ? p31t3 : 0;
                                                        p31t4 = (p31t4 != null) ? p31t4 : 0;
                                                        p31t5 = (p31t5 != null) ? p31t5 : 0;
                                                        p31t6 = (p31t6 != null) ? p31t6 : 0;
                                                        p31t7 = (p31t7 != null) ? p31t7 : 0;

                                                        dieta = (dieta != null) ? dieta : 0;
                                                        recarga = (recarga != null) ? recarga : 0;
                                                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta : 0;
                                                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva : 0;
                                                        alVoleo = (alVoleo != null) ? alVoleo : 0;
                                                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                        htmlBuilder.append("<tr><td>31</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p31t1).append("</td><td>").append(p31t2).append("</td><td>").append(p31t3).append("</td><td>").append(p31t4).append("</td><td>").append(p31t5).append("</td><td>").append(p31t6).append("</td><td>").append(p31t7).append("</td></tr>");
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