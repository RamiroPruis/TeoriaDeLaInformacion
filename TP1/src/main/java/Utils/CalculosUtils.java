package Utils;

import org.ejml.simple.SimpleMatrix;

/**
 * Define algoritmos necesarios para la resolucion de ejercicios
 */
public class CalculosUtils {

    /**
     * Genera el vector estacionario a partir de la matriz de transicion
     * @param mat Matriz de transicion
     * @return Vector estacionario
     */
    public static double[] generaVecEstacionario (double [][] mat){
        SimpleMatrix M = new SimpleMatrix(mat);
        SimpleMatrix add = new SimpleMatrix(1,4,false,new double[]{1,1,1,1});
        SimpleMatrix Id = SimpleMatrix.identity(M.numCols());
        M = M.minus(Id);
        M = M.combine(4,0,add);
        double [] cero = {0,0,0,0,1};
        SimpleMatrix b = new SimpleMatrix(5,1,true,cero);
        SimpleMatrix V = M.solve(b);
        return SimpleMatrixToArray(V,V.getNumElements());
    }

    /**
     * Convierte un vector de timpo SimpleMatrix a tipo double[]
     * @param vec Vector a convertir
     * @param size Tamano del vector
     * @return Mismo vector pero de tipo double[]
     */
    public static double[] SimpleMatrixToArray (SimpleMatrix vec,int size){
        double[] aux = new double[size];
        for(int i=0;i<vec.getNumElements();i++)
            aux[i] = vec.get(i);
        return aux;
    }

    /**
     * Calcula la entropia de una fuente con memoria
     * @param vec Vector Estacionario
     * @param matriz Matriz de transicion
     * @return valor de la entropia (bits/simbolo)
     */
    public static double calculaEntropiaMarkoviana(double[] vec, double[][] matriz){
        double acum1=0, acum2;
        for (int i = 0; i < vec.length; i++){
            acum2=0;
            for (int j = 0; j < matriz[i].length; j++){
                if (matriz[j][i]!=0)
                    acum2 += matriz[j][i] * (-Math.log(matriz[j][i]) / Math.log(2));
            }
            acum1 += vec[i] * acum2;
        }
        return acum1;
    }


}
