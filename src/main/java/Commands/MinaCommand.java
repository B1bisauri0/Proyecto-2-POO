/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Commands;

import Ataques.AtaqueHeavy;
import Ataques.Minas;
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
public class MinaCommand extends BaseCommand implements Serializable{
    
    public static final String COMMAND_NAME = "Mina";       
    
    @Override       
    public String getCommandName() {           
        return COMMAND_NAME;   
    }       
    
    @Override
    public void execute(String[] args, OutputStream out, Barco barco, int Atacante, int Receptor, Cliente cliente) {
                
        // Imagen del comandante de bombas
        ImageIcon icon = new javax.swing.ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\Imagenes\\ComandanteBombas.png");
        // El numero de barco es -1, para que si cambia, se realiza x o y accion
        boolean HayMinas = false;
        
        // Verifica que se hayan proporcionado dos argumentos numéricos.
        if (args.length >= 2) {
            try {
                // Convierte los argumentos String a enteros.
                int x = Integer.parseInt(args[0]);
                int y = Integer.parseInt(args[1]);
                
                // Revisa que el argumento no sea mayor a la cantidad de balas que posee
                if(barco.getMinas() <= 0){
                    throw new IllegalArgumentException("Cantidad de minas insuficiente");
                }
                
                else if(x <= 0 && x >= 15 && y <= 0 && y >= 15){
                    cliente.getPantalla().write("Favor ingrese valores validos dentro de la matriz");
                }
                
                // Si cumplre todo, realiza el ataque
                else{
                    
                    try {
                                                
                        // Crea el ataque y busca el barco
                        Minas ataque = new Minas(x, y, barco);
                        HayMinas = ataque.HayMinaExistente(barco.getMapa());
                        ataque.Ataque(barco);
                        
                        // Si el barco es -1, significa que no encontro nada y lanza una advertencia
                        if(HayMinas == true){
                            throw new IllegalArgumentException("Mina existente");
                        }
                        
                        // Si lo encontro, realiza el ataque
                        else{
                            cliente.salida3.reset();
                            cliente.salida3.writeObject(ataque);
                            cliente.salida3.flush();
                            cliente.getPantalla().txfComandos.setText("");
                            cliente.getPantalla().write("Haz plantado un bomba en la siguiente posicion: (" + x + ", " + y + ")");
                            cliente.getPantalla().lblMinasC1.setText(Integer.toString(barco.getMinas()));
                            cliente.getPantalla().RefrescarTablero(barco.getMapa(), barco);
                            // Si al final se ejecuta el comando
                            cliente.salidaDatos.writeInt(cliente.getBarco().getNumBarco());
                            cliente.Turno = false;
                            cliente.threadTurnos.Turno = false;
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(AtaqueHeavyCommand.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                
                
            } catch (NumberFormatException e) {
               cliente.getPantalla().write("Los argumentos deben ser números enteros.");
            } catch(IllegalArgumentException e){ 
                
                if(e.getMessage().equals("Cantidad de minas insuficiente")){
                    JOptionPane.showMessageDialog(null, "Comandante de Bombas: Oye capitán, no tenemos suficientes minas para lanzar!", "No hay minas", JOptionPane.INFORMATION_MESSAGE, icon);
                }
                
                else
                    JOptionPane.showMessageDialog(null, "Comandante de Bombas: Capitán, no podemos hacer eso!! En ese sitio ya hay una mina.", "Hay minas en el sitio", JOptionPane.INFORMATION_MESSAGE, icon);
            }
            
        } else {    
           cliente.getPantalla().write("Debe proporcionar dos argumentos numéricos para poder colocar la mina");
        }
    }
}
