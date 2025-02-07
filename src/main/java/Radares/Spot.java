/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Radares;

import Clientes.Cliente;
import com.mycompany.proyecto2.Mapa.Barco;
import com.mycompany.proyecto2.Mapa.Celdas;
import com.mycompany.proyecto2.Mapa.TipoCelda;
import java.awt.Color;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Tamara
 */
public class Spot implements Serializable{
    
    Cliente cliente; // Recibe un cliente
    Barco barco; // Recibe un barco
    int rango;

    // Constructor
    public Spot(Cliente cliente, Barco barco) {
        this.cliente = cliente;
        this.barco = barco;
        this.rango = 3;
    }
    
    public void EjecutarSpot(){
        
        // Imagen del comandante
        ImageIcon icon = new javax.swing.ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\Imagenes\\ComandanteRadares.png");
        
        // Cantidades de cada barco, tesoro y amenaza
        int cantidadBarcos = 0;
        int cantidadTesoros = 0;
        int cantidadAmenazas = 0;
        
        // Se setea el arreglo de celdas
        Celdas[][] arregloCeldas = barco.getMapa().getArregloCeldas();
        
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

                        // Si se encuentra un barco lo sumara
                        if (celda.isOcupadoXBarco() == true && celda.getNumBarco() != barco.getNumBarco()) {
                            cantidadBarcos++;
                        }

                        // Si se encuentra un barco con el numero 2, lo muestra en pantalla
                        if (celda.getTipoCelda() == TipoCelda.TESORO) {
                            cantidadTesoros++;
                        }

                        // Si se encuentra un barco con el numero 3, lo muestra en pantalla
                        if (celda.getTipoCelda() == TipoCelda.AMENAZA) {
                            cantidadAmenazas++;
                        }
                    }
                }
            }
        }
        
        cliente.getPantalla().write("Hay:\n- Barcos: " + cantidadBarcos + "\n- Tesoros: " + cantidadTesoros + "\n- Amenazas: " + cantidadAmenazas);
        JOptionPane.showMessageDialog(null, "Comandante de Radares: Hay:\n- Barcos: " + cantidadBarcos + "\n- Tesoros: " + cantidadTesoros + "\n- Amenazas: " + cantidadAmenazas, "Informe del spot", JOptionPane.INFORMATION_MESSAGE, icon);
    }
    
}
