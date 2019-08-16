package cabedi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


@RestController
public class AnagramController implements ErrorController {

    @Autowired
    private  HashMap<String, ArrayList<String>> dataStore;

    @PostMapping(value = "/words.json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void PostWords(@RequestBody final String json) throws IOException {

        final ObjectMapper mapper = new ObjectMapper();

        HashMap<String,ArrayList<String>> words = mapper.readValue(json,new HashMap<String, ArrayList<String>>().getClass());

       String key = Utility.SortString(words.get("words").get(0));
       dataStore.put(key, words.get("words"));
    }

    @RequestMapping(value = "/anagrams/{word}.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, ArrayList<String>> GetAnagrams(@PathVariable("word") final String word, @RequestParam(required = false) final String limit) {

        String key = Utility.SortString(word);
            if (dataStore.get(key)!=null) {
                ArrayList<String> anagrams = new ArrayList<>(dataStore.get(key));

                int max = anagrams.size() - 1;
                if (limit != null)
                    max = Integer.parseInt(limit);

                anagrams.remove(word);

                ArrayList<String> returnJson = new ArrayList<String>(anagrams.subList(0,max));

                return new HashMap<String,ArrayList<String>>(){
                    {
                        put("anagrams",returnJson);
                    }
                };
            }
            else
                return new HashMap<String,ArrayList<String>>(){
                    {
                        put("anagrams", new ArrayList<String>());
                    }
                };

    }

    @DeleteMapping(value = "/words/{word}.json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public HashMap<String, ArrayList<String>> DeleteAnagram(@PathVariable("word") final String word) {

        String key = Utility.SortString(word);
        ArrayList<String> anagrams = dataStore.get(key);

        if (anagrams.remove(word)) {
            dataStore.put(key, anagrams);
        }

        return new HashMap<String,ArrayList<String>>(){
            {
                put("words", anagrams);
            }
        };
    }

    @DeleteMapping(value = "/words.json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public HashMap<String,ArrayList<String>> DeleteAnagram() {

        if (dataStore.size()>0){
            dataStore.clear();
        }
            return new HashMap<String, ArrayList<String>>(){
                {
                    put("words", new ArrayList<String>());
                }
            };
    }

    @Override
    public String getErrorPath(){
        return "/error";
    }

}
