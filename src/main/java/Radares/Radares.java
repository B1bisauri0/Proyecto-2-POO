/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Radares;

import Clientes.Cliente;
import com.mycompany.proyecto2.Mapa.Barco;
import com.mycompany.proyecto2.Mapa.Celdas;
import com.mycompany.proyecto2.Mapa.Mapa;
import java.awt.Color;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tamara
 */
public class Radares extends Thread implements Serializable{
    
    int rango; // Rango de vista
    int tiempo; // Tiempo
    Barco barco;
    Mapa mapa;
    Cliente cliente;

    public Radares(int rango, Barco barco, Cliente cliente) {
        this.rango = rango;
        this.barco = barco;
        this.mapa = barco.getMapa();
        this.tiempo = 20;
        this.cliente = cliente;
    }
    
    @Override
    public void run(){
        
        // Se setea el arreglo de celdas
        Celdas[][] arregloCeldas = mapa.getArregloCeldas();
        
        // For del tiempo
        for(int z = 0; z <= tiempo; z++){

            // SLEEP
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Radares.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // --------------- For para setearlo -------------------------
            for (int i = 0; i < rango; i++) {
                for (int j = 0; j < rango; j++) {

                    // Verificar las 4 direcciones con una matriz de enteros
                    int[][] direcciones = {
                        {i, j},    // Abajo derecha
                        {i, -j},   // Abajo izquierda
                        {-i, j},   // Arriba derecha
                        {-i, -j}   // Arriba izquierda
                    };

                    // Recorre la matriz de celdas
                    for (int[] dir : direcciones) {
                        int y = barco.getY() + dir[0];
                        int x = barco.getX() + dir[1];

                        // Verifica que no se salga del mapa
                        if (y >= 0 && y < 15 && x >= 0 && x < 15) {
                            Celdas celda = arregloCeldas[y][x];

                            // Si se encuentra un barco con el numero 1, lo muestra en pantalla
                            if (celda.isOcupadoXBarco() == true && celda.getNumBarco() == 1) {
                                cliente.getPantalla().tableroLabels[y][x].setBackground(new Color(0,0,255));
                            }
                            
                            // Si se encuentra un barco con el numero 2, lo muestra en pantalla
                            if (celda.isOcupadoXBarco() == true && celda.getNumBarco() == 2) {
                                cliente.getPantalla().tableroLabels[y][x].setBackground(new Color(120,40,140));
                            }
                            
                            // Si se encuentra un barco con el numero 3, lo muestra en pantalla
                            if (celda.isOcupadoXBarco() == true && celda.getNumBarco() == 3) {
                                cliente.getPantalla().tableroLabels[y][x].setBackground(new Color(140,0,75));
                            }
                            
                            // Si se encuentra un barco con el numero 4, lo muestra en pantalla
                            if (celda.isOcupadoXBarco() == true && celda.getNumBarco() == 4) {
                                cliente.getPantalla().tableroLabels[y][x].setBackground(new Color(141,182,0));
                            }
                        }
                    }
                }
            }
            
            // ---------------------------------------------------------------
        }
        // Refresca la pantalla
        cliente.getPantalla().RefrescarTablero(mapa, barco);
        
    }
}
