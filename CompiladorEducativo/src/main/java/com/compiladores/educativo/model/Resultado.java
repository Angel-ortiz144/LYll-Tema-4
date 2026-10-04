package com.compiladores.educativo.model;

import java.util.ArrayList;
import java.util.List;

public class Resultado {

    private String mensaje;

    private List<Token> tokens =
            new ArrayList<>();

    private List<String> arbol =
            new ArrayList<>();

    private List<String> errores =
            new ArrayList<>();

    private List<Simbolo> tablaSimbolos =
            new ArrayList<>();

    private String prefija;
    private String infija;
    private String postfija;

    private List<String> triplos =
            new ArrayList<>();

    private List<Cuadruplo> cuadruplos =
            new ArrayList<>();

    private String codigoIntermedio;
    private String codigoOptimizado;
    private String codigoEnsamblador;
    private String codigoMaquina;

    private String optimizacionSuma;
    private String optimizacionResta;
    private String optimizacionMultiplicacion;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public List<String> getArbol() {
        return arbol;
    }

    public List<String> getErrores() {
        return errores;
    }

    public List<Simbolo> getTablaSimbolos() {
        return tablaSimbolos;
    }

    public String getPrefija() {
        return prefija;
    }

    public void setPrefija(String prefija) {
        this.prefija = prefija;
    }

    public String getInfija() {
        return infija;
    }

    public void setInfija(String infija) {
        this.infija = infija;
    }

    public String getPostfija() {
        return postfija;
    }

    public void setPostfija(String postfija) {
        this.postfija = postfija;
    }

    public List<String> getTriplos() {
        return triplos;
    }

    public List<Cuadruplo> getCuadruplos() {
        return cuadruplos;
    }

    public String getCodigoIntermedio() {
        return codigoIntermedio;
    }

    public void setCodigoIntermedio(String codigoIntermedio) {
        this.codigoIntermedio = codigoIntermedio;
    }

    public String getCodigoOptimizado() {
        return codigoOptimizado;
    }

    public void setCodigoOptimizado(String codigoOptimizado) {
        this.codigoOptimizado = codigoOptimizado;
    }

    public String getCodigoEnsamblador() {
        return codigoEnsamblador;
    }

    public void setCodigoEnsamblador(String codigoEnsamblador) {
        this.codigoEnsamblador = codigoEnsamblador;
    }

    public String getCodigoMaquina() {
        return codigoMaquina;
    }

    public void setCodigoMaquina(String codigoMaquina) {
        this.codigoMaquina = codigoMaquina;
    }

    public String getOptimizacionSuma() {
        return optimizacionSuma;
    }

    public void setOptimizacionSuma(String optimizacionSuma) {
        this.optimizacionSuma = optimizacionSuma;
    }

    public String getOptimizacionResta() {
        return optimizacionResta;
    }

    public void setOptimizacionResta(String optimizacionResta) {
        this.optimizacionResta = optimizacionResta;
    }

    public String getOptimizacionMultiplicacion() {
        return optimizacionMultiplicacion;
    }

    public void setOptimizacionMultiplicacion(
            String optimizacionMultiplicacion) {

        this.optimizacionMultiplicacion =
                optimizacionMultiplicacion;
    }
}
