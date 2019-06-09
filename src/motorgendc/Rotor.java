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
public class Rotor extends JPanel implements Runnable {

    //Parámetros
    private int w;      //Velocidad (r.p.m)
    private double gradseg; //grados rotados por segundo
    

    private int x, y;                   //Posición del rotor
    private Panel panel_rotor;          //Panel del rotor 

    //atributos asociados al hilo
    private boolean enPausa;
    private boolean detenido;
    private long time_sleep;

    private ImageIcon rotor = new ImageIcon(
            getClass().getResource("/imagenes/engine.png"));

    private int i = 1;

    public Rotor(int x, int y, Panel panel_r, int w) {

        super();
        enPausa = false;
        detenido = false;
        this.w = w;

        gradseg = 6;
        try {
            time_sleep = Math.round(1000 / w);
        } catch (Exception e) {
            detenido = true;
        }

        this.x = x;
        this.y = y;
        this.panel_rotor = panel_r;
        setBounds(x, y, rotor.getIconHeight(), rotor.getIconWidth());
        setOpaque(false);

    }
    
        
    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs); //to change body of generated
        Graphics2D g2d = (Graphics2D) grphcs;

        //Si hay movimiento, rota a la velocidad indicada
        if (!detenido) {

            g2d.rotate(Math.toRadians(gradseg),
                    rotor.getIconWidth() / 2,
                    rotor.getIconHeight() / 2);
            g2d.drawImage(rotor.getImage(), 0, 0,
                    getWidth(), getHeight(), this);

        } else {

            //si no hay movimiento, solo imprime la imagen
            g2d.drawImage(
                    rotor.getImage(), 0, 0,
                    getWidth(), getHeight(), this
            );

        }
    }

    private void GirarRotor() {

        gradseg = 6 * (i++);
        repaint();

    }

    @Override
    public void run() {
        while (!detenido) {

            try {
                if (!enPausa) {
                    GirarRotor();
                    Thread.sleep(time_sleep);
                } else {

                }
            } catch (InterruptedException e) {
                System.out.println("Falla");
            }

        }

    }

}
