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
    private double IL;
    private double H;
    private double fi;
    private double B;
    private double A;
    private double K;
    private double L;
    private double w;
    private double Ieq;

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
    private double Tf;

    private double Tabla[][] = {
        {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
        {5, 60, 120, 170, 220, 250, 275, 295, 310, 320}};
    private double Wtabla = 100;
    private int Iftabla;
    private double K_fi;

    public MaquinaDC(int Vtm, int Vfg) {
        
        this.Vtm = Vtm;
        this.Vfg = Vfg;
    
    }

    public void generador(int tipo) {

        switch (tipo) {
            case 1:
                //excitacion separada

                //in: Vf , W , Carga (o W y Vt) ; out: Vt (o Vf) 
                If = Vfg / Rf;
                Iftabla = (int) Math.round(If);
                Ea = buscar(Tabla, Iftabla);

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

    public void motor(int tipo) {

        switch (tipo) {
            case 1:
                //excitacion separada

                //in: Vt , Vf, Tf ; out W , TL
                If = Vtm / Rf;
                //Ea = K*fi*w;
                Ea = buscar(Tabla, Iftabla);
                Ia = (Vtm - Ea) / Ra;
                K_fi = Ea/Wtabla;
                Te = K_fi*Ia;
                //Te = Tf;
                w = (Vtm / (Ea / Wtabla)) - Te * (Ra / Math.pow((Ea / Wtabla), 2));

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

    public double buscar(double M[][], double I) {

        for (int i = 0; i < M.length; i++) {

            if (I == M[0][i]) {
                return M[1][i];
            }

        }

        return -1;
    }

    public double getVtg() {
        return Vtg;
    }

    public double getW() {
        return w;
    }

 
    
    
}
