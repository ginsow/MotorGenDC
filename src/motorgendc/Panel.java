/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motorgendc;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Panel extends JPanel {

    private ExecutorService threadPool;
    private Rotor rotorg, rotorm;

    public Panel() {

        super();
        threadPool = Executors.newCachedThreadPool();
        setLayout(new GridLayout(1, 3)); // Grid 1 fila 3 columnas

        //configuracion panel izquierdo
        JPanel panel_izquierdo = new JPanel();
        panel_izquierdo.setLayout(new BorderLayout());
        rotorm = new Rotor(0, 0, Panel.this, 0);
        panel_izquierdo.setBounds(0, 0,
                rotorm.getWidth(),
                rotorm.getHeight());
        panel_izquierdo.add(rotorm);

        add(panel_izquierdo);

        //configuracion panel central
        JPanel panel_central = new JPanel();
        panel_central.setLayout(new BorderLayout());
        rotorg = new Rotor(0, 0, Panel.this, 100);
        panel_central.setBounds(0, 0,
                rotorg.getWidth(),
                rotorg.getHeight());
        panel_central.add(rotorg);

        add(panel_central);

        //configuracion panel derecho
        JPanel panel_derecho = new JPanel();
        panel_derecho.setLayout(new FlowLayout());
        JLabel label = new JLabel();
        label.setText("controles");
        panel_derecho.add(label);

        add(panel_derecho);

        //ejecucion
        threadPool.execute(rotorm);
        threadPool.execute(rotorg);
    }

}
