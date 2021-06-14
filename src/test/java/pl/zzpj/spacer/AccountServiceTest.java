package pl.zzpj.spacer;

import com.google.gson.JsonParser;
import org.jglue.fluentjson.JsonBuilderFactory;
import org.junit.jupiter.api.*;
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
import pl.zzpj.spacer.service.AccountServiceImpl;

import java.util.List;
import java.util.UUID;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountServiceTest
{

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

    String newPictureJson(PictureDto pd) {
        return JsonBuilderFactory.buildObject()
                .add("id", pd.getId())
                .add("url", pd.getUrl())
                .add("title", pd.getTitle())
                .getJson().toString();
    }

    @Autowired
    AccountServiceImpl accountService;

    @Autowired
    MockMvc mvc;

    static String tokenUser1;
    static String testPictureId;
    static String testPictureId2;

    @Order(1)
    @Test
    void CreateTestData() throws Exception {
        // create test user account
        NewAccountDto newAccount = NewAccountDto.builder()
                .password("password1")
                .username("user1")
                .build();

        String newAccJson = newAccToJson(newAccount);

        mvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newAccJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        NewAccountDto newAccount2 = NewAccountDto.builder()
                .password("password2")
                .username("user2")
                .build();

        String newAccJson2 = newAccToJson(newAccount2);

        mvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newAccJson2)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        // user login
        String newLogin = newLoginJson("user1", "password1");

        MvcResult res = mvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newLogin)
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();

        tokenUser1 = res.getResponse().getContentAsString();

        // create/post picture
        PictureDto newPicture = PictureDto.builder()
                .title("Test picture posted Account Service")
                .url("https://picsum.photos/401")
                .id(UUID.randomUUID().toString())
                .build();

        String newPictureJson = newPictureJson(newPicture);

        mvc.perform(MockMvcRequestBuilders.post("/picture/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newPictureJson)
                .header("Authorization", "Bearer " + tokenUser1)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        newPicture = PictureDto.builder()
                .title("Another test picture posted Account Service")
                .url("https://picsum.photos/501")
                .id(UUID.randomUUID().toString())
                .build();

        newPictureJson = newPictureJson(newPicture);

        mvc.perform(MockMvcRequestBuilders.post("/picture/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newPictureJson)
                .header("Authorization", "Bearer " + tokenUser1)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        res = mvc.perform(MockMvcRequestBuilders.get("/pictures")
                .header("Authorization", "Bearer " + tokenUser1)
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();

        String resString = res.getResponse().getContentAsString();
        testPictureId = JsonParser.parseString(resString)
                .getAsJsonArray().get(0)
                .getAsJsonObject().get("id")
                .getAsString();
        testPictureId2 = JsonParser.parseString(resString)
                .getAsJsonArray().get(1)
                .getAsJsonObject().get("id")
                .getAsString();
    }

    @Order(2)
    @Test
    void AddLikedPicture() throws Exception {

        accountService.addLikedPicture("user1", testPictureId);
        accountService.addLikedPicture("user2", testPictureId);
        accountService.addLikedPicture("user2", testPictureId2);
    }

    @Order(3)
    @Test
    void GetLikedPicturesByUsername() {

        List<String> out = accountService.getLikedPicturesByUsername("user1");
        Assertions.assertEquals(out.size(), 1);

        out = accountService.getLikedPicturesByUsername("user2");
        Assertions.assertEquals(out.size(), 2);
    }

    @Order(4)
    @Test
    void RemoveLikedPicture() throws Exception {

        accountService.removeLikedPicture("user1", testPictureId);
        accountService.removeLikedPicture("user2", testPictureId2);


        List<String> out = accountService.getLikedPicturesByUsername("user1");
        Assertions.assertEquals(out.size(), 0);

        out = accountService.getLikedPicturesByUsername("user2");
        Assertions.assertEquals(out.size(), 1);
    }
}