package cabedi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@RestController
public class AnagramController {

    //TODO: JsonStringify the routes
    //TODO: Right tests


    private HashMap<String, String[]> dataStore = new HashMap<>();
    private Gson gson = new Gson();

    @RequestMapping(value = "/")
    public String HelloApi() {

        dataStore = Utility.SetUpDataStore();
        return "{\"Initial Load of store can take a few seconds\"}";

    }

    @PostMapping(value = "/words.json", consumes = "application/json")
    public String PostWords(@RequestBody final String json){

        // Store words in HashMap equivalent of Json
        Type type = new TypeToken<HashMap<String, String[]>>() {
        }.getType();
        HashMap<String, String[]> anagramJson = gson.fromJson(json, type);
        String[] anagrams = anagramJson.get("anagrams");


        String key = Utility.SortString(anagrams[0]);
        dataStore.put(key, anagrams);

        return gson.toJson(anagrams);
    }

    @RequestMapping(value = "/anagrams/{word}.json")
    public String GetAnagrams(@PathVariable("word") final String word, @RequestParam(required = false) final Integer max) {
        String key = Utility.SortString(word);

        String [] anagrams = dataStore.get(key);

        if (max != null)
            return gson.toJson(Arrays.copyOfRange(anagrams,0, max));

        return gson.toJson(anagrams);
    }


    @DeleteMapping(value = "/words/{word}.json")
    public String[] DeleteAnagram(@PathVariable("word") final String word) {

        String key = Utility.SortString(word);
        ArrayList<String> currentAnagrams = new ArrayList<String>(Arrays.asList(dataStore.get(key)));
        String [] error  = new String[] {"Word", word, "wasn't","found"};

        if (!currentAnagrams.remove(word)) {
            return error;
        }

        String[] newAnagrams = new String[currentAnagrams.size()];
        newAnagrams = currentAnagrams.toArray(newAnagrams);

        dataStore.put(key, newAnagrams);
        return newAnagrams;
    }

    @DeleteMapping(value = "/words.json")
    public String DeleteAnagram() {
        //Remove all Words
        if (dataStore.size()>0)
            dataStore.clear();
        return "You flushed our DB :)";
    }


}
