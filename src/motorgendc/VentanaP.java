/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motorgendc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author SYSTEM
 */
public class VentanaP extends JFrame {

    //atributos de la GUI
    private JPanel panel_norte, panel_A, panel_B;
    private JTextField n_txt;
    private JButton jButton1, jButton2;
    private JPanel panel_centro;
    private JLabel parametros_lbl, ayuda_lbl;

    private Panel panel_maquinas;

    //tamaño de ventana
    private int ancho, altura;
    
    public VentanaP() {

        super();
        this.altura = 250 + 61;
        this.ancho = 250 * 4 + 23;
        panel_maquinas = new Panel();
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/Icono.png")).getImage());
        setTitle("Motor/Generador DC");             //Nombre de la ventana
        setSize(1000, 500);                          //Dimensiones iniciales
        setLocationRelativeTo(null);                //Ubicación central (siempre)
        setDefaultCloseOperation(EXIT_ON_CLOSE);    //Operación close
        Container contenedor = getContentPane();    //Creación del contenedor
        contenedor.setLayout(new BorderLayout());   //Asignación del BorderLayout

        //CONFIGURACIÓN DEL PANEL NORTE
        panel_norte = new JPanel();
        panel_norte.setLayout(new GridLayout(1, 2));

        //Panel A
        panel_A = new JPanel();

        ayuda_lbl = new JLabel();
        ayuda_lbl.setText("Instrucciones de la aplicación: ");
        panel_A.add(ayuda_lbl);
        panel_A.setBackground(Color.CYAN);
        jButton2 = new JButton();
        jButton2.setText("HELP");
        jButton2.addActionListener(new OyenteH());
        panel_A.add(jButton2);

        //Panel B
        panel_B = new JPanel();         //Creación del panel norte

        parametros_lbl = new JLabel();
        parametros_lbl.setText("Ingrese la cantidad de pares de Motor/Generador: ");
        panel_B.add(parametros_lbl);

        n_txt = new JTextField();           //Creación de casilla de generación
        n_txt.setColumns(5);
        n_txt.addActionListener(new Oyente());

        panel_B.setBackground(Color.CYAN);
        panel_B.add(n_txt);             //Adición de casilla      

        jButton1 = new JButton();           //Creación de botón Añadir
        jButton1.setText("Añadir");
        jButton1.addActionListener(new Oyente());   //Asignación de operación
        panel_B.add(jButton1);                  //Adición del botón

        panel_norte.add(panel_A);
        panel_norte.add(panel_B);

        add(panel_norte, BorderLayout.NORTH);       //Adición del panel norte

        //CONFIGURACIÓN DEL PANEL CENTRAL
        panel_centro = new JPanel();                //Creación del panel centro
        panel_centro.setLayout(new GridLayout());   //Asignación de GridLayout
        panel_centro.setBounds(0, 0, //Dimensiones del panel
                panel_maquinas.getWidth(),
                panel_maquinas.getHeight());
        add(panel_centro, BorderLayout.CENTER);     //Asignación del panel

    }

    //Gestión del evento para generar pares motor/generador
    private class Oyente implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent me) {

            try {

                Integer n = Integer.parseInt(n_txt.getText());  //Obtención de n pares
                
                if (n < 4 && n > 0) {
                    
                    panel_centro.removeAll();                       //Remueve componentes
                    panel_centro.setLayout(new GridLayout(n, 1));   //Asignación
                    
                    for (int i = 0; i < n; i++) {

                        panel_maquinas = new Panel();           //Creación de panel_maquinas
                        resize(ancho, altura + (n - 1) * 250);  //Nueva dimensión
                        setResizable(false);                    //No se podrá redimensionar
                        setLocationRelativeTo(null);            //Ubicación central
                        panel_centro.add(panel_maquinas);       //Adición del panel

                    }
                    panel_centro.validate();                //Validación de componentes

                } else if (n < 0) {

                    JOptionPane.showMessageDialog(null, "Cantidad negativa",
                            "NÚMERO ERRONEO", JOptionPane.WARNING_MESSAGE);
                    
                } else if (n == 0) {

                    panel_centro.removeAll();
                    resize(ancho, altura);
                    setResizable(false);
                    setLocationRelativeTo(null); 
                    panel_centro.setBackground(Color.CYAN);
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Cantidad fuera de límite",
                            "LÍMITE DE PARES EXCEDIDO", JOptionPane.WARNING_MESSAGE);

                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Cantidad errónea",
                        "VALOR INVÁLIDO", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private class OyenteH implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent me) {

            JOptionPane.showMessageDialog(null,
                    "Esta aplicación de simulación básica e interactiva con el usuario\n"
                    + "consiste en observar las distintas variaciones de velocidad de una\n"
                    + "máquina DC de pares de Motor/Generador acoplados con una relación\n"
                    + "de velocidad rotacional de 3:1 y adicional a ello, poder describir\n"
                    + "el comportamiento de la salida eléctrica del sistema mediante la\n"
                    + "variación de intensidad lumínica de un bombillo conectado en los\n"
                    + "terminales del generador eléctrico DC.\n\n"
                    + "Datos de operación del sistema: \n\n"
                    + "     Límite de máquinas simultáneas:    3\n"
                    + "     Rango de tensión del motor:             Vtm = (15-210)V\n"
                    + "     Rango de tensión del generador:     Vfg = (0-220)V\n"
                    + "     Velocidad del motor:                 "
                    + "          Wm = (15-145)rad/s\n"
                    + "     Tensión del bombillo:                "
                    + "          Vtg = (0-216)V\n\n "
                    + "IMPORTANTE: Los valores de tensión no deben salir de los rangos\n"
                    + "definidos ya que la máquina puede sufrir daños físicos. En caso\n"
                    + "de que la tensión del motor sea menor de 15V, el torqué eléctrico\n"
                    + "no será suficiente para que la máquina arranque y pueda girar. ",
                    "INSTRUCCIONES DE LA APLICACIÓN", JOptionPane.INFORMATION_MESSAGE);

        }
    }

}
