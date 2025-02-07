/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2.Mapa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Tamara
 */
public class Mapa implements Serializable{
    
    Celdas ArregloCeldas[][]; // Arreglo de celdas que poseera el mapa
    TipoCelda tipo;
    
    // Porcentajes de cada cosa de las celdas
    double PorVacia;
    double PorTesoro;
    double PorAmenaza;
    
    int i; // Alto de mapa
    int j; // Ancho de mapa
    int TamanoMapa; // Tamano total del mapa
    

    // Constructor para el servidor
    public Mapa(double PorVacia, double PorTesoro, double PorAmenaza, int i, int j) {
        
        this.PorVacia = PorVacia;
        this.PorTesoro = PorTesoro;
        this.PorAmenaza = PorAmenaza;
                        
        this. i = i;
        this.j = j;
        TamanoMapa = i * j;
                
        // Tamano del mapa
        ArregloCeldas = new Celdas[i][j];
        
        // Le setea la cantidad de valores por mapa
        int CantidadVacia =  (int)((TamanoMapa * PorVacia));
        int CantidadTesoro = (int)((TamanoMapa * PorTesoro));
        int CantidadPorAmenaza = (int)((TamanoMapa * PorAmenaza));
        int CantidadMercado = (i*j) - (CantidadVacia + CantidadTesoro + CantidadPorAmenaza); 
                
        // Crea un array list del tipo de celda
        ArrayList<TipoCelda> TiposDeCelda = new ArrayList<>();
        
        // Va agregando al array list los tipos de celda
        for(int n = 0; n < CantidadVacia; n++){
            TiposDeCelda.add(TipoCelda.VACIA);
        }
        
        for(int n = 0; n < CantidadTesoro; n++){
            TiposDeCelda.add(TipoCelda.TESORO);
        }
        
        for(int n = 0; n < CantidadPorAmenaza; n++){
            TiposDeCelda.add(TipoCelda.AMENAZA);
        }
        
        for(int n = 0; n < CantidadMercado; n++){
            TiposDeCelda.add(TipoCelda.MERCADO);
        }
        
        Collections.shuffle(TiposDeCelda);
        int index = 0;
        
        // For para inicializar el mapa
        for(int n = 0; n < i; n++){
            for(int m = 0; m < j; m++){
                this.ArregloCeldas[n][m] = new Celdas(n, m, false, TiposDeCelda.get(index), false);
                index++;
            }
        }
    }

    // Funcion que se encarga de contar los barcos que hay en el mapa
    public int BarcosVivos(){
        
        int BarcosVivos = 0; // Contador de la cantidad de barcos
        
        // For para recorrer el mapa
        for(int n = 0; n < i; n++){
            for(int m = 0; m < j; m++){
                
                // Si encuentra un barco, lo suma
                if(ArregloCeldas[n][m].isOcupadoXBarco()){
                    BarcosVivos++;
                }
            }
        }
        
        return BarcosVivos;
        
    }
    
    // Setear el barco en una posicion i,j del mapa
    public int[] SetearBarco(int numBarco){
        
        // Booleano que verificara si se sigue el cliclo
        boolean ciclo = true;
        int arregloARetornar[] = new int[2];
        int randomI = 0;
        int randomJ = 0;
        
        // Entra en un ciclo hasta que encuentre la posicion para el barco
        while(ciclo) {
            
            // Lo hace con unas posiciones con dos valores, 0 y 15, que es el tamano del mapa
            int posiciones[] = {0, 14};
            // Hace el random para encontrar los indices del arreglo de posiciones
            randomI = (int)(Math.random() * 2);
            randomJ = (int)(Math.random() * 2);
            

            // Si encuentra el espacio para el barco, lo inserta alli y rompe el ciclo
            if(ArregloCeldas[posiciones[randomI]][posiciones[randomJ]].isOcupadoXBarco() == false){
                ArregloCeldas[posiciones[randomI]][posiciones[randomJ]].setOcupadoXBarco(true);
                ArregloCeldas[posiciones[randomI]][posiciones[randomJ]].setNumBarco(numBarco);
                numBarco++;
                ciclo = false;
                arregloARetornar[0] = posiciones[randomI];
                arregloARetornar[1] = posiciones[randomJ];
            }
        }
        return arregloARetornar;
    }
    
    //Funcion para setear los barcos en el mapa con un (x, y) y un num de Barco
    public void SetearBarcosEnMapa(int x, int y, int NumBarco){
        
        // For para recorrer el mapa hasta encontrar el numero de barco y quitarlo
        for(int i = 0; i < 15; i++){
            for(int j = 0; j < 15; j++){
                
                // Si se encuentra el barco, se setea como -1 y y la celda ya no
                // estara ocupada del barco
                if(ArregloCeldas[i][j].numBarco == NumBarco){
                    ArregloCeldas[i][j].setNumBarco(-1);
                    ArregloCeldas[i][j].setOcupadoXBarco(false);
                }
            }
        }
        
        // Setea el numero del barco al mapa
        ArregloCeldas[y][x].setNumBarco(NumBarco);
        ArregloCeldas[y][x].setOcupadoXBarco(true);
        
    }
    
    // Funcion que se encarga de revisar cuantas celdas de oro hay descubiertas para asi revisar si el juego acabo
    public boolean CeldasOroDescubiertas(){
        
        // Contadores
        int contador = 0;
        int contadorDescubiertas = 0;
        
        // for que recorre el arreglo para contar las celdas con tesoro
        for(int i = 0; i < this.i; i++){
            for (int j = 0; j < this.j; j++){
                
                // Si en la celda hay tesoro
                if(ArregloCeldas[i][j].getTipoCelda() == TipoCelda.TESORO){
                    contador++;
                }
            }
        }
        
        // for que recorre el arreglo para ver si esa misma cantidad de celdas con tesoro estan decubiertas
        for(int i = 0; i < this.i; i++){
            for (int j = 0; j < this.j; j++){
                
                // Si en la celda hay tesoro y esta descubierta
                if(ArregloCeldas[i][j].getTipoCelda() == TipoCelda.TESORO && ArregloCeldas[i][j].isDescubierta() == true){
                    contadorDescubiertas++;
                }
            }
        }
        
        // Si al final todas las celdas con oro estan descubiertas
        if(contador == contadorDescubiertas){
            return true;
        }
        
        // Si no
        else{
            return false;
        }
        
    }
    
    
    // Getters

    public Celdas[][] getArregloCeldas() {
        return ArregloCeldas;
    }
    
    
}
