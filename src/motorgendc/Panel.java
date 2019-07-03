/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motorgendc;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 *
 * @author SYSTEM
 */
public class Panel extends JPanel {

    private ExecutorService threadPool;
    private Rotor rotorg, rotorm;
    private MaquinaDC maquina;
    private Bombillo bombillo;

    //Declaración de variables del Java.Swing
    JPanel panel_izquierdo, panel_central, panel_derecho, panel_bombillo;
    JLabel CMG;                         //Etiquetas de control
    JPanel panelM, panelG, panelR, panelE, botones; //Paneles de control FlowLayout de panel_derecho
    JLabel Vtm_txt, W_txt;              //Labels del motor           
    JLabel Vfg_txt, Vtg_txt;            //Labels del generador
    JLabel txt1, txt2, txt3, txt4;      //Nombres de los componentes
    JButton Control, pausar;            //Boton de control
    JSlider SliderM, SliderG;           //Slider motor generador

    private int Wmg, Vtg;

    private ImageIcon fondo = new ImageIcon(
            getClass().getResource("/imagenes/fondo3.jpg"));

    @Override
    protected void paintComponent(Graphics grphcs) {

        super.paintComponent(grphcs); //to change body of generated
        Graphics2D g2d = (Graphics2D) grphcs;

        g2d.drawImage(
                fondo.getImage(), 0, 0,
                getWidth(), getHeight(), this);

    }

    public Panel() {

        super();
        threadPool = Executors.newCachedThreadPool();
        setLayout(new GridLayout(1, 4, 5, 5)); // Grid 1 fila 3 columnas

        //CONFIGURACIÓN DEL PANEL DERECHO
        panel_derecho = new JPanel();                   //Creación del panel_derecho
        panel_derecho.setLayout(new GridLayout(6, 1));  //Asignación de GridL

        //Creación de los nombres de componentes
        txt1 = new JLabel();
        txt2 = new JLabel();
        txt3 = new JLabel();
        txt4 = new JLabel();

        Vtm_txt = new JLabel();     //Entrada de tensión motor
        Vfg_txt = new JLabel();     //Salida de tensión generador

        W_txt = new JLabel();       //Salida de velocidad del motor
        Vtg_txt = new JLabel();     //Salida de Tensión en terminales del Gen
        W_txt.setText("0 rad/s");
        Vtg_txt.setText("0 V");

        //Asignación de nombres de componentes
        Vtm_txt.setText("Vtm: ");
        txt2.setText("Wm:");
        Vfg_txt.setText("Vfg: ");
        txt4.setText("\tVtg:");
        txt1.setText("Vtm: 0V \t");
        txt3.setText("\t Vfg: 0V");

        Evento e = new Evento();

        //Controles Motor
        CMG = new JLabel();                  //Creación de etiqueta de control M
        CMG.setText("CONTROLES DEL MOTOR Y GENERADOR");     //Nombre de etiqueta
        CMG.setHorizontalAlignment(JLabel.CENTER);   //Alineación central
        panel_derecho.add(CMG);                      //Adición de etiqueta

        //Panel contenedor de controles del Motor
        panelM = new JPanel();              //Creación de panel contenedor
        panelM.setLayout(new FlowLayout());

        //Creación de Slider Motor
        SliderM = new JSlider(JSlider.HORIZONTAL, 15, 210, 15); //Propiedades
        SliderM.setMajorTickSpacing(10);                  //Lineas de referencia
        SliderM.setPaintTicks(true);
        SliderM.setOpaque(false);           
        SliderM.addChangeListener(e);               //Acción del slider
        SliderM.setEnabled(false);                  //Inicialmente deshabilitado

        //Adición de componentes del motor al panel FlowLayout
        panelM.add(Vtm_txt);
        panelM.add(SliderM);
        panel_derecho.add(panelM);  //Adición del panel de componentes del motor
        panelM.setOpaque(false);

        //Panel contenedor de cntroles del Generador
        panelG = new JPanel();              //Creación de panel contenedor
        panelG.setLayout(new FlowLayout());

        //Creación de Slider Generador
        SliderG = new JSlider(JSlider.HORIZONTAL, 0, 220, 0);   //Propiedades
        SliderG.setMajorTickSpacing(10);                //Lineas de referencia
        SliderG.setPaintTicks(true);
        SliderG.setOpaque(false);
        SliderG.addChangeListener(e);                   //Acción del slider
        SliderG.setEnabled(false);                  //Inicialmente deshabilitado

        //Adición de componentes del generador al panel FlowLayout
        panelG.add(Vfg_txt);
        panelG.add(SliderG);
        panel_derecho.add(panelG);      //Adición del panel de componentes del gen
        panelG.setOpaque(false);

        //Panel de entradas
        panelE = new JPanel();
        panelE.setLayout(new FlowLayout());
        panelE.add(txt1);
        panelE.add(txt3);

        panel_derecho.add(panelE);
        panelE.setOpaque(false);

        //Panel de resultados
        panelR = new JPanel();
        panelR.setLayout(new FlowLayout());

        panelR.add(txt2);
        panelR.add(W_txt);
        panelR.add(txt4);
        panelR.add(Vtg_txt);

        panel_derecho.add(panelR);
        panelR.setOpaque(false);

        //Botones de control
        botones = new JPanel();            //Creación de panel contenedor
        botones.setLayout(new FlowLayout());

        //Boton de inicio
        Control = new JButton();            //Creación del boton
        Control.setText("Iniciar");
        Control.addActionListener(new Oyente1());
        botones.add(Control);

        //Boton de pausa
        pausar = new JButton();
        pausar.setText("Pausar");
        pausar.setEnabled(false);
        pausar.addActionListener(new Oyente2());
        botones.add(pausar);

        panel_derecho.add(botones);
        botones.setOpaque(false);

        //--------------------------------------------------------------------//
        //CONFIGURACIÓN DEL PANEL IZQUIERDO MOTOR
        panel_izquierdo = new JPanel();             //Creación del panel contenedor
        panel_izquierdo.setLayout(new BorderLayout());  //Asignación BorderLayout
        rotorm = new Rotor(0, 0, Panel.this, Wmg);      //Llamado a RotorM
        panel_izquierdo.setBounds(0, 0,
                rotorm.getWidth(),
                rotorm.getHeight());
        panel_izquierdo.add(rotorm);                //Adición del rotor

        //CONFIGURACIÓN DEL PANEL CENTRAL GENERADOR
        panel_central = new JPanel();               //Creación del panel contenedor
        panel_central.setLayout(new BorderLayout());    //Asignación BorderLayout
        rotorg = new Rotor(0, 0, Panel.this, Wmg);      //Llamado a RotorG
        panel_central.setBounds(0, 0,
                rotorg.getWidth(),
                rotorg.getHeight());
        panel_central.add(rotorg);                  //Adición del rotor

        //--------------------------------------------------------------------//
        //CONFIGURACIÓN DEL PANEL DE BOMBILLO
        panel_bombillo = new JPanel();              //Creación del panel contenedor
        panel_bombillo.setLayout(new BorderLayout());   //Asignación de BorderLayout
        bombillo = new Bombillo(Vtg, Panel.this);       //Llamado a Bombillo
        panel_bombillo.setBounds(0, 0,
                bombillo.getWidth(),
                bombillo.getHeight());
        panel_bombillo.add(bombillo);               //Adición del bombillo

        //--------------------------------------------------------------------//
        //Haciendo transparentes los paneles
        panel_izquierdo.setOpaque(false);
        panel_central.setOpaque(false);
        panel_bombillo.setOpaque(false);
        panel_derecho.setOpaque(false);

        //Se añaden los paneles al principal
        add(panel_izquierdo);
        add(panel_central);
        add(panel_bombillo);
        add(panel_derecho);

    }

    //Metodo para calcular salidas del sistema
    public void Calculo() {

        try {

            //Creación del objeto con el constructor MaquinaDC(), recibiendo
            //los parametros de los TextField
            maquina = new MaquinaDC(SliderM.getValue(),
                    SliderG.getValue());

        } catch (NumberFormatException e) {
            //Si uno de los valores es erroneo (no escrito aún)
            JOptionPane.showMessageDialog(null, "Valores erroneos",
                    "ERROR DE COMPONENTE", JOptionPane.WARNING_MESSAGE);
        }

    }

    //Clase Oyente1 que acciona el boton Iniciar/Detener
    private class Oyente1 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand().equalsIgnoreCase("iniciar")) {

                SliderM.setEnabled(true);
                SliderG.setEnabled(true);
                pausar.setEnabled(true);
                Control.setText("Detener");
                Control.setEnabled(false);

            } else {

                //Fin 
                SliderM.setValue(15);
                SliderG.setValue(0);
                SliderM.setEnabled(false);
                SliderG.setEnabled(false);

                pausar.setEnabled(false);
                pausar.setText("Pausar");
                Control.setEnabled(true);
                Control.setText("Iniciar");

                rotorm.setDetenido(true);
                rotorg.setDetenido(true);

            }

        }
    }

    //Clase Oyente2 que acciona el boton pausar
    private class Oyente2 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            //Acciones de los botones del panel de control
            rotorm.setEnPausa(!rotorm.isEnPausa());
            rotorg.setEnPausa(!rotorg.isEnPausa());
            //bombillo.setEnPausa(bombillo.isEnPausa());

            if (pausar.getActionCommand().equalsIgnoreCase("pausar")) {
                pausar.setText("Reanudar");
                Control.setEnabled(true);
            } else {
                pausar.setText("Pausar");
                Control.setEnabled(false);
            }

        }

    }

    //Evento del Slider y acción sobre el panel
    private class Evento implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            
            //Muestreo de valores
            txt1.setText("Vtm: " + SliderM.getValue() + " V");
            txt3.setText("\tVfg: " + SliderG.getValue() + " V");

            Calculo();              //Calculo con parametros nuevos
            
            int auxg;               //Auxiliares de "i" de los rotores que
            auxg = rotorg.getI();   //describen el angulo antes del cambio
            int auxm;
            auxm = rotorm.getI();

            panel_central.remove(rotorg);       //Se remueve el contenido de los
            panel_izquierdo.remove(rotorm);     //paneles, sino se pintaría encima
            panel_bombillo.remove(bombillo);

            //Llamado a Ejecucion de las ecuaciones
            maquina.motor(SliderM.getValue());
            Wmg = (int) maquina.getWm();
            maquina.generador(SliderG.getValue(), Wmg);
            Vtg = (int) maquina.getVtg();

            //Nueva escritura de los rotores
            rotorg = new Rotor(0, 0, Panel.this,
                    (Wmg / 3));
            rotorg.setI(auxg);
            rotorm = new Rotor(0, 0, Panel.this,
                    Wmg);
            rotorm.setI(auxm);
            bombillo = new Bombillo(Vtg, Panel.this);
            bombillo.AsignarBombillo();

            //Actualizacion de valores
            W_txt.setText(Wmg + " rad/s");
            Vtg_txt.setText(Vtg + " V");

            //Adición a los paneles
            panel_central.add(rotorg);
            panel_izquierdo.add(rotorm);
            panel_bombillo.add(bombillo);

            //Ejecución de hilos 
            threadPool.execute(rotorm);
            threadPool.execute(rotorg);
            //threadPool.execute(bombillo);

        }
    }

}
