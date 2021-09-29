package modelo.huffman;

class Nodo
{
    String simbolo;
    Integer freq;
    Nodo izq = null, der = null;

    Nodo(String ch, Integer freq)
    {
        this.simbolo = ch;
        this.freq = freq;
    }
    public Nodo(String ch, Integer freq, Nodo left, Nodo right)
    {
        this.simbolo = ch;
        this.freq = freq;
        this.izq = left;
        this.der = right;
    }
}
