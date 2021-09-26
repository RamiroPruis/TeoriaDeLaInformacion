import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class App {

    public static void main(String[] args) {
        HashMap<String,Integer> Fuente = null;

        Lectura lectura = new Lectura();
        try {
            Fuente = lectura.cuentaApariciones(2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        assert Fuente != null;
        Set<String> Codigos = Fuente.keySet();
        Iterator<String> itCod = Codigos.iterator();

        System.out.println("Datos de la fuente:");
        System.out.println("*********************************");
        System.out.println("Cantidad de simbolos: " + Fuente.size());

        Fuente f = new Fuente(Fuente.size());
        while(itCod.hasNext()) {
            String act = itCod.next();
            double ap = Fuente.get(act);
            double prob = ap/Lectura.getTOTAL();
            f.agregaElemento(act,prob);
        }



        System.out.println(" --> La entropia de la fuente es: " + f.calculaEntropia());


        double acum=0;
        double[][] matriz = lectura.generaMatriz();

        for (int i=0; i< matriz.length; i++){
            for(int j=0; j< matriz[i].length; j++){
                System.out.print(matriz[i][j] + "    ");
                acum += matriz[i][j];
            }
            System.out.println();
        }
        System.out.println("suma: " +acum);




    }
}
