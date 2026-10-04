package com.compiladores.educativo.model;

public class Token {

    private int numero;
    private String lexema;
    private String tipo;
    private String valor;

    public Token() {
    }

    public Token(
            int numero,
            String lexema,
            String tipo,
            String valor) {

        this.numero = numero;
        this.lexema = lexema;
        this.tipo = tipo;
        this.valor = valor;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
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
}
