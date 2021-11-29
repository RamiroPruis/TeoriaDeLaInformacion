package modelo;

import java.io.PrintStream;
import java.util.*;

/**
 * Define la cantidad de elementos de la fuente y almacena su informacion y Entropia
 */
public class Fuente {
    private int cantElementos;
    private HashMap<String,Double> probabilidades = new HashMap<>();
    private HashMap<String,Integer> apariciones = new HashMap<>();


    public Fuente(HashMap<String,Integer> frecuencias) {
        this.cantElementos=frecuencias.size();
        this.apariciones=frecuencias;
        this.calculaProbabilidades(frecuencias);
    }

    public void calculaProbabilidades(HashMap<String,Integer>frecuencias){
        int suma=0;

        Set<String> Codigos = frecuencias.keySet();
        Iterator<String> itCod = Codigos.iterator();
        while(itCod.hasNext()) {
            String act = itCod.next();
            suma += frecuencias.get(act);
        }

        itCod = Codigos.iterator();
        while(itCod.hasNext()) {
            String act = itCod.next();
            this.probabilidades.put(act,(double) frecuencias.get(act)/suma);
        }

    }

    public Set<String> getSetCodigos() {
        return this.probabilidades.keySet();
    }

    /**
     * Calcula la entropia de la fuente. <br>
     * <b>Pre:</b> Tienen que estar cargados todos los simbolos con su probabilidad (Por lo menos 1)
     * @return Valor de la entropia
     */
    public double calculaEntropia() {
        double suma = 0;
        Set<String> Codigos = probabilidades.keySet();
        Iterator<String> itCod = Codigos.iterator();
        while(itCod.hasNext()) {
            String act = itCod.next();
            suma += probabilidades.get(act) * (-Math.log(probabilidades.get(act)) / Math.log(2));
        }
        return suma;
    }

    /**
     * Realiza el calculo de longitud media de la fuente
     * @return valor de longitud media
     */
    public double calculaLongitudMedia(){
        double longitud = 0;
        Set<String> Codigos = this.getSetCodigos();
        Iterator<String> itCod = Codigos.iterator();
        while(itCod.hasNext()) {
            String act=itCod.next();
            longitud+=(this.probabilidades.get(act)*act.length());
        }
        return longitud;
    }

    /**
     * Devuelve la longitud media de los codigos de Huffman asociados a la fuente.<br>
     * <b>Pre</b>: Existe la TablaHuffman asociada a la fuente
     * @param huffman TablaHuffman de la fuente
     * @return longitud media de los codigos Huffman
     */
    public double calculaLongitudMedia(HashMap<String,String> huffman){
        double longitud=0;

        for (var cod : huffman.entrySet()){
            longitud += this.probabilidades.get(cod.getKey())* cod.getValue().length();
        }

        return longitud;
    }

    public int calculaLongitudMediaRLC(int valMaxApariciones){
        int bits = Integer.SIZE-Integer.numberOfLeadingZeros(valMaxApariciones);


        bits+= Integer.SIZE-Integer.numberOfLeadingZeros(apariciones.size());
        if (bits%8!=0){
            bits=(int) (bits/8)+1;
            bits*=8;
        }
        return bits;
    }

    public int getMayorValor(){
        int max = 0;
        String letra="";
        Set<String> Codigos = apariciones.keySet();
        Iterator<String> itCod = Codigos.iterator();
        while(itCod.hasNext()) {
            String act = itCod.next();
            if (apariciones.get(act)>max) {
                max = apariciones.get(act);
                letra=act;
            }
        }
        return max;
    }

}