/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

import Ataques.IAtaques;
import com.mycompany.proyecto2.Mapa.Barco;
import Modelos.Mensaje;
import com.mycompany.proyecto2.Mapa.Mapa;
import com.mycompany.proyecto2.Mapa.TipoCelda;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Tamara
 */
public class Servidor {
    
    // PUERTOS
    private final int PORT1 = 2003;
    private final int PORT2 = 2004;
    private final int PORT3 = 2005;
    private final int PORT4 = 2006;
    
    // SOCKETS
    ServerSocket serverSocket1;
    ServerSocket serverSocket2;
    ServerSocket serverSocket3;
    ServerSocket serverSocket4;
    
    // Pantalla
    PantallaServidor1 pantalla;
    
    // ARRAY DE CLIENTES
    ArrayList<ThreadServidor> clientesConectados;
    
    ArrayList<ThreadServidorMensaje> clientesConectadosMensaje;
    
    ArrayList<ThreadServidorAtaques> clientesConectadosAtaques;
    
    ArrayList<ThreadTurnos> Turnos;

    
    ServerConnectionsThread conexionsThread;
    Mapa mapa;
    
    // Cantidad de oro que tiene cada barco
    int cantidadOroC1;
    int cantidadOroC2;
    int cantidadOroC3;
    int cantidadOroC4;
    
    public int CantidadJugadores;
    public boolean Jugar; // Bool para ver si se puede empezar el juego
    int BarcoVivos; // Cantidad de barcos vivos

    public Servidor(PantallaServidor1 pantalla, Mapa mapa, int CantidadJugadores) {
        
        // Setea la cantidad de oro de los barcos
        this.cantidadOroC1 = 0;
        this.cantidadOroC2 = 0;
        this.cantidadOroC3 = 0;
        this.cantidadOroC4 = 0;
        this.BarcoVivos = CantidadJugadores;
        
        this.CantidadJugadores = CantidadJugadores;
        this.Jugar = false;
        
        this.pantalla = pantalla;
        this.mapa = mapa;
        this.connect();
        
        // Array de los clientes conectados, se enviaran y recibiran barcos
        clientesConectados =  new ArrayList<ThreadServidor>();
        
        // clientesConectados para los mensajes
        clientesConectadosMensaje = new ArrayList<ThreadServidorMensaje>();
        
        // Se enviaran y recibiran ataques
        clientesConectadosAtaques = new ArrayList<ThreadServidorAtaques>();
        
        Turnos = new ArrayList<ThreadTurnos>();
        
        // Thread de conexiones
        conexionsThread = new ServerConnectionsThread(this);
        conexionsThread.start();
    }
    
    private void connect(){
        
        try {
            // Crea los sockets
            serverSocket1 = new ServerSocket(PORT1);
            serverSocket2 = new ServerSocket(PORT2);
            serverSocket3 = new ServerSocket(PORT3);
            serverSocket4 = new ServerSocket(PORT4);

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // ---------------------- FUNCIONES DE CONTAR ---------------------- 
    // Funcion que busca el numero mayor de los cuatro numeros
    public int encontrarMayor(int C1, int C2, int C3, int C4) {
        int mayor = C1;  // Asume que el primer número es el mayor inicialmente
        
        if (C2 > mayor) {
            mayor = C2;  // Actualiza el mayor si el segundo número es mayor
        }
        if (C3 > mayor) {
            mayor = 3;  // Actualiza el mayor si el tercer número es mayor
        }
        if (C4 > mayor) {
            mayor = C4;  // Actualiza el mayor si el cuarto número es mayor
        }
        
        // Si el mayor es el cliente 1, retorna 1
        if(mayor == C1)
            return 1;
        
        // Si el mayor es el cliente 2, retorna 2
        else if(mayor == C2)
            return 2;
        
        // Si el mayor es el cliente 3, retorna 3
        else if(mayor == C3)
            return 3;
        
        // Si no, retorna 4
        else
            return 4;
    }
    
    // Funcion que se encarga de buscar al barco siguiente que poseera el turno
    public int siguienteVivo(int TurnoAnterior){
        
        int NextTurno = 0;
        
        for(ThreadTurnos tsTurnos : Turnos){
            
            // Revisa que el barco no este muerto y que el que envia el turno no sea el anterior
            if(tsTurnos.numCliente != TurnoAnterior && clientesConectados.get(tsTurnos.numCliente-1).barco.Muerte == false){
                return tsTurnos.numCliente;
            }
        }
        
        if(NextTurno == 0)
            NextTurno = TurnoAnterior;
        
        // retorna al que le toca el turno siguiente
        return NextTurno;
    }
    
    // -----------------------------------------------------------------
    
    // Funcion que inicia el juego para todos los jugadores
    public void IniciarJuegoTodos(){
        for(ThreadTurnos tsTurnos : Turnos){
            
            try {
                
                // Envia un true a todos los jugadores para poder empezar el juego
                tsTurnos.salidaDatos.writeBoolean(true);
                tsTurnos.salidaDatos.flush();
                
                
            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    // Funcion que enviara los turnos
    public void enviarTurnos(int LastTurno){
        
        // Setea al que le toque el siguiente turno
        int NextTurno = siguienteVivo(LastTurno);
        
        // recorre el arreglo
        for(ThreadTurnos tsTurnos : Turnos){
            
            // Si se encuentra el server del cliente, se envia el turno
            if(tsTurnos.numCliente == NextTurno){
                
                // Envia el turno donde le corresponde
                try {
                    tsTurnos.salidaDatos.writeBoolean(true);
                    tsTurnos.salidaDatos.flush();
                } catch (IOException ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    //enviarMensajeATodos
    public void broadcastMensaje(Mensaje mensaje){
        for (ThreadServidorMensaje tsDelCliente : clientesConectadosMensaje) {
            try {
                tsDelCliente.salida.writeObject(mensaje);
            } catch (IOException ex) {
                //Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //enviarMensajePrivado
    public void sendPrivateMessage(Mensaje mensaje){
        for (ThreadServidorMensaje tsDelCliente : clientesConectadosMensaje) {
            try {
                                
                if (tsDelCliente.nombre.toUpperCase().equals(mensaje.getReceptor().toUpperCase())){
                     tsDelCliente.salida.writeObject(mensaje);
                }
                
                else if (!tsDelCliente.nombre.toUpperCase().equals(mensaje.getReceptor().toUpperCase()) && tsDelCliente.nombre == ""){
                    throw new IllegalArgumentException("Nombre no existente");
                }
            } catch (IOException ex) {
                //Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            } catch(IllegalArgumentException ex){
                JOptionPane.showMessageDialog(null, "No se encontro el nombre de esa persona en el servidor", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    //enviar  barco a todos
    public void broadcastBarcoMover(Barco barco){
        
        // Si aun continua el juego por el oro descubierto
        if(barco.getMapa().CeldasOroDescubiertas() == false && (BarcoVivos > 1)) {
        
            for (ThreadServidor tsDelCliente : clientesConectados) {
                try {
                    
                    // Si el barco no murio
                    if(barco.Muerte == false){
                        mapa.SetearBarcosEnMapa(barco.getX(), barco.getY(), barco.getNumBarco());
                        pantalla.mapa = this.mapa;


                        // Si al final el barco descubrio algo
                        if(barco.isDescubrirActivo()){
                            mapa.getArregloCeldas()[barco.getY()][barco.getX()].setDescubierta(true);
                        }

                        // Si descubre una mina
                        if(barco.descubrirMina == true){
                            mapa.getArregloCeldas()[barco.getY()][barco.getX()].setMina(false);
                            mapa.getArregloCeldas()[barco.getY()][barco.getX()].setNumBarcoMina(-1);
                        }

                        pantalla.RefrescarTablero();

                        tsDelCliente.barco.getMapa().SetearBarcosEnMapa(barco.getX(), barco.getY(), barco.getNumBarco());

                        tsDelCliente.salida.reset();
                        tsDelCliente.salida.writeObject(barco);
                        tsDelCliente.salida.flush();

                        if(tsDelCliente.numCliente == 1 && tsDelCliente.numCliente == barco.getNumBarco()){
                            pantalla.lblVidaC1.setText(Double.toString(barco.getVida()));
                            pantalla.lblOroC1.setText(Integer.toString(barco.getOro()));
                            pantalla.lblBalasHeavyC1.setText(Integer.toString(barco.getBalasHeavy()));
                            pantalla.lblBalasLongC1.setText(Integer.toString(barco.getBalasLong()));
                            pantalla.lblMinasC1.setText(Integer.toString(barco.getMinas()));
                            pantalla.lblRadarLongC1.setText(Integer.toString(barco.getRadarLong()));
                            pantalla.lblRadarShortC1.setText(Integer.toString(barco.getRadarShort()));
                            pantalla.lblSpotC1.setText(Integer.toString(barco.getSpot()));
                            clientesConectadosAtaques.get(0).barco = barco;
                            cantidadOroC1 = (barco.getOro()) + (barco.getBalasHeavy()* 20) + (barco.getBalasLong()*20) + (barco.getMinas()*20) + (barco.getRadarLong()*20) + (barco.getRadarShort() * 20) + (barco.getSpot()*20);
                        }

                        else if(tsDelCliente.numCliente == 2 && tsDelCliente.numCliente == barco.getNumBarco()){
                            pantalla.lblVidaC2.setText(Double.toString(barco.getVida()));
                            pantalla.lblOroC2.setText(Integer.toString(barco.getOro()));
                            pantalla.lblBalasHeavyC2.setText(Integer.toString(barco.getBalasHeavy()));
                            pantalla.lblBalasLongC2.setText(Integer.toString(barco.getBalasLong()));
                            pantalla.lblMinasC2.setText(Integer.toString(barco.getMinas()));
                            pantalla.lblRadarLongC2.setText(Integer.toString(barco.getRadarLong()));
                            pantalla.lblRadarShortC2.setText(Integer.toString(barco.getRadarShort()));
                            pantalla.lblSpotC2.setText(Integer.toString(barco.getSpot()));
                            clientesConectadosAtaques.get(1).barco = barco;
                            cantidadOroC2 = (barco.getOro()) + (barco.getBalasHeavy()* 20) + (barco.getBalasLong()*20) + (barco.getMinas()*20) + (barco.getRadarLong()*20) + (barco.getRadarShort() * 20) + (barco.getSpot()*20);
                        }

                        else if(tsDelCliente.numCliente == 3 && tsDelCliente.numCliente == barco.getNumBarco()){
                            pantalla.lblVidaC3.setText(Double.toString(barco.getVida()));
                            pantalla.lblOroC3.setText(Integer.toString(barco.getOro()));
                            pantalla.lblBalasHeavyC3.setText(Integer.toString(barco.getBalasHeavy()));
                            pantalla.lblBalasLongC3.setText(Integer.toString(barco.getBalasLong()));
                            pantalla.lblMinasC3.setText(Integer.toString(barco.getMinas()));
                            pantalla.lblRadarLongC3.setText(Integer.toString(barco.getRadarLong()));
                            pantalla.lblRadarShortC3.setText(Integer.toString(barco.getRadarShort()));
                            pantalla.lblSpotC3.setText(Integer.toString(barco.getSpot()));
                            clientesConectadosAtaques.get(2).barco = barco;
                            cantidadOroC3 = (barco.getOro()) + (barco.getBalasHeavy()* 20) + (barco.getBalasLong()*20) + (barco.getMinas()*20) + (barco.getRadarLong()*20) + (barco.getRadarShort() * 20) + (barco.getSpot()*20);
                        }

                        else if(tsDelCliente.numCliente == 4 && tsDelCliente.numCliente == barco.getNumBarco()){
                            pantalla.lblVidaC4.setText(Double.toString(barco.getVida()));
                            pantalla.lblOroC4.setText(Integer.toString(barco.getOro()));
                            pantalla.lblBalasHeavyC4.setText(Integer.toString(barco.getBalasHeavy()));
                            pantalla.lblBalasLongC5.setText(Integer.toString(barco.getBalasLong()));
                            pantalla.lblMinasC5.setText(Integer.toString(barco.getMinas()));
                            pantalla.lblRadarLongC4.setText(Integer.toString(barco.getRadarLong()));
                            pantalla.lblRadarShortC4.setText(Integer.toString(barco.getRadarShort()));
                            pantalla.lblSpotC4.setText(Integer.toString(barco.getSpot()));
                            clientesConectadosAtaques.get(3).barco = barco;
                            cantidadOroC4 = (barco.getOro()) + (barco.getBalasHeavy()* 20) + (barco.getBalasLong()*20) + (barco.getMinas()*20) + (barco.getRadarLong()*20) + (barco.getRadarShort() * 20) + (barco.getSpot()*20);
                        }
                    }
                    
                    // Si el barco mandado murio
                    else {
                        pantalla.MuerteDeBarco(this, barco);
                        pantalla.write("El barco de " + barco.getNombre() + " ha muerto");
                        tsDelCliente.salida.reset();
                        tsDelCliente.salida.writeObject(barco);
                        tsDelCliente.salida.flush();
                        
                        BarcoVivos = barco.getMapa().BarcosVivos();
                    }

                } catch (IOException ex) {
                    //Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            // Si el barco descubrio algo
            if(barco.isDescubrirActivo()){

                // Si el tipo de celda es todo menos amenaza
                if(barco.getMapa().getArregloCeldas()[barco.getY()][barco.getX()].getTipoCelda() != TipoCelda.AMENAZA){
                    pantalla.write("Se movio el barco de " + barco.getNombre() + " a las coordenadas (" + barco.getX() + ", " + barco.getY() +") y descubrio una celda " + barco.getMapa().getArregloCeldas()[barco.getY()][barco.getX()].getTipoCelda().toString());
                }

                // Si el tipo de celda es amenaza de tormenta
                if(barco.getMapa().getArregloCeldas()[barco.getY()][barco.getX()].getTipoCelda() == TipoCelda.AMENAZA && barco.getMapa().getArregloCeldas()[barco.getY()][barco.getX()].getTipoAmenaza() == 0){
                    pantalla.write("El barco de " + barco.getNombre() + " descubrio una amenaza de tipo tormenta");
                }

                // Si no, entonces es un remolino
                else if(barco.getMapa().getArregloCeldas()[barco.getY()][barco.getX()].getTipoCelda() == TipoCelda.AMENAZA && barco.getMapa().getArregloCeldas()[barco.getY()][barco.getX()].getTipoAmenaza() == 1){
                    pantalla.write("El barco de " + barco.getNombre() + " descubrio una amenaza de tipo remolino");
                }
            } 

            // Si al final no descubrio nada
            else if (!barco.isDescubrirActivo()){
                //System.out.println("El barco " + barco.getNombre() + " se movio a la celda " + barco.getX() + ", " + barco.getY());
            }

            // Si descubre una mina
            if(barco.descubrirMina == true){
                pantalla.write("El barco de " + barco.getNombre() + " descubrio una mina");
            } 
        }
        
        // Si solo queda un barco vivo, busca al ganador
        else if(BarcoVivos <= 1){
            
            try {

                for (ThreadServidor tsDelCliente : clientesConectados){

                    // Si el ganador es el Barco 1
                    if(barco.getNumBarco() == 1 && barco.getNumBarco() == tsDelCliente.barco.getNumBarco() && barco.Muerte == false){
                        barco.Ganador = true;
                        tsDelCliente.barco.Ganador = true;
                    }
                    
                    // Si el ganador es el barco 2
                    else if(barco.getNumBarco() == 2 && barco.getNumBarco() == tsDelCliente.barco.getNumBarco() && barco.Muerte == false){
                        barco.Ganador = true;
                        tsDelCliente.barco.Ganador = true;
                    }
                    
                    // Si el ganador es el barco 3
                    else if(barco.getNumBarco() == 3 && barco.getNumBarco() == tsDelCliente.barco.getNumBarco() && barco.Muerte == false){
                        barco.Ganador = true;
                        tsDelCliente.barco.Ganador = true;
                    }
                    
                    // SI el ganador es el barco 4
                    else if(barco.getNumBarco() == 4 && barco.getNumBarco() == tsDelCliente.barco.getNumBarco() && barco.Muerte == false){
                        barco.Ganador = true;
                        tsDelCliente.barco.Ganador = true;
                    }
                    
                    // Si no, significa que el barco perdio
                    else{
                        barco.Perdedor = true;
                        tsDelCliente.barco.Perdedor = true;
                    }
                    
                    // Manda el barco de cada uno al cliente para asi concluir el juego
                    tsDelCliente.salida.reset();
                    tsDelCliente.salida.writeObject(tsDelCliente.barco);
                    tsDelCliente.salida.flush();
                }
                
                Thread.sleep(1000);
                pantalla.dispose();
                
            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        
        // Si al final ya se descubrieron todas las celdas, se acaba el juego
        else if(barco.getMapa().CeldasOroDescubiertas() == true){
            
            try {
                // Consigue el barco con mas oro
                int ClienteConMasOro = encontrarMayor(cantidadOroC1, cantidadOroC2, cantidadOroC3, cantidadOroC4);

                for (ThreadServidor tsDelCliente : clientesConectados){

                    // Si el ganador es el Barco 1
                    if(barco.getNumBarco() == ClienteConMasOro && tsDelCliente.barco.getNumBarco() == barco.getNumBarco()){
                        barco.Ganador = true;
                        tsDelCliente.barco.Ganador = true;
                    }
                    
                    // Si el ganador es el barco 2
                    else if(barco.getNumBarco() == ClienteConMasOro && tsDelCliente.barco.getNumBarco() == barco.getNumBarco()){
                        barco.Ganador = true;
                        tsDelCliente.barco.Ganador = true;
                    }
                    
                    // Si el ganador es el barco 3
                    else if(barco.getNumBarco() == ClienteConMasOro && tsDelCliente.barco.getNumBarco() == barco.getNumBarco()){
                        barco.Ganador = true;
                        tsDelCliente.barco.Ganador = true;
                    }
                    
                    // SI el ganador es el barco 4
                    else if(barco.getNumBarco() == ClienteConMasOro && tsDelCliente.barco.getNumBarco() == barco.getNumBarco()){
                        barco.Ganador = true;
                        tsDelCliente.barco.Ganador = true;
                    }
                    
                    // Si no, significa que el barco perdio
                    else{
                        barco.Perdedor = true;
                        tsDelCliente.barco.Perdedor = true;
                    }
                    
                    // Manda el barco de cada uno al cliente para asi concluir el juego
                    tsDelCliente.salida.reset();
                    tsDelCliente.salida.writeObject(tsDelCliente.barco);
                    tsDelCliente.salida.flush();
                }
                
                Thread.sleep(1000);
                pantalla.dispose();
                
            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
    
    // Funcion para enviar el ataque a la persona que corresponde
    public void enviarAtaque(IAtaques ataque){
        
        Barco barco;
        double vidaBarco;
        if(ataque.getBarco().getMapa().CeldasOroDescubiertas() == false && (BarcoVivos > 1)){
        
            for (ThreadServidorAtaques tsDelClienteAtaque : clientesConectadosAtaques) {

                try {

                    // Si el numero del cliente es igual al atacado del cliente
                    if(tsDelClienteAtaque.numCliente == ataque.getAtacado()){

                        barco = ataque.getBarco();

                        // Envia el ataque al cliente que le corresponde
                        tsDelClienteAtaque.salida.reset();
                        tsDelClienteAtaque.salida.writeObject(ataque);
                        tsDelClienteAtaque.salida.flush();

                        // Realiza el ataque en el servidor
                        ataque.Ataque(tsDelClienteAtaque.barco);

                        vidaBarco = tsDelClienteAtaque.barco.getVida();
                        pantalla.write("El barco " + ataque.getAtacante() + " realizo un ataque al barco " + ataque.getAtacado());

                        // LE BAJA LA VIDA AL ATACADO
                        // Si el cliente es 1, entonces setea la vida del C1
                        if(tsDelClienteAtaque.numCliente == 1){
                            pantalla.lblVidaC1.setText(Double.toString(vidaBarco));

                            // Si el barco murio
                            if(vidaBarco <= 0){
                                pantalla.MuerteDeBarco(this, barco);
                                pantalla.write("El barco de " + barco.getNombre() + " ha muerto");
                                pantalla.lblVidaC1.setText("0");
                                this.broadcastBarcoMover(barco);
                            }
                        }

                        // Si el cliente es 1, entonces setea la vida del C2
                        if(tsDelClienteAtaque.numCliente == 2){
                            pantalla.lblVidaC2.setText(Double.toString(vidaBarco));

                            // Si el barco murio
                            if(vidaBarco <= 0){
                                pantalla.MuerteDeBarco(this, barco);
                                pantalla.write("El barco de " + barco.getNombre() + " ha muerto");
                                pantalla.lblVidaC1.setText("0");
                                this.broadcastBarcoMover(barco);
                            }
                        }

                        // Si el cliente es 1, entonces setea la vida del C3
                        if(tsDelClienteAtaque.numCliente == 3){
                            pantalla.lblVidaC3.setText(Double.toString(vidaBarco));

                            // Si el barco murio
                            if(vidaBarco <= 0){
                                pantalla.MuerteDeBarco(this, barco);
                                pantalla.write("El barco de " + barco.getNombre() + " ha muerto");
                                pantalla.lblVidaC1.setText("0");
                                this.broadcastBarcoMover(barco);
                            }
                        }

                        // Si el cliente es 1, entonces setea la vida del C4
                        if(tsDelClienteAtaque.numCliente == 4){
                            pantalla.lblVidaC4.setText(Double.toString(vidaBarco));

                            // Si el barco murio
                            if(vidaBarco <= 0){
                                pantalla.MuerteDeBarco(this, barco);
                                pantalla.write("El barco de " + barco.getNombre() + " ha muerto");
                                pantalla.lblVidaC1.setText("0");
                                this.broadcastBarcoMover(barco);
                            }
                        }

                    }
                } catch (IOException ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        else if(ataque.getBarco().getMapa().CeldasOroDescubiertas() == true || (BarcoVivos <= 1)){
            this.broadcastBarcoMover(ataque.getBarco());
        }
    }
    
    // Funcion para setearle la mina a todos los barcos
    public void BroadcastMinas(IAtaques ataque){
        
        // For que recorre la los atacantes y les setea el mapa
        for (ThreadServidorAtaques tsDelClienteAtaque : clientesConectadosAtaques){
            
            // Setea la mina donde se debe setear
            mapa.getArregloCeldas()[ataque.getAtacado()][ataque.getAtacante()].setMina(true);
            mapa.getArregloCeldas()[ataque.getAtacado()][ataque.getAtacante()].setNumBarcoMina(ataque.getBarco().getNumBarco());
            pantalla.mapa = mapa;
            pantalla.RefrescarTablero();
            
            // Manda el objeto
            try {
                tsDelClienteAtaque.salida.reset();
                tsDelClienteAtaque.salida.writeObject(ataque);
                tsDelClienteAtaque.salida.flush();
            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    
    }
    
}
