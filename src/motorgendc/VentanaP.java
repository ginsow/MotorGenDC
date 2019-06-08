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
    private Panel panel;
    private JPanel panel_norte;
    private JTextField n_txt;
    private JButton jButton1;
    private JPanel panel_centro;
        
    private ArrayList<Panel> vector;
    
    public VentanaP(){
    
        super();

        setTitle("Motor/Generador DC"); 
        setSize(1000, 400);
        //setLocationRelativeTo(null);
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
        
        add(panel_centro, BorderLayout.CENTER);
    }
    
    
    //gestion del evento
    private class Oyente implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent me){
        
        Integer n = Integer.parseInt(n_txt.getText());
        panel_centro.removeAll();
        panel_centro.setLayout(new GridLayout(n, 1));
      
             
        vector = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
           Panel panel_maquinas = new Panel();
           vector.add(panel_maquinas);
           panel_centro.add(panel_maquinas);
           
        }
          panel_centro.validate();
        }
    }
    
    
    
}
