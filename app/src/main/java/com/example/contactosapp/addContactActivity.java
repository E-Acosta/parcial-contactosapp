package com.example.contactosapp;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.contactosapp.Modelos.contactoContract;
import com.example.contactosapp.Modelos.contactoDBHelper;

import java.util.Random;

public class addContactActivity extends AppCompatActivity {
    EditText etNombre,etTelefono;
    Spinner grupoSpinner;
    Button ingresarButton, cleanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add);
        grupoSpinner = findViewById(R.id.grupoSpinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.grupo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grupoSpinner.setAdapter(adapter);
        etNombre = findViewById(R.id.etNombre);
        etTelefono = findViewById(R.id.etTelefono);
        registerForContextMenu(etTelefono);
        registerForContextMenu(etNombre);
        ingresarButton = findViewById(R.id.ingresarButton);
        cleanButton = findViewById(R.id.btnClean);
        ingresarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noEmpty();

            }
        });
        cleanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleanValues();
            }
        });

    }

    private void registrarUsuario() {
        contactoDBHelper conexion = new contactoDBHelper(this, "bd_contacto", null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(contactoContract.NOMBRE, etNombre.getText().toString());
        values.put(contactoContract.TELEFONO, etTelefono.getText().toString());
        values.put(contactoContract.GRUPO, grupoSpinner.getSelectedItem().toString());
        long idResultado = db.insert(contactoContract.TABLE_NAME, null, values);
        Toast.makeText(this, "Id registro: " + idResultado, Toast.LENGTH_SHORT).show();
        db.close();
        this.finish();
    }
    private void cleanValues(){
        etNombre.getText().clear();
        etTelefono.getText().clear();
    }

    public void noEmpty() {
        if (TextUtils.isEmpty(etNombre.getText().toString())) {
            etNombre.setError("No hay nombre");
            return;
        }
        if (TextUtils.isEmpty(etTelefono.getText().toString())) {
            etTelefono.setError("No hay telefono");
            return;
        }
        registrarUsuario();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId()==R.id.etTelefono)
        {
            menu.add(0, 911, 0, "Generar numero de telefono");
        }
        if (v.getId()==R.id.etNombre)
        {
            menu.add(0, 912, 0, "Convertir en mayusculas");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 911:
                generateRadomPhone();
                break;
            case 912:
                etNombre.setText(etNombre.getText().toString().toUpperCase());
                break;
        }
        return super.onContextItemSelected(item);
    }

    private  void generateRadomPhone(){
        int num1;
        int set2, set3;
        int iniNum[]= {300,310,320};
        Random generator = new Random();
        num1 = generator.nextInt(2);
        set2 = generator.nextInt(799) + 100;
        set3 = generator.nextInt(7999) + 1000;
        etTelefono.setText(""+iniNum[num1]+set2+set3);
    }
}
