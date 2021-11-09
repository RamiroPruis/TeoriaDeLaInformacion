import Utils.CalculosUtils;
import modelo.Codificador;
import modelo.Fuente;
import modelo.Lectura;
import modelo.Huffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;

public class App {

    public static void main(String[] args) {
        HashMap<String,Integer> datos = null;
        Lectura lectura = new Lectura();
        Fuente fuente5 = null,fuente7 = null,fuente9 = null;
        HashMap<String,String> TablaHuffman5, TablaHuffman7, TablaHuffman9;

        datos = lectura.cuentaApariciones(5);
        fuente5 = Lectura.cargaFuente(datos);
        TablaHuffman5 = (HashMap<String, String>) Huffman.creaArbolHuffman(datos);


        datos = lectura.cuentaApariciones(7);
        fuente7 = Lectura.cargaFuente(datos);
        TablaHuffman7 = (HashMap<String, String>) Huffman.creaArbolHuffman(datos);


        datos = lectura.cuentaApariciones(9);
        fuente9 = Lectura.cargaFuente(datos);
        TablaHuffman9 = (HashMap<String, String>) Huffman.creaArbolHuffman(datos);


        //CREO CARPETA REUSLTADOS
        File folder = new File ("./Resultados");
        folder.mkdirs();
        //CREO CARPETA REUSLTADOS
        try {
            PrintStream out = new PrintStream("./Resultados/Codificado5.txt");
            PrintStream outLongitudes = new PrintStream("./Resultados/LongitudesCodificados.txt");
            lectura.escribeCodificadoHuffman(TablaHuffman5,5,out);
            out.println();
            outLongitudes.println("ESCENARIO 1: La longitud media del nuevo codigo es :" + fuente5.calculaLongitudMedia(TablaHuffman5));
            out = new PrintStream("./Resultados/Codificado7.txt");
            lectura.escribeCodificadoHuffman(TablaHuffman7,7,out);
            out.println();
            outLongitudes.println("ESCENARIO 2: La longitud media del nuevo codigo es :" + fuente7.calculaLongitudMedia(TablaHuffman7));
            out = new PrintStream("./Resultados/Codificado9.txt");
            lectura.escribeCodificadoHuffman(TablaHuffman9,9,out);
            outLongitudes.println("ESCENARIO 3: La longitud media del nuevo codigo es :" + fuente9.calculaLongitudMedia(TablaHuffman9));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

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
    }
}
