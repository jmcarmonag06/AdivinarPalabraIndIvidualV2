package com.example.adivinarpalabraindividualv2;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "bdPalabras", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        if (!palabraET.equals("") && !descripcionET.equals("")) {
            ContentValues registro = new ContentValues();
            registro.put("nombre", palabraET.getText().toString());
            registro.put("descripcion", descripcionET.getText().toString());

            BaseDeDatos.insert("tablaPalabras", null, registro);
            BaseDeDatos.close();
            borrarCamposPalabra();

            Cursor palabraSQL = BaseDeDatos.rawQuery("select nombre" +
                    " from palabras where nombre = " + palabraET.getText().toString(), null);
            Cursor descripcionSQL = BaseDeDatos.rawQuery("select descripcion" +
                    " from palabras where nombre = " + descripcionET.getText().toString(), null);

            /*if (palabraSQL.moveToFirst() && descripcionSQL.moveToFirst()) {
                //se hace la inserccion
                //palabraSQL.getString(0);
                //descripcionSQL.getString(1);
                //BaseDeDatos.close();
            } else {
                System.out.println("No existe la palabra");
            }*/

            Toast.makeText(this, "La palabra se ha insertado correctamente", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Debes rellenar todos los campos", Toast.LENGTH_LONG).show();
        }
    }

    public void modificarPalabraSQL(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "bdPalabras", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        if (!palabraET.equals("") && !descripcionET.equals("")) {
            ContentValues registro = new ContentValues();
            registro.put("nombre", palabraET.getText().toString());
            registro.put("descripcion", descripcionET.getText().toString());

            int cantidad = BaseDeDatos.update("tablaPalabras", registro,
                    "nombre = " + palabraET.getText().toString(), null);

            BaseDeDatos.close();

            if (cantidad == 1) {
                Toast.makeText(this, "La palabra se ha modificado correctamente", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "La palabra no se ha podido modificar", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Debes rellenar todos los campos", Toast.LENGTH_LONG).show();
        }
    }

    public void borrarPalabraSQL(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "bdPalabras", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        if (!palabraET.equals("") && !descripcionET.equals("")) {
            int cantidad = BaseDeDatos.delete("tablaPalabras", "nombre = " + palabraET, null);
            BaseDeDatos.close();
            borrarCamposPalabra();

            if (cantidad == 1) {
                Toast.makeText(this, "La palabra se ha borrado correctamente", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "La palabra no existe", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Debes rellenar todos los campos", Toast.LENGTH_LONG).show();
        }
    }

}

