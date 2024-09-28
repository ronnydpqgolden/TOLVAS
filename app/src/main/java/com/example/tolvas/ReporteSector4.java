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

public class ReporteSector4 extends AppCompatActivity {
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
        DatabaseReference piscina22Ref = databaseReference.child("Piscina 22");
        DatabaseReference piscina23Ref = databaseReference.child("Piscina 23");
        DatabaseReference piscina24Ref = databaseReference.child("Piscina 24");
        DatabaseReference piscina25Ref = databaseReference.child("Piscina 25");
        DatabaseReference piscina26Ref = databaseReference.child("Piscina 26");

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html><body>");
        htmlBuilder.append("<h1>DATOS PISCINAS SECTOR 4</h1>");
        htmlBuilder.append("<table border='1'>");
        htmlBuilder.append("<tr><th>PISCINA</th><th>DIETA (KG)</th><th>SOBRANTE TOLVA (KG)</th><th>RECARGA (KG)</th><th>AL VOLEO (KG)</th><th>SOBRANTE CASETA (KG)</th><th>TIPO BALANCEADO</th><th>TOLVA 1</th><th>TOLVA 2</th><th>TOLVA 3</th><th>TOLVA 4</th><th>TOLVA 5</th><th>TOLVA 6</th><th>TOLVA 7</th><th>TOLVA 8</th><th>TOLVA 9</th><th>TOLVA 10</th></tr>");

        // Consultar Piscina 22
        piscina22Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                        Integer p22t1 = snapshot.child("t1").getValue(Integer.class);
                        Integer p22t2 = snapshot.child("t2").getValue(Integer.class);
                        Integer p22t3 = snapshot.child("t3").getValue(Integer.class);
                        Integer p22t4 = snapshot.child("t4").getValue(Integer.class);
                        Integer p22t5 = snapshot.child("t5").getValue(Integer.class);
                        Integer p22t6 = snapshot.child("t6").getValue(Integer.class);
                        Integer p22t7 = snapshot.child("t7").getValue(Integer.class);

                        p22t1 = (p22t1 != null) ? p22t1 : 0;
                        p22t2 = (p22t2 != null) ? p22t2 : 0;
                        p22t3 = (p22t3 != null) ? p22t3 : 0;
                        p22t4 = (p22t4 != null) ? p22t4 : 0;
                        p22t5 = (p22t5 != null) ? p22t5 : 0;
                        p22t6 = (p22t6 != null) ? p22t6 : 0;
                        p22t7 = (p22t7 != null) ? p22t7 : 0;

                        dieta = (dieta != null) ? dieta * 25 : 0;
                        recarga = (recarga != null) ? recarga * 25 : 0;
                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                        alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                        htmlBuilder.append("<tr><td>22</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p22t1).append("</td><td>").append(p22t2).append("</td><td>").append(p22t3).append("</td><td>").append(p22t4).append("</td><td>").append(p22t5).append("</td><td>").append(p22t6).append("</td><td>").append(p22t7).append("</td></tr>");
                    }
                }

                // Consultar Piscina 23
                piscina23Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                Integer p23t1 = snapshot.child("t1").getValue(Integer.class);
                                Integer p23t2 = snapshot.child("t2").getValue(Integer.class);
                                Integer p23t3 = snapshot.child("t3").getValue(Integer.class);
                                Integer p23t4 = snapshot.child("t4").getValue(Integer.class);
                                Integer p23t5 = snapshot.child("t5").getValue(Integer.class);
                                Integer p23t6 = snapshot.child("t6").getValue(Integer.class);
                                Integer p23t7 = snapshot.child("t7").getValue(Integer.class);

                                p23t1 = (p23t1 != null) ? p23t1 : 0;
                                p23t2 = (p23t2 != null) ? p23t2 : 0;
                                p23t3 = (p23t3 != null) ? p23t3 : 0;
                                p23t4 = (p23t4 != null) ? p23t4 : 0;
                                p23t5 = (p23t5 != null) ? p23t5 : 0;
                                p23t6 = (p23t6 != null) ? p23t6 : 0;
                                p23t7 = (p23t7 != null) ? p23t7 : 0;

                                dieta = (dieta != null) ? dieta * 25 : 0;
                                recarga = (recarga != null) ? recarga * 25 : 0;
                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                htmlBuilder.append("<tr><td>23</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p23t1).append("</td><td>").append(p23t2).append("</td><td>").append(p23t3).append("</td><td>").append(p23t4).append("</td><td>").append(p23t5).append("</td><td>").append(p23t6).append("</td><td>").append(p23t7).append("</td></tr>");
                            }
                        }

                        // Consultar Piscina 24
                        piscina24Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                        Integer p24t1 = snapshot.child("t1").getValue(Integer.class);
                                        Integer p24t2 = snapshot.child("t2").getValue(Integer.class);
                                        Integer p24t3 = snapshot.child("t3").getValue(Integer.class);
                                        Integer p24t4 = snapshot.child("t4").getValue(Integer.class);
                                        Integer p24t5 = snapshot.child("t5").getValue(Integer.class);

                                        p24t1 = (p24t1 != null) ? p24t1 : 0;
                                        p24t2 = (p24t2 != null) ? p24t2 : 0;
                                        p24t3 = (p24t3 != null) ? p24t3 : 0;
                                        p24t4 = (p24t4 != null) ? p24t4 : 0;
                                        p24t5 = (p24t5 != null) ? p24t5 : 0;

                                        dieta = (dieta != null) ? dieta * 25 : 0;
                                        recarga = (recarga != null) ? recarga * 25 : 0;
                                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                        alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                        htmlBuilder.append("<tr><td>24</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p24t1).append("</td><td>").append(p24t2).append("</td><td>").append(p24t3).append("</td><td>").append(p24t4).append("</td><td>").append(p24t5).append("</td></tr>");
                                    }
                                }

                                piscina25Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                Integer p25t1 = snapshot.child("t1").getValue(Integer.class);
                                                Integer p25t2 = snapshot.child("t2").getValue(Integer.class);
                                                Integer p25t3 = snapshot.child("t3").getValue(Integer.class);
                                                Integer p25t4 = snapshot.child("t4").getValue(Integer.class);
                                                Integer p25t5 = snapshot.child("t5").getValue(Integer.class);
                                                Integer p25t6 = snapshot.child("t6").getValue(Integer.class);

                                                p25t1 = (p25t1 != null) ? p25t1 : 0;
                                                p25t2 = (p25t2 != null) ? p25t2 : 0;
                                                p25t3 = (p25t3 != null) ? p25t3 : 0;
                                                p25t4 = (p25t4 != null) ? p25t4 : 0;
                                                p25t5 = (p25t5 != null) ? p25t5 : 0;
                                                p25t6 = (p25t6 != null) ? p25t6 : 0;

                                                dieta = (dieta != null) ? dieta * 25 : 0;
                                                recarga = (recarga != null) ? recarga * 25 : 0;
                                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                                alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                htmlBuilder.append("<tr><td>25</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p25t1).append("</td><td>").append(p25t2).append("</td><td>").append(p25t3).append("</td><td>").append(p25t4).append("</td><td>").append(p25t5).append("</td><td>").append(p25t6).append("</td></tr>");
                                            }
                                        }

                                        piscina26Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                        Integer p26t1 = snapshot.child("t1").getValue(Integer.class);
                                                        Integer p26t2 = snapshot.child("t2").getValue(Integer.class);
                                                        Integer p26t3 = snapshot.child("t3").getValue(Integer.class);
                                                        Integer p26t4 = snapshot.child("t4").getValue(Integer.class);
                                                        Integer p26t5 = snapshot.child("t5").getValue(Integer.class);
                                                        Integer p26t6 = snapshot.child("t6").getValue(Integer.class);
                                                        Integer p26t7 = snapshot.child("t7").getValue(Integer.class);
                                                        Integer p26t8 = snapshot.child("t8").getValue(Integer.class);

                                                        p26t1 = (p26t1 != null) ? p26t1 : 0;
                                                        p26t2 = (p26t2 != null) ? p26t2 : 0;
                                                        p26t3 = (p26t3 != null) ? p26t3 : 0;
                                                        p26t4 = (p26t4 != null) ? p26t4 : 0;
                                                        p26t5 = (p26t5 != null) ? p26t5 : 0;
                                                        p26t6 = (p26t6 != null) ? p26t6 : 0;
                                                        p26t7 = (p26t7 != null) ? p26t7 : 0;
                                                        p26t8 = (p26t8 != null) ? p26t8 : 0;

                                                        dieta = (dieta != null) ? dieta * 25 : 0;
                                                        recarga = (recarga != null) ? recarga * 25 : 0;
                                                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                                        alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                        htmlBuilder.append("<tr><td>26</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p26t1).append("</td><td>").append(p26t2).append("</td><td>").append(p26t3).append("</td><td>").append(p26t4).append("</td><td>").append(p26t5).append("</td><td>").append(p26t6).append("</td><td>").append(p26t7).append("</td><td>").append(p26t8).append("</td></tr>");
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