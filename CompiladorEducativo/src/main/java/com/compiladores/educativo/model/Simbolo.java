package com.compiladores.educativo.model;

public class Simbolo {

    private String nombre;
    private String tipo;
    private String valor;
    private int direccion;

    public Simbolo() {
    }

    public Simbolo(
            String nombre,
            String tipo,
            String valor,
            int direccion) {

        this.nombre = nombre;
        this.tipo = tipo;
        this.valor = valor;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getDireccion() {
        return direccion;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }
}
