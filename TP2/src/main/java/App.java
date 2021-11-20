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
            Fuente fuenteHuff = new Fuente(datos);
            huffman = (HashMap<String, String>) Huffman.creaArbolHuffman(datos);
            double entropia,longmedia;

            PrintStream out = new PrintStream(newOut);
            Lectura.escribeCodificadoHuffman(huffman,new FileInputStream(inputName),out);
            System.out.println("Archivo: " + outputName + " creado correctamente");

            out = outputResultados;
            entropia = fuenteHuff.calculaEntropia();
            longmedia = fuenteHuff.calculaLongitudMedia(huffman);
            out.println("Archivo: " + outputName + " creado correctamente");
            out.println("La entropia de la fuente es : " + entropia );
            out.println("La longitud media de la fuente es : " + longmedia);

            double rendimiento = entropia/longmedia;
            out.println("El rendimiento de la fuente dada por el archivo " + outputName + " es:" + rendimiento);
            out.println("La redundancia es " + (1-rendimiento));
            calcCompression(inputName,newOut, outputResultados);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void comprimeShannonFano(String inputName, String outPutName, PrintStream outputResultados){
        Lectura lectura = new Lectura(inputName);
        String strInitial = lectura.getNums();
        ShannonFano shannon = new ShannonFano(strInitial);
        try {
            outputResultados.println();
            shannon.writeCompressed(new PrintStream("Resultados/" + outPutName));
            System.out.println("Archivo: " + outPutName + " creado correctamente");
            outputResultados.println("Archivo: " + outPutName + " creado correctamente");
            outputResultados.println("La entropia de la fuente es : " + shannon.getEntropy() );
            outputResultados.println("La longitud media de la fuente es : " + shannon.getAverageLengthAfter());
            outputResultados.println("Rendimiento: " + shannon.getRendimiento() + "\n Redundancia: " + (1-shannon.getRendimiento()));
            calcCompression(inputName,"Resultados/" + outPutName, outputResultados);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void comprimeRLC(String inputName, String outputName, PrintStream outputResultados){
        FileInputStream file = null;
        Lectura lectura = new Lectura(inputName);
        HashMap<String,Integer> datos = null;
        String newOut = "Resultados/" + outputName;
        int valMaxApariciones;

        try {
            file = new FileInputStream(inputName);
            System.out.println("Archivo: " + outputName + " creado correctamente");
            PrintStream out = new PrintStream(newOut);
            if (outputName.equalsIgnoreCase("imagen.rlc")){
                valMaxApariciones = Lectura.escribeCodificadoRLC(lectura.getNums(), out,true);
            }
            else {
                valMaxApariciones = Lectura.escribeCodificadoRLC(lectura.getNums(), out,false);
            }
            //FileInputStream newFile = new FileInputStream(newOut);

            datos = Lectura.AparicionesRLC(file);

            Fuente fuente = new Fuente(datos);
            out = outputResultados;
            double entropia = fuente.calculaEntropia();
            double longitudMedia = fuente.calculaLongitudMediaRLC(valMaxApariciones); // o calculaLongitudMedia

            double rendimiento = entropia/longitudMedia;
            out.println("Archivo: " + outputName + " creado correctamente");
            out.println("La entropia de la fuente es : " + entropia);
            out.println("La longitud media de la fuente es : " + longitudMedia);
            out.println("El rendimiento de la fuente dada por el archivo " + outputName + " es:" + rendimiento);
            out.println("La redundancia es " + (1-rendimiento));
            calcCompression(inputName,"Resultados/" + outputName, outputResultados);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
    } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void calcCompression(String originalPath,String compressedPath, PrintStream outputResultados){
        File original = new File(originalPath);
        File compressed = new File(compressedPath);

        outputResultados.println("Tamano archivo original: "+ original.length() + " bytes");
        outputResultados.println("Tamano archivo comprimido: " + compressed.length() + " bytes");
        outputResultados.println("Tasa de compresion = " + original.length()/compressed.length() + ":1");
        outputResultados.println();
    }

}
