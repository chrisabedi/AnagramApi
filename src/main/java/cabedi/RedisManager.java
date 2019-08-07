package cabedi;

import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class RedisManager {

    private RedisClient redisClient;

    public RedisManager(String connectionString){
        redisClient = RedisClient.create("redis://password@localhost:6379/0");
    }

    public void set(String key, String value){
        StatefulRedisConnection<String, String> connection= redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();
        syncCommands.set(key,value);
        connection.close();
    }

    public  String get(String key){
        StatefulRedisConnection<String, String> connection= redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();
        String value = syncCommands.get(key);
        connection.close();
        return value;
    }

    private String LoadFromDictionary(){
        String content = "";
        //Implement Read from File if File File is not Read
        if (StoreIsEmpty()){
            try{
                content = new String(Files.readAllBytes(Paths.get("dictionary.txt")));

        } catch (IOException ex){
                ex.printStackTrace();
            }
        }
        return content;
    }

    public boolean BulkSet(){
        StatefulRedisConnection<String, String> connection= redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();
        //syncCommands.set
        //Bulk upload the Dictionary keys, values
        connection.close();
        return false;
    }

    private boolean StoreIsEmpty(){
        StatefulRedisConnection<String, String> connection= redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();
         List<String> values = syncCommands.keys("*");
        return (values.size()==0);
    }
}
