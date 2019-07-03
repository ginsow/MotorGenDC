/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motorgendc;

import javax.swing.JOptionPane;

/**
 *
 * @author SYSTEM
 */
public class MaquinaDC {

    //parametros
    private double Vtm, Vtg;
    private double Wm , Wg;
    private double Ea; 
    private double Ia;
    private double Vfg;
    private double If;
    private double Te;
    private double Etabla;
    private double K_fi;
    
    //Parametros definidos de la Maquina
    private double Rcarga = 100;
    private double Ra = 0.025;
    private double Rf = 85;
    private double Tf = 75;
    private double Wtabla = 100;
    private double Tabla[][] = {
        {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
        {5, 60, 120, 170, 220, 250, 275, 295, 310, 320}};
    
    
    
    public MaquinaDC(int Vtm, int Vfg) {
        
        this.Vtm = Vtm;
        this.Vfg = Vfg;
    
    }

    public void motor(double Vmot){
        this.Vtm = Vmot;
        
            //Motor Shunt
                
            if(Vtm >= 15 && Vtm <= 210){
                         
                calculovelocidad();
                
            } else if(Vtm < 15 && Vtm >= 0) {
                JOptionPane.showMessageDialog(null, 
                    "No se supera el torque de frenado", "MUY BAJA TENSION PARA OPERAR", JOptionPane.WARNING_MESSAGE);
            
            } else if (Vtm < 0){
                JOptionPane.showMessageDialog(null, 
                    "Voltaje de motor negativo", "ERROR", JOptionPane.WARNING_MESSAGE);    
            
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Se está excediendo el valor nominal", "PELIGRO", JOptionPane.WARNING_MESSAGE);    
            }
                
    }

    public void generador(double Vind, double Wgen) {
        
        this.Vfg = Vind;
        this.Wg = Wgen;
        
            //Generador de Excitacion Separada
            if(Vfg <= 220 && Vfg >= 0){
                calculotension();
            } else if(Vfg < 0){
                JOptionPane.showMessageDialog(null, 
                "Voltaje de generador   negativo", "ERROR", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, 
                "Se está excediendo el valor nominal", "PELIGRO", JOptionPane.WARNING_MESSAGE);
            }
 

    }
  
    private double interpolacion(double M[][], double I){
        double x = I, x1 = 0, x2 = 0 , y = 0 , y1 = 0, y2 = 0;
        
        
        for (int i = 0; i < M[0].length; i++) {

            if (I >= M[0][i] && I<= M[0][i+1]) {
                x1 = M[0][i];
                y1 = M[1][i];
                x2 = M[0][i+1];
                y2 = M[1][i+1];
                
            }
            
        }
        
        y = y1 + ( (y2 - y1)*(x - x1) / (x2 - x1) );
        
        return y;
    }
    


    private void calculovelocidad() {
        
        if (Vtm == 15){
            Wm = 0;
        } else {
            If = Vtm / Rf;
            Ea = interpolacion(Tabla, If);
            K_fi = Ea / Wtabla;
            Ia = Vtm / Ra;
            Te = K_fi * Ia;

            if (Te > Tf) {
                Ia = Tf / K_fi;
                Ea = Vtm - Ia * Ra;
                Wm = Ea / K_fi;
            } 
        }
    }
    
    private void calculotension(){
        
        If = Vfg / Rf;
        Etabla = interpolacion(Tabla, If);
        Ea = Etabla*(Wg/Wtabla);

        Ia = Ea / (Ra + Rcarga);
        Vtg = Ea - Ia * Ra;
     
        
    }
 
    public double getVtg() {
        return Vtg;
    }

    public double getWm() {
        return Wm;
    }

    
    
    
}
