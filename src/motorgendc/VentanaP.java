/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motorgendc;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 *
 * @author SYSTEM
 */
public class VentanaP extends JFrame {

    //atributos de la GUI
    private JPanel panel_norte;
    private JTextField n_txt;
    private JButton jButton1;
    private JPanel panel_centro;

    private Panel panel_maquinas;

    //tamaño de ventana
    private int ancho, altura;

    private ArrayList<Panel> vector;

    public VentanaP() {

        super();
        this.altura = 342 + 61;
        this.ancho = 342 * 3;
        panel_maquinas = new Panel();

        setTitle("Motor/Generador DC");             //Nombre de la ventana
        setSize(500, 500);                          //Dimensiones iniciales
        setLocationRelativeTo(null);                //Ubicación central (siempre)
        setDefaultCloseOperation(EXIT_ON_CLOSE);    //Operación close
        Container contenedor = getContentPane();    //Creación del contenedor
        contenedor.setLayout(new BorderLayout());   //Asignación del BorderLayout

        //CONFIGURACIÓN DEL PANEL NORTE
        panel_norte = new JPanel();         //Creación del panel norte

        n_txt = new JTextField();           //Creación de casilla de generación
        n_txt.setColumns(5);

        panel_norte.add(n_txt);             //Adición de casilla      

        jButton1 = new JButton();           //Creación de botón Generar
        jButton1.setText("Generar");
        jButton1.addActionListener(new Oyente());   //Asignación de operación
        panel_norte.add(jButton1);                  //Adición del botón

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

            Integer n = Integer.parseInt(n_txt.getText());  //Obtención de n pares
            panel_centro.removeAll();                       //Remueve componentes
            panel_centro.setLayout(new GridLayout(n, 1));   //Asignación

            vector = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {

                panel_maquinas = new Panel();           //Creación de panel_maquinas
                vector.add(panel_maquinas);             //Adición al vector
                resize(ancho, altura + (n - 1) * 342);  //Nueva dimensión
                setResizable(false);                    //No se podrá redimensionar
                setLocationRelativeTo(null);            //Ubicación central
                panel_centro.add(panel_maquinas);       //Adición del panel

            }
            panel_centro.validate();                //Validación de componentes
        }
    }

}
