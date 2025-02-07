/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ataques;

import com.mycompany.proyecto2.Mapa.Barco;
import com.mycompany.proyecto2.Mapa.Mapa;

/**
 *
 * @author Tamara
 */
public class Minas implements IAtaques{

    
    int x; // x de donde se encuentra la mina
    int y; // y de donde se coloca la mina
    Barco barcoAtacante; // Barco del atacante
    String TipoAtaque; // Tipo de ataque
    int CantidadAtaques; // Cantidad Ataques

    public Minas(int x, int y, Barco barcoAtacante) {
        this.x = x;
        this.y = y;
        this.barcoAtacante = barcoAtacante;
        this.TipoAtaque = "AtaqueMina";
        this.CantidadAtaques = 1;
        barcoAtacante.setMinas(barcoAtacante.getMinas() - 1);
    }
    
    // Verifica que no haya una mina en ese sitio
    public boolean HayMinaExistente(Mapa mapa){
        
        // Si lo hay, retorna true
        if(mapa.getArregloCeldas()[y][x].isMina() == true){
            return true;
        }
        
        // Si no, retorna false
        else{
            return false;
        }
    }
    
    @Override
    public void Ataque(Barco barcoAtacante) {
        
        Mapa mapa = barcoAtacante.getMapa();
        
        // Si se puede colocar la mina, la coloca
        if(HayMinaExistente(mapa) == false){
            mapa.getArregloCeldas()[y][x].setNumBarcoMina(barcoAtacante.getNumBarco());
            mapa.getArregloCeldas()[y][x].setMina(true);
        }
    }
    
    // GETTERS
    @Override
    public int getAtacante() {
        return x;
    }

    @Override
    public int getAtacado() {
        return y;
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
        return barcoAtacante;
    }
}
