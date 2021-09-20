package com.example.esperanza;


import static com.example.esperanza.LoginActivity.nombreusuario;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class InicioActivity extends AppCompatActivity {
    private TextView mensajebienvenida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //enlazamos las variables
        mensajebienvenida = findViewById(R.id.nombreUsuarioeditText);

        //creamos objeto de la clase helper,para obtener el nombre del usuario y mostrarlo
        AdminSQLiteOpenHelper traernombre = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db1 = traernombre.getWritableDatabase();
        Cursor tn = db1.rawQuery("select nombre,apellidos from usuarios where usuario='" + nombreusuario + "'", null);
        if (tn != null) {
            tn.moveToFirst();
            do {
                mensajebienvenida.setText("  Bienvenido "+" \n"+tn.getString(0) + " " + tn.getString(1));

            } while (tn.moveToNext());
        }
    }
    public void irrecargas(View view){
        Intent intent = new Intent(this,RecargarActivity.class);
        startActivity(intent);
        finish();
    }
    public void irhistorial(View view){
        Intent intent2 = new Intent(this, MostraruActivity.class);
        startActivity(intent2);

    }
    public void irmiperfil(View view){
        Intent intent3 = new Intent(this,PerfilActivity.class);
        startActivity(intent3);

    }
    public void irconfiguracion(View view){
        Intent intent4 = new Intent(this,ConfiguracionActivity.class);
        startActivity(intent4);

    }
    public void cerrarsesion(View view){
        Intent intent5 = new Intent(this,LoginActivity.class);
        startActivity(intent5);

    }


}