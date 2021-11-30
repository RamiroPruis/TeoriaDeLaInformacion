import modelo.*;

import java.io.*;
import java.util.HashMap;


public class App {

    public static void main(String[] args) {

        new File("Resultados").mkdirs();
            System.out.println("Los resultados se almacenan en la carpeta \"Resultados\"");

        PrintStream outputResultados = null;
        PrintStream outDiccionarioHuff = null;
        PrintStream outDiccionarioShannon = null;

        try {
            outputResultados = new PrintStream("Resultados/Resultados.txt");
            outDiccionarioHuff = new PrintStream("Resultados/diccHuff.txt");
            outDiccionarioShannon = new PrintStream("Resultados/diccShannon.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

            comprimeHuffman("Argentina.txt","Argentina.huf",outputResultados,outDiccionarioHuff);
            comprimeHuffman("Hungaro.txt","Hungaro.huf",outputResultados,outDiccionarioHuff);
            comprimeHuffman("Imagen.raw","Imagen.huf",outputResultados,outDiccionarioHuff);

            outDiccionarioHuff.close();
            outputResultados.println("*****************************************************");

            comprimeRLC("Argentina.txt","Argentina.RLC",outputResultados);
            comprimeRLC("Hungaro.txt","Hungaro.RLC",outputResultados);
            comprimeRLC("imagen.raw","Imagen.RLC",outputResultados);

            outputResultados.println("*****************************************************");

            comprimeShannonFano("Argentina.txt","Argentina.fan",outputResultados,outDiccionarioShannon);
            comprimeShannonFano("Hungaro.txt","Hungaro.fan",outputResultados,outDiccionarioShannon);
            comprimeShannonFano("Imagen.raw","Imagen.fan",outputResultados,outDiccionarioShannon);

            outDiccionarioShannon.close();


    }

    public static void comprimeHuffman(String inputName,String outputName, PrintStream outputResultados,PrintStream outDiccionario){
        FileInputStream file = null;
        HashMap<String,Integer> datos = null;
        HashMap<String,String> huffman = null;
        String newOut = "Resultados/" + outputName;


        try {
            file = new FileInputStream(inputName);
            datos = Lectura.cuentaApariciones(file);

            Fuente fuenteHuff = new Fuente(datos);
            huffman = (HashMap<String, String>) Huffman.creaArbolHuffman(datos);
            imprimeDiccionario(huffman,datos,"Huffman de "+ inputName,outDiccionario);
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

    public static void imprimeDiccionario(HashMap<String, String> diccionario,HashMap<String,Integer> frecuency,String title, PrintStream out){
        out.println("----- Diccionario de Codificacion por " + title + " -----");
        out.println("Simbolo -> Frecuencia -> Codigo");
        diccionario.forEach((k,v) -> {
            if (k.equalsIgnoreCase("\n"))
                out.println("\\n -> "+ frecuency.get(k) +" -> "+ v);
            else if (k.equalsIgnoreCase("\s"))
                out.println("\\s -> "+ frecuency.get(k)+" -> "+ v);
            else
                out.println(k + " -> "+ frecuency.get(k)+" -> " + v);
        });
        out.println();
    }

    public static void comprimeShannonFano(String inputName, String outPutName, PrintStream outputResultados,PrintStream outDicc){
        FileInputStream inputFile = null;
        try {
            inputFile = new FileInputStream(inputName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        char[] strInitial = Lectura.getChars(inputFile);

        ShannonFano shannon = new ShannonFano(String.valueOf(strInitial));
        try {
            outputResultados.println();
            shannon.writeCompressed(new PrintStream("Resultados/" + outPutName));
            imprimeDiccionario(shannon.getCompressedResult(),shannon.getCharacterFrequency()," ShannonFano en "+ inputName,outDicc);
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
        outputResultados.println("Tasa de compresion = " + Math.round((float) original.length()/compressed.length()) + ":1");
        outputResultados.println();
    }

}
