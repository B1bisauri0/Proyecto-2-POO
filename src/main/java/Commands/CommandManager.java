/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import java.io.Serializable;
import java.util.HashMap;

public class CommandManager implements Serializable {   
//singleton    
    private static CommandManager commandManager;  
//hash de ICommands: nombre, class que extiende ICommand
    private static final HashMap<String, Class<? extends ICommand>> COMMANDS =          
            new HashMap<String, Class<? extends ICommand>>();       
    
    private CommandManager() {           
        registCommand(ExitCommand.COMMAND_NAME, ExitCommand.class);
        registCommand(Mover.COMMAND_NAME, Mover.class); 
        registCommand(MoverDescubrirCommand.COMMAND_NAME, MoverDescubrirCommand.class); 
        registCommand(ComprarCommand.COMMAND_NAME, ComprarCommand.class);
        registCommand(AtaqueHeavyCommand.COMMAND_NAME, AtaqueHeavyCommand.class); 
        registCommand(AtaqueLongCommand.COMMAND_NAME, AtaqueLongCommand.class); 
        registCommand(MinaCommand.COMMAND_NAME, MinaCommand.class); 
        registCommand(RadarLongCommand.COMMAND_NAME, RadarLongCommand.class); 
        registCommand(RadarShortCommand.COMMAND_NAME, RadarShortCommand.class); 
        registCommand(SpotCommand.COMMAND_NAME, SpotCommand.class); 
        
    }       
    
    public static synchronized CommandManager getIntance() {           
        if (commandManager == null) {               
            commandManager = new CommandManager();   
        }
        return commandManager;   
    }       
    
    // obtiene un ICommand por nombre
    // obtien una instancia con el nombre de la clase
    public ICommand getCommand(String commandName) {           
        if (COMMANDS.containsKey(commandName.toUpperCase())) {               
            try {   
                   //retorna nueva isntancia de comando solicitado
                return COMMANDS.get(commandName.toUpperCase()).newInstance();
            } catch (Exception e) {   
                e.printStackTrace();  
                //retorna comando de error en la exception
                return new ErrorCommand();   
            }           
        } 
        else {
            // retorno de error comando no encontrado
            return new NotFoundCommand();   
        }   
    }
    
    // para registrar un comando, nombre y clase de tipo ICommand
    public void registCommand(String commandName, Class<? extends ICommand> command) {   
        COMMANDS.put(commandName.toUpperCase(), command);   
    }   
}

