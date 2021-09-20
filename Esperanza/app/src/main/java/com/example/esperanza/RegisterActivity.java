package com.example.esperanza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private EditText Regcorreo,Regnidentidad,Regnombre,Regapellidos,Regtelefono,Regusuario,Regcontrasena;
    private Button Reg,Can;

    //creamos una variable de tipo de la clase AdminHelper y a su ves de la clase Register.
    AdminSQLiteOpenHelper  db;
    RegisterActivity objValidar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Regcorreo = findViewById(R.id.RegCorreo);
        Regnidentidad = findViewById(R.id.RegIdentidad);
        Regnombre =  findViewById(R.id.RegNombre);
        Regapellidos =  findViewById(R.id.RegApellidos);
        Regtelefono =  findViewById(R.id.RegTelefono);
        Regusuario=  findViewById(R.id.RegUser);
        Regcontrasena = findViewById(R.id.RegPass);
        Reg =  findViewById(R.id.buttonRegistrar);
        Can =  findViewById(R.id.buttonCancelar);
        objValidar = new RegisterActivity();
    }
    //metodo para validar el correo electronico, y recibe la cadena del correo.
    public  boolean isEmail(String cadena) {
        boolean resultado;
        if (Patterns.EMAIL_ADDRESS.matcher(cadena).matches()) {
            resultado = true;
        } else {
            resultado = false;
        }

        return resultado;
    }
    //metodo para registrar los datos del usuario
    public void registrar(View v) {
        db = new AdminSQLiteOpenHelper(this);
        String regcorreo = Regcorreo.getText().toString();
        String regnidentidad = Regnidentidad.getText().toString();
        String regnombre = Regnombre.getText().toString();
        String regapellidos = Regapellidos.getText().toString();
        String regtelefono = Regtelefono.getText().toString();
        String regusuario = Regusuario.getText().toString();
        String regcontrasena = Regcontrasena.getText().toString();

        if( regcorreo.equals("") ||regnidentidad.equals("") || regnombre.equals("") || regapellidos.equals("")  || regtelefono.equals("") || regusuario.equals("") || regcontrasena.equals("")){
            Toast.makeText(this, "Los campos estan vacios", Toast.LENGTH_SHORT).show();
        }else{
            Boolean verificar = db.verificarUsuario(regusuario);
            if (objValidar.isEmail(Regcorreo.getText().toString())){
                if (Regnidentidad.length()==10){
                    if (Regtelefono.length()==10){
                        if (verificar==false){
                            Boolean insert = db.registrar(regcorreo,regnidentidad,regnombre,regapellidos,regtelefono,regusuario,regcontrasena);
                            if (insert==true){
                                Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                                Regcorreo.setText("");
                                Regnidentidad.setText("");
                                Regnombre.setText("");
                                Regapellidos.setText("");
                                Regtelefono.setText("");
                                Regusuario.setText("");
                                Regcontrasena.setText("");
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(RegisterActivity.this, "Usuario no registrado", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(RegisterActivity.this, "Usuario Existente", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(this, "Ingrese un numero de telefono valido", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Ingrese un numero de documento valido", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Ingrese un Correo valido", Toast.LENGTH_SHORT).show();
            }

        }
    }
    //metodo para cancelar el registro e ir a la zona de login
    public void cancelarRegi(View view){
        Intent  i3 = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(i3);
    }
}
