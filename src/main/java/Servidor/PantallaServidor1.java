/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Servidor;

import static Clientes.PantallaCliente.COLUMNAS;
import static Clientes.PantallaCliente.FILAS;
import com.mycompany.proyecto2.Mapa.Barco;
import com.mycompany.proyecto2.Mapa.Mapa;
import com.mycompany.proyecto2.Mapa.TipoCelda;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Tamara
 */
public class PantallaServidor1 extends javax.swing.JFrame implements Serializable{

    Servidor server;
    
    //constantes para manejar dimensiones de matriz y tamaño del botón
    public static int FILAS = 15;
    public static int COLUMNAS = 15;
    public static int DIMENSION = 20;
    public int CantidadJugadores;
    
    //matriz de botones, quedan todos los botones
    //podría ser una matriz de un objeto que tenga boton como atributo
    //podría ser JLabel también
    public JButton[][] tableroLabels = new JButton[FILAS][COLUMNAS];
    
    Mapa mapa;
    ImagenFondo fondo = new ImagenFondo();

    
    public PantallaServidor1(Mapa mapa, int CantidadJugadores) {
        this.setContentPane(fondo);
        this.mapa = mapa;
        initComponents();
        generarTableros();
        this.setVisible(true);
        this.setResizable(false);
        this.CantidadJugadores = CantidadJugadores;
        
        server = new Servidor(this, mapa, CantidadJugadores);
    }
    
    public void write(String text){
        this.txaInfoServidor1.append(text + "\n");
    }

    
   void generarTableros() {
    
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                // coloca imagen a todos vacío
                tableroLabels[i][j] = new JButton("");
                // añade al panel el botón;
                this.add(tableroLabels[i][j]);
                // coloca dimensiones y localidad
                tableroLabels[i][j].setBounds(10 + DIMENSION * j, 10 + DIMENSION * i, DIMENSION, DIMENSION);
                
                // Si la celda esta vacia tendra este color verde azul todas las celdas vacias
                if(mapa.getArregloCeldas()[i][j].getTipoCelda() == TipoCelda.VACIA){
                    tableroLabels[i][j].setBackground(new Color(7,100,97));
                }
                
                // Si la celda esta tesoro tendra este color dorado todas las celdas tesoro
                else if(mapa.getArregloCeldas()[i][j].getTipoCelda() == TipoCelda.TESORO){
                    tableroLabels[i][j].setBackground(new Color(239,184,36));
                }
                
                // Si la celda esta amenaza tendra este color rosa todas las celdas amenaza
                else if(mapa.getArregloCeldas()[i][j].getTipoCelda() == TipoCelda.AMENAZA){
                    tableroLabels[i][j].setBackground(new Color(212,159,143));
                }
                
                // Si la celda esta mercado tendra este color cafe todas las celdas mercado
                else if(mapa.getArregloCeldas()[i][j].getTipoCelda() == TipoCelda.MERCADO){
                    tableroLabels[i][j].setBackground(new Color(139,96,81));
                }
            }
        }
   }
   
   public void RefrescarTablero(){
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                
                // Si la celda esta un jugador 1 tendra este color azul rey en donde este ese jugador
                if(mapa.getArregloCeldas()[i][j].getNumBarco() == 1){
                    tableroLabels[i][j].setBackground(new Color(0,0,255));
                }

                // Si la celda esta un jugador 2 tendra este color morado en donde este ese jugador
                else if(mapa.getArregloCeldas()[i][j].getNumBarco() == 2){
                    tableroLabels[i][j].setBackground(new Color(120,40,140));
                }
                
                // Si la celda esta un jugador 3 tendra este color rosado en donde este ese jugador
                else if(mapa.getArregloCeldas()[i][j].getNumBarco() == 3){
                    tableroLabels[i][j].setBackground(new Color(140,0,75));
                }
                
                // Si la celda esta un jugador 4 tendra este color rosado en donde este ese jugador
                else if(mapa.getArregloCeldas()[i][j].getNumBarco() == 4){
                    tableroLabels[i][j].setBackground(new Color(141,182,0));
                }
                
                else if(mapa.getArregloCeldas()[i][j].isMina() == true){
                    tableroLabels[i][j].setBackground(new Color(255,255,255));
                }
                
                // Si la celda esta vacia tendra este color verde azul todas las celdas vacias
                else if(mapa.getArregloCeldas()[i][j].getTipoCelda() == TipoCelda.VACIA){
                    tableroLabels[i][j].setBackground(new Color(7,100,97));
                }
                
                // Si la celda esta tesoro tendra este color dorado todas las celdas tesoro
                else if(mapa.getArregloCeldas()[i][j].getTipoCelda() == TipoCelda.TESORO){
                    tableroLabels[i][j].setBackground(new Color(239,184,36));
                }
                
                // Si la celda esta amenaza tendra este color rosa todas las celdas amenaza
                else if(mapa.getArregloCeldas()[i][j].getTipoCelda() == TipoCelda.AMENAZA){
                    tableroLabels[i][j].setBackground(new Color(212,159,143));
                }
                
                // Si la celda esta mercado tendra este color cafe todas las celdas mercado
                else if(mapa.getArregloCeldas()[i][j].getTipoCelda() == TipoCelda.MERCADO){
                    tableroLabels[i][j].setBackground(new Color(139,96,81));
                }
            }
        }
   }
   
   // Funcion para desactivar todo de la pantalla del server
   public void MuerteDeBarco(Servidor server, Barco barco){
       
       
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                
                // Si es el barco 1
                if(mapa.getArregloCeldas()[i][j].getNumBarco() == barco.getNumBarco() && barco.getNumBarco() == 1){
                    mapa.getArregloCeldas()[i][j].setNumBarco(-1);
                    mapa.getArregloCeldas()[i][j].setOcupadoXBarco(false);
                    server.cantidadOroC1 = 0;
                    this.lblVidaC1.setText("0");
                }
                
                // Si es el barco 2
                if(mapa.getArregloCeldas()[i][j].getNumBarco() == barco.getNumBarco() && barco.getNumBarco() == 2){
                    mapa.getArregloCeldas()[i][j].setNumBarco(-1);
                    mapa.getArregloCeldas()[i][j].setOcupadoXBarco(false);
                    server.cantidadOroC2 = 0;
                    this.lblVidaC2.setText("0");
                }
                
                // Si es el barco 3
                if(mapa.getArregloCeldas()[i][j].getNumBarco() == barco.getNumBarco() && barco.getNumBarco() == 3){
                    mapa.getArregloCeldas()[i][j].setNumBarco(-1);
                    mapa.getArregloCeldas()[i][j].setOcupadoXBarco(false);
                    server.cantidadOroC3 = 0;
                    this.lblVidaC3.setText("0");
                }
                
                // Si es el barco 4
                if(mapa.getArregloCeldas()[i][j].getNumBarco() == barco.getNumBarco() && barco.getNumBarco() == 4){
                    mapa.getArregloCeldas()[i][j].setNumBarco(-1);
                    mapa.getArregloCeldas()[i][j].setOcupadoXBarco(false);
                    server.cantidadOroC4 = 0;
                    this.lblVidaC4.setText("0");
                }
            }
        }
        
        this.RefrescarTablero();
   }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblBalasLongC4 = new javax.swing.JLabel();
        lblMinasC4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaChatServidor = new javax.swing.JTextArea();
        lblCliente1Nombre = new javax.swing.JLabel();
        lblCliente2 = new javax.swing.JLabel();
        lblClienteVida = new javax.swing.JLabel();
        lblCliente1 = new javax.swing.JLabel();
        lblClienteOro = new javax.swing.JLabel();
        lblClienteBalasLong = new javax.swing.JLabel();
        lblClienteBalasHeavy = new javax.swing.JLabel();
        lblCliente3 = new javax.swing.JLabel();
        lblClienteMinas = new javax.swing.JLabel();
        lblClienteRadarShort = new javax.swing.JLabel();
        lblClienteRadarLong = new javax.swing.JLabel();
        lblClienteSpot = new javax.swing.JLabel();
        lblCliente4 = new javax.swing.JLabel();
        lblVidaC1 = new javax.swing.JLabel();
        lblVidaC2 = new javax.swing.JLabel();
        lblVidaC3 = new javax.swing.JLabel();
        lblVidaC4 = new javax.swing.JLabel();
        lblOroC1 = new javax.swing.JLabel();
        lblOroC2 = new javax.swing.JLabel();
        lblOroC3 = new javax.swing.JLabel();
        lblOroC4 = new javax.swing.JLabel();
        lblBalasHeavyC1 = new javax.swing.JLabel();
        lblBalasHeavyC2 = new javax.swing.JLabel();
        lblBalasHeavyC3 = new javax.swing.JLabel();
        lblBalasHeavyC4 = new javax.swing.JLabel();
        lblBalasLongC1 = new javax.swing.JLabel();
        lblBalasLongC2 = new javax.swing.JLabel();
        lblBalasLongC3 = new javax.swing.JLabel();
        lblBalasLongC5 = new javax.swing.JLabel();
        lblMinasC1 = new javax.swing.JLabel();
        lblMinasC2 = new javax.swing.JLabel();
        lblMinasC3 = new javax.swing.JLabel();
        lblMinasC5 = new javax.swing.JLabel();
        lblRadarShortC1 = new javax.swing.JLabel();
        lblRadarShortC2 = new javax.swing.JLabel();
        lblRadarShortC3 = new javax.swing.JLabel();
        lblRadarShortC4 = new javax.swing.JLabel();
        lblRadarLongC1 = new javax.swing.JLabel();
        lblRadarLongC2 = new javax.swing.JLabel();
        lblRadarLongC3 = new javax.swing.JLabel();
        lblRadarLongC4 = new javax.swing.JLabel();
        lblSpotC1 = new javax.swing.JLabel();
        lblSpotC2 = new javax.swing.JLabel();
        lblSpotC3 = new javax.swing.JLabel();
        lblSpotC4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaInfoServidor1 = new javax.swing.JTextArea();
        lblChat = new javax.swing.JLabel();

        lblBalasLongC4.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblBalasLongC4.setText("0");

        lblMinasC4.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblMinasC4.setText("0");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txaChatServidor.setBackground(new java.awt.Color(198, 210, 211));
        txaChatServidor.setColumns(20);
        txaChatServidor.setForeground(new java.awt.Color(55, 53, 53));
        txaChatServidor.setRows(5);
        jScrollPane1.setViewportView(txaChatServidor);

        lblCliente1Nombre.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N

        lblCliente2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        lblCliente2.setForeground(new java.awt.Color(128, 64, 0));
        lblCliente2.setText("Jugador 2:");

        lblClienteVida.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblClienteVida.setForeground(new java.awt.Color(128, 64, 0));
        lblClienteVida.setText("Vida");

        lblCliente1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        lblCliente1.setForeground(new java.awt.Color(128, 64, 0));
        lblCliente1.setText("Jugador 1:");

        lblClienteOro.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblClienteOro.setForeground(new java.awt.Color(128, 64, 0));
        lblClienteOro.setText("Oro");

        lblClienteBalasLong.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblClienteBalasLong.setForeground(new java.awt.Color(255, 255, 255));
        lblClienteBalasLong.setText("Balas Long");

        lblClienteBalasHeavy.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblClienteBalasHeavy.setForeground(new java.awt.Color(128, 64, 0));
        lblClienteBalasHeavy.setText("Balas Heavy");

        lblCliente3.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        lblCliente3.setForeground(new java.awt.Color(128, 64, 0));
        lblCliente3.setText("Jugador 3:");

        lblClienteMinas.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblClienteMinas.setForeground(new java.awt.Color(255, 255, 255));
        lblClienteMinas.setText("Minas");

        lblClienteRadarShort.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblClienteRadarShort.setForeground(new java.awt.Color(255, 255, 255));
        lblClienteRadarShort.setText("Radar Short");

        lblClienteRadarLong.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblClienteRadarLong.setForeground(new java.awt.Color(255, 255, 255));
        lblClienteRadarLong.setText("Radar Long");

        lblClienteSpot.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblClienteSpot.setForeground(new java.awt.Color(128, 64, 0));
        lblClienteSpot.setText("Spot");

        lblCliente4.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        lblCliente4.setForeground(new java.awt.Color(128, 64, 0));
        lblCliente4.setText("Jugador 4:");

        lblVidaC1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblVidaC1.setForeground(new java.awt.Color(128, 64, 0));
        lblVidaC1.setText("0");

        lblVidaC2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblVidaC2.setForeground(new java.awt.Color(128, 64, 0));
        lblVidaC2.setText("0");

        lblVidaC3.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblVidaC3.setForeground(new java.awt.Color(128, 64, 0));
        lblVidaC3.setText("0");

        lblVidaC4.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblVidaC4.setForeground(new java.awt.Color(128, 64, 0));
        lblVidaC4.setText("0");

        lblOroC1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblOroC1.setForeground(new java.awt.Color(128, 64, 0));
        lblOroC1.setText("0");

        lblOroC2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblOroC2.setForeground(new java.awt.Color(128, 64, 0));
        lblOroC2.setText("0");

        lblOroC3.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblOroC3.setForeground(new java.awt.Color(128, 64, 0));
        lblOroC3.setText("0");

        lblOroC4.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblOroC4.setForeground(new java.awt.Color(128, 64, 0));
        lblOroC4.setText("0");

        lblBalasHeavyC1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblBalasHeavyC1.setForeground(new java.awt.Color(128, 64, 0));
        lblBalasHeavyC1.setText("0");

        lblBalasHeavyC2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblBalasHeavyC2.setForeground(new java.awt.Color(128, 64, 0));
        lblBalasHeavyC2.setText("0");

        lblBalasHeavyC3.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblBalasHeavyC3.setForeground(new java.awt.Color(128, 64, 0));
        lblBalasHeavyC3.setText("0");

        lblBalasHeavyC4.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblBalasHeavyC4.setForeground(new java.awt.Color(128, 64, 0));
        lblBalasHeavyC4.setText("0");

        lblBalasLongC1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblBalasLongC1.setForeground(new java.awt.Color(255, 255, 255));
        lblBalasLongC1.setText("0");

        lblBalasLongC2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblBalasLongC2.setForeground(new java.awt.Color(255, 255, 255));
        lblBalasLongC2.setText("0");

        lblBalasLongC3.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblBalasLongC3.setForeground(new java.awt.Color(255, 255, 255));
        lblBalasLongC3.setText("0");

        lblBalasLongC5.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblBalasLongC5.setForeground(new java.awt.Color(255, 255, 255));
        lblBalasLongC5.setText("0");

        lblMinasC1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblMinasC1.setForeground(new java.awt.Color(255, 255, 255));
        lblMinasC1.setText("0");

        lblMinasC2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblMinasC2.setForeground(new java.awt.Color(255, 255, 255));
        lblMinasC2.setText("0");

        lblMinasC3.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblMinasC3.setForeground(new java.awt.Color(255, 255, 255));
        lblMinasC3.setText("0");

        lblMinasC5.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblMinasC5.setForeground(new java.awt.Color(255, 255, 255));
        lblMinasC5.setText("0");

        lblRadarShortC1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblRadarShortC1.setForeground(new java.awt.Color(255, 255, 255));
        lblRadarShortC1.setText("0");

        lblRadarShortC2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblRadarShortC2.setForeground(new java.awt.Color(255, 255, 255));
        lblRadarShortC2.setText("0");

        lblRadarShortC3.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblRadarShortC3.setForeground(new java.awt.Color(255, 255, 255));
        lblRadarShortC3.setText("0");

        lblRadarShortC4.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblRadarShortC4.setForeground(new java.awt.Color(255, 255, 255));
        lblRadarShortC4.setText("0");

        lblRadarLongC1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblRadarLongC1.setForeground(new java.awt.Color(255, 255, 255));
        lblRadarLongC1.setText("0");

        lblRadarLongC2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblRadarLongC2.setForeground(new java.awt.Color(255, 255, 255));
        lblRadarLongC2.setText("0");

        lblRadarLongC3.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblRadarLongC3.setForeground(new java.awt.Color(255, 255, 255));
        lblRadarLongC3.setText("0");

        lblRadarLongC4.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblRadarLongC4.setForeground(new java.awt.Color(255, 255, 255));
        lblRadarLongC4.setText("0");

        lblSpotC1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblSpotC1.setForeground(new java.awt.Color(128, 64, 0));
        lblSpotC1.setText("0");

        lblSpotC2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblSpotC2.setForeground(new java.awt.Color(128, 64, 0));
        lblSpotC2.setText("0");

        lblSpotC3.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblSpotC3.setForeground(new java.awt.Color(128, 64, 0));
        lblSpotC3.setText("0");

        lblSpotC4.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblSpotC4.setForeground(new java.awt.Color(128, 64, 0));
        lblSpotC4.setText("0");

        txaInfoServidor1.setEditable(false);
        txaInfoServidor1.setBackground(new java.awt.Color(198, 210, 211));
        txaInfoServidor1.setColumns(20);
        txaInfoServidor1.setForeground(new java.awt.Color(55, 53, 53));
        txaInfoServidor1.setRows(5);
        jScrollPane2.setViewportView(txaInfoServidor1);

        lblChat.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        lblChat.setForeground(new java.awt.Color(255, 255, 255));
        lblChat.setText("Chat");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(124, 124, 124)
                                .addComponent(lblCliente1Nombre))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(12, 12, 12)
                                            .addComponent(lblCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addGap(9, 9, 9)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(lblCliente3)
                                                .addComponent(lblCliente4))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(lblCliente2)))
                                .addGap(76, 76, 76)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblClienteVida)
                                        .addGap(0, 29, Short.MAX_VALUE))
                                    .addComponent(lblVidaC1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblVidaC2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblVidaC3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblVidaC4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblOroC2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(lblBalasHeavyC2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblOroC3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(lblBalasHeavyC3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblOroC1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(lblBalasHeavyC1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblOroC4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(lblBalasHeavyC4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(59, 59, 59)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblBalasLongC2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblBalasLongC3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblBalasLongC5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(56, 56, 56)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblMinasC2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblMinasC3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblMinasC5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(28, 28, 28)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblRadarShortC2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblRadarShortC3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblRadarShortC4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(63, 63, 63)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblRadarLongC2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblRadarLongC3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblRadarLongC1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblRadarLongC4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(61, 61, 61)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblSpotC4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblSpotC2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblSpotC3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblClienteOro)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblClienteBalasHeavy)
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblClienteBalasLong)
                                            .addComponent(lblBalasLongC1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblClienteMinas)
                                            .addComponent(lblMinasC1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblClienteRadarShort)
                                                .addGap(18, 18, 18)
                                                .addComponent(lblClienteRadarLong))
                                            .addComponent(lblRadarShortC1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblSpotC1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblClienteSpot))))))
                        .addGap(34, 34, 34))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1))
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(344, 344, 344)
                .addComponent(lblChat)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(293, Short.MAX_VALUE)
                .addComponent(lblCliente1Nombre)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblClienteVida)
                                .addGap(14, 14, 14)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblCliente1)
                                    .addComponent(lblVidaC1)
                                    .addComponent(lblOroC1)
                                    .addComponent(lblBalasHeavyC1)
                                    .addComponent(lblBalasLongC1)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblMinasC1)
                                .addComponent(lblRadarShortC1)
                                .addComponent(lblRadarLongC1)
                                .addComponent(lblSpotC1)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCliente2)
                            .addComponent(lblVidaC2)
                            .addComponent(lblOroC2)
                            .addComponent(lblBalasHeavyC2)
                            .addComponent(lblBalasLongC2)
                            .addComponent(lblMinasC2)
                            .addComponent(lblRadarShortC2)
                            .addComponent(lblRadarLongC2)
                            .addComponent(lblSpotC2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCliente3)
                            .addComponent(lblVidaC3)
                            .addComponent(lblOroC3)
                            .addComponent(lblBalasHeavyC3)
                            .addComponent(lblBalasLongC3)
                            .addComponent(lblMinasC3)
                            .addComponent(lblRadarShortC3)
                            .addComponent(lblRadarLongC3)
                            .addComponent(lblSpotC3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCliente4)
                            .addComponent(lblVidaC4)
                            .addComponent(lblOroC4)
                            .addComponent(lblBalasHeavyC4)
                            .addComponent(lblBalasLongC5)
                            .addComponent(lblMinasC5)
                            .addComponent(lblRadarShortC4)
                            .addComponent(lblRadarLongC4)
                            .addComponent(lblSpotC4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblClienteOro)
                        .addComponent(lblClienteBalasLong)
                        .addComponent(lblClienteBalasHeavy)
                        .addComponent(lblClienteMinas)
                        .addComponent(lblClienteRadarShort)
                        .addComponent(lblClienteRadarLong)
                        .addComponent(lblClienteSpot)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblChat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JLabel lblBalasHeavyC1;
    public javax.swing.JLabel lblBalasHeavyC2;
    public javax.swing.JLabel lblBalasHeavyC3;
    public javax.swing.JLabel lblBalasHeavyC4;
    public javax.swing.JLabel lblBalasLongC1;
    public javax.swing.JLabel lblBalasLongC2;
    public javax.swing.JLabel lblBalasLongC3;
    private javax.swing.JLabel lblBalasLongC4;
    public javax.swing.JLabel lblBalasLongC5;
    public javax.swing.JLabel lblChat;
    public javax.swing.JLabel lblCliente1;
    private javax.swing.JLabel lblCliente1Nombre;
    public javax.swing.JLabel lblCliente2;
    public javax.swing.JLabel lblCliente3;
    public javax.swing.JLabel lblCliente4;
    private javax.swing.JLabel lblClienteBalasHeavy;
    private javax.swing.JLabel lblClienteBalasLong;
    private javax.swing.JLabel lblClienteMinas;
    private javax.swing.JLabel lblClienteOro;
    private javax.swing.JLabel lblClienteRadarLong;
    private javax.swing.JLabel lblClienteRadarShort;
    private javax.swing.JLabel lblClienteSpot;
    private javax.swing.JLabel lblClienteVida;
    public javax.swing.JLabel lblMinasC1;
    public javax.swing.JLabel lblMinasC2;
    public javax.swing.JLabel lblMinasC3;
    private javax.swing.JLabel lblMinasC4;
    public javax.swing.JLabel lblMinasC5;
    public javax.swing.JLabel lblOroC1;
    public javax.swing.JLabel lblOroC2;
    public javax.swing.JLabel lblOroC3;
    public javax.swing.JLabel lblOroC4;
    public javax.swing.JLabel lblRadarLongC1;
    public javax.swing.JLabel lblRadarLongC2;
    public javax.swing.JLabel lblRadarLongC3;
    public javax.swing.JLabel lblRadarLongC4;
    public javax.swing.JLabel lblRadarShortC1;
    public javax.swing.JLabel lblRadarShortC2;
    public javax.swing.JLabel lblRadarShortC3;
    public javax.swing.JLabel lblRadarShortC4;
    public javax.swing.JLabel lblSpotC1;
    public javax.swing.JLabel lblSpotC2;
    public javax.swing.JLabel lblSpotC3;
    public javax.swing.JLabel lblSpotC4;
    public javax.swing.JLabel lblVidaC1;
    public javax.swing.JLabel lblVidaC2;
    public javax.swing.JLabel lblVidaC3;
    public javax.swing.JLabel lblVidaC4;
    public javax.swing.JTextArea txaChatServidor;
    private javax.swing.JTextArea txaInfoServidor1;
    // End of variables declaration//GEN-END:variables
    
    // Clase de la imagen del fondo, esto es para que el fondo se adapte al tamano que se ponga de la pantalla
    class ImagenFondo extends JPanel{
    
        //Atributos
        private Image imagen;

        //Metodos
        public void paint (Graphics g) {
            imagen = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\Imagenes\\FondoPantallaJuego.png").getImage();
            g.drawImage(imagen, 0, 0, getWidth(), getHeight() ,rootPane);
            setOpaque(false);
            super.paint(g);
        }
    }

}
