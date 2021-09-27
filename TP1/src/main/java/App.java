import Utils.CalculosUtils;
import modelo.Codificador;
import modelo.Fuente;
import modelo.Lectura;
import org.ejml.simple.SimpleMatrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;

public class App {

    public static void main(String[] args) {
        HashMap<String,Integer> datos = null;
        Lectura lectura = new Lectura();
        Fuente fuente5 = null,fuente7 = null,fuente9 = null;

        try {
            datos = lectura.cuentaApariciones(5);
            fuente5 = lectura.cargaFuente(datos);

            datos = lectura.cuentaApariciones(7);
            fuente7 = lectura.cargaFuente(datos);

            datos = lectura.cuentaApariciones(9);
            fuente9 = lectura.cargaFuente(datos);
        } catch (FileNotFoundException ignored) {
        }

        //CREO CARPETA REUSLTADOS
        File folder = new File ("./Resultados");
        folder.mkdirs();
        //CREO CARPETA REUSLTADOS

        try {
            PrintStream output = new PrintStream("./Resultados/Ejercicio1.txt");
            fuente5.imprimeFuente(output);
            fuente7.imprimeFuente(output);
            fuente9.imprimeFuente(output);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        double[][] matriz = lectura.generaMatriz();

        double[] VecEst = CalculosUtils.generaVecEstacionario(matriz);


        try {
            lectura.muestraMatriz(new PrintStream("./Resultados/Ejercicio2.txt"), matriz);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Codificador cod = new Codificador();
        cod.agregaCodigo(fuente5.getSetCodigos());
        System.out.println("Todos tienen la misma longitud " +cod.esCodigoBloque());
        System.out.println("Ninguna cadena es prefijo de otra "+cod.esInstantaneo());
        System.out.println("No hay cadenas repetidas "+cod.esNoSingular());
    }
}
