package com.example.esperanza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ConfiguracionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
    }
    public void regresar(View view){
        //creamos el objeto tipo intent, lo mandamos a inicio
        Intent iregresar = new Intent(this,InicioActivity.class);
        startActivity(iregresar);
    }
}