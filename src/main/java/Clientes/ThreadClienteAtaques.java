/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clientes;

import Ataques.IAtaques;
import com.mycompany.proyecto2.Mapa.Barco;
import com.mycompany.proyecto2.Mapa.Mapa;
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
class ThreadClienteAtaques extends Thread implements Serializable{
    boolean isrunnig = true;
    private Socket socket;
    private Cliente cliente;
    private ObjectInputStream entrada;
    Mapa mapa;

    public ThreadClienteAtaques(Socket socket, Cliente cliente) {
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

        IAtaques ataques;
        // Imagen del comandante de ataques
        ImageIcon icon = new javax.swing.ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\Imagenes\\TripulanteEnojado.png");
        
        while(isrunnig){

            try {
                
                // Espera a recibir un ataque
                ataques = (IAtaques) entrada.readObject();
                
                
                // Si el ataque es de tipo mina, setea la mina en el mapa
                if(ataques.getTipoAtaque().equals("AtaqueMina") == true){
                    
                    // Setea la mina y refresca el mapa
                    cliente.threadCliente.mapa.getArregloCeldas()[ataques.getAtacado()][ataques.getAtacante()].setMina(true);
                    cliente.threadCliente.mapa.getArregloCeldas()[ataques.getAtacado()][ataques.getAtacante()].setNumBarcoMina(ataques.getBarco().getNumBarco());
                    cliente.getPantalla().RefrescarTablero(cliente.threadCliente.mapa, cliente.threadCliente.barco);
                }
                
                // Si no, realiza el ataque
                else{
                    // Recibe el ataque y lo ejecuta
                    ataques.Ataque(cliente.threadCliente.barco);

                    // Setea la vida en pantalla
                    cliente.pantalla.lblVidaC1.setText(Double.toString(cliente.threadCliente.barco.getVida()));

                    // Setea el mensaje de ataque
                    cliente.pantalla.write(ataques.toString());
                    
                    if(ataques.getAtacado() == cliente.threadCliente.barco.getNumBarco()){
                        JOptionPane.showMessageDialog(null, "Juan: Por los bigotes de Godos, nos han disparado!", "Ataque", JOptionPane.INFORMATION_MESSAGE, icon);
                    }
                    
                        
                        // Si el barco murio
                        if(cliente.threadCliente.barco.getVida() <= 0){
                            cliente.threadCliente.barco.setVida(0);
                            cliente.getPantalla().lblVidaC1.setText(Double.toString(cliente.threadCliente.barco.getVida()));
                            cliente.threadCliente.barco.Muerte = true;
                            cliente.threadCliente.barco = cliente.getPantalla().MuerteDeBarco(cliente.threadCliente.barco.getMapa(), cliente.threadCliente.barco);
                            
                            cliente.salida1.reset();
                            cliente.salida1.writeObject(cliente.threadCliente.barco);
                            cliente.salida1.flush();
                        }
                }
                          

            } catch (IOException ex) {
                //System.out.println("Error 1 ");
            } catch (ClassNotFoundException ex) {
                //System.out.println("Error 2");
            }
            
        }
    }
}
