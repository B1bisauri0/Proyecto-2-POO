/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import com.mycompany.proyecto2.Mapa.Barco;
import Clientes.Cliente;
import Clientes.PantallaCliente;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JTextArea;


public class CommandMain implements Serializable{
    
    // Método para ejecutar comandos desde la línea de comandos
    public static void ejecutarComando(String comando, Barco barco, int Atacante, int Receptor, Cliente cliente) {
        // Obtener el commandManager
        CommandManager manager = CommandManager.getIntance();
        Scanner in = new Scanner(comando);

        while (in.hasNextLine()) { // Verifica si hay una línea disponible para leer
            String line = in.nextLine();
            if (line.trim().length() == 0) {
                continue;
            }
            String[] commands = CommandUtil.tokenizerArgs(line);
            String commandName = commands[0];
            String[] commandArgs = Arrays.copyOfRange(commands, 1, commands.length);

            ICommand command = manager.getCommand(commandName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            command.execute(commandArgs, ps, barco, Atacante, Receptor, cliente);
            //textArea.append(baos.toString() + "\n"); // Agrega el mensaje al TextArea
        }
        in.close(); // Cierra el Scanner al terminar de usarlo
    }
}

