package com.example.adivinarpalabraindividualv2;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Formulario extends AppCompatActivity {

    ArrayList<Palabra> palabras = new ArrayList<>();
    int posicionF;
    Partida partida;
    TextView palabraET;
    TextView descripcionET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formularios);
        Bundle extras = getIntent().getExtras();
        Intent recogerPartida = getIntent();
        partida = (Partida) recogerPartida.getSerializableExtra("partida");

        if (partida != null) {
            palabras = partida.getPalabras();
        } else {
            Toast.makeText(this, "No se ha recogido ninguna palabra", Toast.LENGTH_LONG).show();
        }

        Bundle datos = getIntent().getExtras();
        /*String palabraF = datos.getString("palabraForm");
        String descripcionF = datos.getString("descripcionForm");*/
        posicionF = datos.getInt("posicionForm");

        palabraET = (TextView) findViewById(R.id.palabraF);
        palabraET.setText(partida.getPalabras().get(posicionF).toString());

        descripcionET = (TextView) findViewById(R.id.descripcionF);
        descripcionET.setText(partida.getPalabras().get(posicionF).getDescripcion());

    }

    public void borrarPalabra(View view) {
        palabras.remove(posicionF);
        borrarCamposPalabra();
    }

    public void modificarPalabra(View view) {
        if (!palabraET.equals("") && !descripcionET.equals("")) {

            palabras.get(posicionF).setNombre(palabraET.getText().toString());
            palabras.get(posicionF).setDescripcion(descripcionET.getText().toString());
            Toast.makeText(this, "La palabra " + palabraET +
                    " se modifico correctamente.", Toast.LENGTH_LONG).show();
            borrarCamposPalabra();
        } else if (palabraET.equals("") && descripcionET.equals("")) {
            Toast.makeText(this, "No se puede modificar una palabra que " +
                    "previamente se ha borrado", Toast.LENGTH_LONG).show();
        }
    }

    public void borrarCamposPalabra() {
        palabraET.setText("");
        descripcionET.setText("");
    }

    public void volver(View view) {
        Intent irListas = new Intent(getApplicationContext(), Listas.class);
        irListas.putExtra("partida", partida);
        startActivity(irListas);
    }

    public void insertarPalabraSQL(View view) {

    }

    public void modificarPalabraSQL(View view) {

    }

    public void borrarPalabraSQL(View view) {

    }

}

