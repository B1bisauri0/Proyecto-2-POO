/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import com.mycompany.proyecto2.Mapa.Barco;
import Clientes.Cliente;
import Clientes.PantallaCliente;
import java.io.OutputStream;
import java.io.Serializable;

public abstract class BaseCommand implements ICommand, Serializable {       
    
    @Override       
    public abstract String getCommandName();       
    
    @Override       
    public abstract void execute(String[] args, OutputStream out, Barco barco, int Atacante, int Receptor, Cliente cliente);       
    
    public void write(OutputStream out, String message) {           
        try {   
            out.write(message.getBytes());   
            out.flush();           
        } 
        catch (Exception e) {   
            e.printStackTrace();   
        }   
    }   
}

