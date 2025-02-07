/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

import com.mycompany.proyecto2.Mapa.Barco;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tamara
 */
public class ServerConnectionsThread extends Thread implements Serializable{
    
    private boolean isRunning = true;
    Servidor server;
    int numCliente;
    
    private DataInputStream entradaDatos;
    
    public ServerConnectionsThread(Servidor server) {
        this.server = server;
        this.numCliente = 1;
    }
    
    @Override
    public void run(){
        while (isRunning) {  
            try {
                
                if(server.Jugar == false) {
                    server.pantalla.write("Esperando nuevo cliente...");

                    // Para barcos busca que algun cliente se conecte
                    Socket nuevoSocket1 = server.serverSocket1.accept();
                    // Crea el thread para el cliente en el server
                    ThreadServidor ts = new ThreadServidor(nuevoSocket1, server, numCliente);      
                    // Inicia el thread
                    ts.start();
                    // Agrega el thread al array list
                    server.clientesConectados.add(ts);
                    // Conecta al cliente
                    server.pantalla.write("Cliente " + server.clientesConectados.size() + " aceptado");

                    // Para mensajes
                    Socket nuevoSocket2 = server.serverSocket2.accept();
                    // Crea el thread para el cliente en el server
                    ThreadServidorMensaje tsm = new ThreadServidorMensaje(nuevoSocket2, server, numCliente);
                    // Arranca el thread de mensajes en el server
                    tsm.start();
                    // Agrega el thread en el array list de mensajes
                    server.clientesConectadosMensaje.add(tsm);
                    server.pantalla.txaChatServidor.append("Cliente " + server.clientesConectadosMensaje.size() +" conectado al chat\n");

                    // Para ataques
                    Socket nuevoSocket3 = server.serverSocket3.accept();
                    // Crea el thread de ataques paraa el cliente en el servidor
                    ThreadServidorAtaques tsAtaques = new ThreadServidorAtaques(nuevoSocket3, server, numCliente);    
                    // Inicia el thread
                    tsAtaques.start();
                    // Agrega el thread al array list
                    server.clientesConectadosAtaques.add(tsAtaques);
                    
                    // Para ataques
                    Socket nuevoSocket4 = server.serverSocket4.accept();
                    // Crea el thread de ataques paraa el cliente en el servidor
                    ThreadTurnos tsTurnos = new ThreadTurnos(nuevoSocket4, server, numCliente);    
                    // Inicia el thread
                    tsTurnos.start();
                    // Agrega el thread al array list
                    server.Turnos.add(tsTurnos);

                    if(numCliente == server.CantidadJugadores){
                        server.Jugar = true;
                    }

                    numCliente++;
                    server.pantalla.RefrescarTablero();
                }
                
                else {
                    
                    // Si ya se puede jugar, inicia la partida
                    server.IniciarJuegoTodos();
                    server.enviarTurnos(1);
                    break;
                }
                
            } catch (IOException ex) {
                //System.out.println("Se cayo");
            }
        }
    }
    
}
