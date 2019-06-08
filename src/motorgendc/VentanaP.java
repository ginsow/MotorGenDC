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

        setTitle("Motor/Generador DC");
        setSize(500, 500);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container contenedor = getContentPane();
        contenedor.setLayout(new BorderLayout());

        //estableciendo el panel del norte
        panel_norte = new JPanel();

        n_txt = new JTextField();
        n_txt.setColumns(5);

        panel_norte.add(n_txt);

        jButton1 = new JButton();
        jButton1.setText("Generar");
        jButton1.addActionListener(new Oyente());
        panel_norte.add(jButton1);

        add(panel_norte, BorderLayout.NORTH);

        //estableciendo el panel central
        panel_centro = new JPanel();
        panel_centro.setLayout(new GridLayout());
        panel_centro.setBounds(0, 0,
                panel_maquinas.getWidth(),
                panel_maquinas.getHeight());
        add(panel_centro, BorderLayout.CENTER);

    }

    //gestion del evento
    private class Oyente implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent me) {

            Integer n = Integer.parseInt(n_txt.getText());
            panel_centro.removeAll();
            panel_centro.setLayout(new GridLayout(n, 1));

            vector = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {

                panel_maquinas = new Panel();
                vector.add(panel_maquinas);
                resize(ancho, altura + (n - 1) * 342);
                setLocationRelativeTo(null);
                panel_centro.add(panel_maquinas);

            }
            panel_centro.validate();
        }
    }

}
