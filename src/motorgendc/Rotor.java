/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motorgendc;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author SYSTEM
 */
public class Rotor extends JPanel implements Runnable {

    //Parámetros
    private int w;      //Velocidad (r.p.m)
   
    private int x, y;                   //Posición del rotor
    private Panel panel_rotor;          //Panel del rotor 

    //atributos asociados al hilo
    private boolean detenido;
    private long time_sleep;
    private boolean enPausa;

    private ImageIcon rotor = new ImageIcon(
            getClass().getResource("/imagenes/engine.png"));

    private int i = 1;

    public Rotor(int x, int y, Panel panel_r, int w) {

        super();
        //detenido = false;
        this.w = w;

        //gradseg = 1;
        try {
            time_sleep = Math.round(1000 / w);
            detenido = false;
        } catch (Exception e) {
            detenido = true;
        }

        this.x = x;
        this.y = y;
        this.panel_rotor = panel_r;
        setBounds(x, y, rotor.getIconHeight(), rotor.getIconWidth());
        setOpaque(false);

    }

    private void GirarRotor() {

        i++;
        repaint();

    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs); //to change body of generated
        Graphics2D g2d = (Graphics2D) grphcs;

        if (!detenido) {
            //Si hay movimiento, rota a la velocidad indicada
            g2d.rotate(Math.toRadians(6*i),
                    rotor.getIconWidth() / 2,
                    rotor.getIconHeight() / 2);
            g2d.drawImage(rotor.getImage(), 0, 0,
                    getWidth(), getHeight(), this);

        } else {

            //Si no hay movimiento, solo imprime la imagen
            g2d.drawImage(
                    rotor.getImage(), 0, 0,
                    getWidth(), getHeight(), this);

        }
    }

    @Override
    public void run() {

        while (!detenido) {

            try {
                if (!enPausa) {
                    GirarRotor();
                }
                Thread.sleep(time_sleep);
            } catch (InterruptedException e) {
                //System.out.println("Falla");
            }

        }

        i = 0;
        repaint();

    }

    public boolean isEnPausa() {
        return enPausa;
    }

    public void setEnPausa(boolean enPausa) {
        this.enPausa = enPausa;
    }

    public boolean isDetenido() {
        return detenido;
    }

    public void setDetenido(boolean detenido) {
        this.detenido = detenido;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

}
