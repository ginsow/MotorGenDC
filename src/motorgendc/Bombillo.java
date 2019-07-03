/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motorgendc;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author SYSTEM
 */
public class Bombillo extends JPanel  {

    private ImageIcon bombillo;
    private JPanel panelbombillo;

    private int carga;         //Tensión en terminales
    
    public Bombillo(int voltaje, JPanel panelbombillo) {

        this.bombillo = new ImageIcon(getClass().getResource("/imagenes/bombillo1.png"));
        carga = voltaje;
        this.panelbombillo = panelbombillo;
        setBounds(0, 0,
                bombillo.getIconWidth(),
                bombillo.getIconHeight());

    }

    public void AsignarBombillo() {

        try {

            //Rangos de función del bombillo
            if (carga > 0 && carga <= 25) {

                bombillo = new ImageIcon(
                        getClass().getResource("/imagenes/bombillo2.png"));

            } else if (carga > 25 && carga <= 50) {

                bombillo = new ImageIcon(
                        getClass().getResource("/imagenes/bombillo3.png"));

            } else if (carga > 50 && carga <= 80) {

                bombillo = new ImageIcon(
                        getClass().getResource("/imagenes/bombillo4.png"));

            } else if (carga > 80 && carga <= 120) {

                bombillo = new ImageIcon(
                        getClass().getResource("/imagenes/bombillo5.png"));

            } else if (carga > 120) {

                bombillo = new ImageIcon(
                        getClass().getResource("/imagenes/bombillo6.png"));

            }
            
        } catch (NumberFormatException e) {

            bombillo = new ImageIcon(getClass().getResource("/imagenes/bombillo1.png"));

        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs); //to change body of generated
        Graphics2D g2d = (Graphics2D) grphcs;

        g2d.drawImage(bombillo.getImage(), 0, 0,
                getWidth(), getHeight(), this);

    }

    
    
}
