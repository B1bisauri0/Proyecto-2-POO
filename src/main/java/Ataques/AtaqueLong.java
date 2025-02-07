/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ataques;

import com.mycompany.proyecto2.Mapa.Barco;
import Clientes.Cliente;
import com.mycompany.proyecto2.Mapa.Celdas;
import com.mycompany.proyecto2.Mapa.Mapa;
import java.io.Serializable;

/**
 *
 * @author Tamara
 */
public class AtaqueLong implements Serializable, IAtaques{

    int atacante; // El numero de barco del atacante
    int atacado; // El numero de barco del atacado
    public double damage; // Dano hecho
    int rango;
    int CantidadAtaques;
    String TipoAtaque; // String del tipo de ataque
    Barco barco;


    public AtaqueLong(int atacante, int CantidadAtaques, Barco barco) {
        this.atacante = atacante;
        this.damage = 10;
        this.rango = 8;
        this.CantidadAtaques = CantidadAtaques;
        this.TipoAtaque = "AtaqueLong";
        this.barco = barco;
        barco.setBalasLong(barco.getBalasLong() - CantidadAtaques);
    }
    
    // Funcion que se encarga de buscar al barco que se va a atacar
    public int BuscarBarco(Barco barco1){
        
        // Bool para verificar si se encontro el barco
        boolean encontroBarco = false;
        
        int barcoEncontrado = -1; // Numero de barco que se encontro
        
        // Se setea un mapa especifico
        Mapa mapa = barco1.getMapa();
        
        // Se setea el arreglo de celdas
        Celdas[][] arregloCeldas = mapa.getArregloCeldas();

        // For para ir codificando el i, j
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
                    int y = barco1.getY() + dir[0];
                    int x = barco1.getX() + dir[1];

                    // Verifica que no se salga del mapa
                    if (y >= 0 && y < 15 && x >= 0 && x < 15) {
                        Celdas celda = arregloCeldas[y][x];
                        
                        // Si se encuentra el barco, se setea el numero de barco y se rompre el ciclo
                        if (celda.isOcupadoXBarco() && celda.getNumBarco() != barco1.getNumBarco()) {
                            barcoEncontrado = celda.getNumBarco();
                            encontroBarco = true;
                            break;
                        }
                    }
                }
                
                // Si encontro al barco rompe el bucle
                if (encontroBarco) {
                    break;
                }
            }

            // Si encontro el barco rompe el ciclo
            if (encontroBarco) {
                break;
            }
        }

        // El atacado toma el valor del barco encontrado
        atacado = barcoEncontrado;
        // Retorna el barco encontrado
        return barcoEncontrado;
    }
    
    // Funcion que realiza el ataque
    @Override
    public void Ataque(Barco barcoAtacado) {
        
        for(int i = 0; i < CantidadAtaques; i++){
            barcoAtacado.setVida(barcoAtacado.getVida() - damage);
        }
    }

    @Override
    public String toString() {
        return "Haz recibido un ataque del barco de " + barco.getNombre();
    }

    // GETTERS
    @Override
    public int getAtacante() {
        return atacante;
    }

    @Override
    public int getAtacado() {
        return atacado;
    }

    @Override
    public int getCantidadAtaque() {
        return CantidadAtaques;
    }

    @Override
    public String getTipoAtaque() {
        return TipoAtaque;
    }

    @Override
    public Barco getBarco() {
        return barco;
    }
    
}
