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

    static HashMap<String, String[]> SetUpDataStore(){

        String [] words = LoadDictionary().split("\n");
        HashMap<String, String[]> dataStore = new HashMap<>();

        //Load Dictionary Into the correct value in DataStore
        for  (String word : words) {
            List<String> currentAnagrams = null;
            String key = Utility.SortString(word);

            //If word is not the first anagram of these letters
            if ( dataStore.get(key)!=null)
                currentAnagrams = new ArrayList<String> (Arrays.asList(dataStore.get(key)));
            else
                currentAnagrams = new ArrayList<String>();
            String[] newAnagrams;

            if ((currentAnagrams.size()  >  0)) {
                currentAnagrams.add(word);
                newAnagrams = new String[currentAnagrams.size()];
                newAnagrams = currentAnagrams.toArray(newAnagrams);
            } else {
                newAnagrams = new String[1];
                newAnagrams[0] = word;
            }
            dataStore.put(key,newAnagrams);
        }
        return dataStore;
    }

    //array sort for 0(N log N)
    static String SortString(String str) {
        char []arr = str.toCharArray();
        Arrays.sort(arr);
        return String.valueOf(arr);
    }
}
