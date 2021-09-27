package Utils;

import org.ejml.simple.SimpleMatrix;

public class CalculosUtils {



    public static SimpleMatrix generaVecEstacionario (double [][] mat){
        SimpleMatrix M = new SimpleMatrix(mat);
        SimpleMatrix Id = SimpleMatrix.identity(M.numCols());
        M = M.minus(Id);
        for(int i=0;i<4;i++)
            M.set(3,i,1);
        double [] cero = {0,0,0,1};
        SimpleMatrix b = new SimpleMatrix(4,1,true,cero);
        return M.solve(b);
    }


    public static double calculaEntropiaMarkoviana(double[] vec, double[][] matriz){
        double acum1=0, acum2=0;
        for (int i = 0; i < vec.length; i++){
            acum2=0;
            for (int j = 0; j< matriz[i].length; j++){
                acum2 += matriz[j][i] * (-Math.log(matriz[j][i]) / Math.log(2));
            }
            acum1 += vec[i] * acum2;
        }
        return acum1;
    }
}
