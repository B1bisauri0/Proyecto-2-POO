/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clientes;

import Ataques.IAtaques;
import com.mycompany.proyecto2.Mapa.Mapa;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Tamara
 */
public class ThreadTurnosCliente extends Thread implements Serializable{
    
    boolean isrunnig = true;
    private Socket socket;
    private Cliente cliente;
    private DataInputStream entrada;
    public boolean Inicio;
    public boolean Turno;

    public ThreadTurnosCliente(Socket socket, Cliente cliente) {
        try {
            this.socket = socket;
            this.cliente = cliente;
            this.Inicio = false;
            this.Turno = false;
            entrada = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            //Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run(){
        
        boolean boolEntrada;
        
        try {
            // Lee si el juego ya empezo para empezarlo el
            Inicio = entrada.readBoolean();
            
            // Si el inicio es true, entonces inicia el programa
            if(Inicio == true){
                cliente.pantalla.pantallaInicio.dispose();
                cliente.pantalla.setVisible(true);
            }
            
            
        } catch (IOException ex) {
            //Logger.getLogger(ThreadTurnosCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while(isrunnig){
            
            if(Inicio == true){
            
                try {
                    boolEntrada = entrada.readBoolean();
                    
                    // Setea los turnos con lo que se les mande
                    Turno = boolEntrada;
                    cliente.Turno = boolEntrada;
                    
                } catch (IOException ex) {
                    //Logger.getLogger(ThreadTurnosCliente.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
        }
    }
}
