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
        char[] chars = getChars(file);

        for (char aChar : chars) {
            String valueStr = String.valueOf(aChar);
            codigoNuevo.append(huffmanCode.get(valueStr));
        }

        //codificacion del String obtenido
        BigInteger numCod = new BigInteger(codigoNuevo.toString(), 2);
        byte[] binarydevuelve = numCod.toByteArray();
        output.write(binarydevuelve);
        output.close();
    }

    public static char[] getChars(FileInputStream file){
        Scanner input = new Scanner(file);
        String text = input.nextLine();
        while(input.hasNextLine())
            text= text + '\n' + input.nextLine();
        return text.toCharArray();
    }

    public static int escribeCodificadoRLC(String word, PrintStream output, boolean img) throws IOException{
        char strout;
        int i=1;
        int n;
        int count = 1;
        int valMaxApariciones = 0;
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

                if (count > valMaxApariciones)
                    valMaxApariciones = count;
                count = 1;

            }
            i++;
        }
        while (i < n);
        output.print(count);
        output.print(word.charAt(i-1));
        output.close();
        return valMaxApariciones;
    }

    /**
     * A partir de una secuencia binaria de digitos arma una fuente de un largo especifico
     *
     * @return La fuente indexada por sus simbolos con la cantidad de apariciones de cada uno
     */
    public static HashMap<String,Integer> cuentaApariciones(FileInputStream file) throws FileNotFoundException {
        HashMap<String,Integer> fuente = new HashMap<>();

        char[] chars = getChars(file);
        for (char aChar : chars) {
            String valueStr = String.valueOf(aChar);
            if (fuente.get(valueStr) != null) {
                int value = fuente.get(valueStr);
                fuente.replace(valueStr, value + 1);
            } else
                fuente.put(valueStr, 1);
        }
        return fuente;
    }


    public static HashMap<String,Integer> AparicionesRLC(FileInputStream file) throws IOException {
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