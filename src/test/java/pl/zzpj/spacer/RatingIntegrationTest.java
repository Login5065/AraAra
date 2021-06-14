package pl.zzpj.spacer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
import pl.zzpj.spacer.dto.RatingDto;
import pl.zzpj.spacer.dto.NewAccountDto;
import pl.zzpj.spacer.dto.PictureDto;

import java.util.*;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RatingIntegrationTest {

    @Autowired
    MockMvc mvc;
    static String UserToken1;
    static String UserToken2;


    static String testPictureId;
    static String testPictureId2;



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

    String newRatingJson(RatingDto cd) {
        return JsonBuilderFactory.buildObject()
                .add("owner", cd.getOwner())
                .add("pictureId", cd.getPictureId())
                .add("rating", cd.getRating())
                .add("date", cd.getDate())
                .getJson().toString();
    }

    RatingDto newRatingFromJson(JsonObject jsonObject) {
        return RatingDto.builder()
                .owner(jsonObject.get("owner").getAsString())
                .pictureId(jsonObject.get("pictureId").getAsString())
                .rating(jsonObject.get("rating").getAsInt())
                .date(jsonObject.get("date").getAsString())
                .build();
    }

    List<RatingDto> newRatingsFromJson(JsonElement jsonElement) {
        ArrayList<RatingDto> list = new ArrayList<>();
        Iterator<JsonElement> jsonIter = jsonElement.getAsJsonArray().iterator();
        jsonIter.forEachRemaining((JsonElement element) -> list.add(newRatingFromJson(element.getAsJsonObject())));

        return list;
    }

    @Order(1)
    @Test
    void CreateTestData() throws Exception {
        // create test user account
        NewAccountDto newAccount = NewAccountDto.builder()
                .password("abc123")
                .username("bolek")
                .build();

        String newAccJson = newAccToJson(newAccount);

        mvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newAccJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        NewAccountDto newAccount2 = NewAccountDto.builder()
                .password("123abc")
                .username("lolek")
                .build();

        String newAccJson2 = newAccToJson(newAccount2);

        mvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newAccJson2)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        // user login
        String newLogin = newLoginJson("bolek", "abc123");

        MvcResult res = mvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newLogin)
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();

        UserToken1 = res.getResponse().getContentAsString();

        newLogin = newLoginJson("lolek", "123abc");

        res = mvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newLogin)
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();

        UserToken2 = res.getResponse().getContentAsString();

        // create/post picture
        PictureDto newPicture = PictureDto.builder()
                .title("Test picture posted Integrated")
                .url("https://picsum.photos/400")
                .id(UUID.randomUUID().toString())
                .build();

        String newPictureJson = newPictureJson(newPicture);

        mvc.perform(MockMvcRequestBuilders.post("/picture/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newPictureJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        newPicture = PictureDto.builder()
                .title("Another test picture posted Integrated")
                .url("https://picsum.photos/500")
                .id(UUID.randomUUID().toString())
                .build();

        newPictureJson = newPictureJson(newPicture);

        mvc.perform(MockMvcRequestBuilders.post("/picture/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newPictureJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        res = mvc.perform(MockMvcRequestBuilders.get("/pictures")
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
    void AddRating() throws Exception {
        RatingDto ratingDto = RatingDto.builder()
                .owner("bolek")
                .pictureId(testPictureId)
                .rating(3)
                .build();

        String ratingDtoJson = newRatingJson(ratingDto);
        mvc.perform(MockMvcRequestBuilders.post("/rating")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ratingDtoJson)
                .header("Authorization", "Bearer " + UserToken1)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

         ratingDto = RatingDto.builder()
                .owner("lolek")
                .pictureId(testPictureId)
                .rating(5)
                .build();

        ratingDtoJson = newRatingJson(ratingDto);
        mvc.perform(MockMvcRequestBuilders.post("/rating")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ratingDtoJson)
                .header("Authorization", "Bearer " + UserToken2)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        ratingDto = RatingDto.builder()
                .owner("bolek")
                .pictureId(testPictureId2)
                .rating(0)
                .build();

        ratingDtoJson = newRatingJson(ratingDto);
        mvc.perform(MockMvcRequestBuilders.post("/rating")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ratingDtoJson)
                .header("Authorization", "Bearer " + UserToken1)
        ).andExpect(MockMvcResultMatchers.status().isCreated());


        ratingDto = RatingDto.builder()
                .owner("bolek")
                .pictureId(testPictureId2)
                .rating(-20)
                .build();

        ratingDtoJson = newRatingJson(ratingDto);
        mvc.perform(MockMvcRequestBuilders.post("/rating")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ratingDtoJson)
                .header("Authorization", "Bearer " + UserToken1)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Order(3)
    @Test
    void FindRatingByPictureId() throws Exception{
        String requestPath = "/rating/picture/" + testPictureId;
        MvcResult res = mvc.perform(MockMvcRequestBuilders.get(requestPath)
                .header("Authorization", "Bearer " + UserToken1))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        List<RatingDto> ratingDtos = newRatingsFromJson(JsonParser.parseString(res.getResponse().getContentAsString()));
        Assertions.assertEquals(2, ratingDtos.size());

    }

    @Order(4)
    @Test
    void FindRatingByUserId()throws Exception{
        String requestPath = "/rating/username/" + "bolek";
        MvcResult res = mvc.perform((MockMvcRequestBuilders.get(requestPath)
        .header("Authorization", "Bearer " + UserToken1)
        )).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        List<RatingDto>ratingDtos=newRatingsFromJson(JsonParser.parseString(res.getResponse().getContentAsString()));
        Assertions.assertEquals(2,ratingDtos.size());

    }

    @Order(5)
    @Test
    void EditRating() throws Exception{
        String requestPath = "/rating/username/" + "bolek";
        MvcResult res = mvc.perform((MockMvcRequestBuilders.get(requestPath)
                .header("Authorization", "Bearer " + UserToken1)
        )).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        List<RatingDto>ratingDtos=newRatingsFromJson(JsonParser.parseString(res.getResponse().getContentAsString()));

        RatingDto ratingNew = ratingDtos.get(0);
        ratingNew.setRating(5);

        mvc.perform(MockMvcRequestBuilders.put("/rating/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newRatingJson(ratingNew))
                .header("Authorization", "Bearer " + UserToken1)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        res = mvc.perform((MockMvcRequestBuilders.get(requestPath)
                .header("Authorization", "Bearer " + UserToken1)
        )).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        ratingDtos=newRatingsFromJson(JsonParser.parseString(res.getResponse().getContentAsString()));

        Assertions.assertEquals((ratingDtos.get(0).getRating()).intValue(), ratingNew.getRating());

        ratingNew.setRating(90);

        mvc.perform(MockMvcRequestBuilders.put("/rating/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newRatingJson(ratingNew))
                .header("Authorization", "Bearer " + UserToken1)
        );

        res = mvc.perform((MockMvcRequestBuilders.get(requestPath)
                .header("Authorization", "Bearer " + UserToken1)
        )).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        ratingDtos=newRatingsFromJson(JsonParser.parseString(res.getResponse().getContentAsString()));

        Assertions.assertEquals((ratingDtos.get(0).getRating()).intValue(), 5);


    }


}
