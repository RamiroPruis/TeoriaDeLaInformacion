import java.util.*;

public class Fuente {
    private int cantElementos;
    private HashMap<String,Double> probabilidades = new HashMap<String,Double>();
    private HashMap<String,Double> informacion = new HashMap<String,Double>();


    public Fuente(int cantElementos) {
        this.cantElementos=cantElementos;
    }

    public void agregaElemento(String simbolo, double probabilidad){
        this.probabilidades.put(simbolo,probabilidad);
        double infoact = (-Math.log(probabilidad) / Math.log(2));
        this.informacion.put(simbolo,infoact);
        System.out.println("- " + simbolo + ": Probabilidad: " + String.format("%5.5f ",probabilidad) + " | Cantidad de Informacion:  " +String.format("%5.5f ",infoact) );

    }

    public void muestraDistribucion(){
        for (int i=0;i<cantElementos;i++){
            System.out.println("S"+ i+1 + ":\'" + probabilidades.get(i));
        }
    }

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