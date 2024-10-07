package com.example.tolvas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tolvas.model.Piscinas;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class PiscinasSector8 extends AppCompatActivity {

    private TextInputLayout dietaTV,sobranteTolvaTV,recargaTV,alVoleoTV,sobranteCasetaTV,piscinaTV,tiposBalanceadoTV;
    private TextInputEditText dietaET,sobranteTolvaET,sobranteCasetaET,recargaET,alVoleoET;
    private TextInputLayout TVt1,TVt2,TVt3,TVt4,TVt5,TVt6,TVt7,TVt8,TVt9,TVt10;
    private TextInputEditText ETt1,ETt2,ETt3,ETt4,ETt5,ETt6,ETt7,ETt8,ETt9,ETt10,ETRojo,ETFresco, ETReportado, ETObservaciones;
    private String piscinaSeleccionada;
    private TextView dateTextView,TVz1,TVz2;
    MaterialButton botonGuardar,botonInicio,botonActualizar;
    MaterialAutoCompleteTextView autoCompleteTextViewTipos, autoCompleteTextViewPiscinas;
    String currentDate;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piscinas_sector);

        inicializarFirebase();
        inicializarCamposVariables();
        inicializarListas();
        actualizarDatosDesdeFirebase();


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        //currentDate = "30/09/2024";
        //dateTextView.setText("30/09/2024");
        currentDate = sdf.format(new Date());
        SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE", new Locale("es", "ES"));
        String currentDay = sdf2.format(new Date());

        dateTextView.setText(currentDay+", "+currentDate);


        insertarDatosPiscinasZona1();
        volverInicio();


    }
    private void actualizarDatosDesdeFirebase() {
        // Lista de referencias a las piscinas
        List<String> piscinas = Arrays.asList("Piscina 47", "Piscina 48", "Piscina 49", "Piscina 50", "Piscina 51", "Piscina 52", "Piscina 53", "Piscina 54");

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


    private Long parseLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }


    private void volverInicio() {
        botonInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PiscinasSector8.this, INICIO.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void insertarDatosPiscinasZona1() {
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(autoCompleteTextViewPiscinas.getText().toString().isEmpty()){
                    piscinaTV.setError("Por favor seleccione una piscina");
                }else{
                    piscinaTV.setError(null);
                    String piscinaSeleccionada = autoCompleteTextViewPiscinas.getText().toString();
                    if(piscinaSeleccionada.equals("Piscina 47")){
                        String tipoBalanceadoSeleccionado=autoCompleteTextViewTipos.getText().toString();
                        if (isEditTextEmpty(dietaET)||isEditTextEmpty(sobranteTolvaET)||isEditTextEmpty(alVoleoET)||isEditTextEmpty(sobranteCasetaET)
                                ||isEditTextEmpty(recargaET)||tipoBalanceadoSeleccionado.equals("")||isEditTextEmpty(ETt1)||isEditTextEmpty(ETt2)||isEditTextEmpty(ETt3)
                                ||isEditTextEmpty(ETt4)||isEditTextEmpty(ETt6)||isEditTextEmpty(ETt7)||isEditTextEmpty(ETt8)||isEditTextEmpty(ETt9)){
                            Toast.makeText(PiscinasSector8.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Piscinas p47= new Piscinas();
                        p47.setUid(UUID.randomUUID().toString());
                        p47.setFecha(currentDate);
                        p47.setDieta(Integer.parseInt(Objects.requireNonNull(dietaET.getText()).toString()));
                        p47.setSobrantetolva(Integer.parseInt(Objects.requireNonNull(sobranteTolvaET.getText()).toString()));
                        p47.setRecarga(Integer.parseInt(Objects.requireNonNull(recargaET.getText()).toString()));
                        p47.setAlvoleo(Integer.parseInt(Objects.requireNonNull(alVoleoET.getText()).toString()));
                        p47.setSobrantecaseta(Integer.parseInt(Objects.requireNonNull(sobranteCasetaET.getText()).toString()));
                        p47.setTipobalanceado(tipoBalanceadoSeleccionado);
                        p47.setT1(Integer.parseInt(Objects.requireNonNull(ETt1.getText()).toString()));
                        p47.setT2(Integer.parseInt(Objects.requireNonNull(ETt2.getText()).toString()));
                        p47.setT3(Integer.parseInt(Objects.requireNonNull(ETt3.getText()).toString()));
                        p47.setT4(Integer.parseInt(Objects.requireNonNull(ETt4.getText()).toString()));
                        p47.setT5(Integer.parseInt(Objects.requireNonNull(ETt6.getText()).toString()));
                        p47.setT6(Integer.parseInt(Objects.requireNonNull(ETt7.getText()).toString()));
                        p47.setT7(Integer.parseInt(Objects.requireNonNull(ETt8.getText()).toString()));
                        p47.setT8(Integer.parseInt(Objects.requireNonNull(ETt9.getText()).toString()));
                        p47.setRojo(getIntValue(ETRojo));
                        p47.setFresco(getIntValue(ETFresco));
                        p47.setReportado(getIntValue(ETReportado));
                        p47.setObservaciones(getStringValue(ETObservaciones));

                        databaseReference.child("Piscina 47").child(p47.getUid()).setValue(p47);
                        //limparcampospiscina1();
                        verificarDatosEnFirebase8T("Piscina 47",currentDate);
                        Toast.makeText(PiscinasSector8.this, "DATOS INGRESADOS", Toast.LENGTH_SHORT).show();

                    } else if (piscinaSeleccionada.equals("Piscina 48")) {
                        String tipoBalanceadoSeleccionado=autoCompleteTextViewTipos.getText().toString();
                        if (isEditTextEmpty(dietaET)||isEditTextEmpty(sobranteTolvaET)||isEditTextEmpty(alVoleoET)||isEditTextEmpty(sobranteCasetaET)
                                ||isEditTextEmpty(recargaET)||tipoBalanceadoSeleccionado.equals("")||isEditTextEmpty(ETt1)||isEditTextEmpty(ETt2)||isEditTextEmpty(ETt3)
                                ||isEditTextEmpty(ETt6)||isEditTextEmpty(ETt7)||isEditTextEmpty(ETt8)){
                            Toast.makeText(PiscinasSector8.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Piscinas p48= new Piscinas();
                        p48.setUid(UUID.randomUUID().toString());
                        p48.setFecha(currentDate);
                        p48.setDieta(Integer.parseInt(Objects.requireNonNull(dietaET.getText()).toString()));
                        p48.setSobrantetolva(Integer.parseInt(Objects.requireNonNull(sobranteTolvaET.getText()).toString()));
                        p48.setRecarga(Integer.parseInt(Objects.requireNonNull(recargaET.getText()).toString()));
                        p48.setAlvoleo(Integer.parseInt(Objects.requireNonNull(alVoleoET.getText()).toString()));
                        p48.setSobrantecaseta(Integer.parseInt(Objects.requireNonNull(sobranteCasetaET.getText()).toString()));
                        p48.setTipobalanceado(tipoBalanceadoSeleccionado);
                        p48.setT1(Integer.parseInt(Objects.requireNonNull(ETt1.getText()).toString()));
                        p48.setT2(Integer.parseInt(Objects.requireNonNull(ETt2.getText()).toString()));
                        p48.setT3(Integer.parseInt(Objects.requireNonNull(ETt3.getText()).toString()));
                        p48.setT4(Integer.parseInt(Objects.requireNonNull(ETt6.getText()).toString()));
                        p48.setT5(Integer.parseInt(Objects.requireNonNull(ETt7.getText()).toString()));
                        p48.setT6(Integer.parseInt(Objects.requireNonNull(ETt8.getText()).toString()));
                        p48.setRojo(getIntValue(ETRojo));
                        p48.setFresco(getIntValue(ETFresco));
                        p48.setReportado(getIntValue(ETReportado));
                        p48.setObservaciones(getStringValue(ETObservaciones));
                        databaseReference.child("Piscina 48").child(p48.getUid()).setValue(p48);
                        //limparcampospiscina1();
                        verificarDatosEnFirebase6T("Piscina 48",currentDate);
                        Toast.makeText(PiscinasSector8.this, "DATOS INGRESADOS", Toast.LENGTH_SHORT).show();

                    }else if (piscinaSeleccionada.equals("Piscina 49")) {
                        String tipoBalanceadoSeleccionado=autoCompleteTextViewTipos.getText().toString();
                        if (isEditTextEmpty(dietaET)||isEditTextEmpty(sobranteTolvaET)||isEditTextEmpty(alVoleoET)||isEditTextEmpty(sobranteCasetaET)
                                ||isEditTextEmpty(recargaET)||tipoBalanceadoSeleccionado.equals("")||isEditTextEmpty(ETt1)||isEditTextEmpty(ETt2)||isEditTextEmpty(ETt3)
                                ||isEditTextEmpty(ETt6)||isEditTextEmpty(ETt7)||isEditTextEmpty(ETt8)){
                            Toast.makeText(PiscinasSector8.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Piscinas p49= new Piscinas();
                        p49.setUid(UUID.randomUUID().toString());
                        p49.setFecha(currentDate);
                        p49.setDieta(Integer.parseInt(Objects.requireNonNull(dietaET.getText()).toString()));
                        p49.setSobrantetolva(Integer.parseInt(Objects.requireNonNull(sobranteTolvaET.getText()).toString()));
                        p49.setRecarga(Integer.parseInt(Objects.requireNonNull(recargaET.getText()).toString()));
                        p49.setAlvoleo(Integer.parseInt(Objects.requireNonNull(alVoleoET.getText()).toString()));
                        p49.setSobrantecaseta(Integer.parseInt(Objects.requireNonNull(sobranteCasetaET.getText()).toString()));
                        p49.setTipobalanceado(tipoBalanceadoSeleccionado);
                        p49.setT1(Integer.parseInt(Objects.requireNonNull(ETt1.getText()).toString()));
                        p49.setT2(Integer.parseInt(Objects.requireNonNull(ETt2.getText()).toString()));
                        p49.setT3(Integer.parseInt(Objects.requireNonNull(ETt3.getText()).toString()));
                        p49.setT4(Integer.parseInt(Objects.requireNonNull(ETt6.getText()).toString()));
                        p49.setT5(Integer.parseInt(Objects.requireNonNull(ETt7.getText()).toString()));
                        p49.setT6(Integer.parseInt(Objects.requireNonNull(ETt8.getText()).toString()));
                        p49.setRojo(getIntValue(ETRojo));
                        p49.setFresco(getIntValue(ETFresco));
                        p49.setReportado(getIntValue(ETReportado));
                        p49.setObservaciones(getStringValue(ETObservaciones));
                        databaseReference.child("Piscina 49").child(p49.getUid()).setValue(p49);
                        //limparcampospiscina1();
                        verificarDatosEnFirebase6T("Piscina 49",currentDate);
                        Toast.makeText(PiscinasSector8.this, "DATOS INGRESADOS", Toast.LENGTH_SHORT).show();
                    } else if (piscinaSeleccionada.equals("Piscina 50")) {
                        String tipoBalanceadoSeleccionado=autoCompleteTextViewTipos.getText().toString();
                        if (isEditTextEmpty(dietaET)||isEditTextEmpty(sobranteTolvaET)||isEditTextEmpty(alVoleoET)||isEditTextEmpty(sobranteCasetaET)
                                ||isEditTextEmpty(recargaET)||tipoBalanceadoSeleccionado.equals("")||isEditTextEmpty(ETt1)||isEditTextEmpty(ETt2)||isEditTextEmpty(ETt3)
                                ||isEditTextEmpty(ETt4)||isEditTextEmpty(ETt6)||isEditTextEmpty(ETt7)||isEditTextEmpty(ETt8)||isEditTextEmpty(ETt9)){
                            Toast.makeText(PiscinasSector8.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Piscinas p50= new Piscinas();
                        p50.setUid(UUID.randomUUID().toString());
                        p50.setFecha(currentDate);
                        p50.setDieta(Integer.parseInt(Objects.requireNonNull(dietaET.getText()).toString()));
                        p50.setSobrantetolva(Integer.parseInt(Objects.requireNonNull(sobranteTolvaET.getText()).toString()));
                        p50.setRecarga(Integer.parseInt(Objects.requireNonNull(recargaET.getText()).toString()));
                        p50.setAlvoleo(Integer.parseInt(Objects.requireNonNull(alVoleoET.getText()).toString()));
                        p50.setSobrantecaseta(Integer.parseInt(Objects.requireNonNull(sobranteCasetaET.getText()).toString()));
                        p50.setTipobalanceado(tipoBalanceadoSeleccionado);
                        p50.setT1(Integer.parseInt(Objects.requireNonNull(ETt1.getText()).toString()));
                        p50.setT2(Integer.parseInt(Objects.requireNonNull(ETt2.getText()).toString()));
                        p50.setT3(Integer.parseInt(Objects.requireNonNull(ETt3.getText()).toString()));
                        p50.setT4(Integer.parseInt(Objects.requireNonNull(ETt4.getText()).toString()));
                        p50.setT5(Integer.parseInt(Objects.requireNonNull(ETt6.getText()).toString()));
                        p50.setT6(Integer.parseInt(Objects.requireNonNull(ETt7.getText()).toString()));
                        p50.setT7(Integer.parseInt(Objects.requireNonNull(ETt8.getText()).toString()));
                        p50.setT8(Integer.parseInt(Objects.requireNonNull(ETt9.getText()).toString()));
                        p50.setRojo(getIntValue(ETRojo));
                        p50.setFresco(getIntValue(ETFresco));
                        p50.setReportado(getIntValue(ETReportado));
                        p50.setObservaciones(getStringValue(ETObservaciones));
                        databaseReference.child("Piscina 50").child(p50.getUid()).setValue(p50);
                        //limparcampospiscina1();
                        verificarDatosEnFirebase8T("Piscina 50",currentDate);
                        Toast.makeText(PiscinasSector8.this, "DATOS INGRESADOS", Toast.LENGTH_SHORT).show();
                    } else if (piscinaSeleccionada.equals("Piscina 51")) {
                        String tipoBalanceadoSeleccionado=autoCompleteTextViewTipos.getText().toString();
                        if (isEditTextEmpty(dietaET)||isEditTextEmpty(sobranteTolvaET)||isEditTextEmpty(alVoleoET)||isEditTextEmpty(sobranteCasetaET)
                                ||isEditTextEmpty(recargaET)||tipoBalanceadoSeleccionado.equals("")||isEditTextEmpty(ETt1)||isEditTextEmpty(ETt2)||isEditTextEmpty(ETt3)
                                ||isEditTextEmpty(ETt4)||isEditTextEmpty(ETt6)||isEditTextEmpty(ETt7)||isEditTextEmpty(ETt8)||isEditTextEmpty(ETt9)){
                            Toast.makeText(PiscinasSector8.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Piscinas p51= new Piscinas();
                        p51.setUid(UUID.randomUUID().toString());
                        p51.setFecha(currentDate);
                        p51.setDieta(Integer.parseInt(Objects.requireNonNull(dietaET.getText()).toString()));
                        p51.setSobrantetolva(Integer.parseInt(Objects.requireNonNull(sobranteTolvaET.getText()).toString()));
                        p51.setRecarga(Integer.parseInt(Objects.requireNonNull(recargaET.getText()).toString()));
                        p51.setAlvoleo(Integer.parseInt(Objects.requireNonNull(alVoleoET.getText()).toString()));
                        p51.setSobrantecaseta(Integer.parseInt(Objects.requireNonNull(sobranteCasetaET.getText()).toString()));
                        p51.setTipobalanceado(tipoBalanceadoSeleccionado);
                        p51.setT1(Integer.parseInt(Objects.requireNonNull(ETt1.getText()).toString()));
                        p51.setT2(Integer.parseInt(Objects.requireNonNull(ETt2.getText()).toString()));
                        p51.setT3(Integer.parseInt(Objects.requireNonNull(ETt3.getText()).toString()));
                        p51.setT4(Integer.parseInt(Objects.requireNonNull(ETt4.getText()).toString()));
                        p51.setT5(Integer.parseInt(Objects.requireNonNull(ETt6.getText()).toString()));
                        p51.setT6(Integer.parseInt(Objects.requireNonNull(ETt7.getText()).toString()));
                        p51.setT7(Integer.parseInt(Objects.requireNonNull(ETt8.getText()).toString()));
                        p51.setT8(Integer.parseInt(Objects.requireNonNull(ETt9.getText()).toString()));
                        p51.setRojo(getIntValue(ETRojo));
                        p51.setFresco(getIntValue(ETFresco));
                        p51.setReportado(getIntValue(ETReportado));
                        p51.setObservaciones(getStringValue(ETObservaciones));
                        databaseReference.child("Piscina 51").child(p51.getUid()).setValue(p51);
                        //limparcampospiscina1();
                        verificarDatosEnFirebase8T("Piscina 51",currentDate);
                        Toast.makeText(PiscinasSector8.this, "DATOS INGRESADOS", Toast.LENGTH_SHORT).show();
                    } else if (piscinaSeleccionada.equals("Piscina 52")) {
                        String tipoBalanceadoSeleccionado=autoCompleteTextViewTipos.getText().toString();
                        if (isEditTextEmpty(dietaET)||isEditTextEmpty(sobranteTolvaET)||isEditTextEmpty(alVoleoET)||isEditTextEmpty(sobranteCasetaET)
                                ||isEditTextEmpty(recargaET)||tipoBalanceadoSeleccionado.equals("")||isEditTextEmpty(ETt1)||isEditTextEmpty(ETt2)||isEditTextEmpty(ETt3)
                                ||isEditTextEmpty(ETt4)||isEditTextEmpty(ETt5)||isEditTextEmpty(ETt6)||isEditTextEmpty(ETt7)||isEditTextEmpty(ETt8)||isEditTextEmpty(ETt9)||isEditTextEmpty(ETt10)){
                            Toast.makeText(PiscinasSector8.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Piscinas p52= new Piscinas();
                        p52.setUid(UUID.randomUUID().toString());
                        p52.setFecha(currentDate);
                        p52.setDieta(Integer.parseInt(Objects.requireNonNull(dietaET.getText()).toString()));
                        p52.setSobrantetolva(Integer.parseInt(Objects.requireNonNull(sobranteTolvaET.getText()).toString()));
                        p52.setRecarga(Integer.parseInt(Objects.requireNonNull(recargaET.getText()).toString()));
                        p52.setAlvoleo(Integer.parseInt(Objects.requireNonNull(alVoleoET.getText()).toString()));
                        p52.setSobrantecaseta(Integer.parseInt(Objects.requireNonNull(sobranteCasetaET.getText()).toString()));
                        p52.setTipobalanceado(tipoBalanceadoSeleccionado);
                        p52.setT1(Integer.parseInt(Objects.requireNonNull(ETt1.getText()).toString()));
                        p52.setT2(Integer.parseInt(Objects.requireNonNull(ETt2.getText()).toString()));
                        p52.setT3(Integer.parseInt(Objects.requireNonNull(ETt3.getText()).toString()));
                        p52.setT4(Integer.parseInt(Objects.requireNonNull(ETt4.getText()).toString()));
                        p52.setT5(Integer.parseInt(Objects.requireNonNull(ETt5.getText()).toString()));
                        p52.setT6(Integer.parseInt(Objects.requireNonNull(ETt6.getText()).toString()));
                        p52.setT7(Integer.parseInt(Objects.requireNonNull(ETt7.getText()).toString()));
                        p52.setT8(Integer.parseInt(Objects.requireNonNull(ETt8.getText()).toString()));
                        p52.setT9(Integer.parseInt(Objects.requireNonNull(ETt9.getText()).toString()));
                        p52.setT10(Integer.parseInt(Objects.requireNonNull(ETt10.getText()).toString()));
                        p52.setRojo(getIntValue(ETRojo));
                        p52.setFresco(getIntValue(ETFresco));
                        p52.setReportado(getIntValue(ETReportado));
                        p52.setObservaciones(getStringValue(ETObservaciones));
                        databaseReference.child("Piscina 52").child(p52.getUid()).setValue(p52);
                        //limparcampospiscina1();
                        verificarDatosEnFirebase10T("Piscina 52",currentDate);
                        Toast.makeText(PiscinasSector8.this, "DATOS INGRESADOS", Toast.LENGTH_SHORT).show();
                    }else if (piscinaSeleccionada.equals("Piscina 53")) {
                        String tipoBalanceadoSeleccionado=autoCompleteTextViewTipos.getText().toString();
                        if (isEditTextEmpty(dietaET)||isEditTextEmpty(sobranteTolvaET)||isEditTextEmpty(alVoleoET)||isEditTextEmpty(sobranteCasetaET)
                                ||isEditTextEmpty(recargaET)||tipoBalanceadoSeleccionado.equals("")||isEditTextEmpty(ETt1)||isEditTextEmpty(ETt2)||isEditTextEmpty(ETt3)
                                ||isEditTextEmpty(ETt6)||isEditTextEmpty(ETt7)||isEditTextEmpty(ETt8)){
                            Toast.makeText(PiscinasSector8.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Piscinas p53= new Piscinas();
                        p53.setUid(UUID.randomUUID().toString());
                        p53.setFecha(currentDate);
                        p53.setDieta(Integer.parseInt(Objects.requireNonNull(dietaET.getText()).toString()));
                        p53.setSobrantetolva(Integer.parseInt(Objects.requireNonNull(sobranteTolvaET.getText()).toString()));
                        p53.setRecarga(Integer.parseInt(Objects.requireNonNull(recargaET.getText()).toString()));
                        p53.setAlvoleo(Integer.parseInt(Objects.requireNonNull(alVoleoET.getText()).toString()));
                        p53.setSobrantecaseta(Integer.parseInt(Objects.requireNonNull(sobranteCasetaET.getText()).toString()));
                        p53.setTipobalanceado(tipoBalanceadoSeleccionado);
                        p53.setT1(Integer.parseInt(Objects.requireNonNull(ETt1.getText()).toString()));
                        p53.setT2(Integer.parseInt(Objects.requireNonNull(ETt2.getText()).toString()));
                        p53.setT3(Integer.parseInt(Objects.requireNonNull(ETt3.getText()).toString()));
                        p53.setT4(Integer.parseInt(Objects.requireNonNull(ETt6.getText()).toString()));
                        p53.setT5(Integer.parseInt(Objects.requireNonNull(ETt7.getText()).toString()));
                        p53.setT6(Integer.parseInt(Objects.requireNonNull(ETt8.getText()).toString()));
                        p53.setRojo(getIntValue(ETRojo));
                        p53.setFresco(getIntValue(ETFresco));
                        p53.setReportado(getIntValue(ETReportado));
                        p53.setObservaciones(getStringValue(ETObservaciones));
                        databaseReference.child("Piscina 53").child(p53.getUid()).setValue(p53);
                        //limparcampospiscina1();
                        verificarDatosEnFirebase6T("Piscina 53",currentDate);
                        Toast.makeText(PiscinasSector8.this, "DATOS INGRESADOS", Toast.LENGTH_SHORT).show();
                    }
                    else if (piscinaSeleccionada.equals("Piscina 54")) {
                        String tipoBalanceadoSeleccionado=autoCompleteTextViewTipos.getText().toString();
                        if (isEditTextEmpty(dietaET)||isEditTextEmpty(sobranteTolvaET)||isEditTextEmpty(alVoleoET)||isEditTextEmpty(sobranteCasetaET)
                                ||isEditTextEmpty(recargaET)||tipoBalanceadoSeleccionado.equals("")||isEditTextEmpty(ETt1)||isEditTextEmpty(ETt2)||isEditTextEmpty(ETt3)
                                ||isEditTextEmpty(ETt6)||isEditTextEmpty(ETt7)||isEditTextEmpty(ETt8)){
                            Toast.makeText(PiscinasSector8.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Piscinas p54= new Piscinas();
                        p54.setUid(UUID.randomUUID().toString());
                        p54.setFecha(currentDate);
                        p54.setDieta(Integer.parseInt(Objects.requireNonNull(dietaET.getText()).toString()));
                        p54.setSobrantetolva(Integer.parseInt(Objects.requireNonNull(sobranteTolvaET.getText()).toString()));
                        p54.setRecarga(Integer.parseInt(Objects.requireNonNull(recargaET.getText()).toString()));
                        p54.setAlvoleo(Integer.parseInt(Objects.requireNonNull(alVoleoET.getText()).toString()));
                        p54.setSobrantecaseta(Integer.parseInt(Objects.requireNonNull(sobranteCasetaET.getText()).toString()));
                        p54.setTipobalanceado(tipoBalanceadoSeleccionado);
                        p54.setT1(Integer.parseInt(Objects.requireNonNull(ETt1.getText()).toString()));
                        p54.setT2(Integer.parseInt(Objects.requireNonNull(ETt2.getText()).toString()));
                        p54.setT3(Integer.parseInt(Objects.requireNonNull(ETt3.getText()).toString()));
                        p54.setT4(Integer.parseInt(Objects.requireNonNull(ETt6.getText()).toString()));
                        p54.setT5(Integer.parseInt(Objects.requireNonNull(ETt7.getText()).toString()));
                        p54.setT6(Integer.parseInt(Objects.requireNonNull(ETt8.getText()).toString()));
                        p54.setRojo(getIntValue(ETRojo));
                        p54.setFresco(getIntValue(ETFresco));
                        p54.setReportado(getIntValue(ETReportado));
                        p54.setObservaciones(getStringValue(ETObservaciones));
                        databaseReference.child("Piscina 54").child(p54.getUid()).setValue(p54);
                        //limparcampospiscina1();
                        verificarDatosEnFirebase6T("Piscina 54",currentDate);
                        Toast.makeText(PiscinasSector8.this, "DATOS INGRESADOS", Toast.LENGTH_SHORT).show();
                    } else if (piscinaSeleccionada.equals("Piscina 55")) {
                        String tipoBalanceadoSeleccionado=autoCompleteTextViewTipos.getText().toString();
                        if (isEditTextEmpty(dietaET)||isEditTextEmpty(sobranteTolvaET)||isEditTextEmpty(alVoleoET)||isEditTextEmpty(sobranteCasetaET)
                                ||isEditTextEmpty(recargaET)||tipoBalanceadoSeleccionado.equals("")||isEditTextEmpty(ETt1)||isEditTextEmpty(ETt2)||isEditTextEmpty(ETt3)
                                ||isEditTextEmpty(ETt6)||isEditTextEmpty(ETt7)||isEditTextEmpty(ETt8)){
                            Toast.makeText(PiscinasSector8.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Piscinas p55= new Piscinas();
                        p55.setUid(UUID.randomUUID().toString());
                        p55.setFecha(currentDate);
                        p55.setDieta(Integer.parseInt(Objects.requireNonNull(dietaET.getText()).toString()));
                        p55.setSobrantetolva(Integer.parseInt(Objects.requireNonNull(sobranteTolvaET.getText()).toString()));
                        p55.setRecarga(Integer.parseInt(Objects.requireNonNull(recargaET.getText()).toString()));
                        p55.setAlvoleo(Integer.parseInt(Objects.requireNonNull(alVoleoET.getText()).toString()));
                        p55.setSobrantecaseta(Integer.parseInt(Objects.requireNonNull(sobranteCasetaET.getText()).toString()));
                        p55.setTipobalanceado(tipoBalanceadoSeleccionado);
                        p55.setT1(Integer.parseInt(Objects.requireNonNull(ETt1.getText()).toString()));
                        p55.setT2(Integer.parseInt(Objects.requireNonNull(ETt2.getText()).toString()));
                        p55.setT3(Integer.parseInt(Objects.requireNonNull(ETt3.getText()).toString()));
                        p55.setT4(Integer.parseInt(Objects.requireNonNull(ETt6.getText()).toString()));
                        p55.setT5(Integer.parseInt(Objects.requireNonNull(ETt7.getText()).toString()));
                        p55.setT6(Integer.parseInt(Objects.requireNonNull(ETt8.getText()).toString()));
                        p55.setRojo(getIntValue(ETRojo));
                        p55.setFresco(getIntValue(ETFresco));
                        p55.setReportado(getIntValue(ETReportado));
                        p55.setObservaciones(getStringValue(ETObservaciones));
                        databaseReference.child("Piscina 55").child(p55.getUid()).setValue(p55);
                        //limparcampospiscina1();
                        verificarDatosEnFirebase6T("Piscina 55",currentDate);
                        Toast.makeText(PiscinasSector8.this, "DATOS INGRESADOS", Toast.LENGTH_SHORT).show();
                    } else if (piscinaSeleccionada.equals("Piscina 56")) {
                        String tipoBalanceadoSeleccionado=autoCompleteTextViewTipos.getText().toString();
                        if (isEditTextEmpty(dietaET)||isEditTextEmpty(sobranteTolvaET)||isEditTextEmpty(alVoleoET)||isEditTextEmpty(sobranteCasetaET)
                                ||isEditTextEmpty(recargaET)||tipoBalanceadoSeleccionado.equals("")||isEditTextEmpty(ETt1)||isEditTextEmpty(ETt2)||isEditTextEmpty(ETt3)
                                ||isEditTextEmpty(ETt6)||isEditTextEmpty(ETt7)||isEditTextEmpty(ETt8)){
                            Toast.makeText(PiscinasSector8.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Piscinas p56= new Piscinas();
                        p56.setUid(UUID.randomUUID().toString());
                        p56.setFecha(currentDate);
                        p56.setDieta(Integer.parseInt(Objects.requireNonNull(dietaET.getText()).toString()));
                        p56.setSobrantetolva(Integer.parseInt(Objects.requireNonNull(sobranteTolvaET.getText()).toString()));
                        p56.setRecarga(Integer.parseInt(Objects.requireNonNull(recargaET.getText()).toString()));
                        p56.setAlvoleo(Integer.parseInt(Objects.requireNonNull(alVoleoET.getText()).toString()));
                        p56.setSobrantecaseta(Integer.parseInt(Objects.requireNonNull(sobranteCasetaET.getText()).toString()));
                        p56.setTipobalanceado(tipoBalanceadoSeleccionado);
                        p56.setT1(Integer.parseInt(Objects.requireNonNull(ETt1.getText()).toString()));
                        p56.setT2(Integer.parseInt(Objects.requireNonNull(ETt2.getText()).toString()));
                        p56.setT3(Integer.parseInt(Objects.requireNonNull(ETt3.getText()).toString()));
                        p56.setT4(Integer.parseInt(Objects.requireNonNull(ETt6.getText()).toString()));
                        p56.setT5(Integer.parseInt(Objects.requireNonNull(ETt7.getText()).toString()));
                        p56.setT6(Integer.parseInt(Objects.requireNonNull(ETt8.getText()).toString()));
                        p56.setRojo(getIntValue(ETRojo));
                        p56.setFresco(getIntValue(ETFresco));
                        p56.setReportado(getIntValue(ETReportado));
                        p56.setObservaciones(getStringValue(ETObservaciones));
                        databaseReference.child("Piscina 56").child(p56.getUid()).setValue(p56);
                        //limparcampospiscina1();
                        verificarDatosEnFirebase6T("Piscina 56",currentDate);
                        Toast.makeText(PiscinasSector8.this, "DATOS INGRESADOS", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

    }


    private void inicializarListas() {

        piscinaTV = findViewById(R.id.tvPiscinas);
        autoCompleteTextViewPiscinas =findViewById(R.id.listaPiscinas);
        String[] opcionesPiscinas = getResources().getStringArray(R.array.options_listZ8);
        ArrayAdapter<String> adaptadorPiscinas = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, opcionesPiscinas);
        adaptadorPiscinas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autoCompleteTextViewPiscinas.setAdapter(adaptadorPiscinas);


        tiposBalanceadoTV=findViewById(R.id.tvTipoBalanceado);
        autoCompleteTextViewTipos = findViewById(R.id.listaTipoBalanceado);
        String[] opcionesTipos = getResources().getStringArray(R.array.balanceado_options);
        ArrayAdapter<String> adaptadorTipos = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                opcionesTipos
        );
        adaptadorTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autoCompleteTextViewTipos.setAdapter(adaptadorTipos);
        autoCompleteTextViewPiscinas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No se necesita implementar nada aquí
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                piscinaSeleccionada = s.toString();
                String fechaSeleccionada = currentDate;
                if (!piscinaSeleccionada.isEmpty()) {
                }

                // Aquí puedes manejar el texto cambiado
                if(piscinaSeleccionada.equals("Piscina 47")){
                    limpiarCamposPiscina1();
                    TVz1.setText("ZONA 1");
                    TVz2.setText("ZONA 2");
                    piscinaTV.setError(null);
                    TVt1.setVisibility(View.VISIBLE);
                    TVt1.setEnabled(true);
                    TVt2.setVisibility(View.VISIBLE);
                    TVt2.setEnabled(true);
                    TVt3.setVisibility(View.VISIBLE);
                    TVt3.setEnabled(true);
                    TVt4.setVisibility(View.VISIBLE);
                    TVt4.setEnabled(true);
                    TVt5.setVisibility(View.INVISIBLE);
                    TVt5.setEnabled(false);
                    TVt6.setVisibility(View.VISIBLE);
                    TVt6.setEnabled(true);
                    TVt6.setHint("TOLVA 5");
                    TVt7.setVisibility(View.VISIBLE);
                    TVt7.setEnabled(true);
                    TVt7.setHint("TOLVA 6");
                    TVt8.setVisibility(View.VISIBLE);
                    TVt8.setEnabled(true);
                    TVt8.setHint("TOLVA 7");
                    TVt9.setVisibility(View.VISIBLE);
                    TVt9.setEnabled(true);
                    TVt9.setHint("TOLVA 8");
                    TVt10.setVisibility(View.INVISIBLE);
                    TVt10.setEnabled(false);
                    botonGuardar.setEnabled(true);
                    verificarDatosEnFirebase8T("Piscina 47",fechaSeleccionada);
                } else if (piscinaSeleccionada.equals("Piscina 48")) {
                    limpiarCamposPiscina1();
                    TVz1.setText("ZONA 1");
                    TVz2.setText("ZONA 2");
                    piscinaTV.setError(null);
                    TVt1.setVisibility(View.VISIBLE);
                    TVt2.setVisibility(View.VISIBLE);
                    TVt3.setVisibility(View.VISIBLE);
                    TVt4.setVisibility(View.INVISIBLE);
                    TVt4.setEnabled(false);
                    TVt5.setVisibility(View.INVISIBLE);
                    TVt5.setEnabled(false);
                    TVt6.setVisibility(View.VISIBLE);
                    TVt7.setVisibility(View.VISIBLE);
                    TVt8.setVisibility(View.VISIBLE);
                    TVt9.setVisibility(View.INVISIBLE);
                    TVt10.setVisibility(View.INVISIBLE);
                    TVt9.setEnabled(false);
                    TVt10.setEnabled(false);
                    TVt6.setHint("TOLVA 4");
                    TVt7.setHint("TOLVA 5");
                    TVt8.setHint("TOLVA 6");
                    botonGuardar.setEnabled(true);
                    verificarDatosEnFirebase6T("Piscina 48",fechaSeleccionada);
                } else if(piscinaSeleccionada.equals("Piscina 49")){
                    limpiarCamposPiscina1();
                    TVz1.setText("ZONA 2");
                    TVz2.setText("ZONA 1");
                    piscinaTV.setError(null);
                    TVt1.setVisibility(View.VISIBLE);
                    TVt2.setVisibility(View.VISIBLE);
                    TVt3.setVisibility(View.VISIBLE);
                    TVt4.setVisibility(View.INVISIBLE);
                    TVt4.setEnabled(false);
                    TVt5.setVisibility(View.INVISIBLE);
                    TVt5.setEnabled(false);
                    TVt6.setVisibility(View.VISIBLE);
                    TVt7.setVisibility(View.VISIBLE);
                    TVt8.setVisibility(View.VISIBLE);
                    TVt9.setVisibility(View.INVISIBLE);
                    TVt10.setVisibility(View.INVISIBLE);
                    TVt9.setEnabled(false);
                    TVt10.setEnabled(false);
                    TVt6.setHint("TOLVA 4");
                    TVt7.setHint("TOLVA 5");
                    TVt8.setHint("TOLVA 6");
                    botonGuardar.setEnabled(true);
                    verificarDatosEnFirebase6T("Piscina 49",fechaSeleccionada);
                }else if(piscinaSeleccionada.equals("Piscina 50")){
                    limpiarCamposPiscina1();
                    TVz1.setText("ZONA 1");
                    TVz2.setText("ZONA 2");
                    piscinaTV.setError(null);
                    TVt1.setVisibility(View.VISIBLE);
                    TVt1.setEnabled(true);
                    TVt2.setVisibility(View.VISIBLE);
                    TVt2.setEnabled(true);
                    TVt3.setVisibility(View.VISIBLE);
                    TVt3.setEnabled(true);
                    TVt4.setVisibility(View.VISIBLE);
                    TVt4.setEnabled(true);
                    TVt5.setVisibility(View.INVISIBLE);
                    TVt5.setEnabled(false);
                    TVt6.setVisibility(View.VISIBLE);
                    TVt6.setEnabled(true);
                    TVt6.setHint("TOLVA 5");
                    TVt7.setVisibility(View.VISIBLE);
                    TVt7.setEnabled(true);
                    TVt7.setHint("TOLVA 6");
                    TVt8.setVisibility(View.VISIBLE);
                    TVt8.setEnabled(true);
                    TVt8.setHint("TOLVA 7");
                    TVt9.setVisibility(View.VISIBLE);
                    TVt9.setEnabled(true);
                    TVt9.setHint("TOLVA 8");
                    TVt10.setVisibility(View.INVISIBLE);
                    TVt10.setEnabled(false);
                    botonGuardar.setEnabled(true);
                    verificarDatosEnFirebase8T("Piscina 50",fechaSeleccionada);
                } else if (piscinaSeleccionada.equals("Piscina 51")) {
                    limpiarCamposPiscina1();
                    TVz1.setText("ZONA 1");
                    TVz2.setText("ZONA 2");
                    piscinaTV.setError(null);
                    TVt1.setVisibility(View.VISIBLE);
                    TVt1.setEnabled(true);
                    TVt2.setVisibility(View.VISIBLE);
                    TVt2.setEnabled(true);
                    TVt3.setVisibility(View.VISIBLE);
                    TVt3.setEnabled(true);
                    TVt4.setVisibility(View.VISIBLE);
                    TVt4.setEnabled(true);
                    TVt5.setVisibility(View.INVISIBLE);
                    TVt5.setEnabled(false);
                    TVt6.setVisibility(View.VISIBLE);
                    TVt6.setEnabled(true);
                    TVt6.setHint("TOLVA 5");
                    TVt7.setVisibility(View.VISIBLE);
                    TVt7.setEnabled(true);
                    TVt7.setHint("TOLVA 6");
                    TVt8.setVisibility(View.VISIBLE);
                    TVt8.setEnabled(true);
                    TVt8.setHint("TOLVA 7");
                    TVt9.setVisibility(View.VISIBLE);
                    TVt9.setEnabled(true);
                    TVt9.setHint("TOLVA 8");
                    TVt10.setVisibility(View.INVISIBLE);
                    TVt10.setEnabled(false);
                    botonGuardar.setEnabled(true);
                    verificarDatosEnFirebase8T("Piscina 51",fechaSeleccionada);
                } else if (piscinaSeleccionada.equals("Piscina 52")) {
                    limpiarCamposPiscina1();
                    TVz1.setText("ZONA 1");
                    TVz2.setText("ZONA 2");
                    piscinaTV.setError(null);
                    TVt1.setVisibility(View.VISIBLE);
                    TVt1.setEnabled(true);
                    TVt2.setVisibility(View.VISIBLE);
                    TVt2.setEnabled(true);
                    TVt3.setVisibility(View.VISIBLE);
                    TVt3.setEnabled(true);
                    TVt4.setVisibility(View.VISIBLE);
                    TVt4.setEnabled(true);
                    TVt5.setVisibility(View.VISIBLE);
                    TVt5.setEnabled(true);
                    TVt6.setVisibility(View.VISIBLE);
                    TVt6.setEnabled(true);
                    TVt6.setHint("TOLVA 6");
                    TVt7.setVisibility(View.VISIBLE);
                    TVt7.setEnabled(true);
                    TVt7.setHint("TOLVA 7");
                    TVt8.setVisibility(View.VISIBLE);
                    TVt8.setEnabled(true);
                    TVt8.setHint("TOLVA 8");
                    TVt9.setVisibility(View.VISIBLE);
                    TVt9.setEnabled(true);
                    TVt9.setHint("TOLVA 9");
                    TVt10.setVisibility(View.VISIBLE);
                    TVt10.setEnabled(true);
                    TVt10.setHint("TOLVA 10");
                    botonGuardar.setEnabled(true);
                    verificarDatosEnFirebase10T("Piscina 52",fechaSeleccionada);
                } else if (piscinaSeleccionada.equals("Piscina 53")) {
                    TVz1.setText("ZONA 2");
                    TVz2.setText("ZONA 1");
                    limpiarCamposPiscina1();
                    piscinaTV.setError(null);
                    TVt1.setVisibility(View.VISIBLE);
                    TVt1.setEnabled(true);
                    TVt2.setVisibility(View.VISIBLE);
                    TVt2.setEnabled(true);
                    TVt3.setVisibility(View.VISIBLE);
                    TVt3.setEnabled(true);
                    TVt4.setVisibility(View.INVISIBLE);
                    TVt4.setEnabled(false);
                    TVt5.setVisibility(View.INVISIBLE);
                    TVt5.setEnabled(false);
                    TVt6.setVisibility(View.VISIBLE);
                    TVt6.setEnabled(true);
                    TVt6.setHint("TOLVA 4");
                    TVt7.setVisibility(View.VISIBLE);
                    TVt7.setEnabled(true);
                    TVt7.setHint("TOLVA 5");
                    TVt8.setVisibility(View.VISIBLE);
                    TVt8.setEnabled(true);
                    TVt8.setHint("TOLVA 6");
                    TVt9.setVisibility(View.INVISIBLE);
                    TVt9.setEnabled(false);
                    TVt10.setVisibility(View.INVISIBLE);
                    TVt9.setEnabled(false);
                    botonGuardar.setEnabled(true);
                    verificarDatosEnFirebase6T("Piscina 53",fechaSeleccionada);
                }else if (piscinaSeleccionada.equals("Piscina 54")) {
                    TVz1.setText("ZONA 2");
                    TVz2.setText("ZONA 1");
                    limpiarCamposPiscina1();
                    piscinaTV.setError(null);
                    TVt1.setVisibility(View.VISIBLE);
                    TVt1.setEnabled(true);
                    TVt2.setVisibility(View.VISIBLE);
                    TVt2.setEnabled(true);
                    TVt3.setVisibility(View.VISIBLE);
                    TVt3.setEnabled(true);
                    TVt4.setVisibility(View.INVISIBLE);
                    TVt4.setEnabled(false);
                    TVt5.setVisibility(View.INVISIBLE);
                    TVt5.setEnabled(false);
                    TVt6.setVisibility(View.VISIBLE);
                    TVt6.setEnabled(true);
                    TVt6.setHint("TOLVA 4");
                    TVt7.setVisibility(View.VISIBLE);
                    TVt7.setEnabled(true);
                    TVt7.setHint("TOLVA 5");
                    TVt8.setVisibility(View.VISIBLE);
                    TVt8.setEnabled(true);
                    TVt8.setHint("TOLVA 6");
                    TVt9.setVisibility(View.INVISIBLE);
                    TVt9.setEnabled(false);
                    TVt10.setVisibility(View.INVISIBLE);
                    TVt9.setEnabled(false);
                    botonGuardar.setEnabled(true);
                    verificarDatosEnFirebase6T("Piscina 54",fechaSeleccionada);
                } else if (piscinaSeleccionada.equals("Piscina 55")) {
                    limpiarCamposPiscina1();
                    TVz1.setText("ZONA 1");
                    TVz2.setText("ZONA 2");
                    piscinaTV.setError(null);
                    TVt1.setVisibility(View.VISIBLE);
                    TVt2.setVisibility(View.VISIBLE);
                    TVt3.setVisibility(View.VISIBLE);
                    TVt4.setVisibility(View.INVISIBLE);
                    TVt4.setEnabled(false);
                    TVt5.setVisibility(View.INVISIBLE);
                    TVt5.setEnabled(false);
                    TVt6.setVisibility(View.VISIBLE);
                    TVt7.setVisibility(View.VISIBLE);
                    TVt8.setVisibility(View.VISIBLE);
                    TVt9.setVisibility(View.INVISIBLE);
                    TVt10.setVisibility(View.INVISIBLE);
                    TVt9.setEnabled(false);
                    TVt10.setEnabled(false);
                    TVt6.setHint("TOLVA 4");
                    TVt7.setHint("TOLVA 5");
                    TVt8.setHint("TOLVA 6");
                    botonGuardar.setEnabled(true);
                    verificarDatosEnFirebase6T("Piscina 55",fechaSeleccionada);
                } else if (piscinaSeleccionada.equals("Piscina 56")) {
                    limpiarCamposPiscina1();
                    TVz1.setText("ZONA 1");
                    TVz2.setText("ZONA 2");
                    piscinaTV.setError(null);
                    TVt1.setVisibility(View.VISIBLE);
                    TVt2.setVisibility(View.VISIBLE);
                    TVt3.setVisibility(View.VISIBLE);
                    TVt4.setVisibility(View.INVISIBLE);
                    TVt4.setEnabled(false);
                    TVt5.setVisibility(View.INVISIBLE);
                    TVt5.setEnabled(false);
                    TVt6.setVisibility(View.VISIBLE);
                    TVt7.setVisibility(View.VISIBLE);
                    TVt8.setVisibility(View.VISIBLE);
                    TVt9.setVisibility(View.INVISIBLE);
                    TVt10.setVisibility(View.INVISIBLE);
                    TVt9.setEnabled(false);
                    TVt10.setEnabled(false);
                    TVt6.setHint("TOLVA 4");
                    TVt7.setHint("TOLVA 5");
                    TVt8.setHint("TOLVA 6");
                    botonGuardar.setEnabled(true);
                    verificarDatosEnFirebase6T("Piscina 56",fechaSeleccionada);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No se necesita implementar nada aquí
            }
        });


    }

    private void verificarDatosEnFirebase5T(final String piscinaSeleccionada, final String fechaSeleccionada) {
        // Limpiar los campos de la interfaz de usuario antes de la nueva consulta
        limpiarCamposPiscina1();

        DatabaseReference referenciaPiscina = databaseReference.child(piscinaSeleccionada);
        //Toast.makeText(PiscinasSector3.this, referenciaPiscina.toString(), Toast.LENGTH_SHORT).show();
        referenciaPiscina.orderByChild("fecha").equalTo(fechaSeleccionada).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Datos encontrados para la fecha seleccionada
                    //Toast.makeText(PiscinasSector3.this, "Datos encontrados para esta piscina y fecha", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(PiscinasSector3.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Recuperar los valores y actualizar la interfaz de usuario
                        String tipoBalanceado = snapshot.child("tipobalanceado").getValue(String.class);
                        Long dieta = snapshot.child("dieta").getValue(Long.class);
                        Long sobranteTolva = snapshot.child("sobrantetolva").getValue(Long.class);
                        Long recarga = snapshot.child("recarga").getValue(Long.class);
                        Long alvoleo = snapshot.child("alvoleo").getValue(Long.class);
                        Long sobranteCaseta = snapshot.child("sobrantecaseta").getValue(Long.class);
                        Long t1 = snapshot.child("t1").getValue(Long.class);
                        Long t2 = snapshot.child("t2").getValue(Long.class);
                        Long t3 = snapshot.child("t3").getValue(Long.class);
                        Long t4 = snapshot.child("t4").getValue(Long.class);
                        Long t5 = snapshot.child("t5").getValue(Long.class);

                        // Convertir Long a String solo si los valores no son null
                        String dietaS = (dieta != null) ? dieta.toString() : "";
                        String sobranteTolvaS = (sobranteTolva != null) ? sobranteTolva.toString() : "";
                        String recargaS = (recarga != null) ? recarga.toString() : "";
                        String alvoleoS = (alvoleo != null) ? alvoleo.toString() : "";
                        String sobranteCasetaS = (sobranteCaseta != null) ? sobranteCaseta.toString() : "";
                        String tipoBalanceadoS = (tipoBalanceado != null) ? tipoBalanceado : "";
                        String t1S = (t1 != null) ? t1.toString() : "";
                        String t2S = (t2 != null) ? t2.toString() : "";
                        String t3S = (t3 != null) ? t3.toString() : "";
                        String t4S = (t4 != null) ? t4.toString() : "";
                        String t5S = (t5 != null) ? t5.toString() : "";

                        // Actualizar los campos de la interfaz de usuario
                        dietaET.setText(dietaS);
                        recargaET.setText(recargaS);
                        sobranteTolvaET.setText(sobranteTolvaS);
                        alVoleoET.setText(alvoleoS);
                        sobranteCasetaET.setText(sobranteCasetaS);
                        autoCompleteTextViewTipos.setText(tipoBalanceadoS);

                        ETt1.setText(t1S);
                        ETt2.setText(t2S);
                        ETt3.setText(t3S);
                        ETt4.setText(t4S);
                        ETt5.setText(t5S);

                        botonActualizar.setVisibility(View.VISIBLE);
                        botonGuardar.setEnabled(false); // Desactiva el botón de guardar si es necesario
                        actualizarDatosEnFirebase5T(piscinaSeleccionada,currentDate);
                    }
                } else {
                    // No se encontraron datos para la fecha seleccionada
                    //Toast.makeText(PiscinasSector8.this, "No existen datos para esta fecha", Toast.LENGTH_SHORT).show();

                    // Habilitar el botón de guardar
                    botonGuardar.setEnabled(true);
                    botonGuardar.setText("GUARDAR");
                    botonActualizar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejo de errores
                Toast.makeText(PiscinasSector8.this, "Error al recuperar los datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void actualizarDatosEnFirebase5T(final String piscinaSeleccionada, final String fechaSeleccionada) {
        botonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference referenciaPiscina = databaseReference.child(piscinaSeleccionada);

                // Buscar el nodo por fecha
                referenciaPiscina.orderByChild("fecha").equalTo(fechaSeleccionada).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                // Obtén la referencia del nodo actual
                                DatabaseReference nodoReferencia = snapshot.getRef();

                                // Actualiza los valores en Firebase
                                nodoReferencia.child("dieta").setValue(parseLong(dietaET.getText().toString()));
                                nodoReferencia.child("sobrantetolva").setValue(parseLong(sobranteTolvaET.getText().toString()));
                                nodoReferencia.child("recarga").setValue(parseLong(recargaET.getText().toString()));
                                nodoReferencia.child("alvoleo").setValue(parseLong(alVoleoET.getText().toString()));
                                nodoReferencia.child("sobrantecaseta").setValue(parseLong(sobranteCasetaET.getText().toString()));
                                nodoReferencia.child("tipobalanceado").setValue(autoCompleteTextViewTipos.getText().toString());
                                nodoReferencia.child("t1").setValue(parseLong(ETt1.getText().toString()));
                                nodoReferencia.child("t2").setValue(parseLong(ETt2.getText().toString()));
                                nodoReferencia.child("t3").setValue(parseLong(ETt3.getText().toString()));
                                nodoReferencia.child("t4").setValue(parseLong(ETt4.getText().toString()));
                                nodoReferencia.child("t5").setValue(parseLong(ETt5.getText().toString()));

                                Toast.makeText(PiscinasSector8.this, "DATOS ACTUALIZADOS", Toast.LENGTH_SHORT).show();
                                inicializarListas();
                                inicializarCamposVariables();
                                limpiarCamposPiscina1();

                            }
                        } else {
                            Toast.makeText(PiscinasSector8.this, "No se encontraron datos para actualizar", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(PiscinasSector8.this, "Error al actualizar datos en Firebase: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void verificarDatosEnFirebase8T(final String piscinaSeleccionada, final String fechaSeleccionada) {
        // Limpiar los campos de la interfaz de usuario antes de la nueva consulta
        limpiarCamposPiscina1();

        DatabaseReference referenciaPiscina = databaseReference.child(piscinaSeleccionada);
        //Toast.makeText(PiscinasSector1.this, referenciaPiscina.toString(), Toast.LENGTH_SHORT).show();
        referenciaPiscina.orderByChild("fecha").equalTo(fechaSeleccionada).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Datos encontrados para la fecha seleccionada
                    //Toast.makeText(PiscinasSector1.this, "Datos encontrados para esta piscina y fecha", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(PiscinasSector1.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Recuperar los valores y actualizar la interfaz de usuario
                        String tipoBalanceado = snapshot.child("tipobalanceado").getValue(String.class);
                        Long dieta = snapshot.child("dieta").getValue(Long.class);
                        Long sobranteTolva = snapshot.child("sobrantetolva").getValue(Long.class);
                        Long recarga = snapshot.child("recarga").getValue(Long.class);
                        Long alvoleo = snapshot.child("alvoleo").getValue(Long.class);
                        Long sobranteCaseta = snapshot.child("sobrantecaseta").getValue(Long.class);
                        Long t1 = snapshot.child("t1").getValue(Long.class);
                        Long t2 = snapshot.child("t2").getValue(Long.class);
                        Long t3 = snapshot.child("t3").getValue(Long.class);
                        Long t4 = snapshot.child("t4").getValue(Long.class);
                        Long t5 = snapshot.child("t5").getValue(Long.class);
                        Long t6 = snapshot.child("t6").getValue(Long.class);
                        Long t7 = snapshot.child("t7").getValue(Long.class);
                        Long t8 = snapshot.child("t8").getValue(Long.class);

                        Long rojo = snapshot.child("rojo").getValue(Long.class);
                        Long fresco = snapshot.child("fresco").getValue(Long.class);
                        Long reportado = snapshot.child("reportado").getValue(Long.class);
                        String observaciones = snapshot.child("observaciones").getValue(String.class);

                        // Convertir Long a String solo si los valores no son null
                        String dietaS = (dieta != null) ? dieta.toString() : "";
                        String sobranteTolvaS = (sobranteTolva != null) ? sobranteTolva.toString() : "";
                        String recargaS = (recarga != null) ? recarga.toString() : "";
                        String alvoleoS = (alvoleo != null) ? alvoleo.toString() : "";
                        String sobranteCasetaS = (sobranteCaseta != null) ? sobranteCaseta.toString() : "";
                        String tipoBalanceadoS = (tipoBalanceado != null) ? tipoBalanceado : "";
                        String t1S = (t1 != null) ? t1.toString() : "";
                        String t2S = (t2 != null) ? t2.toString() : "";
                        String t3S = (t3 != null) ? t3.toString() : "";
                        String t4S = (t4 != null) ? t4.toString() : "";
                        String t5S = (t5 != null) ? t5.toString() : "";
                        String t6S = (t6 != null) ? t6.toString() : "";
                        String t7S = (t7 != null) ? t7.toString() : "";
                        String t8S = (t8 != null) ? t8.toString() : "";

                        String rojoS = (rojo != null) ? rojo.toString() : "";
                        String frescoS= (fresco != null) ? fresco.toString() : "";
                        String reportadoS = (reportado != null) ? reportado.toString() : "";
                        String observacionesS = (observaciones != null) ? observaciones : "";

                        // Actualizar los campos de la interfaz de usuario
                        dietaET.setText(dietaS);
                        recargaET.setText(recargaS);
                        sobranteTolvaET.setText(sobranteTolvaS);
                        alVoleoET.setText(alvoleoS);
                        sobranteCasetaET.setText(sobranteCasetaS);
                        autoCompleteTextViewTipos.setText(tipoBalanceadoS);

                        ETt1.setText(t1S);
                        ETt2.setText(t2S);
                        ETt3.setText(t3S);
                        ETt4.setText(t4S);
                        ETt6.setText(t5S);
                        ETt7.setText(t6S);
                        ETt8.setText(t7S);
                        ETt9.setText(t8S);

                        ETRojo.setText(rojoS);
                        ETFresco.setText(frescoS);
                        ETReportado.setText(reportadoS);
                        ETObservaciones.setText(observacionesS);

                        botonActualizar.setVisibility(View.VISIBLE);
                        botonGuardar.setEnabled(false); // Desactiva el botón de guardar si es necesario
                        actualizarDatosEnFirebase8T(piscinaSeleccionada,currentDate);
                    }
                } else {
                    // No se encontraron datos para la fecha seleccionada
                    //Toast.makeText(PiscinasSector1.this, "No existen datos para esta fecha", Toast.LENGTH_SHORT).show();

                    // Habilitar el botón de guardar
                    botonGuardar.setEnabled(true);
                    botonGuardar.setText("GUARDAR");
                    botonActualizar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejo de errores
                Toast.makeText(PiscinasSector8.this, "Error al recuperar los datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actualizarDatosEnFirebase8T(final String piscinaSeleccionada, final String fechaSeleccionada) {
        botonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference referenciaPiscina = databaseReference.child(piscinaSeleccionada);

                // Buscar el nodo por fecha
                referenciaPiscina.orderByChild("fecha").equalTo(fechaSeleccionada).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                // Obtén la referencia del nodo actual
                                DatabaseReference nodoReferencia = snapshot.getRef();

                                // Actualiza los valores en Firebase
                                nodoReferencia.child("dieta").setValue(parseLong(dietaET.getText().toString()));
                                nodoReferencia.child("sobrantetolva").setValue(parseLong(sobranteTolvaET.getText().toString()));
                                nodoReferencia.child("recarga").setValue(parseLong(recargaET.getText().toString()));
                                nodoReferencia.child("alvoleo").setValue(parseLong(alVoleoET.getText().toString()));
                                nodoReferencia.child("sobrantecaseta").setValue(parseLong(sobranteCasetaET.getText().toString()));
                                nodoReferencia.child("tipobalanceado").setValue(autoCompleteTextViewTipos.getText().toString());
                                nodoReferencia.child("t1").setValue(parseLong(ETt1.getText().toString()));
                                nodoReferencia.child("t2").setValue(parseLong(ETt2.getText().toString()));
                                nodoReferencia.child("t3").setValue(parseLong(ETt3.getText().toString()));
                                nodoReferencia.child("t4").setValue(parseLong(ETt4.getText().toString()));
                                nodoReferencia.child("t5").setValue(parseLong(ETt6.getText().toString()));
                                nodoReferencia.child("t6").setValue(parseLong(ETt7.getText().toString()));
                                nodoReferencia.child("t7").setValue(parseLong(ETt8.getText().toString()));
                                nodoReferencia.child("t8").setValue(parseLong(ETt9.getText().toString()));

                                nodoReferencia.child("rojo").setValue(parseLong(ETRojo.getText().toString()));
                                nodoReferencia.child("fresco").setValue(parseLong(ETFresco.getText().toString()));
                                nodoReferencia.child("reportado").setValue(parseLong(ETReportado.getText().toString()));
                                nodoReferencia.child("observaciones").setValue(ETObservaciones.getText().toString());



                                Toast.makeText(PiscinasSector8.this, "DATOS ACTUALIZADOS", Toast.LENGTH_SHORT).show();
                                inicializarListas();
                                inicializarCamposVariables();
                                limpiarCamposPiscina1();

                            }
                        } else {
                            Toast.makeText(PiscinasSector8.this, "No se encontraron datos para actualizar", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(PiscinasSector8.this, "Error al actualizar datos en Firebase: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void verificarDatosEnFirebase6T(final String piscinaSeleccionada, final String fechaSeleccionada) {
        limpiarCamposPiscina1();

        DatabaseReference referenciaPiscina = databaseReference.child(piscinaSeleccionada);
        //Toast.makeText(PiscinasSector8.this, referenciaPiscina.toString(), Toast.LENGTH_SHORT).show();
        referenciaPiscina.orderByChild("fecha").equalTo(fechaSeleccionada).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Datos encontrados para la fecha seleccionada
                    //Toast.makeText(PiscinasSector2.this, "Datos encontrados para esta piscina y fecha", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(PiscinasSector2.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Recuperar los valores y actualizar la interfaz de usuario
                        String tipoBalanceado = snapshot.child("tipobalanceado").getValue(String.class);
                        Long dieta = snapshot.child("dieta").getValue(Long.class);
                        Long sobranteTolva = snapshot.child("sobrantetolva").getValue(Long.class);
                        Long recarga = snapshot.child("recarga").getValue(Long.class);
                        Long alvoleo = snapshot.child("alvoleo").getValue(Long.class);
                        Long sobranteCaseta = snapshot.child("sobrantecaseta").getValue(Long.class);
                        Long t1 = snapshot.child("t1").getValue(Long.class);
                        Long t2 = snapshot.child("t2").getValue(Long.class);
                        Long t3 = snapshot.child("t3").getValue(Long.class);
                        Long t4 = snapshot.child("t4").getValue(Long.class);
                        Long t5 = snapshot.child("t5").getValue(Long.class);
                        Long t6 = snapshot.child("t6").getValue(Long.class);

                        Long rojo = snapshot.child("rojo").getValue(Long.class);
                        Long fresco = snapshot.child("fresco").getValue(Long.class);
                        Long reportado = snapshot.child("reportado").getValue(Long.class);
                        String observaciones = snapshot.child("observaciones").getValue(String.class);

                        // Convertir Long a String solo si los valores no son null
                        String dietaS = (dieta != null) ? dieta.toString() : "";
                        String sobranteTolvaS = (sobranteTolva != null) ? sobranteTolva.toString() : "";
                        String recargaS = (recarga != null) ? recarga.toString() : "";
                        String alvoleoS = (alvoleo != null) ? alvoleo.toString() : "";
                        String sobranteCasetaS = (sobranteCaseta != null) ? sobranteCaseta.toString() : "";
                        String tipoBalanceadoS = (tipoBalanceado != null) ? tipoBalanceado : "";
                        String t1S = (t1 != null) ? t1.toString() : "";
                        String t2S = (t2 != null) ? t2.toString() : "";
                        String t3S = (t3 != null) ? t3.toString() : "";
                        String t4S = (t4 != null) ? t4.toString() : "";
                        String t5S = (t5 != null) ? t5.toString() : "";
                        String t6S = (t6 != null) ? t6.toString() : "";

                        String rojoS = (rojo != null) ? rojo.toString() : "";
                        String frescoS= (fresco != null) ? fresco.toString() : "";
                        String reportadoS = (reportado != null) ? reportado.toString() : "";
                        String observacionesS = (observaciones != null) ? observaciones : "";

                        // Actualizar los campos de la interfaz de usuario
                        dietaET.setText(dietaS);
                        recargaET.setText(recargaS);
                        sobranteTolvaET.setText(sobranteTolvaS);
                        alVoleoET.setText(alvoleoS);
                        sobranteCasetaET.setText(sobranteCasetaS);
                        autoCompleteTextViewTipos.setText(tipoBalanceadoS);

                        ETt1.setText(t1S);
                        ETt2.setText(t2S);
                        ETt3.setText(t3S);
                        ETt6.setText(t4S);
                        ETt7.setText(t5S);
                        ETt8.setText(t6S);

                        ETRojo.setText(rojoS);
                        ETFresco.setText(frescoS);
                        ETReportado.setText(reportadoS);
                        ETObservaciones.setText(observacionesS);

                        botonActualizar.setVisibility(View.VISIBLE);
                        botonGuardar.setEnabled(false); // Desactiva el botón de guardar si es necesario
                        actualizarDatosEnFirebase6T(piscinaSeleccionada,currentDate);
                    }
                } else {
                    // No se encontraron datos para la fecha seleccionada
                    //Toast.makeText(PiscinasSector8.this, "No existen datos para esta fecha", Toast.LENGTH_SHORT).show();

                    // Habilitar el botón de guardar
                    botonGuardar.setEnabled(true);
                    botonGuardar.setText("GUARDAR");
                    botonActualizar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejo de errores
                Toast.makeText(PiscinasSector8.this, "Error al recuperar los datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actualizarDatosEnFirebase6T(final String piscinaSeleccionada, final String fechaSeleccionada) {
        botonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference referenciaPiscina = databaseReference.child(piscinaSeleccionada);

                // Buscar el nodo por fecha
                referenciaPiscina.orderByChild("fecha").equalTo(fechaSeleccionada).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                // Obtén la referencia del nodo actual
                                DatabaseReference nodoReferencia = snapshot.getRef();

                                // Actualiza los valores en Firebase
                                nodoReferencia.child("dieta").setValue(parseLong(dietaET.getText().toString()));
                                nodoReferencia.child("sobrantetolva").setValue(parseLong(sobranteTolvaET.getText().toString()));
                                nodoReferencia.child("recarga").setValue(parseLong(recargaET.getText().toString()));
                                nodoReferencia.child("alvoleo").setValue(parseLong(alVoleoET.getText().toString()));
                                nodoReferencia.child("sobrantecaseta").setValue(parseLong(sobranteCasetaET.getText().toString()));
                                nodoReferencia.child("tipobalanceado").setValue(autoCompleteTextViewTipos.getText().toString());
                                nodoReferencia.child("t1").setValue(parseLong(ETt1.getText().toString()));
                                nodoReferencia.child("t2").setValue(parseLong(ETt2.getText().toString()));
                                nodoReferencia.child("t3").setValue(parseLong(ETt3.getText().toString()));
                                nodoReferencia.child("t4").setValue(parseLong(ETt6.getText().toString()));
                                nodoReferencia.child("t5").setValue(parseLong(ETt7.getText().toString()));
                                nodoReferencia.child("t6").setValue(parseLong(ETt8.getText().toString()));

                                nodoReferencia.child("rojo").setValue(parseLong(ETRojo.getText().toString()));
                                nodoReferencia.child("fresco").setValue(parseLong(ETFresco.getText().toString()));
                                nodoReferencia.child("reportado").setValue(parseLong(ETReportado.getText().toString()));
                                nodoReferencia.child("observaciones").setValue(ETObservaciones.getText().toString());


                                Toast.makeText(PiscinasSector8.this, "DATOS ACTUALIZADOS", Toast.LENGTH_SHORT).show();
                                inicializarListas();
                                inicializarCamposVariables();
                                limpiarCamposPiscina1();

                            }
                        } else {
                            Toast.makeText(PiscinasSector8.this, "No se encontraron datos para actualizar", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(PiscinasSector8.this, "Error al actualizar datos en Firebase: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void verificarDatosEnFirebase7T(final String piscinaSeleccionada, final String fechaSeleccionada) {

        limpiarCamposPiscina1();

        DatabaseReference referenciaPiscina = databaseReference.child(piscinaSeleccionada);
        //Toast.makeText(PiscinasSector8.this, referenciaPiscina.toString(), Toast.LENGTH_SHORT).show();
        referenciaPiscina.orderByChild("fecha").equalTo(fechaSeleccionada).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Datos encontrados para la fecha seleccionada
                    //Toast.makeText(PiscinasSector3.this, "Datos encontrados para esta piscina y fecha", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(PiscinasSector3.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Recuperar los valores y actualizar la interfaz de usuario
                        String tipoBalanceado = snapshot.child("tipobalanceado").getValue(String.class);
                        Long dieta = snapshot.child("dieta").getValue(Long.class);
                        Long sobranteTolva = snapshot.child("sobrantetolva").getValue(Long.class);
                        Long recarga = snapshot.child("recarga").getValue(Long.class);
                        Long alvoleo = snapshot.child("alvoleo").getValue(Long.class);
                        Long sobranteCaseta = snapshot.child("sobrantecaseta").getValue(Long.class);
                        Long t1 = snapshot.child("t1").getValue(Long.class);
                        Long t2 = snapshot.child("t2").getValue(Long.class);
                        Long t3 = snapshot.child("t3").getValue(Long.class);
                        Long t4 = snapshot.child("t4").getValue(Long.class);
                        Long t5 = snapshot.child("t5").getValue(Long.class);
                        Long t6 = snapshot.child("t6").getValue(Long.class);
                        Long t7 = snapshot.child("t7").getValue(Long.class);

                        Long rojo = snapshot.child("rojo").getValue(Long.class);
                        Long fresco = snapshot.child("fresco").getValue(Long.class);
                        Long reportado = snapshot.child("reportado").getValue(Long.class);
                        String observaciones = snapshot.child("observaciones").getValue(String.class);


                        // Convertir Long a String solo si los valores no son null
                        String dietaS = (dieta != null) ? dieta.toString() : "";
                        String sobranteTolvaS = (sobranteTolva != null) ? sobranteTolva.toString() : "";
                        String recargaS = (recarga != null) ? recarga.toString() : "";
                        String alvoleoS = (alvoleo != null) ? alvoleo.toString() : "";
                        String sobranteCasetaS = (sobranteCaseta != null) ? sobranteCaseta.toString() : "";
                        String tipoBalanceadoS = (tipoBalanceado != null) ? tipoBalanceado : "";
                        String t1S = (t1 != null) ? t1.toString() : "";
                        String t2S = (t2 != null) ? t2.toString() : "";
                        String t3S = (t3 != null) ? t3.toString() : "";
                        String t4S = (t4 != null) ? t4.toString() : "";
                        String t5S = (t5 != null) ? t5.toString() : "";
                        String t6S = (t6 != null) ? t6.toString() : "";
                        String t7S = (t7 != null) ? t7.toString() : "";

                        String rojoS = (rojo != null) ? rojo.toString() : "";
                        String frescoS= (fresco != null) ? fresco.toString() : "";
                        String reportadoS = (reportado != null) ? reportado.toString() : "";
                        String observacionesS = (observaciones != null) ? observaciones : "";

                        // Actualizar los campos de la interfaz de usuario
                        dietaET.setText(dietaS);
                        recargaET.setText(recargaS);
                        sobranteTolvaET.setText(sobranteTolvaS);
                        alVoleoET.setText(alvoleoS);
                        sobranteCasetaET.setText(sobranteCasetaS);
                        autoCompleteTextViewTipos.setText(tipoBalanceadoS);

                        ETt1.setText(t1S);
                        ETt2.setText(t2S);
                        ETt3.setText(t3S);
                        ETt4.setText(t4S);
                        ETt6.setText(t5S);
                        ETt7.setText(t6S);
                        ETt8.setText(t7S);

                        ETRojo.setText(rojoS);
                        ETFresco.setText(frescoS);
                        ETReportado.setText(reportadoS);
                        ETObservaciones.setText(observacionesS);

                        botonActualizar.setVisibility(View.VISIBLE);
                        botonGuardar.setEnabled(false); // Desactiva el botón de guardar si es necesario
                        actualizarDatosEnFirebase7T(piscinaSeleccionada,currentDate);
                    }
                } else {
                    // No se encontraron datos para la fecha seleccionada
                    //Toast.makeText(PiscinasSector8.this, "No existen datos para esta fecha", Toast.LENGTH_SHORT).show();

                    // Habilitar el botón de guardar
                    botonGuardar.setEnabled(true);
                    botonGuardar.setText("GUARDAR");
                    botonActualizar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejo de errores
                Toast.makeText(PiscinasSector8.this, "Error al recuperar los datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actualizarDatosEnFirebase7T(final String piscinaSeleccionada, final String fechaSeleccionada) {
        botonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference referenciaPiscina = databaseReference.child(piscinaSeleccionada);

                // Buscar el nodo por fecha
                referenciaPiscina.orderByChild("fecha").equalTo(fechaSeleccionada).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                // Obtén la referencia del nodo actual
                                DatabaseReference nodoReferencia = snapshot.getRef();

                                // Actualiza los valores en Firebase
                                nodoReferencia.child("dieta").setValue(parseLong(dietaET.getText().toString()));
                                nodoReferencia.child("sobrantetolva").setValue(parseLong(sobranteTolvaET.getText().toString()));
                                nodoReferencia.child("recarga").setValue(parseLong(recargaET.getText().toString()));
                                nodoReferencia.child("alvoleo").setValue(parseLong(alVoleoET.getText().toString()));
                                nodoReferencia.child("sobrantecaseta").setValue(parseLong(sobranteCasetaET.getText().toString()));
                                nodoReferencia.child("tipobalanceado").setValue(autoCompleteTextViewTipos.getText().toString());
                                nodoReferencia.child("t1").setValue(parseLong(ETt1.getText().toString()));
                                nodoReferencia.child("t2").setValue(parseLong(ETt2.getText().toString()));
                                nodoReferencia.child("t3").setValue(parseLong(ETt3.getText().toString()));
                                nodoReferencia.child("t4").setValue(parseLong(ETt4.getText().toString()));
                                nodoReferencia.child("t5").setValue(parseLong(ETt6.getText().toString()));
                                nodoReferencia.child("t6").setValue(parseLong(ETt7.getText().toString()));
                                nodoReferencia.child("t7").setValue(parseLong(ETt8.getText().toString()));

                                nodoReferencia.child("rojo").setValue(parseLong(ETRojo.getText().toString()));
                                nodoReferencia.child("fresco").setValue(parseLong(ETFresco.getText().toString()));
                                nodoReferencia.child("reportado").setValue(parseLong(ETReportado.getText().toString()));
                                nodoReferencia.child("observaciones").setValue(ETObservaciones.getText().toString());

                                Toast.makeText(PiscinasSector8.this, "DATOS ACTUALIZADOS", Toast.LENGTH_SHORT).show();
                                inicializarListas();
                                inicializarCamposVariables();
                                limpiarCamposPiscina1();

                            }
                        } else {
                            Toast.makeText(PiscinasSector8.this, "No se encontraron datos para actualizar", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(PiscinasSector8.this, "Error al actualizar datos en Firebase: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void verificarDatosEnFirebase9T(final String piscinaSeleccionada, final String fechaSeleccionada) {
        // Limpiar los campos de la interfaz de usuario antes de la nueva consulta
        limpiarCamposPiscina1();

        DatabaseReference referenciaPiscina = databaseReference.child(piscinaSeleccionada);
        //Toast.makeText(PiscinasSector2.this, referenciaPiscina.toString(), Toast.LENGTH_SHORT).show();
        referenciaPiscina.orderByChild("fecha").equalTo(fechaSeleccionada).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Datos encontrados para la fecha seleccionada
                    //Toast.makeText(PiscinasSector2.this, "Datos encontrados para esta piscina y fecha", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(PiscinasSector2.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Recuperar los valores y actualizar la interfaz de usuario
                        String tipoBalanceado = snapshot.child("tipobalanceado").getValue(String.class);
                        Long dieta = snapshot.child("dieta").getValue(Long.class);
                        Long sobranteTolva = snapshot.child("sobrantetolva").getValue(Long.class);
                        Long recarga = snapshot.child("recarga").getValue(Long.class);
                        Long alvoleo = snapshot.child("alvoleo").getValue(Long.class);
                        Long sobranteCaseta = snapshot.child("sobrantecaseta").getValue(Long.class);
                        Long t1 = snapshot.child("t1").getValue(Long.class);
                        Long t2 = snapshot.child("t2").getValue(Long.class);
                        Long t3 = snapshot.child("t3").getValue(Long.class);
                        Long t4 = snapshot.child("t4").getValue(Long.class);
                        Long t5 = snapshot.child("t5").getValue(Long.class);
                        Long t6 = snapshot.child("t6").getValue(Long.class);
                        Long t7 = snapshot.child("t7").getValue(Long.class);
                        Long t8 = snapshot.child("t8").getValue(Long.class);
                        Long t9 = snapshot.child("t9").getValue(Long.class);

                        Long rojo = snapshot.child("rojo").getValue(Long.class);
                        Long fresco = snapshot.child("fresco").getValue(Long.class);
                        Long reportado = snapshot.child("reportado").getValue(Long.class);
                        String observaciones = snapshot.child("observaciones").getValue(String.class);



                        // Convertir Long a String solo si los valores no son null
                        String dietaS = (dieta != null) ? dieta.toString() : "";
                        String sobranteTolvaS = (sobranteTolva != null) ? sobranteTolva.toString() : "";
                        String recargaS = (recarga != null) ? recarga.toString() : "";
                        String alvoleoS = (alvoleo != null) ? alvoleo.toString() : "";
                        String sobranteCasetaS = (sobranteCaseta != null) ? sobranteCaseta.toString() : "";
                        String tipoBalanceadoS = (tipoBalanceado != null) ? tipoBalanceado : "";
                        String t1S = (t1 != null) ? t1.toString() : "";
                        String t2S = (t2 != null) ? t2.toString() : "";
                        String t3S = (t3 != null) ? t3.toString() : "";
                        String t4S = (t4 != null) ? t4.toString() : "";
                        String t5S = (t5 != null) ? t5.toString() : "";
                        String t6S = (t6 != null) ? t6.toString() : "";
                        String t7S = (t7 != null) ? t7.toString() : "";
                        String t8S = (t8 != null) ? t8.toString() : "";
                        String t9S = (t9 != null) ? t9.toString() : "";

                        String rojoS = (rojo != null) ? rojo.toString() : "";
                        String frescoS= (fresco != null) ? fresco.toString() : "";
                        String reportadoS = (reportado != null) ? reportado.toString() : "";
                        String observacionesS = (observaciones != null) ? observaciones : "";

                        // Actualizar los campos de la interfaz de usuario
                        dietaET.setText(dietaS);
                        recargaET.setText(recargaS);
                        sobranteTolvaET.setText(sobranteTolvaS);
                        alVoleoET.setText(alvoleoS);
                        sobranteCasetaET.setText(sobranteCasetaS);
                        autoCompleteTextViewTipos.setText(tipoBalanceadoS);

                        ETt1.setText(t1S);
                        ETt2.setText(t2S);
                        ETt3.setText(t3S);
                        ETt4.setText(t4S);
                        ETt5.setText(t5S);
                        ETt6.setText(t6S);
                        ETt7.setText(t7S);
                        ETt8.setText(t8S);
                        ETt9.setText(t9S);

                        ETRojo.setText(rojoS);
                        ETFresco.setText(frescoS);
                        ETReportado.setText(reportadoS);
                        ETObservaciones.setText(observacionesS);

                        botonActualizar.setVisibility(View.VISIBLE);
                        botonGuardar.setEnabled(false); // Desactiva el botón de guardar si es necesario
                        actualizarDatosEnFirebase9T(piscinaSeleccionada,currentDate);
                    }
                } else {
                    // No se encontraron datos para la fecha seleccionada
                    //Toast.makeText(PiscinasSector8.this, "No existen datos para esta fecha", Toast.LENGTH_SHORT).show();

                    // Habilitar el botón de guardar
                    botonGuardar.setEnabled(true);
                    botonGuardar.setText("GUARDAR");
                    botonActualizar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejo de errores
                Toast.makeText(PiscinasSector8.this, "Error al recuperar los datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actualizarDatosEnFirebase9T(final String piscinaSeleccionada, final String fechaSeleccionada) {
        botonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference referenciaPiscina = databaseReference.child(piscinaSeleccionada);

                // Buscar el nodo por fecha
                referenciaPiscina.orderByChild("fecha").equalTo(fechaSeleccionada).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                // Obtén la referencia del nodo actual
                                DatabaseReference nodoReferencia = snapshot.getRef();

                                // Actualiza los valores en Firebase
                                nodoReferencia.child("dieta").setValue(parseLong(dietaET.getText().toString()));
                                nodoReferencia.child("sobrantetolva").setValue(parseLong(sobranteTolvaET.getText().toString()));
                                nodoReferencia.child("recarga").setValue(parseLong(recargaET.getText().toString()));
                                nodoReferencia.child("alvoleo").setValue(parseLong(alVoleoET.getText().toString()));
                                nodoReferencia.child("sobrantecaseta").setValue(parseLong(sobranteCasetaET.getText().toString()));
                                nodoReferencia.child("tipobalanceado").setValue(autoCompleteTextViewTipos.getText().toString());
                                nodoReferencia.child("t1").setValue(parseLong(ETt1.getText().toString()));
                                nodoReferencia.child("t2").setValue(parseLong(ETt2.getText().toString()));
                                nodoReferencia.child("t3").setValue(parseLong(ETt3.getText().toString()));
                                nodoReferencia.child("t4").setValue(parseLong(ETt4.getText().toString()));
                                nodoReferencia.child("t5").setValue(parseLong(ETt5.getText().toString()));
                                nodoReferencia.child("t6").setValue(parseLong(ETt6.getText().toString()));
                                nodoReferencia.child("t7").setValue(parseLong(ETt7.getText().toString()));
                                nodoReferencia.child("t8").setValue(parseLong(ETt8.getText().toString()));
                                nodoReferencia.child("t9").setValue(parseLong(ETt9.getText().toString()));

                                nodoReferencia.child("rojo").setValue(parseLong(ETRojo.getText().toString()));
                                nodoReferencia.child("fresco").setValue(parseLong(ETFresco.getText().toString()));
                                nodoReferencia.child("reportado").setValue(parseLong(ETReportado.getText().toString()));
                                nodoReferencia.child("observaciones").setValue(ETObservaciones.getText().toString());

                                Toast.makeText(PiscinasSector8.this, "DATOS ACTUALIZADOS", Toast.LENGTH_SHORT).show();
                                inicializarListas();
                                inicializarCamposVariables();
                                limpiarCamposPiscina1();

                            }
                        } else {
                            Toast.makeText(PiscinasSector8.this, "No se encontraron datos para actualizar", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(PiscinasSector8.this, "Error al actualizar datos en Firebase: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void verificarDatosEnFirebase10T(final String piscinaSeleccionada, final String fechaSeleccionada) {
        // Limpiar los campos de la interfaz de usuario antes de la nueva consulta
        limpiarCamposPiscina1();

        DatabaseReference referenciaPiscina = databaseReference.child(piscinaSeleccionada);
        //Toast.makeText(PiscinasSector8.this, referenciaPiscina.toString(), Toast.LENGTH_SHORT).show();
        referenciaPiscina.orderByChild("fecha").equalTo(fechaSeleccionada).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Datos encontrados para la fecha seleccionada
                    //Toast.makeText(PiscinasSector8.this, "Datos encontrados para esta piscina y fecha", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(PiscinasSector8.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Recuperar los valores y actualizar la interfaz de usuario
                        String tipoBalanceado = snapshot.child("tipobalanceado").getValue(String.class);
                        Long dieta = snapshot.child("dieta").getValue(Long.class);
                        Long sobranteTolva = snapshot.child("sobrantetolva").getValue(Long.class);
                        Long recarga = snapshot.child("recarga").getValue(Long.class);
                        Long alvoleo = snapshot.child("alvoleo").getValue(Long.class);
                        Long sobranteCaseta = snapshot.child("sobrantecaseta").getValue(Long.class);
                        Long t1 = snapshot.child("t1").getValue(Long.class);
                        Long t2 = snapshot.child("t2").getValue(Long.class);
                        Long t3 = snapshot.child("t3").getValue(Long.class);
                        Long t4 = snapshot.child("t4").getValue(Long.class);
                        Long t5 = snapshot.child("t5").getValue(Long.class);
                        Long t6 = snapshot.child("t6").getValue(Long.class);
                        Long t7 = snapshot.child("t7").getValue(Long.class);
                        Long t8 = snapshot.child("t8").getValue(Long.class);
                        Long t9 = snapshot.child("t9").getValue(Long.class);
                        Long t10 = snapshot.child("t10").getValue(Long.class);

                        Long rojo = snapshot.child("rojo").getValue(Long.class);
                        Long fresco = snapshot.child("fresco").getValue(Long.class);
                        Long reportado = snapshot.child("reportado").getValue(Long.class);
                        String observaciones = snapshot.child("observaciones").getValue(String.class);

                        // Convertir Long a String solo si los valores no son null
                        String dietaS = (dieta != null) ? dieta.toString() : "";
                        String sobranteTolvaS = (sobranteTolva != null) ? sobranteTolva.toString() : "";
                        String recargaS = (recarga != null) ? recarga.toString() : "";
                        String alvoleoS = (alvoleo != null) ? alvoleo.toString() : "";
                        String sobranteCasetaS = (sobranteCaseta != null) ? sobranteCaseta.toString() : "";
                        String tipoBalanceadoS = (tipoBalanceado != null) ? tipoBalanceado : "";
                        String t1S = (t1 != null) ? t1.toString() : "";
                        String t2S = (t2 != null) ? t2.toString() : "";
                        String t3S = (t3 != null) ? t3.toString() : "";
                        String t4S = (t4 != null) ? t4.toString() : "";
                        String t5S = (t5 != null) ? t5.toString() : "";
                        String t6S = (t6 != null) ? t6.toString() : "";
                        String t7S = (t7 != null) ? t7.toString() : "";
                        String t8S = (t8 != null) ? t8.toString() : "";
                        String t9S = (t9 != null) ? t9.toString() : "";
                        String t10S = (t10 != null) ? t10.toString() : "";

                        String rojoS = (rojo != null) ? rojo.toString() : "";
                        String frescoS= (fresco != null) ? fresco.toString() : "";
                        String reportadoS = (reportado != null) ? reportado.toString() : "";
                        String observacionesS = (observaciones != null) ? observaciones : "";

                        // Actualizar los campos de la interfaz de usuario
                        dietaET.setText(dietaS);
                        recargaET.setText(recargaS);
                        sobranteTolvaET.setText(sobranteTolvaS);
                        alVoleoET.setText(alvoleoS);
                        sobranteCasetaET.setText(sobranteCasetaS);
                        autoCompleteTextViewTipos.setText(tipoBalanceadoS);

                        ETt1.setText(t1S);
                        ETt2.setText(t2S);
                        ETt3.setText(t3S);
                        ETt4.setText(t4S);
                        ETt5.setText(t5S);
                        ETt6.setText(t6S);
                        ETt7.setText(t7S);
                        ETt8.setText(t8S);
                        ETt9.setText(t9S);
                        ETt10.setText(t10S);

                        ETRojo.setText(rojoS);
                        ETFresco.setText(frescoS);
                        ETReportado.setText(reportadoS);
                        ETObservaciones.setText(observacionesS);

                        botonActualizar.setVisibility(View.VISIBLE);
                        botonGuardar.setEnabled(false); // Desactiva el botón de guardar si es necesario
                        actualizarDatosEnFirebase10T(piscinaSeleccionada,currentDate);
                    }
                } else {

                    botonGuardar.setEnabled(true);
                    botonGuardar.setText("GUARDAR");
                    botonActualizar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejo de errores
                Toast.makeText(PiscinasSector8.this, "Error al recuperar los datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actualizarDatosEnFirebase10T(final String piscinaSeleccionada, final String fechaSeleccionada) {
        botonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference referenciaPiscina = databaseReference.child(piscinaSeleccionada);

                // Buscar el nodo por fecha
                referenciaPiscina.orderByChild("fecha").equalTo(fechaSeleccionada).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                // Obtén la referencia del nodo actual
                                DatabaseReference nodoReferencia = snapshot.getRef();

                                // Actualiza los valores en Firebase
                                nodoReferencia.child("dieta").setValue(parseLong(dietaET.getText().toString()));
                                nodoReferencia.child("sobrantetolva").setValue(parseLong(sobranteTolvaET.getText().toString()));
                                nodoReferencia.child("recarga").setValue(parseLong(recargaET.getText().toString()));
                                nodoReferencia.child("alvoleo").setValue(parseLong(alVoleoET.getText().toString()));
                                nodoReferencia.child("sobrantecaseta").setValue(parseLong(sobranteCasetaET.getText().toString()));
                                nodoReferencia.child("tipobalanceado").setValue(autoCompleteTextViewTipos.getText().toString());
                                nodoReferencia.child("t1").setValue(parseLong(ETt1.getText().toString()));
                                nodoReferencia.child("t2").setValue(parseLong(ETt2.getText().toString()));
                                nodoReferencia.child("t3").setValue(parseLong(ETt3.getText().toString()));
                                nodoReferencia.child("t4").setValue(parseLong(ETt4.getText().toString()));
                                nodoReferencia.child("t5").setValue(parseLong(ETt5.getText().toString()));
                                nodoReferencia.child("t6").setValue(parseLong(ETt6.getText().toString()));
                                nodoReferencia.child("t7").setValue(parseLong(ETt7.getText().toString()));
                                nodoReferencia.child("t8").setValue(parseLong(ETt8.getText().toString()));
                                nodoReferencia.child("t9").setValue(parseLong(ETt8.getText().toString()));
                                nodoReferencia.child("t10").setValue(parseLong(ETt10.getText().toString()));

                                nodoReferencia.child("rojo").setValue(parseLong(ETRojo.getText().toString()));
                                nodoReferencia.child("fresco").setValue(parseLong(ETFresco.getText().toString()));
                                nodoReferencia.child("reportado").setValue(parseLong(ETReportado.getText().toString()));
                                nodoReferencia.child("observaciones").setValue(ETObservaciones.getText().toString());

                                Toast.makeText(PiscinasSector8.this, "DATOS ACTUALIZADOS", Toast.LENGTH_SHORT).show();
                                inicializarListas();
                                inicializarCamposVariables();
                                limpiarCamposPiscina1();

                            }
                        } else {
                            Toast.makeText(PiscinasSector8.this, "No se encontraron datos para actualizar", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(PiscinasSector8.this, "Error al actualizar datos en Firebase: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void inicializarCamposVariables() {

        dateTextView = findViewById(R.id.tvFecha);
        dietaTV = findViewById(R.id.tvDieta);
        sobranteTolvaTV=findViewById(R.id.tvSobranteTolva);
        recargaTV=findViewById(R.id.tvRecarga);
        alVoleoTV=findViewById(R.id.tvAlVoleo);
        sobranteCasetaTV=findViewById(R.id.tvSobranteCaseta);
        piscinaTV=findViewById(R.id.tvPiscinas);


        dietaET=(TextInputEditText) dietaTV.getEditText();
        sobranteTolvaET=(TextInputEditText) sobranteTolvaTV.getEditText();
        recargaET=(TextInputEditText) recargaTV.getEditText();
        alVoleoET=(TextInputEditText) alVoleoTV.getEditText();
        sobranteCasetaET=(TextInputEditText) sobranteCasetaTV.getEditText();

        botonGuardar = findViewById(R.id.btnGuardar);
        botonInicio = findViewById(R.id.btnInicio);
        botonActualizar = findViewById(R.id.btnActualizar);
        botonActualizar.setVisibility(View.INVISIBLE);


        TVz1=findViewById(R.id.tvzona1);
        TVz2=findViewById(R.id.tvzona2);

        TVt1=findViewById(R.id.tvT1);
        TVt2=findViewById(R.id.tvT2);
        TVt3=findViewById(R.id.tvT3);
        TVt4=findViewById(R.id.tvT4);
        TVt5=findViewById(R.id.tvT5);
        TVt6=findViewById(R.id.tvT6);
        TVt7=findViewById(R.id.tvT7);
        TVt8=findViewById(R.id.tvT8);
        TVt9=findViewById(R.id.tvT9);
        TVt10=findViewById(R.id.tvT10);

        ETt1=findViewById(R.id.etT1);
        ETt2=findViewById(R.id.etT2);
        ETt3=findViewById(R.id.etT3);
        ETt4=findViewById(R.id.etT4);
        ETt5=findViewById(R.id.etT5);
        ETt6=findViewById(R.id.etT6);
        ETt7=findViewById(R.id.etT7);
        ETt8=findViewById(R.id.etT8);
        ETt9=findViewById(R.id.etT9);
        ETt10=findViewById(R.id.etT10);

        ETRojo=findViewById(R.id.etRojo);
        ETFresco=findViewById(R.id.etFresco);
        ETReportado=findViewById(R.id.etReportado);
        ETObservaciones=findViewById(R.id.etObservaciones);


        TVt1.setVisibility(View.INVISIBLE);
        TVt2.setVisibility(View.INVISIBLE);
        TVt3.setVisibility(View.INVISIBLE);
        TVt4.setVisibility(View.INVISIBLE);
        TVt5.setVisibility(View.INVISIBLE);
        TVt6.setVisibility(View.INVISIBLE);
        TVt7.setVisibility(View.INVISIBLE);
        TVt8.setVisibility(View.INVISIBLE);
        TVt9.setVisibility(View.INVISIBLE);
        TVt10.setVisibility(View.INVISIBLE);

    }


    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }
    private boolean isEditTextEmpty(EditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }



    private void limpiarCamposPiscina1() {
        dietaET.setText("");
        recargaET.setText("");
        sobranteTolvaET.setText("");
        alVoleoET.setText("");
        sobranteCasetaET.setText("");
        autoCompleteTextViewTipos.setText("");
        ETt1.setText("");
        ETt2.setText("");
        ETt3.setText("");
        ETt4.setText("");
        ETt5.setText("");
        ETt6.setText("");
        ETt7.setText("");
        ETt8.setText("");
        ETt9.setText("");
        ETt10.setText("");
        ETRojo.setText("");
        ETFresco.setText("");
        ETReportado.setText("");
        ETObservaciones.setText("");
        botonActualizar.setVisibility(View.INVISIBLE);

    }
    private int getIntValue(EditText editText) {
        String text = editText.getText().toString();
        return text.isEmpty() ? 0 : Integer.parseInt(text);
    }

    // Método para obtener un valor de texto, o una cadena vacía si es null
    private String getStringValue(EditText editText) {
        String text = editText.getText().toString();
        return text.isEmpty() ? "" : text;
    }
    @Override
    public void onBackPressed() {
        // Aquí puedes agregar cualquier lógica que necesites antes de cerrar la actividad
        // Por ejemplo, mostrar un mensaje o realizar limpieza
        super.onBackPressed(); // Esto cerrará la actividad actual
    }


}