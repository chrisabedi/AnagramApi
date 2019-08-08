package cabedi;
import org.springframework.web.bind.annotation.*;


@RestController
public class AnagramController {

    //TODO: JsonStringify the routes
    //TODO: Right testss
    private RedisManager redisManager = new RedisManager("redis://localhost:6379/0");


    @RequestMapping(value = "/")
    public String HelloApi(){
        String content="";
        if (redisManager.StoreIsEmpty()) {
            redisManager.BulkSet();
        }
        return "{ \"Initial Load of the Redis store can take a few seconds\" }";

    }

    @PostMapping(value = "/words.json",consumes="application/json")
    public boolean PostWords(@RequestBody String words) throws Exception {
        // Store words in Redis

        //TODD: Jsonify the Request body and store in redis
        //for(String word: words.split())
       redisManager.Set(RedisManager.SortString(words), word)
        return true;
    }

    @RequestMapping(value = "/anagrams/{word}.json")
    public String GetAnagrams (@PathVariable("word") final String word){
        return redisManager.Get(RedisManager.SortString(word));
    }

    @DeleteMapping(value="/words/{word}")
    public boolean DeleteAnagram(@PathVariable("word") final String word){
        //Remove the one Word
        return true;
    }

    @DeleteMapping(value="/words.json")
    public String DeleteAnagram(){
        //Remove all Words
        redisManager.DeleteAll();
        return "You flushed our DB :(";
    }


}
