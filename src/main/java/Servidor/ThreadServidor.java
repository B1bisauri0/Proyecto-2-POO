/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

import com.mycompany.proyecto2.Mapa.Barco;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tamara
 */
public class ThreadServidor extends Thread implements Serializable{
    
    public Socket socket;
    private Servidor server;
    private ObjectInputStream entrada;
    private DataInputStream entradaDatos;
    ObjectOutputStream salida;
    String nombre;
    int numCliente;
    public Barco barco;
    
    private boolean isRunning = true;

    public ThreadServidor(Socket socket, Servidor server, int numCliente) {
        this.socket = socket;
        this.server = server;
        this.numCliente = numCliente;
        this.barco = new Barco(server.mapa, numCliente);
        
        try {
            entrada = new ObjectInputStream(socket.getInputStream());
            salida = new ObjectOutputStream(socket.getOutputStream());
            entradaDatos = new DataInputStream(socket.getInputStream());
            
        } catch (IOException ex) {
            
        }
    }
    
    @Override
    public void run() {
        
        Barco barcoEnviado;
        
        try {
            
            this.nombre = entradaDatos.readUTF();
            barco.setNombre(nombre);

            server.clientesConectadosMensaje.get(numCliente-1).nombre = this.nombre;
            
            server.broadcastBarcoMover(this.barco);
            server.mapa = this.barco.getMapa();
                        
            // Si es el primer cliente en entrar al servidor
            if(numCliente == 1){
                server.pantalla.lblCliente1.setText(nombre + ":");
            }

            // Si es el segundo cliente en entrar al servidor
            else if(numCliente == 2){
                server.pantalla.lblCliente2.setText(nombre + ":");
            }

            // Si es el tercer cliente en entrar al servidor
            else if(numCliente == 3){
                server.pantalla.lblCliente3.setText(nombre + ":");
            }

            // Si es el cuarto cliente en entrar al servidor
            else if(numCliente == 4){
                server.pantalla.lblCliente4.setText(nombre + ":");
            }
            
            
        } catch (IOException ex) {
            //System.out.println(ex.toString());
        }
        
        while(isRunning){
            
            try {
                // Se queda esperando a que envien un barco
                barcoEnviado = (Barco) entrada.readObject();
                
                // Hace que se muestre cual barco se movio
                server.pantalla.write("El barco de " + barcoEnviado.getNumBarco()+ " se movio a la coordenada (" + barcoEnviado.getX() + " ," + barcoEnviado.getY() + ")");
                
                // Hace un broadcast del mapa
                server.broadcastBarcoMover(barcoEnviado);
                
                barcoEnviado = null;
                
            } catch (IOException ex) {
                //System.out.println("Cayo en el IO");
            } catch (ClassNotFoundException ex) {
                //System.out.println("Cayo en la clase");
            }
            
        }
    }
}
