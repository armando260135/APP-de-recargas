package com.example.esperanza;


import static com.example.esperanza.LoginActivity.nombreusuario;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class RecargarActivity extends AppCompatActivity {
    private EditText Regoperador, Regtelefonor, Regvalor;
    private TextView saldo_disponible, texto_saldo;
    private Button recar;
    private ImageButton backhome;
    private Spinner spinneroperadoras;
    private String selecionoperadora;
    AdminSQLiteOpenHelper db;
    private String retorno;

    public String getRetorno() {
        return retorno;
    }

    public void setRetorno(String retorno) {
        this.retorno = retorno;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recargar);
        db = new AdminSQLiteOpenHelper(this);

        spinneroperadoras = findViewById(R.id.spinner1);
        saldo_disponible = findViewById(R.id.mision);
        texto_saldo = findViewById(R.id.mostito);
        Regtelefonor = findViewById(R.id.Regnumero_tel);
        Regvalor = findViewById(R.id.Regvalor_recarga);
        recar = findViewById(R.id.buttonRecargar);
        backhome = findViewById(R.id.backhome);
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        Timer timer = new Timer();
        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                saldo_disponible.setText("1000000");
                /*
                AdminSQLiteOpenHelper actua = new AdminSQLiteOpenHelper(getApplicationContext());
                SQLiteDatabase bd = actua.getWritableDatabase();

                String saldo = saldo_disponible.getText().toString();
                ContentValues registrar = new ContentValues();
                registrar.put("saldo", saldo);
                bd.update("recarga", registrar, "usuarioUsuario='" + nombreusuario + "'", null);
                bd.close();*/
            }
        };
        //timer.schedule(tarea,100000,1000);
        Calendar date = Calendar.getInstance();
        date.set(Calendar.HOUR_OF_DAY, 00);
        date.set(Calendar.MINUTE, 00);
        date.set(Calendar.SECOND, 00);
        timer.schedule(tarea, date.getTime());
        // textView is the TextView view that should display it
        // hora.setText(currentDateTimeString);
        if (obtenerSaldo() == false) {
            saldo_disponible.setText("1000000");
        } else {
            String resultado = consultarSaldo();
            saldo_disponible.setText(resultado);
        }
        String[] operadoras = {"   Seleccciona el Operador   ", "Movistar", "Tigo", "Claro", "Wom", "Comcel", "Movil Exito", "Virgin", "Flash Movile"};
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, R.layout.spinner_operadora, operadoras);
        spinneroperadoras.setAdapter(adapter);
        spinneroperadoras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (parent.getItemAtPosition(i).toString().equals("   Seleccciona el Operador  ")) {
                    Toast.makeText(RecargarActivity.this, "Ingrese un Operador", Toast.LENGTH_SHORT).show();
                } else {
                    setRetorno(parent.getItemAtPosition(i).toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        recar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String regoperador = getRetorno();
                String regtelefono = Regtelefonor.getText().toString();
                String regvalor = Regvalor.getText().toString();
                String nombreu2 = LoginActivity.nombreusuario;

                if (regtelefono.equals("") || regvalor.equals("")) {
                    Toast.makeText(RecargarActivity.this, "Los campos estan vacios", Toast.LENGTH_SHORT).show();
                } else {
                    if (regoperador.equals("   Seleccciona el Operador   ")) {
                        Toast.makeText(RecargarActivity.this, "Seleccione un Operador", Toast.LENGTH_SHORT).show();
                    } else {
                        if (Regtelefonor.length() == 10) {
                            double saldo = Double.parseDouble(saldo_disponible.getText().toString());
                            saldo = saldo - (Double.parseDouble(Regvalor.getText().toString()));
                            String saldo2 = String.valueOf(saldo);
                            if (saldo > 0) {
                                Boolean insertarr = db.registrarRecargas(regoperador, regtelefono, regvalor, saldo2, currentDateTimeString, nombreu2);
                                saldo_disponible.setText(saldo2);
                                if (insertarr == true) {
                                    Toast.makeText(RecargarActivity.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                                    Regtelefonor.setText("");
                                    Regvalor.setText("");
                                } else {
                                    Toast.makeText(RecargarActivity.this, "No se pudo realizar la Recarga", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(RecargarActivity.this, "Saldo insuficiente", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RecargarActivity.this, "Ingrese un numero de telefono de 10 digitos", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }
        });
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent irhome = new Intent(RecargarActivity.this, InicioActivity.class);
                startActivity(irhome);
            }
        });
    }

    public boolean obtenerSaldo() {
        AdminSQLiteOpenHelper saldito1 = new AdminSQLiteOpenHelper(getApplicationContext());
        String code = "";
        SQLiteDatabase bd = saldito1.getWritableDatabase();
        Cursor fila = bd.rawQuery(
                "select saldo from recarga where usuarioUsuario='" + nombreusuario + "'", null);

        if (fila.moveToFirst()) {
            do {
                code = fila.getString(0);

            } while (fila.moveToNext());
        }
        if (code.equals("")) {
            return false;
        } else
            return true;
    }

    public String consultarSaldo() {
        AdminSQLiteOpenHelper saldito2 = new AdminSQLiteOpenHelper(getApplicationContext());
        String code = "";
        SQLiteDatabase bd = saldito2.getWritableDatabase();
        Cursor fila = bd.rawQuery(
                "select saldo from recarga where usuarioUsuario='" + nombreusuario + "'", null);
        if (fila.moveToFirst()) {
            do {
                code = fila.getString(0);

            } while (fila.moveToNext());

            //
            // tvMostrar.setText(String.valueOf(saldo));
        }
        return code;
    }
}


