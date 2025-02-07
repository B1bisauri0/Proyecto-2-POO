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

public class ExitCommand implements ICommand, Serializable{       
    public static final String COMMAND_NAME = "exit";       
    
    @Override       
    public String getCommandName() {           
        return COMMAND_NAME;   
    }       
    
    @Override       
    public void execute(String[] args, OutputStream out, Barco barco, int atacante, int receptor, Cliente cliente) {           
        System.exit(0);   
    }
}

