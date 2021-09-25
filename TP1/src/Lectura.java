import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class Lectura {
    private static int TOTAL=0;

    public static HashMap<String,Integer> leeDatos(int largo) throws FileNotFoundException {
        HashMap<String,Integer> fuente = new HashMap<>();
        Integer acum;

        FileInputStream arch = new FileInputStream("anexo1-grupo6.txt");
        try {
            byte[] lec = arch.readAllBytes();
            TOTAL = lec.length/largo;
            char[] nums = new char[lec.length];
            for(int i=0;i<lec.length;i++){
                nums[i] = (char) lec[i];         //transformo todos los bytes en char
            }
            int J = 0;
            int I=0;
            StringBuilder sb = new StringBuilder();
            do {
                char num=' ';
                if (I<nums.length)
                    num = nums[I];
                if (J < largo) {
                    sb.append(num);                 //armo los strings
                    J++;
                } else {
                    String str = sb.toString();
                    sb = new StringBuilder();
                    sb.append(num);
                    J = 1;
                    if (!fuente.containsKey(str)) {
                        fuente.put(str, 1);          //si no existe el codigo, lo agrego
                    } else {
                        acum = fuente.get(str);
                        fuente.put(str, acum + 1);
                    }
                }
                I++;
            } while (I<=nums.length);
        } catch (IOException ignored) {
        }
        return fuente;
    }

    public static int getTOTAL() {
        return TOTAL;
    }


}
