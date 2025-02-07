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
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Tamara
 */
public class MoverDescubrirCommand extends BaseCommand implements Serializable{
    
    public static final String COMMAND_NAME = "MoverDescubrir";       
    
    @Override       
    public String getCommandName() {           
        return COMMAND_NAME;   
    }       
    
    @Override
    public void execute(String[] args, OutputStream out, Barco barco, int Atacante, int Receptor, Cliente cliente) {
        
        // Imagen del comandante de ataques
        ImageIcon icon = new javax.swing.ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\Imagenes\\TripulanteEnojado.png"); 
        
        // Imagen de tripulante de movimientos
        ImageIcon icon2 = new javax.swing.ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\Imagenes\\ComandanteMovimientos.png"); 
        
        // Verifica que se hayan proporcionado dos argumentos numéricos.
        if (args.length >= 2) {
            try {
                // Convierte los argumentos String a enteros.
                int x = Integer.parseInt(args[0]);
                int y = Integer.parseInt(args[1]);
                boolean mover = false;
                
                for(int i = 0; i <= 3; i++){
                    
                    // Si x esta a la izquierda del barco
                    if(x < barco.getX() && y == barco.getY()){
                        
                        // Si al final el barco esta en el rango de moviento
                        if(barco.getX() - i > -1 && barco.getX() - i == x){
                            
                            // Si hay un barco en esa coordenada
                            if(barco.getMapa().getArregloCeldas()[y][x].isOcupadoXBarco() == true){
                                throw new IllegalArgumentException("Barco existente");
                            }
                            
                            // Si hay una mina que no coloco el barco
                            if(barco.getMapa().getArregloCeldas()[y][x].isMina() == true && barco.getMapa().getArregloCeldas()[y][x].getNumBarcoMina() != barco.getNumBarco()){
                                barco.setVida(barco.getVida()-50);
                                cliente.getPantalla().lblVidaC1.setText(Double.toString(barco.getVida()));
                                barco.descubrirMina = true;
                                JOptionPane.showMessageDialog(null, "Juan: Por la loquera de Misi, hemos pisado una mina!!", "Se piso mina", JOptionPane.INFORMATION_MESSAGE, icon);
                            }
                            
                            // Si el tipo de celda es vacia, no envia el barco para descubirlo
                            if(barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.VACIA){
                                barco.getMapa().getArregloCeldas()[y][x].setDescubierta(true);
                                cliente.getPantalla().RefrescarTablero(barco.getMapa(), barco);
                                cliente.getPantalla().btnComprar.setVisible(false);
                                cliente.getPantalla().lblVendedor.setVisible(false);
                                
                            }
                            
                            // Si el tipo de celda es oro, entonces le aumenta el oro al barco
                            else if (barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.TESORO && barco.getMapa().getArregloCeldas()[y][x].isDescubierta() == false){
                                barco.setOro(barco.getOro() + 20);
                                barco.setDescubrirActivo(true);                                
                                cliente.getPantalla().btnComprar.setVisible(false);
                                cliente.getPantalla().lblVendedor.setVisible(false);
                                cliente.getPantalla().lblOroC1.setText(Integer.toString(barco.getOro()));
                            }
                            
                            // Si el tipo de celda es amenaza, entonces realiza el ataque
                            else if(barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.AMENAZA){
                                Amenaza amenaza = new Amenaza(cliente.getPantalla(), barco, barco.getMapa().getArregloCeldas()[y][x]);
                                amenaza.start();
                                barco.setDescubrirActivo(true);
                                cliente.getPantalla().btnComprar.setVisible(false);
                                cliente.getPantalla().lblVendedor.setVisible(false);
                                cliente.getPantalla().lblVidaC1.setText(Double.toString(barco.getVida()));
                            }
                            
                            // Si hay un mercado, activa el mercado
                            else if(barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.MERCADO){
                                cliente.getPantalla().btnComprar.setVisible(true);
                                cliente.getPantalla().lblVendedor.setVisible(true);
                            }
                            
                            // Mueve el barco
                            mover = true;
                            barco.MoverBarcoEnMapa(x, y);
                        }
                    }
                    
                   // Si x esta a la izquierda arriba
                    else if(x < barco.getX() && y < barco.getY()){
                        
                        // Si al final el barco esta en el rango de moviento
                        if(barco.getX() - i > -1 && barco.getX() - i == x && barco.getY() - i > -1 && barco.getY() - i == y){
                            
                            // Si hay un barco en esa coordenada
                            if(barco.getMapa().getArregloCeldas()[y][x].isOcupadoXBarco() == true){
                                throw new IllegalArgumentException("Barco existente");
                            }
                            
                            // Si hay una mina que no coloco el barco
                            if(barco.getMapa().getArregloCeldas()[y][x].isMina() == true && barco.getMapa().getArregloCeldas()[y][x].getNumBarcoMina() != barco.getNumBarco()){
                                barco.setVida(barco.getVida()-50);
                                cliente.getPantalla().lblVidaC1.setText(Double.toString(barco.getVida()));
                                barco.descubrirMina = true;
                                JOptionPane.showMessageDialog(null, "Juan: Por la loquera de Misi, hemos pisado una mina!!", "Se piso mina", JOptionPane.INFORMATION_MESSAGE, icon);
                            }
                            
                            // Si el tipo de celda es vacia, no envia el barco para descubirlo
                            if(barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.VACIA){
                                barco.getMapa().getArregloCeldas()[y][x].setDescubierta(true);
                                cliente.getPantalla().RefrescarTablero(barco.getMapa(), barco);
                                cliente.getPantalla().btnComprar.setVisible(false);
                                cliente.getPantalla().lblVendedor.setVisible(false);
                                
                            }
                            
                            // Si el tipo de celda es oro, entonces le aumenta el oro al barco
                            else if (barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.TESORO && barco.getMapa().getArregloCeldas()[y][x].isDescubierta() == false){
                                barco.setOro(barco.getOro() + 20);
                                barco.setDescubrirActivo(true);
                                cliente.getPantalla().btnComprar.setVisible(false);
                                cliente.getPantalla().lblVendedor.setVisible(false);
                                cliente.getPantalla().lblOroC1.setText(Integer.toString(barco.getOro()));
                            }
                            
                            // Si el tipo de celda es amenaza, entonces realiza el ataque
                            else if(barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.AMENAZA){
                                Amenaza amenaza = new Amenaza(cliente.getPantalla(), barco, barco.getMapa().getArregloCeldas()[y][x]);
                                amenaza.start();
                                barco.setDescubrirActivo(true);
                                cliente.getPantalla().btnComprar.setVisible(false);
                                cliente.getPantalla().lblVendedor.setVisible(false);
                                cliente.getPantalla().lblVidaC1.setText(Double.toString(barco.getVida()));
                            }
                            
                            // Si hay un mercado, activa el mercado
                            else if(barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.MERCADO){
                                cliente.getPantalla().btnComprar.setVisible(true);
                                cliente.getPantalla().lblVendedor.setVisible(true);
                            }
                            
                            // Mueve el barco
                            mover = true;
                            barco.MoverBarcoEnMapa(x, y);
                        }
                    }
                    
                   // Si x esta arriba
                    else if(x == barco.getX() && y < barco.getY()){
                        
                        // Si al final el barco esta en el rango de moviento
                        if(barco.getY() - i > -1 && barco.getY() - i == y){
                            
                             // Si hay un barco en esa coordenada
                            if(barco.getMapa().getArregloCeldas()[y][x].isOcupadoXBarco() == true){
                                throw new IllegalArgumentException("Barco existente");
                            }
                            
                            // Si hay una mina que no coloco el barco
                            if(barco.getMapa().getArregloCeldas()[y][x].isMina() == true && barco.getMapa().getArregloCeldas()[y][x].getNumBarcoMina() != barco.getNumBarco()){
                                barco.setVida(barco.getVida()-50);
                                cliente.getPantalla().lblVidaC1.setText(Double.toString(barco.getVida()));
                                barco.descubrirMina = true;
                                JOptionPane.showMessageDialog(null, "Juan: Por la loquera de Misi, hemos pisado una mina!!", "Se piso mina", JOptionPane.INFORMATION_MESSAGE, icon);
                            }
                            
                            // Si el tipo de celda es vacia, no envia el barco para descubirlo
                            if(barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.VACIA){
                                barco.getMapa().getArregloCeldas()[y][x].setDescubierta(true);
                                cliente.getPantalla().RefrescarTablero(barco.getMapa(), barco);
                                cliente.getPantalla().btnComprar.setVisible(false);
                                cliente.getPantalla().lblVendedor.setVisible(false);
                                
                            }
                            
                            // Si el tipo de celda es oro, entonces le aumenta el oro al barco
                            else if (barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.TESORO && barco.getMapa().getArregloCeldas()[y][x].isDescubierta() == false){
                                barco.setOro(barco.getOro() + 20);
                                barco.setDescubrirActivo(true);
                                cliente.getPantalla().btnComprar.setVisible(false);
                                cliente.getPantalla().lblVendedor.setVisible(false);
                                cliente.getPantalla().lblOroC1.setText(Integer.toString(barco.getOro()));
                            }
                            
                            // Si el tipo de celda es amenaza, entonces realiza el ataque
                            else if(barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.AMENAZA){
                                Amenaza amenaza = new Amenaza(cliente.getPantalla(), barco, barco.getMapa().getArregloCeldas()[y][x]);
                                amenaza.start();
                                barco.setDescubrirActivo(true);
                                cliente.getPantalla().btnComprar.setVisible(false);
                                cliente.getPantalla().lblVendedor.setVisible(false);
                                cliente.getPantalla().lblVidaC1.setText(Double.toString(barco.getVida()));
                                
                            }
                            
                            else if(barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.MERCADO){
                                cliente.getPantalla().btnComprar.setVisible(true);
                                cliente.getPantalla().lblVendedor.setVisible(true);
                            }
                            
                            // Mueve el barco
                            mover = true;
                            barco.MoverBarcoEnMapa(x, y);
                            
                        }
                    }
                    
                   // Si x esta arriba derecha
                    else if(x > barco.getX() && y < barco.getY()){
                        
                        // Si al final el barco esta en el rango de moviento
                        if(barco.getX() + i < 15 && barco.getX() + i == x && barco.getY() - i > -1 && barco.getY() - i == y){
                            
                            // Si hay un barco en esa coordenada
                            if(barco.getMapa().getArregloCeldas()[y][x].isOcupadoXBarco() == true){
                                throw new IllegalArgumentException("Barco existente");
                            }
                            
                            // Si hay una mina que no coloco el barco
                            if(barco.getMapa().getArregloCeldas()[y][x].isMina() == true && barco.getMapa().getArregloCeldas()[y][x].getNumBarcoMina() != barco.getNumBarco()){
                                barco.setVida(barco.getVida()-50);
                                cliente.getPantalla().lblVidaC1.setText(Double.toString(barco.getVida()));
                                barco.descubrirMina = true;
                                JOptionPane.showMessageDialog(null, "Juan: Por la loquera de Misi, hemos pisado una mina!!", "Se piso mina", JOptionPane.INFORMATION_MESSAGE, icon);
                            }
                            
                            // Si el tipo de celda es vacia, no envia el barco para descubirlo
                            if(barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.VACIA){
                                barco.getMapa().getArregloCeldas()[y][x].setDescubierta(true);
                                cliente.getPantalla().RefrescarTablero(barco.getMapa(), barco);
                                cliente.getPantalla().btnComprar.setVisible(false);
                                cliente.getPantalla().lblVendedor.setVisible(false);
                                
                            }
                            
                            // Si el tipo de celda es oro, entonces le aumenta el oro al barco
                            else if (barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.TESORO && barco.getMapa().getArregloCeldas()[y][x].isDescubierta() == false){
                                barco.setOro(barco.getOro() + 20);
                                barco.setDescubrirActivo(true);
                                cliente.getPantalla().btnComprar.setVisible(false);
                                cliente.getPantalla().lblVendedor.setVisible(false);
                                cliente.getPantalla().lblOroC1.setText(Integer.toString(barco.getOro()));
                            }
                            
                            // Si el tipo de celda es amenaza, entonces realiza el ataque
                            else if(barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.AMENAZA){
                                Amenaza amenaza = new Amenaza(cliente.getPantalla(), barco, barco.getMapa().getArregloCeldas()[y][x]);
                                amenaza.start();
                                barco.setDescubrirActivo(true);
                                cliente.getPantalla().btnComprar.setVisible(false);
                                cliente.getPantalla().lblVendedor.setVisible(false);
                                cliente.getPantalla().lblVidaC1.setText(Double.toString(barco.getVida()));
                            }
                            
                            // Si hay un mercado, activa el mercado
                            else if(barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.MERCADO){
                                cliente.getPantalla().btnComprar.setVisible(true);
                                cliente.getPantalla().lblVendedor.setVisible(true);
                            }
                            
                            // Mueve el barco
                            mover = true;
                            barco.MoverBarcoEnMapa(x, y);
                        }
                    }
                    
                   // Si x esta a la derecha del barco
                    else if(x > barco.getX() && y == barco.getY()){
                        
                        // Si al final el barco esta en el rango de moviento
                        if(barco.getX() + i < 15 && barco.getX() + i == x){
                            
                            // Si hay un barco en esa coordenada
                            if(barco.getMapa().getArregloCeldas()[y][x].isOcupadoXBarco() == true){
                                throw new IllegalArgumentException("Barco existente");
                            }
                            
                            // Si hay una mina que no coloco el barco
                            if(barco.getMapa().getArregloCeldas()[y][x].isMina() == true && barco.getMapa().getArregloCeldas()[y][x].getNumBarcoMina() != barco.getNumBarco()){
                                barco.setVida(barco.getVida()-50);
                                cliente.getPantalla().lblVidaC1.setText(Double.toString(barco.getVida()));
                                barco.descubrirMina = true;
                                JOptionPane.showMessageDialog(null, "Juan: Por la loquera de Misi, hemos pisado una mina!!", "Se piso mina", JOptionPane.INFORMATION_MESSAGE, icon);
                            }
                            
                            // Si el tipo de celda es vacia, no envia el barco para descubirlo
                            if(barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.VACIA){
                                barco.getMapa().getArregloCeldas()[y][x].setDescubierta(true);
                                cliente.getPantalla().RefrescarTablero(barco.getMapa(), barco);
                                cliente.getPantalla().btnComprar.setVisible(false);
                                cliente.getPantalla().lblVendedor.setVisible(false);
                                
                            }
                            
                            // Si el tipo de celda es oro, entonces le aumenta el oro al barco
                            else if (barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.TESORO && barco.getMapa().getArregloCeldas()[y][x].isDescubierta() == false){
                                barco.setOro(barco.getOro() + 20);
                                barco.setDescubrirActivo(true);
                                cliente.getPantalla().btnComprar.setVisible(false);
                                cliente.getPantalla().lblVendedor.setVisible(false);
                                cliente.getPantalla().lblOroC1.setText(Integer.toString(barco.getOro()));
                            }
                            
                            // Si el tipo de celda es amenaza, entonces realiza el ataque
                            else if(barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.AMENAZA){
                                Amenaza amenaza = new Amenaza(cliente.getPantalla(), barco, barco.getMapa().getArregloCeldas()[y][x]);
                                amenaza.start();
                                barco.setDescubrirActivo(true);
                                cliente.getPantalla().btnComprar.setVisible(false);
                                cliente.getPantalla().lblVendedor.setVisible(false);
                                cliente.getPantalla().lblVidaC1.setText(Double.toString(barco.getVida()));
                            }
                            
                            // Si hay un mercado, activa el mercado
                            else if(barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.MERCADO){
                                cliente.getPantalla().btnComprar.setVisible(true);
                                cliente.getPantalla().lblVendedor.setVisible(true);
                            }
                            
                            // Mueve el barco
                            mover = true;
                            barco.MoverBarcoEnMapa(x, y);
                        }
                    }
                    
                   // Si x esta derecha abajo
                    else if(x > barco.getX() && y > barco.getY()){
                        
                        // Si al final el barco esta en el rango de moviento
                        if(barco.getX() + i < 15 && barco.getX() + i == x && barco.getY() + i < 15 && barco.getY() + i == y){
                            
                            // Si hay un barco en esa coordenada
                            if(barco.getMapa().getArregloCeldas()[y][x].isOcupadoXBarco() == true){
                                throw new IllegalArgumentException("Barco existente");
                            }
                            
                            // Si hay una mina que no coloco el barco
                            if(barco.getMapa().getArregloCeldas()[y][x].isMina() == true && barco.getMapa().getArregloCeldas()[y][x].getNumBarcoMina() != barco.getNumBarco()){
                                barco.setVida(barco.getVida()-50);
                                cliente.getPantalla().lblVidaC1.setText(Double.toString(barco.getVida()));
                                barco.descubrirMina = true;
                                JOptionPane.showMessageDialog(null, "Juan: Por la loquera de Misi, hemos pisado una mina!!", "Se piso mina", JOptionPane.INFORMATION_MESSAGE, icon);
                            }
                            
                            // Si el tipo de celda es vacia, no envia el barco para descubirlo
                            if(barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.VACIA){
                                barco.getMapa().getArregloCeldas()[y][x].setDescubierta(true);
                                cliente.getPantalla().RefrescarTablero(barco.getMapa(), barco);
                                cliente.getPantalla().btnComprar.setVisible(false);
                                cliente.getPantalla().lblVendedor.setVisible(false);
                                
                            }
                            
                            // Si el tipo de celda es oro, entonces le aumenta el oro al barco
                            else if (barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.TESORO && barco.getMapa().getArregloCeldas()[y][x].isDescubierta() == false){
                                barco.setOro(barco.getOro() + 20);
                                barco.setDescubrirActivo(true);
                                cliente.getPantalla().btnComprar.setVisible(false);
                                cliente.getPantalla().lblVendedor.setVisible(false);
                                cliente.getPantalla().lblOroC1.setText(Integer.toString(barco.getOro()));
                            }
                            
                            // Si el tipo de celda es amenaza, entonces realiza el ataque
                            else if(barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.AMENAZA){
                                Amenaza amenaza = new Amenaza(cliente.getPantalla(), barco, barco.getMapa().getArregloCeldas()[y][x]);
                                amenaza.start();
                                barco.setDescubrirActivo(true);
                                cliente.getPantalla().btnComprar.setVisible(false);
                                cliente.getPantalla().lblVendedor.setVisible(false);
                                cliente.getPantalla().lblVidaC1.setText(Double.toString(barco.getVida()));
                            }
                            
                            // Si hay un mercado, activa el mercado
                            else if(barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.MERCADO){
                                cliente.getPantalla().btnComprar.setVisible(true);
                                cliente.getPantalla().lblVendedor.setVisible(true);
                            }
                            
                            // Mueve el barco
                            mover = true;
                            barco.MoverBarcoEnMapa(x, y);
                        }
                    }
                    
                   // Si x esta abajo
                    else if(x == barco.getX() && y > barco.getY()){
                        
                        // Si al final el barco esta en el rango de moviento
                        if(barco.getY() + i < 15 && barco.getY() + i == y){
                            
                            // Si hay un barco en esa coordenada
                            if(barco.getMapa().getArregloCeldas()[y][x].isOcupadoXBarco() == true){
                                throw new IllegalArgumentException("Barco existente");
                            }
                            
                            // Si hay una mina que no coloco el barco
                            if(barco.getMapa().getArregloCeldas()[y][x].isMina() == true && barco.getMapa().getArregloCeldas()[y][x].getNumBarcoMina() != barco.getNumBarco()){
                                barco.setVida(barco.getVida()-50);
                                cliente.getPantalla().lblVidaC1.setText(Double.toString(barco.getVida()));
                                barco.descubrirMina = true;
                                JOptionPane.showMessageDialog(null, "Juan: Por la loquera de Misi, hemos pisado una mina!!", "Se piso mina", JOptionPane.INFORMATION_MESSAGE, icon);
                            }
                            
                            // Si el tipo de celda es vacia, no envia el barco para descubirlo
                            if(barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.VACIA){
                                barco.getMapa().getArregloCeldas()[y][x].setDescubierta(true);
                                cliente.getPantalla().RefrescarTablero(barco.getMapa(), barco);
                                cliente.getPantalla().btnComprar.setVisible(false);
                                cliente.getPantalla().lblVendedor.setVisible(false);
                                
                            }
                            
                            // Si el tipo de celda es oro, entonces le aumenta el oro al barco
                            else if (barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.TESORO && barco.getMapa().getArregloCeldas()[y][x].isDescubierta() == false){
                                barco.setOro(barco.getOro() + 20);
                                barco.setDescubrirActivo(true);
                                cliente.getPantalla().btnComprar.setVisible(false);
                                cliente.getPantalla().lblVendedor.setVisible(false);
                                cliente.getPantalla().lblOroC1.setText(Integer.toString(barco.getOro()));
                            }
                            
                            // Si el tipo de celda es amenaza, entonces realiza el ataque
                            else if(barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.AMENAZA){
                                Amenaza amenaza = new Amenaza(cliente.getPantalla(), barco, barco.getMapa().getArregloCeldas()[y][x]);
                                amenaza.start();
                                barco.setDescubrirActivo(true);
                                cliente.getPantalla().btnComprar.setVisible(false);
                                cliente.getPantalla().lblVendedor.setVisible(false);
                                cliente.getPantalla().lblVidaC1.setText(Double.toString(barco.getVida()));
                            }
                            
                            // Si hay un mercado, activa el mercado
                            else if(barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.MERCADO){
                                cliente.getPantalla().btnComprar.setVisible(true);
                                cliente.getPantalla().lblVendedor.setVisible(true);
                            }
                            
                            // Mueve el barco
                            mover = true;
                            barco.MoverBarcoEnMapa(x, y);
                        }
                    }
                    
                   // Si x esta abajo izquierda
                    else if(x < barco.getX() && y > barco.getY()){
                        
                        // Si al final el barco esta en el rango de moviento
                        if(barco.getX() - i > -1 && barco.getX() - i == x && barco.getY() + i < 15 && barco.getY() + i == y){
                            
                            // Si hay un barco en esa coordenada
                            if(barco.getMapa().getArregloCeldas()[y][x].isOcupadoXBarco() == true){
                                throw new IllegalArgumentException("Barco existente");
                            }
                            
                            // Si hay una mina que no coloco el barco
                            if(barco.getMapa().getArregloCeldas()[y][x].isMina() == true && barco.getMapa().getArregloCeldas()[y][x].getNumBarcoMina() != barco.getNumBarco()){
                                barco.setVida(barco.getVida()-50);
                                cliente.getPantalla().lblVidaC1.setText(Double.toString(barco.getVida()));
                                barco.descubrirMina = true;
                                JOptionPane.showMessageDialog(null, "Juan: Por la loquera de Misi, hemos pisado una mina!!", "Se piso mina", JOptionPane.INFORMATION_MESSAGE, icon);
                            }
                            
                            // Si el tipo de celda es vacia, no envia el barco para descubirlo
                            if(barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.VACIA){
                                barco.getMapa().getArregloCeldas()[y][x].setDescubierta(true);
                                cliente.getPantalla().RefrescarTablero(barco.getMapa(), barco);
                                cliente.getPantalla().btnComprar.setVisible(false);
                                cliente.getPantalla().lblVendedor.setVisible(false);
                                
                            }
                            
                            // Si el tipo de celda es oro, entonces le aumenta el oro al barco
                            else if (barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.TESORO && barco.getMapa().getArregloCeldas()[y][x].isDescubierta() == false){
                                barco.setOro(barco.getOro() + 20);
                                barco.setDescubrirActivo(true);
                                cliente.getPantalla().btnComprar.setVisible(false);
                                cliente.getPantalla().lblVendedor.setVisible(false);
                                cliente.getPantalla().lblOroC1.setText(Integer.toString(barco.getOro()));
                            }
                            
                            // Si el tipo de celda es amenaza, entonces realiza el ataque
                            else if(barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.AMENAZA){
                                Amenaza amenaza = new Amenaza(cliente.getPantalla(), barco, barco.getMapa().getArregloCeldas()[y][x]);
                                amenaza.start();
                                barco.setDescubrirActivo(true);
                                cliente.getPantalla().btnComprar.setVisible(false);
                                cliente.getPantalla().lblVendedor.setVisible(false);
                                cliente.getPantalla().lblVidaC1.setText(Double.toString(barco.getVida()));
                            }
                            
                            // Si hay un mercado, activa el mercado
                            else if(barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.MERCADO){
                                cliente.getPantalla().btnComprar.setVisible(true);
                                cliente.getPantalla().lblVendedor.setVisible(true);
                            }
                            
                            // Mueve el barco
                            mover = true;
                            barco.MoverBarcoEnMapa(x, y);
                        }
                    }
                }
                
                // Si al final el mover no es posible, solicitara al usuario una entrada valida
                if(mover == false){
                    cliente.getPantalla().write("Ingrese una coordenada valida");
                }
                
                else{
                    try {
                        
                        // Si el barco murio
                        if(barco.getVida() <= 0){
                            barco.setVida(0);
                            cliente.getPantalla().lblVidaC1.setText(Double.toString(barco.getVida()));
                            barco.Muerte = true;
                            barco = cliente.getPantalla().MuerteDeBarco(barco.getMapa(), barco);
                        }
                        
                        cliente.salida1.reset();
                        cliente.salida1.writeObject(barco);
                        cliente.salida1.flush();
                        barco.setDescubrirActivo(false);
                        
                        // Si al final se ejecuta el comando
                        cliente.salidaDatos.writeInt(cliente.getBarco().getNumBarco());
                        cliente.Turno = false;
                        cliente.threadTurnos.Turno = false;
                        
                        // Si el tipo de celda es todo menos amenaza
                        if(barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() != TipoCelda.AMENAZA){
                            cliente.getPantalla().write("Se movio el barco a las coordenadas (" + x + ", " + y +") y descubrio una celda " + barco.getMapa().getArregloCeldas()[y][x].getTipoCelda().toString());
                        }
                        
                        // Si el tipo de celda es amenaza de tormenta
                        if(barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.AMENAZA && barco.getMapa().getArregloCeldas()[y][x].getTipoAmenaza() == 0){
                            cliente.getPantalla().write("OH NO! Tu barco descubrio una amenaza de tipo tormenta");
                        }
                        
                        // Si no, entonces es un remolino
                        else if(barco.getMapa().getArregloCeldas()[y][x].getTipoCelda() == TipoCelda.AMENAZA && barco.getMapa().getArregloCeldas()[y][x].getTipoAmenaza() == 1){
                            cliente.getPantalla().write("OH NO! Tu barco descubrio una amenaza de tipo remolino");
                        }
                        
                        // Si descubre una mina
                        if(barco.descubrirMina == true){
                            cliente.getPantalla().write("OH NO! Tu barco piso una mina, por lo que se te bajo 50 puntos de vida!!!");
                            barco.descubrirMina = false;
                        }
                        
                    } catch (IOException ex) {
                        //System.out.println("Se cayo moviendose 1");
                    }
                }
                
            } catch (NumberFormatException e) {
               cliente.getPantalla().write("Los argumentos deben ser números enteros.");
            } catch (IllegalArgumentException e){
                JOptionPane.showMessageDialog(null, "Comandante Movimiento: Oye capitán, si nos movemos ahi chocaremos con otro barco!", "Barco Cerca", JOptionPane.INFORMATION_MESSAGE, icon2);
            }
            
        } else {    
           cliente.getPantalla().write("Debe proporcionar dos argumentos numéricos para mover el barco.");
        }
    }        
}
