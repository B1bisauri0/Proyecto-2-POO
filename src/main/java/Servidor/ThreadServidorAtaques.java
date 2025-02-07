/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

import Ataques.IAtaques;
import com.mycompany.proyecto2.Mapa.Barco;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 *
 * @author Tamara
 */
public class ThreadServidorAtaques extends Thread implements Serializable{
    
    public Socket socket;
    private Servidor server;
    private ObjectInputStream entrada;
    private DataInputStream entradaDatos;
    ObjectOutputStream salida;
    String nombre;
    int numCliente;
    Barco barco;
    
    private boolean isRunning = true;

    public ThreadServidorAtaques(Socket socket, Servidor server, int numCliente) {
        this.socket = socket;
        this.server = server;
        this.numCliente = numCliente;
        
        try {
            entrada = new ObjectInputStream(socket.getInputStream());
            salida = new ObjectOutputStream(socket.getOutputStream());
            entradaDatos = new DataInputStream(socket.getInputStream());
            
        } catch (IOException ex) {
            
        }
    }
    
    @Override
    public void run() {
        
        IAtaques ataque;
        PantallaServidor1 pantalla = server.pantalla;
        Barco BarcoAtaque;
        
        while(isRunning){
            
            try {
                // Se queda esperando a que envien un ataque
                ataque = (IAtaques) entrada.readObject();
                BarcoAtaque = ataque.getBarco();
                
                if(ataque.getAtacado() == -1  || ataque.getTipoAtaque().equals("AtaqueMina")){
                    
                    // Si el ataque es una mina
                    if(ataque.getTipoAtaque().equals("AtaqueMina")){
                        server.BroadcastMinas(ataque);
                    }
                    
                    // LE BAJA LAS BALAS AL ATACANTE
                    // Si el barco del ataque es 1
                    if(BarcoAtaque.getNumBarco() == 1){
                        
                        // Si el ataque es ataque heavy
                        if(ataque.getTipoAtaque().equals("AtaqueHeavy")){
                            pantalla.lblBalasHeavyC1.setText(Integer.toString(BarcoAtaque.getBalasHeavy()));
                        }
                        
                        // Si el ataque es long
                        else if(ataque.getTipoAtaque().equals("AtaqueLong")){
                            pantalla.lblBalasLongC1.setText(Integer.toString(BarcoAtaque.getBalasLong()));
                            
                        }
                        
                        // Si el ataque es mina
                        else if(ataque.getTipoAtaque().equals("AtaqueMina")){
                            pantalla.lblMinasC1.setText(Integer.toString(BarcoAtaque.getMinas()));
                        }
                        
                        server.cantidadOroC1 = (BarcoAtaque.getOro()) + (BarcoAtaque.getBalasHeavy()* 20) + (BarcoAtaque.getBalasLong()*20) + (BarcoAtaque.getMinas()*20) + (BarcoAtaque.getRadarLong()*20) + (BarcoAtaque.getRadarShort() * 20) + (BarcoAtaque.getSpot()*20);
                    }
                    
                    // Si el barco del ataque es 2
                    if(BarcoAtaque.getNumBarco() == 2){
                        
                        // Si el ataque es ataque heavy
                        if(ataque.getTipoAtaque().equals("AtaqueHeavy")){
                            pantalla.lblBalasHeavyC2.setText(Integer.toString(BarcoAtaque.getBalasHeavy()));
                        }
                        
                        // Si el ataque es long
                        else if(ataque.getTipoAtaque().equals("AtaqueLong")){
                            pantalla.lblBalasLongC2.setText(Integer.toString(BarcoAtaque.getBalasLong()));
                        }
                        
                        // Si el ataque es mina
                        else if(ataque.getTipoAtaque().equals("AtaqueMina")){
                            pantalla.lblMinasC2.setText(Integer.toString(BarcoAtaque.getMinas()));
                        }
                        
                        server.cantidadOroC2 = (BarcoAtaque.getOro()) + (BarcoAtaque.getBalasHeavy()* 20) + (BarcoAtaque.getBalasLong()*20) + (BarcoAtaque.getMinas()*20) + (BarcoAtaque.getRadarLong()*20) + (BarcoAtaque.getRadarShort() * 20) + (BarcoAtaque.getSpot()*20);
                    }
                    
                    // Si el barco del ataque es 3
                    if(BarcoAtaque.getNumBarco() == 3){
                        
                        // Si el ataque es ataque heavy
                        if(ataque.getTipoAtaque().equals("AtaqueHeavy")){
                            pantalla.lblBalasHeavyC3.setText(Integer.toString(BarcoAtaque.getBalasHeavy()));
                        }
                        
                        // Si el ataque es long
                        else if(ataque.getTipoAtaque().equals("AtaqueLong")){
                            pantalla.lblBalasLongC3.setText(Integer.toString(BarcoAtaque.getBalasLong()));
                        }
                        
                        // Si el ataque es mina
                        else if(ataque.getTipoAtaque().equals("AtaqueMina")){
                            pantalla.lblMinasC3.setText(Integer.toString(BarcoAtaque.getMinas()));
                        }
                        
                        server.cantidadOroC3 = (BarcoAtaque.getOro()) + (BarcoAtaque.getBalasHeavy()* 20) + (BarcoAtaque.getBalasLong()*20) + (BarcoAtaque.getMinas()*20) + (BarcoAtaque.getRadarLong()*20) + (BarcoAtaque.getRadarShort() * 20) + (BarcoAtaque.getSpot()*20);
                    }
                    
                     // Si el barco del ataque es 4
                    if(BarcoAtaque.getNumBarco() == 4){
                        
                        // Si el ataque es ataque heavy
                        if(ataque.getTipoAtaque().equals("AtaqueHeavy")){
                            pantalla.lblBalasHeavyC4.setText(Integer.toString(BarcoAtaque.getBalasHeavy()));
                        }
                        
                        // Si el ataque es long
                        else if(ataque.getTipoAtaque().equals("AtaqueLong")){
                            pantalla.lblBalasLongC5.setText(Integer.toString(BarcoAtaque.getBalasLong()));
                        }
                        
                        // Si el ataque es mina
                        else if(ataque.getTipoAtaque().equals("AtaqueMina")){
                            pantalla.lblMinasC5.setText(Integer.toString(BarcoAtaque.getMinas()));
                        }
                        
                        server.cantidadOroC4 = (BarcoAtaque.getOro()) + (BarcoAtaque.getBalasHeavy()* 20) + (BarcoAtaque.getBalasLong()*20) + (BarcoAtaque.getMinas()*20) + (BarcoAtaque.getRadarLong()*20) + (BarcoAtaque.getRadarShort() * 20) + (BarcoAtaque.getSpot()*20);
                    }
                }
                

                
                else{
                    server.enviarAtaque(ataque);
                }
                
                
                
            } catch (IOException ex) {
                //System.out.println("Cayo en el IO");
            } catch (ClassNotFoundException ex) {
                //System.out.println("Cayo en la clase");
            }
            
        }
    }
}
