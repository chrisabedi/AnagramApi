package cabedi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class Utility {


    static HashMap<String, ArrayList<String>> SetUpDataStore(){

        String [] words = LoadDictionary().split("\n");
        HashMap<String, ArrayList<String>> dataStore = new HashMap<>();

        //Load Dictionary Into the correct value in DataStore
        for  (String word : words) {
            ArrayList<String> currentAnagrams = null;
            String key = Utility.SortString(word);

            //If word is not the first anagram of these letters
            if ( dataStore.get(key)!=null) {
                currentAnagrams = dataStore.get(key);
                currentAnagrams.add(word);
            }
            else
                currentAnagrams = new ArrayList<String>(){
                    {
                        add(word);
                    }
                };

            dataStore.put(key,currentAnagrams);
        }
        return dataStore;
    }

    private static String LoadDictionary(){
        String content = "";
        //Implement read from file
        try{
            content = new String(Files.readAllBytes(Paths.get("src/main/resources/dictionary.txt")));

        } catch (IOException ex){
            ex.printStackTrace();
        }
        return content;
    }

    public static <K, V extends Comparable<? super V>> Map.Entry<String, ArrayList<String>> maxUsingCollectionsMax(HashMap<String, ArrayList<String>> map ) {
        //This is used to identify the largest anagram in the dataStore ("/anagrams/largest") endpoint

        Map.Entry<String, ArrayList<String>> maxEntry = Collections.max(map.entrySet(), (Comparator<Map.Entry<String, ArrayList<String>>>) (e1, e2) -> {
            int first = ((ArrayList<String>) e1.getValue()).size();
            int second = ((ArrayList<String>) e2.getValue()).size();

            if (first >= second)
                return 1;
            else
                return -1;
        });

        return maxEntry;

    }

    public static int safeMaxForLimit(int anagramsSize,String limit){
        int max = anagramsSize- 1;
        if (max<0)
            max=0;
        if (limit != null)
            max = Integer.parseInt(limit);
        return max;
    }
    //array sort for 0(N log N)
    static String SortString(String str) {

        char []tempArray = str.toCharArray();
        Arrays.sort(tempArray);
        return String.valueOf(tempArray);
    }
}
