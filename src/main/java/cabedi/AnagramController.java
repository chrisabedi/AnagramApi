package cabedi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class AnagramController implements ErrorController {

    private HashMap<String, ArrayList<String>> dataStore = new HashMap<>();
    private Gson gson = new Gson();
    private HashMap<String,String> routes = new HashMap<String,String>(){
        {
            put("/", "Initial Load of store, it can take a few seconds");
            put("POST /words.json", "Takes a JSON array of English-language words and adds them to the corpus (data store).");
            put("GET /anagrams/{word}.json", "Returns a JSON array of English-language words that are anagrams of the word passed in the URL. Optional Query Parameter of int Max");
            put("DELETE /words/{word}.json", "Deletes a single word from the data store.");
            put("DELETE /words.json", "Deletes all contents of the data store.");
        }
    };
    ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/")
    public HashMap<String,String> ApiHome() {

        dataStore = Utility.SetUpDataStore();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);

        return routes;

    }

    @PostMapping(value = "/words.json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void PostWords(@RequestBody final String json) throws IOException {

        // Store words in HashMap equivalent of Json
        JsonNode valuesNode = mapper.readTree(json).get("words");

        ArrayList<String> words = new ArrayList<>();
        for (JsonNode node : valuesNode) {
            words.add(node.asText());
        }

        String key = Utility.SortString(words.get(0));
        dataStore.put(key, words);
    }

    @RequestMapping(value = "/anagrams/{word}.json")
    public ArrayList<String> GetAnagrams(@PathVariable("word") final String word, @RequestParam(required = false) final String limit) {

        String key = Utility.SortString(word);
            if (dataStore.get(key)!=null) {
                ArrayList<String> anagrams = new ArrayList<>(dataStore.get(key));

                int max = anagrams.size() - 1;
                if (limit != null)
                    max = Integer.parseInt(limit);

                anagrams.remove(word);
                return new ArrayList<String>(anagrams.subList(0,max));
            }
            else
                return new ArrayList<>();

    }

    @DeleteMapping(value = "/words/{word}.json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ArrayList<String> DeleteAnagram(@PathVariable("word") final String word) {

        String key = Utility.SortString(word);
        ArrayList<String> anagrams = dataStore.get(key);

        if (anagrams.remove(word)) {
            dataStore.put(key, anagrams);
        }

        return anagrams;
    }

    @DeleteMapping(value = "/words.json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ArrayList<String> DeleteAnagram() {
        //Remove all Words
        if (dataStore.size()>0){
            dataStore.clear();
        }
            return new ArrayList<String>();
    }

   // @RequestMapping(value="/error")
    //public HashMap<String,String> handleError() {
       // return routes;
    //}

    @Override
    public String getErrorPath(){
        return "/error";
    }

}
