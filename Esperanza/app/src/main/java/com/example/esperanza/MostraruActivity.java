package com.example.esperanza;

import static com.example.esperanza.LoginActivity.nombreusuario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MostraruActivity extends AppCompatActivity {
    private ListView l1;
    private TextView hora,mostrar_hora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostraru);

        l1 = findViewById(R.id.lista1);
        ArrayList<String> items = new ArrayList<>();

        //creamos un objeto de la clase AdminSQLiteOpenHelper
        //en el if nos movemos al ultimo de la base de datos
        AdminSQLiteOpenHelper mrecargas = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase bd = mrecargas.getWritableDatabase();
        Cursor fila= bd.rawQuery("select hora,operador,telefono,valor from recarga where usuarioUsuario='" + nombreusuario + "'",null);
        if(fila.moveToFirst()){
            do{
                items.add("             Recarga "+" "+fila.getString(0)+"\n \nOperador Telefonico: "+fila.getString(1)+"\n \n"+
                        "Telefono: +57 "+fila.getString(2)+"\n \n"+"Valor De La Recarga: $ "+fila.getString(3)+"\n");
            }while (fila.moveToNext());
        }
        //creamos un objeto de la clase Arrayadapter
        ArrayAdapter<String> adaptador=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        l1.setAdapter(adaptador);
        //llamamos al metodo setOnItemClickListener
        l1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView adapterView, View view, int i, long l) {
                final int posicion=i;
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MostraruActivity.this);
                dialogo1.setTitle("Importante");
                    dialogo1.setMessage("¿ Quieres Eliminar Esta Recarga ?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        deleteItemDB();
                        Toast.makeText(MostraruActivity.this, "Eliminado", Toast.LENGTH_SHORT).show();
                        items.remove(i);
                        adaptador.notifyDataSetChanged();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                    }
                });
                dialogo1.show();
                return false;
            }
        });
    }
    public void irhome(View view){
        Intent intenthome = new Intent(MostraruActivity.this,InicioActivity.class);
        startActivity(intenthome);
        finish();
    }
    public void deleteItemDB(){
        AdminSQLiteOpenHelper dbHelper2 = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db4 = dbHelper2.getWritableDatabase();
        //db.execSQL("DELETE FROM " + TABLE_NOMBRE + " WHERE " + COLUMN_DIRECCION + "='" + itemID + "'");
        db4.execSQL("DELETE FROM recarga WHERE usuarioUsuario='" + nombreusuario + "'");
        db4.close();
    }
}