    package com.example.tolvas;

    import android.annotation.SuppressLint;
    import android.content.Intent;
    import android.content.SharedPreferences;
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

    public class PiscinasSector1 extends AppCompatActivity {

        private TextInputLayout dietaTV,sobranteTolvaTV,recargaTV,alVoleoTV,sobranteCasetaTV,piscinaTV,tiposBalanceadoTV;
        private TextInputEditText dietaET,sobranteTolvaET,sobranteCasetaET,recargaET,alVoleoET;
        private TextInputLayout TVt1,TVt2,TVt3,TVt4,TVt5,TVt6,TVt7,TVt8,TVt9,TVt10;
        private TextInputEditText ETt1,ETt2,ETt3,ETt4,ETt5,ETt6,ETt7,ETt8,ETt9,ETt10,ETGramos, ETPorcentaje;
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
            currentDate = sdf.format(new Date());
            dateTextView.setText(currentDate);


            insertarDatosPiscinasZona1();
            volverInicio();


        }
        private void actualizarDatosDesdeFirebase() {
            // Lista de referencias a las piscinas
            List<String> piscinas = Arrays.asList("Piscina 1", "Piscina 2", "Piscina 3A", "Piscina 3B", "Piscina 4", "Piscina 5A", "Piscina 5B", "Piscina 6", "Piscina 7A", "Piscina 7B", "Piscina 8A", "Piscina 8B");

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
                    Intent intent = new Intent(PiscinasSector1.this, INICIO.class);
                    startActivity(intent);
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
                        if(piscinaSeleccionada.equals("Piscina 1")){
                            String tipoBalanceadoSeleccionado=autoCompleteTextViewTipos.getText().toString();
                            if (isEditTextEmpty(dietaET)||isEditTextEmpty(sobranteTolvaET)||isEditTextEmpty(alVoleoET)||isEditTextEmpty(sobranteCasetaET)
                                    ||isEditTextEmpty(recargaET)||tipoBalanceadoSeleccionado.equals("")||isEditTextEmpty(ETt1)||isEditTextEmpty(ETt2)||isEditTextEmpty(ETt3)
                                    ||isEditTextEmpty(ETt4)||isEditTextEmpty(ETt5)||isEditTextEmpty(ETt6)||isEditTextEmpty(ETt7)||isEditTextEmpty(ETt8)||isEditTextEmpty(ETt9)||isEditTextEmpty(ETt10)){
                                Toast.makeText(PiscinasSector1.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Piscinas p1= new Piscinas();
                            p1.setUid(UUID.randomUUID().toString());
                            p1.setFecha(dateTextView.getText().toString());
                            p1.setDieta(Integer.parseInt(Objects.requireNonNull(dietaET.getText()).toString()));
                            p1.setSobrantetolva(Integer.parseInt(Objects.requireNonNull(sobranteTolvaET.getText()).toString()));
                            p1.setRecarga(Integer.parseInt(Objects.requireNonNull(recargaET.getText()).toString()));
                            p1.setAlvoleo(Integer.parseInt(Objects.requireNonNull(alVoleoET.getText()).toString()));
                            p1.setSobrantecaseta(Integer.parseInt(Objects.requireNonNull(sobranteCasetaET.getText()).toString()));
                            p1.setTipobalanceado(tipoBalanceadoSeleccionado);
                            p1.setT1(Integer.parseInt(Objects.requireNonNull(ETt1.getText()).toString()));
                            p1.setT2(Integer.parseInt(Objects.requireNonNull(ETt2.getText()).toString()));
                            p1.setT3(Integer.parseInt(Objects.requireNonNull(ETt3.getText()).toString()));
                            p1.setT4(Integer.parseInt(Objects.requireNonNull(ETt4.getText()).toString()));
                            p1.setT5(Integer.parseInt(Objects.requireNonNull(ETt5.getText()).toString()));
                            p1.setT6(Integer.parseInt(Objects.requireNonNull(ETt6.getText()).toString()));
                            p1.setT7(Integer.parseInt(Objects.requireNonNull(ETt7.getText()).toString()));
                            p1.setT8(Integer.parseInt(Objects.requireNonNull(ETt8.getText()).toString()));
                            p1.setT9(Integer.parseInt(Objects.requireNonNull(ETt9.getText()).toString()));
                            p1.setT10(Integer.parseInt(Objects.requireNonNull(ETt10.getText()).toString()));
                            //p1.setGramosTotalP(Integer.parseInt(Objects.requireNonNull(ETGramos.getText()).toString()));
                            //p1.setPorcentajeSobranteP(Integer.parseInt(Objects.requireNonNull(ETPorcentaje.getText()).toString()));
                            //p1.setGramosTotalP(Integer.parseInt(Objects.requireNonNull(ETGramos.getText()).toString()));
                            //p1.setPorcentajeSobranteP(Integer.parseInt(Objects.requireNonNull(ETPorcentaje.getText()).toString()));
                            databaseReference.child("Piscina 1").child(p1.getUid()).setValue(p1);

                            //limparcampospiscina1();
                            verificarDatosEnFirebase10T("Piscina 1",currentDate);
                            Toast.makeText(PiscinasSector1.this, "DATOS INGRESADOS", Toast.LENGTH_SHORT).show();

                        } else if (piscinaSeleccionada.equals("Piscina 2")) {
                            String tipoBalanceadoSeleccionado=autoCompleteTextViewTipos.getText().toString();
                            if (isEditTextEmpty(dietaET)||isEditTextEmpty(sobranteTolvaET)||isEditTextEmpty(alVoleoET)||isEditTextEmpty(sobranteCasetaET)
                                    ||isEditTextEmpty(recargaET)||tipoBalanceadoSeleccionado.equals("")||isEditTextEmpty(ETt1)||isEditTextEmpty(ETt2)||isEditTextEmpty(ETt3)
                                    ||isEditTextEmpty(ETt4)||isEditTextEmpty(ETt6)||isEditTextEmpty(ETt7)||isEditTextEmpty(ETt8)){
                                Toast.makeText(PiscinasSector1.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Piscinas p2= new Piscinas();
                            p2.setUid(UUID.randomUUID().toString());
                            p2.setFecha(dateTextView.getText().toString());
                            p2.setDieta(Integer.parseInt(Objects.requireNonNull(dietaET.getText()).toString()));
                            p2.setSobrantetolva(Integer.parseInt(Objects.requireNonNull(sobranteTolvaET.getText()).toString()));
                            p2.setRecarga(Integer.parseInt(Objects.requireNonNull(recargaET.getText()).toString()));
                            p2.setAlvoleo(Integer.parseInt(Objects.requireNonNull(alVoleoET.getText()).toString()));
                            p2.setSobrantecaseta(Integer.parseInt(Objects.requireNonNull(sobranteCasetaET.getText()).toString()));
                            p2.setTipobalanceado(tipoBalanceadoSeleccionado);
                            p2.setT1(Integer.parseInt(Objects.requireNonNull(ETt1.getText()).toString()));
                            p2.setT2(Integer.parseInt(Objects.requireNonNull(ETt2.getText()).toString()));
                            p2.setT3(Integer.parseInt(Objects.requireNonNull(ETt3.getText()).toString()));
                            p2.setT4(Integer.parseInt(Objects.requireNonNull(ETt4.getText()).toString()));
                            p2.setT5(Integer.parseInt(Objects.requireNonNull(ETt6.getText()).toString()));
                            p2.setT6(Integer.parseInt(Objects.requireNonNull(ETt7.getText()).toString()));
                            p2.setT7(Integer.parseInt(Objects.requireNonNull(ETt8.getText()).toString()));
                            databaseReference.child("Piscina 2").child(p2.getUid()).setValue(p2);
                            //limparcampospiscina1();
                            verificarDatosEnFirebase7T("Piscina 2",currentDate);
                            Toast.makeText(PiscinasSector1.this, "DATOS INGRESADOS", Toast.LENGTH_SHORT).show();
                            
                        }else if (piscinaSeleccionada.equals("Piscina 3A")) {
                            String tipoBalanceadoSeleccionado=autoCompleteTextViewTipos.getText().toString();
                            if (isEditTextEmpty(dietaET)||isEditTextEmpty(sobranteTolvaET)||isEditTextEmpty(alVoleoET)||isEditTextEmpty(sobranteCasetaET)
                                    ||isEditTextEmpty(recargaET)||tipoBalanceadoSeleccionado.equals("")||isEditTextEmpty(ETt1)||isEditTextEmpty(ETt2)||isEditTextEmpty(ETt3)
                                    ||isEditTextEmpty(ETt6)||isEditTextEmpty(ETt7)||isEditTextEmpty(ETt8)){
                                Toast.makeText(PiscinasSector1.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Piscinas p3A= new Piscinas();
                            p3A.setUid(UUID.randomUUID().toString());
                            p3A.setFecha(dateTextView.getText().toString());
                            p3A.setDieta(Integer.parseInt(Objects.requireNonNull(dietaET.getText()).toString()));
                            p3A.setSobrantetolva(Integer.parseInt(Objects.requireNonNull(sobranteTolvaET.getText()).toString()));
                            p3A.setRecarga(Integer.parseInt(Objects.requireNonNull(recargaET.getText()).toString()));
                            p3A.setAlvoleo(Integer.parseInt(Objects.requireNonNull(alVoleoET.getText()).toString()));
                            p3A.setSobrantecaseta(Integer.parseInt(Objects.requireNonNull(sobranteCasetaET.getText()).toString()));
                            p3A.setTipobalanceado(tipoBalanceadoSeleccionado);
                            p3A.setT1(Integer.parseInt(Objects.requireNonNull(ETt1.getText()).toString()));
                            p3A.setT2(Integer.parseInt(Objects.requireNonNull(ETt2.getText()).toString()));
                            p3A.setT3(Integer.parseInt(Objects.requireNonNull(ETt3.getText()).toString()));
                            p3A.setT4(Integer.parseInt(Objects.requireNonNull(ETt6.getText()).toString()));
                            p3A.setT5(Integer.parseInt(Objects.requireNonNull(ETt7.getText()).toString()));
                            p3A.setT6(Integer.parseInt(Objects.requireNonNull(ETt8.getText()).toString()));
                            databaseReference.child("Piscina 3A").child(p3A.getUid()).setValue(p3A);
                            //limparcampospiscina1();
                            verificarDatosEnFirebase6T("Piscina 3A",currentDate);
                            Toast.makeText(PiscinasSector1.this, "DATOS INGRESADOS", Toast.LENGTH_SHORT).show();
                         } else if (piscinaSeleccionada.equals("Piscina 3B")) {
                            String tipoBalanceadoSeleccionado=autoCompleteTextViewTipos.getText().toString();
                            if (isEditTextEmpty(dietaET)||isEditTextEmpty(sobranteTolvaET)||isEditTextEmpty(alVoleoET)||isEditTextEmpty(sobranteCasetaET)
                                    ||isEditTextEmpty(recargaET)||tipoBalanceadoSeleccionado.equals("")||isEditTextEmpty(ETt1)||isEditTextEmpty(ETt2)||isEditTextEmpty(ETt3)
                                    ||isEditTextEmpty(ETt6)||isEditTextEmpty(ETt7)||isEditTextEmpty(ETt8)){
                                Toast.makeText(PiscinasSector1.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Piscinas p3B= new Piscinas();
                            p3B.setUid(UUID.randomUUID().toString());
                            p3B.setFecha(dateTextView.getText().toString());
                            p3B.setDieta(Integer.parseInt(Objects.requireNonNull(dietaET.getText()).toString()));
                            p3B.setSobrantetolva(Integer.parseInt(Objects.requireNonNull(sobranteTolvaET.getText()).toString()));
                            p3B.setRecarga(Integer.parseInt(Objects.requireNonNull(recargaET.getText()).toString()));
                            p3B.setAlvoleo(Integer.parseInt(Objects.requireNonNull(alVoleoET.getText()).toString()));
                            p3B.setSobrantecaseta(Integer.parseInt(Objects.requireNonNull(sobranteCasetaET.getText()).toString()));
                            p3B.setTipobalanceado(tipoBalanceadoSeleccionado);
                            p3B.setT1(Integer.parseInt(Objects.requireNonNull(ETt1.getText()).toString()));
                            p3B.setT2(Integer.parseInt(Objects.requireNonNull(ETt2.getText()).toString()));
                            p3B.setT3(Integer.parseInt(Objects.requireNonNull(ETt3.getText()).toString()));
                            p3B.setT4(Integer.parseInt(Objects.requireNonNull(ETt6.getText()).toString()));
                            p3B.setT5(Integer.parseInt(Objects.requireNonNull(ETt7.getText()).toString()));
                            p3B.setT6(Integer.parseInt(Objects.requireNonNull(ETt8.getText()).toString()));
                            databaseReference.child("Piscina 3B").child(p3B.getUid()).setValue(p3B);
                            //limparcampospiscina1();
                            verificarDatosEnFirebase6T("Piscina 3B",currentDate);
                            Toast.makeText(PiscinasSector1.this, "DATOS INGRESADOS", Toast.LENGTH_SHORT).show();
                        } else if (piscinaSeleccionada.equals("Piscina 4")) {
                            String tipoBalanceadoSeleccionado=autoCompleteTextViewTipos.getText().toString();
                            if (isEditTextEmpty(dietaET)||isEditTextEmpty(sobranteTolvaET)||isEditTextEmpty(alVoleoET)||isEditTextEmpty(sobranteCasetaET)
                                    ||isEditTextEmpty(recargaET)||tipoBalanceadoSeleccionado.equals("")||isEditTextEmpty(ETt1)||isEditTextEmpty(ETt2)||isEditTextEmpty(ETt3)
                                    ||isEditTextEmpty(ETt6)||isEditTextEmpty(ETt7)||isEditTextEmpty(ETt8)){
                                Toast.makeText(PiscinasSector1.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Piscinas p4= new Piscinas();
                            p4.setUid(UUID.randomUUID().toString());
                            p4.setFecha(dateTextView.getText().toString());
                            p4.setDieta(Integer.parseInt(Objects.requireNonNull(dietaET.getText()).toString()));
                            p4.setSobrantetolva(Integer.parseInt(Objects.requireNonNull(sobranteTolvaET.getText()).toString()));
                            p4.setRecarga(Integer.parseInt(Objects.requireNonNull(recargaET.getText()).toString()));
                            p4.setAlvoleo(Integer.parseInt(Objects.requireNonNull(alVoleoET.getText()).toString()));
                            p4.setSobrantecaseta(Integer.parseInt(Objects.requireNonNull(sobranteCasetaET.getText()).toString()));
                            p4.setTipobalanceado(tipoBalanceadoSeleccionado);
                            p4.setT1(Integer.parseInt(Objects.requireNonNull(ETt1.getText()).toString()));
                            p4.setT2(Integer.parseInt(Objects.requireNonNull(ETt2.getText()).toString()));
                            p4.setT3(Integer.parseInt(Objects.requireNonNull(ETt3.getText()).toString()));
                            p4.setT4(Integer.parseInt(Objects.requireNonNull(ETt6.getText()).toString()));
                            p4.setT5(Integer.parseInt(Objects.requireNonNull(ETt7.getText()).toString()));
                            p4.setT6(Integer.parseInt(Objects.requireNonNull(ETt8.getText()).toString()));
                            databaseReference.child("Piscina 4").child(p4.getUid()).setValue(p4);
                            //limparcampospiscina1();
                            verificarDatosEnFirebase6T("Piscina 4",currentDate);
                            Toast.makeText(PiscinasSector1.this, "DATOS INGRESADOS", Toast.LENGTH_SHORT).show();
                        } else if (piscinaSeleccionada.equals("Piscina 5A")) {
                            String tipoBalanceadoSeleccionado=autoCompleteTextViewTipos.getText().toString();
                            if (isEditTextEmpty(dietaET)||isEditTextEmpty(sobranteTolvaET)||isEditTextEmpty(alVoleoET)||isEditTextEmpty(sobranteCasetaET)
                                    ||isEditTextEmpty(recargaET)||tipoBalanceadoSeleccionado.equals("")||isEditTextEmpty(ETt1)||isEditTextEmpty(ETt2)||isEditTextEmpty(ETt3)
                                    ||isEditTextEmpty(ETt4)||isEditTextEmpty(ETt5)||isEditTextEmpty(ETt6)||isEditTextEmpty(ETt7)||isEditTextEmpty(ETt8)||isEditTextEmpty(ETt9)){
                                Toast.makeText(PiscinasSector1.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Piscinas p5A= new Piscinas();
                            p5A.setUid(UUID.randomUUID().toString());
                            p5A.setFecha(dateTextView.getText().toString());
                            p5A.setDieta(Integer.parseInt(Objects.requireNonNull(dietaET.getText()).toString()));
                            p5A.setSobrantetolva(Integer.parseInt(Objects.requireNonNull(sobranteTolvaET.getText()).toString()));
                            p5A.setRecarga(Integer.parseInt(Objects.requireNonNull(recargaET.getText()).toString()));
                            p5A.setAlvoleo(Integer.parseInt(Objects.requireNonNull(alVoleoET.getText()).toString()));
                            p5A.setSobrantecaseta(Integer.parseInt(Objects.requireNonNull(sobranteCasetaET.getText()).toString()));
                            p5A.setTipobalanceado(tipoBalanceadoSeleccionado);
                            p5A.setT1(Integer.parseInt(Objects.requireNonNull(ETt1.getText()).toString()));
                            p5A.setT2(Integer.parseInt(Objects.requireNonNull(ETt2.getText()).toString()));
                            p5A.setT3(Integer.parseInt(Objects.requireNonNull(ETt3.getText()).toString()));
                            p5A.setT4(Integer.parseInt(Objects.requireNonNull(ETt4.getText()).toString()));
                            p5A.setT5(Integer.parseInt(Objects.requireNonNull(ETt5.getText()).toString()));
                            p5A.setT6(Integer.parseInt(Objects.requireNonNull(ETt6.getText()).toString()));
                            p5A.setT7(Integer.parseInt(Objects.requireNonNull(ETt7.getText()).toString()));
                            p5A.setT8(Integer.parseInt(Objects.requireNonNull(ETt8.getText()).toString()));
                            p5A.setT9(Integer.parseInt(Objects.requireNonNull(ETt9.getText()).toString()));
                            databaseReference.child("Piscina 5A").child(p5A.getUid()).setValue(p5A);
                            //limparcampospiscina1();
                            verificarDatosEnFirebase9T("Piscina 5A",currentDate);
                            Toast.makeText(PiscinasSector1.this, "DATOS INGRESADOS", Toast.LENGTH_SHORT).show();
                        }else if (piscinaSeleccionada.equals("Piscina 5B")) {
                            Toast.makeText(PiscinasSector1.this, "PISCINA NO HABILITADA", Toast.LENGTH_SHORT).show();
                        }
                        else if (piscinaSeleccionada.equals("Piscina 6")) {
                            String tipoBalanceadoSeleccionado=autoCompleteTextViewTipos.getText().toString();
                            if (isEditTextEmpty(dietaET)||isEditTextEmpty(sobranteTolvaET)||isEditTextEmpty(alVoleoET)||isEditTextEmpty(sobranteCasetaET)
                                    ||isEditTextEmpty(recargaET)||tipoBalanceadoSeleccionado.equals("")||isEditTextEmpty(ETt1)||isEditTextEmpty(ETt2)||isEditTextEmpty(ETt3)
                                    ||isEditTextEmpty(ETt4)||isEditTextEmpty(ETt5)||isEditTextEmpty(ETt6)||isEditTextEmpty(ETt7)||isEditTextEmpty(ETt8)||isEditTextEmpty(ETt9)
                                    ||isEditTextEmpty(ETt10)){
                                Toast.makeText(PiscinasSector1.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Piscinas p6= new Piscinas();
                            p6.setUid(UUID.randomUUID().toString());
                            p6.setFecha(dateTextView.getText().toString());
                            p6.setDieta(Integer.parseInt(Objects.requireNonNull(dietaET.getText()).toString()));
                            p6.setSobrantetolva(Integer.parseInt(Objects.requireNonNull(sobranteTolvaET.getText()).toString()));
                            p6.setRecarga(Integer.parseInt(Objects.requireNonNull(recargaET.getText()).toString()));
                            p6.setAlvoleo(Integer.parseInt(Objects.requireNonNull(alVoleoET.getText()).toString()));
                            p6.setSobrantecaseta(Integer.parseInt(Objects.requireNonNull(sobranteCasetaET.getText()).toString()));
                            p6.setTipobalanceado(tipoBalanceadoSeleccionado);
                            p6.setT1(Integer.parseInt(Objects.requireNonNull(ETt1.getText()).toString()));
                            p6.setT2(Integer.parseInt(Objects.requireNonNull(ETt2.getText()).toString()));
                            p6.setT3(Integer.parseInt(Objects.requireNonNull(ETt3.getText()).toString()));
                            p6.setT4(Integer.parseInt(Objects.requireNonNull(ETt4.getText()).toString()));
                            p6.setT5(Integer.parseInt(Objects.requireNonNull(ETt5.getText()).toString()));
                            p6.setT6(Integer.parseInt(Objects.requireNonNull(ETt6.getText()).toString()));
                            p6.setT7(Integer.parseInt(Objects.requireNonNull(ETt7.getText()).toString()));
                            p6.setT8(Integer.parseInt(Objects.requireNonNull(ETt8.getText()).toString()));
                            p6.setT9(Integer.parseInt(Objects.requireNonNull(ETt9.getText()).toString()));
                            p6.setT10(Integer.parseInt(Objects.requireNonNull(ETt10.getText()).toString()));
                            databaseReference.child("Piscina 6").child(p6.getUid()).setValue(p6);
                            //limparcampospiscina1();
                            verificarDatosEnFirebase10T("Piscina 6",currentDate);
                            Toast.makeText(PiscinasSector1.this, "DATOS INGRESADOS", Toast.LENGTH_SHORT).show();
                        } else if (piscinaSeleccionada.equals("Piscina 7A")) {
                            String tipoBalanceadoSeleccionado=autoCompleteTextViewTipos.getText().toString();
                            if (isEditTextEmpty(dietaET)||isEditTextEmpty(sobranteTolvaET)||isEditTextEmpty(alVoleoET)||isEditTextEmpty(sobranteCasetaET)
                                    ||isEditTextEmpty(recargaET)||tipoBalanceadoSeleccionado.equals("")||isEditTextEmpty(ETt1)||isEditTextEmpty(ETt2)||isEditTextEmpty(ETt3)
                                    ||isEditTextEmpty(ETt4)||isEditTextEmpty(ETt5)||isEditTextEmpty(ETt6)||isEditTextEmpty(ETt7)||isEditTextEmpty(ETt8)||isEditTextEmpty(ETt9)
                                    ||isEditTextEmpty(ETt10)){
                                Toast.makeText(PiscinasSector1.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Piscinas p7A= new Piscinas();
                            p7A.setUid(UUID.randomUUID().toString());
                            p7A.setFecha(dateTextView.getText().toString());
                            p7A.setDieta(Integer.parseInt(Objects.requireNonNull(dietaET.getText()).toString()));
                            p7A.setSobrantetolva(Integer.parseInt(Objects.requireNonNull(sobranteTolvaET.getText()).toString()));
                            p7A.setRecarga(Integer.parseInt(Objects.requireNonNull(recargaET.getText()).toString()));
                            p7A.setAlvoleo(Integer.parseInt(Objects.requireNonNull(alVoleoET.getText()).toString()));
                            p7A.setSobrantecaseta(Integer.parseInt(Objects.requireNonNull(sobranteCasetaET.getText()).toString()));
                            p7A.setTipobalanceado(tipoBalanceadoSeleccionado);
                            p7A.setT1(Integer.parseInt(Objects.requireNonNull(ETt1.getText()).toString()));
                            p7A.setT2(Integer.parseInt(Objects.requireNonNull(ETt2.getText()).toString()));
                            p7A.setT3(Integer.parseInt(Objects.requireNonNull(ETt3.getText()).toString()));
                            p7A.setT4(Integer.parseInt(Objects.requireNonNull(ETt4.getText()).toString()));
                            p7A.setT5(Integer.parseInt(Objects.requireNonNull(ETt5.getText()).toString()));
                            p7A.setT6(Integer.parseInt(Objects.requireNonNull(ETt6.getText()).toString()));
                            p7A.setT7(Integer.parseInt(Objects.requireNonNull(ETt7.getText()).toString()));
                            p7A.setT8(Integer.parseInt(Objects.requireNonNull(ETt8.getText()).toString()));
                            p7A.setT9(Integer.parseInt(Objects.requireNonNull(ETt9.getText()).toString()));
                            p7A.setT10(Integer.parseInt(Objects.requireNonNull(ETt10.getText()).toString()));
                            databaseReference.child("Piscina 7A").child(p7A.getUid()).setValue(p7A);
                            //limparcampospiscina1();
                            verificarDatosEnFirebase10T("Piscina 7A",currentDate);
                            Toast.makeText(PiscinasSector1.this, "DATOS INGRESADOS", Toast.LENGTH_SHORT).show();
                        } else if (piscinaSeleccionada.equals("Piscina 7B")) {
                            Toast.makeText(PiscinasSector1.this, "PISCINA NO HABILITADA", Toast.LENGTH_SHORT).show();

                        }else if (piscinaSeleccionada.equals("Piscina 8A")) {
                            String tipoBalanceadoSeleccionado=autoCompleteTextViewTipos.getText().toString();
                            if (isEditTextEmpty(dietaET)||isEditTextEmpty(sobranteTolvaET)||isEditTextEmpty(alVoleoET)||isEditTextEmpty(sobranteCasetaET)
                                    ||isEditTextEmpty(recargaET)||tipoBalanceadoSeleccionado.equals("")||isEditTextEmpty(ETt1)||isEditTextEmpty(ETt2)||isEditTextEmpty(ETt3)
                                    ||isEditTextEmpty(ETt4)||isEditTextEmpty(ETt5)||isEditTextEmpty(ETt6)||isEditTextEmpty(ETt7)||isEditTextEmpty(ETt8)||isEditTextEmpty(ETt9)){
                                Toast.makeText(PiscinasSector1.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Piscinas p8A= new Piscinas();
                            p8A.setUid(UUID.randomUUID().toString());
                            p8A.setFecha(dateTextView.getText().toString());
                            p8A.setDieta(Integer.parseInt(Objects.requireNonNull(dietaET.getText()).toString()));
                            p8A.setSobrantetolva(Integer.parseInt(Objects.requireNonNull(sobranteTolvaET.getText()).toString()));
                            p8A.setRecarga(Integer.parseInt(Objects.requireNonNull(recargaET.getText()).toString()));
                            p8A.setAlvoleo(Integer.parseInt(Objects.requireNonNull(alVoleoET.getText()).toString()));
                            p8A.setSobrantecaseta(Integer.parseInt(Objects.requireNonNull(sobranteCasetaET.getText()).toString()));
                            p8A.setTipobalanceado(tipoBalanceadoSeleccionado);
                            p8A.setT1(Integer.parseInt(Objects.requireNonNull(ETt1.getText()).toString()));
                            p8A.setT2(Integer.parseInt(Objects.requireNonNull(ETt2.getText()).toString()));
                            p8A.setT3(Integer.parseInt(Objects.requireNonNull(ETt3.getText()).toString()));
                            p8A.setT4(Integer.parseInt(Objects.requireNonNull(ETt4.getText()).toString()));
                            p8A.setT5(Integer.parseInt(Objects.requireNonNull(ETt5.getText()).toString()));
                            p8A.setT6(Integer.parseInt(Objects.requireNonNull(ETt6.getText()).toString()));
                            p8A.setT7(Integer.parseInt(Objects.requireNonNull(ETt7.getText()).toString()));
                            p8A.setT8(Integer.parseInt(Objects.requireNonNull(ETt8.getText()).toString()));
                            p8A.setT9(Integer.parseInt(Objects.requireNonNull(ETt9.getText()).toString()));
                            databaseReference.child("Piscina 8A").child(p8A.getUid()).setValue(p8A);
                            //limparcampospiscina1();
                            verificarDatosEnFirebase9T("Piscina 8A",currentDate);
                            Toast.makeText(PiscinasSector1.this, "DATOS INGRESADOS", Toast.LENGTH_SHORT).show();
                        }else if(piscinaSeleccionada.equals("Piscina 8B")){
                            Toast.makeText(PiscinasSector1.this,"PISCINA NO HABILITADA 1",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        }


        private void inicializarListas() {

            piscinaTV = findViewById(R.id.tvPiscinas);
            autoCompleteTextViewPiscinas =findViewById(R.id.listaPiscinas);
            String[] opcionesPiscinas = getResources().getStringArray(R.array.options_listZ1);
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
                    String fechaSeleccionada = dateTextView.getText().toString();
                    if (!piscinaSeleccionada.isEmpty()) {
                    }

                    // Aquí puedes manejar el texto cambiado
                    if(piscinaSeleccionada.equals("Piscina 1")){
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
                        verificarDatosEnFirebase10T("Piscina 1",fechaSeleccionada);
                    } else if (piscinaSeleccionada.equals("Piscina 2")) {
                        limpiarCamposPiscina1();
                        TVz1.setText("ZONA 1");
                        TVz2.setText("ZONA 2");
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
                        botonGuardar.setEnabled(true);
                        verificarDatosEnFirebase7T("Piscina 2",fechaSeleccionada);
                    } else if(piscinaSeleccionada.equals("Piscina 3A")){
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
                        verificarDatosEnFirebase6T("Piscina 3A",fechaSeleccionada);
                    }else if(piscinaSeleccionada.equals("Piscina 3B")){
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
                        verificarDatosEnFirebase6T("Piscina 3B",fechaSeleccionada);
                    } else if (piscinaSeleccionada.equals("Piscina 4")) {
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
                        verificarDatosEnFirebase6T("Piscina 4",fechaSeleccionada);
                    } else if (piscinaSeleccionada.equals("Piscina 5A")) {
                        limpiarCamposPiscina1();
                        TVz1.setText("ZONA 1");
                        TVz2.setText("ZONA 2");
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
                        TVt10.setVisibility(View.INVISIBLE);
                        TVt10.setHint("TOLVA 10");
                        TVt10.setEnabled(false);
                        botonGuardar.setEnabled(false);
                        verificarDatosEnFirebase9T("Piscina 5A",fechaSeleccionada);
                    } else if (piscinaSeleccionada.equals("Piscina 5B")) {
                        limpiarCamposPiscina1();
                        TVz1.setText("ZONA 1");
                        TVz2.setText("ZONA 2");
                        piscinaTV.setError(null);
                        TVt1.setVisibility(View.INVISIBLE);
                        TVt2.setVisibility(View.INVISIBLE);
                        TVt3.setVisibility(View.INVISIBLE);
                        TVt4.setVisibility(View.INVISIBLE);
                        TVt4.setEnabled(false);
                        TVt5.setVisibility(View.INVISIBLE);
                        TVt5.setEnabled(false);
                        TVt6.setVisibility(View.INVISIBLE);
                        TVt7.setVisibility(View.INVISIBLE);
                        TVt8.setVisibility(View.INVISIBLE);
                        TVt9.setVisibility(View.INVISIBLE);
                        TVt10.setVisibility(View.INVISIBLE);
                        botonGuardar.setEnabled(false);
                        Toast.makeText(PiscinasSector1.this, "PISCINA NO HABILITADA", Toast.LENGTH_SHORT).show();
                    }else if (piscinaSeleccionada.equals("Piscina 6")) {
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
                        botonGuardar.setEnabled(true);
                        verificarDatosEnFirebase10T("Piscina 6",fechaSeleccionada);
                    } else if (piscinaSeleccionada.equals("Piscina 7A")) {
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
                        verificarDatosEnFirebase10T("Piscina 7A",fechaSeleccionada);
                    } else if (piscinaSeleccionada.equals("Piscina 7B")) {
                        limpiarCamposPiscina1();
                        piscinaTV.setError(null);
                        TVt1.setVisibility(View.INVISIBLE);
                        TVt2.setVisibility(View.INVISIBLE);
                        TVt3.setVisibility(View.INVISIBLE);
                        TVt4.setVisibility(View.INVISIBLE);
                        TVt4.setEnabled(false);
                        TVt5.setVisibility(View.INVISIBLE);
                        TVt5.setEnabled(false);
                        TVt6.setVisibility(View.INVISIBLE);
                        TVt7.setVisibility(View.INVISIBLE);
                        TVt8.setVisibility(View.INVISIBLE);
                        TVt9.setVisibility(View.INVISIBLE);
                        TVt10.setVisibility(View.INVISIBLE);
                        botonGuardar.setEnabled(false);
                        Toast.makeText(PiscinasSector1.this, "PISCINA NO HABILITADA", Toast.LENGTH_SHORT).show();
                    }else if (piscinaSeleccionada.equals("Piscina 8A")) {
                        limpiarCamposPiscina1();
                        TVz1.setText("ZONA 1");
                        TVz2.setText("ZONA 2");
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
                        botonGuardar.setEnabled(false);
                        verificarDatosEnFirebase9T("Piscina 8A",fechaSeleccionada);
                    }  else if(piscinaSeleccionada.equals("Piscina 8B")){
                        limpiarCamposPiscina1();
                        piscinaTV.setError(null);
                        TVt1.setVisibility(View.INVISIBLE);
                        TVt2.setVisibility(View.INVISIBLE);
                        TVt3.setVisibility(View.INVISIBLE);
                        TVt4.setVisibility(View.INVISIBLE);
                        TVt4.setEnabled(false);
                        TVt5.setVisibility(View.INVISIBLE);
                        TVt5.setEnabled(false);
                        TVt6.setVisibility(View.INVISIBLE);
                        TVt7.setVisibility(View.INVISIBLE);
                        TVt8.setVisibility(View.INVISIBLE);
                        TVt9.setVisibility(View.INVISIBLE);
                        TVt10.setVisibility(View.INVISIBLE);
                        botonGuardar.setEnabled(false);
                        Toast.makeText(PiscinasSector1.this, "PISCINA NO HABILITADA", Toast.LENGTH_SHORT).show();
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
            //Toast.makeText(PiscinasSector1.this, referenciaPiscina.toString(), Toast.LENGTH_SHORT).show();
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

                            botonActualizar.setVisibility(View.VISIBLE);
                            botonGuardar.setEnabled(false); // Desactiva el botón de guardar si es necesario
                            actualizarDatosEnFirebase6T(piscinaSeleccionada,currentDate);
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
                    Toast.makeText(PiscinasSector1.this, "Error al recuperar los datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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

                                    Toast.makeText(PiscinasSector1.this, "DATOS ACTUALIZADOS", Toast.LENGTH_SHORT).show();
                                    inicializarListas();
                                    inicializarCamposVariables();
                                    limpiarCamposPiscina1();

                                }
                            } else {
                                Toast.makeText(PiscinasSector1.this, "No se encontraron datos para actualizar", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(PiscinasSector1.this, "Error al actualizar datos en Firebase: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

        private void verificarDatosEnFirebase7T(final String piscinaSeleccionada, final String fechaSeleccionada) {

            limpiarCamposPiscina1();

            DatabaseReference referenciaPiscina = databaseReference.child(piscinaSeleccionada);
            //Toast.makeText(PiscinasSector1.this, referenciaPiscina.toString(), Toast.LENGTH_SHORT).show();
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

                            botonActualizar.setVisibility(View.VISIBLE);
                            botonGuardar.setEnabled(false); // Desactiva el botón de guardar si es necesario
                            actualizarDatosEnFirebase7T(piscinaSeleccionada,currentDate);
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
                    Toast.makeText(PiscinasSector1.this, "Error al recuperar los datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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

                                    Toast.makeText(PiscinasSector1.this, "DATOS ACTUALIZADOS", Toast.LENGTH_SHORT).show();
                                    inicializarListas();
                                    inicializarCamposVariables();
                                    limpiarCamposPiscina1();

                                }
                            } else {
                                Toast.makeText(PiscinasSector1.this, "No se encontraron datos para actualizar", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(PiscinasSector1.this, "Error al actualizar datos en Firebase: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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

                            botonActualizar.setVisibility(View.VISIBLE);
                            botonGuardar.setEnabled(false); // Desactiva el botón de guardar si es necesario
                            actualizarDatosEnFirebase9T(piscinaSeleccionada,currentDate);
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
                    Toast.makeText(PiscinasSector1.this, "Error al recuperar los datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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

                                    Toast.makeText(PiscinasSector1.this, "DATOS ACTUALIZADOS", Toast.LENGTH_SHORT).show();
                                    inicializarListas();
                                    inicializarCamposVariables();
                                    limpiarCamposPiscina1();

                                }
                            } else {
                                Toast.makeText(PiscinasSector1.this, "No se encontraron datos para actualizar", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(PiscinasSector1.this, "Error al actualizar datos en Firebase: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
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
                            Long gramos = snapshot.child("gramosTotalP").getValue(Long.class);
                            Long porcentaje = snapshot.child("porcentajeSobranteP").getValue(Long.class);


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
                            String gramosS = (t9 != null) ? t9.toString() : "";
                            String porcentajeS = (t10 != null) ? t10.toString() : "";

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
                    Toast.makeText(PiscinasSector1.this, "Error al recuperar los datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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

                                    Toast.makeText(PiscinasSector1.this, "DATOS ACTUALIZADOS", Toast.LENGTH_SHORT).show();
                                    inicializarListas();
                                    inicializarCamposVariables();
                                    limpiarCamposPiscina1();

                                }
                            } else {
                                Toast.makeText(PiscinasSector1.this, "No se encontraron datos para actualizar", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(PiscinasSector1.this, "Error al actualizar datos en Firebase: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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

            // ETGramos=findViewById(R.id.etgramos);
            // ETPorcentaje=findViewById(R.id.etporcentaje);


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
            botonActualizar.setVisibility(View.INVISIBLE);

        }


    }