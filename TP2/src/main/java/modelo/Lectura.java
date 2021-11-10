package modelo;

import excepciones.*;

import java.io.*;
import java.math.BigInteger;
import java.util.*;


/**
 * Clase encargada de generar las estructuras necesarias para la lectura del archivo y el procesamiento de los datos.
 */
public class Lectura {
    private static int TOTAL=0;
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
        byte[] binary = numCod.toByteArray();
        output.write(binary);
        output.close();
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

    public static int getTOTAL() {
        return TOTAL;
    }


    /**
     * A partir de una secuencia binaria se calculan las probabilidades condicionales segun la fuente [00,01,10,11]
     * @return Matriz de transicion
     */
    public double [][] generaMatriz(){

        double [][] matriz = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
        double [][] matrizContadora = new double[4][4];
        String codigoAnt, codigoAct;

        for (int act=2, ant=0; act < nums.length - 1; act+=2, ant+=2){
            StringBuilder sb = new StringBuilder();
            sb.append(nums[ant]);
            sb.append(nums[ant+1]);
            codigoAnt = sb.toString();
            sb = new StringBuilder();
            sb.append(nums[act]);
            sb.append(nums[act+1]);
            codigoAct = sb.toString();

            try {
                matriz[devuelveIndices(codigoAct)][devuelveIndices(codigoAnt)] ++;
            } catch (IndiceInvalidoException e) {
                e.printStackTrace();
            }

        }
        //Clonamos la matriz
        matrizContadora = Arrays.stream(matriz).map(double[]::clone).toArray(double[][]::new);

        for (int i=0; i< matriz.length; i++)
            for(int j=0; j< matriz[i].length; j++)
                matriz[i][j]/= sumaFila(i, matrizContadora);

        return matriz;
    }

    /**
     * A partir de los datos recolectados, se genera una fuente
     * @param datos Estructura de datos con los simbolos y su cantidad de apariciones
     * @return La fuente
     */
    public static Fuente cargaFuente(HashMap<String,Integer> datos){
        Fuente fuente = new Fuente(datos.size());
        Set<String> Codigos = datos.keySet();
        for (String act : Codigos) {
            double ap = datos.get(act);
            double prob = ap / Lectura.getTOTAL();
            fuente.agregaElemento(act, prob);
        }
        return fuente;
    }


    /**
     * Imprime la matriz formateada
     * @param output Define la terminal de salida de los datos
     * @param matriz Matriz que se quiere visualizar
     */
    public void muestraMatriz(PrintStream output,double[][] matriz){

        for (double[] doubles : matriz) {
            output.print("|\t");
            for (int j = 0; j < doubles.length; j++) {
                output.printf("%.4f\t", doubles[j]);
            }
            output.println("|");
        }
    }


    private double sumaFila(int fila, double [][] matriz){
        int acum=0;
        for (int j = 0; j < matriz[fila].length; j++)
            acum += matriz[fila][j];
        return acum;
    }

    private int devuelveIndices(String cod) throws IndiceInvalidoException{
        return switch (cod) {
            case "00" -> 0;
            case "01" -> 1;
            case "10" -> 2;
            case "11" -> 3;
            default -> throw new IndiceInvalidoException();
        };
    }
}