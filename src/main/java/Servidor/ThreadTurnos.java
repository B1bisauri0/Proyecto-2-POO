/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

import Ataques.IAtaques;
import Clientes.Cliente;
import com.mycompany.proyecto2.Mapa.Barco;
import com.mycompany.proyecto2.Mapa.Mapa;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
public class ThreadTurnos extends Thread implements Serializable{
    
    
    public Socket socket;
    private Servidor server;
    public DataOutputStream salidaDatos;
    private DataInputStream entradaDatos;
    String nombre;
    int numCliente;
    boolean Turno;
    
    private boolean isRunning = true;

    public ThreadTurnos(Socket socket, Servidor server, int numCliente) {
        this.socket = socket;
        this.server = server;
        this.numCliente = numCliente;
        this.Turno = false;
        
        try {
            salidaDatos = new DataOutputStream(socket.getOutputStream());
            entradaDatos = new DataInputStream(socket.getInputStream());
            
        } catch (IOException ex) {
            
        }
    }
    
    @Override
    public void run(){
        
        int TurnoAntiguo; // El barco que tenia el turno
        
        while(isRunning){

            try {
                // Espera a que entre un turno para seteatlo
                TurnoAntiguo = entradaDatos.readInt();
                server.enviarTurnos(TurnoAntiguo);
                        
            } catch (IOException ex) {
                Logger.getLogger(ThreadTurnos.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
