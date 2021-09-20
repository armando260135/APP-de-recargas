package com.example.esperanza;

import static com.example.esperanza.LoginActivity.nombreusuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PerfilActivity extends AppCompatActivity {
    private TextView mostrarCorreo,mostrarNumIdentidad,mostrarTelefono,mostrarNombre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //enlazamos las variables con los widgets del xml
        mostrarCorreo = findViewById(R.id.mostrarcorreo);
        mostrarNumIdentidad = findViewById(R.id.mostrarNumIdentidad);
        mostrarTelefono = findViewById(R.id.mostrarTelefono);
        mostrarNombre = findViewById(R.id.mostrarNombre);

        //creamos el objeto de la clase AdminSQLiteOpenHelper
        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT correo,nidentidad,nombre,apellidos,telefono FROM usuarios WHERE usuario='" + nombreusuario + "'", null);
        if (c != null) {
            c.moveToFirst();
            do {
                mostrarCorreo.setText(" "+" "+c.getString(0));
                mostrarNumIdentidad.setText(c.getString(1));
                mostrarNombre.setText(c.getString(2) + " " + c.getString(3));
                mostrarTelefono.setText("+57 " + c.getString(4));
            } while (c.moveToNext());
        }
    }
    //creamos el metodo para volver al inicio
    public void irCasa(View view){
        Intent intentcasa = new Intent(this,InicioActivity.class);
        startActivity(intentcasa);
    }
}