package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class ShannonFano {
    private String originalString;
    private HashMap<Character, String> compressedResult;
    private HashMap<Character, Double> characterFrequency;

    public ShannonFano(String str){
        characterFrequency = new HashMap<Character, Double>();
        compressedResult = new HashMap<Character, String>();

        this.calculateFrequency();
        this.compressString();
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

        Iterator<Entry<Character, Double>> entries = characterFrequency.entrySet().iterator();
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

    public String getStringCompressed(){
        StringBuilder str = new StringBuilder();
        for (Character c : originalString.toCharArray()) {
            str.append(compressedResult.get(c));
        }
        return str.toString();
    }

}
