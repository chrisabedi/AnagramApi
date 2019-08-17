package cabedi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
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


    @Before
    public void setup() throws Exception {
        this.mvc.perform(delete("/words.json"));
    }

    @Test
    public void postWords() throws Exception {

        this.mvc.perform(post("/words.json")
                .content("{\"words\": [\"ared\", \"daer\",\"read\",\"dear\",\"dare\"]}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));

    }

    @Test
    public void getAnagrams() throws Exception {

        this.mvc.perform(post("/words.json")
                .content("{\"words\": [\"ared\", \"daer\",\"read\",\"dear\",\"dare\"]}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));

        this.mvc.perform(get("/anagrams/read.json")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"anagrams\": [\"ared\", \"daer\",\"dare\",\"dear\" ]}"));

    }


    @Test
    public void getAnagramsWithLimit() throws Exception {

        this.mvc.perform(post("/words.json")
                .content("{\"words\": [\"ared\", \"daer\",\"read\",\"dear\",\"dare\"]}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));

        this.mvc.perform(get("/anagrams/read.json?limit=2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"anagrams\": [\"ared\", \"daer\"]}"));

    }

    @Test
    public void getLargestAnagram() throws Exception {
        this.mvc.perform(post("/words.json")
                .content("{\"words\": [\"ared\", \"daer\",\"read\",\"dear\",\"dare\"]}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));

        this.mvc.perform(post("/words.json")
                .content("{\"words\": [\"enlist\", \"listen\",\"tinsel\",\"silent\"]}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));

        this.mvc.perform(get("/anagrams/largest")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"anagrams\": [\"ared\", \"daer\",\"read\",\"dear\",\"dare\"]}"));
    }


    @Test
    public void deleteAnagramsOfWord() throws Exception{
        this.mvc.perform(post("/words.json")
                .content("{\"words\": [\"ared\", \"daer\",\"read\",\"dear\",\"dare\"]}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));

        this.mvc.perform(delete("/words/remove/read.json")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(204));

        this.mvc.perform(get("/anagrams/read.json")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"anagrams\": []}"));

    }

    @Test
    public void deleteAnagram() throws Exception{

        this.mvc.perform(delete("/words/read.json"))
                .andExpect(status().is(204));

    }

    @Test
    public void deleteAllAnagrams() throws Exception{

        this.mvc.perform(delete("/words.json"))
                .andExpect(status().is(204));

    }
}