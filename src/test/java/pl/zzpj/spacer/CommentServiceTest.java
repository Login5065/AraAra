package pl.zzpj.spacer;

import org.jglue.fluentjson.JsonBuilderFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.zzpj.spacer.dto.NewAccountDto;
import pl.zzpj.spacer.dto.PictureDto;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentServiceTest {

    String newAccToJson(NewAccountDto nad) {
        return JsonBuilderFactory.buildObject()
                .add("username", nad.getUsername())
                .add("password", nad.getPassword())
                .add("firstName", nad.getFirstName())
                .add("lastName", nad.getLastName())
                .getJson().toString();
    }

    String newLoginJson(String username, String password) {
        return JsonBuilderFactory.buildObject()
                .add("username", username)
                .add("password", password)
                .getJson().toString();
    }

    @Autowired
    MockMvc mvc;

    static String token;

    @Order(1)
    @Test
    void CreateTestData() throws Exception {
        // create test user account
        NewAccountDto newAccount = NewAccountDto.builder()
                .password("pasuworudo")
                .username("usero")
                .build();

        String newAccJson = newAccToJson(newAccount);

        mvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newAccJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        // user login
        String newLogin = newLoginJson("usero", "pasuworudo");

        MvcResult res = mvc.perform(MockMvcRequestBuilders.post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(newLogin)
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();

        token = res.getResponse().getContentAsString();

        // create/post picture
        PictureDto newPicture = PictureDto.builder()
                .title("Test picture posted")
                .url("https://picsum.photos/200")
                .build();

        String newPictureJson = "";

        mvc.perform(MockMvcRequestBuilders.post("/picture/add")
        .contentType(MediaType.APPLICATION_JSON)
        .content(newPictureJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
    }
}
