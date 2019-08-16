package cabedi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class Utility {

    private static String LoadDictionary(){
        String content = "";
        //Implement Read from File if File is not Read
        try{
            content = new String(Files.readAllBytes(Paths.get("src/main/dictionary.txt")));

        } catch (IOException ex){
            ex.printStackTrace();
        }
        return content;
    }

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

    //array sort for 0(N log N)
    static String SortString(String str) {

        char []tempArray = str.toCharArray();
        Arrays.sort(tempArray);
        return String.valueOf(tempArray);
    }
}
