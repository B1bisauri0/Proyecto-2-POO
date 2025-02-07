/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Commands;

import Clientes.Cliente;
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
public class ComprarCommand extends BaseCommand implements Serializable{
    
    public static final String COMMAND_NAME = "Comprar";       
    
    @Override       
    public String getCommandName() {           
        return COMMAND_NAME;   
    }       
    
    @Override
    public void execute(String[] args, OutputStream out, Barco barco, int Atacante, int Receptor, Cliente cliente) {
                
        int Total = 0;
        int cantidadBalasHeavy = 0;
        int cantidadBalasLong = 0;
        int cantidadMinas = 0;
        int cantidadRadarShort = 0;
        int cantidadRadarLong = 0;
        int cantidadSpot = 0;
        ImageIcon icon = new javax.swing.ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\Imagenes\\Comerciantes.png");
        
        try{
        
            // Revisa cuales opciones estan seleccionadas y les saca su valor del text field
            if(cliente.getPantalla().rbtnBalasHeavy.isSelected() == true){
                cantidadBalasHeavy = Integer.parseInt(cliente.getPantalla().txfBalasHeavy.getText());
                Total = Total + (20 * cantidadBalasHeavy);
            }
            
            if(cliente.getPantalla().rbtnBalasLong.isSelected() == true){
                cantidadBalasLong = Integer.parseInt(cliente.getPantalla().txfBalasLong.getText());
                Total = Total + (20 * cantidadBalasLong);
            }
            
            if(cliente.getPantalla().rbtnMinas.isSelected() == true){
                cantidadMinas = Integer.parseInt(cliente.getPantalla().txfMinas.getText());
                Total = Total + (20 * cantidadMinas);
            }
            
            if(cliente.getPantalla().rbtnRadarShort.isSelected() == true){
                cantidadRadarShort = Integer.parseInt(cliente.getPantalla().txfRadarShort.getText());
                Total = Total + (20 * cantidadRadarShort);
            }
            
            if(cliente.getPantalla().rbtnRadarLong.isSelected() == true){
                cantidadRadarLong = Integer.parseInt(cliente.getPantalla().txfRadarLong.getText());
                Total = Total + (20 * cantidadRadarLong);
            }
            
            if(cliente.getPantalla().rbtnSpot.isSelected() == true){
                cantidadSpot = Integer.parseInt(cliente.getPantalla().txfSpot.getText());
                Total = Total + (20 * cantidadSpot);
            }
            
            if(Total > barco.getOro()){
                throw new IllegalArgumentException("Cantidad insuficiente de dinero");
            }
            
            if(Total <= barco.getOro()){
                
                // Setea la cantidad de balas del barco
                barco.setBalasHeavy(barco.getBalasHeavy() + cantidadBalasHeavy);
                barco.setBalasLong(barco.getBalasLong() + cantidadBalasLong);
                barco.setMinas(barco.getMinas() + cantidadMinas);
                barco.setRadarShort(barco.getRadarShort() + cantidadRadarShort);
                barco.setRadarLong(barco.getRadarLong() + cantidadRadarLong);
                barco.setSpot(barco.getSpot() + cantidadSpot);
                barco.setOro(barco.getOro() - Total);
                
                // Setea las caracteristicas del barco en la pantalla del jugador
                cliente.getPantalla().lblBalasHeavyC1.setText(Integer.toString(barco.getBalasHeavy()));
                cliente.getPantalla().lblBalasLongC1.setText(Integer.toString(barco.getBalasLong()));
                cliente.getPantalla().lblMinasC1.setText(Integer.toString(barco.getMinas()));
                cliente.getPantalla().lblRadarLongC1.setText(Integer.toString(barco.getRadarLong()));
                cliente.getPantalla().lblRadarShortC1.setText(Integer.toString(barco.getRadarShort()));
                cliente.getPantalla().lblSpotC1.setText(Integer.toString(barco.getSpot()));
                cliente.getPantalla().lblOroC1.setText(Integer.toString(barco.getOro()));
            }

            
        } catch (NumberFormatException e) {
            cliente.getPantalla().txfBalasHeavy.setText("");
            cliente.getPantalla().txfBalasLong.setText("");
            cliente.getPantalla().txfMinas.setText("");
            cliente.getPantalla().txfRadarLong.setText("");
            cliente.getPantalla().txfRadarShort.setText("");
            cliente.getPantalla().txfSpot.setText("");
            
            JOptionPane.showMessageDialog(null, "Debe de ingresar un numero valido", "Error", JOptionPane.ERROR_MESSAGE);
        } catch(IllegalArgumentException e){
            cliente.getPantalla().txfBalasHeavy.setText("");
            cliente.getPantalla().txfBalasLong.setText("");
            cliente.getPantalla().txfMinas.setText("");
            cliente.getPantalla().txfRadarLong.setText("");
            cliente.getPantalla().txfRadarShort.setText("");
            cliente.getPantalla().txfSpot.setText("");
            JOptionPane.showMessageDialog(null, "Comerciante: No tienes la cantidad de oro suficiente, consigue más y vuelve", "Oro insuficiente", JOptionPane.INFORMATION_MESSAGE, icon);
        }
        
        try {
            cliente.salida1.reset();
            cliente.salida1.writeObject(barco);
            cliente.salida1.flush();
            // Si al final se ejecuta el comando
            cliente.salidaDatos.writeInt(cliente.getBarco().getNumBarco());
            cliente.Turno = false;
            cliente.threadTurnos.Turno = false;   
            
        } catch (IOException ex) {
            Logger.getLogger(ComprarCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cliente.getPantalla().txfBalasHeavy.setText("");
        cliente.getPantalla().txfBalasLong.setText("");
        cliente.getPantalla().txfMinas.setText("");
        cliente.getPantalla().txfRadarLong.setText("");
        cliente.getPantalla().txfRadarShort.setText("");
        cliente.getPantalla().txfSpot.setText("");
        
    }        
}
