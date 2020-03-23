package com.example.contactosapp.Modelos;

public class contactoContract {


    public static final String TABLE_NAME = "contacto";
    public static final String ID = "id";
    public static final String NOMBRE = "nombre";
    public static final String TELEFONO = "telefono";
    public static final String GRUPO = "grupo";
    static final String CREAR_TABLA_CONTACTO = "CREATE TABLE contacto(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NOMBRE + " TEXT," +TELEFONO+" TEXT,"+ GRUPO + " TEXT)";
}
