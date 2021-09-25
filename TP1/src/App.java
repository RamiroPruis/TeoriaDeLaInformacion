import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class App {

    public static void main(String[] args) {
        HashMap<String,Integer> Fuente = null;


        try {
            Fuente = Lectura.leeDatos(5);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert Fuente != null;
        Set<String> Codigos = Fuente.keySet();
        Iterator<String> itCod = Codigos.iterator();

        while(itCod.hasNext()) {
            String act = itCod.next();
            double ap = Fuente.get(act);
            double prob = ap/Lectura.getTOTAL();
            System.out.println(act + ": " + String.format("%.4f",prob));
        }
    }
}
