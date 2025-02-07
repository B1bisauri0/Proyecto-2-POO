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

public interface ICommand {       
    public String getCommandName();       
    public void execute(String[] args, OutputStream out, Barco barco, int numBarcoActual, int numBarcoAtacar, Cliente cliente);   
}
