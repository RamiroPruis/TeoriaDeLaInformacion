package modelo;

import excepciones.*;

import java.io.*;
import java.math.BigInteger;
import java.util.*;


/**
 * Clase encargada de generar las estructuras necesarias para la lectura del archivo y el procesamiento de los datos.
 */
public class Lectura {
    private char[] nums;


    public Lectura(String fileName) {
        try {
            FileInputStream arch = new FileInputStream(fileName);
            byte[] lec = arch.readAllBytes();
            nums = new char[lec.length];
            for(int i=0;i<lec.length;i++){
                nums[i] = (char) lec[i];         //transformo todos los bytes en char
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNums() {
        return String.valueOf(nums);
    }

    public static void escribeCodificadoHuffman(Map<String,String> huffmanCode, FileInputStream file, PrintStream output) throws IOException {
        StringBuilder codigoNuevo = new StringBuilder();
        Scanner input = new Scanner(file);
        while(input.hasNext()){
            String word = input.next();
            codigoNuevo.append(huffmanCode.get(word));
        }

        //codificacion del String obtenido
        BigInteger numCod = new BigInteger(codigoNuevo.toString(), 2);
        byte[] binarydevuelve = numCod.toByteArray();
        output.write(binarydevuelve);
        output.close();
    }

    public static void escribeCodificadoRLC(String word, PrintStream output, boolean img) throws IOException{
        char strout;
        int i=1;
        int n;
        int count = 1;
        if (img)
            word = word.replace("\n","").replace("\r","");
        n=word.length();
        do {
            if(word.charAt(i) == word.charAt(i-1))
                count++;
            else{
                strout=word.charAt(i-1);
                if (strout!='\n') {
                    output.print(count);
                    if (!img)
                        output.print(strout);
                    else
                        output.println(strout);
                }
                else
                    output.print(strout);
                count = 1;

            }
            i++;
        }
        while (i < n);
        output.print(count);
        output.print(word.charAt(i-1));
    }

    /**
     * A partir de una secuencia binaria de digitos arma una fuente de un largo especifico
     *
     * @return La fuente indexada por sus simbolos con la cantidad de apariciones de cada uno
     */
    public static HashMap<String,Integer> cuentaApariciones(FileInputStream file) throws FileNotFoundException {
        HashMap<String,Integer> fuente = new HashMap<>();
        Scanner input = new Scanner(file);
        while(input.hasNext()){
            String word = input.next();
            if (fuente.get(word) != null){
                int value = fuente.get(word);
                fuente.replace(word,value+1);
            }
            else
                fuente.put(word,1);
        }
        return fuente;
    }


    public static HashMap<String,Integer> AparicionesRLC(FileInputStream file, PrintStream output) throws FileNotFoundException, IOException {
        HashMap<String,Integer> fuente = new HashMap<>();
        Scanner input = new Scanner(file);
        input.useDelimiter(System.getProperty("line.separator"));
        while(input.hasNext()) {
            String word = input.next();
            for (int i = 0; i < word.length(); i++) {
                String letra = String.valueOf(word.charAt(i));
                if (fuente.get(letra) != null) {
                    int valor = fuente.get(letra);
                    fuente.replace(letra, valor + 1);
                } else {
                    fuente.put(letra, 1);
                }
            }
        }
        return fuente;
    }

}