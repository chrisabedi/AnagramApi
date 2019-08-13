/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package cabedi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@AutoConfigureMockMvc
public class AnagramControllerTest {

    @Autowired
    private MockMvc mvc;

    private void setUpStore() throws Exception {
        String json="{\"anagrams\": [\"word\",\"drow\"]}";
        mvc.perform(post("/words.json")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
    }

    @Test
    public void ApiHome() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void TestPostWordsReturnsValidAnagrams() throws Exception {
        String json="{\"anagrams\": [\"word\",\"drow\"]}";
        mvc.perform(post("/words.json")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect((status().isOk()))
                .andExpect((content().string("[\"word\",\"drow\"]")));
    }

    @Test
    public void TestGetAnagramsReturnsValidAnagrams() throws Exception {
        setUpStore();
        mvc.perform(get("/anagrams/word.json"))
                .andExpect(((status().isOk())))
                .andExpect((content().string("[\"word\",\"drow\"]")));
    }

    @Test
    public void TestDeleteAnagramDeletesAnagram() throws Exception {
        setUpStore();
        mvc.perform(delete("/words/word.json"))
                .andExpect((status().isOk()))
                .andExpect((content().string("[\"drow\"]")));
    }

    @Test
    public void TestDeleteAllAnagramRouteValid() throws Exception {
        mvc.perform(delete("/words.json"))
                .andExpect((status().isOk()))
                .andExpect(content().string("You flushed our DB :)"));
    }
}

