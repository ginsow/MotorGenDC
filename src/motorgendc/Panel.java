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

public class Panel extends JPanel {

    private ExecutorService threadPool;
    private Rotor rotorg, rotorm;
    private MaquinaDC maquina;
    
    //Declaración de variables del Java.Swing
    JPanel panel_izquierdo, panel_central, panel_derecho;
    JLabel CMotor, CGen;                //Etiquetas de control
    JPanel panelM, panelG;              //Paneles de control FlowLayout de panel_derecho
    JTextField Vtm_txt, W_txt;          //Casillas de entrada y salida MOTOR
    JLabel txt1, txt2, txt3, txt4;      //Nombres de los componentes
    JTextField Vfg_txt, Vtg_txt;        //Casillas de entrada y salida2 GEN
    JButton Control;                     //Boton de control

    private int Wmg, Vtg;

    public Panel() {

        super();
        threadPool = Executors.newCachedThreadPool();
        setLayout(new GridLayout(1, 3)); // Grid 1 fila 3 columnas

        //CONFIGURACIÓN DEL PANEL DERECHO
        panel_derecho = new JPanel();                   //Creación del panel_derecho
        panel_derecho.setLayout(new GridLayout(5, 1));  //Asignación de GridL

        //Creación de los nombres de componentes
        txt1 = new JLabel();
        txt2 = new JLabel();
        txt3 = new JLabel();
        txt4 = new JLabel();
        //Asignación de nombres de componentes
        txt1.setText("Tensión de terminales:                 ");
        txt2.setText("Velocidad de salida del motor:   ");
        txt3.setText("Tensión del inductor:                       ");
        txt4.setText("Tensión de salida del generador: ");

        //Controles Motor
        CMotor = new JLabel();                  //Creación de etiqueta de control M
        CMotor.setText("Controles Motor");              //Nombre de etiqueta
        CMotor.setHorizontalAlignment(JLabel.CENTER);   //Alineación central
        panel_derecho.add(CMotor);                      //Adición de etiqueta

        //Panel contenedor de controles del Motor
        panelM = new JPanel();              //Creación de panel contenedor
        panelM.setLayout(new FlowLayout());

        //Creación de casillas de control
        Vtm_txt = new JTextField();     //Entrada de tensión en terminales del motor
        Vtm_txt.setColumns(5);
        W_txt = new JTextField();       //Salida de velocidad del motor
        W_txt.setColumns(5);
        //W_txt.setEnabled(false);        //No puede ser configurada directamente

        //Adición de componentes del motor al panel FlowLayout
        panelM.add(txt1);
        panelM.add(Vtm_txt);
        panelM.add(txt2);
        panelM.add(W_txt);

        panel_derecho.add(panelM);  //Adición del panel de componentes del motor

        //Controles Generador
        CGen = new JLabel();                    //Creación de etiqueta de control G
        CGen.setText("Controles Generador");        //Nombre de etiqueta
        CGen.setHorizontalAlignment(JLabel.CENTER); //Alineación central
        panel_derecho.add(CGen);                    //Adición de etiqueta

        //Panel contenedor de cntroles del Generador
        panelG = new JPanel();              //Creación de panel contenedor
        panelG.setLayout(new FlowLayout());

        //Creación de casillas de control
        Vfg_txt = new JTextField();     //Entrada de tensión de excitación del Gen
        Vfg_txt.setColumns(5);
        Vtg_txt = new JTextField();     //Salida de Tensión en terminales del Gen
        Vtg_txt.setColumns(5);
        Vtg_txt.setEnabled(false);      //No puede ser configurada directamente

        //Adición de componentes del generador al panel FlowLayout
        panelG.add(txt3);
        panelG.add(Vfg_txt);
        panelG.add(txt4);
        panelG.add(Vtg_txt);

        panel_derecho.add(panelG);      //Adición del panel de componentes del gen

        //Boton de control
        Control = new JButton();
        Control.setText("Agregar");
        Control.addActionListener(new OyenteC());
        panel_derecho.add(Control);

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

        //Se añaden los paneles al principal
        add(panel_izquierdo);
        add(panel_central);
        add(panel_derecho);

    }

    //Metodo para calcular salidas del sistema
    public void Calculo() {

        try {
            
            //Creación del objeto con el constructor MaquinaDC(), recibiendo
            //los parametros de los TextField
            maquina = new MaquinaDC(
                    Integer.parseInt(Vtm_txt.getText()),
                    Integer.parseInt(Vfg_txt.getText())
            );
            
            //Llamador a los metodos de maquina para generar entradas y salidas
            maquina.motor(1);
            maquina.generador(1);
            
            //Asignación de salidas de los metodos
            Wmg = (int) maquina.getW();
            Vtg = (int) maquina.getVtg();
            
            //Escritura de los resultados en las casillas de TextField
            //W_txt.setText(String.valueOf(Wmg));
            Vtg_txt.setText(String.valueOf(Vtg));

        } catch (NumberFormatException e) {
            //Si uno de los valores es erroneo (no escrito aún)
            JOptionPane.showMessageDialog(null, "Valores erroneos",
                    "ERROR DE COMPONENTE", JOptionPane.WARNING_MESSAGE);
        }

    }

    //Clase OyenteC que acciona el boton Agregar
    private class OyenteC implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Calculo();              //Calculo con parametros nuevos

            panel_central.remove(rotorg);       //Se remueve el contenido de los
            panel_izquierdo.remove(rotorm);     //paneles, sino se pintaría encima
            
            //Nueva escritura de los rotores
            rotorg = new Rotor(0, 0, Panel.this,
                    Integer.parseInt(W_txt.getText()));
            rotorm = new Rotor(0, 0, Panel.this,
                    Integer.parseInt(W_txt.getText()));
            
            //Adición a los paneles
            panel_central.add(rotorg);
            panel_izquierdo.add(rotorm);

            //Ejecución de hilos 
            threadPool.execute(rotorm);
            threadPool.execute(rotorg);

        }
    }

}
