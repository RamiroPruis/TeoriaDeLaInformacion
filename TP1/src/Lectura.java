import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;


/**
 * Clase encargada de generar las estructuras necesarias para la lectura del archivo y el procesamiento de los datos.
 */
public class Lectura {
    private static int TOTAL=0;
    private char[] nums;


    public Lectura() {
        try {
            FileInputStream arch = new FileInputStream("anexo1-grupo6.txt");
            byte[] lec = arch.readAllBytes();
            nums = new char[lec.length];
            for(int i=0;i<lec.length;i++){
                nums[i] = (char) lec[i];         //transformo todos los bytes en char
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public HashMap<String,Integer> cuentaApariciones(int largo) throws FileNotFoundException {
        HashMap<String,Integer> fuente = new HashMap<>();
        Integer acum;
        TOTAL = nums.length/largo;
        int j = 0;
        int i=0;
        StringBuilder sb = new StringBuilder();
        do {
            char num=' ';
            if (i<nums.length)
                num = nums[i];
            if (j < largo) {
                sb.append(num);                 //armo los strings
                j++;
            } else {
                String str = sb.toString();
                sb = new StringBuilder();
                sb.append(num);
                j = 1;
                if (!fuente.containsKey(str)) {
                    fuente.put(str, 1);          //si no existe el codigo, lo agrego
                } else {
                    acum = fuente.get(str);
                    fuente.put(str, acum + 1);
                }
            }
            i++;
            } while (i<=nums.length);
        return fuente;
    }

    public static int getTOTAL() {
        return TOTAL;
    }

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

            matriz[devuelveIndices(codigoAct)][devuelveIndices(codigoAnt)] += 1;

        }
        //Clonamos la matriz
        matrizContadora = Arrays.stream(matriz).map(double[]::clone).toArray(double[][]::new);

        for (int i=0; i< matriz.length; i++)
            for(int j=0; j< matriz[i].length; j++)
                matriz[i][j]/= sumaFila(i, matrizContadora);

        return matriz;
    }


    public Fuente cargaFuente(HashMap<String,Integer> datos){
        Fuente fuente = new Fuente(datos.size());
        Set<String> Codigos = datos.keySet();
        for (String act : Codigos) {
            double ap = datos.get(act);
            double prob = ap / Lectura.getTOTAL();
            fuente.agregaElemento(act, prob);
        }
        return fuente;
    }

    public void muestraMatriz(PrintStream output,double[][] matriz){

        for (int i=0; i< matriz.length; i++){
            output.print("|\t");
            for(int j=0; j< matriz[i].length; j++){
                //output.print(matriz[i][j] + "    ");
                output.printf("%.4f\t", matriz[i][j]);
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

    private int devuelveIndices(String cod){
        int result = 0;
        switch (cod){
            case "00":
                result=0;
                break;
            case "01":
                result= 1;
                break;
            case "10":
                result=2;
                break;
            case "11":
                result=3;
                break;
        }
        return result;
    }

}
