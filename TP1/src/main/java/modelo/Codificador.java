package modelo;

import java.util.*;

public class Codificador {
    private ArrayList<String> codigos;

    public Codificador() {
        codigos = new ArrayList<String>();
    }

    public void agregaCodigo(Set<String> codes) {
        codigos.addAll(codes);

    }

    public void ordenaLista() {
        Collections.sort(codigos);
    }

    public boolean esCodigoBloque() {
        int i = 0, length = codigos.size();

        return codigos.get(0).length() == codigos.get(length - 1).length();

    }

    public boolean esNoSingular() {
        int i = 0, length = codigos.size();

        // En un TreeSet no puede haber elementos repetidos entonces si hay elementos
        // repetidos no los añade y el tamaño sera menor
        TreeSet<String> nuevo = new TreeSet<String>(codigos);

        return nuevo.size() == codigos.size();

    }

    public boolean esInstantaneo() {
        int i = 0, j, length = codigos.size();
        boolean condicion = true;
        String comparacion;

        while (i < length && condicion) {

            j = i + 1;
            while (j < length && condicion) {
                comparacion = obtenerPalabraCortada(codigos.get(j), codigos.get(i).length());
                if (codigos.get(i).equals(comparacion)) {
                    condicion = false;
                }
                j++;
            }
            i++;
        }
        return condicion;
    }

    private String obtenerPalabraCortada(String s, int largo) {
        StringBuilder nuevaCadena = new StringBuilder();

        for (int i = 0; i < largo; i++)
            nuevaCadena.append(s.charAt(i));

        return nuevaCadena.toString();
    }

//    public void añadirCodigos() {
//        Scanner scan = new Scanner(System.in).useLocale(Locale.US);
//        int cantidad;
//        System.out.println("Escribe a continuacion la cantidad y luego las cadenas");
//        cantidad = scan.nextInt();
//        scan.nextLine();
//        for (int i = 0; i < cantidad; i++)
//            codigos.add(scan.nextLine());
//
//        ordenaLista();
//
//    }

//    public void muestraCodigos() {
//        for (int i = 0; i < this.codigos.size(); i++) {
//            System.out.println(codigos.get(i));
//
//        }
//    }

}
