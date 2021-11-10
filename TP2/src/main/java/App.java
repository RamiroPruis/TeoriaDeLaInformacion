import modelo.*;

import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;


public class App {

    public static void main(String[] args) {

        new File("Resultados").mkdirs();
            System.out.println("Los resultados se almacenan en la carpeta \"Resultados\"");

            comprimeHuffman("Argentina.txt","Argentina.huf");
            comprimeHuffman("Hungaro.txt","Hungaro.huf");
            comprimeHuffman("Imagen.raw","Imagen.huf");


            comprimeShannonFano("Argentina.txt","Argentina.fan");
            comprimeShannonFano("Hungaro.txt","Hungaro.fan");
            comprimeShannonFano("Imagen.raw","Imagen.fan");


    }

    public static void comprimeHuffman(String inputName,String outputName){
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void comprimeShannonFano(String inputName, String outPutName){
        Lectura lectura = new Lectura(inputName);
        String strInitial = lectura.getNums();
        ShannonFano shannon = new ShannonFano(strInitial);
        try {
            shannon.writeCompressed(new PrintStream("Resultados/" + outPutName));
            System.out.println("Archivo: " + outPutName + " creado correctamente");
            System.out.println("La taza de compresion fue " + shannon.getCompressionTaza() + " El rendimiento es  " + shannon.getRendimiento() + " Y la redundancia " + (1-shannon.getRendimiento()));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }





}
