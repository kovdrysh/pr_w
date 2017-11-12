import Graph.GraphBuilder;
import org.jfree.data.xy.XYSeries;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");

        String text = readFile("/texts/fairytale.txt");

        Map<String, Integer> wordsMap = countWords(text);
        printMap(wordsMap);

        Map<Integer, Integer> data = zipfs2ndLaw(wordsMap);
        List<Integer> X = new ArrayList<>();
        List<Integer> Y = new ArrayList<>();
        Iterator it = data.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            X.add((int)pair.getKey());
            Y.add((int)pair.getValue());
        }

        XYSeries functionGraph = GraphBuilder.buildSeries("Zipf`s 2nd Law", X, Y);
        GraphBuilder.buildGraph("Zipf", functionGraph);

        data = hipsLaw(wordsMap,
                countWords(readFile("/texts/1.txt")),
                countWords(readFile("/texts/2.txt")),
                countWords(readFile("/texts/3.txt")),
                countWords(readFile("/texts/4.txt")),
                countWords(readFile("/texts/5.txt")),
                countWords(readFile("/texts/6.txt")),
                countWords(readFile("/texts/7.txt")),
                countWords(readFile("/texts/8.txt")),
                countWords(readFile("/texts/9.txt")));
        X = new ArrayList<>();
        Y = new ArrayList<>();
        it = data.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            X.add((int)pair.getKey());
            Y.add((int)pair.getValue());
        }
        functionGraph = GraphBuilder.buildSeries("Hips Law", X, Y);
        GraphBuilder.buildGraph("Hips", functionGraph);

    }

    private static Map<String, Integer> countWords(String text){
        Map<String, Integer> wordsMap = new HashMap<>();
        text = text.toLowerCase().trim().replaceAll("[^а-яіїєґa-z'0-9]|[^\n]]"," ").replaceAll("  +"," ");
        String[] words = text.split(" ");
        for (String word : words){
            if (wordsMap.containsKey(word))
                wordsMap.put(word,wordsMap.get(word) + 1);
            else
                wordsMap.put(word, 1);
        }
        return sortByValue(wordsMap);
    }

    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(/*Collections.reverseOrder()*/))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    private static <K, V> void printMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey()
                    + " Value : " + entry.getValue());
        }
    }

    private static Map<Integer, Integer> zipfs2ndLaw(Map<String, Integer> wordsMap){
        Map<Integer, Integer> result = new TreeMap<>();
        Iterator it = wordsMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (result.containsKey((int)pair.getValue())){
                result.put((int)pair.getValue(), result.get(pair.getValue()) + 1);
            }
            else{
                result.put((int)pair.getValue(), 1);
            }
        }
        return result;
    }

    private static Map<Integer, Integer> hipsLaw(Map<String, Integer>... wordsMaps){
        Map<Integer, Integer> result = new TreeMap<>();
        for (Map<String, Integer> wordsMap :
                wordsMaps) {
            int all = 0;
            for (int v :
                    wordsMap.values()) {
                all += v;
            }
            result.put(all, wordsMap.keySet().size());
        }

        return result;
    }

    private static String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(Main.class.getResource(fileName).getFile()));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }

}
