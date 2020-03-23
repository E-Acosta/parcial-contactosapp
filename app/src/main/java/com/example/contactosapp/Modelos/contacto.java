package com.example.contactosapp.Modelos;

public class contacto {
    private int id;
    private String nombre;
    private String telefono;
    private String grupo;


    public int getId() {
        return id;
    }

    public contacto(int id, String nombre, String telefono, String grupo) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.grupo = grupo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getGrupo() {
        return grupo;
    }

}
