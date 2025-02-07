/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyecto2;

import Clientes.PantallaCliente;
import Clientes.PantallaInicioClientes;
import Servidor.PantallaServidor1;
import Servidor.PantallaServidorInicializador;
import com.mycompany.proyecto2.Mapa.Mapa;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tamara
 */
public class Proyecto2 implements Serializable{

    public static void main(String[] args) {
        
        PantallaServidorInicializador pantallaS = new PantallaServidorInicializador();
        pantallaS.setVisible(true);

        
         PantallaInicioClientes pantalla = new PantallaInicioClientes();
         pantalla.setVisible(true);
        
         PantallaInicioClientes pantalla1 = new PantallaInicioClientes();
         pantalla1.setVisible(true);
    }
}
