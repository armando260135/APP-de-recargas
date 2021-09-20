package com.example.esperanza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText usuarioob,contrasenaob;
    //creamos una variable del tipo de la clase AdminSQLiteOpenHelper
    AdminSQLiteOpenHelper db;
    //creamos una variable statica donde capturaremos el nombre del usuario y poder llevarlo a mostrarlo al inicio
    public static  String nombreusuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //enlazamos las variables
        usuarioob = findViewById(R.id.editTextTextUser);
        contrasenaob = findViewById(R.id.editTextTextPassword);

        db = new AdminSQLiteOpenHelper(getApplicationContext());
        SQLiteDatabase data = db.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("correo", "hoola@gmail.com");
        registro.put("nidentidad", "1234567894");
        registro.put("nombre", "daniel");
        registro.put("apellidos", "fuentes");
        registro.put("telefono", "1234567897");
        registro.put("usuario", "Wposs");
        registro.put("contrasena", "wposs");
        data.insert("usuarios",null,registro);
    }
    public void ingresar(View view){
        //creamos un metodo ingresar, que se ejecuta con el boton que tiene el onclick ingresar
        db = new AdminSQLiteOpenHelper(this);
        String usuario= usuarioob.getText().toString();
        String contrasena= contrasenaob.getText().toString();
        if (usuario.equals("") || contrasena.equals("")) {
            Toast.makeText(LoginActivity.this, "Todos los campos deben ser llenados", Toast.LENGTH_SHORT).show();
        }else{
            Boolean verificaup = db.verificarCredenciales(usuario,contrasena);
            if (verificaup==true){
                nombreusuario=usuario;
                Toast.makeText(LoginActivity.this, "datos correctos!", Toast.LENGTH_SHORT).show();
                Intent intentingresar= new Intent(this,InicioActivity.class);
                startActivity(intentingresar);
                usuarioob.setText("");
                contrasenaob.setText("");
            }else{
                Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
        }

    }

    //metodo para llevar a la zona de registro
   /* public void irregistrar(View view){
        Intent irregistrar = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(irregistrar);
        finish();


    }*/
    public void irRegistrar(View v){
        Intent irregistrar = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(irregistrar);
        finish();

    }
}
