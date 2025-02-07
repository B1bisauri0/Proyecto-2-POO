/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clientes;

import com.mycompany.proyecto2.Mapa.Barco;
import com.mycompany.proyecto2.Mapa.Mapa;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Tamara
 */
class ThreadCliente extends Thread implements Serializable{
    boolean isrunnig = true;
    private Socket socket;
    private Cliente cliente;
    private ObjectInputStream entrada;
    Mapa mapa;
    int numCliente;
    Barco barco;

    public ThreadCliente(Socket socket, Cliente cliente) {
        try {
            this.socket = socket;
            this.cliente = cliente;
            entrada = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            //Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Override
    public void run(){
        
        Barco barcoNuevo;
        
        try {
            barcoNuevo = (Barco) entrada.readObject();
            
            mapa = barcoNuevo.getMapa();
            numCliente = barcoNuevo.getNumBarco();
            barco = barcoNuevo;
            
            cliente.setBarco(barcoNuevo);
            cliente.pantalla.generarTableros(mapa, barco);
            
            
            // Variables de tipo del barco            
            cliente.pantalla.lblVidaC1.setText(Double.toString(barcoNuevo.getVida()));
            cliente.pantalla.lblOroC1.setText(Integer.toString(barcoNuevo.getOro()));
            cliente.pantalla.lblBalasHeavyC1.setText(Integer.toString(barcoNuevo.getBalasHeavy()));
            cliente.pantalla.lblBalasLongC1.setText(Integer.toString(barcoNuevo.getBalasLong()));
            cliente.pantalla.lblMinasC1.setText(Integer.toString(barcoNuevo.getMinas()));
            
            
        } catch (IOException ex) {
            Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
        }


        while(isrunnig){
            
            try {
                
                // Espera a recibir un nuevo barco
                barcoNuevo = (Barco) entrada.readObject();
                          
                if(barcoNuevo.Ganador == false && barcoNuevo.Perdedor == false){

                    // Setea al mapa los barcos contrarios
                    mapa.SetearBarcosEnMapa(barcoNuevo.getX(), barcoNuevo.getY(), barcoNuevo.getNumBarco());

                    // Si el descubrir esta activo, descubre esa celda
                    if(barcoNuevo.isDescubrirActivo()){
                        mapa.getArregloCeldas()[barcoNuevo.getY()][barcoNuevo.getX()].setDescubierta(true);
                    }

                    // Si descubre una mina
                    if(barcoNuevo.descubrirMina == true){
                        // Setea los valores, como que los reinicia
                        mapa.getArregloCeldas()[barcoNuevo.getY()][barcoNuevo.getX()].setMina(false);
                        mapa.getArregloCeldas()[barcoNuevo.getY()][barcoNuevo.getX()].setNumBarcoMina(-1);
                    }
                    
                    if(barcoNuevo.Muerte == true){
                        cliente.getPantalla().write("El barco de " + barcoNuevo.getNombre() + " ha muerto");
                        mapa.getArregloCeldas()[barcoNuevo.getY()][barcoNuevo.getX()].setNumBarco(-1);
                        mapa.getArregloCeldas()[barcoNuevo.getY()][barcoNuevo.getX()].setOcupadoXBarco(false);
                    }

                    // Refresca el tablero
                    cliente.pantalla.RefrescarTablero(mapa, barco);
                }
                
                // Si el barco gano
                else if(barcoNuevo.Ganador == true && barcoNuevo.getNumBarco() == barco.getNumBarco()){
                    JOptionPane.showMessageDialog(null, "HAZ GANADO!!!", "Ganaste", JOptionPane.INFORMATION_MESSAGE);
                    Thread.sleep(500);
                    cliente.getPantalla().dispose();
                }
                
                else if(barcoNuevo.Perdedor == true && barcoNuevo.getNumBarco() == barco.getNumBarco()){
                    JOptionPane.showMessageDialog(null, "HAZ PERDIDO!!!", "Perdiste", JOptionPane.INFORMATION_MESSAGE);
                    Thread.sleep(500);
                    cliente.getPantalla().dispose();
                }

            } catch (IOException ex) {
                //System.out.println("Error 1 ");
            } catch (ClassNotFoundException ex) {
                //System.out.println("Error 2");
            } catch (InterruptedException ex) {
                //Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}