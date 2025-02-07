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

public class ErrorCommand extends BaseCommand {       
    private static final String COMMAND_NAME = "ERROR";       
    
    @Override       
    public String getCommandName() {           
        return COMMAND_NAME;   
    }       
    
    @Override       
    public void execute(String[] args, OutputStream out, Barco barco, int atacante, int receptor, Cliente cliente) {           
        String message = "Error al invocar el comando";   
        write(out, message);   
    }   
}

