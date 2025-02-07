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
public class Celdas implements Serializable{
    
    private TipoCelda TipoCelda; // El tipo de celda que sea
    private boolean Descubierta; // Si la celda ha sido descubierta o no
    private int i; // i que poseera la celda
    private int j; // j de la celda
    private boolean OcupadoXBarco; // Si esta ocupado por un barco
    int numBarco; // Num de barco que se encuentra
    boolean mina; // Verifica si hay una mina activa en ese espacio o no
    int TipoAmenaza; // Si el tipo de amenaza es 0, entonces es tormenta, si no sera remolino
    
    int numBarcoMina;

    // CONSTRUCTOR
    public Celdas(int i, int j, boolean OcupadoXBarco, TipoCelda TipoCelda, boolean Descubierta) {
        this.TipoCelda = TipoCelda;
        this.Descubierta = Descubierta;
        this.i = i;
        this.j = j;
        this.OcupadoXBarco = OcupadoXBarco;
        numBarco = -1;
        this.mina = false;
        this.numBarcoMina = -1;
        
        if(TipoCelda == TipoCelda.AMENAZA){
            this.TipoAmenaza = (int)(Math.random() * 2);
        }
        
    }

    // GETTERS
    public TipoCelda getTipoCelda() {
        return TipoCelda;
    }

    public boolean isDescubierta() {
        return Descubierta;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public boolean isOcupadoXBarco() {
        return OcupadoXBarco;
    }

    public int getNumBarco() {
        return numBarco;
    }

    public boolean isMina() {
        return mina;
    }

    public int getTipoAmenaza() {
        return TipoAmenaza;
    }

    public int getNumBarcoMina() {
        return numBarcoMina;
    }
    
    
    
    // SETTERS
    public void setTipoCelda(TipoCelda TipoCelda) {
        this.TipoCelda = TipoCelda;
    }

    public void setDescubierta(boolean Descubierta) {
        this.Descubierta = Descubierta;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public void setOcupadoXBarco(boolean OcupadoXBarco) {
        this.OcupadoXBarco = OcupadoXBarco;
    }

    public void setNumBarco(int numBarco) {
        this.numBarco = numBarco;
    }

    public void setMina(boolean mina) {
        this.mina = mina;
    }

    public void setTipoAmenaza(int TipoAmenaza) {
        this.TipoAmenaza = TipoAmenaza;
    }

    public void setNumBarcoMina(int numBarcoMina) {
        this.numBarcoMina = numBarcoMina;
    }
    
    
}
