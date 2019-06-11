/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motorgendc;



/**
 *
 * @author SYSTEM
 */
public class MaquinaDC {

    //parametros
    private double Vtm, Vtg;
    private double Wm , Wg;
    //private double IL, H, fi, B, A, K, L, Ieq;

    private double Rcarga = 100;

    private double Va;
    private double Ea; 
    private double Ra = 0.025;
    private double Ia;
    private int Ns;
    private double Rs;
    private double Rarr;

    private double Rf = 85;
    private double Vfm, Vfg;
    private int Nf;
    private double If;

    private double Te;
    private double Tf = 75;
    private double inercia = 1;

    private double Tabla[][] = {
        {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
        {5, 60, 120, 170, 220, 250, 275, 295, 310, 320}};
    private double Wtabla = 100;
    private double Etabla;
    private int Iftabla;
    private double K_fi;
    
    
    public MaquinaDC(int Vtm, int Vfg) {
        
        this.Vtm = Vtm;
        this.Vfg = Vfg;
    
    }

    public void motor(int tipo, double Vmot){
        this.Vtm = Vmot;
        
        
        switch (tipo) {
            case 1:
                //excitacion separada

                //in: Vt , Vf, Tf ; out W , TL
                If = Vtm / Rf;
                Iftabla = (int) Math.round(If);
                Ea = buscar(Tabla, Iftabla);
                K_fi = Ea/Wtabla;
                calculovelocidad();
                
                
                //Ea = K_fi*Wm;                
                //Ia = (Vtm - Ea) / Ra;               
                //Te = K_fi*Ia;
                //Te = Tf;
                //Wm = (Vtm / (Ea / Wtabla)) - Te * (Ra / Math.pow((Ea / Wtabla), 2));
                //Wm = Ea/K_fi;
                
                //Wm = Te*1.2;
                break;
            case 2:
                //en derivacion

                break;

            case 3:
                //serie        

                break;
            case 4:
                //compuesto

                break;
        }
    }

    public void generador(int tipo, double Vind, double Wgen) {
        
        this.Vfg = Vind;
        this.Wg = Wgen;
        
        switch (tipo) {
            case 1:
                //excitacion separada

                //in: Vf , W , Carga (o W y Vt) ; out: Vt (o Vf) 
                If = Vfg / Rf;
                Iftabla = (int) Math.round(If);
                Etabla = buscar(Tabla, Iftabla);
                Ea = Etabla*(Wg/Wtabla);

                Ia = Ea / (Ra + Rcarga);
                Vtg = Ea - Ia * Ra;

                break;
            case 2:
                //en derivacion

                /*
                Ia = If+ IL;
                Vt = Ea - Ia*Ra;
                 */
                break;
            case 3:
                //serie

                /*
                Vt = Ea - Ia*(Ra+Rs);
                break;
                 */
                break;
            case 4:
                //compuesto

                /*
                Ieq = If + (Ns/Nf)* Ia ;
                 */
                break;
        }

    }

    public double buscar(double M[][], double I) {

        for (int i = 0; i < M[0].length; i++) {

            if (I == M[0][i]) {
                return M[1][i];
            }

        }

        return -1;
    }

    public double getVtg() {
        return Vtg;
    }

    public double getWm() {
        return Wm;
    }

    

    private void calculovelocidad() {
        Ia = Vtm / Ra;
        Te = K_fi*Ia;
        
        if(Te>Tf){
            Ia = Tf / K_fi;
            Ea = Vtm - Ia*Ra;
            Wm = Ea / K_fi;
        } 
        
    }

 
    
    
}
