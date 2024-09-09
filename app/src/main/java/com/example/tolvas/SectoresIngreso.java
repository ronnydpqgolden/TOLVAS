package com.example.tolvas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class SectoresIngreso extends AppCompatActivity {

    private static final String CORRECT_PIN = "9876";
    private static final String CORRECT_PIN2 = "8765";
    private static final String CORRECT_PIN3 = "7654";
    private static final String CORRECT_PIN4 = "6543";
    private static final String CORRECT_PIN5 = "5432";
    private static final String CORRECT_PIN6 = "4321";
    private static final String CORRECT_PIN7 = "1234";
    private static final String CORRECT_PIN8 = "2345";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zonas);
        MaterialButton z1=findViewById(R.id.botonz1);
        MaterialButton z2=findViewById(R.id.botonz2);
        MaterialButton z3=findViewById(R.id.botonz3);
        MaterialButton z4=findViewById(R.id.botonz4);
        MaterialButton z5=findViewById(R.id.botonz5);
        MaterialButton z6=findViewById(R.id.botonz6);
        MaterialButton z7=findViewById(R.id.botonz7);
        MaterialButton z8=findViewById(R.id.botonz8);
        View.OnClickListener showPinDialog = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPinDialog(SectoresIngreso.this);
            }
        };
        View.OnClickListener showPinDialog2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPinDialog2(SectoresIngreso.this);
            }
        };
        View.OnClickListener showPinDialog3 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPinDialog3(SectoresIngreso.this);
            }
        };
        View.OnClickListener showPinDialog4 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPinDialog4(SectoresIngreso.this);
            }
        };
        View.OnClickListener showPinDialog5 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPinDialog5(SectoresIngreso.this);
            }
        };
        View.OnClickListener showPinDialog6 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPinDialog6(SectoresIngreso.this);
            }
        };
        View.OnClickListener showPinDialog7 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPinDialog7(SectoresIngreso.this);
            }
        };
        View.OnClickListener showPinDialog8 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPinDialog8(SectoresIngreso.this);
            }
        };


        z1.setOnClickListener(showPinDialog);
        z2.setOnClickListener(showPinDialog2);
        z3.setOnClickListener(showPinDialog3);
        z4.setOnClickListener(showPinDialog4);
        z5.setOnClickListener(showPinDialog5);
        z6.setOnClickListener(showPinDialog6);
        z7.setOnClickListener(showPinDialog7);
        z8.setOnClickListener(showPinDialog8);

        }



    private void showPinDialog(Context context) {
        // Inflate the custom layout for the dialog
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_pin, null);

        final EditText pinEditText = dialogView.findViewById(R.id.pinEditText);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Ingrese PIN")
                .setView(dialogView)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                pinEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // No need to implement
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // No need to implement
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String enteredPin = s.toString();
                        if (enteredPin.length() == 4) { // Check if PIN has 4 digits
                            if (CORRECT_PIN.equals(enteredPin)) {
                                // If PIN is correct, start the activity
                                Intent intent = new Intent(SectoresIngreso.this, PiscinasSector1.class);
                                startActivity(intent);
                                dialog.dismiss(); // Close dialog
                            } else {
                                // Show error if PIN is incorrect
                                pinEditText.setError("PIN incorrecto");
                                pinEditText.setText(""); // Clear the PIN field
                            }
                        }
                    }
                });
            }
        });

        dialog.show();
    }
    private void showPinDialog2(Context context) {
        // Inflate the custom layout for the dialog
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_pin, null);

        final EditText pinEditText = dialogView.findViewById(R.id.pinEditText);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Ingrese PIN")
                .setView(dialogView)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                pinEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // No need to implement
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // No need to implement
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String enteredPin = s.toString();
                        if (enteredPin.length() == 4) { // Check if PIN has 4 digits
                            if (CORRECT_PIN2.equals(enteredPin)) {
                                // If PIN is correct, start the activity
                                Intent intent = new Intent(SectoresIngreso.this, PiscinasSector2.class);
                                startActivity(intent);
                                dialog.dismiss(); // Close dialog
                            } else {
                                // Show error if PIN is incorrect
                                pinEditText.setError("PIN incorrecto");
                                pinEditText.setText(""); // Clear the PIN field
                            }
                        }
                    }
                });
            }
        });

        dialog.show();
    }
    private void showPinDialog3(Context context) {
        // Inflate the custom layout for the dialog
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_pin, null);

        final EditText pinEditText = dialogView.findViewById(R.id.pinEditText);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Ingrese PIN")
                .setView(dialogView)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                pinEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // No need to implement
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // No need to implement
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String enteredPin = s.toString();
                        if (enteredPin.length() == 4) { // Check if PIN has 4 digits
                            if (CORRECT_PIN3.equals(enteredPin)) {
                                // If PIN is correct, start the activity
                                Intent intent = new Intent(SectoresIngreso.this, PiscinasSector3.class);
                                startActivity(intent);
                                dialog.dismiss(); // Close dialog
                            } else {
                                // Show error if PIN is incorrect
                                pinEditText.setError("PIN incorrecto");
                                pinEditText.setText(""); // Clear the PIN field
                            }
                        }
                    }
                });
            }
        });

        dialog.show();
    }
    private void showPinDialog4(Context context) {
        // Inflate the custom layout for the dialog
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_pin, null);

        final EditText pinEditText = dialogView.findViewById(R.id.pinEditText);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Ingrese PIN")
                .setView(dialogView)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                pinEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // No need to implement
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // No need to implement
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String enteredPin = s.toString();
                        if (enteredPin.length() == 4) { // Check if PIN has 4 digits
                            if (CORRECT_PIN4.equals(enteredPin)) {
                                // If PIN is correct, start the activity
                                Intent intent = new Intent(SectoresIngreso.this, PiscinasSector4.class);
                                startActivity(intent);
                                dialog.dismiss(); // Close dialog
                            } else {
                                // Show error if PIN is incorrect
                                pinEditText.setError("PIN incorrecto");
                                pinEditText.setText(""); // Clear the PIN field
                            }
                        }
                    }
                });
            }
        });

        dialog.show();
    }
    private void showPinDialog5(Context context) {
        // Inflate the custom layout for the dialog
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_pin, null);

        final EditText pinEditText = dialogView.findViewById(R.id.pinEditText);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Ingrese PIN")
                .setView(dialogView)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                pinEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // No need to implement
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // No need to implement
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String enteredPin = s.toString();
                        if (enteredPin.length() == 4) { // Check if PIN has 4 digits
                            if (CORRECT_PIN5.equals(enteredPin)) {
                                // If PIN is correct, start the activity
                                Intent intent = new Intent(SectoresIngreso.this, PiscinasSector5.class);
                                startActivity(intent);
                                dialog.dismiss(); // Close dialog
                            } else {
                                // Show error if PIN is incorrect
                                pinEditText.setError("PIN incorrecto");
                                pinEditText.setText(""); // Clear the PIN field
                            }
                        }
                    }
                });
            }
        });

        dialog.show();
    }
    private void showPinDialog6(Context context) {
        // Inflate the custom layout for the dialog
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_pin, null);

        final EditText pinEditText = dialogView.findViewById(R.id.pinEditText);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Ingrese PIN")
                .setView(dialogView)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                pinEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // No need to implement
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // No need to implement
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String enteredPin = s.toString();
                        if (enteredPin.length() == 4) { // Check if PIN has 4 digits
                            if (CORRECT_PIN6.equals(enteredPin)) {
                                // If PIN is correct, start the activity
                                Intent intent = new Intent(SectoresIngreso.this, PiscinasSector6.class);
                                startActivity(intent);
                                dialog.dismiss(); // Close dialog
                            } else {
                                // Show error if PIN is incorrect
                                pinEditText.setError("PIN incorrecto");
                                pinEditText.setText(""); // Clear the PIN field
                            }
                        }
                    }
                });
            }
        });

        dialog.show();
    }
    private void showPinDialog7(Context context) {
        // Inflate the custom layout for the dialog
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_pin, null);

        final EditText pinEditText = dialogView.findViewById(R.id.pinEditText);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Ingrese PIN")
                .setView(dialogView)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                pinEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // No need to implement
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // No need to implement
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String enteredPin = s.toString();
                        if (enteredPin.length() == 4) { // Check if PIN has 4 digits
                            if (CORRECT_PIN7.equals(enteredPin)) {
                                // If PIN is correct, start the activity
                                Intent intent = new Intent(SectoresIngreso.this, PiscinasSector7.class);
                                startActivity(intent);
                                dialog.dismiss(); // Close dialog
                            } else {
                                // Show error if PIN is incorrect
                                pinEditText.setError("PIN incorrecto");
                                pinEditText.setText(""); // Clear the PIN field
                            }
                        }
                    }
                });
            }
        });

        dialog.show();
    }
    private void showPinDialog8(Context context) {
        // Inflate the custom layout for the dialog
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_pin, null);

        final EditText pinEditText = dialogView.findViewById(R.id.pinEditText);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Ingrese PIN")
                .setView(dialogView)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                pinEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // No need to implement
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // No need to implement
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String enteredPin = s.toString();
                        if (enteredPin.length() == 4) { // Check if PIN has 4 digits
                            if (CORRECT_PIN8.equals(enteredPin)) {
                                // If PIN is correct, start the activity
                                Intent intent = new Intent(SectoresIngreso.this, PiscinasSector8.class);
                                startActivity(intent);
                                dialog.dismiss(); // Close dialog
                            } else {
                                // Show error if PIN is incorrect
                                pinEditText.setError("PIN incorrecto");
                                pinEditText.setText(""); // Clear the PIN field
                            }
                        }
                    }
                });
            }
        });

        dialog.show();
    }

}
