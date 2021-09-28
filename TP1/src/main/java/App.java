import Utils.CalculosUtils;
import modelo.Codificador;
import modelo.Fuente;
import modelo.Lectura;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;

public class App {

    public static void main(String[] args) {
        HashMap<String,Integer> datos = null;
        Lectura lectura = new Lectura();
        Fuente fuente5 = null,fuente7 = null,fuente9 = null;

        datos = lectura.cuentaApariciones(5);
        fuente5 = lectura.cargaFuente(datos);

        datos = lectura.cuentaApariciones(7);
        fuente7 = lectura.cargaFuente(datos);

        datos = lectura.cuentaApariciones(9);
        fuente9 = lectura.cargaFuente(datos);

        //CREO CARPETA REUSLTADOS
        File folder = new File ("./Resultados");
        folder.mkdirs();
        //CREO CARPETA REUSLTADOS

        try {
            PrintStream output = new PrintStream("./Resultados/Ejercicio1.txt");
            fuente5.imprimeFuente(output);
            fuente7.imprimeFuente(output);
            fuente9.imprimeFuente(output);



            double[][] matriz = lectura.generaMatriz();

            double[] VecEst = CalculosUtils.generaVecEstacionario(matriz);
            lectura.muestraMatriz(new PrintStream("./Resultados/Ejercicio2.txt"), matriz);


            output = new PrintStream("./Resultados/Ejercicio3.txt");
            output.println("El Vector Estacionario es: " + CalculosUtils.devuelveVectorString(VecEst));
            output.println("Y la entropia de la fuente markoviana es: "+ CalculosUtils.calculaEntropiaMarkoviana(VecEst, matriz));

            output = new PrintStream("./Resultados/Ejercicio4.txt");
            Codificador cod = new Codificador();
            cod.agregaCodigo(fuente5.getSetCodigos());
            output.println("Todos tienen la misma longitud " +cod.esCodigoBloque());
            output.println("Ninguna cadena es prefijo de otra "+cod.esInstantaneo());
            output.println("No hay cadenas repetidas "+cod.esNoSingular());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        double[][] matriz = lectura.generaMatriz();
//
//        double[] VecEst = CalculosUtils.generaVecEstacionario(matriz);



//        try {
//            lectura.muestraMatriz(new PrintStream("./Resultados/Ejercicio2.txt"), matriz);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

//        try {
//            PrintStream output = new PrintStream("./Resultados/Ejercicio3.txt");
//            output.println("El Vector Estacionario es: " + CalculosUtils.devuelveVectorString(VecEst));
//            output.println("Y la entropia de la fuente markoviana es: "+ CalculosUtils.calculaEntropiaMarkoviana(VecEst, matriz));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

//
//        try {
//            PrintStream output = new PrintStream("./Resultados/Ejercicio4.txt");
//            Codificador cod = new Codificador();
//            cod.agregaCodigo(fuente5.getSetCodigos());
//            output.println("Todos tienen la misma longitud " +cod.esCodigoBloque());
//            output.println("Ninguna cadena es prefijo de otra "+cod.esInstantaneo());
//            output.println("No hay cadenas repetidas "+cod.esNoSingular());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

    }
}
