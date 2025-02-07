/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clientes;

import com.mycompany.proyecto2.Mapa.Barco;
import com.mycompany.proyecto2.Mapa.Mapa;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Tamara
 */
public class Cliente implements Serializable{
    
    private final String IP = "localhost";
    
    // Para Barcos
    private final int PORT1 = 2003;
    private Socket socket1;
    public ObjectOutputStream salida1;
    public DataOutputStream salidaDatos1;
    
    // Para mensajes
    private final int PORT2 = 2004;
    private Socket socket2;
    public ObjectOutputStream salida2;
    
    // Para Ataques
    private final int PORT3 = 2005;
    private Socket socket3;
    public ObjectOutputStream salida3;
    
    // Para Turnos
    private final int PORT4 = 2006;
    private Socket socket4;
    public DataOutputStream salidaDatos;
    
    PantallaCliente pantalla;
    String nombre;
    Barco barco;
    
    public boolean Activo;
    
    public boolean Turno;
    
     ThreadCliente threadCliente;
     ThreadClienteMensaje threadClienteMensaje;
     ThreadClienteAtaques threadClienteAtaques;
     public ThreadTurnosCliente threadTurnos;
     
     // Constructor
    public Cliente(PantallaCliente pantalla, String nombre) {
        this.pantalla = pantalla;
        this.nombre = nombre;
        this.Activo = false;
        this.Turno = false;
        conectar();        
    }
    
    
   public void conectar(){
        try {
            
            // Barcos
            socket1 =  new Socket(IP, PORT1);
            salida1 =  new ObjectOutputStream(socket1.getOutputStream());
            salidaDatos1 =  new DataOutputStream(socket1.getOutputStream());
            threadCliente = new ThreadCliente(socket1, this);
            
            
            // Mensajes
            socket2 =  new Socket(IP, PORT2);
            salida2 =  new ObjectOutputStream(socket2.getOutputStream());
            threadClienteMensaje  = new ThreadClienteMensaje(socket2, this);
            
            // Ataques
            socket3 =  new Socket(IP, PORT3);
            salida3 =  new ObjectOutputStream(socket3.getOutputStream());
            threadClienteAtaques  = new ThreadClienteAtaques(socket3, this);
            
            // Turnos
            socket4 =  new Socket(IP, PORT4);
            salidaDatos =  new DataOutputStream(socket4.getOutputStream());
            threadTurnos  = new ThreadTurnosCliente(socket4, this);
            
            //Envia el nombre
            salidaDatos1.writeUTF(this.nombre);
            
            // Setea el nombre
            pantalla.lblCliente1.setText(nombre);
                        
            // Inicia los threads de los clientes
            threadCliente.start();
            threadClienteMensaje.start();
            threadClienteAtaques.start();
            threadTurnos.start();
            
        } catch (IOException ex) {

        } 
    }
   
   // Getters

    public PantallaCliente getPantalla() {
        return pantalla;
    }

    public Barco getBarco() {
        return barco;
    }

    public void setBarco(Barco barco) {
        this.barco = barco;
    }
    
    
    
   
}
