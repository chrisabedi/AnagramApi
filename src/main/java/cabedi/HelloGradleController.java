package cabedi;
import org.springframework.web.bind.annotation.*;


@RestController
public class HelloGradleController {

    @RequestMapping(value = "/")
    public String helloGradle(){
        return "Hello Gradle!";
    }

    @PostMapping(value = "/words.json",consumes="application/json")
    public boolean PostWords(@RequestBody String words) throws Exception {
        // Store all words in Redis
        System.out.println(words);
        return true;
    }

    @RequestMapping(value = "/anagrams/{word}")
    public String GetAnagrams (@PathVariable("word") final String word){
        // Return all anagrams of word from Redis
        return String.format("HelloWorld at anagrams with path variable %s", word);
    }


    @DeleteMapping(value="/words/{word}")
    public boolean DeleteAnagram(@PathVariable("word") final String word){
        //Remove the one Word
        return true;
    }

    @DeleteMapping(value="/words.json")
    public boolean DeleteAnagram(){
        //Remove all Words
        return true;
    }


}
