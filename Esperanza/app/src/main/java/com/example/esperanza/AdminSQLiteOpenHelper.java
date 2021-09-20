package com.example.esperanza;

import static com.example.esperanza.LoginActivity.nombreusuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

//creamos una clase abstracta sqliteopenhelper
public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    //creamos la base de datos
    public AdminSQLiteOpenHelper(Context context) {
        super(context, "proyecto1.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creamos las tablas, definiendo los atributos de las mismas
        db.execSQL("create table usuarios(usuario text primary key,correo text,nidentidad text,nombre text,apellidos text,telefono text,contrasena text)");
        db.execSQL("create table recarga(cod integer primary key autoincrement, operador text ,telefono text,valor text,saldo text,hora text, usuarioUsuario text references usuarios(usuario) on delete cascade on update cascade)");
        db.execSQL("insert into usuarios (usuario,correo,nidentidad,nombre,apellidos,telefono,contrasena) values ('Hola','hola@gmail.com','1234567895','daniel','fuentes','1234567893','hola')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //borramos las tablas en caso que existan, asi aseguramos estar desistalando
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS recarga");
        onCreate(db);
    }
    public boolean registrar(String correo,String nidentidad, String nombre , String apellidos,String telefono, String usuario, String contrasena)  {
        //creamos un metodo registrar, donde le pasamos todos los parametros que va a recibir para registrarlos en la tabla.
        //con el getwritabledatabase obtenemos la base de datos en modo escritura
        //creamos un objeto de content values, llamado registro, que es quien va a retener los datos.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("correo", correo);
        registro.put("nidentidad", nidentidad);
        registro.put("nombre", nombre);
        registro.put("apellidos", apellidos);
        registro.put("telefono", telefono);
        registro.put("usuario", usuario);
        registro.put("contrasena", contrasena);
        //cuando insertamos guardamos en un objeto tipo Long,mandamos la tabla, y null para en caso que el contentvalues este vacio le mande null
        //si el devuelve un numero diferente a -1 retorne falso, si es diferente retorne true
        Long results = db.insert("usuarios", null, registro);
        if (results==-1){
            return false;
        }else
            return true;
    }
    public boolean registrarRecargas(String operador,String telefono,String valor,String saldo,String hora,String usuarioUsuario)  {
        //creamos un metodo registrarRecargas, donde le pasamos todos los parametros que va a recibir para registrarlos en la tabla.
        //con el getwritabledatabase obtenemos la base de datos en modo escritura
        //creamos un objeto de content values, llamado registrorecarga, que es quien va a retener los datos.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues registrorecarga = new ContentValues();
        registrorecarga.put("operador", operador);
        registrorecarga.put("telefono", telefono);
        registrorecarga.put("valor", valor);
        registrorecarga.put("saldo",saldo);
        registrorecarga.put("hora",hora);
        registrorecarga.put("usuarioUsuario", usuarioUsuario);
        //cuando insertamos guardamos en un objeto tipo Long,mandamos la tabla, y null para en caso que el contentvalues este vacio le mande null
        //si el devuelve un numero diferente a -1 retorne falso, si es diferente retorne true
        Long resultado2 = db.insert("recarga", null, registrorecarga);
        if (resultado2==-1){
            return false;
        }else{
            return true;
        }
    }
    //para login verificar usuario existente
    public boolean verificarUsuario(String usuario){
        //creamos un metodo verificarUsuario que recibe como parametro el usuario
        //creamos una estrucutra de control cursor para recorrer la base de datos y con el rawquery hacemos la consulta
        //nos aseguramos que existe al menos un reigstro con el > 0
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from usuarios where usuario=?",new String[]{usuario});
        if (cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }
    //para login verificar las credenciales
    public boolean verificarCredenciales(String usuario, String contrasena){
        SQLiteDatabase bd = this.getWritableDatabase();
        Cursor cursor = bd.rawQuery("select * from usuarios where usuario=? and contrasena=?",new String[]{usuario,contrasena});
        if (cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }



}