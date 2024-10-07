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

public class PiscinasSector3 extends AppCompatActivity {

    private TextInputLayout dietaTV,sobranteTolvaTV,recargaTV,alVoleoTV,sobranteCasetaTV,piscinaTV,tiposBalanceadoTV;
    private TextInputEditText dietaET,sobranteTolvaET,sobranteCasetaET,recargaET,alVoleoET;
    private TextInputLayout TVt1,TVt2,TVt3,TVt4,TVt5,TVt6,TVt7,TVt8,TVt9,TVt10;
    private TextInputEditText ETt1,ETt2,ETt3,ETt4,ETt5,ETt6,ETt7,ETt8,ETt9,ETt10,ETRojo, ETFresco, ETReportado, ETObservaciones;
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


        insertarDatosPiscinasSector3();
        volverInicio();


    }
    private void actualizarDatosDesdeFirebase() {
        // Lista de referencias a las piscinas
        List<String> piscinas = Arrays.asList("Piscina 16", "Piscina 17", "Piscina 18", "Piscina 19", "Piscina 20", "Piscina 21");

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

                                Toast.makeText(PiscinasSector3.this, "DATOS ACTUALIZADOS", Toast.LENGTH_SHORT).show();
                                inicializarListas();
                                inicializarCamposVariables();
                                limpiarCamposPiscina1();

                            }
                        } else {
                            Toast.makeText(PiscinasSector3.this, "No se encontraron datos para actualizar", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(PiscinasSector3.this, "Error al actualizar datos en Firebase: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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

                                nodoReferencia.child("rojo").setValue(parseLong(ETRojo.getText().toString()));
                                nodoReferencia.child("fresco").setValue(parseLong(ETFresco.getText().toString()));
                                nodoReferencia.child("reportado").setValue(parseLong(ETReportado.getText().toString()));
                                nodoReferencia.child("observaciones").setValue(ETObservaciones.getText().toString());

                                Toast.makeText(PiscinasSector3.this, "DATOS ACTUALIZADOS", Toast.LENGTH_SHORT).show();
                                inicializarListas();
                                inicializarCamposVariables();
                                limpiarCamposPiscina1();

                            }
                        } else {
                            Toast.makeText(PiscinasSector3.this, "No se encontraron datos para actualizar", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(PiscinasSector3.this, "Error al actualizar datos en Firebase: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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

                                Toast.makeText(PiscinasSector3.this, "DATOS ACTUALIZADOS", Toast.LENGTH_SHORT).show();
                                inicializarListas();
                                inicializarCamposVariables();
                                limpiarCamposPiscina1();

                            }
                        } else {
                            Toast.makeText(PiscinasSector3.this, "No se encontraron datos para actualizar", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(PiscinasSector3.this, "Error al actualizar datos en Firebase: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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

                                Toast.makeText(PiscinasSector3.this, "DATOS ACTUALIZADOS", Toast.LENGTH_SHORT).show();
                                inicializarListas();
                                inicializarCamposVariables();
                                limpiarCamposPiscina1();

                            }
                        } else {
                            Toast.makeText(PiscinasSector3.this, "No se encontraron datos para actualizar", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(PiscinasSector3.this, "Error al actualizar datos en Firebase: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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
                                nodoReferencia.child("t9").setValue(parseLong(ETt9.getText().toString()));
                                nodoReferencia.child("t10").setValue(parseLong(ETt10.getText().toString()));

                                nodoReferencia.child("rojo").setValue(parseLong(ETRojo.getText().toString()));
                                nodoReferencia.child("fresco").setValue(parseLong(ETFresco.getText().toString()));
                                nodoReferencia.child("reportado").setValue(parseLong(ETReportado.getText().toString()));
                                nodoReferencia.child("observaciones").setValue(ETObservaciones.getText().toString());

                                Toast.makeText(PiscinasSector3.this, "DATOS ACTUALIZADOS", Toast.LENGTH_SHORT).show();
                                inicializarListas();
                                inicializarCamposVariables();
                                limpiarCamposPiscina1();

                            }
                        } else {
                            Toast.makeText(PiscinasSector3.this, "No se encontraron datos para actualizar", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(PiscinasSector3.this, "Error al actualizar datos en Firebase: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
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
                Intent intent = new Intent(PiscinasSector3.this, INICIO.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void insertarDatosPiscinasSector3() {
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(autoCompleteTextViewPiscinas.getText().toString().isEmpty()){
                    piscinaTV.setError("Por favor seleccione una piscina");
                }else{
                    piscinaTV.setError(null);
                    String piscinaSeleccionada = autoCompleteTextViewPiscinas.getText().toString();
                    if(piscinaSeleccionada.equals("Piscina 16")){
                        String tipoBalanceadoSeleccionado=autoCompleteTextViewTipos.getText().toString();
                        if (isEditTextEmpty(dietaET)||isEditTextEmpty(sobranteTolvaET)||isEditTextEmpty(alVoleoET)||isEditTextEmpty(sobranteCasetaET)
                                ||isEditTextEmpty(recargaET)||tipoBalanceadoSeleccionado.equals("")||isEditTextEmpty(ETt1)||isEditTextEmpty(ETt2)||isEditTextEmpty(ETt3)
                                ||isEditTextEmpty(ETt4)||isEditTextEmpty(ETt6)||isEditTextEmpty(ETt7)||isEditTextEmpty(ETt8)){
                            Toast.makeText(PiscinasSector3.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Piscinas p16= new Piscinas();
                        p16.setUid(UUID.randomUUID().toString());
                        p16.setFecha(currentDate);
                        p16.setDieta(Integer.parseInt(Objects.requireNonNull(dietaET.getText()).toString()));
                        p16.setSobrantetolva(Integer.parseInt(Objects.requireNonNull(sobranteTolvaET.getText()).toString()));
                        p16.setRecarga(Integer.parseInt(Objects.requireNonNull(recargaET.getText()).toString()));
                        p16.setAlvoleo(Integer.parseInt(Objects.requireNonNull(alVoleoET.getText()).toString()));
                        p16.setSobrantecaseta(Integer.parseInt(Objects.requireNonNull(sobranteCasetaET.getText()).toString()));
                        p16.setTipobalanceado(tipoBalanceadoSeleccionado);
                        p16.setT1(Integer.parseInt(Objects.requireNonNull(ETt1.getText()).toString()));
                        p16.setT2(Integer.parseInt(Objects.requireNonNull(ETt2.getText()).toString()));
                        p16.setT3(Integer.parseInt(Objects.requireNonNull(ETt3.getText()).toString()));
                        p16.setT4(Integer.parseInt(Objects.requireNonNull(ETt4.getText()).toString()));
                        p16.setT5(Integer.parseInt(Objects.requireNonNull(ETt6.getText()).toString()));
                        p16.setT6(Integer.parseInt(Objects.requireNonNull(ETt7.getText()).toString()));
                        p16.setT7(Integer.parseInt(Objects.requireNonNull(ETt8.getText()).toString()));
                        p16.setRojo(getIntValue(ETRojo));
                        p16.setFresco(getIntValue(ETFresco));
                        p16.setReportado(getIntValue(ETReportado));
                        p16.setObservaciones(getStringValue(ETObservaciones));
                        databaseReference.child("Piscina 16").child(p16.getUid()).setValue(p16);
                        //limparcampospiscina1();
                        verificarDatosEnFirebase7T("Piscina 16",currentDate);
                        Toast.makeText(PiscinasSector3.this, "DATOS INGRESADOS", Toast.LENGTH_SHORT).show();

                    } else if (piscinaSeleccionada.equals("Piscina 17")) {
                        String tipoBalanceadoSeleccionado=autoCompleteTextViewTipos.getText().toString();
                        if (isEditTextEmpty(dietaET)||isEditTextEmpty(sobranteTolvaET)||isEditTextEmpty(alVoleoET)||isEditTextEmpty(sobranteCasetaET)
                                ||isEditTextEmpty(recargaET)||tipoBalanceadoSeleccionado.equals("")||isEditTextEmpty(ETt1)||isEditTextEmpty(ETt2)||isEditTextEmpty(ETt3)
                                ||isEditTextEmpty(ETt6)||isEditTextEmpty(ETt7)||isEditTextEmpty(ETt8)){
                            Toast.makeText(PiscinasSector3.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Piscinas p17= new Piscinas();
                        p17.setUid(UUID.randomUUID().toString());
                        p17.setFecha(currentDate);
                        p17.setDieta(Integer.parseInt(Objects.requireNonNull(dietaET.getText()).toString()));
                        p17.setSobrantetolva(Integer.parseInt(Objects.requireNonNull(sobranteTolvaET.getText()).toString()));
                        p17.setRecarga(Integer.parseInt(Objects.requireNonNull(recargaET.getText()).toString()));
                        p17.setAlvoleo(Integer.parseInt(Objects.requireNonNull(alVoleoET.getText()).toString()));
                        p17.setSobrantecaseta(Integer.parseInt(Objects.requireNonNull(sobranteCasetaET.getText()).toString()));
                        p17.setTipobalanceado(tipoBalanceadoSeleccionado);
                        p17.setT1(Integer.parseInt(Objects.requireNonNull(ETt1.getText()).toString()));
                        p17.setT2(Integer.parseInt(Objects.requireNonNull(ETt2.getText()).toString()));
                        p17.setT3(Integer.parseInt(Objects.requireNonNull(ETt3.getText()).toString()));
                        p17.setT4(Integer.parseInt(Objects.requireNonNull(ETt6.getText()).toString()));
                        p17.setT5(Integer.parseInt(Objects.requireNonNull(ETt7.getText()).toString()));
                        p17.setT6(Integer.parseInt(Objects.requireNonNull(ETt8.getText()).toString()));
                        p17.setRojo(getIntValue(ETRojo));
                        p17.setFresco(getIntValue(ETFresco));
                        p17.setReportado(getIntValue(ETReportado));
                        p17.setObservaciones(getStringValue(ETObservaciones));
                        databaseReference.child("Piscina 17").child(p17.getUid()).setValue(p17);
                        //limparcampospiscina1();
                        verificarDatosEnFirebase6T("Piscina 17",currentDate);
                        Toast.makeText(PiscinasSector3.this, "DATOS INGRESADOS", Toast.LENGTH_SHORT).show();

                    } else if (piscinaSeleccionada.equals("Piscina 18")) {
                        String tipoBalanceadoSeleccionado=autoCompleteTextViewTipos.getText().toString();
                        if (isEditTextEmpty(dietaET)||isEditTextEmpty(sobranteTolvaET)||isEditTextEmpty(alVoleoET)||isEditTextEmpty(sobranteCasetaET)
                                ||isEditTextEmpty(recargaET)||tipoBalanceadoSeleccionado.equals("")||isEditTextEmpty(ETt1)||isEditTextEmpty(ETt2)||isEditTextEmpty(ETt3)
                                ||isEditTextEmpty(ETt4)||isEditTextEmpty(ETt5)||isEditTextEmpty(ETt6)||isEditTextEmpty(ETt7)||isEditTextEmpty(ETt8)||isEditTextEmpty(ETt9)||isEditTextEmpty(ETt10)){
                            Toast.makeText(PiscinasSector3.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Piscinas p18= new Piscinas();
                        p18.setUid(UUID.randomUUID().toString());
                        p18.setFecha(currentDate);
                        p18.setDieta(Integer.parseInt(Objects.requireNonNull(dietaET.getText()).toString()));
                        p18.setSobrantetolva(Integer.parseInt(Objects.requireNonNull(sobranteTolvaET.getText()).toString()));
                        p18.setRecarga(Integer.parseInt(Objects.requireNonNull(recargaET.getText()).toString()));
                        p18.setAlvoleo(Integer.parseInt(Objects.requireNonNull(alVoleoET.getText()).toString()));
                        p18.setSobrantecaseta(Integer.parseInt(Objects.requireNonNull(sobranteCasetaET.getText()).toString()));
                        p18.setTipobalanceado(tipoBalanceadoSeleccionado);
                        p18.setT1(Integer.parseInt(Objects.requireNonNull(ETt1.getText()).toString()));
                        p18.setT2(Integer.parseInt(Objects.requireNonNull(ETt2.getText()).toString()));
                        p18.setT3(Integer.parseInt(Objects.requireNonNull(ETt3.getText()).toString()));
                        p18.setT4(Integer.parseInt(Objects.requireNonNull(ETt4.getText()).toString()));
                        p18.setT5(Integer.parseInt(Objects.requireNonNull(ETt5.getText()).toString()));
                        p18.setT6(Integer.parseInt(Objects.requireNonNull(ETt6.getText()).toString()));
                        p18.setT7(Integer.parseInt(Objects.requireNonNull(ETt7.getText()).toString()));
                        p18.setT8(Integer.parseInt(Objects.requireNonNull(ETt8.getText()).toString()));
                        p18.setT9(Integer.parseInt(Objects.requireNonNull(ETt9.getText()).toString()));
                        p18.setT10(Integer.parseInt(Objects.requireNonNull(ETt10.getText()).toString()));
                        p18.setRojo(getIntValue(ETRojo));
                        p18.setFresco(getIntValue(ETFresco));
                        p18.setReportado(getIntValue(ETReportado));
                        p18.setObservaciones(getStringValue(ETObservaciones));
                        databaseReference.child("Piscina 18").child(p18.getUid()).setValue(p18);

                        //limparcampospiscina1();
                        verificarDatosEnFirebase10T("Piscina 18",currentDate);
                        Toast.makeText(PiscinasSector3.this, "DATOS INGRESADOS", Toast.LENGTH_SHORT).show();
                    } else if (piscinaSeleccionada.equals("Piscina 19")) {
                        String tipoBalanceadoSeleccionado=autoCompleteTextViewTipos.getText().toString();
                        if (isEditTextEmpty(dietaET)||isEditTextEmpty(sobranteTolvaET)||isEditTextEmpty(alVoleoET)||isEditTextEmpty(sobranteCasetaET)
                                ||isEditTextEmpty(recargaET)||tipoBalanceadoSeleccionado.equals("")||isEditTextEmpty(ETt1)||isEditTextEmpty(ETt2)||isEditTextEmpty(ETt3)
                                ||isEditTextEmpty(ETt4)||isEditTextEmpty(ETt6)||isEditTextEmpty(ETt7)||isEditTextEmpty(ETt8)||isEditTextEmpty(ETt9)){
                            Toast.makeText(PiscinasSector3.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Piscinas p19= new Piscinas();
                        p19.setUid(UUID.randomUUID().toString());
                        p19.setFecha(currentDate);
                        p19.setDieta(Integer.parseInt(Objects.requireNonNull(dietaET.getText()).toString()));
                        p19.setSobrantetolva(Integer.parseInt(Objects.requireNonNull(sobranteTolvaET.getText()).toString()));
                        p19.setRecarga(Integer.parseInt(Objects.requireNonNull(recargaET.getText()).toString()));
                        p19.setAlvoleo(Integer.parseInt(Objects.requireNonNull(alVoleoET.getText()).toString()));
                        p19.setSobrantecaseta(Integer.parseInt(Objects.requireNonNull(sobranteCasetaET.getText()).toString()));
                        p19.setTipobalanceado(tipoBalanceadoSeleccionado);
                        p19.setT1(Integer.parseInt(Objects.requireNonNull(ETt1.getText()).toString()));
                        p19.setT2(Integer.parseInt(Objects.requireNonNull(ETt2.getText()).toString()));
                        p19.setT3(Integer.parseInt(Objects.requireNonNull(ETt3.getText()).toString()));
                        p19.setT4(Integer.parseInt(Objects.requireNonNull(ETt4.getText()).toString()));
                        p19.setT5(Integer.parseInt(Objects.requireNonNull(ETt6.getText()).toString()));
                        p19.setT6(Integer.parseInt(Objects.requireNonNull(ETt7.getText()).toString()));
                        p19.setT7(Integer.parseInt(Objects.requireNonNull(ETt8.getText()).toString()));
                        p19.setT8(Integer.parseInt(Objects.requireNonNull(ETt9.getText()).toString()));
                        p19.setRojo(getIntValue(ETRojo));
                        p19.setFresco(getIntValue(ETFresco));
                        p19.setReportado(getIntValue(ETReportado));
                        p19.setObservaciones(getStringValue(ETObservaciones));
                        databaseReference.child("Piscina 19").child(p19.getUid()).setValue(p19);
                        //limparcampospiscina1();
                        verificarDatosEnFirebase8T("Piscina 19",currentDate);
                        Toast.makeText(PiscinasSector3.this, "DATOS INGRESADOS", Toast.LENGTH_SHORT).show();
                    } else if (piscinaSeleccionada.equals("Piscina 20")) {
                        String tipoBalanceadoSeleccionado=autoCompleteTextViewTipos.getText().toString();
                        if (isEditTextEmpty(dietaET)||isEditTextEmpty(sobranteTolvaET)||isEditTextEmpty(alVoleoET)||isEditTextEmpty(sobranteCasetaET)
                                ||isEditTextEmpty(recargaET)||tipoBalanceadoSeleccionado.equals("")||isEditTextEmpty(ETt1)||isEditTextEmpty(ETt2)||isEditTextEmpty(ETt3)
                                ||isEditTextEmpty(ETt4)||isEditTextEmpty(ETt5)||isEditTextEmpty(ETt6)||isEditTextEmpty(ETt7)||isEditTextEmpty(ETt8)||isEditTextEmpty(ETt9)||isEditTextEmpty(ETt10)){
                            Toast.makeText(PiscinasSector3.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Piscinas p20= new Piscinas();
                        p20.setUid(UUID.randomUUID().toString());
                        p20.setFecha(currentDate);
                        p20.setDieta(Integer.parseInt(Objects.requireNonNull(dietaET.getText()).toString()));
                        p20.setSobrantetolva(Integer.parseInt(Objects.requireNonNull(sobranteTolvaET.getText()).toString()));
                        p20.setRecarga(Integer.parseInt(Objects.requireNonNull(recargaET.getText()).toString()));
                        p20.setAlvoleo(Integer.parseInt(Objects.requireNonNull(alVoleoET.getText()).toString()));
                        p20.setSobrantecaseta(Integer.parseInt(Objects.requireNonNull(sobranteCasetaET.getText()).toString()));
                        p20.setTipobalanceado(tipoBalanceadoSeleccionado);
                        p20.setT1(Integer.parseInt(Objects.requireNonNull(ETt1.getText()).toString()));
                        p20.setT2(Integer.parseInt(Objects.requireNonNull(ETt2.getText()).toString()));
                        p20.setT3(Integer.parseInt(Objects.requireNonNull(ETt3.getText()).toString()));
                        p20.setT4(Integer.parseInt(Objects.requireNonNull(ETt4.getText()).toString()));
                        p20.setT5(Integer.parseInt(Objects.requireNonNull(ETt5.getText()).toString()));
                        p20.setT6(Integer.parseInt(Objects.requireNonNull(ETt6.getText()).toString()));
                        p20.setT7(Integer.parseInt(Objects.requireNonNull(ETt7.getText()).toString()));
                        p20.setT8(Integer.parseInt(Objects.requireNonNull(ETt8.getText()).toString()));
                        p20.setT9(Integer.parseInt(Objects.requireNonNull(ETt9.getText()).toString()));
                        p20.setT10(Integer.parseInt(Objects.requireNonNull(ETt10.getText()).toString()));
                        p20.setRojo(getIntValue(ETRojo));
                        p20.setFresco(getIntValue(ETFresco));
                        p20.setReportado(getIntValue(ETReportado));
                        p20.setObservaciones(getStringValue(ETObservaciones));
                        databaseReference.child("Piscina 20").child(p20.getUid()).setValue(p20);
                        //limparcampospiscina1();
                        verificarDatosEnFirebase10T("Piscina 20",currentDate);
                        Toast.makeText(PiscinasSector3.this, "DATOS INGRESADOS", Toast.LENGTH_SHORT).show();
                    } else if (piscinaSeleccionada.equals("Piscina 21")) {
                        String tipoBalanceadoSeleccionado=autoCompleteTextViewTipos.getText().toString();
                        if (isEditTextEmpty(dietaET)||isEditTextEmpty(sobranteTolvaET)||isEditTextEmpty(alVoleoET)||isEditTextEmpty(sobranteCasetaET)
                                ||isEditTextEmpty(recargaET)||tipoBalanceadoSeleccionado.equals("")||isEditTextEmpty(ETt1)||isEditTextEmpty(ETt2)||isEditTextEmpty(ETt3)
                                ||isEditTextEmpty(ETt4)||isEditTextEmpty(ETt5)||isEditTextEmpty(ETt6)||isEditTextEmpty(ETt7)||isEditTextEmpty(ETt8)||isEditTextEmpty(ETt9)||isEditTextEmpty(ETt10)){
                            Toast.makeText(PiscinasSector3.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Piscinas p21= new Piscinas();
                        p21.setUid(UUID.randomUUID().toString());
                        p21.setFecha(currentDate);
                        p21.setDieta(Integer.parseInt(Objects.requireNonNull(dietaET.getText()).toString()));
                        p21.setSobrantetolva(Integer.parseInt(Objects.requireNonNull(sobranteTolvaET.getText()).toString()));
                        p21.setRecarga(Integer.parseInt(Objects.requireNonNull(recargaET.getText()).toString()));
                        p21.setAlvoleo(Integer.parseInt(Objects.requireNonNull(alVoleoET.getText()).toString()));
                        p21.setSobrantecaseta(Integer.parseInt(Objects.requireNonNull(sobranteCasetaET.getText()).toString()));
                        p21.setTipobalanceado(tipoBalanceadoSeleccionado);
                        p21.setT1(Integer.parseInt(Objects.requireNonNull(ETt1.getText()).toString()));
                        p21.setT2(Integer.parseInt(Objects.requireNonNull(ETt2.getText()).toString()));
                        p21.setT3(Integer.parseInt(Objects.requireNonNull(ETt3.getText()).toString()));
                        p21.setT4(Integer.parseInt(Objects.requireNonNull(ETt4.getText()).toString()));
                        p21.setT5(Integer.parseInt(Objects.requireNonNull(ETt5.getText()).toString()));
                        p21.setT6(Integer.parseInt(Objects.requireNonNull(ETt6.getText()).toString()));
                        p21.setT7(Integer.parseInt(Objects.requireNonNull(ETt7.getText()).toString()));
                        p21.setT8(Integer.parseInt(Objects.requireNonNull(ETt8.getText()).toString()));
                        p21.setT9(Integer.parseInt(Objects.requireNonNull(ETt9.getText()).toString()));
                        p21.setT10(Integer.parseInt(Objects.requireNonNull(ETt10.getText()).toString()));
                        p21.setRojo(getIntValue(ETRojo));
                        p21.setFresco(getIntValue(ETFresco));
                        p21.setReportado(getIntValue(ETReportado));
                        p21.setObservaciones(getStringValue(ETObservaciones));
                        databaseReference.child("Piscina 21").child(p21.getUid()).setValue(p21);

                        //limparcampospiscina1();
                        verificarDatosEnFirebase10T("Piscina 21",currentDate);
                        Toast.makeText(PiscinasSector3.this, "DATOS INGRESADOS", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }


    private void inicializarListas() {


        piscinaTV = findViewById(R.id.tvPiscinas);
        autoCompleteTextViewPiscinas =findViewById(R.id.listaPiscinas);
        String[] opcionesPiscinas = getResources().getStringArray(R.array.options_listZ3);
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
                if(piscinaSeleccionada.equals("Piscina 16")){
                    limpiarCamposPiscina1();
                    TVz1.setText("ZONA 2");
                    TVz2.setText("ZONA 1");
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
                    TVt9.setVisibility(View.INVISIBLE);
                    TVt9.setEnabled(false);
                    TVt10.setVisibility(View.INVISIBLE);
                    TVt9.setEnabled(false);
                    botonGuardar.setEnabled(true);
                    verificarDatosEnFirebase7T("Piscina 16",fechaSeleccionada);
                } else if (piscinaSeleccionada.equals("Piscina 17")) {
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
                    TVt4.setVisibility(View.INVISIBLE);
                    TVt4.setEnabled(false);
                    TVt5.setVisibility(View.INVISIBLE);
                    TVt4.setEnabled(false);
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
                    verificarDatosEnFirebase6T("Piscina 17",fechaSeleccionada);

                } else if (piscinaSeleccionada.equals("Piscina 18")) {
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
                    piscinaTV.setError(null);
                    botonGuardar.setEnabled(false);
                    //Toast.makeText(PiscinasSector3.this, "PISCINA PESCADA", Toast.LENGTH_SHORT).show();
                    verificarDatosEnFirebase10T("Piscina 18",fechaSeleccionada);
                } else if (piscinaSeleccionada.equals("Piscina 19")) {
                    limpiarCamposPiscina1();
                    TVz1.setText("ZONA 2");
                    TVz2.setText("ZONA 1");
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
                    verificarDatosEnFirebase8T("Piscina 19",fechaSeleccionada);
                } else if (piscinaSeleccionada.equals("Piscina 20")) {
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
                    piscinaTV.setError(null);
                    botonGuardar.setEnabled(false);
                    //Toast.makeText(PiscinasSector1.this, "PISCINA PESCADA", Toast.LENGTH_SHORT).show();
                    verificarDatosEnFirebase10T("Piscina 20",fechaSeleccionada);
                } else if (piscinaSeleccionada.equals("Piscina 21")) {
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
                    piscinaTV.setError(null);
                    botonGuardar.setEnabled(false);
                    //Toast.makeText(PiscinasSector1.this, "PISCINA PESCADA", Toast.LENGTH_SHORT).show();
                    verificarDatosEnFirebase10T("Piscina 21",fechaSeleccionada);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No se necesita implementar nada aquí
            }
        });


    }

    private void verificarDatosEnFirebase6T(final String piscinaSeleccionada, final String fechaSeleccionada) {

        limpiarCamposPiscina1();

        DatabaseReference referenciaPiscina = databaseReference.child(piscinaSeleccionada);
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
                    //Toast.makeText(PiscinasSector2.this, "No existen datos para esta fecha", Toast.LENGTH_SHORT).show();

                    // Habilitar el botón de guardar
                    botonGuardar.setEnabled(true);
                    botonGuardar.setText("GUARDAR");
                    botonActualizar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejo de errores
                Toast.makeText(PiscinasSector3.this, "Error al recuperar los datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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

                                Toast.makeText(PiscinasSector3.this, "DATOS ACTUALIZADOS", Toast.LENGTH_SHORT).show();
                                inicializarListas();
                                inicializarCamposVariables();
                                limpiarCamposPiscina1();

                            }
                        } else {
                            Toast.makeText(PiscinasSector3.this, "No se encontraron datos para actualizar", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(PiscinasSector3.this, "Error al actualizar datos en Firebase: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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

                        ETRojo.setText(rojoS);
                        ETFresco.setText(frescoS);
                        ETReportado.setText(reportadoS);
                        ETObservaciones.setText(observacionesS);

                        ETt1.setText(t1S);
                        ETt2.setText(t2S);
                        ETt3.setText(t3S);
                        ETt4.setText(t4S);
                        ETt6.setText(t5S);
                        ETt7.setText(t6S);
                        ETt8.setText(t7S);
                        ETt9.setText(t8S);

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
                Toast.makeText(PiscinasSector3.this, "Error al recuperar los datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void verificarDatosEnFirebase10T(final String piscinaSeleccionada, final String fechaSeleccionada) {
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
                Toast.makeText(PiscinasSector3.this, "Error al recuperar los datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void verificarDatosEnFirebase7T(final String piscinaSeleccionada, final String fechaSeleccionada) {

        limpiarCamposPiscina1();

        DatabaseReference referenciaPiscina = databaseReference.child(piscinaSeleccionada);
        Toast.makeText(PiscinasSector3.this, referenciaPiscina.toString(), Toast.LENGTH_SHORT).show();
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
                    //Toast.makeText(PiscinasSector3.this, "No existen datos para esta fecha", Toast.LENGTH_SHORT).show();

                    // Habilitar el botón de guardar
                    botonGuardar.setEnabled(true);
                    botonGuardar.setText("GUARDAR");
                    botonActualizar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejo de errores
                Toast.makeText(PiscinasSector3.this, "Error al recuperar los datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void limparcampospiscina1() {
        dietaET.setText("");
        sobranteTolvaET.setText("");
        recargaET.setText("");
        alVoleoET.setText("");
        sobranteCasetaET.setText("");
        autoCompleteTextViewTipos.setText("");
        ETt1.setText("");
        ETt2.setText("");
        ETt3.setText("");
        ETt4.setText("");
        ETt6.setText("");
        ETt7.setText("");
        ETt8.setText("");
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
    private void verificarDatosEnFirebase9T(final String piscinaSeleccionada, final String fechaSeleccionada) {
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
                    //Toast.makeText(PiscinasSector3.this, "No existen datos para esta fecha", Toast.LENGTH_SHORT).show();

                    // Habilitar el botón de guardar
                    botonGuardar.setEnabled(true);
                    botonGuardar.setText("GUARDAR");
                    botonActualizar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejo de errores
                Toast.makeText(PiscinasSector3.this, "Error al recuperar los datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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

                        ETRojo.setText(rojoS);
                        ETFresco.setText(frescoS);
                        ETReportado.setText(reportadoS);
                        ETObservaciones.setText(observacionesS);

                        botonActualizar.setVisibility(View.VISIBLE);
                        botonGuardar.setEnabled(false); // Desactiva el botón de guardar si es necesario
                        actualizarDatosEnFirebase5T(piscinaSeleccionada,currentDate);
                    }
                } else {
                    // No se encontraron datos para la fecha seleccionada
                    //Toast.makeText(PiscinasSector3.this, "No existen datos para esta fecha", Toast.LENGTH_SHORT).show();

                    // Habilitar el botón de guardar
                    botonGuardar.setEnabled(true);
                    botonGuardar.setText("GUARDAR");
                    botonActualizar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejo de errores
                Toast.makeText(PiscinasSector3.this, "Error al recuperar los datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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