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
       // System.out.printf("- %s: Probabilidad: %.4f | Cantidad de Informacion: %.3f  \n",simbolo,probabilidad,infoact);
    }


    public void imprimeFuente(){
        System.out.println("Datos de la fuente:");
        System.out.println("*********************************");
        System.out.println("Cantidad de simbolos: " + cantElementos);
        this.probabilidades.forEach((key,value) ->  System.out.printf("- %s: Probabilidad: %.4f | Cantidad de Informacion: %.3f  \n",key,value,informacion.get(key)));

        System.out.printf(" --> La entropia de la fuente es: %.3f bits/simbolo \n",calculaEntropia());
    }

    public void muestraDistribucion(){
        for (int i=0;i<cantElementos;i++){
            System.out.println("S"+ i+1 + ":\'" + probabilidades.get(i));
        }
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
}