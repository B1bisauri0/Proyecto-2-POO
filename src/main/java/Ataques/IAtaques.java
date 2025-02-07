package Ataques;


import com.mycompany.proyecto2.Mapa.Barco;
import com.mycompany.proyecto2.Mapa.Mapa;
import java.io.Serializable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author Tamara
 */
public interface IAtaques extends Serializable{
    
    
    // Realiza el ataque de los barcos
    void Ataque(Barco barcoAtacado);
    
    // GETTERS
    int getAtacante();
    
    int getAtacado();
    
    int getCantidadAtaque();
    
    String getTipoAtaque();
    
    Barco getBarco();
}
