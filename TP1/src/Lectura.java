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
            int j = 0;
            StringBuilder sb = new StringBuilder();
            for (char num : nums) {
                if (j < largo){
                    sb.append(num);                 //armo los strings
                    j++;
                }
                else {
                    String str = sb.toString();
                    sb = new StringBuilder();
                    j = 0;
                    if (!fuente.containsKey(str)){
                        fuente.put(str,1);          //si no existe el codigo, lo agrego
                    }
                    else {
                        acum = fuente.get(str);
                        fuente.put(str,++acum);
                    }
                }
            }
        } catch (IOException ignored) {
        }
        return fuente;
    }

    public static int getTOTAL() {
        return TOTAL;
    }
}
