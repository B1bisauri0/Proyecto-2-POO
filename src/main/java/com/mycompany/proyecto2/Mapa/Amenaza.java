/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2.Mapa;

import Clientes.PantallaCliente;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author Tamara
 */
public class Amenaza extends Thread implements Serializable{
    
    JLabel amenazaImagen;
    PantallaCliente pantalla;
    int damage;
    Barco barco;

    // Constructor amenaza
    public Amenaza(PantallaCliente pantalla, Barco barco, Celdas celda) {
        this.pantalla = pantalla;
        this.damage = (int)(Math.random() * 100);
        this.amenazaImagen = pantalla.lblAmenaza;
        this.barco = barco;
        
        if(celda.getTipoAmenaza() == 0){
            pantalla.lblAmenaza.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\Imagenes\\Tormenta.png"));
        }
        
        else {
            pantalla.lblAmenaza.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\Imagenes\\Remolino.png"));
        }
        
        barco.setVida(barco.getVida() - damage);
        
    }
    
    
    @Override
    public void run() {
    
        // Setea la imagen como visible
        pantalla.lblAmenaza.setVisible(true);
        // Baja la vida del barco
        
        
        // Espera 10 segundos
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Amenaza.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Setea la amenaza como invisible
        pantalla.lblAmenaza.setVisible(false);
        
    }
    
}
