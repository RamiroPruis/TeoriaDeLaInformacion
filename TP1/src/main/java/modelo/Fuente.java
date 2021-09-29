package modelo;

import java.io.PrintStream;
import java.util.*;

/**
 * Define la cantidad de elementos de la fuente y almacena su informacion y Entropia
 */
public class Fuente {
    private int cantElementos;
    private HashMap<String,Double> probabilidades = new HashMap<String,Double>();
    private HashMap<String,Double> informacion = new HashMap<String,Double>();


    public Fuente(int cantElementos) {
        this.cantElementos=cantElementos;
    }

    /**
     * Agrega el elemento a la fuente y calcula I(S)
     * @param simbolo simbolo a insertar en la fuente
     * @param probabilidad valor entre 0 y 1 que define la probabilidad de aparicion del simbolo
     */
    public void agregaElemento(String simbolo, double probabilidad){
        this.probabilidades.put(simbolo,probabilidad);
        double infoact = (-Math.log(probabilidad) / Math.log(2));
        this.informacion.put(simbolo,infoact);
    }

    public Set<String> getSetCodigos() {
        return this.probabilidades.keySet();
    }

    /**
     * Imprime los datos de la fuente. Muestra la cantidad de simbolos, la probabilidad de aparicion de cada uno y la cantidad de informacion
     * @param output Define la terminal de salida de los datos
     */
    public void imprimeFuente(PrintStream output){
        output.println("Datos de la fuente:");
        output.println("*********************************");
        output.println("Cantidad de simbolos: " + cantElementos);
        this.probabilidades.forEach((key,value) ->  output.printf("- %s: Probabilidad: %.4f | Cantidad de Informacion: %.3f  \n",key,value,informacion.get(key)));

        output.printf(" --> La entropia de la fuente es: %.3f bits/simbolo \n",calculaEntropia());
        output.println("\n*********************************");
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
            suma += probabilidades.get(act) * informacion.get(act);
        }
        return suma;
    }

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

    public double calculaLongitudMedia(HashMap<String,String> huffman){
        double longitud=0;

        for (var cod : huffman.entrySet()){
            longitud += this.probabilidades.get(cod.getKey())* cod.getValue().length();
        }

        return longitud;
    }

    public void cumpleKraft(int largo) {
        double sum = probabilidades.size() * (Math.pow(2, -largo));
        System.out.println("El valor de la sumatoria es: " + sum);
        if (sum <= 1)
            System.out.println("Cumple con la Inecuacion de Kraft");
        else {
            System.out.println("No cumple con la inecuacion");
        }
    }

}