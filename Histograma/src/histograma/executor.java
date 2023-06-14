package histograma;


import java.awt.Color;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import javax.swing.JPanel;

public class executor implements Runnable {
    //variables
    private int[][] histograma;
    private JPanel rojo, verde, azul, gris;
    
    
     //constructor
    public executor(int[][] histograma, JPanel rojo, JPanel verde, JPanel azul, JPanel gris) {
        this.histograma = histograma;
        this.rojo = rojo;
        this.verde = verde;
        this.azul = azul;
        this.gris = gris;
        
    }

    
    public void run() {
        DibujarGrafico ObjDibujaHisto=new DibujarGrafico();
        for (int i = 0; i < 5; i++) {
                //extraemos un canal del histograma 
                int[] histogramaCanal=new int[256];
                System.arraycopy(histograma[i], 0, histogramaCanal, 0, histograma[i].length);
                //Dibujamos en el panel
                
                switch(i){
                    case 0:
                        ObjDibujaHisto.crearHistograma(histogramaCanal, rojo, Color.red);
                        break;
                    case 1:
                        ObjDibujaHisto.crearHistograma(histogramaCanal, verde, Color.green);
                        break;
                    case 2:
                        ObjDibujaHisto.crearHistograma(histogramaCanal, azul, Color.blue);
                        break;
                    case 4:
                        ObjDibujaHisto.crearHistograma(histogramaCanal, gris, Color.gray);
                        break;
                }
        }
    }
    
}