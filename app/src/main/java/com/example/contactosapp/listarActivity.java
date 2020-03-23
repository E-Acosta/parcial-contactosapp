package com.example.contactosapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;

import com.example.contactosapp.Modelos.contacto;
import com.example.contactosapp.Modelos.contactoDBHelper;
import com.example.contactosapp.Modelos.contactoContract;

import java.util.ArrayList;

public class listarActivity extends AppCompatActivity {
    contactoDBHelper con;
    SQLiteDatabase db;
    RecyclerView recyclerView;
    recyclerViewAdapter adapter;
    Button btnDrop;
    private static final String TAG = "listarActivity";
    private ArrayList<contacto> contactos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Log.d(TAG, "Created");
        consultarSql();
        initRecyclerView();
        registerForContextMenu(recyclerView);
        btnDrop = findViewById(R.id.btnDropDb);
        btnDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSqlAll();
            }
        });

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case 123:
                int idEliminar = contactos.get(adapter.getPosition()).getId();
                deleteSql(idEliminar);
                //Toast.makeText(this, "Eliminar", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void deleteSql(int ID) {
        con = new contactoDBHelper(this, "bd_contacto", null, 1);
        SQLiteDatabase db = con.getWritableDatabase();
        long idResultado = db.delete(contactoContract.TABLE_NAME, "id = ?", new String[]{String.valueOf(ID)});
        Toast.makeText(this, "Constactos Eliminados:" + idResultado, Toast.LENGTH_SHORT).show();
        db.close();
        this.finish();
        startActivity(new Intent(this, opcionesActivity.class));
    }
    private void deleteSqlAll() {
        con = new contactoDBHelper(this, "bd_contacto", null, 1);
        SQLiteDatabase db = con.getWritableDatabase();
        long idResultado = db.delete(contactoContract.TABLE_NAME, "id != ?", new String[]{"999999999999999"});
        Toast.makeText(this, "Contactos Eliminados:" + idResultado, Toast.LENGTH_SHORT).show();
        db.close();
        this.finish();
        startActivity(new Intent(this, opcionesActivity.class));
    }

    private void consultarSql() {
        con = new contactoDBHelper(this, "bd_contacto", null, 1);
        Log.d(TAG, "consultarSql: iniciado");
        try {
            SQLiteDatabase db = con.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + contactoContract.TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    int id = cursor.getInt(cursor.getColumnIndex(contactoContract.ID));
                    String nombre = cursor.getString(cursor.getColumnIndex(contactoContract.NOMBRE));
                    String telefono = cursor.getString(cursor.getColumnIndex(contactoContract.TELEFONO));
                    String grupo = cursor.getString(cursor.getColumnIndex(contactoContract.GRUPO));
                    cursor.moveToNext();
                    contactos.add(new contacto(id, nombre, telefono, grupo));
                }
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView");
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new recyclerViewAdapter(contactos, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //Toast.makeText(listarActivity.this, "Text Changed", Toast.LENGTH_SHORT).show();
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

}
