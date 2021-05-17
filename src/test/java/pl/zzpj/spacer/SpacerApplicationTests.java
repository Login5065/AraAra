package pl.zzpj.spacer;

import lombok.SneakyThrows;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.zzpj.spacer.dto.NewAccountDto;
import org.jglue.fluentjson.JsonBuilderFactory;
import pl.zzpj.spacer.repositories.AccountRepository;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SpacerApplicationTests {

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
    @SneakyThrows
    @Test
    void registerTest() {
        NewAccountDto newTestAccount = NewAccountDto.builder()
                .username("__TEST_ACCOUNT")
                .password("__TEST_PASSWORD")
                .build();

        String newTestAccountJson = newAccToJson(newTestAccount);

        mvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newTestAccountJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Order(2)
    @SneakyThrows
    @Test
    void unauthorisedTest() {
        mvc.perform(MockMvcRequestBuilders.get("/hello")
                .header("Authorisation", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Order(3)
    @SneakyThrows
    @Test
    void loginTest() {
        String newTestLoginJson = newLoginJson("__TEST_ACCOUNT", "__TEST_PASSWORD");

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newTestLoginJson)
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();

        token = result.getResponse().getContentAsString();
    }

    @Order(4)
    @SneakyThrows
    @Test
    void authorisedTest() {
        mvc.perform(MockMvcRequestBuilders.get("/hello")
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Order(5)
    @SneakyThrows
    @Test
    void getAllAccountsTest() {
        mvc.perform(MockMvcRequestBuilders.get("/accounts")
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Order(6)
    @SneakyThrows
    @Test
    void getAccountTest() {
        mvc.perform(MockMvcRequestBuilders.get("/account/__TEST_ACCOUNT")
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Order(7)
    @SneakyThrows
    @Test
    void editAccountTest() {
        NewAccountDto newTestAccountEdit = NewAccountDto.builder()
                .username("__TEST_ACCOUNT")
                .password("__TEST_PASSWORD_EDITED")
                .firstName("Tester")
                .lastName("Chester")
                .build();

        String newTestAccountEditJson = newAccToJson(newTestAccountEdit);

        mvc.perform(MockMvcRequestBuilders.put("/account/__TEST_ACCOUNT")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newTestAccountEditJson))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Order(8)
    @SneakyThrows
    @Test
    void deleteAccountTest() {
        mvc.perform(MockMvcRequestBuilders.delete("/account/__TEST_ACCOUNT")
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}
