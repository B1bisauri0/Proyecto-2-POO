/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Commands;

import Clientes.Cliente;
import Radares.Radares;
import com.mycompany.proyecto2.Mapa.Amenaza;
import com.mycompany.proyecto2.Mapa.Barco;
import com.mycompany.proyecto2.Mapa.TipoCelda;
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
public class RadarShortCommand extends BaseCommand implements Serializable{
    
    public static final String COMMAND_NAME = "RadarShort";       
    
    @Override       
    public String getCommandName() {           
        return COMMAND_NAME;   
    }       
    
    @Override
    public void execute(String[] args, OutputStream out, Barco barco, int Atacante, int Receptor, Cliente cliente) {
        
        // Imagen del comandante de ataques
        ImageIcon icon = new javax.swing.ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\Imagenes\\ComandanteRadares.png"); 
        
        try{
            // Si el la cantidad de radares son 0 o menos
            if(barco.getRadarShort() <= 0){
                throw new IllegalArgumentException("Radares insuficientes");
            }
            
            // Si no, significa que se puede realizar el radar
            else{
                // Le resta un radar al barco
                barco.setRadarShort(barco.getRadarShort() - 1);
                cliente.getPantalla().lblRadarShortC1.setText(Integer.toString(barco.getRadarShort()));
                
                // Manda el barco para que se le seteen sus respectivos valores
                cliente.salida1.reset();
                cliente.salida1.writeObject(barco);
                cliente.salida1.flush();
                
                // Si al final se ejecuta el comando
                cliente.salidaDatos.writeInt(cliente.getBarco().getNumBarco());
                cliente.Turno = false;
                cliente.threadTurnos.Turno = false;
                
                // Inicia el radar
                Radares radar = new Radares(3, barco, cliente);
                radar.start();
                
            }
            
        } catch(IllegalArgumentException e){
            JOptionPane.showMessageDialog(null, "Comandante de Radares: Hey capitán, que radares lanzaremos si no hay!", "No hay radares", JOptionPane.INFORMATION_MESSAGE, icon);
        } catch (IOException ex) {
            Logger.getLogger(RadarShortCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }        
}
