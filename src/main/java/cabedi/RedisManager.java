package cabedi;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
public class RedisManager {

    private RedisClient redisClient;

    public RedisManager(String connectionString){
        redisClient = RedisClient.create(connectionString);
    }

    public void Set(String key, String value){
        StatefulRedisConnection<String, String> connection= redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();
        syncCommands.set(key,value);
        connection.close();
    }

    public String Get(String key){
        StatefulRedisConnection<String, String> connection= redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();
        String value = syncCommands.get(key);
        connection.close();
        return value;
    }

    public void DeleteAll(){
        StatefulRedisConnection<String, String> connection= redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();
        String value = syncCommands.flushall();
        connection.close();
    }

    public String LoadDictionary(){
        String content = "";
        //Implement Read from File if File is not Read
        if (StoreIsEmpty()){
            try{
                content = new String(Files.readAllBytes(Paths.get("src/main/dictionary.txt")));

        } catch (IOException ex){
                ex.printStackTrace();
            }
        }
        return content;
    }

    public boolean BulkSet(){
        StatefulRedisConnection<String, String> connection= redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();
        String [] words = LoadDictionary().split("\n");

        for  (String word : words){
            String sortedString = SortString(word);
            String newValue = syncCommands.get(sortedString);
            if (newValue!=null)
                newValue += String.format(" %s", word);
            else
                newValue = word;
            syncCommands.set(sortedString, newValue);
        }
        //Bulk upload the Dictionary keys, values
        connection.close();
        return false;
    }


    // function to print string in sorted order
    public static String SortString(String str) {
        char []arr = str.toCharArray();
        Arrays.sort(arr);
        return String.valueOf(arr);
    }

    public boolean StoreIsEmpty(){
        StatefulRedisConnection<String, String> connection= redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();
         List<String> values = syncCommands.keys("*");
        return (values.size()==0);
    }
}
