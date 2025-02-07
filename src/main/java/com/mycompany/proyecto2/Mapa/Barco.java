/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2.Mapa;

import java.io.Serializable;

/**
 *
 * @author Tamara
 */
public class Barco implements Serializable {

    Mapa mapa;
    
    // Variables de cada cliente o barco
    String nombre; // Nombre del cliente
    int oro; // Cantidad de oro que posee
    double vida; // La vida del barco
    int x; // la posicion j del cliente
    int y; // la posicion i del barco
    int balasHeavy; // La cantidad de balas Heavy que posee el barco
    int balasLong; // La cantidad de balas Long que posee el cliente
    int minas; // Cantidad de minas que tiene el barco
    
    int radarShort; // La cantidad de radares short que posee el barco
    int radarLong; // Cantidad de radares long que posee el barco
    int spot; // Cantidad de spots que posee el barco
    
    int numBarco; // Numero del barco
    boolean descubrirActivo; // Si el barco decubrio una celda
    
    public boolean descubrirMina; // Si el barco descubrio una mina
    
    // Booleans para saber si son ganadores o no
    public boolean Ganador;
    public boolean Perdedor;
    
    public boolean Muerte;
    
    // CONSTRUCTOR de barco, solo recibe el mapa a usar, y el numero de barco
    public Barco(Mapa mapa, int NumBarco) {
        
        this.oro = 0;
        this.vida = 100;
        this.balasHeavy = 0;
        this.balasLong = 0;
        this.minas = 0;
        this.nombre = "";
        
        this.radarLong = 0;
        this.radarShort = 0;
        this.spot = 0;
        
        this.numBarco = NumBarco;
        this.descubrirActivo = false;
        
        this.Ganador = false;
        this.Perdedor = false;
        
        this.Muerte = false;

        // Inicializa las variables del mapa
        this.mapa = mapa;
        int ArregloPosiciones[] = new int[2];
        ArregloPosiciones = mapa.SetearBarco(NumBarco);
        this.x = ArregloPosiciones[1];
        this.y = ArregloPosiciones[0];
    }
    
    public void MoverBarcoEnMapa(int x, int y){
        
        mapa.getArregloCeldas()[this.y][this.x].setNumBarco(-1);
        mapa.getArregloCeldas()[this.y][this.x].setOcupadoXBarco(false);
        mapa.getArregloCeldas()[y][x].setNumBarco(numBarco);
        mapa.getArregloCeldas()[y][x].setOcupadoXBarco(true);
        this.x = x;
        this.y = y;
    }
    
    // GETTERS
    public Mapa getMapa() {
        return mapa;
    }

    public String getNombre() {
        return nombre;
    }

    public int getOro() {
        return oro;
    }

    public double getVida() {
        return vida;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getBalasHeavy() {
        return balasHeavy;
    }

    public int getBalasLong() {
        return balasLong;
    }

    public int getMinas() {
        return minas;
    }

    public int getNumBarco() {
        return numBarco;
    }

    public boolean isDescubrirActivo() {
        return descubrirActivo;
    }

    public int getRadarShort() {
        return radarShort;
    }

    public int getRadarLong() {
        return radarLong;
    }

    public int getSpot() {
        return spot;
    }
    
    
    // Setters

    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setOro(int oro) {
        this.oro = oro;
    }

    public void setVida(double vida) {
        this.vida = vida;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setBalasHeavy(int balasHeavy) {
        this.balasHeavy = balasHeavy;
    }

    public void setBalasLong(int balasLong) {
        this.balasLong = balasLong;
    }

    public void setMinas(int minas) {
        this.minas = minas;
    }

    public void setNumBarco(int numBarco) {
        this.numBarco = numBarco;
    }

    public void setDescubrirActivo(boolean descubrirActivo) {
        this.descubrirActivo = descubrirActivo;
    }

    public void setRadarShort(int radarShort) {
        this.radarShort = radarShort;
    }

    public void setRadarLong(int radarLong) {
        this.radarLong = radarLong;
    }

    public void setSpot(int spot) {
        this.spot = spot;
    }
}
