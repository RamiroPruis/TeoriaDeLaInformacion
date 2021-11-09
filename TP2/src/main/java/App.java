import modelo.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;


public class App {

    public static void main(String[] args) {
        FileInputStream file = null;
        HashMap<String,Integer> datos = null;
        HashMap<String,String> huffman = null;



        try {
            file = new FileInputStream("Argentina.txt");
            datos = Lectura.cuentaApariciones(file);

            huffman = (HashMap<String, String>) Huffman.creaArbolHuffman(datos);

            System.out.println(huffman);
            PrintStream out = new PrintStream("Argentina.huf");
            Lectura.escribeCodificadoHuffman(huffman,new FileInputStream("Argentina.txt"),out);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


}
