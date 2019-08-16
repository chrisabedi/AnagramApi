package cabedi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AnagramController.class)
public class AnagramControllerTest {

    @Autowired
    private MockMvc mvc;


    @Test
    public void postWords() throws Exception {

        this.mvc.perform(post("/words.json")
                .content("{\"words\": [\"read\",\"dear\",\"dare\"]}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));

    }

    @Test
    public void getAnagrams() throws Exception {

        this.mvc.perform(get("/anagrams/read.json")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"anagrams\": [\"ared\", \"daer\",\"dare\",\"dear\" ]}"));

    }

    @Test
    public void deleteAnagram() throws Exception{

        this.mvc.perform(delete("/words/read.json"))
                .andExpect(status().is(204));

    }

    @Test
    public void deleteAnagram1() throws Exception{

        this.mvc.perform(delete("/words.json"))
                .andExpect(status().is(204));

    }
}