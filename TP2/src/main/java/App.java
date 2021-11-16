import modelo.*;

import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;


public class App {

    public static void main(String[] args) {

        new File("Resultados").mkdirs();
            System.out.println("Los resultados se almacenan en la carpeta \"Resultados\"");

        PrintStream outputResultados = null;
        try {
            outputResultados = new PrintStream("Resultados/Resultados.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
            comprimeHuffman("Argentina.txt","Argentina.huf",outputResultados);
            comprimeHuffman("Hungaro.txt","Hungaro.huf",outputResultados);
            comprimeHuffman("Imagen.raw","Imagen.huf",outputResultados);

            outputResultados.println("*****************************************************");

            comprimeRLC("Argentina.txt","Argentina.RLC",outputResultados);
            comprimeRLC("Hungaro.txt","Hungaro.RLC",outputResultados);
            comprimeRLC("imagen.raw","Imagen.RLC",outputResultados);

            outputResultados.println("*****************************************************");

            comprimeShannonFano("Argentina.txt","Argentina.fan",outputResultados);
            comprimeShannonFano("Hungaro.txt","Hungaro.fan",outputResultados);
            comprimeShannonFano("Imagen.raw","Imagen.fan",outputResultados);


    }

    public static void comprimeHuffman(String inputName,String outputName, PrintStream outputResultados){
        FileInputStream file = null;
        HashMap<String,Integer> datos = null;
        HashMap<String,String> huffman = null;
        String newOut = "Resultados/" + outputName;


        try {
            file = new FileInputStream(inputName);
            datos = Lectura.cuentaApariciones(file);

            huffman = (HashMap<String, String>) Huffman.creaArbolHuffman(datos);

            PrintStream out = new PrintStream(newOut);
            Lectura.escribeCodificadoHuffman(huffman,new FileInputStream(inputName),out);


            System.out.println("Archivo: " + outputName + " creado correctamente");

            out = outputResultados;
/*
            double rendimiento = fuente.calculaEntropia()/fuente.calculaLongitudMedia();
            out.println("El rendimiento de la fuente dada por el archivo " + outputName + " es:" + rendimiento);
            out.println("La redundancia es " + (1-rendimiento));
 */
            calcCompression(inputName,newOut);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void comprimeShannonFano(String inputName, String outPutName, PrintStream outputResultados){
        Lectura lectura = new Lectura(inputName);
        String strInitial = lectura.getNums();
        ShannonFano shannon = new ShannonFano(strInitial);
        try {
            shannon.writeCompressed(new PrintStream("Resultados/" + outPutName));
            System.out.println("Archivo: " + outPutName + " creado correctamente");
            calcCompression(inputName,"Resultados/" + outPutName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void comprimeRLC(String inputName, String outputName, PrintStream outputResultados){
        FileInputStream file = null;
        Lectura lectura = new Lectura(inputName);
        HashMap<String,Integer> datos = null;
        String newOut = "Resultados/" + outputName;

        try {
            file = new FileInputStream(inputName);
            System.out.println("Archivo: " + outputName + " creado correctamente");
            PrintStream out = new PrintStream(newOut);
            if (outputName.equalsIgnoreCase("imagen.rlc")){
                Lectura.escribeCodificadoRLC(lectura.getNums(), out,true);

            }
            else {
                Lectura.escribeCodificadoRLC(lectura.getNums(), out,false);
            }
            datos = lectura.AparicionesRLC(file, out);

            Fuente fuente = new Fuente(datos);
            out = outputResultados;

            double rendimiento = fuente.calculaEntropia()/fuente.calculaLongitudMediaRLC();
            out.println("El rendimiento de la fuente dada por el archivo " + outputName + " es:" + rendimiento);
            out.println("La redundancia es " + (1-rendimiento));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
    } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void calcCompression(String originalPath,String compressedPath){
        File original = new File(originalPath);
        File compressed = new File(compressedPath);

        System.out.println("Tamano archivo original: "+ original.length() + " bytes");
        System.out.println("Tamano archivo comprimido: " + compressed.length() + " bytes");
        System.out.println("Tasa de compresion = " + original.length()/compressed.length() + ":1");

    }

}
