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

public class ReporteSector3 extends AppCompatActivity {
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
        DatabaseReference piscina16Ref = databaseReference.child("Piscina 16");
        DatabaseReference piscina17Ref = databaseReference.child("Piscina 17");
        DatabaseReference piscina18Ref = databaseReference.child("Piscina 18");
        DatabaseReference piscina19Ref = databaseReference.child("Piscina 19");
        DatabaseReference piscina20Ref = databaseReference.child("Piscina 20");
        DatabaseReference piscina21Ref = databaseReference.child("Piscina 21");

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html><body>");
        htmlBuilder.append("<h1>DATOS PISCINAS SECTOR 3</h1>");
        htmlBuilder.append("<table border='1'>");
        htmlBuilder.append("<tr><th>PISCINA</th><th>DIETA (KG)</th><th>SOBRANTE TOLVA (KG)</th><th>RECARGA (KG)</th><th>AL VOLEO (KG)</th><th>SOBRANTE CASETA (KG)</th><th>TIPO BALANCEADO</th><th>TOLVA 1</th><th>TOLVA 2</th><th>TOLVA 3</th><th>TOLVA 4</th><th>TOLVA 5</th><th>TOLVA 6</th><th>TOLVA 7</th><th>TOLVA 8</th><th>TOLVA 9</th><th>TOLVA 10</th></tr>");

        // Consultar Piscina 16
        piscina16Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                        Integer p16t1 = snapshot.child("t1").getValue(Integer.class);
                        Integer p16t2 = snapshot.child("t2").getValue(Integer.class);
                        Integer p16t3 = snapshot.child("t3").getValue(Integer.class);
                        Integer p16t4 = snapshot.child("t4").getValue(Integer.class);
                        Integer p16t5 = snapshot.child("t5").getValue(Integer.class);
                        Integer p16t6 = snapshot.child("t6").getValue(Integer.class);
                        Integer p16t7 = snapshot.child("t7").getValue(Integer.class);

                        p16t1 = (p16t1 != null) ? p16t1 : 0;
                        p16t2 = (p16t2 != null) ? p16t2 : 0;
                        p16t3 = (p16t3 != null) ? p16t3 : 0;
                        p16t4 = (p16t4 != null) ? p16t4 : 0;
                        p16t5 = (p16t5 != null) ? p16t5 : 0;
                        p16t6 = (p16t6 != null) ? p16t6 : 0;
                        p16t7 = (p16t7 != null) ? p16t7 : 0;

                        dieta = (dieta != null) ? dieta * 25 : 0;
                        recarga = (recarga != null) ? recarga * 25 : 0;
                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                        alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                        htmlBuilder.append("<tr><td>16</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p16t1).append("</td><td>").append(p16t2).append("</td><td>").append(p16t3).append("</td><td>").append(p16t4).append("</td><td>").append(p16t5).append("</td><td>").append(p16t6).append("</td><td>").append(p16t7).append("</td></tr>");
                    }
                }

                // Consultar Piscina 17
                piscina17Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                Integer p17t1 = snapshot.child("t1").getValue(Integer.class);
                                Integer p17t2 = snapshot.child("t2").getValue(Integer.class);
                                Integer p17t3 = snapshot.child("t3").getValue(Integer.class);
                                Integer p17t4 = snapshot.child("t4").getValue(Integer.class);
                                Integer p17t5 = snapshot.child("t5").getValue(Integer.class);
                                Integer p17t6 = snapshot.child("t6").getValue(Integer.class);

                                p17t1 = (p17t1 != null) ? p17t1 : 0;
                                p17t2 = (p17t2 != null) ? p17t2 : 0;
                                p17t3 = (p17t3 != null) ? p17t3 : 0;
                                p17t4 = (p17t4 != null) ? p17t4 : 0;
                                p17t5 = (p17t5 != null) ? p17t5 : 0;
                                p17t6 = (p17t6 != null) ? p17t6 : 0;

                                dieta = (dieta != null) ? dieta * 25 : 0;
                                recarga = (recarga != null) ? recarga * 25 : 0;
                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                htmlBuilder.append("<tr><td>17</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p17t1).append("</td><td>").append(p17t2).append("</td><td>").append(p17t3).append("</td><td>").append(p17t4).append("</td><td>").append(p17t5).append("</td><td>").append(p17t6).append("</td></tr>");
                            }
                        }

                        // Consultar Piscina 18
                        piscina18Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                        Integer p18t1 = snapshot.child("t1").getValue(Integer.class);
                                        Integer p18t2 = snapshot.child("t2").getValue(Integer.class);
                                        Integer p18t3 = snapshot.child("t3").getValue(Integer.class);
                                        Integer p18t4 = snapshot.child("t4").getValue(Integer.class);
                                        Integer p18t5 = snapshot.child("t5").getValue(Integer.class);
                                        Integer p18t6 = snapshot.child("t6").getValue(Integer.class);
                                        Integer p18t7 = snapshot.child("t7").getValue(Integer.class);
                                        Integer p18t8 = snapshot.child("t8").getValue(Integer.class);
                                        Integer p18t9 = snapshot.child("t9").getValue(Integer.class);
                                        Integer p18t10 = snapshot.child("t10").getValue(Integer.class);

                                        p18t1 = (p18t1 != null) ? p18t1 : 0;
                                        p18t2 = (p18t2 != null) ? p18t2 : 0;
                                        p18t3 = (p18t3 != null) ? p18t3 : 0;
                                        p18t4 = (p18t4 != null) ? p18t4 : 0;
                                        p18t5 = (p18t5 != null) ? p18t5 : 0;
                                        p18t6 = (p18t6 != null) ? p18t6 : 0;
                                        p18t7 = (p18t7 != null) ? p18t7 : 0;
                                        p18t8 = (p18t8 != null) ? p18t8 : 0;
                                        p18t9 = (p18t9 != null) ? p18t9 : 0;
                                        p18t10 = (p18t10 != null) ? p18t10 : 0;

                                        dieta = (dieta != null) ? dieta * 25 : 0;
                                        recarga = (recarga != null) ? recarga * 25 : 0;
                                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                        alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                        htmlBuilder.append("<tr><td>18</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p18t1).append("</td><td>").append(p18t2).append("</td><td>").append(p18t3).append("</td><td>").append(p18t4).append("</td><td>").append(p18t5).append("</td><td>").append(p18t6).append("</td><td>").append(p18t7).append("</td><td>").append(p18t8).append("</td><td>").append(p18t9).append("</td><td>").append(p18t10).append("</td></tr>");
                                    }
                                }

                                piscina19Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                Integer p19t1 = snapshot.child("t1").getValue(Integer.class);
                                                Integer p19t2 = snapshot.child("t2").getValue(Integer.class);
                                                Integer p19t3 = snapshot.child("t3").getValue(Integer.class);
                                                Integer p19t4 = snapshot.child("t4").getValue(Integer.class);
                                                Integer p19t5 = snapshot.child("t5").getValue(Integer.class);
                                                Integer p19t6 = snapshot.child("t6").getValue(Integer.class);
                                                Integer p19t7 = snapshot.child("t7").getValue(Integer.class);
                                                Integer p19t8 = snapshot.child("t8").getValue(Integer.class);

                                                p19t1 = (p19t1 != null) ? p19t1 : 0;
                                                p19t2 = (p19t2 != null) ? p19t2 : 0;
                                                p19t3 = (p19t3 != null) ? p19t3 : 0;
                                                p19t4 = (p19t4 != null) ? p19t4 : 0;
                                                p19t5 = (p19t5 != null) ? p19t5 : 0;
                                                p19t6 = (p19t6 != null) ? p19t6 : 0;
                                                p19t7 = (p19t7 != null) ? p19t7 : 0;
                                                p19t8 = (p19t8 != null) ? p19t8 : 0;

                                                dieta = (dieta != null) ? dieta * 25 : 0;
                                                recarga = (recarga != null) ? recarga * 25 : 0;
                                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                                alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                htmlBuilder.append("<tr><td>19</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p19t1).append("</td><td>").append(p19t2).append("</td><td>").append(p19t3).append("</td><td>").append(p19t4).append("</td><td>").append(p19t5).append("</td><td>").append(p19t6).append("</td><td>").append(p19t7).append("</td><td>").append(p19t8).append("</td></tr>");
                                            }
                                        }

                                        piscina20Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                        Integer p20t1 = snapshot.child("t1").getValue(Integer.class);
                                                        Integer p20t2 = snapshot.child("t2").getValue(Integer.class);
                                                        Integer p20t3 = snapshot.child("t3").getValue(Integer.class);
                                                        Integer p20t4 = snapshot.child("t4").getValue(Integer.class);
                                                        Integer p20t5 = snapshot.child("t5").getValue(Integer.class);
                                                        Integer p20t6 = snapshot.child("t6").getValue(Integer.class);
                                                        Integer p20t7 = snapshot.child("t7").getValue(Integer.class);
                                                        Integer p20t8 = snapshot.child("t8").getValue(Integer.class);
                                                        Integer p20t9 = snapshot.child("t9").getValue(Integer.class);
                                                        Integer p20t10 = snapshot.child("t10").getValue(Integer.class);

                                                        p20t1 = (p20t1 != null) ? p20t1 : 0;
                                                        p20t2 = (p20t2 != null) ? p20t2 : 0;
                                                        p20t3 = (p20t3 != null) ? p20t3 : 0;
                                                        p20t4 = (p20t4 != null) ? p20t4 : 0;
                                                        p20t5 = (p20t5 != null) ? p20t5 : 0;
                                                        p20t6 = (p20t6 != null) ? p20t6 : 0;
                                                        p20t7 = (p20t7 != null) ? p20t7 : 0;
                                                        p20t8 = (p20t8 != null) ? p20t8 : 0;
                                                        p20t9 = (p20t9 != null) ? p20t9 : 0;
                                                        p20t10 = (p20t10 != null) ? p20t10 : 0;

                                                        dieta = (dieta != null) ? dieta * 25 : 0;
                                                        recarga = (recarga != null) ? recarga * 25 : 0;
                                                        sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                                        sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                                        alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                                        tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                        htmlBuilder.append("<tr><td>20</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p20t1).append("</td><td>").append(p20t2).append("</td><td>").append(p20t3).append("</td><td>").append(p20t4).append("</td><td>").append(p20t5).append("</td><td>").append(p20t6).append("</td><td>").append(p20t7).append("</td><td>").append(p20t8).append("</td><td>").append(p20t9).append("</td><td>").append(p20t10).append("</td></tr>");
                                                    }
                                                }

                                                piscina21Ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                                Integer p21t1 = snapshot.child("t1").getValue(Integer.class);
                                                                Integer p21t2 = snapshot.child("t2").getValue(Integer.class);
                                                                Integer p21t3 = snapshot.child("t3").getValue(Integer.class);
                                                                Integer p21t4 = snapshot.child("t4").getValue(Integer.class);
                                                                Integer p21t5 = snapshot.child("t5").getValue(Integer.class);
                                                                Integer p21t6 = snapshot.child("t6").getValue(Integer.class);
                                                                Integer p21t7 = snapshot.child("t7").getValue(Integer.class);
                                                                Integer p21t8 = snapshot.child("t8").getValue(Integer.class);
                                                                Integer p21t9 = snapshot.child("t9").getValue(Integer.class);
                                                                Integer p21t10 = snapshot.child("t10").getValue(Integer.class);

                                                                p21t1 = (p21t1 != null) ? p21t1 : 0;
                                                                p21t2 = (p21t2 != null) ? p21t2 : 0;
                                                                p21t3 = (p21t3 != null) ? p21t3 : 0;
                                                                p21t4 = (p21t4 != null) ? p21t4 : 0;
                                                                p21t5 = (p21t5 != null) ? p21t5 : 0;
                                                                p21t6 = (p21t6 != null) ? p21t6 : 0;
                                                                p21t7 = (p21t7 != null) ? p21t7 : 0;
                                                                p21t8 = (p21t8 != null) ? p21t8 : 0;
                                                                p21t9 = (p21t9 != null) ? p21t9 : 0;
                                                                p21t10 = (p21t10 != null) ? p21t10 : 0;

                                                                dieta = (dieta != null) ? dieta * 25 : 0;
                                                                recarga = (recarga != null) ? recarga * 25 : 0;
                                                                sobranteCaseta = (sobranteCaseta != null) ? sobranteCaseta * 25 : 0;
                                                                sobranteTolva = (sobranteTolva != null) ? sobranteTolva * 25 : 0;
                                                                alVoleo = (alVoleo != null) ? alVoleo * 25 : 0;
                                                                tipoBalanceado = (tipoBalanceado != null) ? tipoBalanceado : "";

                                                                htmlBuilder.append("<tr><td>21</td><td>").append(dieta).append("</td><td>").append(sobranteTolva).append("</td><td>").append(recarga).append("</td><td>").append(alVoleo).append("</td><td>").append(sobranteCaseta).append("</td><td>").append(tipoBalanceado).append("</td><td>").append(p21t1).append("</td><td>").append(p21t2).append("</td><td>").append(p21t3).append("</td><td>").append(p21t4).append("</td><td>").append(p21t5).append("</td><td>").append(p21t6).append("</td><td>").append(p21t7).append("</td><td>").append(p21t8).append("</td><td>").append(p21t9).append("</td><td>").append(p21t10).append("</td></tr>");
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