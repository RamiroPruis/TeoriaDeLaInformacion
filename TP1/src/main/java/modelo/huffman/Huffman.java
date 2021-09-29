package modelo.huffman;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class ImplementComparator implements Comparator<Nodo> {
    public int compare(Nodo x, Nodo y) {
        return x.freq - y.freq;
    }
}
public class Huffman
{
    public static void encode(Nodo root, String str, Map<String, String> huffmanCode)
    {
        if (root == null) {
            return;
        }
        if (esHoja(root)) {
            huffmanCode.put(root.simbolo, str.length() > 0 ? str : "1");
        }
        encode(root.izq, str + '0', huffmanCode);
        encode(root.der, str + '1', huffmanCode);
    }

    public static boolean esHoja(Nodo root) {
        return root.izq == null && root.der == null;
    }


    public static Map<String,String> creaArbolHuffman(Map<String,Integer> datos)
    {
        //Creo una cola con su criterio de ordenamiento (menor a mayor frecuencia)
        PriorityQueue<Nodo> pq;
        pq = new PriorityQueue<>(new ImplementComparator());

        //Paso los datos a la cola
        for (var entry: datos.entrySet()) {
            pq.add(new Nodo(entry.getKey(), entry.getValue()));
        }

        while (pq.size() != 1)
        {
            Nodo izq = pq.poll();
            Nodo der = pq.poll();

            //Creo nodo de suma y lo agrego a la cola
            int sum = izq.freq + der.freq;
            pq.add(new Nodo(null, sum, izq, der));
        }

        //raiz del arbol de Huffman
        Nodo root = pq.peek();

        // Traverse the Huffman tree and store the Huffman codes in a map
        Map<String, String> huffmanCode = new HashMap<>();
        encode(root, "", huffmanCode);

        // Print the Huffman codes

        System.out.println("Codidos de Huffman: "+ huffmanCode);

        return huffmanCode;

    }
}
