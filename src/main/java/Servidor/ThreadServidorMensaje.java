/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

import Modelos.Mensaje;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tamara
 */
public class ThreadServidorMensaje extends Thread{
    public Socket socket;
    private Servidor server;
    private ObjectInputStream entrada;
    private DataInputStream entradaDatos;
    ObjectOutputStream salida;
    String nombre;
    int NumCliente;
    private boolean isRunning = true;

    public ThreadServidorMensaje(Socket socket, Servidor server, int NumCliente) {
        this.socket = socket;
        this.server = server;
        this.NumCliente = NumCliente;
        try {
            entrada = new ObjectInputStream(socket.getInputStream());
            salida = new ObjectOutputStream(socket.getOutputStream());
            entradaDatos = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            
        }
    }
    
    @Override
    public void run() {
        Mensaje mensaje;
        
        while(isRunning){                
            try {
                mensaje = (Mensaje) entrada.readObject();
                if (mensaje.getTipo().equals("PUBLICO")){
                    server.pantalla.txaChatServidor.append(mensaje.toString());
                    server.broadcastMensaje(mensaje);
                }else if (mensaje.getTipo().equals("PRIVADO")){
                    server.pantalla.txaChatServidor.append(mensaje.toString());
                    server.sendPrivateMessage(mensaje);
                }

            } catch (IOException ex) {
                //Logger.getLogger(ThreadServidor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                //Logger.getLogger(ThreadServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
                
       
        }
    }
}