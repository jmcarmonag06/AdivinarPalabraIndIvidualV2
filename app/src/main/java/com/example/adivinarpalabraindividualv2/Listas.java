package com.example.adivinarpalabraindividualv2;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Listas extends AppCompatActivity {

    ArrayList<Palabra> palabras = new ArrayList<>();

    private ListView lv1;
    Partida partida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listas);
        Bundle extras = getIntent().getExtras();
        Intent recogerPartida = getIntent();
        partida = (Partida) recogerPartida.getSerializableExtra("partida");

        if (partida != null) {
            palabras = partida.getPalabras();
        } else {
            Toast.makeText(this, "No se ha recogido ninguna palabra", Toast.LENGTH_LONG).show();
        }

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, palabras);

        ListView listaComp = (ListView) findViewById(R.id.listaDePalabras);
        listaComp.setAdapter(adaptador);
        listaComp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //Toast.makeText(Listas.this, palabras.get(position).getDescripcion(), Toast.LENGTH_SHORT).show();

                Intent irAformulario = new Intent(getApplicationContext(), Formulario.class);

                //String palabraF = palabras.get(position).toString();
                //String descripcionF = palabras.get(position).getDescripcion();

                irAformulario.putExtra("partida", partida);
                //irAformulario.putExtra("palabraForm", palabraF);
                //irAformulario.putExtra("descripcionForm", descripcionF);
                irAformulario.putExtra("palabraForm", partida);
                irAformulario.putExtra("posicionForm", position);

                startActivity(irAformulario);

            }

        });
    }

    public void borrarTodas(View view) {
        palabras.clear();
        Intent irListas = new Intent(getApplicationContext(), Listas.class);
        irListas.putExtra("partida", partida);
        startActivity(irListas);
    }

    public void volverL(View view) {
        Intent irMainActivity = new Intent(getApplicationContext(), MainActivity.class);
        irMainActivity.putExtra("partida", partida);
        startActivity(irMainActivity);
    }
}
