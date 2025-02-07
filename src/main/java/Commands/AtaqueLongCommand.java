/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Commands;

import Ataques.AtaqueLong;
import Clientes.Cliente;
import com.mycompany.proyecto2.Mapa.Barco;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Tamara
 */
public class AtaqueLongCommand extends BaseCommand implements Serializable{
    
    public static final String COMMAND_NAME = "AtaqueLong";       
    
    @Override       
    public String getCommandName() {           
        return COMMAND_NAME;   
    }       
    
    @Override
    public void execute(String[] args, OutputStream out, Barco barco, int Atacante, int Receptor, Cliente cliente) {
                
        // Imagen del comandante de ataques
        ImageIcon icon = new javax.swing.ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\Imagenes\\ComandanteAtaques.png");
        // El numero de barco es -1, para que si cambia, se realiza x o y accion
        int numBarco = -1;
        
        // Verifica que se hayan proporcionado dos argumentos numéricos.
        if (args.length >= 1) {
            try {
                // Convierte los argumentos String a enteros.
                int cantidadAtaques = Integer.parseInt(args[0]);
                
                // Revisa que el argumento no sea mayor a la cantidad de balas que posee
                if(barco.getBalasLong()< cantidadAtaques){
                    throw new IllegalArgumentException("Cantidad de balas insuficiente");
                }
                
                // Revisa que el argumento no sea mayor a 4
                else if(cantidadAtaques > 4){
                    throw new IllegalArgumentException("Cantidad ilegal de ataques");
                }
                
                // Si cumplre todo, realiza el ataque
                else{
                    
                    try {
                        
                        // Crea el ataque y busca el barco
                        AtaqueLong ataque = new AtaqueLong(barco.getNumBarco(), cantidadAtaques, barco);
                        numBarco = ataque.BuscarBarco(barco);
                        
                        // Si el barco es -1, significa que no encontro nada y lanza una advertencia
                        if(numBarco == -1){
                            cliente.getPantalla().lblBalasLongC1.setText(Integer.toString(barco.getBalasLong()));
                            ataque.damage = 0;
                            JOptionPane.showMessageDialog(null, "Comandante Ataques: Oh no, no habían barcos alrededor, perdimos " + cantidadAtaques + " balas!", "No habia barcos", JOptionPane.INFORMATION_MESSAGE, icon);
                            cliente.salida3.writeObject(ataque);
                        }
                        
                        // Si lo encontro, realiza el ataque
                        else{
                            cliente.salida3.writeObject(ataque);
                            cliente.getPantalla().txfComandos.setText("");
                            cliente.getPantalla().write("Haz enviado un ataque al barco numero: " + numBarco);
                            cliente.getPantalla().lblBalasLongC1.setText(Integer.toString(barco.getBalasLong()));
                            
                            // Si al final se ejecuta el comando
                            cliente.salidaDatos.writeInt(cliente.getBarco().getNumBarco());
                            cliente.Turno = false;
                            cliente.threadTurnos.Turno = false;
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(AtaqueLongCommand.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                
                
            } catch (NumberFormatException e) {
               cliente.getPantalla().write("Los argumentos deben ser números enteros.");
            } catch(IllegalArgumentException e){ 
                
                if(e.getMessage().equals("Cantidad de balas insuficiente") == true){
                    JOptionPane.showMessageDialog(null, "Comandante Ataques: Argh capitán, no tenemos balas suficientes para realizar el ataque!", "Balas insuficientes", JOptionPane.INFORMATION_MESSAGE, icon); 
                }
                
                else{
                    JOptionPane.showMessageDialog(null, "Comandante Ataques: Argh capitán, no tenemos tantos cañones para todas estas balas!", "Más de cuatro balas", JOptionPane.INFORMATION_MESSAGE, icon);
                }
            }
            
        } else {    
           cliente.getPantalla().write("Debe proporcionar un argumento numérico para poder atacar");
        }
    }
}
