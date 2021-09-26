import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class App {

    public static void main(String[] args) {
        HashMap<String,Integer> datos = null;
        Lectura lectura = new Lectura();

        try {
            datos = lectura.cuentaApariciones(2);
        } catch (FileNotFoundException e) {
        }
        Set<String> Codigos = datos.keySet();
        Iterator<String> itCod = Codigos.iterator();

        Fuente fuente = new Fuente(datos.size());

        //Agrega los elementos leidos a la fuente
        while(itCod.hasNext()) {
            String act = itCod.next();
            double ap = datos.get(act);
            double prob = ap/Lectura.getTOTAL();
            fuente.agregaElemento(act,prob);
        }
        try {
            fuente.imprimeFuente(new PrintStream("./Ejercicio1.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        double acum=0;
        double[][] matriz = lectura.generaMatriz();
        try {
            lectura.muestraMatriz(new PrintStream("./Ejercicio2.txt"), matriz);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
