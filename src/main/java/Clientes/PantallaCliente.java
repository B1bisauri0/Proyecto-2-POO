/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Clientes;

import com.mycompany.proyecto2.Mapa.Barco;
import Commands.CommandMain;
import Modelos.Mensaje;
import com.mycompany.proyecto2.Mapa.Mapa;
import com.mycompany.proyecto2.Mapa.TipoCelda;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Tamara
 */
public class PantallaCliente extends javax.swing.JFrame implements Serializable {

    public Cliente cliente;
    Mapa mapa;
    ImagenFondo fondo = new ImagenFondo();
    String nombre;
    public PantallaInicioClientes pantallaInicio;
    
    //constantes para manejar dimensiones de matriz y tamaño del botón
    public static int FILAS = 15;
    public static int COLUMNAS = 15;
    public static int DIMENSION = 20;
    
    //matriz de botones, quedan todos los botones
    //podría ser una matriz de un objeto que tenga boton como atributo
    //podría ser JLabel también
    public JButton[][] tableroLabels = new JButton[FILAS][COLUMNAS];
    

    /**
     * Creates new form PantallaCliente
     */
    public PantallaCliente(String nombre, PantallaInicioClientes pantallaInicio) {
        
        this.nombre = nombre;
        this.pantallaInicio = pantallaInicio;
        
        this.setContentPane(fondo);
        this.setResizable(false);
        initComponents();
        this.lblAmenaza.setVisible(false);
        
        // Les quita el fondo a los botones
        this.rbtnBalasHeavy.setOpaque(false);
        this.rbtnBalasLong.setOpaque(false);
        this.rbtnMinas.setOpaque(false);
        this.rbtnRadarLong.setOpaque(false);
        this.rbtnRadarShort.setOpaque(false);
        this.rbtnSpot.setOpaque(false);
        
        // Quita los botones de la tienda
        btnComprar.setVisible(false);
        lblVendedor.setVisible(false);
        
        cliente = new Cliente(this, nombre);        
    } 
    
    public void write(String text){
        this.txaInfoClienteComandos.append(text + "\n");
    }

    
   public void generarTableros(Mapa mapa, Barco barco) {
    
       this.mapa = mapa;
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                // coloca imagen a todos vacío
                tableroLabels[i][j] = new JButton("");
                // añade al panel el botón;
                this.add(tableroLabels[i][j]);
                // coloca dimensiones y localidad
                tableroLabels[i][j].setBounds(10 + DIMENSION * j, 10 + DIMENSION * i, DIMENSION, DIMENSION);
                
                
                // Si la celda esta un jugador 1 tendra este color azul rey en donde este ese jugador
                if(mapa.getArregloCeldas()[i][j].getNumBarco() == barco.getNumBarco() && mapa.getArregloCeldas()[i][j].getNumBarco() == 1){
                    tableroLabels[i][j].setBackground(new Color(0,0,255));
                }
                
                // Si la celda esta un jugador 2 tendra este color morado en donde este ese jugador
                else if(mapa.getArregloCeldas()[i][j].getNumBarco() == barco.getNumBarco() && mapa.getArregloCeldas()[i][j].getNumBarco() == 2){
                    tableroLabels[i][j].setBackground(new Color(120,40,140));
                }
                
                // Si la celda esta un jugador 3 tendra este color rosado en donde este ese jugador
                else if(mapa.getArregloCeldas()[i][j].getNumBarco() == barco.getNumBarco() && mapa.getArregloCeldas()[i][j].getNumBarco() == 3){
                    tableroLabels[i][j].setBackground(new Color(140,0,75));
                }
                
                // Si la celda esta un jugador 4 tendra este color rosado en donde este ese jugador
                else if(mapa.getArregloCeldas()[i][j].getNumBarco() == barco.getNumBarco() && mapa.getArregloCeldas()[i][j].getNumBarco() == 4){
                    tableroLabels[i][j].setBackground(new Color(141,182,0));
                }
                
                // Si hay una mina que coloco el barco
                else if(mapa.getArregloCeldas()[i][j].getNumBarcoMina() == barco.getNumBarco() && mapa.getArregloCeldas()[i][j].isMina() == true){
                    tableroLabels[i][j].setBackground(new Color(255,255,255));
                }
                
                // Si la celda esta mercado tendra este color cafe todas las celdas mercado
                else if(mapa.getArregloCeldas()[i][j].getTipoCelda() == TipoCelda.MERCADO){
                    tableroLabels[i][j].setBackground(new Color(139,96,81));
                }
                
                // Si la celda esta vacia tendra este color verde azul todas las celdas vacias
                else if(mapa.getArregloCeldas()[i][j].isDescubierta() == false){
                    tableroLabels[i][j].setBackground(new Color(127,126,128));
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
            }
        }
   }
   
   public void RefrescarTablero(Mapa mapa, Barco barco){
       
       this.mapa = mapa;
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                
                // Si la celda esta un jugador 1 tendra este color azul rey en donde este ese jugador
                if(mapa.getArregloCeldas()[i][j].getNumBarco() == barco.getNumBarco() && mapa.getArregloCeldas()[i][j].getNumBarco() == 1){
                    tableroLabels[i][j].setBackground(new Color(0,0,255));
                }
                
                // Si la celda esta un jugador 2 tendra este color morado en donde este ese jugador
                else if(mapa.getArregloCeldas()[i][j].getNumBarco() == barco.getNumBarco() && mapa.getArregloCeldas()[i][j].getNumBarco() == 2){
                    tableroLabels[i][j].setBackground(new Color(120,40,140));
                }
                
                // Si la celda esta un jugador 3 tendra este color rosado en donde este ese jugador
                else if(mapa.getArregloCeldas()[i][j].getNumBarco() == barco.getNumBarco() && mapa.getArregloCeldas()[i][j].getNumBarco() == 3){
                    tableroLabels[i][j].setBackground(new Color(140,0,75));
                }
                
                // Si la celda esta un jugador 4 tendra este color rosado en donde este ese jugador
                else if(mapa.getArregloCeldas()[i][j].getNumBarco() == barco.getNumBarco() && mapa.getArregloCeldas()[i][j].getNumBarco() == 4){
                    tableroLabels[i][j].setBackground(new Color(141,182,0));
                }
                
                // Si hay una mina que coloco el barco
                else if(mapa.getArregloCeldas()[i][j].getNumBarcoMina() == barco.getNumBarco() && mapa.getArregloCeldas()[i][j].isMina() == true){
                    tableroLabels[i][j].setBackground(new Color(255,255,255));
                }
                
                // Si la celda esta mercado tendra este color cafe todas las celdas mercado
                else if(mapa.getArregloCeldas()[i][j].getTipoCelda() == TipoCelda.MERCADO){
                    tableroLabels[i][j].setBackground(new Color(139,96,81));
                }
                
                // Si la celda esta vacia tendra este color verde azul todas las celdas vacias
                else if(mapa.getArregloCeldas()[i][j].isDescubierta() == false){
                    tableroLabels[i][j].setBackground(new Color(127,126,128));
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
                
            }
        }
   }
   
   // Funcion para desactivar todo de la pantalla del cliente
   public Barco MuerteDeBarco(Mapa mapa, Barco barco){
       
       ImageIcon icon = new javax.swing.ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\Imagenes\\Angel.png");
       
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                
                if(mapa.getArregloCeldas()[i][j].getNumBarco() == barco.getNumBarco()){
                    mapa.getArregloCeldas()[i][j].setNumBarco(-1);
                    mapa.getArregloCeldas()[i][j].setOcupadoXBarco(false);
                    barco.setMapa(mapa);
                    
                    // Desactiva todos los botones
                    this.btnComprar.setVisible(false);
                    this.txfComandos.setVisible(false);
                    this.txfBalasHeavy.setVisible(false);
                    this.txfBalasLong.setVisible(false);
                    this.txfMinas.setVisible(false);
                    this.txfRadarLong.setVisible(false);
                    this.txfRadarShort.setVisible(false);
                    this.txfSpot.setVisible(false);
                    this.rbtnBalasHeavy.setVisible(false);
                    this.rbtnBalasLong.setVisible(false);
                    this.rbtnMinas.setVisible(false);
                    this.rbtnRadarLong.setVisible(false);
                    this.rbtnRadarShort.setVisible(false);
                    this.rbtnSpot.setVisible(false);
                    this.lblVendedor.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\Imagenes\\Angel.png"));
                    this.lblVendedor.setVisible(true);
                    this.lblBalas.setVisible(false);
                    this.lblRadares.setVisible(false);
                    JOptionPane.showMessageDialog(null, "Angel: Has muerto!", "Murio", JOptionPane.INFORMATION_MESSAGE, icon);
                }
            }
        }
        
        this.mapa = mapa;
        this.RefrescarTablero(mapa, barco);
        return barco;
   }
   
    public void ejecutarComando() {
        String comando = txfComandos.getText();
        CommandMain.ejecutarComando(comando, cliente.barco, cliente.barco.getNumBarco(), 1, cliente); // Pasar el JTextArea como segundo argumento
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblSpotC1 = new javax.swing.JLabel();
        lblBalasHeavyC1 = new javax.swing.JLabel();
        lblClienteVida = new javax.swing.JLabel();
        lblCliente1 = new javax.swing.JLabel();
        lblClienteOro = new javax.swing.JLabel();
        lblBalasLongC1 = new javax.swing.JLabel();
        lblClienteBalasLong = new javax.swing.JLabel();
        lblClienteBalasHeavy = new javax.swing.JLabel();
        lblMinasC1 = new javax.swing.JLabel();
        lblClienteMinas = new javax.swing.JLabel();
        lblClienteRadarShort = new javax.swing.JLabel();
        lblClienteRadarLong = new javax.swing.JLabel();
        lblClienteSpot = new javax.swing.JLabel();
        lblRadarShortC1 = new javax.swing.JLabel();
        lblVidaC1 = new javax.swing.JLabel();
        lblRadarLongC1 = new javax.swing.JLabel();
        lblOroC1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaInfoClienteComandos = new javax.swing.JTextArea();
        btnEnviarMensaje = new javax.swing.JButton();
        btnEnviarMensajePriv = new javax.swing.JButton();
        txfComandos = new javax.swing.JTextField();
        txfChat = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txaChat = new javax.swing.JTextArea();
        lblChat = new javax.swing.JLabel();
        txfNombreChat = new javax.swing.JTextField();
        lblAmenaza = new javax.swing.JLabel();
        lblBalas = new javax.swing.JLabel();
        lblRadares = new javax.swing.JLabel();
        lblCliente2 = new javax.swing.JLabel();
        lblVendedor = new javax.swing.JLabel();
        rbtnBalasLong = new javax.swing.JRadioButton();
        rbtnRadarLong = new javax.swing.JRadioButton();
        rbtnRadarShort = new javax.swing.JRadioButton();
        rbtnBalasHeavy = new javax.swing.JRadioButton();
        txfRadarShort = new javax.swing.JTextField();
        txfRadarLong = new javax.swing.JTextField();
        txfBalasHeavy = new javax.swing.JTextField();
        txfBalasLong = new javax.swing.JTextField();
        txfMinas = new javax.swing.JTextField();
        rbtnMinas = new javax.swing.JRadioButton();
        txfSpot = new javax.swing.JTextField();
        rbtnSpot = new javax.swing.JRadioButton();
        btnComprar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblSpotC1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblSpotC1.setForeground(new java.awt.Color(255, 255, 255));
        lblSpotC1.setText("0");

        lblBalasHeavyC1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblBalasHeavyC1.setForeground(new java.awt.Color(155, 178, 194));
        lblBalasHeavyC1.setText("0");

        lblClienteVida.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblClienteVida.setForeground(new java.awt.Color(155, 178, 194));
        lblClienteVida.setText("Vida");

        lblCliente1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        lblCliente1.setForeground(new java.awt.Color(155, 178, 194));
        lblCliente1.setText("Jugador 1:");

        lblClienteOro.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblClienteOro.setForeground(new java.awt.Color(155, 178, 194));
        lblClienteOro.setText("Oro");

        lblBalasLongC1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblBalasLongC1.setForeground(java.awt.Color.white);
        lblBalasLongC1.setText("0");

        lblClienteBalasLong.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblClienteBalasLong.setForeground(new java.awt.Color(255, 255, 255));
        lblClienteBalasLong.setText("Balas Long");

        lblClienteBalasHeavy.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblClienteBalasHeavy.setForeground(new java.awt.Color(155, 178, 194));
        lblClienteBalasHeavy.setText("Balas Heavy");

        lblMinasC1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblMinasC1.setForeground(new java.awt.Color(255, 255, 255));
        lblMinasC1.setText("0");

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
        lblClienteSpot.setForeground(new java.awt.Color(255, 255, 255));
        lblClienteSpot.setText("Spot");

        lblRadarShortC1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblRadarShortC1.setForeground(new java.awt.Color(255, 255, 255));
        lblRadarShortC1.setText("0");

        lblVidaC1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblVidaC1.setForeground(new java.awt.Color(155, 178, 194));
        lblVidaC1.setText("0");

        lblRadarLongC1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblRadarLongC1.setForeground(new java.awt.Color(255, 255, 255));
        lblRadarLongC1.setText("0");

        lblOroC1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        lblOroC1.setForeground(new java.awt.Color(155, 178, 194));
        lblOroC1.setText("0");

        txaInfoClienteComandos.setEditable(false);
        txaInfoClienteComandos.setBackground(new java.awt.Color(198, 210, 211));
        txaInfoClienteComandos.setColumns(20);
        txaInfoClienteComandos.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        txaInfoClienteComandos.setForeground(new java.awt.Color(102, 102, 102));
        txaInfoClienteComandos.setRows(5);
        jScrollPane2.setViewportView(txaInfoClienteComandos);

        btnEnviarMensaje.setBackground(new java.awt.Color(198, 210, 211));
        btnEnviarMensaje.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        btnEnviarMensaje.setForeground(new java.awt.Color(255, 255, 255));
        btnEnviarMensaje.setText("Enviar Mensaje a TODOS");
        btnEnviarMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarMensajeActionPerformed(evt);
            }
        });

        btnEnviarMensajePriv.setBackground(new java.awt.Color(198, 210, 211));
        btnEnviarMensajePriv.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        btnEnviarMensajePriv.setForeground(new java.awt.Color(255, 255, 255));
        btnEnviarMensajePriv.setText("Enviar Mensaje privado");
        btnEnviarMensajePriv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarMensajePrivActionPerformed(evt);
            }
        });

        txfComandos.setBackground(new java.awt.Color(198, 210, 211));
        txfComandos.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        txfComandos.setForeground(new java.awt.Color(102, 102, 102));
        txfComandos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfComandosActionPerformed(evt);
            }
        });

        txfChat.setBackground(new java.awt.Color(198, 210, 211));
        txfChat.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        txfChat.setForeground(new java.awt.Color(102, 102, 102));
        txfChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfChatActionPerformed(evt);
            }
        });
        txfChat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txfChatKeyPressed(evt);
            }
        });

        txaChat.setEditable(false);
        txaChat.setBackground(new java.awt.Color(198, 210, 211));
        txaChat.setColumns(20);
        txaChat.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        txaChat.setForeground(new java.awt.Color(102, 102, 102));
        txaChat.setRows(5);
        jScrollPane3.setViewportView(txaChat);

        lblChat.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        lblChat.setForeground(java.awt.Color.white);
        lblChat.setText("Chat");

        txfNombreChat.setBackground(new java.awt.Color(198, 210, 211));
        txfNombreChat.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        txfNombreChat.setForeground(new java.awt.Color(102, 102, 102));
        txfNombreChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfNombreChatActionPerformed(evt);
            }
        });

        lblBalas.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\Imagenes\\Balas.png"));

        lblRadares.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\Imagenes\\Radares.png"));

        lblCliente2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        lblCliente2.setForeground(new java.awt.Color(68, 108, 114));
        lblCliente2.setText("Tienda");

        lblVendedor.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\Imagenes\\Comerciantes.png"));

        rbtnBalasLong.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        rbtnBalasLong.setForeground(new java.awt.Color(255, 255, 255));
        rbtnBalasLong.setText("Balas Long");
        rbtnBalasLong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnBalasLongActionPerformed(evt);
            }
        });

        rbtnRadarLong.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        rbtnRadarLong.setForeground(new java.awt.Color(255, 255, 255));
        rbtnRadarLong.setText("Radar Long");
        rbtnRadarLong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnRadarLongActionPerformed(evt);
            }
        });

        rbtnRadarShort.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        rbtnRadarShort.setForeground(new java.awt.Color(255, 255, 255));
        rbtnRadarShort.setText("Radar Short");
        rbtnRadarShort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnRadarShortActionPerformed(evt);
            }
        });

        rbtnBalasHeavy.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        rbtnBalasHeavy.setForeground(new java.awt.Color(255, 255, 255));
        rbtnBalasHeavy.setText("Balas Heavy");
        rbtnBalasHeavy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnBalasHeavyActionPerformed(evt);
            }
        });

        txfRadarShort.setBackground(new java.awt.Color(155, 178, 194));
        txfRadarShort.setFont(new java.awt.Font("Tempus Sans ITC", 1, 12)); // NOI18N
        txfRadarShort.setForeground(new java.awt.Color(255, 255, 255));
        txfRadarShort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfRadarShortActionPerformed(evt);
            }
        });

        txfRadarLong.setBackground(new java.awt.Color(155, 178, 194));
        txfRadarLong.setFont(new java.awt.Font("Tempus Sans ITC", 1, 12)); // NOI18N
        txfRadarLong.setForeground(new java.awt.Color(255, 255, 255));
        txfRadarLong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfRadarLongActionPerformed(evt);
            }
        });

        txfBalasHeavy.setBackground(new java.awt.Color(155, 178, 194));
        txfBalasHeavy.setFont(new java.awt.Font("Tempus Sans ITC", 1, 12)); // NOI18N
        txfBalasHeavy.setForeground(new java.awt.Color(255, 255, 255));
        txfBalasHeavy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfBalasHeavyActionPerformed(evt);
            }
        });

        txfBalasLong.setBackground(new java.awt.Color(155, 178, 194));
        txfBalasLong.setFont(new java.awt.Font("Tempus Sans ITC", 1, 12)); // NOI18N
        txfBalasLong.setForeground(new java.awt.Color(255, 255, 255));
        txfBalasLong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfBalasLongActionPerformed(evt);
            }
        });

        txfMinas.setBackground(new java.awt.Color(155, 178, 194));
        txfMinas.setFont(new java.awt.Font("Tempus Sans ITC", 1, 12)); // NOI18N
        txfMinas.setForeground(new java.awt.Color(255, 255, 255));
        txfMinas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfMinasActionPerformed(evt);
            }
        });

        rbtnMinas.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        rbtnMinas.setForeground(new java.awt.Color(255, 255, 255));
        rbtnMinas.setText("Minas");
        rbtnMinas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnMinasActionPerformed(evt);
            }
        });

        txfSpot.setBackground(new java.awt.Color(155, 178, 194));
        txfSpot.setFont(new java.awt.Font("Tempus Sans ITC", 1, 12)); // NOI18N
        txfSpot.setForeground(new java.awt.Color(255, 255, 255));
        txfSpot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfSpotActionPerformed(evt);
            }
        });

        rbtnSpot.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        rbtnSpot.setForeground(new java.awt.Color(255, 255, 255));
        rbtnSpot.setText("Spot");
        rbtnSpot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnSpotActionPerformed(evt);
            }
        });

        btnComprar.setBackground(new java.awt.Color(155, 178, 194));
        btnComprar.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        btnComprar.setForeground(new java.awt.Color(255, 255, 255));
        btnComprar.setText("Comprar");
        btnComprar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txfComandos, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblCliente1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 884, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(162, 162, 162)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblVidaC1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblOroC1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(21, 21, 21)
                                        .addComponent(lblBalasHeavyC1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblBalasLongC1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblMinasC1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblRadarShortC1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblRadarLongC1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblSpotC1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblClienteVida)
                                        .addGap(44, 44, 44)
                                        .addComponent(lblClienteOro)
                                        .addGap(41, 41, 41)
                                        .addComponent(lblClienteBalasHeavy)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblClienteBalasLong)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblClienteMinas)
                                        .addGap(31, 31, 31)
                                        .addComponent(lblClienteRadarShort)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblClienteRadarLong)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblClienteSpot))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addComponent(btnComprar)
                                        .addGap(39, 39, 39))))))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnEnviarMensaje)
                                .addComponent(txfChat, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txfNombreChat, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnEnviarMensajePriv)))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(468, 468, 468)
                            .addComponent(lblChat))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 884, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblAmenaza, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txfBalasHeavy, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rbtnBalasLong)
                            .addComponent(txfBalasLong, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblBalas, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(rbtnBalasHeavy, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(rbtnMinas)
                            .addComponent(txfMinas, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txfRadarShort, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rbtnRadarLong)
                            .addComponent(txfRadarLong, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblRadares, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(rbtnRadarShort))
                            .addComponent(rbtnSpot)
                            .addComponent(txfSpot, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(49, 49, 49))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblCliente2)
                        .addGap(161, 161, 161))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblCliente2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblBalas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblRadares, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(rbtnBalasHeavy)
                                        .addGap(6, 6, 6)
                                        .addComponent(txfBalasHeavy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(7, 7, 7)
                                        .addComponent(rbtnBalasLong)
                                        .addGap(6, 6, 6)
                                        .addComponent(txfBalasLong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rbtnMinas)
                                        .addGap(6, 6, 6)
                                        .addComponent(txfMinas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(rbtnRadarShort)
                                        .addGap(6, 6, 6)
                                        .addComponent(txfRadarShort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(7, 7, 7)
                                        .addComponent(rbtnRadarLong)
                                        .addGap(6, 6, 6)
                                        .addComponent(txfRadarLong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rbtnSpot)
                                        .addGap(6, 6, 6)
                                        .addComponent(txfSpot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(18, 18, 18)
                        .addComponent(btnComprar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblAmenaza, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblClienteVida)
                    .addComponent(lblClienteOro)
                    .addComponent(lblClienteBalasHeavy)
                    .addComponent(lblClienteBalasLong)
                    .addComponent(lblClienteMinas)
                    .addComponent(lblClienteRadarShort)
                    .addComponent(lblClienteRadarLong)
                    .addComponent(lblClienteSpot))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCliente1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblVidaC1)
                            .addComponent(lblOroC1)
                            .addComponent(lblBalasHeavyC1)
                            .addComponent(lblBalasLongC1)
                            .addComponent(lblMinasC1)
                            .addComponent(lblRadarShortC1)
                            .addComponent(lblRadarLongC1)
                            .addComponent(lblSpotC1))))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(txfComandos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblChat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txfNombreChat, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEnviarMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEnviarMensajePriv, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarMensajeActionPerformed
        
        // Envia un mensaje publico
        try {
            Mensaje m =  new Mensaje(cliente.nombre, txfChat.getText());
            cliente.salida2.writeObject(m);
            this.txfChat.setText("");
            
        } catch (IOException ex) {
            Logger.getLogger(PantallaCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_btnEnviarMensajeActionPerformed

    private void btnEnviarMensajePrivActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarMensajePrivActionPerformed

        
        try {
            Mensaje m =  new Mensaje(cliente.nombre, txfChat.getText(), txfNombreChat.getText());
            cliente.salida2.writeObject(m);
            this.txfChat.setText("");
            this.txfNombreChat.setText("");
        } catch (IOException ex) {
            Logger.getLogger(PantallaCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btnEnviarMensajePrivActionPerformed

    private void txfChatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txfChatKeyPressed

    }//GEN-LAST:event_txfChatKeyPressed

    private void txfChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfChatActionPerformed
        // Envia un mensaje publico
        try {
            Mensaje m =  new Mensaje(cliente.nombre, txfChat.getText());
            cliente.salida2.writeObject(m);
            this.txfChat.setText("");
            
        } catch (IOException ex) {
            Logger.getLogger(PantallaCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_txfChatActionPerformed

    private void txfNombreChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfNombreChatActionPerformed
        
        try {
            Mensaje m =  new Mensaje(cliente.nombre, txfChat.getText(), txfNombreChat.getText());
            cliente.salida2.writeObject(m);
            this.txfChat.setText("");
            this.txfNombreChat.setText("");
        } catch (IOException ex) {
            Logger.getLogger(PantallaCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txfNombreChatActionPerformed

    private void txfComandosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfComandosActionPerformed

        ImageIcon icon = new javax.swing.ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\Imagenes\\TripulanteEnojado.png");
        
        // Si el turno del cliente esta activo
        if(cliente.Turno == true){
            
            // Si no se desea skipear de turno
            if(!txfComandos.getText().equals("Skip")){
                ejecutarComando();
                this.txfComandos.setText("");
            }
            
            // Si es vacio, se toma como un skip y se salta de turno
            else if(txfComandos.getText().equals("Skip")){
                try {
                    // Si al final se ejecuta el comando
                    cliente.salidaDatos.writeInt(cliente.barco.getNumBarco());
                    cliente.Turno = false;
                    cliente.threadTurnos.Turno = false;      
                    this.txfComandos.setText("");
                } catch (IOException ex) {
                    Logger.getLogger(PantallaCliente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        // Si no, tira un error
        else {
            JOptionPane.showMessageDialog(null, "Juan: Nono, ni se te ocurra saltarte de turno!\nSomos ladrones, oportunistas y asesinos, pero esto ya es un limite.", "Saltar Turno", JOptionPane.INFORMATION_MESSAGE, icon);
        }

    }//GEN-LAST:event_txfComandosActionPerformed

    private void rbtnBalasLongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnBalasLongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnBalasLongActionPerformed

    private void rbtnRadarLongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnRadarLongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnRadarLongActionPerformed

    private void rbtnRadarShortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnRadarShortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnRadarShortActionPerformed

    private void rbtnBalasHeavyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnBalasHeavyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnBalasHeavyActionPerformed

    private void txfRadarShortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfRadarShortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfRadarShortActionPerformed

    private void txfRadarLongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfRadarLongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfRadarLongActionPerformed

    private void txfBalasHeavyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfBalasHeavyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfBalasHeavyActionPerformed

    private void txfBalasLongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfBalasLongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfBalasLongActionPerformed

    private void txfMinasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfMinasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfMinasActionPerformed

    private void rbtnMinasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnMinasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnMinasActionPerformed

    private void txfSpotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfSpotActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfSpotActionPerformed

    private void rbtnSpotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnSpotActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnSpotActionPerformed

    private void btnComprarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprarActionPerformed
        
        CommandMain.ejecutarComando("Comprar", cliente.barco, cliente.barco.getNumBarco(), 1, cliente);

    }//GEN-LAST:event_btnComprarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnComprar;
    private javax.swing.JButton btnEnviarMensaje;
    private javax.swing.JButton btnEnviarMensajePriv;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JLabel lblAmenaza;
    private javax.swing.JLabel lblBalas;
    public javax.swing.JLabel lblBalasHeavyC1;
    public javax.swing.JLabel lblBalasLongC1;
    public javax.swing.JLabel lblChat;
    public javax.swing.JLabel lblCliente1;
    public javax.swing.JLabel lblCliente2;
    private javax.swing.JLabel lblClienteBalasHeavy;
    private javax.swing.JLabel lblClienteBalasLong;
    private javax.swing.JLabel lblClienteMinas;
    private javax.swing.JLabel lblClienteOro;
    private javax.swing.JLabel lblClienteRadarLong;
    private javax.swing.JLabel lblClienteRadarShort;
    private javax.swing.JLabel lblClienteSpot;
    private javax.swing.JLabel lblClienteVida;
    public javax.swing.JLabel lblMinasC1;
    public javax.swing.JLabel lblOroC1;
    public javax.swing.JLabel lblRadarLongC1;
    public javax.swing.JLabel lblRadarShortC1;
    private javax.swing.JLabel lblRadares;
    public javax.swing.JLabel lblSpotC1;
    public javax.swing.JLabel lblVendedor;
    public javax.swing.JLabel lblVidaC1;
    public javax.swing.JRadioButton rbtnBalasHeavy;
    public javax.swing.JRadioButton rbtnBalasLong;
    public javax.swing.JRadioButton rbtnMinas;
    public javax.swing.JRadioButton rbtnRadarLong;
    public javax.swing.JRadioButton rbtnRadarShort;
    public javax.swing.JRadioButton rbtnSpot;
    public javax.swing.JTextArea txaChat;
    private javax.swing.JTextArea txaInfoClienteComandos;
    public javax.swing.JTextField txfBalasHeavy;
    public javax.swing.JTextField txfBalasLong;
    private javax.swing.JTextField txfChat;
    public javax.swing.JTextField txfComandos;
    public javax.swing.JTextField txfMinas;
    private javax.swing.JTextField txfNombreChat;
    public javax.swing.JTextField txfRadarLong;
    public javax.swing.JTextField txfRadarShort;
    public javax.swing.JTextField txfSpot;
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
