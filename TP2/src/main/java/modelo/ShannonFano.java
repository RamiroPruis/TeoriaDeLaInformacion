package modelo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.*;
import java.util.Map.Entry;

public class ShannonFano {
    private String originalString;
    private HashMap<Character, String> compressedResult;
    private HashMap<Character, Double> characterFrequency;
    private double entropy;
    private double averageLengthAfter;

    public double getRendimiento(){
        return entropy/averageLengthAfter;
    }

    public double getEntropy() {
        return entropy;
    }

    public double getAverageLengthAfter() {
        return averageLengthAfter;
    }

    public ShannonFano(String str){

        this.originalString = str;
        characterFrequency = new HashMap<Character, Double>();
        compressedResult = new HashMap<Character, String>();


        this.calculateFrequency();
        this.compressString();
        this.calculateEntropy();
        this.calculateAverageLengthAfterCompression();

    }

    private void calculateFrequency() {
        for (Character c : originalString.toCharArray()) {
            if (characterFrequency.containsKey(c)) {
                characterFrequency.put(c, characterFrequency.get(c) + 1.0);
            } else {
                characterFrequency.put(c, 1.0);
            }
        }
    }

    private void compressString() {
        List<Character> charList = new ArrayList<Character>();

        //Creamos un linkedHashMap ordenado decrecientemente por valor (frecuencia)
        LinkedHashMap<Character, Double> reverseSortedMap = new LinkedHashMap<>();
        characterFrequency.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));


        Iterator<Entry<Character, Double>> entries = reverseSortedMap.entrySet().iterator();
        while (entries.hasNext()) {
            Entry<Character, Double> entry = entries.next();
            charList.add(entry.getKey());
        }

        appendBit(compressedResult, charList, true);
    }

    private void appendBit(HashMap<Character, String> result, List<Character> charList, boolean up) {
        String bit = "";
        if (!result.isEmpty()) {
            bit = (up) ? "0" : "1";
        }

        for (Character c : charList) {
            String s = (result.get(c) == null) ? "" : result.get(c);
            result.put(c, s + bit);
        }

        if (charList.size() >= 2) {
            int separator = (int) Math.floor((float) charList.size() / 2.0);

            List<Character> upList = charList.subList(0, separator);
            appendBit(result, upList, true);
            List<Character> downList = charList.subList(separator, charList.size());
            appendBit(result, downList, false);
        }
    }

    private void calculateEntropy() {
        double probability = 0.0;
        for (Character c : originalString.toCharArray()) {
            probability = 1.0 * characterFrequency.get(c) / this.originalString.length();
            this.entropy += probability * (Math.log(1.0 / probability) / Math.log(2));
        }
    }

    private void calculateAverageLengthAfterCompression() {
        double probability = 0.0;
        for (Character c : originalString.toCharArray()) {
            probability = 1.0 * characterFrequency.get(c) / this.originalString.length();
            this.averageLengthAfter += probability * compressedResult.get(c).length();
        }
    }

    public String getStringCompressed(){
        StringBuilder str = new StringBuilder();
        for (Character c : originalString.toCharArray()) {
            str.append(compressedResult.get(c));
        }
        return str.toString();
    }

    public void writeCompressed(PrintStream output) throws IOException {
        //codificacion del String obtenido
        BigInteger numCod = new BigInteger(getStringCompressed(), 2);
        byte[] binary = numCod.toByteArray();
        output.write(binary);
        output.close();
    }

}
